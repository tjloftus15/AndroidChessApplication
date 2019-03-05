package com.example.chess05.chess;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class SortButtons extends AppCompatActivity {
    public static String EXTRA_MESSAGE = "hi";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sort_buttons);
        /*Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
    }

    public void byDate(View v){
        Intent intent = new Intent(this, PastGame.class);
        String message = "Date";
        intent.putExtra(EXTRA_MESSAGE, message);
        Toast.makeText(SortButtons.this, "Sorted By Date", Toast.LENGTH_SHORT).show();
        startActivity(intent);
    }

    public void byName(View v){
        Intent intent = new Intent(this, PastGame.class);
        String message = "Name";
        intent.putExtra(EXTRA_MESSAGE, message);
        Toast.makeText(SortButtons.this, "Sorted By Name", Toast.LENGTH_SHORT).show();
        startActivity(intent);
    }

    public void onMainMenu(View v){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}
