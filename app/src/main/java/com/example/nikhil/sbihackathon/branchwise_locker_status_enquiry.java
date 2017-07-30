package com.example.nikhil.sbihackathon;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONArray;
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

public class branchwise_locker_status_enquiry extends AppCompatActivity {
  String Sbi_REQUEST_URL="http://52.172.213.166:8080/sbi/Status/branchwise-locker-status-enquiry/Locker_Enquiry";
    Account_layout customer;
    Button submit;
    EditText branch,locker_type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_branchwise_locker_status_enquiry);

        setupActionbar();
        submit=(Button)findViewById(R.id.branch_wise_locker_status_submit);
        branch=(EditText)findViewById(R.id.locker_status_branach_code);
        locker_type=(EditText)findViewById(R.id.locker_status_locker_type);

        locker_type.setText("SMART VAULT");
        final Intent intent1=getIntent();
        customer=(Account_layout)intent1.getSerializableExtra("cust");

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                branchwise_locker_status_enquiry.AccountAsync task=new branchwise_locker_status_enquiry.AccountAsync();
                task.execute();
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
                Intent intent=new Intent(branchwise_locker_status_enquiry.this,HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    public class AccountAsync extends AsyncTask<URL,Void,String> {
        String pro;
        String jsonResponse="";

        ProgressDialog progressDialog=new ProgressDialog(branchwise_locker_status_enquiry.this);
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog.setMessage("Loading...");
            progressDialog.show();
        }
        @Override
        protected String doInBackground(URL... params) {
            URL url=create(Sbi_REQUEST_URL);
            try {
                jsonResponse=makeHttpRequest(url);
            } catch (IOException e) {
                e.printStackTrace();
            }
            pro=extractBankAccount(jsonResponse);
            return pro;
        }

        @Override
        protected void onPostExecute(String account) {

            if(pro==null){
                Toast.makeText(branchwise_locker_status_enquiry.this,"Invalid Credencials",Toast.LENGTH_SHORT).show();
        progressDialog.dismiss();
                return;
            }

            Toast.makeText(branchwise_locker_status_enquiry.this,pro,Toast.LENGTH_SHORT).show();
            //   Intent intent=new Intent(loan_enquiry_details.this,transaction_status.class);
            //  intent.putExtra("status",pro);
            // startActivity(intent);
        progressDialog.dismiss();
        }

    }

    public URL create(String str){
        URL url=null;
        try {
            url=new URL(str);
        } catch (MalformedURLException e) {
            return null;
        }
        return url;
    }
    public String makeHttpRequest(URL url) throws IOException {
        String jsonResponse="";
        HttpURLConnection httpURLConnection=null;
        InputStream inputStream=null;
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("LockerType",locker_type.getText().toString());
            jsonObject.put("BranchCode",branch.getText().toString());

            httpURLConnection=(HttpURLConnection)url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("apikey",getString(R.string.sbi_api));
            httpURLConnection.setRequestProperty("Content-Type","application/json;chatset=utf-8");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setReadTimeout(1000000);
            httpURLConnection.setConnectTimeout(15000);
            httpURLConnection.connect();

            OutputStream wr=new BufferedOutputStream(httpURLConnection.getOutputStream());
            wr.write(jsonObject.toString().getBytes());
            wr.flush();

            inputStream=httpURLConnection.getInputStream();
            jsonResponse=readfromstream(inputStream);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
            if(inputStream!=null){
                inputStream.close();
            }

        }
        return jsonResponse;
    }

    private String readfromstream(InputStream inputStream) throws IOException {
        StringBuilder output=new StringBuilder();
        if(inputStream!=null){
            InputStreamReader inputstreamReader=new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader=new BufferedReader(inputstreamReader);
            String line=reader.readLine();
            while (line!=null){
                output.append(line);
                line=reader.readLine();
            }
        }
        return output.toString();
    }

    public String extractBankAccount(String json) {
        ArrayList<transaction_list_item> arrayList= new ArrayList<transaction_list_item>();
        String type[] = new String[1];
        type[0]="";
        try {
            JSONObject object = new JSONObject(json);
            JSONArray array=object.getJSONArray("Lockerdetails");

           // for (int i=0;i<array.length();i++) {
                JSONObject jsonObject=array.getJSONObject(0);
                type[0] = jsonObject.getString("LockerNumber");
            //}
        } catch (JSONException e1) {
            e1.printStackTrace();
        }
        return type[0];
    }
}
