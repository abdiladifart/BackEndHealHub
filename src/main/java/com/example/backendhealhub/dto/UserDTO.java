package com.example.backendhealhub.dto;
import lombok.Getter;
import lombok.Setter;

public class UserDTO {
    private Long id;

    @Getter @Setter
    private String email;

    @Getter @Setter
    private String name;

    @Getter @Setter
    private String password;


    // Constructors
    public UserDTO() {}

    public UserDTO(Long id, String email, String name, String password) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.password = password;
    }


}
