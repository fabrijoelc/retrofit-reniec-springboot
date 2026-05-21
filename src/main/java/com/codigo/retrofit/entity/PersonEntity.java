package com.codigo.retrofit.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@Table(name = "persons")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String names;
    private String lastName;
    private String motherLastName;
    private String fullName;
    private String typeDocument;

    @Column(unique = true)
    private String numberDocument;

    private String checkDigit;
    private String status;
    private Timestamp dateCreated;
    private Timestamp dateUpdated;
    private String userCreated;
    private String userUpdated;
}
