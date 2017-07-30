package com.example.nikhil.sbihackathon;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;

public class Enquiry_list extends AppCompatActivity {

    ArrayList<list_item_main> Account_list;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enquiry_list);
        setupActionbar();
        final Intent i=getIntent();

        final Account_layout account=(Account_layout) i.getSerializableExtra("customer");
        final ArrayList<Account_layout> deposit_list=(ArrayList<Account_layout>) i.getSerializableExtra("deposit_acc_list");
        final ArrayList<Account_layout> loan_list=(ArrayList<Account_layout>) i.getSerializableExtra("loan_acc_list");
        final ArrayList<Account_layout> whole_list=(ArrayList<Account_layout>) i.getSerializableExtra("whole_acc_list");

        ArrayList<list_item_main> arrayList= new ArrayList<list_item_main>();
        arrayList.add(new list_item_main(R.mipmap.loan_enquiry,"Loan Enquiry",loan_enquiry.class));
        arrayList.add(new list_item_main(R.mipmap.tds_enquiry,"TDS Enquiry",TDS_enquiry.class));
        arrayList.add(new list_item_main(R.mipmap.locker_enquiry,"Customer Locker Enquiry",customer_locker_enquiry.class));
        arrayList.add(new list_item_main(R.mipmap.bank_wise_locker_enquiry,"Branchwise Locker status Enquiry",branchwise_locker_status_enquiry.class));

        final adapter_listview_main adapter=new adapter_listview_main(Enquiry_list.this,arrayList);

        listView=(ListView)findViewById(R.id.enquiry_list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                list_item_main current=adapter.getItem(position);
                Class s=current.getActivity();
                Intent intent=new Intent(Enquiry_list.this,s);
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
                Intent intent=new Intent(Enquiry_list.this,HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

}
