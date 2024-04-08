package com.example.backendhealhub.dto;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class AuthResponseDTO {

    @Getter
    @Setter
    private String accessToken;
    private String tokenType = "Bearer ";

    public AuthResponseDTO(String accessToken) {
        this.accessToken = accessToken;
    }
    public AuthResponseDTO() {
        this.accessToken = accessToken;
    }


}