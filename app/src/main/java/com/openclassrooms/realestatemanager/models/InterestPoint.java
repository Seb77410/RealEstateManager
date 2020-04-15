package com.openclassrooms.realestatemanager.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;

@Entity
public class InterestPoint {

    @PrimaryKey(autoGenerate = true)
    private long id;
    private String category;

    // --- CONSTRUCTORS ---
    public InterestPoint(String category) {
        this.category = category;
    }

    // --- GETTER ---
    public long getId() {return id;}
    public String getCategory() {return category;}

    // --- SETTERS ---
    public void setId(long id) {this.id = id;}
    public void setCategory(String category) {this.category = category;}
}
