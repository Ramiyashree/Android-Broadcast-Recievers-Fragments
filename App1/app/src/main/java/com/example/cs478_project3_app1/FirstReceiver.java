package com.example.cs478_project3_app1;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class FirstReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
                // Start ShowWebPageActivity
        Intent tvWebPageIntent = new Intent(context, ShowWebPageActivity.class);
        tvWebPageIntent.putExtra("ShowURL", intent.getStringExtra("ShowURL"));
        context.startActivity(tvWebPageIntent);
        }
}