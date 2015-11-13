package com.weet.weet;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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
    ListView friendsList;
    ArrayAdapter<String> friendsArrayAdapter;
    String phoneNumber;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_contact);
        friendsList = (ListView) findViewById(R.id.friends_list);

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

                    phoneNumber = user.get("username").toString();
                    friendsArrayList = (ArrayList<String>)user.get("friends");

                    Set<String> set1 = new HashSet<String>(allUsersInDb);
                    refinedFriendsList = new ArrayList<String>();
                    for(String num : friendsArrayList){
                        if(set1.contains(num)) {
                            refinedFriendsList.add(num);
                        }
                    }

                    friendsList = (ListView) findViewById(R.id.friends_list);
                    friendsArrayAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.friends_list_item, refinedFriendsList);
                    friendsList.setAdapter(friendsArrayAdapter);

                } else {
                    Toast.makeText(getApplicationContext(), "Heywho", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
       // getMenuInflater().inflate(R.menu.menu_choose_contact, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
