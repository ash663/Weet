package com.weet.weet;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


import com.parse.Parse;
import com.digits.sdk.android.Digits;
import com.parse.ParseUser;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterCore;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.fabric.sdk.android.Fabric;
import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardHeader;
import it.gmariotti.cardslib.library.view.CardViewNative;

public class Home extends BaseActivity  {

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.


    Toolbar toolbar;
    ViewPager pager;
    ViewPagerAdapter adapter;
    SlidingTabLayout tabs;
    CharSequence Titles[]={"Restaurant","Outside"};
    int Numboftabs = 2;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //Setting statusBar color to ColorPrimaryDark
        getWindow().setStatusBarColor(getResources().getColor(R.color.ColorPrimaryDark));

        // Creating The Toolbar and setting it as the Toolbar for the activity
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);


        ParseUser currentUser = ParseUser.getCurrentUser();
        if (currentUser != null) {
            // do stuff with the user. Show groups and stuff
            Card card = new Card(getApplicationContext());

            CardHeader header = new CardHeader(getApplicationContext());

            card.addCardHeader(header);

            CardViewNative cardView = (CardViewNative) findViewById(R.id.carddemo);

            cardView.setCard(card);
        } else {
            // show the signup or login screen. Launch LoginActivity
        }


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.homeFab);

        fab.setOnClickListener(new FloatingActionButton.OnClickListener() {
            public void onClick(View view) {
                //Launch new activity to create Group
                Intent intent = new Intent(Home.this, CreateGroupActivity.class);
                startActivity(intent);
            }
        });

        // RecyclerView rv = (RecyclerView) findViewById(R.id.homeRec);

        //Context context = this;
        //LinearLayoutManager llm = new LinearLayoutManager(context);
        //rv.setLayoutManager(llm);

        /*// Creating The ViewPagerAdapter and Passing Fragment Manager, Titles fot the Tabs and Number Of Tabs.
        adapter =  new ViewPagerAdapter(getSupportFragmentManager(),Titles,Numboftabs);

        // Assigning ViewPager View and setting the adapter
        pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(adapter);

        // Assigning the Sliding Tab Layout View
        tabs = (SlidingTabLayout) findViewById(R.id.tabs);
        tabs.setDistributeEvenly(true); // To make the Tabs Fixed set this true, This makes the tabs Space Evenly in Available width

        // Setting Custom Color for the Scroll bar indicator of the Tab View
        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.tabsScrollColor);
            }
        });

        // Setting the ViewPager For the SlidingTabsLayout
        tabs.setViewPager(pager);*/






    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
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

class Group {
    String name;
    Date created;
    int photoId;

    Group(String name, Date created, int photoId) {
        this.name = name;
        this.created = created;
        this.photoId = photoId;
    }


    private List<Group> groups;

    // This method creates an ArrayList that has three Person objects
// Checkout the project associated with this tutorial on Github if
// you want to use the same images.
    private void initializeData() {
        groups = new ArrayList<>();
        groups.add(new Group("Emma Wilson", new Date(), R.drawable.test));
        //groups.add(new Group("Lavery Maiss", "25 years old", R.drawable.lavery));
       // groups.add(new Group("Lillie Watts", "35 years old", R.drawable.lillie));
    }
}