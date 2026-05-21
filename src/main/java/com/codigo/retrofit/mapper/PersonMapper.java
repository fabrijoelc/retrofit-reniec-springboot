package com.codigo.retrofit.mapper;

import com.codigo.retrofit.aggregates.constants.Constants;
import com.codigo.retrofit.aggregates.request.PersonUpdateRequest;
import com.codigo.retrofit.aggregates.response.ReniecResponse;
import com.codigo.retrofit.entity.PersonEntity;

import java.sql.Timestamp;

public class PersonMapper {

    private PersonMapper() {
    }

    public static PersonEntity fromReniecResponse(ReniecResponse reniecResponse) {
        return PersonEntity.builder()
                .names(reniecResponse.getNombres())
                .lastName(reniecResponse.getApellidoPaterno())
                .motherLastName(reniecResponse.getApellidoMaterno())
                .fullName(reniecResponse.getNombreCompleto())
                .numberDocument(reniecResponse.getNumeroDocumento())
                .status(Constants.STATUS_ACTIVE)
                .userCreated(Constants.USER_ADMIN)
                .dateCreated(new Timestamp(System.currentTimeMillis()))
                .build();
    }

    public static void updateEntity(PersonEntity personEntity, PersonUpdateRequest request) {
        personEntity.setNames(request.getNames());
        personEntity.setLastName(request.getLastName());
        personEntity.setMotherLastName(request.getMotherLastName());
        personEntity.setFullName(request.getFullName());
        personEntity.setTypeDocument(request.getTypeDocument());
        personEntity.setNumberDocument(request.getNumberDocument());
        personEntity.setCheckDigit(request.getCheckDigit());
        personEntity.setStatus(request.getStatus());
        personEntity.setUserUpdated(Constants.USER_ADMIN);
        personEntity.setDateUpdated(new Timestamp(System.currentTimeMillis()));
    }

    public static void updateStatus(PersonEntity personEntity, String status) {
        personEntity.setStatus(status);
        personEntity.setUserUpdated(Constants.USER_ADMIN);
        personEntity.setDateUpdated(new Timestamp(System.currentTimeMillis()));
    }
}
