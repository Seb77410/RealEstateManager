package com.openclassrooms.realestatemanager.models.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.ArrayList;

@Entity
public class InterestPoint {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "interest_point_id", index = true)
    private long id;
    private ArrayList<String> category;

    // --- CONSTRUCTORS ---
    @Ignore
    public InterestPoint(){}

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
