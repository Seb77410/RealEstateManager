package com.openclassrooms.realestatemanager.ui.activities.AddHouseSeller;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;
import androidx.lifecycle.ViewModelProvider;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.google.android.material.textfield.TextInputLayout;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.database.injection.Injection;
import com.openclassrooms.realestatemanager.models.database.HouseSeller;

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
        nameIsWrite = nameIsNotNull();
        emailIsWrite =  emailIsNotNull();
        if (nameIsWrite && emailIsWrite){
            nameTextInputLayout.setError(null);
            if(emailAsGoodFormat()) {
                createHouseSeller();
            }
        }
        else{
            checkForInputError();
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

    private boolean nameIsNotNull(){
        return !name.equals("");
    }

    private boolean emailIsNotNull(){
        return !email.equals("");
    }

    private void checkForInputError(){
        if (!nameIsWrite && !emailIsWrite){
            emailTextInputLayout.setError("Enter an e-mail");
            nameTextInputLayout.setError("Enter a name");
        }
        else if (!nameIsWrite){
            nameTextInputLayout.setError("Enter a name");
            emailTextInputLayout.setError(null);
            emailAsGoodFormat();
        }
        else if (!emailIsWrite){
            emailTextInputLayout.setError("Enter an e-mail");
            nameTextInputLayout.setError(null);
        }
        else{
            nameTextInputLayout.setError(null);
            emailAsGoodFormat();
        }
    }

    private boolean emailAsGoodFormat(){
        emailIsCorrect = !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
        if (!emailIsCorrect){
            emailTextInputLayout.setError("Pls enter a valid e-mail");
            return false;
        }
        else {
            emailTextInputLayout.setError(null);
            return true;
        }
     }

    //----------------------------------------------------------------------------------------------
    // DATA
    //----------------------------------------------------------------------------------------------

    // Configure ViewModel
    private void configureViewModel(){
        AddHouseSellerViewModelFactory addHouseSellerViewModelFactory = Injection.providerAddHouseSellerViewModelFactory(getApplicationContext());
        this.viewModel = new ViewModelProvider(this, addHouseSellerViewModelFactory).get(AddHouseSellerViewModel.class);
    }

    // Crete House Seller
    private void createHouseSeller (){
        HouseSeller houseSeller = new HouseSeller(name, email);
         this.viewModel.createHouseSeller(houseSeller, getApplicationContext());
    }



}

