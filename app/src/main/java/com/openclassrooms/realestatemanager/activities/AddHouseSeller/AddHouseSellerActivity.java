package com.openclassrooms.realestatemanager.activities.AddHouseSeller;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;
import androidx.lifecycle.ViewModelProviders;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.injection.Injection;
import com.openclassrooms.realestatemanager.models.HouseSeller;

import java.util.Objects;

public class AddHouseSellerActivity extends AppCompatActivity {

    // --- FOR DATA ---
    Toolbar mToolbar;
    TextInputLayout nameTextInputLayout;
    TextInputLayout emailTextInputLayout;
    String name;
    String email;
    Boolean nameIsWrite;
    Boolean emailIsWrite;
    Boolean emailIsCorrect;
    private AddHouseSellerViewModel viewModel;

    //----------------------------------------------------------------------------------------------
    // OnCreate
    //----------------------------------------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_house_seller);
        configureBackStack();
        configureViewModel();
    }


    //----------------------------------------------------------------------------------------------
    //  Back Stack
    //----------------------------------------------------------------------------------------------
    private void configureBackStack(){
        mToolbar = findViewById(R.id.add_house_seller_activity_toolbar);
        setSupportActionBar(mToolbar);
        // Set back stack
        Drawable upArrow = ResourcesCompat.getDrawable(this.getResources(), R.drawable.ic_arrow_back_white_24dp, null);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(upArrow);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    //----------------------------------------------------------------------------------------------
    // Save Button
    //----------------------------------------------------------------------------------------------
    public void saveHouseSeller(View view) {
        getNameFromEditText();
        getEmailFromEditText();
        nameIsWrite = nameIsCorrectlyWrite();
        emailIsWrite =  emailIsCorrectlyWrite();
        if (nameIsWrite && emailIsWrite){
            // Check if input email have correct format
            emailIsCorrect = !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
            if(emailIsCorrect) {
                // TODO : save house seller
                createHouseSeller();
                createToastAfterHouseSellerCreating();
            }
            else {
                Toast.makeText(getApplicationContext(), "E-mail is wrong", Toast.LENGTH_LONG).show();
            }
        }
        else{
            checkForToast();
        }
    }

    //----------------------------------------------------------------------------------------------
    // Check for House Seller values correctly write
    //----------------------------------------------------------------------------------------------
    private void getNameFromEditText(){
        nameTextInputLayout = findViewById(R.id.add_house_seller_activity_name);
        name = String.valueOf(Objects.requireNonNull(nameTextInputLayout.getEditText()).getText());
    }

    private void getEmailFromEditText(){
        emailTextInputLayout = findViewById(R.id.add_house_seller_activity_mail);
        email = String.valueOf(Objects.requireNonNull(emailTextInputLayout.getEditText()).getText());
    }

    private boolean nameIsCorrectlyWrite(){
        return !name.equals("");
    }

    private Boolean emailIsCorrectlyWrite(){
        return !email.equals("");
    }

    private void checkForToast(){
        if (!nameIsWrite && !emailIsWrite){
            Toast.makeText(getApplicationContext(), "A name and an e-mail must be write", Toast.LENGTH_LONG).show();
        }
        else if (!nameIsWrite){
            Toast.makeText(getApplicationContext(), "A name must be write", Toast.LENGTH_LONG).show();
        }
        else if (!emailIsWrite){
            Toast.makeText(getApplicationContext(), "An e-mail must be write", Toast.LENGTH_LONG).show();
        }
    }

    //----------------------------------------------------------------------------------------------
    // DATA
    //----------------------------------------------------------------------------------------------

    // Configure ViewModel
    private void configureViewModel(){
        AddHouseSellerViewModelFactory addHouseSellerViewModelFactory = Injection.providerAddHouseSellerViewModelFactory(getApplicationContext());
        this.viewModel = ViewModelProviders.of(this, addHouseSellerViewModelFactory).get(AddHouseSellerViewModel.class);
    }

    // Crete House Seller
    private void createHouseSeller (){
        HouseSeller houseSeller = new HouseSeller(name, email);
        this.viewModel.createHouseSeller(houseSeller);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    private void createToastAfterHouseSellerCreating(){
        viewModel.getHouseSellersLIst()
                .observe(this, houseSellers -> Toast.makeText(getApplicationContext(),
                        houseSellers.get(houseSellers.size()-1).getName() + "  " + houseSellers.get(houseSellers.size()-1).getEmail(),
                        Toast.LENGTH_LONG).show());
    }

}

