package com.example.nikhil.sbihackathon;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

        Account_layout Account=intent.getParcelableExtra("mi");

   if(Account!=null){    Acc_no=(TextView)findViewById(R.id.account_detail_all_Account_no);
       Acc_no.setText(Account.toString());

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
}
