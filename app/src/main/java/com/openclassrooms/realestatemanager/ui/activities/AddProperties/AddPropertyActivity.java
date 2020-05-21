package com.openclassrooms.realestatemanager.ui.activities.AddProperties;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;

import com.bumptech.glide.Glide;
import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.database.injection.Injection;
import com.openclassrooms.realestatemanager.models.database.HouseSeller;
import com.openclassrooms.realestatemanager.models.database.InterestPoint;
import com.openclassrooms.realestatemanager.models.database.Media;
import com.openclassrooms.realestatemanager.models.database.Property;
import com.openclassrooms.realestatemanager.ui.activities.AddHouseSeller.AddHouseSellerActivity;
import com.openclassrooms.realestatemanager.utils.Converters;
import com.openclassrooms.realestatemanager.utils.MyConstants;
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
    private ArrayList<Long> mediaToDelete =  new ArrayList<>();
    private Boolean isEditActivity = false;
    private Switch propertySold;
    private LinearLayout houseSellerContainer;
    private RecyclerView recyclerView;


    //----------------------------------------------------------------------------------------------
    //  On Create
    //----------------------------------------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_property);

        recyclerView = findViewById(R.id.add_property_activity_recyclerView);

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
        propertySold = findViewById(R.id.add_property_activity_property_sold);
        houseSellerContainer = findViewById(R.id.add_property_activity_house_seller_container);

        this.getArguments();
        this.configureToolbar();
        this.configureViewModel();
        this.configurePropertyTypeSpinner();
        this.configureHouseSellerSpinner();
        this.configurePropertySoldSwitch();
        this.configureAddPhotoButton();
        this.configureRecyclerView();
        this.updatingPhotoList();
        this.configurePropertyUiData();
        this.configureSavePropertyButton();
    }

    //----------------------------------------------------------------------------------------------
    // For edit property
    //----------------------------------------------------------------------------------------------
    private void getArguments(){
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            isEditActivity = true;
            property = Converters.convertStringToProperty(bundle.getString(MyConstants.EDIT_ACTIVITY_PARAM));
        }
    }

    //----------------------------------------------------------------------------------------------
    //  Toolbar
    //----------------------------------------------------------------------------------------------
    private void configureToolbar(){
        Toolbar mToolbar = findViewById(R.id.add_property_activity_toolbar);
        setSupportActionBar(mToolbar);
        // Set back stack
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        // Set title
        if(isEditActivity){mToolbar.setTitle(getResources().getString(R.string.edit_property_title));}
    }

    //----------------------------------------------------------------------------------------------
    //  Spinners
    //----------------------------------------------------------------------------------------------
    private void configurePropertyTypeSpinner(){
        propertyTypeSpinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, PropertyTypeEnum.values()));
        // Set default selection
        if (isEditActivity){
            if (property.getType().equals(PropertyTypeEnum.LOFT.toString())) {propertyTypeSpinner.setSelection(0);}
            else if(property.getType().equals(PropertyTypeEnum.DUPLEX.toString())) {propertyTypeSpinner.setSelection(1);}
            else if (property.getType().equals(PropertyTypeEnum.HOME.toString())) {propertyTypeSpinner.setSelection(2);}
            else if (property.getType().equals(PropertyTypeEnum.STUDIO.toString())) {propertyTypeSpinner.setSelection(3);}
            else if (property.getType().equals(PropertyTypeEnum.APARTMENT.toString())) {propertyTypeSpinner.setSelection(4);}
        }
    }

    private void configureHouseSellerSpinner(){
        if(isEditActivity){houseSellerContainer.setVisibility(View.GONE);}
        else {
            // Get all House Seller names into ArrayList
            ArrayList<CharSequence> houseSellerNameArray = new ArrayList<>();
            viewModel.getHouseSellersLIst().observe(this, houseSellers -> {
                if(houseSellers.size()>0) {
                    for (HouseSeller houseSeller : houseSellers) {
                        houseSellerList.add(houseSeller);
                        houseSellerNameArray.add(houseSeller.getName());
                    }
                    // Show this list inside spinner
                    propertyHouseSellerSpinner.setAdapter(new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, houseSellerNameArray));
                }
                else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.myDialog));
                    builder.setMessage(getResources().getString(R.string.alert_no_house_seller_content))
                            .setTitle(getResources().getString(R.string.alert_no_house_seller_title))
                            .setPositiveButton("Register House seller", (dialog, which) -> {
                                Intent intent = new Intent(getApplicationContext(), AddHouseSellerActivity.class);
                                startActivity(intent);
                            })
                            .show();
                }
            });
        }
    }

    private void configurePropertySoldSwitch(){
        if(isEditActivity) {
            // Set default position
            if (property.isSold()) {propertySold.setChecked(true);}
            else {propertySold.setChecked(false);}
            // Configure switch reaction
            propertySold.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (isChecked) {this.configureAndShowDatePicker();}
                else {property.setSold(false);}
            });
        }
        else {propertySold.setVisibility(View.GONE);}
    }

    private void configureAndShowDatePicker(){
        // Create date picker dialog
        DatePickerDialog selector = new DatePickerDialog(AddPropertyActivity.this, R.style.TimePickerTheme, (view, year, month, dayOfMonth) -> {
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            property.setSold(true);
            property.setSellDate(calendar);
        }, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));

        // Configure cancel reaction
        selector.setOnCancelListener(dialogInterface -> {
            propertySold.setChecked(false);
            property.setSold(false);
        });

        // Show date picker
        selector.show();
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
    //  Edit Property data UI
    //----------------------------------------------------------------------------------------------
    private void configurePropertyUiData(){
        if(isEditActivity){
            Objects.requireNonNull(descriptionEditText.getEditText()).setText(property.getDescription());
            Objects.requireNonNull(priceEditText.getEditText()).setText(String.valueOf(property.getPrice()));
            Objects.requireNonNull(surfaceEditText.getEditText()).setText(String.valueOf(property.getArea()));
            Objects.requireNonNull(roomNumberEditText.getEditText()).setText(String.valueOf(property.getRooms()));
            Objects.requireNonNull(addressEditText.getEditText()).setText(property.getAddress());
            this.configureCheckboxes();
        }
    }

    private void configureCheckboxes(){
        viewModel.getInterestPointById(property.getInterestPointId()).observe(this, interestPoint1 -> {
            interestPoint = interestPoint1;
            if(interestPoint.getCategory() != null && interestPoint.getCategory().size()>0){
                if (interestPoint.getCategory().contains(schoolCheckbox.getText().toString())){schoolCheckbox.setChecked(true);}
                if (interestPoint.getCategory().contains(businessCheckbox.getText().toString())){businessCheckbox.setChecked(true);}
                if (interestPoint.getCategory().contains(gardenCheckbox.getText().toString())){gardenCheckbox.setChecked(true);}
                if (interestPoint.getCategory().contains(transportCheckbox.getText().toString())){transportCheckbox.setChecked(true);}
            }
        });
    }

    //----------------------------------------------------------------------------------------------
    //  Recycler view for photos
    //----------------------------------------------------------------------------------------------
    private void configureRecyclerView() {

        if (isEditActivity){
            viewModel.getMediaByPropertyId(property.getId()).observe(this, medias -> {
                mediaList = new ArrayList<>(medias);
                adapter = new AddPhotosRecyclerAdapter(mediaList,mediaToDelete ,Glide.with(this));
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            });
        }else{
            adapter = new AddPhotosRecyclerAdapter(mediaList,Glide.with(this));
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        }
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
            Log.i("AddPropertyActivity", "image uri added = " + imageUri);
            mediaList.add(new Media(null, imageUri, 0));
            adapter.notifyItemInserted(mediaList.size());
        };
    }

    //----------------------------------------------------------------------------------------------
    // Save button
    //----------------------------------------------------------------------------------------------

    private void configureSavePropertyButton(){
        ExtendedFloatingActionButton floatingActionButton = findViewById(R.id.add_house_seller_save_floating_button);
        if(isEditActivity){floatingActionButton.setText(getResources().getString(R.string.update_property));}

        floatingActionButton.setOnClickListener(v -> {
            boolean editTextContentAsNoError = checkForEditTextError();
            boolean photoContentAsNoError = photoContentIsOK(mediaList);
            boolean photoCommentAsNoError = photosCommentsAreOK();
            if (editTextContentAsNoError && photoContentAsNoError && photoCommentAsNoError){
                createOrUpdatePropertyData();}
        });
    }

    private void createOrUpdatePropertyData(){
        ArrayList<String> interestsPointList = new ArrayList<>();
        if(schoolCheckbox.isChecked()){interestsPointList.add(schoolCheckbox.getText().toString());}
        if(businessCheckbox.isChecked()){interestsPointList.add(businessCheckbox.getText().toString());}
        if(gardenCheckbox.isChecked()){interestsPointList.add(gardenCheckbox.getText().toString());}
        if(transportCheckbox.isChecked()){interestsPointList.add(transportCheckbox.getText().toString());}

        if (isEditActivity){
            Log.i("EditActivity", "MediaId list to delete  = " + mediaToDelete);
            interestPoint.setCategory(interestsPointList);

            property.setPrice(getIntFromInputLayoutValue(priceEditText));
            property.setArea(getIntFromInputLayoutValue(surfaceEditText));
            property.setRooms(getIntFromInputLayoutValue(roomNumberEditText));
            property.setType(propertyTypeSpinner.getSelectedItem().toString());
            property.setDescription(getStringFromInputLayoutValue(descriptionEditText));
            property.setAddress(getStringFromInputLayoutValue(addressEditText));

            viewModel.updatePropertyAndData(interestPoint, property, mediaList, mediaToDelete, this);
        }
        else{
            interestPoint = new InterestPoint(interestsPointList);
            Property property1 = new Property(getIntFromInputLayoutValue(priceEditText), getIntFromInputLayoutValue(surfaceEditText),
                    getIntFromInputLayoutValue(roomNumberEditText), propertyTypeSpinner.getSelectedItem().toString(),
                    getStringFromInputLayoutValue(descriptionEditText), getStringFromInputLayoutValue(addressEditText),
                    false, Calendar.getInstance(), null, interestPoint.getId(),
                    houseSellerList.get(propertyHouseSellerSpinner.getSelectedItemPosition()).getId());

            viewModel.createPropertyAndData(interestPoint, property1, mediaList, getApplicationContext());
        }
    }

    private String getStringFromInputLayoutValue(TextInputLayout textInputLayout){
        return String.valueOf(Objects.requireNonNull(textInputLayout.getEditText()).getText());
    }

    private int getIntFromInputLayoutValue(TextInputLayout textInputLayout){
        return Integer.parseInt(getStringFromInputLayoutValue(textInputLayout));
    }

    //----------------------------------------------------------------------------------------------
    // Error check
    //----------------------------------------------------------------------------------------------

    private boolean editTextContentIsOK(TextInputLayout textInputLayout){
        String value = getStringFromInputLayoutValue(textInputLayout);
        if (value.equals("")){
            textInputLayout.setError(getResources().getString(R.string.required));
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
            builder.setMessage(getResources().getString(R.string.alert_no_photo_content))
                    .setTitle(getResources().getString(R.string.alert_no_photo_title))
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
            TextInputLayout textInputLayout = Objects.requireNonNull(recyclerView.findViewHolderForAdapterPosition(x)).itemView.findViewById(R.id.add_property_activity_item_edit_text);
            if(mediaList.get(x).getComment() == null || mediaList.get(x).getComment().equals("")){
                allMediaHaveComment = false;
                textInputLayout.setError(getResources().getString(R.string.required));
            }else{
                textInputLayout.setError(null);
            }
        }
        return allMediaHaveComment;
    }

}

