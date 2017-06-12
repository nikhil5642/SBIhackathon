package com.example.nikhil.sbihackathon;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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


        ArrayList<list_item_main> arrayList= new ArrayList<list_item_main>();

        arrayList.add(new list_item_main(R.drawable.icon,"Balance Enqiry",BalanceEnquiry.class));
        arrayList.add(new list_item_main(R.drawable.icon,"Mini Statement",MiniStatement.class));
        arrayList.add(new list_item_main(R.drawable.icon,"Transaction Accounts",TransactionAccounts.class));
        arrayList.add(new list_item_main(R.drawable.icon,"Loan Accounts",Loan_accounts.class));
        arrayList.add(new list_item_main(R.drawable.icon,"Deposit Accounts",DepositAccount.class));
        arrayList.add(new list_item_main(R.drawable.icon,"My Passbook",MyPassbook.class));

        final adapter_listview_main adapter=new adapter_listview_main(MyAccountList.this,arrayList);

        ListView listView=(ListView)findViewById(R.id.my_account_list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                list_item_main current=adapter.getItem(position);
                Class s=current.getActivity();
                Intent intent=new Intent(MyAccountList.this,s);
                startActivity(intent);
            }
        });
    }


}
