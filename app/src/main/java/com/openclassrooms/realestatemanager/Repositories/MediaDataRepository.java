package com.openclassrooms.realestatemanager.Repositories;

import androidx.lifecycle.LiveData;

import com.openclassrooms.realestatemanager.Database.Dao.MediaDAO;
import com.openclassrooms.realestatemanager.Models.Media;

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
