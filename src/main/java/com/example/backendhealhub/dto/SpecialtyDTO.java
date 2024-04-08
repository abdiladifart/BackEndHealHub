package com.example.backendhealhub.dto;
import lombok.Getter;
import lombok.Setter;

public class SpecialtyDTO {
    @Getter @Setter
    private Long id;
    @Getter @Setter
    private String name;

    public SpecialtyDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public SpecialtyDTO() {
    }
}
