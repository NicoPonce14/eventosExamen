package com.nicolas.eventosexamen.models;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class Login {

	@Email(message = "El correo ingresado no es valido")
    @NotBlank(message = "Por favor, no olvides ingresar un correo electronico")
    private String email;
   
    @NotBlank(message = "Por favor ingresa tu password")
    @Size(min=8,max=20,message = "Password debe tener entre 8 a 20 caracteres")
    private String password;

    public Login(){}


    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
