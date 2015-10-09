package com.weet.weet;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.digits.sdk.android.ContactsUploadResult;
import com.digits.sdk.android.ContactsUploadService;

public class ContactsReceiver extends BroadcastReceiver {
    public ContactsReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        if (ContactsUploadService.UPLOAD_COMPLETE.equals(intent.getAction())) {
            ContactsUploadResult result = intent
                    .getParcelableExtra(ContactsUploadService.UPLOAD_COMPLETE_EXTRA);

            // Post success notification
        } else {
            // Post failure notification
        }
    }
}
