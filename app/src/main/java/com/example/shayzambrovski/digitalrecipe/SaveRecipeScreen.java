package com.example.shayzambrovski.digitalrecipe;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;


public class SaveRecipeScreen extends AppCompatActivity {
    EditText name;
    EditText description;
    Button save;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.save_new_recipe);
        View view = findViewById(android.R.id.content);
        Animation mLoadAnimation = AnimationUtils.loadAnimation(getApplicationContext(), android.R.anim.fade_in);
        mLoadAnimation.setDuration(1200);
        view.startAnimation(mLoadAnimation);
        Bundle extras = getIntent().getExtras();
        String sUserName = extras.getString("key");
        String sIngredients = extras.getString("ingredients");
        Log.e("Error: ", sUserName + " " + sIngredients);
        bindUI(sUserName, sIngredients);
    }
    public void bindUI(final String sUserName, final String sIngredients) {
        this.progressBar = (ProgressBar) findViewById(R.id.progressBar);
        final ProgressBar oProgressBar = this.progressBar;
        oProgressBar.setVisibility(View.GONE);
        final Context oContext = this;
        this.name = (EditText)findViewById(R.id.recipe_name);
        this.description = (EditText)findViewById(R.id.recipe_desc);
        this.save = (Button)findViewById(R.id.save);

        this.save.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String sName = ((EditText)findViewById(R.id.recipe_name)).getText().toString();
                String sDescription = ((EditText)findViewById(R.id.recipe_desc)).getText().toString();
                try{
                    //DatabaseHandler db = new DatabaseHandler(oContext);
                    if(sName.equals("") || sDescription.equals("")) {
                        DialogManager dm = new DialogManager(oContext, getResources().getString(R.string.missing_data), (getResources().getString(R.string.fill_fields)));
                        dm.show();
                    }
                    else {

                        DatabaseHandlerV2 loader = new DatabaseHandlerV2(oContext, 3, oProgressBar, (RelativeLayout) findViewById(R.id.shaike), null, new Recipe(sName, sDescription, sIngredients, sUserName, 0, 0), null, null);
                        try {
                            int number = (int)loader.execute().get();
                            //Log.e("Error", String.valueOf(number));
                        } catch (InterruptedException e) {
                            Log.e("Error", e.toString());
                        } catch (ExecutionException e) {
                            Log.e("Error", e.toString());
                        }


                        String sRecipeName = ((EditText)findViewById(R.id.recipe_name)).getText().toString();
                        NotificationCompat.Builder builder =
                                new NotificationCompat.Builder(oContext)
                                        .setSmallIcon(R.drawable.app_logo)
                                        .setContentTitle(getResources().getString(R.string.success))
                                        .setContentText(sRecipeName + " " + getResources().getString(R.string.saved));

                        // Add as notification
                        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                        manager.notify(0, builder.build());

                        Toast.makeText(getApplicationContext(),getResources().getString(R.string.recipe_saved),Toast.LENGTH_SHORT).show();
                        Intent myIntent = new Intent(SaveRecipeScreen.this, MenuScreen.class);
                        myIntent.putExtra("key", sUserName); //Optional parameters
                        startActivity(myIntent);



                    }
                    //Intent myIntent = new Intent(NewRecipeScreen.this, SaveRecipeScreen.class);
                    //myIntent.putExtra("key", sUserName);
                    //startActivity(myIntent);
                } catch(Exception e) {
                    Log.e("Error: ", e.toString());
                }
            }
        });

    }
}
