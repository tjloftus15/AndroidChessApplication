package com.example.chess05.chess;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class QuitDecision extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quit_decision);
    }

    public void quit(View v){
        System.exit(0);
    }

    public void main(View v){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
