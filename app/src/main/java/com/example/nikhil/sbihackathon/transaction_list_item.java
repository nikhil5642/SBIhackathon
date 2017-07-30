package com.example.nikhil.sbihackathon;

import java.io.Serializable;

/**
 * Created by nikhil on 13/6/17.
 */

public class transaction_list_item implements Serializable {
    String date,detail,type,amount;

    public String getDate() {
        return date;
    }

    public String getDetail() {
        return detail;
    }

    public String getType() {
        return type;
    }

    public String getAmount() {
        return amount;
    }

    public transaction_list_item(String date, String detail, String type, String amount) {

        this.date = date;
        this.detail = detail;
        this.type = type;
        this.amount = amount;
    }
}
