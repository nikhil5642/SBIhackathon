package com.example.nikhil.sbihackathon;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.R.attr.thumbnail;
import static android.os.Environment.getExternalStoragePublicDirectory;

public class ImageSelector extends AppCompatActivity {

    private static final int REQUEST_TAKE_PHOTO = 0;
    private static final int REQUEST_SELECT_IMAGE_IN_ALBUM = 1;
    private Uri mUriPhotoTaken;
    String mCurrentPhotoPath;
    private Button btn_capture,btn_gallary,_btn_cancel;
private ImageView img;
    String imageurl="0";


    private File mFilePhotoTaken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_selector);

        btn_capture=(Button)findViewById(R.id.button_takePhoto);
        btn_gallary=(Button)findViewById(R.id.button_getPicfromGallary);
        _btn_cancel=(Button)findViewById(R.id.button_Cancel_takephoto);
        img=(ImageView)findViewById(R.id.image);
    }

    public void TakePhoto(View view){
        File directory = new File(Environment.getExternalStorageDirectory() + "/Droid Wars/images");
        if (!directory.exists()) {
            directory.mkdirs();
        }
//
        File destination = new File(directory, System.currentTimeMillis() + ".jpg");
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //File imgFile = new File("Cache directory", "img.png");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(destination));
        imageurl = "file://" + destination.getAbsolutePath();
        startActivityForResult(intent, REQUEST_TAKE_PHOTO);
    }


    public void selectImageInAlbum(View view) {
        Intent intent = new Intent(
                Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(
                Intent.createChooser(intent, "Select File"),
                REQUEST_SELECT_IMAGE_IN_ALBUM);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_TAKE_PHOTO:
                if (resultCode == RESULT_OK) {
                 Toast.makeText(this,"ajdaj;lfaj;",Toast.LENGTH_SHORT);
                    Picasso.with(ImageSelector.this).load(imageurl).into(img);
                }
                break;
            case REQUEST_SELECT_IMAGE_IN_ALBUM:
                if (resultCode == RESULT_OK) {

                 Uri targetUri = data.getData();

                        try {
                            Picasso.with(this).load(targetUri).into(img);
                            //bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(targetUri));
                            //ivImage.setImageBitmap(bitmap);
                            imageurl = targetUri.toString();
                        }catch (Exception e){

                        }

                break;}
            default:
                break;
        }
    }
}
