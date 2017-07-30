package com.example.nikhil.sbihackathon;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class Account_detail_all extends AppCompatActivity {

    //Bundle bundle=getIntent().getExtras();
   // Account_layout Account=bundle.getParcelable("M");

TextView Acc_no,Account_type,
        Acc_available_balance,
        Account_status,
        Total_balance,
        Approoved_sanction,
        Home_branch,
        Interest_rate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_detail_all);
        Intent intent=getIntent();
        setupActionbar();
        Account_layout Account=(Account_layout) intent.getSerializableExtra("mi");

   if(Account!=null){    Acc_no=(TextView)findViewById(R.id.account_detail_all_Account_no);

                         Acc_no.setText(Account.getAccount_no());

        Account_type=(TextView)findViewById(R.id.account_detail_all_Account_type);
        Account_type.setText(Account.getAccount_type());

        Acc_available_balance=(TextView)findViewById(R.id.account_detail_all_Available_balance);
        Acc_available_balance.setText(Account.getAvailable_balance());

        Account_status=(TextView)findViewById(R.id.account_detail_all_Account_status);
        Account_status.setText(Account.getAccount_status());

        Total_balance=(TextView)findViewById(R.id.account_detail_all_total_balance);
        Total_balance.setText(Account.getTotal_balance());

        Approoved_sanction=(TextView)findViewById(R.id.account_detail_all_Approved_sanction);
        Approoved_sanction.setText(Account.getApproved_sanction_amount());

        Home_branch=(TextView)findViewById(R.id.account_detail_all_Account_branch);
        Home_branch.setText(Account.getHome_branch());

        Interest_rate=(TextView)findViewById(R.id.account_detail_all_Interest_Rate);
        Interest_rate.setText(Account.getInterest_rate());

   }
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
                Intent intent=new Intent(Account_detail_all.this,HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

}
