package com.example.shayzambrovski.digitalrecipe;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import java.util.Calendar;
import java.util.TimeZone;
import java.util.concurrent.ExecutionException;

public class MainScreen extends AppCompatActivity {

    EditText userName;
    EditText password;
    Button logIn;
    Button register;
    Button about;
    Button initData;
    Button deleteData;
    Bundle extras;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        View view = findViewById(android.R.id.content);
        Animation mLoadAnimation = AnimationUtils.loadAnimation(getApplicationContext(), android.R.anim.fade_in);
        mLoadAnimation.setDuration(1200);
        view.startAnimation(mLoadAnimation);
        extras = new Bundle();
        //DatabaseHandler db = new DatabaseHandler(this);
        //db.deleteDB();
        this.deleteDatabase("myAppDataBase");
        try {
            //setRecurringAlarm(this);
        } catch(Exception e) {
            Log.e("Error: ", e.toString());
        }
        bindUI();
        isLogIn();
        //createDB();
    }

    private void setRecurringAlarm(Context context) {

        Calendar updateTime = Calendar.getInstance();
        updateTime.setTimeZone(TimeZone.getDefault());
        updateTime.set(Calendar.HOUR_OF_DAY, 12);
        updateTime.set(Calendar.MINUTE, 30);
        Intent downloader = new Intent(context, MyStartServiceReceiver.class);
        downloader.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, downloader, PendingIntent.FLAG_CANCEL_CURRENT);

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, updateTime.getTimeInMillis(), 5000, pendingIntent);

        Log.d("MyActivity", "Set alarmManager.setRepeating to: " + updateTime.getTime().toLocaleString());

    }

    public void bindUI() {
        this.progressBar = (ProgressBar) findViewById(R.id.progressBar);
        final ProgressBar oProgressBar = this.progressBar;
        oProgressBar.setVisibility(View.GONE);
        final Context oContext = this;
        this.userName = (EditText)findViewById(R.id.user_name_id);

        this.password = (EditText)findViewById(R.id.password_id);

        this.logIn = (Button)findViewById(R.id.log_in_id);

        this.logIn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try{
                    //DatabaseHandler db = new DatabaseHandler(oContext);
                    String sUserName = userName.getText().toString();
                    String sPassword = password.getText().toString();
                    //Toast.makeText(getApplicationContext(),"Good",Toast.LENGTH_SHORT).show();
                    if(sUserName.equals("") || sPassword.equals("")) { // if 1 of fields are empty

                        DialogManager dm = new DialogManager(oContext, getResources().getString(R.string.authentication_error), getResources().getString(R.string.fill_fields));
                        dm.show();

                    } else { // user not exists
                        DatabaseHandlerV2 loader = new DatabaseHandlerV2(oContext, 4, oProgressBar, (RelativeLayout) findViewById(R.id.mainID), null, null, sUserName, sPassword);
                        try {
                            int number = (int)loader.execute().get();
                            if(number == -1) { // duplicate user name
                                DialogManager dm = new DialogManager(oContext, getResources().getString(R.string.authentication_error), (getResources().getString(R.string.cant_found_user)));
                                dm.show();
                            } else {
                                User oUser = new User(sUserName, sPassword);
                                Intent myIntent = new Intent(MainScreen.this, MenuScreen.class);
                                myIntent.putExtra("key", sUserName); //Optional parameters
                                startActivity(myIntent);
                            }
                            //Log.e("Error", String.valueOf(number));
                        } catch (InterruptedException e) {
                            Log.e("Error", e.toString());
                        } catch (ExecutionException e) {
                            Log.e("Error", e.toString());
                        }
                    }

                } catch(Exception e) {
                    Log.e("Error: ", e.toString());
                }
            }
        });

        this.register = (Button)findViewById(R.id.register_id);

        this.register.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try{
                    //DatabaseHandler db = new DatabaseHandler(oContext);
                    String sUserName = userName.getText().toString();
                    String sPassword = password.getText().toString();
                    //Toast.makeText(getApplicationContext(),"Good",Toast.LENGTH_SHORT).show();
                    if(sUserName.equals("") || sPassword.equals("")) { // if 1 of fields are empty

                        DialogManager dm = new DialogManager(oContext, getResources().getString(R.string.authentication_error), getResources().getString(R.string.fill_fields));
                        dm.show();

                    } else {

                        DatabaseHandlerV2 loader1 = new DatabaseHandlerV2(oContext, 1, oProgressBar, (RelativeLayout) findViewById(R.id.mainID),new User(sUserName, sPassword), null, null, null);
                        try {
                            int number = (int)loader1.execute().get();
                            if(number == -1) { // duplicate user name
                                DialogManager dm = new DialogManager(oContext, getResources().getString(R.string.registration_error), getResources().getString(R.string.user_duplicate));
                                dm.show();
                            } else {
                                DialogManager dm = new DialogManager(oContext, getResources().getString(R.string.success),sUserName + ": " + getResources().getString(R.string.registration_success));
                                dm.show();
                            }
                            //Log.e("Error", String.valueOf(number));
                        } catch (InterruptedException e) {
                            Log.e("Error", e.toString());
                        } catch (ExecutionException e) {
                            Log.e("Error", e.toString());
                        }

                    }

                } catch(Exception e) {
                    Log.e("Error: ", e.toString());
                }
            }
        });

        this.about = (Button)findViewById(R.id.about_id);
        this.about.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try{
                    //createDB();
                    DialogManager dm = new DialogManager(oContext, getResources().getString(R.string.my_title), getResources().getString(R.string.my_body));
                    dm.show();
                } catch(Exception e) {
                    Log.e("Error: ", e.toString());
                }
            }
        });

        this.deleteData = (Button)findViewById(R.id.delete_data);
        this.deleteData.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) { // delete all data
                try{
                    DatabaseHandlerV2 loader = new DatabaseHandlerV2(oContext, 2, oProgressBar, (RelativeLayout) findViewById(R.id.mainID), null, null, null, null);
                    try {
                        int number = (int)loader.execute().get();
                        //Log.e("Error", String.valueOf(number));
                    } catch (InterruptedException e) {
                        Log.e("Error", e.toString());
                    } catch (ExecutionException e) {
                        Log.e("Error", e.toString());
                    }
                    //DatabaseHandler db = new DatabaseHandler(oContext);
                    //Intent myIntent = new Intent(MainScreen.this, AboutScreen.class);
                    //startActivity(myIntent);
                    //db.deleteDB();
                } catch(Exception e) {
                    Log.e("Error: ", e.toString());
                }
            }
        });

        this.initData = (Button)findViewById(R.id.init_data);
        this.initData.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try{ // create 2 users with 1 recipe each
                    createDB();
                    DatabaseHandlerV2 loader = new DatabaseHandlerV2(oContext, 1, oProgressBar, (RelativeLayout) findViewById(R.id.mainID), (new User("Shay", "123")), null, null, null);
                    try {
                        int number = (int)loader.execute().get();
                        //Log.e("Error", String.valueOf(number));
                    } catch (InterruptedException e) {
                        Log.e("Error", e.toString());
                    } catch (ExecutionException e) {
                        Log.e("Error", e.toString());
                    }
                    loader = new DatabaseHandlerV2(oContext, 1, oProgressBar, (RelativeLayout) findViewById(R.id.mainID), (new User("Arnon", "123")), null, null, null);
                    try {
                        int number = (int)loader.execute().get();
                        //Log.e("Error", String.valueOf(number));
                    } catch (InterruptedException e) {
                        Log.e("Error", e.toString());
                    } catch (ExecutionException e) {
                        Log.e("Error", e.toString());
                    }
                    loader = new DatabaseHandlerV2(oContext, 3, oProgressBar, (RelativeLayout) findViewById(R.id.mainID), null, new Recipe("Banana Bread", "Mix well by hand until all ingredients are just moistened.\nPour into greased loaf pan and bake 55-65 minutes.\nRemove from pan and cool before slicing.",
                            "Flour@2.5 cups@Suger@1 cup@baking powder@3.5 teaspoons@salt@1 teaspoon@vegetable oil@3 tablespoons@milk@0.75 cup@egg@1@banana@3",
                            "Shay", 1, 1), null, null);
                    try {
                        int number = (int)loader.execute().get();
                        //Log.e("Error", String.valueOf(number));
                    } catch (InterruptedException e) {
                        Log.e("Error", e.toString());
                    } catch (ExecutionException e) {
                        Log.e("Error", e.toString());
                    }
                    loader = new DatabaseHandlerV2(oContext, 3, oProgressBar, (RelativeLayout) findViewById(R.id.mainID), null, new Recipe("Apple Pie", "Preheat oven to 425°F / 220°C\n" +
                            "For the filling: mix ingredients together just before filling pie crust, dot top of apple mixture with 2 tablespoons of cold butter cubed.\n" +
                            "For the crust:\n" +
                            "Measure flour, salt, sugar, shortening, and butter into a bowl and cut with pastry cutter until bits are no larger than size of a pea.\n" +
                            "Sprinkle water over top and fold mixture until dough sticks together.\n" +
                            "Divide dough into two pieces and roll out for pie top and bottom.\n" +
                            "Fill with apples and top with 2nd piece of dough, making a design of some sort for steam to escape.\n" +
                            "Bake for 40-50 minutes until crust is golden and filling begins to bubble through the crust.",
                            "Flour@2 cups@Suger@1 tablespoon@unsalted butter cold@3 tablespoons@ice water@4 tablespoons@apples thinly sliced@6@cinnamon@0.5 tablespoon@lemon juice@2 teaspoons@baking powder@3.5 teaspoons@salt@1 teaspoon@vegetable oil@3 tablespoons@milk@0.75 cup@egg@1@banana@3",
                            "Arnon", 2, 2), null, null);
                    try {
                        int number = (int)loader.execute().get();
                        //Log.e("Error", String.valueOf(number));
                    } catch (InterruptedException e) {
                        Log.e("Error", e.toString());
                    } catch (ExecutionException e) {
                        Log.e("Error", e.toString());
                    }
                    loader = new DatabaseHandlerV2(oContext, 3, oProgressBar, (RelativeLayout) findViewById(R.id.mainID), null, new Recipe("Cream Scones", "Preheat oven to 425°F / 220°C\n" +
                            "Place flour, baking powder, sugar, and salt in a large bowl - then whisk together.\n" +
                            "Cut butter into flour mixture with pastry cutter or two knives until it looks like coarse meal with only a few larger butter lumps.\n" +
                            "Add dried fruit and stir in heavy cream.\n" +
                            "Knead the dough by hand until just combined.\n" +
                            "Cut scones into 8 wedges or use decorative cutter.\n" +
                            "Place scones on ungreased baking sheet.\n" +
                            "Bake until scone tops are light brown, 12-15 minutes.\n" +
                            "Cool on wire rack for 10 minutes.",
                            "Flour@2 cups@Suger@3 tablespoon@unsalted butter cold@5 tablespoons@ice water@4 tablespoons@powder@1 tablespoon@salt@0.5 table spoon@dried fruit@0.5 cup@heavy cream@1 cup@sanding suger@2 tablespoonhs@",
                            "Arnon", 5, 1), null, null);
                    try {
                        int number = (int)loader.execute().get();
                        //Log.e("Error", String.valueOf(number));
                    } catch (InterruptedException e) {
                        Log.e("Error", e.toString());
                    } catch (ExecutionException e) {
                        Log.e("Error", e.toString());
                    }
                } catch(Exception e) {
                    Log.e("Error: ", e.toString());
                }
            }
        });

    }
    public void createDB() {
        DatabaseHandlerV2 loader = new DatabaseHandlerV2(this, 0, this.progressBar, (RelativeLayout) findViewById(R.id.mainID), null, null, null, null);
        try {
            int number = (int)loader.execute().get();
            //Log.e("Error", String.valueOf(number));
        } catch (InterruptedException e) {
            Log.e("Error", e.toString());
        } catch (ExecutionException e) {
            Log.e("Error", e.toString());
        }
    }
    public void isLogIn() {
        try {
            DatabaseHandler db = new DatabaseHandler(this);
            String sUserName = db.getLogInUser();
            if(sUserName != null) {
                Intent myIntent = new Intent(MainScreen.this, MenuScreen.class);
                myIntent.putExtra("key", sUserName); //Optional parameters
                startActivity(myIntent);
            } else {
                return;
            }
        } catch(Exception e) {
            Log.e("Error: ", e.toString());
        }
    }
}