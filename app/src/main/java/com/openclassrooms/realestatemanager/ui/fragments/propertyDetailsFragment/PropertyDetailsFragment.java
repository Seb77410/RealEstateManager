package com.openclassrooms.realestatemanager.ui.fragments.propertyDetailsFragment;

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
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.injection.Injection;
import com.openclassrooms.realestatemanager.models.Media;
import com.openclassrooms.realestatemanager.models.Property;
import com.openclassrooms.realestatemanager.utils.Utils;

import java.util.List;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PropertyDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PropertyDetailsFragment extends Fragment
{
    private static final String ARG_PARAM1 = "property";

    //----------------------------------------------------------------------------------------------
    // Data
    //----------------------------------------------------------------------------------------------

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
    private LinearLayout propertyNearbyContentConainer;
    private PropertyDetailsViewModel viewModel;
    private PropertyDetailsPhotoAdapter adapter;
    private ImageView staticMap;

    //----------------------------------------------------------------------------------------------
    // Constructor
    //----------------------------------------------------------------------------------------------

    public PropertyDetailsFragment() {
        // Required empty public constructor
    }

      public static PropertyDetailsFragment newInstance(Property property) {
        PropertyDetailsFragment fragment = new PropertyDetailsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, Utils.convertPropertyToString(property));
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
            this.property = Utils.convertStringToProperty(getArguments().getString(ARG_PARAM1));
        }

        Log.e("OnCreate", "OnCreate");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragmentResult = inflater.inflate(R.layout.fragment_property_details, container, false);

        Log.e("OnCreateView", "OnCreateView");

        propertyType = fragmentResult.findViewById(R.id.property_details_fragment_type);
        propertyDescription = fragmentResult.findViewById(R.id.property_details_fragment_description_content);
        propertyPrice = fragmentResult.findViewById(R.id.property_details_fragment_price);
        propertySurface = fragmentResult.findViewById(R.id.property_details_fragment_surface_content);
        propertyRoomNumber = fragmentResult.findViewById(R.id.property_details_fragment_room_number_content);
        propertyAddress = fragmentResult.findViewById(R.id.property_details_fragment_address_content);
        propertyNearbyContainer = fragmentResult.findViewById(R.id.property_details_fragment_nearby_container);
        propertyNearbyContentConainer = fragmentResult.findViewById(R.id.property_details_fragment_nearby_content_container);
        propertyForSellDate = fragmentResult.findViewById(R.id.property_details_fragment_sell_date_content);
        recyclerView = fragmentResult.findViewById(R.id.property_details_fragment_recyclerView);
        staticMap = fragmentResult.findViewById(R.id.property_details_fragment_address_map_image);


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

        return fragmentResult;
    }

    //----------------------------------------------------------------------------------------------
    // Set Ui property data
    //----------------------------------------------------------------------------------------------
    private void setPropertyType() {
        propertyType.setText(String.valueOf(property.getType()));
    }

    private void setPropertyDescription(){
        propertyDescription.setText(property.getDescription());
    }

    private void setPropertyPrice(){
        propertyPrice.setText(String.valueOf(property.getPrice()));
    }

    private void setPropertySurface(){
        propertySurface.setText(String.format("%s %s", String.valueOf(property.getArea()), "m²") );
    }

    private void setPropertyRoomNumber(){
        propertyRoomNumber.setText(String.valueOf(property.getRooms()));
    }

    private void setPropertyAddress(){
        propertyAddress.setText(property.getAddress());
    }

    private void setPropertyNearby(){
        viewModel.getInterestPointById(property.getId()).observe(getViewLifecycleOwner(), interestPoint -> {
            if(interestPoint.getCategory().size() > 0 ){
                for(String nearby : interestPoint.getCategory()){

                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT);
                    params.setMargins(20, 0, 0, 0);

                    Chip chip = new Chip(Objects.requireNonNull(getContext()));
                    chip.setText(nearby);
                    chip.setClickable(false);
                    chip.setLayoutParams(params);
                    chip.setLayoutParams(params);

                    propertyNearbyContentConainer.addView(chip);
                }
            }
            else{
                propertyNearbyContainer.setVisibility(View.GONE);
            }
        });
    }

    private void setStaticMap(){
        if (Utils.isInternetAvailableNew(Objects.requireNonNull(getContext()))) {
            String propertyAddress = property.getAddress().replaceAll("\\s", "+");
            Log.e("New address", "for static map api = " + propertyAddress);

            String mapUrl = String.format("%s %s %s %s", "https://maps.googleapis.com/maps/api/staticmap?center=", propertyAddress, "&zoom=13&scale=1&size=600x300&maptype=roadmap&key=AIzaSyBaAwomMKgWDHN0nAzAvlUR-VRDolCplKw&format=png&visual_refresh=true&markers=size:mid%7Ccolor:0xff0000%7Clabel:1%7C", propertyAddress);

            Glide.with(Objects.requireNonNull(getContext()))
                    .load(mapUrl)
                    .centerCrop()
                    .placeholder(R.drawable.ic_home_24px)
                    .error(R.drawable.ic_home_24px)
                    .into(staticMap);
        }
        else {
            staticMap.setVisibility(View.GONE);
        }
    }

    //----------------------------------------------------------------------------------------------
    // Configure ViewModel
    //----------------------------------------------------------------------------------------------
    private void configureViewModel() {
        PropertyDetailsViewModelFactory propertyDetailsViewModelFactory = Injection.providerPropertyDetailsViewModelFactory(getActivity());
        this.viewModel = new ViewModelProvider(this, propertyDetailsViewModelFactory).get(PropertyDetailsViewModel.class);
    }


    private void getPropertyMediaData(){
        viewModel.getMediasByPropertyId(property.getId()).observe(getViewLifecycleOwner(), mediaList -> {
            for (Media media : mediaList){
                // Log.e("Details fragment", "Get media data, media Uri = " + media.getMediaUri());
            }
            configureRecyclerView(mediaList);
        });
    }

    private void setPropertyForSellDate(){
        propertyForSellDate.setText(Utils.convertCalendarToString(property.getCreateDate()));
    }

    //----------------------------------------------------------------------------------------------
    // Configure RecyclerView
    //----------------------------------------------------------------------------------------------

    private void configureRecyclerView(List<Media> mediaList){
        // Create adapter passing the list of articles
        adapter = new PropertyDetailsPhotoAdapter(mediaList , Glide.with(Objects.requireNonNull(getContext())));
        // Attach the adapter to the recycler view to populate items
        recyclerView.setAdapter(adapter);
        // Set layout manager to position the items
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
    }

}
