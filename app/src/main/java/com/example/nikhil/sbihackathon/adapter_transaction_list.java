package com.example.nikhil.sbihackathon;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by nikhil on 13/6/17.
 */

public class adapter_transaction_list extends ArrayAdapter<transaction_list_item> {
    public adapter_transaction_list(Context context, ArrayList<transaction_list_item> arrayList) {
        super(context, 0, arrayList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View list_item_view=convertView;
        if(list_item_view==null){
            list_item_view = LayoutInflater.from(getContext()).inflate(
                    R.layout.transaction_list_item, parent, false);
        }
        transaction_list_item e=getItem(position);
        TextView date =(TextView)list_item_view.findViewById(R.id.transaction_list_item_date);
        TextView detail =(TextView)list_item_view.findViewById(R.id.transaction_list_item_description);
        TextView type =(TextView)list_item_view.findViewById(R.id.transaction_list_item_type);
        TextView amount=(TextView)list_item_view.findViewById(R.id.transaction_list_item_ammount);
        date.setText(e.getDate());
        detail.setText(e.getDetail());
        type.setText(e.getType());
        amount.setText(e.getAmount()+" INR");
        return list_item_view;
    }

}
