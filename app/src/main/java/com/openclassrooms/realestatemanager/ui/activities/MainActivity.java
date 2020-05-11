package com.openclassrooms.realestatemanager.ui.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.openclassrooms.realestatemanager.ui.activities.AddHouseSeller.AddHouseSellerActivity;
import com.openclassrooms.realestatemanager.ui.activities.AddProperties.AddPropertyActivity;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.ui.activities.LoanCalculator.LoanCalculatorActivity;
import com.openclassrooms.realestatemanager.ui.activities.Map.MapActivity;
import com.openclassrooms.realestatemanager.ui.activities.SearchProperty.SearchPropertyActivity;
import com.openclassrooms.realestatemanager.ui.fragments.propertiesLisFragment.PropertiesListFragment;
import com.openclassrooms.realestatemanager.utils.Utils;

public class MainActivity extends AppCompatActivity {

// FOR 1ST BUG RESOLVE
    // A résolue un bug en modifiant la ligne suivante
    //this.textViewMain = findViewById(R.id.activity_second_activity_text_view_main);
    // En cette ligne :
    //this.textViewMain = findViewById(R.id.activity_main_activity_text_view_main);

// FOR 2ND BUG RESOLVE
    //private void configureTextViewQuantity(){
    //    int quantity = Utils.convertDollarToEuro(100);
    //    this.textViewQuantity.setTextSize(20);
    //A résolue un bug en modifiant la ligne suivante
    // this.textViewQuantity.setText(quantity);
    // En cette ligne :
    //    this.textViewQuantity.setText(String.valueOf(quantity));
    //}


    //----------------------------------------------------------------------------------------------
    // For data
    //----------------------------------------------------------------------------------------------
    Toolbar toolbar;
    Menu menu;
    FrameLayout listFrameLayout;
    FrameLayout detailsFrameLayout;

    //----------------------------------------------------------------------------------------------
    // onCreate
    //----------------------------------------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.main_activity_toolbar);
        listFrameLayout = findViewById(R.id.main_activity_list_frame_layout);
        detailsFrameLayout = findViewById(R.id.main_activity_details_frame_layout);
        this.configureToolbar();
        this.configureAndShowListFragment();
    }

    //----------------------------------------------------------------------------------------------
    // Configure Toolbar
    //----------------------------------------------------------------------------------------------
    private void configureToolbar(){
        setSupportActionBar(toolbar);
    }

    //----------------------------------------------------------------------------------------------
    // Configure menu
    //----------------------------------------------------------------------------------------------
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        inflateAddMenu(menu);
        this.menu = menu;
        return true;
    }

    private void inflateAddMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu_add, menu);
        Drawable drawable = ContextCompat.getDrawable(getApplicationContext(),R.drawable.ic_add_white_24dp);
        toolbar.setOverflowIcon(drawable);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.main_menu_add_property:
                Intent intent = new Intent(this, AddPropertyActivity.class);
                startActivity(intent);
                break;
            case R.id.main_menu_add_house_seller:
                Intent intent1 = new Intent(this, AddHouseSellerActivity.class);
                startActivity(intent1);
                break;
            case R.id.main_menu_map:
                ConnectivityManager connectivityManager = (ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
                if(Utils.isInternetAvailableNew(connectivityManager)){
                    Intent intent2 = new Intent(this, MapActivity.class);
                    startActivity(intent2);
                }else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.myDialog));
                    builder.setMessage(getResources().getString(R.string.alert_no_network_connection_content))
                            .setTitle(getResources().getString(R.string.alert_no_network_connection_title))
                            .show();
                }
                break;
            case R.id.main_menu_search:
                Intent intent3 = new Intent(this, SearchPropertyActivity.class);
                startActivity(intent3);
                break;
            case R.id.main_menu_loan_calculator:
                Intent intent4 = new Intent(this, LoanCalculatorActivity.class);
                startActivity(intent4);
                break;
        }
        return true;
    }

    //----------------------------------------------------------------------------------------------
    // Configure properties list fragment
    //----------------------------------------------------------------------------------------------
    private void configureAndShowListFragment(){
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_activity_list_frame_layout, new PropertiesListFragment())
                .commit();
    }



}
