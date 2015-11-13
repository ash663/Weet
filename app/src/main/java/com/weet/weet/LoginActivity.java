package com.weet.weet;


import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.digits.sdk.android.AuthCallback;
import com.digits.sdk.android.DigitsAuthButton;
import com.digits.sdk.android.DigitsException;
import com.digits.sdk.android.DigitsSession;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;


public class LoginActivity extends ActionBarActivity {



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
        digitsButton.setAuthTheme(android.R.style.Theme_Material_NoActionBar);
        //ArrayList<String> allNumbers = new ArrayList<String>();
        //getContacts getContacts = new getContacts();
       // final ArrayList<String> allNumbers = getContacts.ReadPhoneContacts(getApplicationContext());
        digitsButton.setCallback(new AuthCallback() {

            @Override
            public void success(DigitsSession session, final String phoneNumber) {
                // Do something with the session and phone number...launch home.java
                //Send phone number to parse
                final ParseUser user = new ParseUser();
                String phnNumber = session.getPhoneNumber();
                if (!session.isLoggedOutUser()) {

                    user.setUsername(phnNumber);
                    user.setPassword(phnNumber);

                    //user.setEmail("email@example.com");

// other fields can be set just like with ParseObject
                /*user.put("phone", "650-253-0000");*/

                    user.signUpInBackground(new SignUpCallback() {
                        public void done(ParseException e) {
                            if (e == null) {
                                //put below code in CreateGroupActivity instead

                            } else {
                                // Sign up didn't succeed. Look at the ParseException
                                // to figure out what went wrong
                            }
                        }
                    });
                }

                ArrayList<String> allNumbers = new ArrayList<String>();
                Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,null,null, null);
                String phoneNumber1;
                while (phones.moveToNext()) {
                    //String name=phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                    phoneNumber1 = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)).replaceAll("\\D", "");
                    if(phoneNumber1.length() >= 10 && phoneNumber1.charAt(phoneNumber1.length() - 10) >= '7') {
                        phoneNumber1 = "+91" + phoneNumber1.substring(phoneNumber1.length() - 10);
                        allNumbers.add(phoneNumber1);
                    }
                }

                Toast.makeText(getApplicationContext(), allNumbers.toString(), Toast.LENGTH_SHORT).show();

                Set<String> hs = new HashSet<>();
                hs.addAll(allNumbers);
                allNumbers.clear();
                allNumbers.addAll(hs);
                phones.close();
                Intent home = new Intent(LoginActivity.this, Home.class);
                home.putExtra("phNo", phnNumber);
                home.putExtra("contacts", allNumbers);
               // getIntent().putExtra("allNum", allNumbers);
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
