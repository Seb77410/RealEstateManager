package com.openclassrooms.realestatemanager.activities.AddProperties;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.utils.PropertyTypeEnum;

public class AddPropertyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_property);

        Spinner mySpinner = (Spinner) findViewById(R.id.add_property_activity_spinner);

        mySpinner.setAdapter(new ArrayAdapter<PropertyTypeEnum>(this, android.R.layout.simple_spinner_item, PropertyTypeEnum.values()));
    }

}

