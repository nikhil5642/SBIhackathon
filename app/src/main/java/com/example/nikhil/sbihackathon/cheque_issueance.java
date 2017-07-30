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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EdgeEffect;
import android.widget.EditText;
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

public class cheque_issueance extends AppCompatActivity {

    ArrayList<Account_layout> accounts;
    Account_layout customer;
    String Sbi_REQUEST_URL="http://52.172.213.166:8080/sbi/Cheque_Issue/chequeBook/issuance";
    Spinner acc_no;
    EditText pageno,bookno,branch;
    Button submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheque_issueance);
        setupActionbar();
        final Intent intent1=getIntent();
        accounts=(ArrayList<Account_layout>)intent1.getSerializableExtra("deposit_acc_list");
        customer=(Account_layout)intent1.getSerializableExtra("cust");

        submit=(Button)findViewById(R.id.cheque_issuance_submit);
        acc_no=(Spinner)findViewById(R.id.cheque_issuance_acc_no);
        pageno=(EditText)findViewById(R.id.cheque_issuance_checkperbook);
        bookno=(EditText)findViewById(R.id.cheque_issuance_book_no);
        branch=(EditText)findViewById(R.id.cheque_issuance_pickupbranach);

        int length=(int)accounts.size()+1;
        String[] strings=new String[length];
        strings[0]="Select Account";
        for(int i=0;i<(length-1);i++){
            strings[i+1]=accounts.get(i).getAccount_no();
        }
        final ArrayAdapter<String> adapter0 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, strings);
        acc_no.setAdapter(adapter0);

        acc_no.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String s=acc_no.getSelectedItem().toString();
                if(s.contains("Select")){
                    branch.setText("");
                    return;
                }
                branch.setText(customer.getHome_branch());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                branch.setText("");
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cheque_issueance.AccountAsync task=new cheque_issueance.AccountAsync();
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
                Intent intent=new Intent(cheque_issueance.this,HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }


    public class AccountAsync extends AsyncTask<URL,Void,String> {
        String pro;
        String jsonResponse="";

        ProgressDialog progressDialog=new ProgressDialog(cheque_issueance.this);
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

            if(pro.contains("cool")){
                Toast.makeText(cheque_issueance.this,"Invalid Date",Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                return;
            }

            Toast.makeText(cheque_issueance.this,account,Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(cheque_issueance.this,transaction_status.class);
            intent.putExtra("status",pro);
            startActivity(intent);
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
            jsonObject.put("ACCOUNT_NUMBER",acc_no.getSelectedItem().toString());
            jsonObject.put("CHEQUE_INSTRUMENT_TYPE","31");
            jsonObject.put("SUB_CATEGORY","02");
            jsonObject.put("CHEQUE_PER_BOOK",pageno);
            jsonObject.put("PICK_UP_BRANCH",branch);
            jsonObject.put("NO_OF_BOOKS",bookno);
            jsonObject.put("SAN_TYPE","0");
            jsonObject.put("CHEQUE_BOOK_AMOUNT","12000000002000000");
            jsonObject.put("IS_P_SEGMENT_ACCOUNT"," ");
            httpURLConnection=(HttpURLConnection)url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("apikey",getString(R.string.sbi_api));
            httpURLConnection.setRequestProperty("Content-Type","application/json;chatset=utf-8");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setReadTimeout(10000);
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
        String type="cool";
        try {
            JSONObject object = new JSONObject(json);
            type=object.getString("JOURNAL_NO");
        } catch (JSONException e1) {
               e1.printStackTrace();
        }
        return type;
    }
}
