package com.example.nikhil.sbihackathon;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileNotFoundException;

public class scanner extends AppCompatActivity {
    private static final int REQUEST_TAKE_PHOTO = 0;
    private static final int REQUEST_SELECT_IMAGE_IN_ALBUM = 1;
    String imageurl="0";
    String text="";
    Button cont;
    ImageView scanner_image;
    TextView abc;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);
        setupActionbar();
        scanner_image=(ImageView)findViewById(R.id.scanner_image);

        abc=(TextView)findViewById(R.id.abc);
        cont=(Button)findViewById(R.id.button_scan);
        progressDialog=new ProgressDialog(scanner.this);
        cont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    progressDialog.setMessage("Processing...");
                    progressDialog.show();

                    Image_to_text();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
        scanner_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String[] items={"Take Photo","Choose From Gallary","Cancel"};
                AlertDialog.Builder builder=new AlertDialog.Builder(scanner.this);
                builder.setTitle("Add Photo");
                builder.setItems(items,new  DialogInterface.OnClickListener(){


                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(items[which].contains("Take Photo")){
                            File directory = new File(Environment.getExternalStorageDirectory() + "/SBI/images");
                            if (!directory.exists()) {
                                directory.mkdirs();}
//
                            File destination = new File(directory, System.currentTimeMillis() + ".jpg");
                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            //File imgFile = new File("Cache directory", "img.png");
                            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(destination));
                            imageurl = "file://" + destination.getAbsolutePath();
                            startActivityForResult(intent, REQUEST_TAKE_PHOTO);
                        }
                        else if(items[which].contains("Choose")){
                            Intent intent = new Intent(
                                    Intent.ACTION_PICK,
                                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            intent.setType("image/*");
                            startActivityForResult(
                                    Intent.createChooser(intent, "Select File"),
                                    REQUEST_SELECT_IMAGE_IN_ALBUM);
                        }else if(items[which].contains("Cancel")){
                            dialog.dismiss();
                        }
                    }
                });
                builder.show();
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_TAKE_PHOTO:
                if (resultCode == RESULT_OK) {
                    Toast.makeText(this,"ajdaj;lfaj;",Toast.LENGTH_SHORT);
                    Picasso.with(scanner.this).load(imageurl).into(scanner_image);
                    scanner_image.setScaleType(ImageView.ScaleType.FIT_XY);
                    abc.setVisibility(View.GONE);
                }
                break;
            case REQUEST_SELECT_IMAGE_IN_ALBUM:
                if (resultCode == RESULT_OK) {

                    Uri targetUri = data.getData();

                    try {
                        Picasso.with(this).load(targetUri).into(scanner_image);
                        scanner_image.setScaleType(ImageView.ScaleType.FIT_XY);
                        abc.setVisibility(View.GONE);
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
    private void Image_to_text() throws FileNotFoundException {
        Bitmap imageBitmap= BitmapFactory.decodeStream(getContentResolver().openInputStream(Uri.parse(imageurl)));
        scanner_image.setImageBitmap(imageBitmap);

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
                text=text+textBlock.getValue()+"\n";
                Log.i("Problem", textBlock.getValue());
            }
            String s=text;
            text="";
           Intent intent=new Intent(this,scanner_result.class);
            intent.putExtra("result",s);
            startActivity(intent);
         //   progressDialog.dismiss();
        }

    }

    @Override
    public void onBackPressed() {
          super.onBackPressed();
        if (imageurl.contains("SBI")) {
            File file = new File(imageurl.substring(7));
            if (file.exists()) {
                boolean result = file.delete();
                Toast.makeText(this, "File Deleted", Toast.LENGTH_LONG).show();

            } else {
                Toast.makeText(this, "File Not Deleted", Toast.LENGTH_LONG).show();
            }
        }

    }
    private void setupActionbar(){
        LayoutInflater inflater=(LayoutInflater)this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v=inflater.inflate(R.layout.actionbar,null);

        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        //actionBar.setBackgroundDrawable(getDrawable(R.drawable.sbi_logo));
        //actionBar.setHomeButtonEnabled(true);
        //  actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setCustomView(v);
        ImageView home=(ImageView)findViewById(R.id.home_btn);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(scanner.this,HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

}
