package com.example.nikhil.sbihackathon;

import android.content.Context;
import android.content.Intent;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;

public class cheque_list extends AppCompatActivity {
    private ListView listView;
    Bundle instance_state;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheque_list);

        setupActionbar();

        final Intent i=getIntent();

        final Account_layout account=(Account_layout) i.getSerializableExtra("customer");
        final ArrayList<Account_layout> deposit_list=(ArrayList<Account_layout>) i.getSerializableExtra("deposit_acc_list");
        final ArrayList<Account_layout> loan_list=(ArrayList<Account_layout>) i.getSerializableExtra("loan_acc_list");
        final ArrayList<Account_layout> whole_list=(ArrayList<Account_layout>) i.getSerializableExtra("whole_acc_list");

        ArrayList<list_item_main> arrayList= new ArrayList<list_item_main>();
        arrayList.add(new list_item_main(R.mipmap.cheque_issuance,"Cheque Issuance",cheque_issueance.class));
        arrayList.add(new list_item_main(R.mipmap.stop_cheque,"Stop Cheque",stop_cheque.class));

        final adapter_listview_main adapter=new adapter_listview_main(cheque_list.this,arrayList);

        listView=(ListView)findViewById(R.id.cheque_list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                list_item_main current=adapter.getItem(position);
                Class s=current.getActivity();
                Intent intent=new Intent(cheque_list.this,s);
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
                Intent intent=new Intent(cheque_list.this,HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }


}

