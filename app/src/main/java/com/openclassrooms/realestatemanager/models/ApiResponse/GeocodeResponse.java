package com.openclassrooms.realestatemanager.models.ApiResponse;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GeocodeResponse {

    @SerializedName("results")
    @Expose
    private List<Result> results = null;
    @SerializedName("status")
    @Expose
    private String status;

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public class AddressComponent {

        @SerializedName("long_name")
        @Expose
        private String longName;
        @SerializedName("short_name")
        @Expose
        private String shortName;
        @SerializedName("types")
        @Expose
        private List<String> types = null;

        public String getLongName() {
            return longName;
        }

        public void setLongName(String longName) {
            this.longName = longName;
        }

        public String getShortName() {
            return shortName;
        }

        public void setShortName(String shortName) {
            this.shortName = shortName;
        }

        public List<String> getTypes() {
            return types;
        }

        public void setTypes(List<String> types) {
            this.types = types;
        }

    }

    public class Example {

        @SerializedName("results")
        @Expose
        private List<Result> results = null;
        @SerializedName("status")
        @Expose
        private String status;

        public List<Result> getResults() {
            return results;
        }

        public void setResults(List<Result> results) {
            this.results = results;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

    }

    public class Geometry {

        @SerializedName("location")
        @Expose
        private Location location;
        @SerializedName("location_type")
        @Expose
        private String locationType;


        public Location getLocation() {
            return location;
        }

        public void setLocation(Location location) {
            this.location = location;
        }

        public String getLocationType() {
            return locationType;
        }

        public void setLocationType(String locationType) {
            this.locationType = locationType;
        }


    }

    public class Location {

        @SerializedName("lat")
        @Expose
        private Double lat;
        @SerializedName("lng")
        @Expose
        private Double lng;

        public Double getLat() {
            return lat;
        }

        public void setLat(Double lat) {
            this.lat = lat;
        }

        public Double getLng() {
            return lng;
        }

        public void setLng(Double lng) {
            this.lng = lng;
        }

    }

    public class Northeast {

        @SerializedName("lat")
        @Expose
        private Double lat;
        @SerializedName("lng")
        @Expose
        private Double lng;

        public Double getLat() {
            return lat;
        }

        public void setLat(Double lat) {
            this.lat = lat;
        }

        public Double getLng() {
            return lng;
        }

        public void setLng(Double lng) {
            this.lng = lng;
        }

    }

    public class PlusCode {

        @SerializedName("compound_code")
        @Expose
        private String compoundCode;
        @SerializedName("global_code")
        @Expose
        private String globalCode;

        public String getCompoundCode() {
            return compoundCode;
        }

        public void setCompoundCode(String compoundCode) {
            this.compoundCode = compoundCode;
        }

        public String getGlobalCode() {
            return globalCode;
        }

        public void setGlobalCode(String globalCode) {
            this.globalCode = globalCode;
        }

    }

    public class Result {

        @SerializedName("address_components")
        @Expose
        private List<AddressComponent> addressComponents = null;
        @SerializedName("formatted_address")
        @Expose
        private String formattedAddress;
        @SerializedName("geometry")
        @Expose
        private Geometry geometry;
        @SerializedName("place_id")
        @Expose
        private String placeId;
        @SerializedName("plus_code")
        @Expose
        private PlusCode plusCode;
        @SerializedName("types")
        @Expose
        private List<String> types = null;

        public List<AddressComponent> getAddressComponents() {
            return addressComponents;
        }

        public void setAddressComponents(List<AddressComponent> addressComponents) {
            this.addressComponents = addressComponents;
        }

        public String getFormattedAddress() {
            return formattedAddress;
        }

        public void setFormattedAddress(String formattedAddress) {
            this.formattedAddress = formattedAddress;
        }

        public Geometry getGeometry() {
            return geometry;
        }

        public void setGeometry(Geometry geometry) {
            this.geometry = geometry;
        }

        public String getPlaceId() {
            return placeId;
        }

        public void setPlaceId(String placeId) {
            this.placeId = placeId;
        }

        public PlusCode getPlusCode() {
            return plusCode;
        }

        public void setPlusCode(PlusCode plusCode) {
            this.plusCode = plusCode;
        }

        public List<String> getTypes() {
            return types;
        }

        public void setTypes(List<String> types) {
            this.types = types;
        }

    }



}
