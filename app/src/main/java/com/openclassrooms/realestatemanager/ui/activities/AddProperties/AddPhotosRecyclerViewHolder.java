package com.openclassrooms.realestatemanager.ui.activities.AddProperties;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.textfield.TextInputLayout;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.models.Media;

import java.util.ArrayList;
import java.util.Objects;

public class AddPhotosRecyclerViewHolder extends RecyclerView.ViewHolder {

    private ImageView imageView;
    private TextInputLayout textInputLayout;
    private ImageButton deleteButton;
    private ArrayList<Media> mediaList = new ArrayList<>();
    private ArrayList<Boolean> mediaHaveComment = new ArrayList<>();
    private ArrayList<Long> mediaToDelete;

    AddPhotosRecyclerViewHolder(@NonNull View itemView) {
        super(itemView);
        imageView = itemView.findViewById(R.id.add_property_activity_item_image);
        textInputLayout = itemView.findViewById(R.id.add_property_activity_item_edit_text);
        deleteButton = itemView.findViewById(R.id.add_property_activity_item_delete_button);

        Objects.requireNonNull(textInputLayout.getEditText()).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mediaList.get(getAdapterPosition()).setComment(String.valueOf(s));
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    void updateWithUriList(ArrayList<Media> mediaList,ArrayList<Boolean> mediaHaveComment ,RequestManager glide, AddPhotosRecyclerAdapter addPhotosRecyclerAdapter){
        this.mediaList = mediaList;
        this.mediaHaveComment = mediaHaveComment;
        updateImage(glide);
        deletePhoto(addPhotosRecyclerAdapter);
        checkForCommentError();
        setComment();
    }

    void updateWithUriList(ArrayList<Media> mediaList,ArrayList<Boolean> mediaHaveComment , ArrayList<Long> mediaToDelete,RequestManager glide, AddPhotosRecyclerAdapter addPhotosRecyclerAdapter){
        this.mediaList = mediaList;
        this.mediaHaveComment = mediaHaveComment;
        this.mediaToDelete = mediaToDelete;
        updateImage(glide);
        deletePhoto(addPhotosRecyclerAdapter);
        checkForCommentError();
        setComment();
    }

    private void setComment() {
        if (mediaList.get(getAdapterPosition()).getComment() != null){
            Objects.requireNonNull(textInputLayout.getEditText()).setText(mediaList.get(getAdapterPosition()).getComment());
        }
    }

    private void updateImage(RequestManager glide) {
        glide.load(mediaList.get(getAdapterPosition()).getMediaUri())
                .apply(RequestOptions.centerCropTransform())
                .placeholder(R.drawable.ic_home_marker_40)
                .error(R.drawable.ic_home_marker_40)
                .into(imageView);
    }

    private void checkForCommentError(){
        if (!mediaHaveComment.get(getAdapterPosition())){
            Objects.requireNonNull(textInputLayout.getEditText()).setError("Required");
        }
    }
    private void deletePhoto(AddPhotosRecyclerAdapter addPhotosRecyclerAdapter){
        deleteButton.setOnClickListener(v -> {
            if(mediaToDelete!= null){
                mediaToDelete.add(mediaList.get(getAdapterPosition()).getId());
            }
            Objects.requireNonNull(textInputLayout.getEditText()).setText(null);
            mediaHaveComment.remove(getAdapterPosition());
            mediaList.remove(getAdapterPosition());
            addPhotosRecyclerAdapter.notifyDataSetChanged();
        });
    }

}
