package com.example.nikhil.sbihackathon;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class transaction_status extends AppCompatActivity {

    Button home2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_status);

        home2=(Button)findViewById(R.id.xyz);
        home2.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent intent=new Intent(transaction_status.this,HomeActivity.class);
                                        startActivity(intent);
                                        finish();

                                    }
                                }

        );
        setupActionbar();
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
                Intent intent=new Intent(transaction_status.this,HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

}
