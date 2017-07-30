package com.example.nikhil.sbihackathon;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
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

public class fund_transfer_customer_to_customer extends AppCompatActivity {
    final String Sbi_REQUEST_URL="http://52.172.213.166:8080/sbi/C2C/api/TxnCustomerToCustomer";
    EditText branch,to,amount;
    Spinner from;
    ArrayList<Account_layout> accounts;
    Account_layout customer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fund_transfer_customer_to_customer);
        setupActionbar();
        Intent intent=getIntent();
        accounts=(ArrayList<Account_layout>)intent.getSerializableExtra("deposit_acc_list");
        customer=(Account_layout)intent.getSerializableExtra("cust");

        branch=(EditText)findViewById(R.id.fund_transfer_customer_to_customer_branch);
        to=(EditText)findViewById(R.id.fund_transfer_customer_to_customer_to_acc_no);
        amount=(EditText)findViewById(R.id.fund_transfer_customer_to_customer_amount);

        branch.setText(customer.getHome_branch());
        from= (Spinner) findViewById(R.id.fund_transfer_customer_to_customer_from_acc_no);
        String[] strings=new String[accounts.size()];
        for(int i=0;i<accounts.size();i++){
            strings[i]=accounts.get(i).getAccount_no();
        }
        ArrayAdapter<String> adapter0 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, strings);
        from.setAdapter(adapter0);

     }
    private void setupActionbar(){
        LayoutInflater inflater=(LayoutInflater)this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v=inflater.inflate(R.layout.actionbar,null);

        android.support.v7.app.ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        //actionBar.setBackgroundDrawable(getDrawable(R.drawable.sbi_logo));
        //actionBar.setHomeButtonEnabled(true);
        //  actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setCustomView(v);
        ImageView home=(ImageView)findViewById(R.id.home_btn);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(fund_transfer_customer_to_customer.this,HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    public void Submit(View view){
        final CharSequence[] items = {"From: "+from.getSelectedItem().toString(), "To: "+to.getText().toString(), "Amount: "+amount.getText().toString(),"Dismiss","Continue"};
        AlertDialog.Builder builder = new AlertDialog.Builder(fund_transfer_customer_to_customer.this);
        builder.setTitle("Are you sure?");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Dismiss")) {
                    dialog.dismiss();
                } else if (items[item].equals("Continue")) {
                   fund_transfer_customer_to_customer.AccountAsync accountAsync=new fund_transfer_customer_to_customer.AccountAsync();
                    accountAsync.execute();
                }
            }
        });
        builder.show();

    }

    public class AccountAsync extends AsyncTask<URL,Void,String> {
       String pro;
        String jsonResponse="";
        ProgressDialog progressDialog=new ProgressDialog(fund_transfer_customer_to_customer.this);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog.setMessage("Processing...");
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

            if(account==null||!account.contains("SUCCESS")){
                Toast.makeText(fund_transfer_customer_to_customer.this,"Invalid Credencials",Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                return;
            }

            Toast.makeText(fund_transfer_customer_to_customer.this,account,Toast.LENGTH_SHORT).show();

               Intent intent=new Intent(fund_transfer_customer_to_customer.this,transaction_status.class);
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
            jsonObject.put("BranchCode",customer.getHome_branch());
            jsonObject.put("DebitAccount",to.getText().toString());
            jsonObject.put("CreditAccount",from.getSelectedItem().toString());
            jsonObject.put("TransactionAmount",amount.getText().toString());

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
