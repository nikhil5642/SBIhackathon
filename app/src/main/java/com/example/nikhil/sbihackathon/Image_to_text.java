package com.example.nikhil.sbihackathon;

/**
 * Created by nikhil on 9/6/17.
 */

    // This sample uses the Apache HTTP client from HTTP Components (http://hc.apache.org/httpcomponents-client-ga/)
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.FaceDetector;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

import static android.media.FaceDetector.*;


public class Image_to_text extends AppCompatActivity{


    String text="";
    ImageView imageView;
    Button btn_process;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.face);

        imageView=(ImageView)findViewById(R.id.imageView);
        btn_process=(Button)findViewById(R.id.button_face);


      Bitmap imageBitmap=BitmapFactory.decodeResource(getResources(),R.drawable.text2);
        imageView.setImageBitmap(imageBitmap);

        if(imageBitmap != null) {

            TextRecognizer textRecognizer = new TextRecognizer.Builder(this).build();

            if(!textRecognizer.isOperational()) {
                Log.w("Problem", "Detector dependencies are not yet available.");

                IntentFilter lowstorageFilter = new IntentFilter(Intent.ACTION_DEVICE_STORAGE_LOW);
                boolean hasLowStorage = registerReceiver(null, lowstorageFilter) != null;

                if (hasLowStorage) {
                    Toast.makeText(this,"Low Storage", Toast.LENGTH_LONG).show();
                    Log.w("Problem", "Low Storage");
                }
            }


            Frame imageFrame = new Frame.Builder()
                    .setBitmap(imageBitmap)
                    .build();

            SparseArray<TextBlock> textBlocks = textRecognizer.detect(imageFrame);

            for (int i = 0; i < textBlocks.size(); i++) {
                TextBlock textBlock = textBlocks.get(textBlocks.keyAt(i));
                text=text+textBlock.getValue()+"/n";
                Log.i("Problem", textBlock.getValue());
            }
            Toast.makeText(this,text,Toast.LENGTH_LONG).show();
        }

    }

}

