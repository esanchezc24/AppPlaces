package com.example.appplaces.Data.Entity;

import java.util.ArrayList;
import java.util.Date;

public class Place {
    private String id;
    private User user;
    private String description;
    private ArrayList arrayFotos;
    private Date createdAt;

    public Place() {
    }

    public Place(String id, User user, String description, ArrayList arrayFotos, Date createdAt) {
        this.id = id;
        this.user = user;
        this.description = description;
        this.arrayFotos = arrayFotos;
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

    public ArrayList getArrayFotos() {
        return arrayFotos;
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
}
