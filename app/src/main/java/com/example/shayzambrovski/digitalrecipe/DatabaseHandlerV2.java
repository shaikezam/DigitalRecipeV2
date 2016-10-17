package com.example.shayzambrovski.digitalrecipe;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Shay Zambrovski on 17/10/2016.
 */


public class DatabaseHandlerV2 extends AsyncTask<Void,Void,String> {

    Context oContext;

    public DatabaseHandlerV2(Context oContext){
        this.oContext = oContext;
    }



    @Override
    protected String doInBackground(Void ... params) {
        String s;
        try{

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
                //return sb.toString();
                Log.e("Error", sb.toString());
            } catch(Exception e) {
                Log.e("Error", e.toString());
            } finally {
                urlConnection.disconnect();
            }
        }
        catch(Exception e){
            Log.e("Error", e.toString());
        }

        return "hi";
    }

    @Override
    protected void onPostExecute(String bitmap) {
        super.onPostExecute(bitmap);
        if (bitmap != null){
            Log.e(bitmap, bitmap);
        }else{
            //
        }
    }
}
