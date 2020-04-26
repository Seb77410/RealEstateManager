package com.openclassrooms.realestatemanager.ui.activities.AddProperties;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.graphics.drawable.Drawable;
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
import com.openclassrooms.realestatemanager.injection.Injection;
import com.openclassrooms.realestatemanager.models.HouseSeller;
import com.openclassrooms.realestatemanager.models.InterestPoint;
import com.openclassrooms.realestatemanager.models.Media;
import com.openclassrooms.realestatemanager.models.Property;
import com.openclassrooms.realestatemanager.utils.PropertyTypeEnum;
import com.openclassrooms.realestatemanager.utils.Utils;

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
    private ArrayList<Boolean> mediaHaveComment = new ArrayList<>();
    private ArrayList<String> interestsPointList = new ArrayList<>();
    private Boolean isEditActivity = false;
    private Switch propertySold;
    private LinearLayout houseSellerContainer;
    private DatePickerDialog selector;


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
        propertySold = findViewById(R.id.add_property_activity_property_sold);
        houseSellerContainer = findViewById(R.id.add_property_activity_house_seller_container);

        this.getArguments();
        this.configureToolbar();
        this.configureViewModel();
        this.configurePropertyTypeSpinner();
        this.configurePropertyHouseSellerSpinner();
        this.configureAddPhotoButton();
        this.configureRecyclerView();
        this.updatingPhotoList();
        this.configurePropertyUiData();
        configureSavePropertyButton();

    }

    //----------------------------------------------------------------------------------------------
    //  Toolbar
    //----------------------------------------------------------------------------------------------
    private void configureToolbar(){
        Toolbar mToolbar = findViewById(R.id.add_property_activity_toolbar);
        setSupportActionBar(mToolbar);
        // Set back stack
        Drawable upArrow = ResourcesCompat.getDrawable(this.getResources(), R.drawable.ic_arrow_back_white_24dp, null);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(upArrow);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        if(isEditActivity){
            mToolbar.setTitle("Edit property");
        }
    }

    //----------------------------------------------------------------------------------------------
    //  Spinners
    //----------------------------------------------------------------------------------------------
    private void configurePropertyTypeSpinner(){
        propertyTypeSpinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, PropertyTypeEnum.values()));
        if (isEditActivity){
            if (property.getType().equals(PropertyTypeEnum.LOFT.toString())) {
                  propertyTypeSpinner.setSelection(0);
              }else if(property.getType().equals(PropertyTypeEnum.DUPLEX.toString())) {
                  propertyTypeSpinner.setSelection(1);
              }else if (property.getType().equals(PropertyTypeEnum.HOME.toString())) {
                propertyTypeSpinner.setSelection(2);
              }else if (property.getType().equals(PropertyTypeEnum.STUDIO.toString())) {
                  propertyTypeSpinner.setSelection(3);
              }else if (property.getType().equals(PropertyTypeEnum.APARTMENT.toString())) {
                  propertyTypeSpinner.setSelection(4);
              }
            }
    }

    private void configurePropertyHouseSellerSpinner(){
        if(isEditActivity){
            houseSellerContainer.setVisibility(View.GONE);
            this.configurePropertySoldSwitch();
        }else {
            propertySold.setVisibility(View.GONE);
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
    }

    private void configurePropertySoldSwitch(){

        if (property.getSold()){propertySold.setChecked(true);}
        else {propertySold.setChecked(false);}

        propertySold.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked){
                this.configureAndShowDatePicker();
            }
            else{ property.setSold(false); }
        });
    }

    private void configureAndShowDatePicker(){
        selector = new DatePickerDialog (AddPropertyActivity.this, R.style.TimePickerTheme , (view, year, month, dayOfMonth) -> {
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            property.setSold(true);
            property.setSellDate(calendar);
        }, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));

        selector.setOnCancelListener(dialogInterface -> {
            propertySold.setChecked(false);
            property.setSold(false);
        });

        selector.show();
        selector.getButton(DatePickerDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.primaryColor));
        selector.getButton(DatePickerDialog.BUTTON_NEGATIVE).setBackgroundColor(getResources().getColor(R.color.transparent));
        selector.getButton(DatePickerDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.primaryColor));
        selector.getButton(DatePickerDialog.BUTTON_POSITIVE).setBackgroundColor(getResources().getColor(R.color.transparent));
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

                if (interestPoint.getCategory().contains(schoolCheckbox.getText().toString())){
                    schoolCheckbox.setChecked(true);
                }
                if (interestPoint.getCategory().contains(businessCheckbox.getText().toString())){
                    businessCheckbox.setChecked(true);
                }
                if (interestPoint.getCategory().contains(gardenCheckbox.getText().toString())){
                    gardenCheckbox.setChecked(true);
                }
                if (interestPoint.getCategory().contains(transportCheckbox.getText().toString())){
                    transportCheckbox.setChecked(true);
                }
            }
        });
    }



    //----------------------------------------------------------------------------------------------
    //  Recycler view for photos
    //----------------------------------------------------------------------------------------------
    private void configureRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.add_property_activity_recyclerView);

        if (isEditActivity){
            viewModel.getMediaByPropertyId(property.getId()).observe(this, medias -> {
                mediaList = new ArrayList<>(medias);
                for(Media media : medias){
                    this.mediaHaveComment.add(true);
                }
                // Create adapter passing the list of articles
                adapter = new AddPhotosRecyclerAdapter(mediaList, mediaHaveComment,mediaToDelete ,Glide.with(this));
                // Attach the adapter to the recycler view to populate items
                recyclerView.setAdapter(adapter);
                // Set layout manager to position the items
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            });
        }else{
            // Create adapter passing the list of articles
            adapter = new AddPhotosRecyclerAdapter(mediaList, mediaHaveComment,Glide.with(this));
            // Attach the adapter to the recycler view to populate items
            recyclerView.setAdapter(adapter);
            // Set layout manager to position the items
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
        if(isEditActivity){floatingActionButton.setText("Update property");}
        floatingActionButton.setOnClickListener(v -> {
            boolean editTextContentAsNoError = checkForEditTextError();
            boolean photoContentAsNoError = photoContentIsOK(mediaList);
            boolean photoCommentAsNoError = photosCommentsAreOK();
            if (editTextContentAsNoError && photoContentAsNoError && photoCommentAsNoError){
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

        //Log.e("Interest Array", String.valueOf(interestsPointList));

        if (isEditActivity){

            Log.e("EDIT ACTIVITY", "MEDIA TO DELETE LIS = " + mediaToDelete);
            interestPoint.setCategory(interestsPointList);

            property.setPrice(getIntFromInputLayoutValue(priceEditText));
            property.setArea(getIntFromInputLayoutValue(surfaceEditText));
            property.setRooms(getIntFromInputLayoutValue(roomNumberEditText));
            property.setType(propertyTypeSpinner.getSelectedItem().toString());
            property.setDescription(getStringFromInputLayoutValue(descriptionEditText));
            property.setAddress(getStringFromInputLayoutValue(addressEditText));

            viewModel.updatePropertyAndData(interestPoint, property, mediaList, mediaToDelete, getApplicationContext());
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

    //----------------------------------------------------------------------------------------------
    // For edit property
    //----------------------------------------------------------------------------------------------

    private void getArguments(){
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            isEditActivity = true;
            property = Utils.convertStringToProperty(bundle.getString("property for details"));
        }

    }

}

