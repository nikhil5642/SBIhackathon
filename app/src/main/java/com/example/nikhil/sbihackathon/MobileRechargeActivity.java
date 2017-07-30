package com.example.nikhil.sbihackathon;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.Spinner;

import com.example.nikhil.sbihackathon.fragment.CustomAdapter;
import com.example.nikhil.sbihackathon.fragment.CustomAdapter_spin;
import com.example.nikhil.sbihackathon.fragment.MobileFragment;
import com.example.nikhil.sbihackathon.fragment.ProvidersData;

import org.json.JSONArray;
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

public class MobileRechargeActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Spinner spnrMobile;
    public static final String ALL_PROVIDERS = "https://www.pay2all.in/web-api/get-provider?api_token=lPDJRHIy6Fhe4TOEOJC8x8e3B7WiW2oTuooMvATFCUSk8AWi1tXxMxuM8Cgz";
    ArrayList<ProvidersData> providerList = new ArrayList<ProvidersData>();
    private CustomAdapter_spin mSpinnerAdapeter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_money_transfer);
         setupActionbar();
/*        ArrayList<list_item_main> arrayList= new ArrayList<list_item_main>();
        arrayList.add(new list_item_main(R.mipmap.benificiary_account,"Mobile",MobileActivity.class));
        arrayList.add(new list_item_main(R.mipmap.loan_acc_list,"dth",dth.class));
        arrayList.add(new list_item_main(R.mipmap.deposit_acc_list,"datacard",datacard.class));

        final adapter_listview_main adapter=new adapter_listview_main(MobileRechargeActivity.this,arrayList);

        ListView listView=(ListView)findViewById(R.id.recharge_list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                list_item_main current=adapter.getItem(position);
                Class s=current.getActivity();
                Intent intent=new Intent(MobileRechargeActivity.this,s);
                startActivity(intent);
            }
        });*/
        // Set the content of the activity to use the activity_main.xml layout file
        spnrMobile = (Spinner)findViewById(R.id.spnr_oprator);
        setContentView(R.layout.activity_money_transfer);

        // Find the view pager that will allow the user to swipe between fragments
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);

        // Create an adapter that knows which fragment should be shown on each page
        CustomAdapter adapter = new CustomAdapter(getSupportFragmentManager());

        // Set the adapter onto the view pager
        viewPager.setAdapter(adapter);

        // Give the TabLayout the ViewPager
        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);



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
                Intent intent=new Intent(MobileRechargeActivity.this,HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }



}
