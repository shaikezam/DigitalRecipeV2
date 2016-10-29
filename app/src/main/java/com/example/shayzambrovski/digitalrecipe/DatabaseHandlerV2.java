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
    Recipe recipe;
    String sPassword;
    String sUserName;

    public DatabaseHandlerV2(Context oContext, int flag, ProgressBar oProgressBar, RelativeLayout oLayout, User user, Recipe recipe, String sUserName, String sPassword){
        this.oContext = oContext;
        this.flag = flag;
        this.oProgressBar = oProgressBar;
        this.oLayout = oLayout;
        this.user = user;
        this.recipe = recipe;
        this.sPassword = sPassword;
        this.sUserName = sUserName;
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
                String userName = this.user.getUserName().replace(" ", "%20");
                String password = this.user.getPassword().replace(" ", "%20");
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
            } else if(this.flag == 2) { //delete DB
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
                    //Log.e("Error", sb.toString());
                } catch(Exception e) {
                    Log.e("Error", e.toString());
                } finally {
                    urlConnection.disconnect();
                }
            } else if(this.flag == 3) { //create recipe
                String recipeName = this.recipe.getName().replace(" ", "%20");;
                String recipeIngredients = this.recipe.getIngredients().replace(" ", "%20");
                String recipeInstructions = this.recipe.getInstructions().replace(" ", "%20").replace("\n", "%0A");;
                String recipeUserName = this.recipe.getUserName().replace(" ", "%20");
                int recipeRate = this.recipe.getRate();
                int recipeAmountOfRate = this.recipe.getAmountOfRates();
                String link = "http://digitalrecipev2.96.lt/createRecipe.php?recipeName="+recipeName+"&recipeIngredients="+recipeIngredients+"&recipeInstructions="+recipeInstructions+"&recipeUserName="+recipeUserName+"&recipeRate="+recipeRate+"&recipeAmountOfRate="+recipeAmountOfRate;
                URL url = new URL(link);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setChunkedStreamingMode(0);
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
            } else if(this.flag == 4) { //Log In
                String userName = this.sUserName;
                String password = this.sPassword;
                String link = "http://digitalrecipev2.96.lt/logIn.php?userName=" + userName +"&userPassword="+password;
                URL url = new URL(link);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setChunkedStreamingMode(0);
                try {
                    InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                    int ch;
                    StringBuffer sb = new StringBuffer();
                    while ((ch = in.read()) != -1) {
                        sb.append((char) ch);
                    }
                    if(sb.toString().replace("\t","").equals("User isnt found")) {
                        returnResult = -1;
                    } else {
                        returnResult = 1;
                    }
                    //Log.e("Error", sb.toString());
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
