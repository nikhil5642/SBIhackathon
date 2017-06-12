package com.example.nikhil.sbihackathon;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

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

public class Loan_accounts extends AppCompatActivity {

    private static final String Sbi_REQUEST_URL = "http://52.172.213.166:8080/sbi/Account_List/api/EnqBancsAccountsList/";
    private ListView listView;
    private MyAccountList myAccountList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loan_accounts);
        listView=(ListView)findViewById(R.id.list_loan_account);

        Loan_accounts.AccountAsync task=new Loan_accounts.AccountAsync();
        task.execute();


    }
    public class AccountAsync extends AsyncTask<URL,Void,ArrayList<Account_layout>> {
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

    private void set(ArrayList<Account_layout> account) {

        ArrayList<Account_layout> arrayList= account;
        final adapter_account_list adapter=new adapter_account_list(Loan_accounts.this,arrayList);

        ListView listView=(ListView)findViewById(R.id.list_loan_account);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Account_layout current=adapter.getItem(position);
                Intent intent=new Intent(Loan_accounts.this,Account_detail_all.class);
                intent.putExtra("mi",current);
                startActivity(intent);

            }
        });

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

            jsonObject.put("AccountNumber","30001512992" );


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

                if(Account_category.contains("L")){    arrayList.add(new Account_layout(Account_category,
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


    }
}
