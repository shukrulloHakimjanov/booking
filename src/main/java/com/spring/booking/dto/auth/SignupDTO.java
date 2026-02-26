package com.spring.booking.dto.auth;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SignupDTO {
    @Email
    private String email;
    @NotNull
    private String firstname;
    private String lastname;
    @Size(min =8)
    @NotNull
    private String password;
    @NotNull
    @Pattern(regexp = "[+](998)[0-9]{9}$")
    private String phoneNumber;


}
