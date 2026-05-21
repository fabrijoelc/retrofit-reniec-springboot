package com.codigo.retrofit.service.impl;


import com.codigo.retrofit.aggregates.constants.Constants;
import com.codigo.retrofit.aggregates.request.PersonUpdateRequest;
import com.codigo.retrofit.aggregates.response.ReniecResponse;
import com.codigo.retrofit.aggregates.response.ResponseBase;
import com.codigo.retrofit.entity.PersonEntity;
import com.codigo.retrofit.exception.ConsultaReniecException;
import com.codigo.retrofit.mapper.PersonMapper;
import com.codigo.retrofit.repository.PersonRepository;
import com.codigo.retrofit.retrofit.ClientReniecService;
import com.codigo.retrofit.retrofit.ClientRetrofit;
import com.codigo.retrofit.service.PersonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Service
@Log4j2
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;

    ClientReniecService retrofitPreConfig =
            ClientRetrofit.getRetrofit()
                    .create(ClientReniecService.class);

    @Value("${value.token}")
    private String token;
    @Override
    public ReniecResponse findByDni(String dni) throws IOException {
        if (token == null || token.isBlank()) {
            throw new ConsultaReniecException("Configura la variable de entorno DECOLECTA_TOKEN");
        }
        Response<ReniecResponse>  executeReniec = preparedClient(dni).execute();
        if(executeReniec.isSuccessful() && Objects.nonNull(executeReniec.body())){
            return executeReniec.body();
        }
        throw new ConsultaReniecException("No se pudo consultar Decolecta RENIEC. Codigo: "
                + executeReniec.code());
    }

    @Override
    public ResponseBase<PersonEntity> registerPerson(String dni) throws IOException {
        personRepository.findByNumberDocument(dni)
                .ifPresent(person -> {
                    throw new IllegalArgumentException("La persona con DNI " + dni + " ya existe");
                });

        ReniecResponse reniecResponse = findByDni(dni);
        PersonEntity personEntity = PersonMapper.fromReniecResponse(reniecResponse);
        PersonEntity personSave = personRepository.save(personEntity);
        return buildResponse(2001, "Persona registrada correctamente", Optional.of(personSave));
    }

    @Override
    public ResponseBase<List<PersonEntity>> findPersonActive() {
        List<PersonEntity> listPersonActive =
                personRepository.findAllByStatus(Constants.STATUS_ACTIVE);
        return buildResponse(2001, "Todo OK!!", Optional.of(listPersonActive));
    }

    @Override
    public ResponseBase<PersonEntity> findById(Long id) {
        Optional<PersonEntity> person = personRepository.findById(id);
        if (person.isEmpty()) {
            return buildResponse(4004, "No se encontro la persona", Optional.empty());
        }
        return buildResponse(2001, "Todo OK!!", person);
    }

    @Override
    public ResponseBase<PersonEntity> updatePerson(Long id, PersonUpdateRequest request) {
        Optional<PersonEntity> person = personRepository.findById(id);
        if (person.isEmpty()) {
            return buildResponse(4004, "No se encontro la persona", Optional.empty());
        }

        PersonEntity personEntity = person.get();
        PersonMapper.updateEntity(personEntity, request);
        PersonEntity personUpdate = personRepository.save(personEntity);
        return buildResponse(2001, "Persona actualizada correctamente", Optional.of(personUpdate));
    }

    @Override
    public ResponseBase<PersonEntity> updateStatus(Long id, String status) {
        Optional<PersonEntity> person = personRepository.findById(id);
        if (person.isEmpty()) {
            return buildResponse(4004, "No se encontro la persona", Optional.empty());
        }

        PersonEntity personEntity = person.get();
        PersonMapper.updateStatus(personEntity, status);
        PersonEntity personUpdate = personRepository.save(personEntity);
        return buildResponse(2001, "Persona actualizada correctamente", Optional.of(personUpdate));
    }

    @Override
    public ResponseBase<PersonEntity> deletePerson(Long id) {
        return updateStatus(id, Constants.STATUS_INACTIVE);
    }

    private Call<ReniecResponse> preparedClient(String dni){
        return retrofitPreConfig.findReniec("Bearer "+token,dni);
    }

    private <T> ResponseBase<T> buildResponse(
            int code, String message, Optional<T> optional) {
        ResponseBase<T> responseBase = new ResponseBase<>();
        responseBase.setCode(code);
        responseBase.setMessage(message);
        responseBase.setEntity(optional);
        return responseBase;
    }
}
