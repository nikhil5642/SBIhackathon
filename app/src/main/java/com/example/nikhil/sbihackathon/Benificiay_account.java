package com.example.nikhil.sbihackathon;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;

public class Benificiay_account extends AppCompatActivity {
    private ListView listView;
    private Account_layout account;
    private ArrayList<Account_layout> whole_list;
    private ArrayList<Account_layout> benificiary_list=new ArrayList<Account_layout>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_benificiay_account);
        setupActionbar();
        Intent i=getIntent();
        account=(Account_layout) i.getSerializableExtra("cust");
        whole_list=(ArrayList<Account_layout>)i.getSerializableExtra("whole_acc_list");
        //  AccountAsync task=new AccountAsync();
        //task.execute();
      benificiary_list.add(whole_list.get(0));
        set(benificiary_list);

    }

    private void set(ArrayList<Account_layout> account) {

        ArrayList<Account_layout> arrayList= account;
        final adapter_account_list adapter=new adapter_account_list(Benificiay_account.this,arrayList);

        ListView listView=(ListView)findViewById(R.id.list_benificiary_account);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Account_layout current=adapter.getItem(position);
                Intent intent=new Intent(Benificiay_account.this,Account_detail_all.class);
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
                Intent intent=new Intent(Benificiay_account.this,HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

}
