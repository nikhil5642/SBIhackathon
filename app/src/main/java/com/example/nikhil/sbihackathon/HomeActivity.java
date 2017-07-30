package com.example.nikhil.sbihackathon;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import me.relex.circleindicator.CircleIndicator;

public class HomeActivity extends AppCompatActivity {

    private boolean backPressedtoExitOnce=false;
    private static final String Sbi_REQUEST_URL_myAcc = "http://52.172.213.166:8080/sbi/CustAcc/api/EnqCustomerAccount";
    private static final String Sbi_REQUEST_URL_accounts = "http://52.172.213.166:8080/sbi/Account_List/api/EnqBancsAccountsList/";

    private Account_layout myaccount;
    private ArrayList<Account_layout> bank_accounts;
    private GridView gridView;
    private ArrayList<grid_item> grid_items=new ArrayList<grid_item>();
    private ArrayList<Account_layout> deposite_accounts=new ArrayList<Account_layout>();
    private ArrayList<Account_layout> loan_accounts=new ArrayList<Account_layout>();
    private static ViewPager mPager;
    private EditText ac;
    private Button ac_cont;
    private static int currentPage = 0;
    private static final Integer[] Hackathaon =
                   {R.drawable.sbi,
                    R.drawable.ncip,
                    R.drawable.microsoft,
                    R.drawable.primechain,
                    R.drawable.ca,
                    R.drawable.vodafone,
                    R.drawable.tata,
                    R.drawable.credit_watch};
    private ArrayList<Integer> HackathaonArray = new ArrayList<Integer>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setupActionbar();
        gridView=(GridView)findViewById(R.id.home_gridView);
        init();

              HomeActivity.my_AccountAsync task=new HomeActivity.my_AccountAsync();
              task.execute();

              HomeActivity.Bank_AccountAsync task2=new HomeActivity.Bank_AccountAsync();
              task2.execute();

        grid_items.add(new grid_item(R.mipmap.accounts,"My Acount",MyAccountList.class));
        grid_items.add(new grid_item(R.mipmap.transfer,"Fund Transfer",FundTranster_list.class));
        grid_items.add(new grid_item(R.mipmap.cheque,"Cheque",cheque_list.class));
        grid_items.add(new grid_item(R.mipmap.enquiry,"Enqiry",Enquiry_list.class));

        grid_items.add(new grid_item(R.mipmap.npci_icon,"UPI",UPI.class));
        grid_items.add(new grid_item(R.mipmap.map_location,"ATM Locator",MapsActivity.class));
        grid_items.add(new grid_item(R.mipmap.scanner,"Scanner",scanner.class));
        grid_items.add(new grid_item(R.mipmap.pay_mobile,"Mobile Recharge",MobileRechargeActivity.class));
        grid_items.add(new grid_item(R.mipmap.more,"MORE",more.class));


        final adapter_gridview adapter=new adapter_gridview(HomeActivity.this,grid_items);
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               if(bank_accounts!=null){
                   grid_item item=adapter.getItem(position);
                Class s=item.getActivity();
                Intent intent=new Intent(HomeActivity.this,s);
                intent.putExtra("customer",myaccount);
                intent.putExtra("whole_acc_list",bank_accounts);
                intent.putExtra("deposit_acc_list",deposite_accounts);
                intent.putExtra("loan_acc_list",loan_accounts);
                startActivity(intent);
            }else {

                   String item[]={"Try by entering your account no. again/ check your interenet connection"};
                   AlertDialog.Builder dialog=new AlertDialog.Builder(HomeActivity.this);
                   dialog.setTitle("Database Not Found! ");
                   dialog.setItems(item, new DialogInterface.OnClickListener() {
                       @Override
                       public void onClick(DialogInterface dialog, int which) {
                          dialog.dismiss();
                       }
                   });
                   dialog.show();


               }

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
                Intent intent=new Intent(HomeActivity.this,HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }


    private void init() {
        for(int i=0;i<Hackathaon.length;i++)
            HackathaonArray.add(Hackathaon[i]);

        mPager = (ViewPager) findViewById(R.id.viewPager);
        mPager.setAdapter(new adapter_slider(HomeActivity.this,HackathaonArray));
        CircleIndicator indicator = (CircleIndicator) findViewById(R.id.circle_indicator);
        indicator.setViewPager(mPager);

        // Auto start of viewpager
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == Hackathaon.length) {
                    currentPage = 0;
                }
                mPager.setCurrentItem(currentPage++, true);
            }
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 10000, 10000);
    }

    public class my_AccountAsync extends AsyncTask<URL,Void,Account_layout> {
        Account_layout Account;
        String jsonResponse="";

        ProgressDialog progressDialog=new ProgressDialog(HomeActivity.this);
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog.setMessage("Loading...");
            progressDialog.show();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected Account_layout doInBackground(URL... params) {
            URL url=create(Sbi_REQUEST_URL_myAcc);
            try {
                jsonResponse=makeHttpRequest(url);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Account=extract_my_Bank_Account(jsonResponse);
            return Account;
        }
        @Override
        protected void onPostExecute(Account_layout account) {

            if(Account==null){
                return;            }

            set(Account);
            progressDialog.dismiss();

        }

    }


    public class Bank_AccountAsync extends AsyncTask<URL,Void,ArrayList<Account_layout>> {
        ArrayList<Account_layout> Account;
        String jsonResponse="";

        @Override
        protected ArrayList<Account_layout> doInBackground(URL... params) {
            URL url=create(Sbi_REQUEST_URL_accounts);
            try {
                jsonResponse=makeHttpRequest(url);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Account=extract_Bank_Accounts(jsonResponse);
            return Account;
        }

        @Override
        protected void onPostExecute(ArrayList<Account_layout> account_layouts) {
            if(Account==null){
                return;
            }
            set(Account);
        }
    }

    private void set(ArrayList<Account_layout> account) {
  bank_accounts=account;
    }

    private void set(Account_layout account) {
        myaccount=account;
    }


    public URL create(String str){
        URL url=null;
        try {
            url=new URL(str);
        } catch (MalformedURLException e) {
            return null;
        }
        return url;
    }

    public String makeHttpRequest(URL url) throws IOException {
        String jsonResponse="";
        HttpURLConnection httpURLConnection=null;
        InputStream inputStream=null;
        JSONObject jsonObject=new JSONObject();
        try {

            jsonObject.put("AccountNumber","30001512992");


            httpURLConnection=(HttpURLConnection)url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("apikey",getString(R.string.sbi_api));
            httpURLConnection.setRequestProperty("Content-Type","application/json;chatset=utf-8");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setReadTimeout(1000000);
            httpURLConnection.setConnectTimeout(15000);
            httpURLConnection.connect();

            OutputStream wr=new BufferedOutputStream(httpURLConnection.getOutputStream());
            wr.write(jsonObject.toString().getBytes());
            wr.flush();


            inputStream=httpURLConnection.getInputStream();
            jsonResponse=readfromstream(inputStream);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
            if(inputStream!=null){
                inputStream.close();
            }

        }
        return jsonResponse;
    }

    private String readfromstream(InputStream inputStream) throws IOException {
        StringBuilder output=new StringBuilder();
        if(inputStream!=null){
            InputStreamReader inputstreamReader=new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader=new BufferedReader(inputstreamReader);
            String line=reader.readLine();
            while (line!=null){
                output.append(line);
                line=reader.readLine();
            }
        }
        return output.toString();
    }

    public Account_layout extract_my_Bank_Account(String json) {
        Account_layout my_account = null;
        try {
            JSONObject object = new JSONObject(json);
                String Acc_no= object.getString("AccountNumber");
                String Acc_available_balance=object.getString("AvailableBal");
                String Account_type =object.getString("AccountType");
                String Customer_name=object.getString("CustomerName");
                String Home_branch=object.getString("AccountHomeBranch");
                String CustomerNumber = object.getString("CustomerNumber");
                String ModeofOperation =object.getString( "ModeofOperation");
                String AccountOpenDate =object.getString("AccountOpenDate");


            my_account= new Account_layout(Acc_no,
                      Account_type,
                        Acc_available_balance,
                        Home_branch,
                        Customer_name,
                        CustomerNumber,
                        ModeofOperation,
                        AccountOpenDate);

        } catch (JSONException e) {
        }
        return my_account;
    }

    public ArrayList<Account_layout> extract_Bank_Accounts(String json) {
       ArrayList<Account_layout> arrayList=new ArrayList<Account_layout>();
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray jsonArray = jsonObject.getJSONArray("ac_details");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object=jsonArray.getJSONObject(i);

                String Account_category=object.getString("AccountCategory");
                String Acc_no= object.getString("AccountNumber");
                String Acc_available_balance=object.getString("AvailableBalance");
                String Account_type =object.getString("AccountType");
                String Account_status=object.getString("AccountStatus");
                String Total_balance=object.getString("TotalBalance");
                String Home_branch=object.getString("HomeBranch");
                String Interest_rate=object.getString("InterestRate");
                String Approoved_sanction=object.getString("ApprovedSanctionedAmount");

          if(Account_category.contains("D")) { deposite_accounts.add(new Account_layout(Account_category,
                        Acc_no,Account_type,
                        Acc_available_balance,
                        Account_status,
                        Total_balance,
                        Approoved_sanction,
                        Home_branch,
                        Interest_rate));
          }else if(Account_category.contains("L")) { loan_accounts.add(new Account_layout(Account_category,
                    Acc_no,Account_type,
                    Acc_available_balance,
                    Account_status,
                    Total_balance,
                    Approoved_sanction,
                    Home_branch,
                    Interest_rate));}
        }
        } catch (JSONException e) {
        }
        arrayList.addAll(deposite_accounts);
        arrayList.addAll(loan_accounts);
        return arrayList;


    }

    @Override
    public void onBackPressed() {
        if(backPressedtoExitOnce) {
            super.onBackPressed();
        }else {
            this.backPressedtoExitOnce=true;
            Toast.makeText(this, "Press again to Exit", Toast.LENGTH_LONG).show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    backPressedtoExitOnce=false;
                }
            },4000);
        }

    }
}














