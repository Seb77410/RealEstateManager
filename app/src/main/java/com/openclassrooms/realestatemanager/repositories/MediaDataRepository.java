package com.openclassrooms.realestatemanager.repositories;

import androidx.lifecycle.LiveData;

import com.openclassrooms.realestatemanager.database.Dao.MediaDAO;
import com.openclassrooms.realestatemanager.models.Media;

import java.util.List;

public class MediaDataRepository {

    private final MediaDAO mediaDAO;

    public MediaDataRepository(MediaDAO mediaDAO){this.mediaDAO = mediaDAO;}

    // --- CREATE ---
    public long createMedia(Media media){return this.mediaDAO.createMedia(media);}

    // --- READ ---
    public LiveData<Media> getMedia(long mediaId){return this.mediaDAO.getMediaById(mediaId);}
    public LiveData<List<Media>> getMediaByPropertyId(long propertyId) { return this.mediaDAO.getMediaByPropertyId(propertyId);}
    // --- DELETE ---
    public void deleteMedia(long mediaId){this.mediaDAO.deleteMedia(mediaId);}


}
