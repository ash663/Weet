package com.weet.weet;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;


import com.digits.sdk.android.AuthCallback;
import com.digits.sdk.android.DigitsAuthButton;
import com.digits.sdk.android.DigitsException;
import com.digits.sdk.android.DigitsSession;
import com.parse.ParseException;
import com.parse.ParseObject;
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
               /* Digits.getInstance().getContactsClient().startContactsUpload();
                Digits.getInstance().getContactsClient().lookupContactMatches(null, null,
                        new ContactsCallback<Contacts>() {

                            @Override
                            public void success(Result<Contacts> result) {
                                if (result.data.users != null) {
                                    // Send data to Parse and add all numbers to phoneNumber's friends list
                                    //Todo: Look up Contacts class and send data!
                                    ArrayList Users = result.data.users;
                                    user.put("friends", Users);


                                }
                            }

                            @Override
                            public void failure(TwitterException exception) {
                                // Means no contact on Digits. Show 'Share' app screen
                            }
                        });*/
                ArrayList<String> allNumbers = new ArrayList<String>();
                Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,null,null, null);
                while (phones.moveToNext()) {
                    //String name=phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                    String phoneNumber1 = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    allNumbers.add(phoneNumber1);
                }
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
