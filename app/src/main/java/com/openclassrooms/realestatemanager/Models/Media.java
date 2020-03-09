package com.openclassrooms.realestatemanager.Models;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = @ForeignKey(entity = Property.class,
                    parentColumns = "id",
                    childColumns = "propertyId"))
public class Media {

    @PrimaryKey
    private long id;
    private String comment;
    private String mediaUrl;
    private String propertyId;

    // --- CONSTRUCTORS ---
    public Media() {}
    public Media(long id, String comment, String mediaUrl, String propertyId) {
        this.id = id;
        this.comment = comment;
        this.mediaUrl = mediaUrl;
        this.propertyId = propertyId;
    }

    // --- GETTER ---
    public long getId() {return id;}
    public String getComment() {return comment;}
    public String getMediaUrl() {return mediaUrl;}
    public String getPropertyId() {return propertyId;}

    // --- SETTERS ---
    public void setId(long id) {this.id = id;}
    public void setComment(String comment) {this.comment = comment;}
    public void setMediaUrl(String mediaUrl) {this.mediaUrl = mediaUrl;}
    public void setPropertyId(String propertyId) {this.propertyId = propertyId;}
}
