package com.openclassrooms.realestatemanager.ui.activities.SearchProperty;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.ui.fragments.propertiesLisFragment.PropertiesListFragment;
import com.openclassrooms.realestatemanager.utils.MyConstants;

public class SearchPropertyResultActivity extends AppCompatActivity {

    //----------------------------------------------------------------------------------------------
    // For data
    //----------------------------------------------------------------------------------------------
    String propertiesList;

    //----------------------------------------------------------------------------------------------
    // One Create
    //----------------------------------------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.configureToolbar();
        this.configureAndShowListFragment();
    }

    //----------------------------------------------------------------------------------------------
    // Configure Views
    //----------------------------------------------------------------------------------------------
    private void configureToolbar(){
        Toolbar mToolbar = findViewById(R.id.main_activity_toolbar);
        mToolbar.setTitle(getResources().getString(R.string.search_result));
        setSupportActionBar(mToolbar);
        // Set back stack
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void configureAndShowListFragment(){
        this.getArgs();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_activity_list_frame_layout, PropertiesListFragment.newInstance(propertiesList))
                .commit();
    }

    //----------------------------------------------------------------------------------------------
    // Get activity args
    //----------------------------------------------------------------------------------------------
    private void getArgs(){
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            propertiesList = bundle.getString(MyConstants.SEARCH_RESULT_ACTIVITY_PARAM);
        }
    }

}
