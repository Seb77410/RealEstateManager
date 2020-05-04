package com.openclassrooms.realestatemanager.provider;

import android.content.ContentValues;

import com.openclassrooms.realestatemanager.models.database.HouseSeller;
import com.openclassrooms.realestatemanager.models.database.InterestPoint;
import com.openclassrooms.realestatemanager.models.database.Property;
import com.openclassrooms.realestatemanager.utils.Converters;

class ProviderContentValues {

    static Property fromPropertyContentValues(ContentValues values) {
        final Property property = new Property();
        if (values.containsKey("id")) property.setId(values.getAsLong("id"));
        if (values.containsKey("price")) property.setPrice(values.getAsInteger("price"));
        if (values.containsKey("area")) property.setArea(values.getAsInteger("area"));
        if (values.containsKey("rooms")) property.setRooms(values.getAsInteger("area"));
        if (values.containsKey("type")) property.setType(values.getAsString("type"));
        if (values.containsKey("description")) property.setDescription(values.getAsString("description"));
        if (values.containsKey("address")) property.setAddress(values.getAsString("address"));
        if (values.containsKey("sold")) property.setSold(values.getAsBoolean("sold"));
        if (values.containsKey("createDate")) property.setCreateDate(Converters.stringToCalendar(values.getAsString("createDate")));
        if (values.containsKey("sellDate")) property.setSellDate(Converters.stringToCalendar(values.getAsString("sellDate")));
        if (values.containsKey("interestPointId")) property.setInterestPointId(values.getAsLong("interestPointId"));
        if (values.containsKey("houseSellerId")) property.setHouseSellerId(values.getAsLong("houseSellerId"));
        return property;
    }


    static HouseSeller fromHouseSellerContentValues(ContentValues values){
        final HouseSeller houseSeller = new HouseSeller();
        if (values.containsKey("id")) houseSeller.setId(values.getAsLong("id"));
        if (values.containsKey("name")) houseSeller.setName(values.getAsString("name"));
        if (values.containsKey("email")) houseSeller.setEmail(values.getAsString("email"));
        return houseSeller;
    }

    static InterestPoint fromInterestPointContentValues(ContentValues values){
        final InterestPoint interestPoint = new InterestPoint();
        if (values.containsKey("id")) interestPoint.setId(values.getAsLong("id"));
        if (values.containsKey("category")) interestPoint.setCategory(Converters.stringToArray(values.getAsString("category")));
        return interestPoint;
    }


}
