package com.example.shayzambrovski.digitalrecipe;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * Created by Shay Zambrovski on 09/09/2016.
 */
public class DialogManager {
    String sTitle; //title of the DialogManager
    String sBody; //body of the DialogManager
    AlertDialog alertDialog;

    public DialogManager(Context oContext, String sTitle, String sBody) {

        this.alertDialog = new AlertDialog.Builder(oContext).create();
        this.alertDialog.setTitle(sTitle);
        this.alertDialog.setMessage(sBody);
        this.alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

    }

    //show dialog manager
    public void show() {
        this.alertDialog.show();
    }
}
