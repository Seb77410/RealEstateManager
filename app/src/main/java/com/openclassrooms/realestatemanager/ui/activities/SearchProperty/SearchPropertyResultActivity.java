package com.openclassrooms.realestatemanager.ui.activities.SearchProperty;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.ui.fragments.propertiesLisFragment.PropertiesListFragment;

public class SearchPropertyResultActivity extends AppCompatActivity {

    String propertiesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.configureToolbar();
        this.configureAndShowListFragment();
    }

    private void configureToolbar(){
        Toolbar mToolbar = findViewById(R.id.main_activity_toolbar);
        mToolbar.setTitle("Search property result");
        setSupportActionBar(mToolbar);
        // Set back stack
        Drawable upArrow = ResourcesCompat.getDrawable(this.getResources(), R.drawable.ic_arrow_back_white_24dp, null);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(upArrow);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void configureAndShowListFragment(){
        this.getArgs();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_activity_list_frame_layout, PropertiesListFragment.newInstance(propertiesList))
                .commit();
    }

    private void getArgs(){
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            propertiesList = bundle.getString("properties list");
            Log.e("Search Result Activity", propertiesList);
        }
    }

}
