package com.example.nikhil.sbihackathon;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class loan_enquiry extends AppCompatActivity {
    ArrayList<Account_layout> accounts;
    Account_layout customer;
    Spinner from_acc_no;
    Button cont;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loan_enquiry);
        setupActionbar();
        final Intent intent1=getIntent();
        accounts=(ArrayList<Account_layout>)intent1.getSerializableExtra("loan_acc_list");
        customer=(Account_layout)intent1.getSerializableExtra("cust");

        from_acc_no=(Spinner)findViewById(R.id.loan_enq_acc_no);
        cont=(Button)findViewById(R.id.loan_enq_submit);
        int length=(int)accounts.size()+1;
        String[] strings=new String[length];
        strings[0]="Select Account";
        for(int i=0;i<(length-1);i++){
            strings[i+1]=accounts.get(i).getAccount_no();
        }
        final ArrayAdapter<String> adapter0 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, strings);
        from_acc_no.setAdapter(adapter0);

        cont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(loan_enquiry.this,loan_enquiry_details.class);
                intent.putExtra("acc",from_acc_no.getSelectedItem().toString());
                startActivity(intent);
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
                Intent intent=new Intent(loan_enquiry.this,HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

}
