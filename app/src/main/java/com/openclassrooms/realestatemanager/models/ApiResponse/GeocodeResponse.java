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

    public String getStatus() {
        return status;
    }


    public class Geometry {

        @SerializedName("location")
        @Expose
        private Location location;

        public Location getLocation() {
            return location;
        }
    }

    public static class Location {

        @SerializedName("lat")
        @Expose
        private Double lat;
        @SerializedName("lng")
        @Expose
        private Double lng;

        public Double getLat() {
            return lat;
        }

        public Double getLng() {
            return lng;
        }

    }

    public class Result {
        @SerializedName("geometry")
        @Expose
        private Geometry geometry;


        public Geometry getGeometry() {
            return geometry;
        }

    }



}
