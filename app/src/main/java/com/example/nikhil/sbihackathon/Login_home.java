package com.example.nikhil.sbihackathon;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import android.os.AsyncTask;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;

/**
 * A login screen that offers login via email/password.
 */
public class Login_home extends AppCompatActivity {

    private static final int REQUEST_READ_CONTACTS = 0;
    private String sent_otp_url="https://2factor.in/API/V1/77146786-5325-11e7-94da-0200cd936042/SMS/";
    private String check_otp_url="https://2factor.in/API/V1/77146786-5325-11e7-94da-0200cd936042/SMS/VERIFY/";
    TextView msg;
    private EditText mOtp,mMobile;
    private Button otp,login,another;
    private static ViewPager mPager;
    private static int currentPage = 0;
    private String session_id,verify;
    private static final Integer[] Hackathaon =
            {R.drawable.sbi,
                    R.drawable.ncip,
                    R.drawable.microsoft,
                    R.drawable.primechain,
                    R.drawable.ca,
                    R.drawable.vodafone,
                    R.drawable.tata,
                    R.drawable.credit_watch};
    private ArrayList<Integer> HackathaonArray = new ArrayList<Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_home);
        // Set up the login form.
        setupActionbar();

        mMobile = (EditText)findViewById(R.id.login_mobile_no);
        otp=(Button)findViewById(R.id.btn_send_otp);

        mOtp=(EditText)findViewById(R.id.login_otp);
        msg=(TextView)findViewById(R.id.otp_mesg);
        login=(Button)findViewById(R.id.button_login);
        another=(Button)findViewById(R.id.other_);

        ImageView home=(ImageView)findViewById(R.id.home_btn);

        home.setVisibility(View.INVISIBLE);

        init();

        otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Login_home.otp_AccountAsync task=new Login_home.otp_AccountAsync();
                task.execute();

            }
        });

    login.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Login_home.cont_AccountAsync task2=new Login_home.cont_AccountAsync();
            task2.execute();
        }
    });

        another.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOtp.setVisibility(View.INVISIBLE);
                msg.setVisibility(View.INVISIBLE);
                login.setVisibility(View.INVISIBLE);
                another.setVisibility(View.INVISIBLE);
                otp.setEnabled(true);
                login.setEnabled(false);

            }
        });
    }
    private void setupActionbar(){
        LayoutInflater inflater=(LayoutInflater)this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v=inflater.inflate(R.layout.actionbar,null);

        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        //actionBar.setBackgroundDrawable(getDrawable(R.drawable.sbi_logo));
       actionBar.setHomeButtonEnabled(true);
        //actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setCustomView(v);

    }
    private void init() {
        for(int i=0;i<Hackathaon.length;i++)
            HackathaonArray.add(Hackathaon[i]);

        mPager = (ViewPager) findViewById(R.id.viewPager_login);
        mPager.setAdapter(new adapter_slider(Login_home.this,HackathaonArray));
        CircleIndicator indicator = (CircleIndicator) findViewById(R.id.circle_indicator_login);
        indicator.setViewPager(mPager);
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == Hackathaon.length) {
                    currentPage = 0;
                }
                mPager.setCurrentItem(currentPage++, true);
            }
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        },10000, 10000);
    }

    public class otp_AccountAsync extends AsyncTask<Object, Object, Void> {
        String jsonResponse="";
        ProgressDialog progressDialog=new ProgressDialog(Login_home.this);
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setMessage("Sending...");
            progressDialog.show();
        }
        @Override
        protected Void doInBackground(Object... params) {
            URL url=create(sent_otp_url+mMobile.getText().toString()+"/AUTOGEN");
            try {
                jsonResponse=makeHttpRequest(url);
            } catch (IOException e) {
                e.printStackTrace();
            }
            session_id=extract_data(jsonResponse);
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if(session_id!=null){
                mOtp.setVisibility(View.VISIBLE);
                msg.setVisibility(View.VISIBLE);
                login.setVisibility(View.VISIBLE);
                another.setVisibility(View.VISIBLE);
                otp.setEnabled(false);
                login.setEnabled(true);
                progressDialog.dismiss();
               return;
            }
            Toast.makeText(Login_home.this,"ENTER A VALID MOBILE NO.",Toast.LENGTH_LONG).show();
            progressDialog.dismiss();
        }
    }


    public class cont_AccountAsync extends AsyncTask<URL,Void,Void> {
        String jsonResponse="";
        @Override
        protected Void doInBackground(URL... params) {
            URL url=create(check_otp_url+session_id+"/"+mOtp.getText().toString());
            try {
                jsonResponse=makeHttpRequest(url);
            } catch (IOException e) {
                e.printStackTrace();
            }
            verify=extract_data(jsonResponse);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        if(verify!=null) {

            Intent intent=new Intent(Login_home.this,HomeActivity.class);
            startActivity(intent);
            Toast.makeText(Login_home.this,"LOGIN SUCCESSFUL",Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        Toast.makeText(Login_home.this,"OTP IS NOT VALID",Toast.LENGTH_LONG).show();

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
            httpURLConnection=(HttpURLConnection)url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setRequestProperty("Content-Type","application/json;chatset=utf-8");
            httpURLConnection.setReadTimeout(1000000);
            httpURLConnection.setConnectTimeout(15000);
            httpURLConnection.connect();

            inputStream=httpURLConnection.getInputStream();
            jsonResponse=readfromstream(inputStream);

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
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

    public String extract_data(String json) {
        Account_layout my_account = null;
        String status="",detail=null;
        try {
            JSONObject object = new JSONObject(json);

            status=object.getString("Status");

            if(status.contains("Success")) {
            detail=object.getString("Details");
            }


        } catch (JSONException e) {
        }
        return detail;
    }

}



