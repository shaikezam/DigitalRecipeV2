package com.example.shayzambrovski.digitalrecipe;

import android.app.Application;

/**
 * Created by Shay Zambrovski on 10/09/2016.
 */
public class MyApplication extends Application {

    private boolean isLogedIn;

    public boolean getIsLogedIn() {
        return isLogedIn;
    }

    public void setgetIsLogedIn(boolean isLogedIn) {
        this.isLogedIn = isLogedIn;
    }
}
