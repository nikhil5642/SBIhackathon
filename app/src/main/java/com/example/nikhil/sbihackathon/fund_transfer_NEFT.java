package com.example.nikhil.sbihackathon;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SnapHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import java.util.ArrayList;

public class fund_transfer_NEFT extends AppCompatActivity {
    ArrayList<Account_layout> accounts;
    Account_layout customer;
    EditText from_name,to_name,to_acc_no,amount,from_ifsc,to_ifsc,mob_no;
    Spinner from_acc_no;
    Button cont;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fund_transfer__neft);
        setupActionbar();
        final Intent intent1=getIntent();
        accounts=(ArrayList<Account_layout>)intent1.getSerializableExtra("deposit_acc_list");
        customer=(Account_layout)intent1.getSerializableExtra("cust");

        from_acc_no=(Spinner)findViewById(R.id.fund_transfer_neft_from_acc_no);

        from_name=(EditText)findViewById(R.id.fund_transfer_neft_from_name);
        to_name=(EditText)findViewById(R.id.fund_transfer_neft_to_name);
        to_acc_no=(EditText)findViewById(R.id.fund_transfer_neft_to_acc_no);
        amount=(EditText)findViewById(R.id.fund_transfer_neft_amount);
        from_ifsc=(EditText)findViewById(R.id.fund_transfer_neft_from_ifsc);
        to_ifsc=(EditText)findViewById(R.id.fund_transfer_neft_to_ifsc);
        mob_no=(EditText)findViewById(R.id.fund_transfer_neft_from_mob);

        cont=(Button)findViewById(R.id.fund_transfer_neft_submit);
       int length=(int)accounts.size()+1;
        String[] strings=new String[length];
        strings[0]="Select Account";
        for(int i=0;i<(length-1);i++){
            strings[i+1]=accounts.get(i).getAccount_no();
        }
        final ArrayAdapter<String> adapter0 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, strings);
        from_acc_no.setAdapter(adapter0);

        from_acc_no.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String s=from_acc_no.getSelectedItem().toString();
                if(s.contains("Select")){
                    from_name.setText("");
                    return;
                }
                from_name.setText(customer.getCustomer_name());

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        cont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              Intent intent=new Intent(fund_transfer_NEFT.this,confirm_fund_transfer_NEFT.class);
              intent.putExtra("from_name",from_name.getText().toString());
              intent.putExtra("to_name",to_name.getText().toString());
              intent.putExtra("to_acc_no",to_acc_no.getText().toString());
              intent.putExtra("amount",amount.getText().toString());
              intent.putExtra("from_ifsc",from_ifsc.getText().toString());
              intent.putExtra("to_ifsc",to_ifsc.getText().toString());
              intent.putExtra("mob_no",mob_no.getText().toString());
              intent.putExtra("from_acc_no",from_acc_no.getSelectedItem().toString());
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
                Intent intent=new Intent(fund_transfer_NEFT.this,HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

}
