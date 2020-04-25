package com.openclassrooms.realestatemanager.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;

@Entity
public class InterestPoint {

    @PrimaryKey(autoGenerate = true)
    private long id;
    private ArrayList<String> category;

    // --- CONSTRUCTORS ---
    public InterestPoint(ArrayList<String> category) {
        this.category = category;
    }

    // --- GETTER ---
    public long getId() {return id;}
    public ArrayList<String> getCategory() {return category;}

    // --- SETTERS ---
    public void setId(long id) {this.id = id;}
    public void setCategory(ArrayList<String> category) {this.category = category;}
}
