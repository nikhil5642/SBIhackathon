package com.example.nikhil.sbihackathon;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

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
import java.util.Calendar;

public class MyPassbook extends AppCompatActivity {

    Spinner acc_no;
    EditText trans_no,from_ammount,to_ammout,from_date,to_date;
    Button Cancel,Submit;
    ImageView from_picker,to_picker;
ArrayList<Account_layout> acounts;
String Sbi_REQUEST_URL="http://52.172.213.166:8080/sbi/Detail/api/EnqINBAccountStatement";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_passbook);
        setupActionbar();
        Intent intent=getIntent();

        acounts=(ArrayList<Account_layout>)intent.getSerializableExtra("deposit_acc_list");
Log.e("problem",acounts.get(0).getAccount_no());
       acc_no=(Spinner) findViewById(R.id.my_passbook_acc_no);

        trans_no=(EditText)findViewById(R.id.my_passbook_trans_no);
       from_ammount=(EditText)findViewById(R.id.my_passbook_from_ammout);
       to_ammout=(EditText)findViewById(R.id.my_passbook_to_ammout);
       from_date=(EditText)findViewById(R.id.my_passbook_from_date);
        to_date=(EditText)findViewById(R.id.my_passbook_to_date);

        from_picker=(ImageView)findViewById(R.id.my_passbook_from_date_picker);
        to_picker=(ImageView)findViewById(R.id.my_passbook_to_date_picker);

        Submit=(Button)findViewById(R.id.my_passbook_btn_submit);

        String[] strings=new String[acounts.size()];
for(int i=0;i<acounts.size();i++){
    strings[i]=acounts.get(i).getAccount_no();

        }
        ArrayAdapter<String> adapter0 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, strings);
        acc_no.setAdapter(adapter0);

    from_picker.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Calendar calendar = Calendar.getInstance();
            int year,month,date;
            year=calendar.get(Calendar.YEAR);
            month=calendar.get(Calendar.MONTH);
            date=calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog dpd=new DatePickerDialog(MyPassbook.this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    from_date.setText(dayOfMonth+"/"+month+"/"+year);
                }
            },year,month,date);
            dpd.show();
        }
    });
        to_picker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year,month,date;
                year=calendar.get(Calendar.YEAR);
                month=calendar.get(Calendar.MONTH);
                date=calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dpd=new DatePickerDialog(MyPassbook.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        to_date.setText(dayOfMonth+"/"+month+"/"+year);
                    }
                },year,month,date);
                dpd.show();
            }
        });

        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             MyPassbook.AccountAsync accountAsync=new MyPassbook.AccountAsync();
                accountAsync.execute();
            }
        });



    }
 public class AccountAsync extends AsyncTask<URL,Void,ArrayList<transaction_list_item>> {
        ArrayList<transaction_list_item> Account;
        String jsonResponse="";

        @Override
        protected ArrayList<transaction_list_item> doInBackground(URL... params) {
            URL url=create(Sbi_REQUEST_URL);
            try {
                jsonResponse=makeHttpRequest(url);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Account=extractBankAccount(jsonResponse);
            return Account;
        }

        @Override
        protected void onPostExecute(ArrayList<transaction_list_item> account) {

            if(account==null){
                return;
            }

            Intent intent=new Intent(MyPassbook.this,Transaction_List.class);
            intent.putExtra("list",Account);
            startActivity(intent);
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

            jsonObject.put("AccountNumber",acc_no.getSelectedItem().toString().substring(6,17));
            jsonObject.put("FromAmount",from_ammount.getText().toString());
            jsonObject.put("FromDate",from_date.getText().toString());
            jsonObject.put("ToAmount",to_ammout.getText().toString());
            jsonObject.put("ToDate",to_date.getText().toString());
            jsonObject.put("TransactionNumber",trans_no.getText().toString());

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

    public ArrayList<transaction_list_item> extractBankAccount(String json) {
        ArrayList<transaction_list_item> arrayList= new ArrayList<transaction_list_item>();
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray jsonArray = jsonObject.getJSONArray("StatementDetails");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object=jsonArray.getJSONObject(i);
                String Amount=object.getString("Amount");
                String detail= object.getString("Narration");
                String date=object.getString("ValueDate");
                String type =object.getString("Statement");

                arrayList.add(new transaction_list_item(date,detail,type,Amount));
            }
        } catch (JSONException e) {
        }
        return arrayList;


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
                Intent intent=new Intent(MyPassbook.this,HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

}
