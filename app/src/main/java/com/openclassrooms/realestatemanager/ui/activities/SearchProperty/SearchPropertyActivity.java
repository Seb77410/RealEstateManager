package com.openclassrooms.realestatemanager.ui.activities.SearchProperty;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.res.ResourcesCompat;
import androidx.sqlite.db.SimpleSQLiteQuery;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.database.AppDatabase;
import com.openclassrooms.realestatemanager.models.database.Property;
import com.openclassrooms.realestatemanager.utils.Converters;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

public class SearchPropertyActivity extends AppCompatActivity {

    SwitchCompat propertySold;
    TextView createDate;
    LinearLayout sellDateContainer;
    TextView sellDate;
    TextInputLayout minPrice;
    TextInputLayout maxPrice;
    TextInputLayout location;
    MaterialCheckBox schoolCheckbox;
    MaterialCheckBox gardenCheckBox;
    MaterialCheckBox businessCheckbox;
    MaterialCheckBox transportCheckbox;
    TextInputLayout minMedia;
    ExtendedFloatingActionButton searchButton;
    Calendar calendarCreateDate = Calendar.getInstance();
    Calendar calendarSellDate= Calendar.getInstance();
    String propertySQLiteRequest;
    String interestPointUnderRequest;
    boolean atLeastOneCategoryIsCheck = false;
    TextView nearby;
    ConstraintLayout checkboxLayout;
    String mediaUnderRequest;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_property);

        propertySold =  findViewById(R.id.search_property_activity_property_sold);
        createDate = findViewById(R.id.search_property_activity_create_date_content);
        sellDateContainer = findViewById(R.id.search_property_activity_sell_date_container);
        sellDate = findViewById(R.id.search_property_activity_sell_date_content);
        minPrice = findViewById(R.id.search_property_activity_min_price);
        maxPrice = findViewById(R.id.search_property_activity_max_price);
        location = findViewById(R.id.search_property_activity_location);
        schoolCheckbox = findViewById(R.id.search_property_activity_school_checkbox);
        gardenCheckBox = findViewById(R.id.search_property_activity_garden_checkbox);
        businessCheckbox = findViewById(R.id.search_property_activity_business_checkbox);
        transportCheckbox = findViewById(R.id.search_property_activity_transport_checkbox);
        minMedia = findViewById(R.id.search_property_activity_min_media);
        searchButton = findViewById(R.id.search_property_activity_button);
        nearby = findViewById(R.id.search_property_activity_nearby_textView);
        checkboxLayout = findViewById(R.id.search_property_activity_nearby_content_layout);

        this.configureToolbar();
        this.configurePropertySoldSwitch();
        this.configureCreateDateView();
        this.configureSellDateView();
        this.configureNearby();
        this.configureSearchButton();
    }


    //----------------------------------------------------------------------------------------------
    // Configure Views
    //----------------------------------------------------------------------------------------------

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

    private void configurePropertySoldSwitch(){
        propertySold.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked){
                sellDateContainer.setVisibility(View.VISIBLE);
            }
            else{ sellDateContainer.setVisibility(View.GONE); }
        });
    }

    private void configureCreateDateView(){
        showDatePicker(createDate, calendarCreateDate);
    }

    private void configureSellDateView(){
        sellDateContainer.setVisibility(View.GONE);
        showDatePicker(sellDate, calendarSellDate);
    }

    private void showDatePicker(TextView textView, Calendar calendar) {

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatePickerDialog datePickerDialog = new DatePickerDialog(SearchPropertyActivity.this, R.style.TimePickerTheme, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        textView.setText(String.format("%s %s %s %s %s", Converters.convertDateIntToString(dayOfMonth), "/", Converters.convertDateIntToString(month+1), "/", year));
                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        calendar.set(Calendar.MONTH, month);
                        calendar.set(Calendar.YEAR, year);
                    }
                }, Calendar.getInstance().get(Calendar.YEAR),
                        Calendar.getInstance().get(Calendar.MONTH),
                        Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });
    }

    private void configureNearby(){
        nearby.setOnClickListener(v -> {
            if(checkboxLayout.getVisibility() == View.GONE){
                checkboxLayout.setVisibility(View.VISIBLE); }
            else { checkboxLayout.setVisibility(View.GONE); }
        });

    }

    private void configureSearchButton(){
        searchButton.setOnClickListener(v -> {
            this.getDataForPropertySearch();
            this.getDataForNearbySearch();
            this.getDataForMediaSearch();
            this.executePropertySearch();
        });
    }

    //----------------------------------------------------------------------------------------------
    // Get data and execute search request
    //----------------------------------------------------------------------------------------------

    private void getDataForPropertySearch(){
        propertySQLiteRequest =  "SELECT * FROM Property WHERE sold = ";

        if(propertySold.isChecked()) {
            propertySQLiteRequest = propertySQLiteRequest+"1";
            if(!sellDate.getText().equals("Select date")){
                propertySQLiteRequest = propertySQLiteRequest + " AND sellDate <= " +
                        calendarSellDate.get(Calendar.YEAR) +
                        Converters.convertDateIntToString(calendarSellDate.get(Calendar.MONTH)+1) +
                        Converters.convertDateIntToString(calendarSellDate.get(Calendar.DAY_OF_MONTH));
            }
        }else {propertySQLiteRequest = propertySQLiteRequest+"0";}

        if (!createDate.getText().equals("Select date")){
            propertySQLiteRequest = propertySQLiteRequest + " AND createDate >= " +
                    calendarCreateDate.get(Calendar.YEAR) +
                    Converters.convertDateIntToString(calendarCreateDate.get(Calendar.MONTH)+1) +
                    Converters.convertDateIntToString(calendarCreateDate.get(Calendar.DAY_OF_MONTH));
        }

        if(!Objects.requireNonNull(minPrice.getEditText()).getText().toString().equals("")){
            propertySQLiteRequest = propertySQLiteRequest + " AND price >= " + minPrice.getEditText().getText().toString(); }
        if(!Objects.requireNonNull(maxPrice.getEditText()).getText().toString().equals("")){
            propertySQLiteRequest = propertySQLiteRequest + " AND price <= " + maxPrice.getEditText().getText().toString(); }
        if(!Objects.requireNonNull(location.getEditText()).getText().toString().equals("")){
            propertySQLiteRequest = propertySQLiteRequest + " AND address LIKE '%" + location.getEditText().getText().toString()+"%'"; }
        Log.e("SEARCH ACTIVITY", "SQLite search request = " + propertySQLiteRequest);

    }

    private void getDataForNearbySearch(){

        if (checkboxLayout.getVisibility() == View.VISIBLE) {
            interestPointUnderRequest = " AND property_interest_point_id IN (SELECT InterestPoint.interest_point_id FROM InterestPoint WHERE InterestPoint.category LIKE ";
            checkForCheckbox(schoolCheckbox);
            checkForCheckbox(businessCheckbox);
            checkForCheckbox(transportCheckbox);
            checkForCheckbox(gardenCheckBox);
            if (!interestPointUnderRequest.contains("%")) {
                interestPointUnderRequest = interestPointUnderRequest + "'%%' )";
            } else {
                interestPointUnderRequest = interestPointUnderRequest + ")";
            }
            Log.e("SEARCH ACTIVITY", "interest Point under request = " + interestPointUnderRequest);

            if(atLeastOneCategoryIsCheck) {
                propertySQLiteRequest = propertySQLiteRequest + interestPointUnderRequest;
            }
            Log.e("SEARCH ACTIVITY", "SQLite search request = " + propertySQLiteRequest);
        }

    }

    private void getDataForMediaSearch(){
        if(!Objects.requireNonNull(minMedia.getEditText()).getText().toString().equals("")){
            String sMinMedia = minMedia.getEditText().getText().toString();
            mediaUnderRequest = " AND (SELECT count(*)  FROM Media WHERE Media.media_property_id = Property.property_id) >=" + sMinMedia;
            propertySQLiteRequest = propertySQLiteRequest + mediaUnderRequest;
            Log.e("SEARCH ACTIVITY", "SQLite search request = " + propertySQLiteRequest);
        }

    }

    private void checkForCheckbox(CheckBox checkBox){
        if(checkBox.isChecked()){
            atLeastOneCategoryIsCheck = true;
            if(interestPointUnderRequest.contains("%")){
                interestPointUnderRequest = interestPointUnderRequest + " AND category LIKE '%"+checkBox.getText().toString()+"%'";
            }
            else {
                interestPointUnderRequest = interestPointUnderRequest + "'%"+checkBox.getText().toString()+"%'";}
        }
    }

    private void executePropertySearch(){
        // Usage of RawDao
        SimpleSQLiteQuery query = new SimpleSQLiteQuery(propertySQLiteRequest);
        AppDatabase.getInstance(this).propertyDAO().searchProperty(query).observe(this, properties -> {
            Log.e("SEARCH ACTIVITY", "resultList size = " + properties.size());
            for (Property property : properties) {
                Log.e("SEARCH ACTIVITY", "property id = " + property.getId());
            }

            if(properties.size()>0){ this.startSearchResultActivity(properties);}
            else {
                AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.myDialog));
                builder.setMessage("No result found, retry wit other parameters")
                        .setTitle("No result")
                        .show();
            }
        });
    }

    //----------------------------------------------------------------------------------------------
    // Start Result activity
    //----------------------------------------------------------------------------------------------

    private void startSearchResultActivity(List<Property> properties){
        Intent intent = new Intent(this, SearchPropertyResultActivity.class);
        intent.putExtra("properties list", Converters.convertPropertiesListToString(properties));
        startActivity(intent);
    }
}
