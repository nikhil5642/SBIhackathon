package com.example.nikhil.sbihackathon;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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


import com.example.nikhil.sbihackathon.R;

import org.apache.http.HttpResponse;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

import static java.net.Proxy.Type.HTTP;

public class MyAccountList extends AppCompatActivity {

    ArrayList<list_item_main> Account_list;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account_list);
        listView=(ListView)findViewById(R.id.my_account_list);
         setupActionbar();
        final Intent i=getIntent();

        final Account_layout account=(Account_layout) i.getSerializableExtra("customer");
        final ArrayList<Account_layout> deposit_list=(ArrayList<Account_layout>) i.getSerializableExtra("deposit_acc_list");
        final ArrayList<Account_layout> loan_list=(ArrayList<Account_layout>) i.getSerializableExtra("loan_acc_list");
        final ArrayList<Account_layout> whole_list=(ArrayList<Account_layout>) i.getSerializableExtra("whole_acc_list");

        ArrayList<list_item_main> arrayList= new ArrayList<list_item_main>();
        arrayList.add(new list_item_main(R.mipmap.benificiary_account,"Benificiary Account",Benificiay_account.class));
        arrayList.add(new list_item_main(R.mipmap.loan_acc_list,"Loan Accounts",Loan_accounts.class));
        arrayList.add(new list_item_main(R.mipmap.deposit_acc_list,"Deposit Accounts",DepositAccount.class));
        arrayList.add(new list_item_main(R.mipmap.passbook,"My Passbook",MyPassbook.class));

        final adapter_listview_main adapter=new adapter_listview_main(MyAccountList.this,arrayList);

        ListView listView=(ListView)findViewById(R.id.my_account_list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                list_item_main current=adapter.getItem(position);
                Class s=current.getActivity();
                Intent intent=new Intent(MyAccountList.this,s);
                intent.putExtra("cust",account);
                intent.putExtra("whole_acc_list",whole_list);
                intent.putExtra("deposit_acc_list",deposit_list);
                intent.putExtra("loan_acc_list",loan_list);
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
                Intent intent=new Intent(MyAccountList.this,HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }



}
