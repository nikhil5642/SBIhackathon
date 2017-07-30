package com.example.nikhil.sbihackathon.fragment;

import java.util.ArrayList;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nikhil.sbihackathon.R;
import com.example.nikhil.sbihackathon.fragment.ProvidersData;

/***** Adapter class extends with ArrayAdapter ******/
public class CustomAdapter_spin extends ArrayAdapter<String>{
     
    private Context mContext;
    private ArrayList data;
    //public Resources res;
    ProvidersData tempValues=null;
    LayoutInflater inflater;
     
    /*************  CustomAdapter Constructor *****************/
    public CustomAdapter_spin(
                          Context mContext,
                          int textViewResourceId,   
                          ArrayList objects
                         ) 
     {
        super(mContext, textViewResourceId, objects);
         
        /********** Take passed values **********/
         mContext = mContext;
        data     = objects;
       // res      = resLocal;
    
        /***********  Layout inflator to call external xml layout () **********************/
        inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
         
      }
 
    @Override
    public View getDropDownView(int position, View convertView,ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }
 
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }
 
    // This funtion called for each row ( Called data.size() times )
    public View getCustomView(int position, View convertView, ViewGroup parent) {
 
        /********** Inflate spinner_rows.xml file for each row ( Defined below ) ************/
        View row = inflater.inflate(R.layout.spinner_row, parent, false);
         
        /***** Get each Model object from Arraylist ********/
        tempValues = null;
        tempValues = (ProvidersData) data.get(position);
         
        TextView label        = (TextView)row.findViewById(R.id.tv_spinner_text);
    /*    TextView sub          = (TextView)row.findViewById(R.id.sub);
        ImageView companyLogo = (ImageView)row.findViewById(R.id.image);*/
         
        if(position==0){
             
            // Default selected Spinner item 
            label.setText("Please select Provider");
           // sub.setText("");
        }
        else
        {
            // Set values for spinner each row 
            label.setText(tempValues.getProvider_name());
           // sub.setText(tempValues.getUrl());
            /*companyLogo.setImageResource(res.getIdentifier
                                         ("com.androidexample.customspinner:drawable/"
                                          + tempValues.getImage(),null,null));
            */
        }   
 
        return row;
      }
 }