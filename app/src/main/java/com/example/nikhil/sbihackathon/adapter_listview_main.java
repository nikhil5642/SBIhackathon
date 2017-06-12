package com.example.nikhil.sbihackathon;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by nikhil on 11/6/17.
 */

public class adapter_listview_main extends ArrayAdapter<list_item_main> {
    public adapter_listview_main(Context context, ArrayList<list_item_main> arrayList) {
        super(context, 0, arrayList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View list_item_view=convertView;
        if(list_item_view==null){
            list_item_view = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item_main, parent, false);
        }
        list_item_main e=getItem(position);
        TextView title =(TextView)list_item_view.findViewById(R.id.title_list_main);
        ImageView img=(ImageView) list_item_view.findViewById(R.id.icon_list_main);

        title.setText(e.getTitle());
        img.setImageResource(e.getIcon());

        return list_item_view;
    }

}
