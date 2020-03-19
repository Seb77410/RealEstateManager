package com.openclassrooms.realestatemanager.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.EnumMap;

@Entity
public class HouseSeller {

    @PrimaryKey(autoGenerate = true)
    private long id;
    private String name;
    private String email;

    // --- CONSTRUCTORS ---
    public HouseSeller(String name, String email) {
        this.name = name;
        this.email = email;
    }

    // --- GETTER ---
    public long getId() {return id;}
    public String getName() {return name; }
    public String getEmail(){return email;}

    // --- SETTERS ---
    public void setId(long id) {this.id = id;}
    public void setName(String name) {this.name = name;}
    public void setEmail(String email){this.email = email;}
}
