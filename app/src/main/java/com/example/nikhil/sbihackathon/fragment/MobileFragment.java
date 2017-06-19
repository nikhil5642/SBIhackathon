package com.asmobisoft.coffer.fragments;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.asmobisoft.coffer.MainActivity1;
import com.asmobisoft.coffer.R;
import com.asmobisoft.coffer.adapter.CustomAdapter;
import com.asmobisoft.coffer.commonmethod.Constants;
import com.asmobisoft.coffer.commonmethod.Utility;
import com.asmobisoft.coffer.model.AllProviders;
import com.asmobisoft.coffer.model.ProvidersData;
import com.asmobisoft.coffer.utility.ApiInterface;
import com.asmobisoft.coffer.webservices.ApiClient;
import com.asmobisoft.coffer.webservices.NetClientGet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MobileFragment extends Fragment implements View.OnClickListener{


    private LinearLayout llTab1View;
    private Spinner spnrMobile;
    private RadioGroup radioMobileGroup;
    private RadioButton radioMobileButton;

    private Button btnRechargeNow;
    private String TAG = "MobileFragment";

    // TODO - insert your API KEY obtained from pay2all.com here
    private final static String API_KEY = "PPqNCFK6DCncHEvLzza4qBmQLS8IbNT60kl0loMVfp6x5h6cXRi3HztFt4Z2";

    private CustomAdapter mSpinnerAdapeter;
    ArrayList<ProvidersData> providerList = new ArrayList<ProvidersData>();
    public MobileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_mobile_recharge, container, false);

        llTab1View = (LinearLayout) v.findViewById(R.id.ll_tab1_view);
        spnrMobile = (Spinner) v.findViewById(R.id.spnr_oprator);
        radioMobileGroup = (RadioGroup) v.findViewById(R.id.radio_mobile);
        btnRechargeNow = (Button) v.findViewById(R.id.btn_recharge_now);
        btnRechargeNow.setOnClickListener(this);

        int selectedIdMobile = radioMobileGroup.getCheckedRadioButtonId();
        radioMobileButton = (RadioButton) v.findViewById(selectedIdMobile);



        if(Utility.isOnline(getActivity())){
            new ProviderAsync().execute();
        }else {
            Utility.InternetSetting(getActivity());
        }



       /* ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        Call<AllProviders> call = apiService.getProvider(API_KEY);
        call.enqueue(new Callback<AllProviders>() {
            @Override
            public void onResponse(Call<AllProviders> call, Response<AllProviders> response) {
                int statusCode = response.code();
                Log.e("MobileFragment","Responce : "+ response.body().getProviders() +"\n"+ statusCode);
               //insert data in list here  List<ProvidersData> movies = response.body().getResults();
                List<ProvidersData> providersDataList = response.body().getProviders();
               // recyclerView.setAdapter(new MoviesAdapter(movies, R.layout.list_item_movie, getApplicationContext()));

            }

            @Override
            public void onFailure(Call<AllProviders> call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
            }
        });
*/
        /*List<String> list = new ArrayList<String>();
        list.add("Airtel");
        list.add("Vodafone");
        list.add("Relince");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnrMobile.setAdapter(dataAdapter);*/

        // Listener called when spinner item selected
        spnrMobile.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View v, int position, long id) {
                // your code here

                // Get selected row data to show on screen
                String name    = ((TextView) v.findViewById(R.id.tv_spinner_text)).getText().toString();

             //   String CompanyUrl = ((TextView) v.findViewById(R.id.sub)).getText().toString();
              //  TextView output = null;
                String OutputMsg = "Selected Company : \n\n"+name;
               // output.setText(OutputMsg);


                Toast.makeText(getActivity(),OutputMsg, Toast.LENGTH_LONG).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });


        return v;
    }
    private ProgressDialog mProgressDialog;
    private class ProviderAsync extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            mProgressDialog = new ProgressDialog(getActivity());
            mProgressDialog.setMessage("Please wait...");
            mProgressDialog.setCancelable(false);
            mProgressDialog.show();

        }

        protected String doInBackground(String... urls) {
            NetClientGet mNetClientGet = new NetClientGet(getActivity());
//
            String responce = "";

            String url = Constants.ALL_PROVIDERS+API_KEY;
            Log.e("Login","URL : "+url);

            responce = mNetClientGet.getDataClientData(url);

            Log.e("Login","responce : "+responce);
            return responce;

        }
        EditText etOTPField;
        protected void onPostExecute(String result) {
            if(mProgressDialog !=null){
                mProgressDialog.dismiss();
            }
            Log.e("Signup","responce otp : "+result);
            if(result != null ){
                if(!result.equals("")){

                    String data = "";
                    try {
                        JSONObject  jsonRootObject = new JSONObject(result);

                        //Get the instance of JSONArray that contains JSONObjects
                        JSONArray jsonArray = jsonRootObject.optJSONArray("providers");

                        //Iterate the jsonArray and print the info of JSONObjects
                        for(int i=0; i < jsonArray.length(); i++){
                            JSONObject jsonObject = jsonArray.getJSONObject(i);

                            ProvidersData mProvidersData = new ProvidersData();
                            if(!jsonObject.optString("provider_id").toString().equals("")){
                                mProvidersData.setProvider_id(jsonObject.optString("provider_id").toString());
                            }else{
                                mProvidersData.setProvider_id("");
                            }

                            if(!jsonObject.optString("provider_name").toString().equals("")){
                                mProvidersData.setProvider_name(jsonObject.optString("provider_name").toString());
                            }else{
                                mProvidersData.setProvider_name("");
                            }

                            if(!jsonObject.optString("provider_code").toString().equals("")){
                                mProvidersData.setProvider_code(jsonObject.optString("provider_code").toString());
                            }else{
                                mProvidersData.setProvider_code("");
                            }

                            if(!jsonObject.optString("status").toString().equals("")){
                                mProvidersData.setStatus(jsonObject.optString("status").toString());
                            }else{
                                mProvidersData.setStatus("");
                            }

                            if(!jsonObject.optString("service").toString().equals("")){
                                mProvidersData.setService(jsonObject.optString("service").toString());
                            }else{
                                mProvidersData.setService("");
                            }
                            providerList.add(mProvidersData);
/*
                            String provider_name = jsonObject.optString("provider_name").toString();
                            String provider_code = jsonObject.optString("provider_code").toString();
                            String status= jsonObject.optString("status").toString();

                            data += "Node"+i+" : \n provider_name= "+ provider_name +" \n provider_code= "+ provider_code
                                    +" \n status= "+ status +" \n ";
                            Log.e(TAG,"Data : "+data);*/
                        }

                        mSpinnerAdapeter = new CustomAdapter(getActivity(),R.layout.spinner_row,providerList);
                        spnrMobile.setAdapter(mSpinnerAdapeter);

                    } catch (JSONException e) {e.printStackTrace();}
                }

                }else{
                    Toast.makeText(getActivity(),getResources().getString(R.string.internet_connection_dialog),Toast.LENGTH_LONG).show();
                }



            }


        }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btn_recharge_now:



                break;
        }


    }
}
