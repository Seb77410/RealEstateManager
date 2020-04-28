package com.openclassrooms.realestatemanager.database.repositories;

import androidx.lifecycle.LiveData;

import com.openclassrooms.realestatemanager.database.Dao.PropertyDAO;
import com.openclassrooms.realestatemanager.models.database.Property;

import java.util.List;

public class PropertyDataRepository {

    private final PropertyDAO propertyDAO;

    public PropertyDataRepository(PropertyDAO propertyDAO){this.propertyDAO = propertyDAO;}

    // --- CREATE ---
    public long createProperty(Property property){return this.propertyDAO.createProperty(property);}

    // --- READE ---
    public LiveData<Property> getProperty(long propertyId){return this.propertyDAO.getPropertyById(propertyId);}
    public LiveData<List<Property>> getProperties(long houseSellerId){return this.propertyDAO.getPropertiesByHouseSellerId(houseSellerId);}
    public LiveData<Property> getLastPropertySaved(){return this.propertyDAO.getLastPropertySaved();}
    public LiveData<List<Property>> getAllProperties() {return this.propertyDAO.getAllProperties();}


    // --- UPDATE ---
    public void updateProperty(Property property){this.propertyDAO.updateProperty(property);}

    // --- DELETE ---
    public void deleteProperty(long propertyId){this.propertyDAO.deleteProperty(propertyId);}


}
