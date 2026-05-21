package com.codigo.retrofit.controller;

import com.codigo.retrofit.aggregates.request.PersonUpdateRequest;
import com.codigo.retrofit.aggregates.response.ReniecResponse;
import com.codigo.retrofit.aggregates.response.ResponseBase;
import com.codigo.retrofit.entity.PersonEntity;
import com.codigo.retrofit.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping("/api/person/")
@RequiredArgsConstructor
public class PersonController {

    private final PersonService personService;

    @GetMapping("/find/{dni}")
    public ResponseEntity<ReniecResponse> findPerson(@PathVariable String dni) throws IOException {
        return new ResponseEntity<>(personService.findByDni(dni), HttpStatus.OK);
    }

    @PostMapping("/save/{dni}")
    public ResponseEntity<ResponseBase<PersonEntity>> savePerson(@PathVariable String dni) throws IOException {
        return new ResponseEntity<>(personService.registerPerson(dni), HttpStatus.CREATED);
    }

    @GetMapping()
    public ResponseEntity<ResponseBase<List<PersonEntity>>> findAllActive() {
        return new ResponseEntity<>(personService.findPersonActive(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseBase<PersonEntity>> findById(@PathVariable Long id) {
        return new ResponseEntity<>(personService.findById(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseBase<PersonEntity>> updatePerson(
            @PathVariable Long id,
            @RequestBody PersonUpdateRequest request) {
        return new ResponseEntity<>(personService.updatePerson(id, request), HttpStatus.OK);
    }

    @PutMapping("/{id}/status/{status}")
    public ResponseEntity<ResponseBase<PersonEntity>> updateStatus(
            @PathVariable Long id,
            @PathVariable String status) {
        return new ResponseEntity<>(personService.updateStatus(id, status), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseBase<PersonEntity>> deletePerson(@PathVariable Long id) {
        return new ResponseEntity<>(personService.deletePerson(id), HttpStatus.OK);
    }

}
