package com.openclassrooms.realestatemanager.ui.activities.SearchProperty;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.openclassrooms.realestatemanager.R;

public class SearchPropertyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_property);
        this.configureToolbar();
    }

    private void configureToolbar(){
        Toolbar mToolbar = findViewById(R.id.search_property_activity_toolbar);
        setSupportActionBar(mToolbar);
        // Set back stack
        Drawable upArrow = ResourcesCompat.getDrawable(this.getResources(), R.drawable.ic_arrow_back_white_24dp, null);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(upArrow);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }
}
