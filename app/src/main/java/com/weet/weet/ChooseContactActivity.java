package com.weet.weet;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ChooseContactActivity extends AppCompatActivity {

    ArrayList<String> friendsArrayList, allUsersInDb,refinedFriendsList;
    private List<ParseObject> ob;
    CheckBox friendsList;
    ArrayAdapter<String> friendsArrayAdapter;
    String phoneNumber;
    LinearLayout LinearMain;
    ArrayList<String> recipientID = new ArrayList<String>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_contact);
        LinearMain = (LinearLayout) findViewById(R.id.linear_main);



        ParseQuery<ParseUser> query = ParseUser.getQuery();//query to find all the usernames in _Users
        query.findInBackground(new FindCallback<ParseUser>() {
            public void done(List<ParseUser> objects, ParseException e) {//objects will contain all the users
                if (e == null) {
                    allUsersInDb = new ArrayList<String>();
                    for (int i = 0; i < objects.size(); i++) {
                        if(ParseUser.getCurrentUser() != objects.get(i)) {
                            allUsersInDb.add(objects.get(i).getUsername().toString());//Getting the username of the individual objects and storing it in the ArrayList
                        }
                    }

                    ParseUser user = ParseUser.getCurrentUser();
                    recipientID = new ArrayList<String>();

                    phoneNumber = user.get("username").toString();
                    friendsArrayList = (ArrayList<String>)user.get("friends");

                    Set<String> set1 = new HashSet<String>(allUsersInDb);
                    refinedFriendsList = new ArrayList<String>();
                    for(String num : friendsArrayList){
                        if(set1.contains(num)) {
                            refinedFriendsList.add(num);
                        }
                    }

                    Toast.makeText(getApplicationContext(), refinedFriendsList.toString(), Toast.LENGTH_SHORT).show();

                    for(int i=0;i<refinedFriendsList.size();i++)
                    {
                        friendsList = new CheckBox(ChooseContactActivity.this);
                        friendsList.setId(i);
                        friendsList.setText(refinedFriendsList.get(i));
                        LinearMain.addView(friendsList);
                        friendsList.setOnClickListener(doSomething(friendsList));
                    }


                } else {
                    Toast.makeText(getApplicationContext(), "Heywho", Toast.LENGTH_SHORT).show();
                }

            }
        });
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.homeFab);
        fab.setOnClickListener(new FloatingActionButton.OnClickListener() {
            public void onClick(View view) {
                //Launch new activity to create Group
                Intent intent = new Intent(ChooseContactActivity.this, CreateGroupActivity.class);
                ParseObject group = new ParseObject("Group");
                group.put("members",recipientID.toString());
                group.saveInBackground();
                intent.putExtra("recipient_id",recipientID.toString());
                startActivity(intent);
            }
        });

    }

    public View.OnClickListener doSomething(final Button button) {

        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ParseQuery<ParseUser> query = ParseUser.getQuery();
                query.whereEqualTo("username", (button.getText()).toString());
                query.findInBackground(new FindCallback<ParseUser>() {
                    public void done(List<ParseUser> user, com.parse.ParseException e) {
                        if (e == null) {

                            recipientID.add(user.get(0).getObjectId());
                        }

                    }
                });
                Toast.makeText(getApplicationContext(), recipientID.toString(), Toast.LENGTH_SHORT).show();


            }

        };


    }
}
