package com.openclassrooms.realestatemanager.utils;

import androidx.annotation.NonNull;

public enum PropertyTypeEnum {

        // --- ENUM ---
        LOFT("Loft"),
        DUPLEX("Duplex"),
        HOME("Home"),
        STUDIO("Studio"),
        APARTMENT("Apartment");

        // --- FOR DATA ---
        private String type;

        // --- CONSTRUCTOR ---
        PropertyTypeEnum(String type){
            this.type = type;
        }

        @NonNull
        public String toString(){
            return type;
        }

}
