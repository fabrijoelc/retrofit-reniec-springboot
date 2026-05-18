package com.codigo.retrofit.aggregates.response;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReniecResponse {
    @SerializedName("first_name")
    private String nombres;

    @SerializedName("first_last_name")
    private String apellidoPaterno;

    @SerializedName("second_last_name")
    private String apellidoMaterno;

    @SerializedName("full_name")
    private String nombreCompleto;

    @SerializedName("document_number")
    private String numeroDocumento;
}
