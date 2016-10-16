package com.example.shayzambrovski.digitalrecipe;

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

public class MainScreen extends AppCompatActivity {

    EditText userName;
    EditText password;
    Button logIn;
    Button register;
    Button about;
    Button initData;
    Button deleteData;
    Bundle extras;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        View view = findViewById(android.R.id.content);
        Animation mLoadAnimation = AnimationUtils.loadAnimation(getApplicationContext(), android.R.anim.fade_in);
        mLoadAnimation.setDuration(1200);
        view.startAnimation(mLoadAnimation);
        extras = new Bundle();

        bindUI();

    }
    public void bindUI() {
        final Context oContext = this;
        this.userName = (EditText)findViewById(R.id.user_name_id);

        this.password = (EditText)findViewById(R.id.password_id);

        this.logIn = (Button)findViewById(R.id.log_in_id);

        this.logIn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try{
                    DatabaseHandler db = new DatabaseHandler(oContext);
                    String sUserName = userName.getText().toString();
                    String sPassword = password.getText().toString();
                    //Toast.makeText(getApplicationContext(),"Good",Toast.LENGTH_SHORT).show();
                    if(sUserName.equals("") || sPassword.equals("")) { // if 1 of fields are empty

                        DialogManager dm = new DialogManager(oContext, getResources().getString(R.string.authentication_error), getResources().getString(R.string.fill_fields));
                        dm.show();

                    } else { // user not exists
                        User oUser = db.getUser(sUserName, sPassword);
                        if (oUser == null) {
                            DialogManager dm = new DialogManager(oContext, getResources().getString(R.string.authentication_error), (getResources().getString(R.string.cant_found_user)));
                            dm.show();
                        }
                        else {
                            Intent myIntent = new Intent(MainScreen.this, MenuScreen.class);
                            myIntent.putExtra("key", sUserName); //Optional parameters
                            startActivity(myIntent);
                        }
                    }

                    //Intent myIntent = new Intent(MainScreen.this, RegisterScreen.class);
                    //startActivity(myIntent);
                } catch(Exception e) {
                    Log.e("Error: ", e.toString());
                }
            }
        });

        this.register = (Button)findViewById(R.id.register_id);

        this.register.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try{
                    DatabaseHandler db = new DatabaseHandler(oContext);
                    String sUserName = userName.getText().toString();
                    String sPassword = password.getText().toString();
                    //Toast.makeText(getApplicationContext(),"Good",Toast.LENGTH_SHORT).show();
                    if(sUserName.equals("") || sPassword.equals("")) { // if 1 of fields are empty

                        DialogManager dm = new DialogManager(oContext, getResources().getString(R.string.authentication_error), getResources().getString(R.string.fill_fields));
                        dm.show();

                    } else {
                        long number = db.addUser(new User(sUserName, sPassword));
                        if(number == -1) { // duplicate user name
                            DialogManager dm = new DialogManager(oContext, getResources().getString(R.string.registration_error), getResources().getString(R.string.user_duplicate));
                            dm.show();
                        } else {

                            DialogManager dm = new DialogManager(oContext, getResources().getString(R.string.success),sUserName + ": " + getResources().getString(R.string.registration_success));
                            dm.show();
                        }
                    }

                    //Intent myIntent = new Intent(MainScreen.this, RegisterScreen.class);
                    //startActivity(myIntent);
                } catch(Exception e) {
                    Log.e("Error: ", e.toString());
                }
            }
        });
        this.about = (Button)findViewById(R.id.about_id);
        this.about.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try{
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
                    DatabaseHandler db = new DatabaseHandler(oContext);
                    //Intent myIntent = new Intent(MainScreen.this, AboutScreen.class);
                    //startActivity(myIntent);
                    db.deleteDB();
                } catch(Exception e) {
                    Log.e("Error: ", e.toString());
                }
            }
        });

        this.initData = (Button)findViewById(R.id.init_data);
        this.initData.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try{ // create 2 users with 1 recipe each
                    DatabaseHandler db = new DatabaseHandler(oContext);
                    db.addUser((new User("Shay", "123")));
                    db.addUser((new User("Arnon", "123")));
                    db.addRecipe(new Recipe("Banana Bread", "Mix well by hand until all ingredients are just moistened.\nPour into greased loaf pan and bake 55-65 minutes.\nRemove from pan and cool before slicing.",
                            "Flour@2.5 cups@Suger@1 cup@baking powder@3.5 teaspoons@salt@1 teaspoon@vegetable oil@3 tablespoons@milk@0.75 cup@egg@1@banana@3",
                            "Shay", 1, 1));
                    db.addRecipe(new Recipe("Apple Pie", "Preheat oven to 425°F / 220°C\n" +
                            "For the filling: mix ingredients together just before filling pie crust, dot top of apple mixture with 2 tablespoons of cold butter cubed.\n" +
                            "For the crust:\n" +
                            "Measure flour, salt, sugar, shortening, and butter into a bowl and cut with pastry cutter until bits are no larger than size of a pea.\n" +
                            "Sprinkle water over top and fold mixture until dough sticks together.\n" +
                            "Divide dough into two pieces and roll out for pie top and bottom.\n" +
                            "Fill with apples and top with 2nd piece of dough, making a design of some sort for steam to escape.\n" +
                            "Bake for 40-50 minutes until crust is golden and filling begins to bubble through the crust.",
                            "Flour@2 cups@Suger@1 tablespoon@unsalted butter cold@3 tablespoons@ice water@4 tablespoons@apples thinly sliced@6@cinnamon@0.5 tablespoon@lemon juice@2 teaspoons@baking powder@3.5 teaspoons@salt@1 teaspoon@vegetable oil@3 tablespoons@milk@0.75 cup@egg@1@banana@3",
                            "Arnon", 2, 2));
                } catch(Exception e) {
                    Log.e("Error: ", e.toString());
                }
            }
        });

    }
}