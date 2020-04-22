package com.openclassrooms.realestatemanager.ui.activities.AddProperties;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.bumptech.glide.Glide;
import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.injection.Injection;
import com.openclassrooms.realestatemanager.models.HouseSeller;
import com.openclassrooms.realestatemanager.models.InterestPoint;
import com.openclassrooms.realestatemanager.models.Media;
import com.openclassrooms.realestatemanager.models.Property;
import com.openclassrooms.realestatemanager.utils.PropertyTypeEnum;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;

public class AddPropertyActivity extends AppCompatActivity {


    private AddPropertiesViewModel viewModel;
    private Spinner propertyTypeSpinner;
    private Spinner propertyHouseSellerSpinner;
    private AddPhotosRecyclerAdapter adapter;
    private OnBottomSheetInteractionListener callback;
    private TextInputLayout priceEditText;
    private TextInputLayout surfaceEditText;
    private TextInputLayout roomNumberEditText;
    private TextInputLayout addressEditText;
    private TextInputLayout descriptionEditText;
    private InterestPoint interestPoint;
    private Property property;
    private ArrayList<HouseSeller> houseSellerList = new ArrayList<>();
    private MaterialCheckBox schoolCheckbox;
    private MaterialCheckBox businessCheckbox;
    private MaterialCheckBox gardenCheckbox;
    private MaterialCheckBox transportCheckbox;
    private ArrayList<Media> mediaList = new ArrayList<>();
    private ArrayList<Boolean> mediaHaveComment = new ArrayList<>();
    ArrayList<String> interestsPointList = new ArrayList<>();
    long interestPointId;

    //----------------------------------------------------------------------------------------------
    //  On Create
    //----------------------------------------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_property);

        priceEditText = findViewById(R.id.add_property_activity_price_filledTextField);
        surfaceEditText = findViewById(R.id.add_property_activity_surface_filledTextField);
        roomNumberEditText = findViewById(R.id.add_property_activity_room_number_filledTextField);
        addressEditText = findViewById(R.id.add_property_activity_address_filledTextField);
        descriptionEditText = findViewById(R.id.add_property_activity_description_filledTextField);
        propertyTypeSpinner = findViewById(R.id.add_property_activity_type_spinner);
        propertyHouseSellerSpinner = findViewById(R.id.add_property_activity_house_seller_spinner);
        schoolCheckbox = findViewById(R.id.add_property_activity_school_checkbox);
        businessCheckbox = findViewById(R.id.add_property_activity_business_checkbox);
        gardenCheckbox = findViewById(R.id.add_property_activity_garden_checkbox);
        transportCheckbox = findViewById(R.id.add_property_activity_transport_checkbox);


        configureBackStack();
        configureViewModel();
        configurePropertyTypeSpinner();
        configurePropertyHouseSellerSpinner();
        configureAddPhotoButton();
        configureRecyclerView(mediaList);
        updatingPhotoList();
        configureSavePropertyButton();
    }

    //----------------------------------------------------------------------------------------------
    //  Back Stack
    //----------------------------------------------------------------------------------------------
    private void configureBackStack(){
        Toolbar mToolbar = findViewById(R.id.add_property_activity_toolbar);
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
    //  Spinners
    //----------------------------------------------------------------------------------------------
    private void configurePropertyTypeSpinner(){
        propertyTypeSpinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, PropertyTypeEnum.values()));
    }

    private void configurePropertyHouseSellerSpinner(){
        // Get House Seller list name into ArrayList
        ArrayList<CharSequence> houseSellerNameArray = new ArrayList<>();
        viewModel.getHouseSellersLIst().observe(this, houseSellers -> {
            for (HouseSeller houseSeller : houseSellers){
                houseSellerList.add(houseSeller);
                houseSellerNameArray.add(houseSeller.getName());
            }
            propertyHouseSellerSpinner.setAdapter(new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_spinner_dropdown_item, houseSellerNameArray));
        });
    }

    //----------------------------------------------------------------------------------------------
    //  Add photo button
    //----------------------------------------------------------------------------------------------
    private void configureAddPhotoButton(){
        Button addPhotoButton = findViewById(R.id.add_property_activity_add_photo_button);
        addPhotoButton.setOnClickListener(v -> openBottomSheet());
    }

    private void openBottomSheet(){
        BottomSheetAddPhoto bottomSheetAddPhoto = new BottomSheetAddPhoto(callback);
        bottomSheetAddPhoto.show(getSupportFragmentManager(), "bottom sheet");
    }

    //----------------------------------------------------------------------------------------------
    //  Recycler view for photos
    //----------------------------------------------------------------------------------------------
    private void configureRecyclerView(ArrayList<Media> mediaList) {
        RecyclerView recyclerView = findViewById(R.id.add_property_activity_recyclerView);
        // Create adapter passing the list of articles
        adapter = new AddPhotosRecyclerAdapter(mediaList, mediaHaveComment,Glide.with(this));
        // Attach the adapter to the recycler view to populate items
        recyclerView.setAdapter(adapter);
        // Set layout manager to position the items
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
    }

    //----------------------------------------------------------------------------------------------
    // Data from ViewModel
    //----------------------------------------------------------------------------------------------
    private void configureViewModel(){
        AddPropertiesViewModelFactory addPropertiesViewModelFactory = Injection.providerAddPropertiesViewModelFactory(getApplicationContext());
        this.viewModel = new ViewModelProvider(this, addPropertiesViewModelFactory).get(AddPropertiesViewModel.class);
    }

    //----------------------------------------------------------------------------------------------
    // Data from BottomSheet
    //----------------------------------------------------------------------------------------------
    public interface OnBottomSheetInteractionListener {
        void bottomSheetPhotoUriCallback(Uri imageUri);
    }

    private void updatingPhotoList(){
        callback = imageUri -> {
            Log.e("AddPropertyActivity", "image uri = " + imageUri);
            mediaList.add(new Media(null, imageUri, 0));
            mediaHaveComment.add(true);
            adapter.notifyDataSetChanged();
        };
    }

    //----------------------------------------------------------------------------------------------
    // Save button
    //----------------------------------------------------------------------------------------------

    private void configureSavePropertyButton(){
        ExtendedFloatingActionButton floatingActionButton = findViewById(R.id.add_house_seller_save_floating_button);
        floatingActionButton.setOnClickListener(v -> {
            boolean editTextContentAsNoError = checkForEditTextError();
            boolean photoContentAsNoError = photoContentIsOK(mediaList);
            boolean photoCommentAsNoError = photosCommentsAreOK();
            if (editTextContentAsNoError && photoContentAsNoError && photoCommentAsNoError){
                //createInterestPoint();
                //createProperty();
                //saveMedias();

                createPropertyAndData();

                if (mediaList.get(0).getComment() == null) {
                    Log.e("MEDIA LIST", " comment = null" );
                }else {
                    Log.e("MEDIA LIST", " comment =" + mediaList.get(0).getComment());
                }
            }

        });

    }

    private String getStringFromInputLayoutValue(TextInputLayout textInputLayout){
        return String.valueOf(Objects.requireNonNull(textInputLayout.getEditText()).getText());
    }

    private int getIntFromInputLayoutValue(TextInputLayout textInputLayout){
        return Integer.parseInt(getStringFromInputLayoutValue(textInputLayout));
    }

    private boolean editTextContentIsOK(TextInputLayout textInputLayout){
        String value = getStringFromInputLayoutValue(textInputLayout);
        if (value.equals("")){
            textInputLayout.setError("Required");
            return false;
        }
        else{
            textInputLayout.setError(null);
            return true;
        }
    }

    private boolean checkForEditTextError(){
        editTextContentIsOK(priceEditText);
        editTextContentIsOK(surfaceEditText);
        editTextContentIsOK(roomNumberEditText);
        editTextContentIsOK(addressEditText);
        editTextContentIsOK(descriptionEditText);

        return editTextContentIsOK(priceEditText) && editTextContentIsOK(surfaceEditText) && editTextContentIsOK(roomNumberEditText) &&
                editTextContentIsOK(addressEditText) && editTextContentIsOK(descriptionEditText);
    }

    private boolean photoContentIsOK(ArrayList<Media> mediaList){
        if (mediaList.size() == 0){
            AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.myDialog));
            builder.setMessage("You have to add at least one photo")
                    .setTitle("No photo")
                    .show();
            return false;
        }
        else{
            return true;
        }
    }

    private boolean photosCommentsAreOK(){
        boolean allMediaHaveComment = true;
        for(int x = 0; x<mediaList.size(); x++){
            if(mediaList.get(x).getComment() == null || mediaList.get(x).getComment().equals("")){
                mediaHaveComment.set(x, false);
                allMediaHaveComment = false;
                adapter.notifyItemChanged(x);
            }
        }
        return allMediaHaveComment;
    }

    private void createInterestPoint(){
        interestsPointList = new ArrayList<>();
        if(schoolCheckbox.isChecked()){
            interestsPointList.add(schoolCheckbox.getText().toString());
        }
        if(businessCheckbox.isChecked()){
            interestsPointList.add(businessCheckbox.getText().toString());
        }
        if(gardenCheckbox.isChecked()){
            interestsPointList.add(gardenCheckbox.getText().toString());
        }
        if(transportCheckbox.isChecked()){
            interestsPointList.add(transportCheckbox.getText().toString());
        }

        Log.e("Interest Array", String.valueOf(interestsPointList));
        // TODO : transformer l'arrayList en String?? ou le laisser en Array ??
        Gson gson = new Gson();
        String sInterestsPointsList = gson.toJson(interestsPointList);
        Log.e("Interest string Array", sInterestsPointsList);

        interestPoint = new InterestPoint(sInterestsPointsList);
        viewModel.createInterestPoint(interestPoint);
        viewModel.getLastInterestPointSaved().observe(this, interestPoint1 ->
                interestPoint.setId(interestPoint1.getId()));
        Log.e("Interest point id", String.valueOf(interestPoint.getId()));

    }


    private void createProperty(){

        property = new Property(getIntFromInputLayoutValue(priceEditText), getIntFromInputLayoutValue(surfaceEditText),
                getIntFromInputLayoutValue(roomNumberEditText), propertyTypeSpinner.getSelectedItem().toString(),
                getStringFromInputLayoutValue(descriptionEditText), getStringFromInputLayoutValue(addressEditText),
                "For sale", Calendar.getInstance(), null, interestPoint.getId(),
                houseSellerList.get(propertyHouseSellerSpinner.getSelectedItemPosition()).getId());

        viewModel.createProperty(property);
        viewModel.getLastPropertySaved().observe(this, property1 ->
                property.setId(property1.getId()));
        Log.e("Property id", String.valueOf(property.getId()));
    }

    private void createPropertyAndData(){
        interestsPointList = new ArrayList<>();
        if(schoolCheckbox.isChecked()){
            interestsPointList.add(schoolCheckbox.getText().toString());
        }
        if(businessCheckbox.isChecked()){
            interestsPointList.add(businessCheckbox.getText().toString());
        }
        if(gardenCheckbox.isChecked()){
            interestsPointList.add(gardenCheckbox.getText().toString());
        }
        if(transportCheckbox.isChecked()){
            interestsPointList.add(transportCheckbox.getText().toString());
        }

        Log.e("Interest Array", String.valueOf(interestsPointList));
        // TODO : transformer l'arrayList en String?? ou le laisser en Array ??
        Gson gson = new Gson();
        String sInterestsPointsList = gson.toJson(interestsPointList);
        Log.e("Interest string Array", sInterestsPointsList);

        interestPoint = new InterestPoint(sInterestsPointsList);

        property = new Property(getIntFromInputLayoutValue(priceEditText), getIntFromInputLayoutValue(surfaceEditText),
                getIntFromInputLayoutValue(roomNumberEditText), propertyTypeSpinner.getSelectedItem().toString(),
                getStringFromInputLayoutValue(descriptionEditText), getStringFromInputLayoutValue(addressEditText),
                "For sale", Calendar.getInstance(), null, interestPoint.getId(),
                houseSellerList.get(propertyHouseSellerSpinner.getSelectedItemPosition()).getId());

        viewModel.createPropertyAndData(interestPoint, property, mediaList, getApplicationContext());
    }

}

