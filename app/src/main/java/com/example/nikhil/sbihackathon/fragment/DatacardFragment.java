package com.example.nikhil.sbihackathon.fragment;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.nikhil.sbihackathon.MobileRechargeActivity;
import com.example.nikhil.sbihackathon.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class DatacardFragment extends Fragment {


    private Spinner spnrData;
    public static final String ALL_PROVIDERS = "https://www.pay2all.in/web-api/get-provider?api_token=lPDJRHIy6Fhe4TOEOJC8x8e3B7WiW2oTuooMvATFCUSk8AWi1tXxMxuM8Cgz";
    ArrayList<ProvidersData> data_List = new ArrayList<ProvidersData>();
    private CustomAdapter_spin mSpinner_datacard_Adapeter;
    private EditText datacard_no,datacard_amount;
    private Button btnRechargeNow;


    public DatacardFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_datacard_recharge, container, false);


        spnrData = (Spinner) v.findViewById(R.id.spnr_oprator_data);
        btnRechargeNow=(Button)v.findViewById(R.id.btn_submit);
        datacard_no=(EditText)v.findViewById(R.id.et_data_card_number);
        datacard_amount=(EditText)v.findViewById(R.id.et_amount_data);

        DatacardFragment.AccountAsync task = new DatacardFragment.AccountAsync();
        task.execute();

        btnRechargeNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{ //     Intent intent=new Intent(getActivity(),pay2all_transaction.class);
                   // ProvidersData s= (ProvidersData) spnrData.getSelectedItem();
                    //ProvidersData data=new ProvidersData(s.getProvider_id(),datacard_no.getText().toString(),datacard_amount.getText().toString());
                    //intent.putExtra("data",data);
                    //startActivity(intent);
                    String item[]={"Balance in your account is insufficint please Recharge"};
                    AlertDialog.Builder dialog=new AlertDialog.Builder(getContext());
                    dialog.setTitle("Insufficient balance! ");
                    dialog.setItems(item, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    dialog.show();

                }catch (Exception e){
                    Log.e("error","jffjhfff");
                    e.printStackTrace();
                }

            }
        });

        return v;
    }
    public class AccountAsync extends AsyncTask<URL, Void, String> {
        String pro;
        String jsonResponse = "";

        @Override
        protected String doInBackground(URL... params) {
            URL url = create(ALL_PROVIDERS);

            try {
                jsonResponse = makeHttpRequest(url);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return jsonResponse;
        }

        @Override
        protected void onPostExecute(String account) {
            extractBankAccount(jsonResponse);
        }

        public URL create(String str) {
            URL url = null;
            try {
                url = new URL(str);
            } catch (MalformedURLException e) {
                return null;
            }
            return url;
        }

        public String makeHttpRequest(URL url) throws IOException {
            String jsonResponse = "";
            HttpURLConnection httpURLConnection = null;
            InputStream inputStream = null;
            JSONObject jsonObject = new JSONObject();
            try {

                httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("GET");
                httpURLConnection.setRequestProperty("Content-Type", "application/json");
                httpURLConnection.setReadTimeout(1000000);
                httpURLConnection.setConnectTimeout(15000);
                httpURLConnection.connect();

                inputStream = httpURLConnection.getInputStream();
                jsonResponse = readfromstream(inputStream);

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (httpURLConnection != null) {
                    httpURLConnection.disconnect();
                }
                if (inputStream != null) {
                    inputStream.close();
                }

            }
            return jsonResponse;
        }

        private String readfromstream(InputStream inputStream) throws IOException {
            StringBuilder output = new StringBuilder();
            if (inputStream != null) {
                InputStreamReader inputstreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
                BufferedReader reader = new BufferedReader(inputstreamReader);
                String line = reader.readLine();
                while (line != null) {
                    output.append(line);
                    line = reader.readLine();
                }
            }
            return output.toString();
        }

        public void extractBankAccount(String json) {
            ArrayList<String> x=new ArrayList<String>();
            try {
                JSONObject jsonRootObject = new JSONObject(json);

                JSONArray jsonArray = jsonRootObject.getJSONArray("providers");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    ProvidersData mProvidersData = new ProvidersData();
                    mProvidersData.setProvider_id(jsonObject.getString("provider_id"));
                    mProvidersData.setProvider_name(jsonObject.getString("provider_name"));
                    mProvidersData.setProvider_code(jsonObject.getString("provider_code"));
                    mProvidersData.setStatus(jsonObject.getString("status"));
                    mProvidersData.setService(jsonObject.getString("service"));

                    if(jsonObject.getString("service").contains("DATACARD")){
                        data_List.add(mProvidersData);

                    }
                    x.add(mProvidersData.getProvider_name());
                }
         //        ArrayAdapter<String> adapter=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,x);
                //adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                // spnrData.setAdapter(adapter);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            mSpinner_datacard_Adapeter = new CustomAdapter_spin(getContext(), R.layout.spinner_row, data_List);
            spnrData.setAdapter(mSpinner_datacard_Adapeter);

        }

    }


}
