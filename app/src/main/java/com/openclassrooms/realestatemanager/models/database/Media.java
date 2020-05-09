package com.openclassrooms.realestatemanager.models.database;

import android.net.Uri;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = @ForeignKey(entity = Property.class,
                    parentColumns = "property_id",
                    childColumns = "media_property_id"))
public class Media {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "media_id")
    private long id;
    private String comment;
    private Uri mediaUri;
    @ColumnInfo(name = "media_property_id", index = true)
    private long propertyId;

    // --- CONSTRUCTORS ---
    public Media(String comment, Uri mediaUri, long propertyId) {
        this.comment = comment;
        this.mediaUri = mediaUri;
        this.propertyId = propertyId;
    }

    // --- GETTER ---
    public long getId() {return id;}
    public String getComment() {return comment;}
    public Uri getMediaUri() {return mediaUri;}
    public long getPropertyId() {return propertyId;}

    // --- SETTERS ---
    public void setId(long id) {this.id = id;}
    public void setComment(String comment) {this.comment = comment;}
    public void setMediaUri(Uri mediaUri) {this.mediaUri = mediaUri;}
    public void setPropertyId(long propertyId) {this.propertyId = propertyId;}
}
