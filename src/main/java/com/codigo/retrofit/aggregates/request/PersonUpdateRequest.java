package com.codigo.retrofit.aggregates.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PersonUpdateRequest {
    private String names;
    private String lastName;
    private String motherLastName;
    private String fullName;
    private String typeDocument;
    private String numberDocument;
    private String checkDigit;
    private String status;
}
