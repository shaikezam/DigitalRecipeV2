package com.example.shayzambrovski.digitalrecipe;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.widget.EditText;
import android.widget.TextView;


/**
 * Created by Shay Zambrovski on 11/09/2016.
 */
public class IngredientControl extends AppCompatActivity {
    TextView myTextView;
    EditText myEditText;
    Context oContext;
    static int iId = 0;

    public IngredientControl(TextView myTextView, EditText myEditText, Context oContext) {
        setMyTextView(myTextView);
        setMyEditText(myEditText);
        this.oContext = oContext;
    }

    public TextView getMyTextView() {
        return myTextView;
    }

    public void setMyTextView(TextView myTextView) {
        myTextView.setId(this.iId++);
        this.myTextView = myTextView;
    }

    public EditText getMyEditText() {
        return myEditText;
    }

    public void setMyEditText(EditText myEditText) {
        myTextView.setId(this.iId++);
        this.myEditText = myEditText;
    }

    public void configControls() {
        int myEditTextWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PT, 120, getResources().getDisplayMetrics());
        int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PT, 13, getResources().getDisplayMetrics());
        int width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PT, 60, getResources().getDisplayMetrics());
        int textSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PT, 3, getResources().getDisplayMetrics());
        int marginTop = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PT, 10, getResources().getDisplayMetrics());

    }
}
