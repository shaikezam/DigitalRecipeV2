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
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Shay Zambrovski on 17/10/2016.
 */


public class DatabaseHandlerV3 extends AsyncTask<Void,Void,List<Recipe>> {

    Context oContext;
    int flag = -1;
    ProgressBar oProgressBar;
    RelativeLayout oLayout;
    String sUserName;

    public DatabaseHandlerV3(Context oContext, int flag, ProgressBar oProgressBar, RelativeLayout oLayout, String sUserName){
        this.oContext = oContext;
        this.flag = flag;
        this.oProgressBar = oProgressBar;
        this.oLayout = oLayout;
        this.sUserName = sUserName;
        //Log.e("Error: ", String.valueOf(this.flag));
    }

    @Override
    protected List<Recipe> doInBackground(Void ... params) {
        List<Recipe> recipeList = new ArrayList<Recipe>();
        try{
            if(this.flag == 0) { //create tables
                String link = "http://digitalrecipev2.96.lt/getRecipes.php?userName=" + this.sUserName;
                URL url = new URL(link);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                try {
                    InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                    int ch;
                    StringBuffer sb = new StringBuffer();
                    while ((ch = in.read()) != -1) {
                        sb.append((char) ch);
                    }
                    String results = sb.toString().replace("\t","");
                    String[] parts = results.split("\\^");
                    for(int i = 0 ; i < parts.length ; i++) {
                        Recipe tempRecipe = new Recipe();
                        String[] recipeParts = parts[i].split("\\*");
                        tempRecipe.setName(recipeParts[0]);
                        tempRecipe.setIngredients(recipeParts[1]);
                        tempRecipe.setInstructions(recipeParts[2]);
                        tempRecipe.setRate(Integer.parseInt(recipeParts[3]));
                        tempRecipe.setUserName(recipeParts[4]);
                        tempRecipe.setAmountOfRates(Integer.parseInt(recipeParts[5]));
                        recipeList.add(tempRecipe);
                    }
                    //String[] str = results.split("^");
                    Log.e("Error", parts[0]);
                    Log.e("Error", parts[1]);
                    //Log.e("Error", str.toString());
                } catch(Exception e) {
                    Log.e("Error", e.toString());
                } finally {
                    urlConnection.disconnect();
                }
            } else if(this.flag == 1) { //create tables
                String link = "http://digitalrecipev2.96.lt/getOtherRecipes.php?userName=" + this.sUserName;
                URL url = new URL(link);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                try {
                    InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                    int ch;
                    StringBuffer sb = new StringBuffer();
                    while ((ch = in.read()) != -1) {
                        sb.append((char) ch);
                    }
                    String results = sb.toString().replace("\t","");
                    String[] parts = results.split("\\^");
                    for(int i = 0 ; i < parts.length ; i++) {
                        Recipe tempRecipe = new Recipe();
                        String[] recipeParts = parts[i].split("\\*");
                        tempRecipe.setName(recipeParts[0]);
                        tempRecipe.setIngredients(recipeParts[1]);
                        tempRecipe.setInstructions(recipeParts[2]);
                        tempRecipe.setRate(Integer.parseInt(recipeParts[3]));
                        tempRecipe.setUserName(recipeParts[4]);
                        tempRecipe.setAmountOfRates(Integer.parseInt(recipeParts[5]));
                        recipeList.add(tempRecipe);
                    }
                    //String[] str = results.split("^");
                    Log.e("Error", parts[0]);
                    Log.e("Error", parts[1]);
                    //Log.e("Error", str.toString());
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
        return recipeList;
    }
    protected void onPostExecute(List<Recipe> bitmap) {
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
