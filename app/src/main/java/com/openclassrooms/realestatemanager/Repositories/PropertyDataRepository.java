package com.openclassrooms.realestatemanager.Repositories;

import androidx.lifecycle.LiveData;

import com.openclassrooms.realestatemanager.Database.Dao.PropertyDAO;
import com.openclassrooms.realestatemanager.Models.Property;

import java.util.List;

public class PropertyDataRepository {

    private final PropertyDAO propertyDAO;

    public PropertyDataRepository(PropertyDAO propertyDAO){this.propertyDAO = propertyDAO;}

    // --- CREATE ---
    public void createProperty(Property property){this.propertyDAO.createProperty(property);}

    // --- READE ---
    public LiveData<Property> getProperty(long propertyId){return this.propertyDAO.getPropertyById(propertyId);}
    public LiveData<List<Property>> getProperties(long houseSellerId){return this.propertyDAO.getPropertiesByHouseSellerId(houseSellerId);}

    // --- UPDATE ---
    public void updateProperty(Property property){this.propertyDAO.updateProperty(property);}

    // --- DELETE ---
    public void deleteProperty(long propertyId){this.propertyDAO.deleteProperty(propertyId);}

}
