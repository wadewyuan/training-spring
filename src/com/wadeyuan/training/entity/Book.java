package com.wadeyuan.training.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Book {
    private int id;
    private String name;
    private String author;
    private String picture;
    private String description;
    private int ownerId;
    private int currentUserId;
    private String ownerName;
    private String currentUserName;

    public Book() {
        this.id=-1;
        this.picture = "";
        this.name = "";
        this.author = "";
        this.description = "";
        this.ownerId = -1;
        this.currentUserId = -1;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
    public String getPicture() {
        return picture;
    }
    public void setPicture(String picture) {
        this.picture = picture;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public int getOwnerId() {
        return ownerId;
    }
    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }
    public int getCurrentUserId() {
        return currentUserId;
    }
    public void setCurrentUserId(int currentUserId) {
        this.currentUserId = currentUserId;
    }

    @JsonIgnore
    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    @JsonIgnore
    public String getCurrentUserName() {
        return currentUserName;
    }

    public void setCurrentUserName(String currentUserName) {
        this.currentUserName = currentUserName;
    }
}
