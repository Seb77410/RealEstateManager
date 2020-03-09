package com.openclassrooms.realestatemanager;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

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


    private TextView textViewMain;
    private TextView textViewQuantity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

}
