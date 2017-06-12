package com.example.nikhil.sbihackathon;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by nikhil on 12/6/17.
 */

public class Account_layout implements Parcelable{
    String Account_category;
    String Account_no;
    String Account_type;
    String Available_balance;
    String Account_status;
    String Total_balance;

    public String getApproved_sanction_amount() {
        return Approved_sanction_amount;
    }

    String Approved_sanction_amount;
    String Home_branch;
    String Interest_rate;

    public Account_layout(String account_category, String account_no, String account_type, String available_balance, String account_status, String total_balance, String approved_sanction_amount, String home_branch, String interest_rate) {
        Account_category = account_category;
        Account_no = account_no;
        Account_type = account_type;
        Available_balance = available_balance;
        Account_status = account_status;
        Total_balance = total_balance;
        Approved_sanction_amount = approved_sanction_amount;
        Home_branch = home_branch;
        Interest_rate = interest_rate;
    }

    public String getAccount_type() {
        return Account_type;
    }

    public String getHome_branch() {

        return Home_branch;
    }

    public String getInterest_rate() {
        return Interest_rate;
    }

    public String getAccount_category() {
        return Account_category;
    }

    public String getAccount_no() {
        return Account_no;
    }

    public String getAvailable_balance() {
        return Available_balance;
    }

    public String getAccount_status() {
        return Account_status;
    }

    public String getTotal_balance() {
        return Total_balance;
    }

    @Override
    public String toString() {
        return Account_no;
    }
   public Account_layout(Parcel in){
        Account_category = in.readString();
        Account_no =in.readString();
        Account_type = in.readString();
        Available_balance = in.readString();
        Account_status = in.readString();
        Total_balance = in.readString();
        Approved_sanction_amount = in.readString();
        Home_branch = in.readString();
        Interest_rate = in.readString();

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
      dest.writeString(Account_category);
        dest.writeString(Account_no);
        dest.writeString(Account_type);
        dest.writeString(Available_balance);
        dest.writeString(Account_status);
        dest.writeString(Total_balance);
        dest.writeString(Approved_sanction_amount);
        dest.writeString(Home_branch);
        dest.writeString(Interest_rate);

    }

    public static final Parcelable.Creator<Account_layout> CREATOR=new Parcelable.Creator<Account_layout>(){
        public Account_layout createFromParcel(Parcel in){
            return new Account_layout(in);
        }

        public Account_layout[] newArray(int size){
            return new Account_layout[size];
        }
    };
}
