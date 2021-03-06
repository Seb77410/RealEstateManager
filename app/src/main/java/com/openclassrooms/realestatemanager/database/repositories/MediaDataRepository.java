package com.openclassrooms.realestatemanager.database.repositories;

import androidx.lifecycle.LiveData;

import com.openclassrooms.realestatemanager.database.Dao.MediaDAO;
import com.openclassrooms.realestatemanager.models.database.Media;

import java.util.List;

public class MediaDataRepository {

    private final MediaDAO mediaDAO;

    public MediaDataRepository(MediaDAO mediaDAO){this.mediaDAO = mediaDAO;}

    // --- CREATE ---
    public long createMedia(Media media){return this.mediaDAO.createMedia(media);}

    // --- READ ---
    public LiveData<List<Media>> getMediaByPropertyId(long propertyId) { return this.mediaDAO.getMediaByPropertyId(propertyId);}
    // --- DELETE ---
    public void deleteMedia(long mediaId){this.mediaDAO.deleteMedia(mediaId);}
    // --- UPDATE ---
    public void updateMedia(Media media){this.mediaDAO.updateMedia(media);}


}
