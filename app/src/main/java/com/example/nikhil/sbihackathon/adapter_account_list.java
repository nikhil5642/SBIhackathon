package com.example.nikhil.sbihackathon;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


public class adapter_account_list extends ArrayAdapter<Account_layout> {

    public adapter_account_list(Context context, ArrayList<Account_layout> arrayList) {
        super(context, 0, arrayList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View list_item_view=convertView;
        if(list_item_view==null){
            list_item_view = LayoutInflater.from(getContext()).inflate(
                    R.layout.account_list_layout, parent, false);
        }
        Account_layout e=getItem(position);
        TextView Accontno =(TextView)list_item_view.findViewById(R.id.account_list_layout_Account_no);
        TextView Availbal =(TextView)list_item_view.findViewById(R.id.account_list_layout_available_balance);
        TextView Accounttype =(TextView)list_item_view.findViewById(R.id.account_list_layout_account_type);

        Accontno.setText(e.getAccount_no());
        Availbal.setText(e.getAvailable_balance());
        Accounttype.setText(e.getAccount_type());
        return list_item_view;
    }

}
