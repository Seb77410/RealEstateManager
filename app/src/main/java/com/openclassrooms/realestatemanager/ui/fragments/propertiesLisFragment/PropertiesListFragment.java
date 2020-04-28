package com.openclassrooms.realestatemanager.ui.fragments.propertiesLisFragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.database.injection.Injection;
import com.openclassrooms.realestatemanager.models.database.Property;
import com.openclassrooms.realestatemanager.ui.activities.PropertyDetailsActivity;
import com.openclassrooms.realestatemanager.ui.fragments.propertyDetailsFragment.PropertyDetailsFragment;
import com.openclassrooms.realestatemanager.utils.RecyclerViewClickSupport;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class PropertiesListFragment extends Fragment {

    private RecyclerView recyclerView;
    private PropertiesListViewModel viewModel;
    private Uri[] propertiesPhotoList;
    private Fragment detailsFragment;
    private List<Property> propertiesList = new ArrayList<>();



    public PropertiesListFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragmentResult = inflater.inflate(R.layout.fragment_properties_list, container, false);

        recyclerView = fragmentResult.findViewById(R.id.properties_list_fragment_recyclerView);

        configureViewModel();
        getDataAndSTartRecyclerView();
        return fragmentResult;
    }


    //----------------------------------------------------------------------------------------------
    // Configure ViewModel
    //----------------------------------------------------------------------------------------------
    private void configureViewModel(){
        PropertiesListViewModelFactory PropertiesListViewModelFactory = Injection.providerPropertiesListViewModelFactory(getActivity());
        this.viewModel = new ViewModelProvider(this, PropertiesListViewModelFactory).get(PropertiesListViewModel.class);
    }

    //----------------------------------------------------------------------------------------------
    // Configure RecyclerView
    //----------------------------------------------------------------------------------------------

    private void configureRecyclerView(List<Property> propertyList){
            // Create adapter passing the list of articles
            PropertiesListAdapter adapter = new PropertiesListAdapter(propertyList, propertiesPhotoList, Glide.with(this));
            // Attach the adapter to the recycler view to populate items
            recyclerView.setAdapter(adapter);
            // Set layout manager to position the items
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    //----------------------------------------------------------------------------------------------
    // Get data and start RecyclerView
    //----------------------------------------------------------------------------------------------

    private void getDataAndSTartRecyclerView(){
        // Get properties list
        viewModel.getPropertiesList().observe(getViewLifecycleOwner(), propertiesList ->{
            this.propertiesList = propertiesList;
            Log.e("Properties list Frag", " List size = " + propertiesList.size());
            propertiesPhotoList = new Uri[propertiesList.size()];
            // Get preview photo for all properties
            for(int x = 0; x < propertiesList.size(); x++){
                int finalX = x;
                viewModel.getMediasByPropertyId(propertiesList.get(x).getId()).observe(getViewLifecycleOwner(), media ->{
                    propertiesPhotoList[finalX] = media.get(0).getMediaUri();
                    if(finalX == propertiesList.size()-1){
                        // Start recycler view
                        this.configureRecyclerView(propertiesList);
                        this.configureOnClickRecyclerView(recyclerView);
                        this.startDefaultDetailsFragmentForTablet();
                    }
                });
            }
            // Log.e("PropertiesPhotosList", "Inside fragment, preview photo list = " + propertiesPreviewPhotoList);
        });
    }

    /**
     *Configure item click on RecyclerView
     * @param recyclerView is item recycler view
     */
    private void configureOnClickRecyclerView(RecyclerView recyclerView) {
        RecyclerViewClickSupport.addTo(recyclerView, R.layout.fragment_properties_list_item)
                .setOnItemClickListener((recyclerView1, mPosition, v) -> configureAndShowDetailsFragment(propertiesList.get(mPosition)));
    }

    private void configureAndShowDetailsFragment(Property property) {
        detailsFragment = Objects.requireNonNull(getActivity()).getSupportFragmentManager().findFragmentById(R.id.main_activity_details_frame_layout);
        //A - We only add DetailFragment in Tablet mode (If found frame_layout_detail)
        if (getActivity().findViewById(R.id.main_activity_details_frame_layout) != null) {
            detailsFragment = PropertyDetailsFragment.newInstance(property);
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main_activity_details_frame_layout, detailsFragment)
                    .commit();
        }
        else{
            Intent intent = new Intent(getContext(), PropertyDetailsActivity.class);
            Gson gson = new Gson();
            String argument = gson.toJson(property);
            intent.putExtra("property", argument);
            startActivity(intent);
        }
    }

    private void startDefaultDetailsFragmentForTablet(){
        detailsFragment = Objects.requireNonNull(getActivity()).getSupportFragmentManager().findFragmentById(R.id.main_activity_details_frame_layout);
        if(propertiesList.size()>=1 && getActivity().findViewById(R.id.main_activity_details_frame_layout) != null){
            configureAndShowDetailsFragment(propertiesList.get(0));
        }
    }
}

