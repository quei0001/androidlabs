package com.example.carlo.androidlabs;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class StartActivity extends AppCompatActivity {

    protected static final String ACTIVITY_NAME = "StartActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        Log.i(ACTIVITY_NAME, "In onCreate()");

        Button backButton = findViewById(R.id.button);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view) {

                Intent intent = new Intent(StartActivity.this, ListItemsActivity.class);
                startActivityForResult(intent, 5);

            }
        });

        Button chatButton = findViewById(R.id.buttonChat);

        chatButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View view) {
                Log.i(ACTIVITY_NAME, "User clicked Start Chat");

                Intent intent = new Intent(StartActivity.this, ChatWindow.class);
                startActivity(intent);
            }
        });

    }//onCreate

    protected void onActivityResult(int requestCode, int responseCode, Intent data){

        if(requestCode == 5) {
            Log.i(ACTIVITY_NAME, "Returned to StartActivity.onActivityResult");
            if(responseCode == Activity.RESULT_OK){
                String messagePassed = data.getStringExtra("Response");

                CharSequence text = messagePassed;
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(this, text, duration);
                toast.show();
            }

        }

    } //onActivityResult
    protected void onResume() {
        super.onResume();
           Log.i(ACTIVITY_NAME, "In onResume()");
    }

    protected void onStart() {
        super.onStart();
        Log.i(ACTIVITY_NAME, "In onStart()");
    }

    protected void onPause() {
        super.onPause();
        Log.i(ACTIVITY_NAME, "In onPause()");
    }

    protected void onStop() {
        super.onStop();
        Log.i(ACTIVITY_NAME, "In onStop()");
    }

    protected void onDestroy() {
        super.onDestroy();
        Log.i(ACTIVITY_NAME, "In onDestroy()");
    }

}
