package com.example.shayzambrovski.digitalrecipe;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Shay Zambrovski on 17/10/2016.
 */


public class DatabaseHandlerV2 extends AsyncTask<Void,Void,Integer> {

    Context oContext;
    int flag = -1;
    ProgressBar oProgressBar;
    RelativeLayout oLayout;
    User user;

    public DatabaseHandlerV2(Context oContext, int flag, ProgressBar oProgressBar, RelativeLayout oLayout, User user){
        this.oContext = oContext;
        this.flag = flag;
        this.oProgressBar = oProgressBar;
        this.oLayout = oLayout;
        this.user = user;
        //Log.e("Error: ", String.valueOf(this.flag));
    }

    @Override
    protected Integer doInBackground(Void ... params) {
        int returnResult = 0;
        try{
            if(this.flag == 0) { //create tables
                String link = "http://digitalrecipev2.96.lt/createDBForApplication.php";
                URL url = new URL(link);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                try {
                    InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                    int ch;
                    StringBuffer sb = new StringBuffer();
                    while ((ch = in.read()) != -1) {
                        sb.append((char) ch);
                    }
                    //Log.e("Error", sb.toString());
                } catch(Exception e) {
                    Log.e("Error", e.toString());
                } finally {
                    urlConnection.disconnect();
                }
            } else if(this.flag == 1) { //create user
                String userName = this.user.getUserName();
                String password = this.user.getPassword();
                String link = "http://digitalrecipev2.96.lt/createNewUser.php?userName=" + userName +"&userPassword="+password;
                URL url = new URL(link);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                try {
                    InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                    int ch;
                    StringBuffer sb = new StringBuffer();
                    while ((ch = in.read()) != -1) {
                        sb.append((char) ch);
                    }
                    if(!sb.toString().equals("User register")) {
                        returnResult = -1;
                    }
                    //Log.e("Error", sb.toString());
                } catch(Exception e) {
                    Log.e("Error", e.toString());
                } finally {
                    urlConnection.disconnect();
                }
            } else if(this.flag == 2) { //create user
                String link = "http://digitalrecipev2.96.lt/deleteDB.php";
                URL url = new URL(link);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                try {
                    InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                    int ch;
                    StringBuffer sb = new StringBuffer();
                    while ((ch = in.read()) != -1) {
                        sb.append((char) ch);
                    }
                    Log.e("Error", sb.toString());
                } catch(Exception e) {
                    Log.e("Error", e.toString());
                } finally {
                    urlConnection.disconnect();
                }
            }
        }
        catch(Exception e){
            Log.e("Error", e.toString());
        }
        return returnResult;
    }
    protected void onPostExecute(Integer bitmap) {
        for (int i = 0; i < oLayout.getChildCount(); i++) {
            View child = oLayout.getChildAt(i);
            child.setEnabled(true);
        }
        super.onPostExecute(bitmap);
        oProgressBar.setVisibility(View.GONE);
    }
    protected void onPreExecute() {
        for (int i = 0; i < oLayout.getChildCount(); i++) {
            View child = oLayout.getChildAt(i);
            child.setEnabled(false);
        }
        super.onPreExecute();
        this.oProgressBar.setVisibility(View.VISIBLE);
    }
}
