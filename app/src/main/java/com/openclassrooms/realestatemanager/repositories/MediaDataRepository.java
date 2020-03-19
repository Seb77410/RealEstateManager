package com.openclassrooms.realestatemanager.repositories;

import androidx.lifecycle.LiveData;

import com.openclassrooms.realestatemanager.database.Dao.MediaDAO;
import com.openclassrooms.realestatemanager.models.Media;

public class MediaDataRepository {

    private final MediaDAO mediaDAO;

    public MediaDataRepository(MediaDAO mediaDAO){this.mediaDAO = mediaDAO;}

    // --- CREATE ---
    public void createMedia(Media media){this.mediaDAO.createMedia(media);}

    // --- READ ---
    public LiveData<Media> getMedia(long mediaId){return this.mediaDAO.getMediaById(mediaId);}

    // --- DELETE ---
    public void deleteMedia(long mediaId){this.mediaDAO.deleteMedia(mediaId);}
}
