package com.openclassrooms.realestatemanager.ui.fragments.propertyDetailsFragment;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.chip.Chip;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.database.injection.Injection;
import com.openclassrooms.realestatemanager.models.database.Media;
import com.openclassrooms.realestatemanager.models.database.Property;
import com.openclassrooms.realestatemanager.ui.activities.AddProperties.AddPropertyActivity;
import com.openclassrooms.realestatemanager.utils.Converters;
import com.openclassrooms.realestatemanager.utils.MyConstants;
import com.openclassrooms.realestatemanager.utils.Utils;

import java.util.List;
import java.util.Objects;

public class PropertyDetailsFragment extends Fragment {

    //----------------------------------------------------------------------------------------------
    // Data
    //----------------------------------------------------------------------------------------------
    private String sProperty;
    private Property property;
    private TextView propertyType;
    private TextView propertyDescription;
    private TextView propertyPrice;
    private TextView propertySurface;
    private TextView propertyRoomNumber;
    private TextView propertyAddress;
    private TextView propertyForSellDate;
    private RecyclerView recyclerView;
    private LinearLayout propertyNearbyContainer;
    private LinearLayout propertyNearbyContentContainer;
    private PropertyDetailsViewModel viewModel;
    private ImageView staticMap;
    private ExtendedFloatingActionButton editButton;

    //----------------------------------------------------------------------------------------------
    // Constructor
    //----------------------------------------------------------------------------------------------
    public PropertyDetailsFragment() {}

      public static PropertyDetailsFragment newInstance(Property property) {
        PropertyDetailsFragment fragment = new PropertyDetailsFragment();
        Bundle args = new Bundle();
        args.putString(MyConstants.PROPERTY_DETAILS_ACTIVITY_PARAM, Converters.convertPropertyToString(property));
        fragment.setArguments(args);
        return fragment;
    }

    //----------------------------------------------------------------------------------------------
    // On Create
    //----------------------------------------------------------------------------------------------
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.sProperty = getArguments().getString(MyConstants.PROPERTY_DETAILS_ACTIVITY_PARAM);
            this.property = Converters.convertStringToProperty(getArguments().getString(MyConstants.PROPERTY_DETAILS_ACTIVITY_PARAM));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragmentResult = inflater.inflate(R.layout.fragment_property_details, container, false);

        propertyType = fragmentResult.findViewById(R.id.property_details_fragment_type);
        propertyDescription = fragmentResult.findViewById(R.id.property_details_fragment_description_content);
        propertyPrice = fragmentResult.findViewById(R.id.property_details_fragment_price);
        propertySurface = fragmentResult.findViewById(R.id.property_details_fragment_surface_content);
        propertyRoomNumber = fragmentResult.findViewById(R.id.property_details_fragment_room_number_content);
        propertyAddress = fragmentResult.findViewById(R.id.property_details_fragment_address_content);
        propertyNearbyContainer = fragmentResult.findViewById(R.id.property_details_fragment_nearby_container);
        propertyNearbyContentContainer = fragmentResult.findViewById(R.id.property_details_fragment_nearby_content_container);
        propertyForSellDate = fragmentResult.findViewById(R.id.property_details_fragment_sell_date_content);
        recyclerView = fragmentResult.findViewById(R.id.property_details_fragment_recyclerView);
        staticMap = fragmentResult.findViewById(R.id.property_details_fragment_address_map_image);
        editButton = fragmentResult.findViewById(R.id.property_details_fragment_edit_floating_button);


        this.configureViewModel();
        this.setPropertyType();
        this.setPropertyPrice();
        this.setPropertyForSellDate();
        this.setPropertyDescription();
        this.setPropertySurface();
        this.setPropertyRoomNumber();
        this.setPropertyAddress();
        this.getPropertyMediaData();
        this.setPropertyNearby();
        this.setStaticMap();
        this.configureEditButton();

        return fragmentResult;
    }

    //----------------------------------------------------------------------------------------------
    // Set Ui property data
    //----------------------------------------------------------------------------------------------
    private void setPropertyType() {
        propertyType.setText(String.valueOf(property.getType()));
    }

    private void setPropertyDescription(){propertyDescription.setText(property.getDescription());}

    private void setPropertyPrice(){
        propertyPrice.setText(String.valueOf(property.getPrice()));
    }

    private void setPropertySurface(){propertySurface.setText(String.format("%s %s", String.valueOf(property.getArea()), "m²") );}

    private void setPropertyRoomNumber(){propertyRoomNumber.setText(String.valueOf(property.getRooms()));}

    private void setPropertyAddress(){
        propertyAddress.setText(property.getAddress());
    }

    private void setPropertyNearby(){
        viewModel.getInterestPointById(property.getId()).observe(getViewLifecycleOwner(), interestPoint -> {
            if(interestPoint.getCategory().size() > 0 ){

                for(String nearby : interestPoint.getCategory()){
                    // Create params for chip
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT);
                    params.setMargins(20, 0, 0, 0);
                    // Create chip with params
                    Chip chip = new Chip(Objects.requireNonNull(getContext()));
                    chip.setText(nearby);
                    chip.setClickable(false);
                    chip.setLayoutParams(params);
                    chip.setLayoutParams(params);
                    // Show chip
                    propertyNearbyContentContainer.addView(chip);
                }
            }
            else{propertyNearbyContainer.setVisibility(View.GONE);}
        });
    }

    private void setStaticMap(){
        ConnectivityManager connectivityManager = (ConnectivityManager) Objects.requireNonNull(getContext()).getSystemService(Context.CONNECTIVITY_SERVICE);
        if (Utils.isInternetAvailableNew(connectivityManager)) {
            String propertyAddress = property.getAddress().replaceAll("\\s", "+");
            Log.i("PropertyDetailsFragment", "Address for static map api = " + propertyAddress);

            String mapUrl = String.format("%s %s %s %s", MyConstants.STATIC_MAP_BASE_URL, propertyAddress,
                    MyConstants.STATIC_MAP_PARAMS_URL_1 + getResources().getString(R.string.map_api_key) + MyConstants.STATIC_MAP_PARAMS_URL_2, propertyAddress);

            Glide.with(Objects.requireNonNull(getContext()))
                    .load(mapUrl)
                    .centerCrop()
                    .placeholder(R.drawable.ic_home_24px)
                    .error(R.drawable.ic_home_24px)
                    .into(staticMap);
        }
        else {staticMap.setVisibility(View.GONE);}
    }

    //----------------------------------------------------------------------------------------------
    // Edit button
    //----------------------------------------------------------------------------------------------
    private void configureEditButton(){
        editButton.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), AddPropertyActivity.class);
            intent.putExtra(MyConstants.EDIT_ACTIVITY_PARAM, sProperty);
            startActivity(intent);
        });
    }

    //----------------------------------------------------------------------------------------------
    // Configure ViewModel
    //----------------------------------------------------------------------------------------------
    private void configureViewModel() {
        PropertyDetailsViewModelFactory propertyDetailsViewModelFactory = Injection.providerPropertyDetailsViewModelFactory(getActivity());
        this.viewModel = new ViewModelProvider(this, propertyDetailsViewModelFactory).get(PropertyDetailsViewModel.class);
    }

    private void getPropertyMediaData(){
        viewModel.getMediasByPropertyId(property.getId()).observe(getViewLifecycleOwner(), this::configureRecyclerView);
    }

    private void setPropertyForSellDate(){
        propertyForSellDate.setText(Converters.convertCalendarToFormatString(property.getCreateDate()));
    }

    //----------------------------------------------------------------------------------------------
    // Configure RecyclerView
    //----------------------------------------------------------------------------------------------
    private void configureRecyclerView(List<Media> mediaList){
        PropertyDetailsPhotoAdapter adapter = new PropertyDetailsPhotoAdapter(mediaList, Glide.with(Objects.requireNonNull(getContext())));
        recyclerView.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
    }

}
