package com.example.shayzambrovski.digitalrecipe;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;


public class NewRecipeScreen extends AppCompatActivity {
    RelativeLayout relativeLayout;
    Spinner spinner;
    Button next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_recipe_screen);
        View view = findViewById(android.R.id.content);
        Animation mLoadAnimation = AnimationUtils.loadAnimation(getApplicationContext(), android.R.anim.fade_in);
        mLoadAnimation.setDuration(1200);
        view.startAnimation(mLoadAnimation);
        Bundle extras = getIntent().getExtras();
        String sUserName = extras.getString("key");
        relativeLayout = new RelativeLayout(this);
        DatabaseHandler db = new DatabaseHandler(this);
        //Log.e("Error: ", sUserName);
        bindUI(sUserName);
    }
    public void bindUI(final String sUserName) {

        final Context oContext = this;
        this.spinner = (Spinner)findViewById(R.id.spinner1);
        ArrayAdapter<CharSequence> foodadapter = ArrayAdapter.createFromResource(this, R.array.ingredient_arrays, R.layout.spinner_item);
        foodadapter.setDropDownViewResource(R.layout.spinner_item);
        spinner.setAdapter(foodadapter); //spinner of choose ingredients
        this.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent,
                                       View view, int pos, long id) {

                String selectedText = parent.getItemAtPosition(pos).toString();
                if(!selectedText.equals(getResources().getString(R.string.ingredient_prompt))) {
                    parent.setSelection(0);

                    int myEditTextWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PT, 120, getResources().getDisplayMetrics());
                    int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PT, 13, getResources().getDisplayMetrics());
                    int width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PT, 60, getResources().getDisplayMetrics());
                    int textViewTextSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PT, 3, getResources().getDisplayMetrics());
                    int editTextTextSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PT, 3, getResources().getDisplayMetrics());
                    int marginTop = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PT, 10, getResources().getDisplayMetrics());


                    EditText myEditText = new EditText(oContext);
                    myEditText.setHint("Please set amount");
                    myEditText.setBackground(getResources().getDrawable(R.drawable.rounded_option));
                    myEditText.setHeight(height);
                    myEditText.setWidth(myEditTextWidth);
                    //myEditText.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    myEditText.setTextSize(editTextTextSize);
                    myEditText.setGravity(Gravity.CENTER | Gravity.BOTTOM);
                    myEditText.setPadding(0, 4, 0, 0);
                    myEditText.setHintTextColor(Color.parseColor("#A38282"));

                    TextView myTextView = new TextView(oContext);
                    myTextView.setText(selectedText);
                    myTextView.setBackground(getResources().getDrawable(R.drawable.rounded_option));
                    RelativeLayout myLayout = (RelativeLayout)findViewById(R.id.shaike);
                    myTextView.setHeight(height);
                    myTextView.setWidth(width);

                    myTextView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    myTextView.setTextSize(textViewTextSize);
                    myTextView.setGravity(Gravity.CENTER | Gravity.BOTTOM);

                    int count = 0;
                    int i = 0;
                    ArrayList<TextView> aTextViews = new ArrayList<TextView>();
                    ArrayList<EditText> aEditTexts = new ArrayList<EditText>();
                    for( ; i < myLayout.getChildCount(); i++ ) {

                        if( myLayout.getChildAt( i ) instanceof TextView ) {
                            if(((TextView) myLayout.getChildAt( i )).getText().equals(selectedText)) {

                                DialogManager dm = new DialogManager(oContext, getResources().getString(R.string.duplicate_ingredient_title), (getResources().getString(R.string.duplicate_ingredient)));
                                dm.show();
                                return;
                            }
                            myLayout.getChildAt( i ).setId(i);
                            aTextViews.add((TextView) myLayout.getChildAt( i ));
                            count++;
                        } else if( myLayout.getChildAt( i ) instanceof EditText ) {
                            myLayout.getChildAt( i ).setId(i);
                            aEditTexts.add((EditText) myLayout.getChildAt( i ));
                            count++;
                        }
                        //Log.e("Error: ", String.valueOf(i));
                    }
                    myTextView.setId(i);
                    RelativeLayout.LayoutParams myTextViewParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                    RelativeLayout.LayoutParams myEditTextParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                    if(count == 0) {
                        myTextViewParams.addRule(RelativeLayout.BELOW, R.id.spinner1);
                        myEditTextParams.addRule(RelativeLayout.BELOW, R.id.spinner1);
                    } else {
                        myTextViewParams.addRule(RelativeLayout.BELOW, aTextViews.get(aTextViews.size() - 1).getId());
                        myEditTextParams.addRule(RelativeLayout.BELOW, aTextViews.get(aTextViews.size() - 1).getId());
                    }

                    myTextViewParams.setMargins(0, marginTop, 0, 0);
                    myLayout.addView(myTextView, myTextViewParams);
                    myEditTextParams.setMargins(0, marginTop, 0, 0);
                    myEditTextParams.addRule(RelativeLayout.RIGHT_OF, i);
                    myLayout.addView(myEditText, myEditTextParams);

                }

            }

            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });
        this.next = (Button)findViewById(R.id.next);

        this.next.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try{
                    RelativeLayout myLayout = (RelativeLayout)findViewById(R.id.shaike);
                    String sIngredients = "";
                    for( int i = 0; i < myLayout.getChildCount(); i++ ) {
                        if(myLayout.getChildAt(i) instanceof TextView && !(myLayout.getChildAt(i) instanceof Button) && !(myLayout.getChildAt(i) instanceof EditText)) {
                            String sViewText = ((TextView) myLayout.getChildAt(i)).getText().toString();
                            sIngredients += sViewText + "@";
                        }
                        else if (myLayout.getChildAt(i) instanceof EditText) {
                            String sEditText = ((EditText) myLayout.getChildAt(i)).getText().toString();
                            if (sEditText.equals("")) {
                                DialogManager dm = new DialogManager(oContext, getResources().getString(R.string.missing_ingredient), (getResources().getString(R.string.missing_ingredient_desc)));
                                dm.show();
                                return;
                            } else {
                                sIngredients += sEditText + "@";
                            }
                        }
                    }
                    Log.e("Error :", sIngredients);
                    Intent myIntent = new Intent(NewRecipeScreen.this, SaveRecipeScreen.class);
                    myIntent.putExtra("key", sUserName);
                    myIntent.putExtra("ingredients", sIngredients);
                    startActivity(myIntent);
                } catch(Exception e) {
                    Log.e("Error: ", e.toString());
                }
            }
        });
    }
}
