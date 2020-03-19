package com.openclassrooms.realestatemanager.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class InterestPoint {

    @PrimaryKey
    private long id;
    private String category;

    // --- CONSTRUCTORS ---
    public InterestPoint(long id, String category) {
        this.id = id;
        this.category = category;
    }

    // --- GETTER ---
    public long getId() {return id;}
    public String getCategory() {return category;}

    // --- SETTERS ---
    public void setId(long id) {this.id = id;}
    public void setCategory(String category) {this.category = category;}
}
