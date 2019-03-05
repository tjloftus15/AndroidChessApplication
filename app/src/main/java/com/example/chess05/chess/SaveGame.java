package com.example.chess05.chess;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class SaveGame extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_game);


    }

    public void onYes(View v){
        Intent intent = new Intent(this, InputGameName.class);
        startActivity(intent);
    }

    public void onNo(View v){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
