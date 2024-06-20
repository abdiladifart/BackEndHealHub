package com.example.backendhealhub.dto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class ClinicDTO {
    @Getter @Setter
    private Long id;

    @Getter @Setter
    private String name;

    @Getter @Setter
    private String city; // Assuming there's an address field

    @Getter @Setter
    private String region;

    @Getter @Setter
    private double latitude;

    @Getter @Setter
    private double longitude;

    private List<Long> specialtyIds;

    public ClinicDTO(Long id, String name, String city,String region) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.region = region;
    }

    public ClinicDTO(Long id, String name, String city, String region, double latitude, double longitude) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.region = region;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public ClinicDTO(Long id, String name, String city, String region, double latitude, double longitude, List<Long> specialtyIds) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.region = region;
        this.latitude = latitude;
        this.longitude = longitude;
        this.specialtyIds = specialtyIds;
    }

    public ClinicDTO() {
    }
}
