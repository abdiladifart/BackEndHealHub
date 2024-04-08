package com.example.backendhealhub.dto;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;

public class UserDTO {
    private Long id;

    @Getter @Setter
    private String email;

    @Getter @Setter
    private String Username;

    @Getter @Setter
    private String password;

    @Getter @Setter
    private String city;

    @Getter @Setter
    private String region;

    // Constructors
    public UserDTO() {}

    public UserDTO(Long id, String email, String Username, String password,String city, String region) {
        this.id = id;
        this.email = email;
        this.Username = Username;
        this.password = password;
        this.city = city;
        this.region = region;
    }


//    public UserDTO(Long id, String email, String username, Object o) {
//        this.id = id;
//        this.email = email;
//        this.Username = Username;
//    }
}
