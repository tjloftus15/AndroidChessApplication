package com.example.chess05.chess;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import chess05.ChessBoard;
import games.Game;

public class WalkthroughGame extends AppCompatActivity {
    public static boolean draw = false;
    public static Game g;
    public ChessBoard b= new ChessBoard();
    public static char turn='w';
    public ImageAdapter_Pieces pieces;
    public int start=-1;
    public int end = -1;

    int i=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_walkthrough_game);

        g=PastGame.game;

        GridView gridview = (GridView) findViewById(R.id.board);
        gridview.setAdapter(new ImageAdapter(this));

        pieces = new ImageAdapter_Pieces(this);
        GridView grid2 = (GridView) findViewById(R.id.pieces);
        grid2.setAdapter(pieces);


    }

    public void onNext(View v){
        if(i>=g.moves.size()){
            //return. Done
            Toast.makeText(WalkthroughGame.this, "Game walkthrough completed", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, QuitDecision.class);
            startActivity(intent);
            return;
        }
        String move = g.moves.get(i);
        int sr = Character.getNumericValue(move.charAt(0));
        int sc = Character.getNumericValue(move.charAt(1));
        int er = Character.getNumericValue(move.charAt(2));
        int ec = Character.getNumericValue(move.charAt(3));
        //rearrange board
        System.out.println(move);
        //boolean m = b.board[sr][sc].movePiece(er,ec,b);
        start=(sr*8)+sc;
        end=(er*8)+ec;
        pieces.mThumbIds[end]=pieces.mThumbIds[start];
        pieces.mThumbIds[start]=0;
        pieces.notifyDataSetChanged();
        i++;

    }
}
