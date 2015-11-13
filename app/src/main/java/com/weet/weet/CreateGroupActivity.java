package com.weet.weet;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.parse.ParseObject;

public class CreateGroupActivity extends ActionBarActivity {
    Toolbar toolbar;
    String groupName;
    String recipientId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_group);

        Intent intent = getIntent();
        recipientId = intent.getStringExtra("recipient_id");

        getWindow().setStatusBarColor(getResources().getColor(R.color.ColorPrimaryDark));
// Creating The Toolbar and setting it as the Toolbar for the activity
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        setTitle(R.string.app_name);
        /*
        Get groupname and store
        */
        final EditText groupNameT = (EditText) findViewById(R.id.groupName);
       /* groupNameT.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    groupName = groupNameT.getText().toString();
                    return true;
                }
                return false;
            }
        }); */

        Button okButton = (Button) findViewById(R.id.button);
        okButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                groupName = groupNameT.getText().toString();
                ParseObject group = new ParseObject("Group");
                group.put("groupName", groupName);
                group.put("members",recipientId);
                group.saveInBackground();
                Intent intent = new Intent(CreateGroupActivity.this, MessagingActivity.class);
                intent.putExtra("recipient_id", recipientId);
                startActivity(intent);

            }
        });


        //Launch ChooseContactsActivity to select members for group

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_create_group, menu);
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
