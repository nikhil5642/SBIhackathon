package com.example.nikhil.sbihackathon;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Spinner;
import android.widget.Toast;

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

public class confirm_fund_transfer_NEFT extends AppCompatActivity {
    TextView from_name,to_name,to_acc_no,amount,from_ifsc,to_ifsc,mob_no,from_acc_no;
    String s_from_name,s_to_name,s_to_acc_no,s_amount,s_from_ifsc,s_to_ifsc,s_mob_no,s_from_acc_no;
    EditText mpin;
    Button cont;
    String Sbi_REQUEST_URL="http://52.172.213.166:8080/sbi/NEFT/api/TxnNEFT";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_fund_transfer__neft);

        setupActionbar();
        from_acc_no=(TextView) findViewById(R.id.confirm_fund_transfer_neft_from_acc_no);

        from_name=(TextView)findViewById(R.id.confirm_fund_transfer_neft_from_name);
        to_name=(TextView)findViewById(R.id.confirm_fund_transfer_neft_to_name);
        to_acc_no=(TextView)findViewById(R.id.confirm_fund_transfer_neft_to_acc_no);
        amount=(TextView)findViewById(R.id.confirm_fund_transfer_neft_amount);
        from_ifsc=(TextView)findViewById(R.id.confirm_fund_transfer_neft_from_ifsc);
        to_ifsc=(TextView)findViewById(R.id.confirm_fund_transfer_neft_to_ifsc);
        mob_no=(TextView)findViewById(R.id.confirm_fund_transfer_neft_from_mob);

        Intent intent=getIntent();

        s_from_name=intent.getExtras().getString(("from_name"));
        s_to_name=intent.getExtras().getString(("to_name"));
        s_to_acc_no=intent.getExtras().getString(("to_acc_no"));
        s_amount=intent.getExtras().getString(("amount"));
        s_from_ifsc=intent.getExtras().getString(("from_ifsc"));
        s_to_ifsc=intent.getExtras().getString(("to_ifsc"));
        s_mob_no=intent.getExtras().getString(("mob_no"));
        s_from_acc_no=intent.getExtras().getString(("from_acc_no"));

        from_name.setText(s_from_name);
        to_name.setText(s_to_name);
        to_acc_no.setText(s_to_acc_no);
        amount.setText(s_amount);
        from_ifsc.setText(s_from_ifsc);
        to_ifsc.setText(s_to_ifsc);
        mob_no.setText(s_mob_no);
        from_acc_no.setText(s_from_acc_no);
            cont=(Button)findViewById(R.id.confirm_fund_transfer_neft_submit);

        cont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirm_fund_transfer_NEFT.AccountAsync task=new confirm_fund_transfer_NEFT.AccountAsync();
                task.execute();
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
                Intent intent=new Intent(confirm_fund_transfer_NEFT.this,HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    public class AccountAsync extends AsyncTask<URL,Void,String> {
        String pro;
        String jsonResponse="";

        ProgressDialog progressDialog=new ProgressDialog(confirm_fund_transfer_NEFT.this);
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog.setMessage("Processing...");
            progressDialog.show();
        }
        @Override
        protected String doInBackground(URL... params) {
            URL url=create(Sbi_REQUEST_URL);
            try {
                jsonResponse=makeHttpRequest(url);
            } catch (IOException e) {
                e.printStackTrace();
            }
            pro=extractBankAccount(jsonResponse);
            return pro;
        }

        @Override
        protected void onPostExecute(String account) {

            if(account==null||!account.contains("SUCCESS")){
                Toast.makeText(confirm_fund_transfer_NEFT.this,"Invalid Credencials",Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
                return;
            }

            Toast.makeText(confirm_fund_transfer_NEFT.this,account,Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(confirm_fund_transfer_NEFT.this,transaction_status.class);
            intent.putExtra("status",pro);
            startActivity(intent);
        progressDialog.dismiss();
        }

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
            jsonObject.put("REMTACCTNO",s_to_acc_no);
            jsonObject.put("REMNAME",s_to_name);
            jsonObject.put("MOBNUMBER",s_mob_no);
            jsonObject.put("BENFACCTNO",s_from_acc_no);
            jsonObject.put("BENFNAME",s_from_acc_no);
            jsonObject.put("RECBNKIFSC",s_to_ifsc);
            jsonObject.put("SNDIFSC",s_from_ifsc);
            jsonObject.put("TXNAMT",s_amount);

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

    public String extractBankAccount(String json) {
        ArrayList<transaction_list_item> arrayList= new ArrayList<transaction_list_item>();
        String type="";
        try {
            JSONObject object = new JSONObject(json);
            type=object.getString("ErrorDescription");

        } catch (JSONException e1) {
            e1.printStackTrace();
        }
        return type;
    }

}
