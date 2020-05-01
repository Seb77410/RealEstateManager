package com.openclassrooms.realestatemanager.ui.activities.AddProperties;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.openclassrooms.realestatemanager.R;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import static android.app.Activity.RESULT_OK;

public class BottomSheetAddPhoto extends BottomSheetDialogFragment {

    private static final int GALLERY_PICK = 1;
    private static final int MY_CAMERA_PERMISSION_CODE = 2;
    private static final int REQUEST_TAKE_PHOTO = 3;
    private String currentPhotoPath;
    private Uri imageUri;
    private AddPropertyActivity.OnBottomSheetInteractionListener callback;

    BottomSheetAddPhoto(AddPropertyActivity.OnBottomSheetInteractionListener callback) {
        this.callback = callback;
    }

    //----------------------------------------------------------------------------------------------
    // onCreate
    //----------------------------------------------------------------------------------------------
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.bottom_sheet_add_photo, container, false);

        configureGalleryButton(v);
        configureCameraButton(v);
        return v;
    }

    //----------------------------------------------------------------------------------------------
    //  Gallery button
    //----------------------------------------------------------------------------------------------
    private void configureGalleryButton(View v) {
        Button galleryButton = v.findViewById(R.id.bottom_sheet_add_photo_from_gallery);
        galleryButton.setOnClickListener(v1 -> {
            Intent galleryIntent = new Intent(Intent.ACTION_OPEN_DOCUMENT, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
            startActivityForResult(galleryIntent, GALLERY_PICK);
        });
    }

    //----------------------------------------------------------------------------------------------
    //  Camera button
    //----------------------------------------------------------------------------------------------
    private void configureCameraButton(View v) {
        Button cameraButton = v.findViewById(R.id.bottom_sheet_add_photo_from_camera);
        cameraButton.setOnClickListener(v1 -> {
            if (ActivityCompat.checkSelfPermission(Objects.requireNonNull(getContext()), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
            } else {
                dispatchTakePictureIntent();
            }
        });
    }

    //----------------------------------------------------------------------------------------------
    //  Ask for camera permission
    //----------------------------------------------------------------------------------------------
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_PERMISSION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                dispatchTakePictureIntent();
            } else {
                Toast.makeText(getContext(), "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }

    //----------------------------------------------------------------------------------------------
    //  Save image file
    //----------------------------------------------------------------------------------------------
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(Objects.requireNonNull(getContext()).getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(getContext(),
                        "com.openclassrooms.realestatemanager.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Objects.requireNonNull(getContext()).getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        Log.e("createImageFile()", "inside method, image = " + image.toString());
        return image;
    }

    @SuppressWarnings("deprecation")
    private void addImageToInternalStorage() {
        Log.e("galleryAddPick()", "inside method");
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(currentPhotoPath);
        imageUri = Uri.fromFile(f);
        mediaScanIntent.setData(imageUri);
        Objects.requireNonNull(getContext()).sendBroadcast(mediaScanIntent);
    }

    //----------------------------------------------------------------------------------------------
    //  Activity Result
    //----------------------------------------------------------------------------------------------
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case GALLERY_PICK:
                if (resultCode == RESULT_OK && data != null) {
                    imageUri = data.getData();
                    callback.bottomSheetPhotoUriCallback(imageUri);
                    Log.e("ACTIVITY RESULT", "image uri = " + imageUri);
                }
                break;

            case REQUEST_TAKE_PHOTO:
                if (resultCode == RESULT_OK && data != null) {
                    addImageToInternalStorage();
                    callback.bottomSheetPhotoUriCallback(imageUri);
                    Log.e("ACTIVITY RESULT", "image uri = " + imageUri);
                }
                break;
        }
    }

}
