package com.openclassrooms.realestatemanager.Models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class HouseSeller {

    @PrimaryKey
    private long id;
    private String name;
    private String firstName;

    // --- CONSTRUCTORS ---
    public HouseSeller() {}
    public HouseSeller(long id, String name, String firstName) {
        this.id = id;
        this.name = name;
        this.firstName = firstName;
    }

    // --- GETTER ---
    public long getId() {return id;}
    public String getName() {return name; }
    public String getFirstName() {return firstName;}

    // --- SETTERS ---
    public void setId(long id) {this.id = id;}
    public void setName(String name) {this.name = name;}
    public void setFirstName(String firstName) {this.firstName = firstName;}
}
