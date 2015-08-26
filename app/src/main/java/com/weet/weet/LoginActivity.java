package com.weet.weet;


import android.content.Intent;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;


import com.digits.sdk.android.AuthCallback;
import com.digits.sdk.android.DigitsAuthButton;
import com.digits.sdk.android.DigitsException;
import com.digits.sdk.android.DigitsSession;


public class LoginActivity extends BaseActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        setTitle(R.string.app_name);
        //Setting statusBar color to ColorPrimaryDark
        getWindow().setStatusBarColor(getResources().getColor(R.color.ColorPrimaryDark));

        // Creating The Toolbar and setting it as the Toolbar for the activity
        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        DigitsAuthButton digitsButton = (DigitsAuthButton) findViewById(R.id.auth_button);
        digitsButton.setAuthTheme(R.style.AppTheme);
        digitsButton.setCallback(new AuthCallback() {
            @Override
            public void success(DigitsSession session, String phoneNumber) {
                // Do something with the session and phone number...launch home.java
                //Send phone number to parse
                //ParseUser user = new ParseUser();
                //user.setUsername(phoneNumber);
                //user.setPassword("my pass");
                //user.setEmail("email@example.com");

// other fields can be set just like with ParseObject
                /*user.put("phone", "650-253-0000");

                user.signUpInBackground(new SignUpCallback() {
                    public void done(ParseException e) {
                        if (e == null) {
                            // Hooray! Let them use the app now.
                        } else {
                            // Sign up didn't succeed. Look at the ParseException
                            // to figure out what went wrong
                        }
                    }
                });*/

                Intent home = new Intent (LoginActivity.this, Home.class);
                LoginActivity.this.startActivity(home);


            }

            @Override
            public void failure(DigitsException exception) {
                // Do something on failure
            }
        });

    }

    protected void onStop() {
        super.onStop();
    }


}

