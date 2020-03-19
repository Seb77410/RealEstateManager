package com.openclassrooms.realestatemanager.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.openclassrooms.realestatemanager.activities.AddHouseSeller.AddHouseSellerActivity;
import com.openclassrooms.realestatemanager.activities.AddProperties.AddPropertyActivity;
import com.openclassrooms.realestatemanager.R;

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

    //----------------------------------------------------------------------------------------------
    // onCreate
    //----------------------------------------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.main_activity_toolbar);
        this.configureToolbar();
    }

    //----------------------------------------------------------------------------------------------
    // Configure Toolbar
    //----------------------------------------------------------------------------------------------
    /**
     * This method configure activity toolbar
     */
    private void configureToolbar(){
        setSupportActionBar(toolbar);
    }

    //----------------------------------------------------------------------------------------------
    // Configure Add menu
    //----------------------------------------------------------------------------------------------
    /**We create a menu by inflate an .xml
     *
     * @param menu is a the new menu we will glue to our layout
     * @return true to show menu
     */
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

    /**This method will allow to manage the clicks on the menu
     * buttons.
     *
     * @param item is the selected button in the menu
     * @return true to able on click item
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Search button ==> Start SearchActivity
        switch (item.getItemId()) {
            case R.id.main_menu_add_property:
                startAddPropertyActivity();
                break;
            case R.id.main_menu_add_house_seller:
                startAddHouseSellerActivity();
                break;
        }
        return true;
    }

    private void startAddPropertyActivity(){
        Intent intent = new Intent(this, AddPropertyActivity.class);
        startActivity(intent);
    }

    private void startAddHouseSellerActivity(){
        Intent intent = new Intent(this, AddHouseSellerActivity.class);
        startActivity(intent);
    }

}
