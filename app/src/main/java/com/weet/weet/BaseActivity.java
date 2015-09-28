package com.weet.weet;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.digits.sdk.android.Digits;
import com.parse.Parse;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterCore;

import io.fabric.sdk.android.Fabric;

public class BaseActivity extends ActionBarActivity {
    private static final String TWITTER_KEY = "nlJ7tLT11SkWGvxJZIWAWiXfu";
    private static final String TWITTER_SECRET = "O2TOJhQ39MQ3mTuUvl2hY8LfS08bQw9Fspm3MvQc5sA8j5R5WL";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Parse.enableLocalDatastore(this);

        Parse.initialize(this, "TAtlc8CTlKZamlNni9egRbIJrWra8r50etoUiQbf", "oKmiR42goQnGM0FW6cQX1E6BCmv8glqacfV5SSNI");
        if (!Fabric.isInitialized()) {
            TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
            Fabric.with(this, new TwitterCore(authConfig), new Digits());
        }
        setContentView(R.layout.activity_base);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_base, menu);
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
