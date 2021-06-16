package com.example.cs478_project3_app2;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {

    private final static String START_APP3 = "edu.uic.cs478.s19.app3";
    private final static String KABOOM_PERMISSION = "edu.uic.cs478.s19.kaboom";
    private final static String DISPLAY_WEBSITE = "edu.uic.cs478.s19.displayWebsite";

    private final static int KABOOM_REQUEST_CODE = 0;

    BroadcastReceiver receiver2;
    private IntentFilter mFilter ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set button listener
        Button startReceiverButton = findViewById(R.id.startReceiverButton);
        startReceiverButton.setOnClickListener(v -> checkPermissionAndStartReceiver());
        getSupportActionBar().setTitle("APP 2");

    }

    public void checkPermissionAndStartReceiver() {
        // Check for permission
        if (ContextCompat.checkSelfPermission(this,KABOOM_PERMISSION) == PackageManager.PERMISSION_GRANTED) {
            if (receiver2 == null) {
                // Permission granted, receiver is registered
                receiver2 = new SecondReceiver();
                mFilter = new IntentFilter(DISPLAY_WEBSITE);
                mFilter.setPriority(10);
                registerReceiver(receiver2, mFilter);
            // Show toast
            Toast.makeText(this, "Start App 2", Toast.LENGTH_SHORT).show();
        }
            // Start app3
            Intent intent = new Intent();
            intent.setAction(START_APP3);
            startActivity(intent);
        } else {
            // Permission not granted, ask for it
            ActivityCompat.requestPermissions(this,new String[]{KABOOM_PERMISSION}, KABOOM_REQUEST_CODE);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Check whether permission has been granted
        if (requestCode == KABOOM_REQUEST_CODE && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            checkPermissionAndStartReceiver();
        } else {
            Toast.makeText(this, "Premission denied", Toast.LENGTH_SHORT).show();
            finish();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("MainActivity", "Activity Destroyed");
        if (receiver2 != null)
            unregisterReceiver(receiver2);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("MainActivity", "Activity Stopped");

    }

}
