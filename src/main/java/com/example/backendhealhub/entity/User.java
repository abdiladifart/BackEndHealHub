package com.example.backendhealhub.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter
    private Long id;

    @Email
    @NotBlank
    @Size(max = 100)
    @Column(nullable = false, unique = true)
    @Getter @Setter
    private String email;

    @NotBlank
    @Size(min = 8, max = 100)
    @Column(nullable = false)
    @Getter @Setter
    private String password;

    @NotBlank
    @Size(max = 100)
    @Column(nullable = false)
    @Getter @Setter
    private String username;

    @Getter @Setter
    @Column(nullable = false)
    private String city;

    @Getter @Setter
    @Column(nullable = false)
    private String region;

    @Getter @Setter
    @Column(nullable = false)
    private String type;

    @Getter @Setter
    @Column(nullable = true)
    private String imageUrl;

    @Size(min = 10, max = 15)
    @Column(nullable = true)  // Make phoneNumber nullable
    @Getter @Setter
    private String phoneNumber;

    @Column(nullable = false)
    @Min(18) // Ensures the age is at least 18
    @Getter @Setter
    private Integer age;

}
