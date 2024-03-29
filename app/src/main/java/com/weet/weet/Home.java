package com.weet.weet;

import android.annotation.TargetApi;
import android.content.Intent;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.util.ArrayList;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardHeader;
import it.gmariotti.cardslib.library.view.CardViewNative;

public class Home extends ActionBarActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    protected Location mLastLocation;
    ParseUser logUser;
    GoogleApiClient mGoogleApiClient;
    Toolbar toolbar;
    ViewPager pager;
    ViewPagerAdapter adapter;
    SlidingTabLayout tabs;
   // CharSequence Titles[]={"Restaurant","Outside"};
    //int Numboftabs = 2;
    ParseObject LocationClass = new ParseObject("Location");

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setTitle(R.string.app_name);
        //Setting statusBar color to ColorPrimaryDark
        getWindow().setStatusBarColor(getResources().getColor(R.color.ColorPrimaryDark));

        // Creating The Toolbar and setting it as the Toolbar for the activity
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);


        buildGoogleApiClient();
        //ArrayList<String> allNumbers = getIntent().getStringArrayListExtra("allNum");
        //ArrayList<String> allNumbers = new ArrayList<String>();
        String phnNumber = getIntent().getStringExtra("phNo");
        ArrayList<String> contacts;// = new ArrayList<>();
        contacts = getIntent().getStringArrayListExtra("contacts");

        logUser = new ParseUser();
        //System.out.println(allNumbers);
        try {
            logUser.logIn(phnNumber, phnNumber);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //TODO: Add Refresh contacts button to do following:
        //ParseUser currentUser = new ParseUser();
        logUser = logUser.getCurrentUser();
        //logUser.put("friends", allNumbers);
        logUser.put("friends", contacts);
        logUser.saveInBackground();
        LocationClass.put("Username", logUser.getObjectId());
        if (logUser != null) {
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
                Intent intent = new Intent(Home.this, ChooseContactActivity.class);
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

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

    }

    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    public void onConnected(Bundle connectionHint) {
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        if (mLastLocation != null) {
            //mLatitudeText.setText(String.valueOf(mLastLocation.getLatitude()));
            //mLongitudeText.setText(String.valueOf(mLastLocation.getLongitude()));
            //ParseGeoPoint point = new ParseGeoPoint(mLastLocation.getLatitude(), mLastLocation.getLongitude());
            //logUser.put("location", point);
            //logUser.put("latitude", mLastLocation.getLatitude());
            //logUser.put("longitude", mLastLocation.getLatitude());
            //logUser.saveInBackground();
            LocationClass.put("latitude", mLastLocation.getLatitude());
            LocationClass.put("longitude", mLastLocation.getLongitude());
            LocationClass.saveInBackground();
        }
    }


    public void onConnectionSuspended(int cause) {
        // The connection has been interrupted.
        // Disable any UI components that depend on Google APIs
        // until onConnected() is called.
    }

    public void onConnectionFailed(ConnectionResult result) {
        // This callback is important for handling errors that
        // may occur while attempting to connect with Google.
        //
        // More about this in the 'Handle Connection Failures' section.

    }

}

/*class Group {
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
}*/