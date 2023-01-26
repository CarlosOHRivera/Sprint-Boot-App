package com.oh.springbootApp.Exception;

public class UsernameOrIdNotFound extends Exception{
    public UsernameOrIdNotFound() {
        super("Usuario o ID no encontraado");
    }

    public UsernameOrIdNotFound(String message) {
        super(message);
    }
}
