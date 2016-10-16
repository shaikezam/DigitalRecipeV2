package com.example.shayzambrovski.digitalrecipe;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Shay Zambrovski on 04/09/2016.
 */
public class RegisterScreen extends AppCompatActivity {

    EditText userName;
    EditText password;
    EditText eMail;
    Button register;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_screen);
        bindUI();
    }
    public void bindUI() {
        this.userName = (EditText)findViewById(R.id.user_name_id);
        this.password = (EditText)findViewById(R.id.password_id);
        this.eMail = (EditText)findViewById(R.id.email);
        this.register = (Button)findViewById(R.id.register_id);
        final Context oContext = this;
        this.register.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try{
                    DatabaseHandler db = new DatabaseHandler(oContext);
                    Toast.makeText(getApplicationContext(),String.valueOf(db.getUsersCount()),Toast.LENGTH_SHORT).show();
                    String sUserName = userName.getText().toString();
                    String sPassword = password.getText().toString();
                    String seMail = eMail.getText().toString();
                    String regExpn =
                            "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                                    +"((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                                    +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                                    +"([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                                    +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                                    +"([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";
                    Pattern pattern = Pattern.compile(regExpn,Pattern.CASE_INSENSITIVE);
                    Matcher matcher = pattern.matcher(seMail);
                    //Toast.makeText(getApplicationContext(),"Good",Toast.LENGTH_SHORT).show();
                    if(sUserName.equals("") || sPassword.equals("") || !matcher.matches() ) {
                        Toast.makeText(getApplicationContext(),"Bad",Toast.LENGTH_SHORT).show();
                    } else {
                        db.addUser(new User(sUserName, sPassword));
                    }
                } catch(Exception e) {
                    Log.e("Error: ", e.toString());
                }
            }
        });
    }
}
