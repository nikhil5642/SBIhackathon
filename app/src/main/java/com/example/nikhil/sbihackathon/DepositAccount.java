package com.example.nikhil.sbihackathon;

import android.accounts.Account;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class DepositAccount extends AppCompatActivity {

   // private static final String Sbi_REQUEST_URL = "http://52.172.213.166:8080/sbi/Account_List/api/EnqBancsAccountsList/";
    private ListView listView;
    private MyAccountList myAccountList;
    private Account_layout account;
    private ArrayList<Account_layout> deposit_list,whole_list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deposit_account);
        setupActionbar();
        listView=(ListView)findViewById(R.id.list_deposit_account);
        Intent i=getIntent();
        account=(Account_layout) i.getSerializableExtra("cust");
         deposit_list=(ArrayList<Account_layout>)i.getSerializableExtra("deposit_acc_list");
      //  AccountAsync task=new AccountAsync();
        //task.execute();

        set(deposit_list);

    }

    private void set(ArrayList<Account_layout> account) {

        ArrayList<Account_layout> arrayList= account;
        final adapter_account_list adapter=new adapter_account_list(DepositAccount.this,arrayList);

        ListView listView=(ListView)findViewById(R.id.list_deposit_account);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Account_layout current=adapter.getItem(position);
                Intent intent=new Intent(DepositAccount.this,Account_detail_all.class);
                intent.putExtra("mi",current);
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
                Intent intent=new Intent(DepositAccount.this,HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

  /*  public class AccountAsync extends AsyncTask<URL,Void,ArrayList<Account_layout>> {
        ArrayList<Account_layout> Account;
        String jsonResponse="";

        @Override
        protected ArrayList<Account_layout> doInBackground(URL... params) {
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
        protected void onPostExecute(ArrayList<Account_layout> account) {

            if(Account==null){
                return;            }
            Log.e("Problem","sjlajdf;ald");

            set(Account);
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

            jsonObject.put("AccountNumber",account.getAccount_no().substring(6,17));

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

    public ArrayList<Account_layout> extractBankAccount(String json) {
        ArrayList<Account_layout> arrayList= new ArrayList<Account_layout>();
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray jsonArray = jsonObject.getJSONArray("ac_details");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object=jsonArray.getJSONObject(i);

                String Account_category=object.getString("AccountCategory");
                String Acc_no= object.getString("AccountNumber");
                String Acc_available_balance=object.getString("AvailableBalance");
                String Account_type =object.getString("AccountType");
                String Account_status=object.getString("AccountStatus");
                String Total_balance=object.getString("TotalBalance");
                String Home_branch=object.getString("HomeBranch");
                String Interest_rate=object.getString("InterestRate");
                String Approoved_sanction=object.getString("ApprovedSanctionedAmount");

            if(Account_category.contains("D")){    arrayList.add(new Account_layout(Account_category,
                        Acc_no,Account_type,
                        Acc_available_balance,
                        Account_status,
                        Total_balance,
                        Approoved_sanction,
                        Home_branch,
                        Interest_rate));
            }}
        } catch (JSONException e) {
        }
        return arrayList;


    }*/

}
