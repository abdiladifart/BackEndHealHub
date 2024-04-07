package com.example.backendhealhub.dto;
import lombok.Getter;
import lombok.Setter;

public class UserDTO {
    private Long id;

    @Getter @Setter
    private String email;

    @Getter @Setter
    private String Username;

    @Getter @Setter
    private String password;


    // Constructors
    public UserDTO() {}

    public UserDTO(Long id, String email, String Username, String password) {
        this.id = id;
        this.email = email;
        this.Username = Username;
        this.password = password;
    }


}
