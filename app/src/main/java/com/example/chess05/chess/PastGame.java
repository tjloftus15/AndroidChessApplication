package com.example.chess05.chess;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import games.Game;


public class PastGame extends AppCompatActivity {
    public ListView listView;
    public static Game game=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_past_game);
        Intent intent = getIntent();
        String message = intent.getStringExtra(SortButtons.EXTRA_MESSAGE);
        //populate list view
        /*game=null;
        try { game = Game.getAllGames(getApplicationContext());
            if(game!=null){
            Toast noPast = Toast.makeText(
                    getApplicationContext(),
                    "gmae.name = "+game.name,
                    Toast.LENGTH_LONG);
            noPast.show();}
        } catch(Exception e){ noPastGames(); return;}
        if(game==null){
            noPastGames();
            return;
        }*/

        if(Game.master.size()==0){
            noPastGames();
            return;
        }
        /*Toast noPast = Toast.makeText(
                getApplicationContext(),
                "message = "+message,
                Toast.LENGTH_LONG);
        noPast.show();*/
        if(message.equals("Date"))
            Game.sortByDate();
        else
            Game.sortByName();

        listView = (ListView) findViewById(R.id.pastList);
        final List<String> pastGames = new ArrayList<String>();
        for(Game g: Game.master){
            pastGames.add(g.name);
        }
        if(pastGames.size()==0) {
            noPastGames();
            return;
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                String game = pastGames.get(position);
                startWalk(game);
            }
        });

        /*pastGames.add("Look it worked");
        pastGames.add("Something");
        pastGames.add("word");
        pastGames.add("Cheese");
        pastGames.add("please");
        pastGames.add("knife");
        pastGames.add("Banana");
        pastGames.add("hot dog");
        pastGames.add("i need a bowl tj for the french fries");
        pastGames.add("I love you');");*/
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_list_item_1, pastGames
        );
        listView.setAdapter(adapter);
    }

    public void noPastGames(){
        Toast.makeText(PastGame.this, "No Past Games", Toast.LENGTH_SHORT).show();

    }

    public void back(View v){
        Intent intent = new Intent(this, SortButtons.class);
        startActivity(intent);
    }

    public void select(View v){
        if(game==null){
            Toast noPast = Toast.makeText(
                    getApplicationContext(),
                    "No Game Selected.",
                    Toast.LENGTH_LONG);
            noPast.show();
            return;
        }
    }

    public void startWalk(String s){
        game = Game.searchGames(s);
        Intent intent = new Intent(this, WalkthroughGame.class);
        startActivity(intent);

    }
}
