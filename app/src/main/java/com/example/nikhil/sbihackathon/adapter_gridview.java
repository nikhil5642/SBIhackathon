package com.example.nikhil.sbihackathon;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by nikhil on 12/6/17.
 */

public class adapter_gridview extends BaseAdapter {
    private Context context;
    private final ArrayList<grid_item> gridValues;

    public adapter_gridview(Context context, ArrayList<grid_item> gridValues) {
        this.context = context;
        this.gridValues = gridValues;
    }

    @Override
    public int getCount() {
        return gridValues.size();
    }

    @Override
    public grid_item getItem(int position) {
        return gridValues.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View gridView;

        if (convertView == null) {

            gridView = new View(context);

            // get layout from grid_item.xml ( Defined Below )

            gridView = inflater.inflate(R.layout.grid_item_layout,null);
            ImageView imageView = (ImageView) gridView.findViewById(R.id.grid_item_image);
            TextView textView = (TextView) gridView.findViewById(R.id.grid_item_text);

            // set value into textview\\
            grid_item gridItem=getItem(position);
            textView.setText(gridItem.getName());
            imageView.setImageResource(gridItem.getImage());

        }else{
            gridView=(View)convertView;
        }

            return gridView;
    }
}
