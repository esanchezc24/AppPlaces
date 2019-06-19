package com.example.appplaces.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Place {
    private String id;
    private User user;
    private String description;
    private List<String> arrayFotos;
    private Date createdAt;

    public Place() {
        this.arrayFotos = new ArrayList<>();
    }

    public Place(String id, User user, String description, Date createdAt) {
        this.id = id;
        this.user = user;
        this.description = description;
        this.arrayFotos = new ArrayList<>();
        this.createdAt = createdAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setArrayFotos(ArrayList arrayFotos) {
        this.arrayFotos = arrayFotos;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public void addFoto(String url){
        this.arrayFotos.add(url);
    }
}
