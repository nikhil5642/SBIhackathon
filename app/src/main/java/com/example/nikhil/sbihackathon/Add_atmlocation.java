package com.example.nikhil.sbihackathon;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Add_atmlocation extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private EditText lat,lon,name;
    Button sub;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_atmlocation);
       lat=(EditText)findViewById(R.id.latitude_add);
        lon=(EditText)findViewById(R.id.longitude_add);
        name=(EditText)findViewById(R.id.atm_name);
        sub=(Button)findViewById(R.id.btn_add_atm);
        setupActionbar();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String key = mDatabase.push().getKey();
                map_layout data=new map_layout(Double.parseDouble(lat.getText().toString()),
                                               Double.parseDouble(lon.getText().toString()),
                                               name.getText().toString(),0,key);

                mDatabase.child(data.getKey()).setValue(data);

                finish();
            }
        });


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
                Intent intent=new Intent(Add_atmlocation.this,HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }


}
