package com.example.chess05.chess;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;



import chess05.PlayChess;
import games.Game;


public class MainActivity extends AppCompatActivity {
    public static String EXTRA_MESSAGE = "hi";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //try{ PlayChess.main(null); }catch(Exception e){}


    }
//https://developer.android.com/training/basics/firstapp/starting-activity#java
    //above is how to send a message from one view to another
    public void onNewGamePush(View v){
        /*Toast newGame = Toast.makeText(
                getApplicationContext(),
                "You've selected new game!",
                Toast.LENGTH_LONG);
        newGame.show();*/
        Intent intent = new Intent(this, DisplayBoard.class);
        //EditText editText = (EditText) findViewById(R.id.editText);
       // String message = editText.getText().toString();
        //intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }

    public void onPastGamePush(View v){
        /*Toast pastGame = Toast.makeText(
                getApplicationContext(),
                "You've selected a past game!",
                Toast.LENGTH_LONG);
        pastGame.show();*/
        if(Game.master.size() == 0){
            Toast.makeText(MainActivity.this, "No Past Games to show", Toast.LENGTH_LONG).show();
            return;
        }
        Intent intent = new Intent(this, SortButtons.class);
        startActivity(intent);
    }
}
































