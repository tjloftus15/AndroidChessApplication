package com.example.chess05.chess;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import games.Game;

public class InputGameName extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_game_name);
    }

    public void onSave(View v){
        EditText edit = (EditText)findViewById(R.id.gamename);
        Editable n = edit.getText();
        String name = n.toString();

        if(name==null) {
            makeToast(v); return;
        }
        Game game=DisplayBoard.getGame();
        game.name = name;
        if(!game.saveGame()){
            makeToast(v); return;
        }
        Toast toast = Toast.makeText(
                getApplicationContext(),
                "Game saved!",
                Toast.LENGTH_LONG);
        toast.show();
        Intent intent = new Intent(this, QuitDecision.class);
        startActivity(intent);

        /*
        try { game = Game.getAllGames(getApplicationContext()); } catch(Exception e){}
        if(game==null){
            Game board = DisplayBoard.getGame();
            board.name=name;
            try { Game.saveGame(board, getApplicationContext()); }catch(Exception e){}
            Intent intent = new Intent(this, QuitDecision.class);
            startActivity(intent);
        }else{
            for(String s : game.moves) {
                if (s.equals(name)) {
                    makeToast(v);
                    return;
                }
            }
            Game board = DisplayBoard.getGame();
            board.name=name;
            try { Game.saveGame(board, getApplicationContext()); }catch(Exception e){}
            Intent intent = new Intent(this, QuitDecision.class);
            startActivity(intent);

        }*/
    }
    public void onBack(View v){
        Intent intent = new Intent(this, SaveGame.class);
        startActivity(intent);
    }

    public void makeToast(View v){
        Toast toast = Toast.makeText(
                getApplicationContext(),
                "Please enter an allowable/available game name",
                Toast.LENGTH_LONG);
        toast.show();
    }
}
