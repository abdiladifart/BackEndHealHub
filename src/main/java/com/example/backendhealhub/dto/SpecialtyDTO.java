package com.example.backendhealhub.dto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class SpecialtyDTO {
    @Getter @Setter
    private Long id;
    @Getter @Setter
    private String name;

    @Getter @Setter
    private List<Long> clinicIds;

    public SpecialtyDTO(Long id, String name, List<Long> clinicIds) {
        this.id = id;
        this.name = name;
        this.clinicIds = clinicIds;
    }

    public SpecialtyDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public SpecialtyDTO() {
    }
}
