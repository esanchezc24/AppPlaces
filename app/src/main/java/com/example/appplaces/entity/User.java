package com.example.appplaces.entity;

public class User {
    private String name;
    private String email;
    private String password;
    private String latitud;
    private String longitud;

    public User() {
    }

    public User(String name, String email, String password, String latitud, String longitud) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }
}
