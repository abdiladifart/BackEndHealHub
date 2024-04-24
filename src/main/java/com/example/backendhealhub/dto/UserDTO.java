package com.example.backendhealhub.dto;
import lombok.Getter;
import lombok.Setter;

public class UserDTO {

    @Getter @Setter
    private Long id;

    @Getter @Setter
    private String email;

    @Getter @Setter
    private String username;

    @Getter @Setter
    private String password;

    @Getter @Setter
    private String city;

    @Getter @Setter
    private String region;

    @Getter @Setter
    private String role;

    @Getter @Setter
    private Long specialtyId;

    // Constructors
    public UserDTO() {}

    public UserDTO(Long id, String email, String Username, String password,String city, String region) {
        this.id = id;
        this.email = email;
        this.username = Username;
        this.password = password;
        this.city = city;
        this.region = region;
    }
    public UserDTO(Long id, String email, String username, String password, String city, String region, String role, Long specialtyId) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.password = password;
        this.city = city;
        this.region = region;
        this.role = role;
        this.specialtyId = specialtyId; // Initialize the new specialtyId attribute
    }

//    public UserDTO(Long id, String email, String username, String city, String region, Object o) {
//    }
//    public UserDTO(Long id, String email, String username, Object o) {
//        this.id = id;
//        this.email = email;
//        this.Username = Username;
//    }
}
