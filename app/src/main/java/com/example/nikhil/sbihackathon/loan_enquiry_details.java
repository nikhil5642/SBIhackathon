package com.example.nikhil.sbihackathon;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
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

public class loan_enquiry_details extends AppCompatActivity {
    String Sbi_REQUEST_URL="http://52.172.213.166:8080/sbi/Loan_Enquiry/api/EnqLoan";
    String from_acc_no;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loan_enquiry_details);
        setupActionbar();
        Intent i=getIntent();
        from_acc_no=i.getExtras().getString("acc");

        loan_enquiry_details.AccountAsync accountAsync=new loan_enquiry_details.AccountAsync();
        accountAsync.execute();
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
                Intent intent=new Intent(loan_enquiry_details.this,HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    public class AccountAsync extends AsyncTask<URL,Void,String> {
        String pro;
        String jsonResponse="";

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

            /*if(pro==null||!pro.contains("SUCCESS")){
                Toast.makeText(loan_enquiry_details.this,"Invalid Credencials",Toast.LENGTH_SHORT).show();
                return;
            }*/

            Toast.makeText(loan_enquiry_details.this,pro,Toast.LENGTH_SHORT).show();
         //   Intent intent=new Intent(loan_enquiry_details.this,transaction_status.class);
          //  intent.putExtra("status",pro);
           // startActivity(intent);
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
            jsonObject.put("AccountNumber",from_acc_no);

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
        String type="";
        try {
            JSONObject object = new JSONObject(json);
            type=object.getString("ErrorDescription");

        } catch (JSONException e1) {
            e1.printStackTrace();
        }
        return type;
    }

}
