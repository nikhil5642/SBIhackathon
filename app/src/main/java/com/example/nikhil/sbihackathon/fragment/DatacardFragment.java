package com.asmobisoft.coffer.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.asmobisoft.coffer.R;

import java.util.ArrayList;
import java.util.List;

public class DatacardFragment extends Fragment {


    private LinearLayout llTab2;
    private View vData;
    private LinearLayout llTab2View;
    private Spinner spnrData;
    private RadioGroup radioDataGroup;
    private RadioButton radioDataButton;


    public DatacardFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_datacard_recharge, container, false);


        llTab2View = (LinearLayout)v.findViewById(R.id.ll_tab2_view);
        spnrData = (Spinner) v.findViewById(R.id.spnr_oprator_data);

        radioDataGroup=(RadioGroup)v.findViewById(R.id.radio_data);

        int selectedIdData=radioDataGroup.getCheckedRadioButtonId();
        radioDataButton=(RadioButton)v.findViewById(selectedIdData);

        // Inflate the layout for this fragment
        List<String> list1 = new ArrayList<String>();
        list1.add("Airtel");
        list1.add("Relince DTH");
        list1.add("TATA SKY");
        ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, list1);
        dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnrData.setAdapter(dataAdapter1);

        return v;
    }


}
