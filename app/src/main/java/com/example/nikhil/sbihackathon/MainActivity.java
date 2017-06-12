package com.example.nikhil.sbihackathon;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.net.URI;
public class MainActivity extends AppCompatActivity {

    // Flag to indicate which task is to be performed.
    private static final int REQUEST_SELECT_IMAGE = 0;

    // The button to select an image
    private Button buttonSelectImage;


    private Uri imagUrl;

    // The image selected to detect.
    private Bitmap bitmap;

    // The edit to show status and result.
    private EditText editText;

    //max retry times to get operation result
    private int retryCountThreshold = 30;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        buttonSelectImage = (Button) findViewById(R.id.button_addImage);
        buttonSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,ImageSelector.class);
                startActivity(intent);
            }
        });

    }


}