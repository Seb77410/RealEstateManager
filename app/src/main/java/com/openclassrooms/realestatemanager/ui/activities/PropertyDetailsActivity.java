package com.openclassrooms.realestatemanager.ui.activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.models.database.Property;
import com.openclassrooms.realestatemanager.ui.fragments.propertyDetailsFragment.PropertyDetailsFragment;

import java.lang.reflect.Type;

public class PropertyDetailsActivity extends AppCompatActivity {

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_details);

        toolbar = findViewById(R.id.property_details_activity_toolbar);

        this.configureToolbar();
        this.configureFragment();
    }

    //----------------------------------------------------------------------------------------------
    // Configure Toolbar
    //----------------------------------------------------------------------------------------------
    private void configureFragment() {
        Bundle bundle = getIntent().getExtras();
        String sProperty = null;
        Property property = null;
        if (bundle != null) {
            sProperty = bundle.getString("property");
            Type type = new TypeToken<Property>() {}.getType();
            property = new Gson().fromJson(sProperty, type);
        }

        getSupportFragmentManager().beginTransaction()
                    .add(R.id.property_details_activity_frame_layout, PropertyDetailsFragment.newInstance(property))
                    .commit();

    }

    //----------------------------------------------------------------------------------------------
    // Configure Toolbar
    //----------------------------------------------------------------------------------------------
    /**
     * This method configure activity toolbar
     */
    private void configureToolbar(){
        setSupportActionBar(toolbar);

        Drawable upArrow = ResourcesCompat.getDrawable(this.getResources(), R.drawable.ic_arrow_back_white_24dp, null);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(upArrow);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }
}
