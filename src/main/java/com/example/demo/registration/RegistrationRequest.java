package com.example.demo.registration;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor

@EqualsAndHashCode
@ToString
public class RegistrationRequest {

    private  final String firstname;
    private  final String lastname;
    private  final String password;
    private  final String email;


}
