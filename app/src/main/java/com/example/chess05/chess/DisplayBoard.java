package com.example.chess05.chess;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;


import chess05.ChessBoard;
import chess05.Pawn;
import chess05.Piece;
import chess05.Queen;
import games.Game;

public class DisplayBoard extends AppCompatActivity {
     public static boolean draw = false;
     public static Game g;
     public ChessBoard b;
     public static char turn='w';
     public ImageAdapter_Pieces pieces;
     public int start=-1;
     public int end = -1;
     static char winner;
     static DisplayBoard d;
     public DisplayBoard(){ }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_board);

        draw=false;

        GridView gridview = (GridView) findViewById(R.id.board);
        gridview.setAdapter(new ImageAdapter(this));

        /*gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Toast.makeText(DisplayBoard.this, "" + position,
                        Toast.LENGTH_SHORT).show();
            }
        });*/

        pieces = new ImageAdapter_Pieces(this);
        GridView grid2 = (GridView) findViewById(R.id.pieces);
        grid2.setAdapter(pieces);
        /*gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Toast.makeText(DisplayBoard.this, "" + position,
                        Toast.LENGTH_SHORT).show();
            }
        });*/

        //pieces = (ImageAdapter_Pieces) grid2.getAdapter();
        g = new Game();
        turn='w';
        grid2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                /*int sr = position/8;
                int sc=position - ((position/8)*8);
                Toast.makeText(DisplayBoard.this, "postion: " + position+ "--> "+sr+","+sc,
                        Toast.LENGTH_SHORT).show();*/
                if(start==-1) {
                    start = position;
                    //Toast.makeText(DisplayBoard.this, "start position is: "+start,
                            //Toast.LENGTH_SHORT).show();
                    return;
                }else{
                    end=position;
                    //Toast.makeText(DisplayBoard.this, "end position is: "+end,
                            //Toast.LENGTH_SHORT).show();

                    if(makeMove()){ //change image layout
                        /*Toast.makeText(DisplayBoard.this, "moved piece",
                                Toast.LENGTH_SHORT).show();*/
                        draw=false;

                       /*ImageView imageView = (ImageView)v;
                       imageView.setImageResource(pieces.mThumbIds[start]);*/
                        return;

                    }
                    else{
                        return;
                    }
                }
            }
        });

        Intent intent = getIntent();
        //Game game = new Game();
        b=new ChessBoard();
        d= DisplayBoard.this;
       // GridView gridview = (GridView) findViewById(R.id.gridview);
       // gridview.setAdapter(new ImageAdapter(this));
    }

    public boolean makeMove(){
         draw=false;
        int sr; int sc; int er; int ec;
        sr = start/8;
        sc=start - ((start/8)*8);
        er=end/8;
        ec=end - ((end/8)*8);
        b.drawBoard();
        System.out.println("("+sr+","+sc+") --> ("+er+","+ec+")");
        //Toast.makeText(DisplayBoard.this, "("+sr+","+sc+") --> ("+er+","+ec+")",Toast.LENGTH_SHORT).show();
        if(b.board[sr][sc] == null) {
            start=-1;
            end=-1;
            return false;
        }
        boolean isPawn = b.board[sr][sc] instanceof Pawn;
        boolean move = b.board[sr][sc].movePiece(er, ec, b);

        if(move==true) {
            pieces.mThumbIds[end]=pieces.mThumbIds[start];
            pieces.mThumbIds[start]=0;
            /*Toast.makeText(DisplayBoard.this, "moved piece",
                    Toast.LENGTH_SHORT).show();*/

            if(isPawn==true && b.board[er][ec] instanceof Queen){
                if(b.board[er][ec].color=='b' && er==7){
                    pieces.mThumbIds[end]=R.drawable.blackqueen;
                }else if(b.board[er][ec].color=='w' && er==0){
                    pieces.mThumbIds[end]=R.drawable.whitequeen;
                }
            }
            pieces.notifyDataSetChanged();
            g.moves.add("" + sr + sc + er + ec);

            start = -1;
            end = -1;
            if(turn=='w')
                turn='b';
            else
                turn='w';
            return true;
        }
        else{
            /*if(b.board[er][ec]!=null)
                System.out.println(b.board[er][ec]);
            else
                System.out.println("it is null");
            if(b.board[sr][sc]!=null)
                System.out.println(b.board[sr][sc]);
            else
                System.out.println("it is also null");*/

            Toast.makeText(DisplayBoard.this, "Illegal Move attempted. Try again.",
                    Toast.LENGTH_SHORT).show();
            start = -1;
            end = -1;
            return false;
        }
    }

    public void onResign(View v){
         draw=false;
         if(turn=='w') {
             Toast draw = Toast.makeText(
                     getApplicationContext(),
                     "White has resigned.. Black Wins!!!!",
                     Toast.LENGTH_LONG);
             draw.show();
         }
         else{
             Toast draw = Toast.makeText(
                     getApplicationContext(),
                     "Black has resigned.. White Wins!!!!",
                     Toast.LENGTH_LONG);
             draw.show();
         }
        gameFinished('w');
    }

    public void onDrawReq(View v) {
        if (draw == true){
            //change scene. draw accepted. bring to save game screen
            if(turn=='w')
                wDrawConfirmToast(v);
            else
                bDrawConfirmToast(v);



             gameFinished('n');

        }if(draw==false) {
            draw=true;
            if(turn=='w') {
                wDrawOfferToast(v);
                turn = 'b';
            }else{
                bDrawOfferToast(v);
                turn='w';
            }

        }

    }


    public void wDrawOfferToast(View v){
        Toast draw = Toast.makeText(
                getApplicationContext(),
                "White has requested a draw. Hit \"Draw\" to accept",
                Toast.LENGTH_LONG);
        draw.show();
    }

    public void bDrawOfferToast(View v){
        Toast draw = Toast.makeText(
                getApplicationContext(),
                "Black has requested a draw. Hit \"Draw\" to accept",
                Toast.LENGTH_LONG);
        draw.show();
    }

    public void wDrawConfirmToast(View v){
        Toast draw = Toast.makeText(
                getApplicationContext(),
                "Draw accepted by White. The Game is a Draw!",
                Toast.LENGTH_LONG);
        draw.show();
    }
    public void bDrawConfirmToast(View v){
        Toast draw = Toast.makeText(
                getApplicationContext(),
                "Draw accepted by Black. The Game is a Draw!",
                Toast.LENGTH_LONG);
        draw.show();
    }

    public void gameFinished(char winner){
        Intent intent = new Intent(this, SaveGame.class);
        //draw=false;
        startActivity(intent);
    }

    public void on_AI_Move(View v){
        /*Toast draw = Toast.makeText(
                getApplicationContext(),
                "in AI move",
                Toast.LENGTH_LONG);*/
        //draw.show();
        String move = Piece.AI_Move( turn, b);
        if(move!=null){
             /*draw = Toast.makeText(
                    getApplicationContext(),
                    "move!=null",
                    Toast.LENGTH_LONG);
            draw.show();*/
            int sr = Character.getNumericValue(move.charAt(0));
            int sc = Character.getNumericValue(move.charAt(1));
            int er = Character.getNumericValue(move.charAt(2));
            int ec = Character.getNumericValue(move.charAt(3));
            //rearrange board

            start=(sr*8)+sc;
            end=(er*8)+ec;

            //at this point the move in the actual board has already been made. now just move the images
            pieces.mThumbIds[end]=pieces.mThumbIds[start];
            pieces.mThumbIds[start]=0;
            //Toast.makeText(DisplayBoard.this, "moved piece from "+start+" to "+end,Toast.LENGTH_SHORT).show();
            pieces.notifyDataSetChanged();
            g.moves.add("" + sr + sc + er + ec);

            start = -1;
            end = -1;
            if(turn=='w')
                turn='b';
            else
                turn='w';
            draw=false;
        }
        else{
            //game is over. there must be some AI move since it checks the move of each piece. it is impossible that no pieces can move
        }
    }

    public static Game getGame(){
        return g;
    }

    public static char getTurn(){
        return DisplayBoard.turn;
    }


    public static void checkMate(char w){
        winner=w;
        d.nsCheckMate();

    }

    public void nsCheckMate(){
        if(winner=='w')
            Toast.makeText(DisplayBoard.this, "Checkmate achieved... White wins!!", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(DisplayBoard.this, "Checkmate achieved... Black wins!!", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, SaveGame.class);
        startActivity(intent);
    }

    public static void checkOnly(char inCheck) {
        d.checkToast(inCheck);
    }

    public void checkToast(char inCheck){
        if(inCheck=='w')
            Toast.makeText(DisplayBoard.this, "White is in check!!", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(DisplayBoard.this, "Black is in check!!", Toast.LENGTH_LONG).show();

    }

    public void undo(View v){
        if(g.moves.size()==0){
            Toast.makeText(DisplayBoard.this, "No moves to undo", Toast.LENGTH_LONG).show();
        }
        String last = g.removeLast();
        //Toast.makeText(DisplayBoard.this, "last move = "+last, Toast.LENGTH_LONG).show();
        int sr = Character.getNumericValue(last.charAt(0));
        int sc = Character.getNumericValue(last.charAt(1));
        int er = Character.getNumericValue(last.charAt(2));
        int ec = Character.getNumericValue(last.charAt(3));
        b.drawBoard();
        Piece p=b.board[er][ec];
        b.board[sr][sc]=p;
        b.board[er][ec]=null;

        start=(sr*8)+sc;
        end=(er*8)+ec;
        b.drawBoard();
        //at this point the move in the actual board has already been made. now just move the images
        pieces.mThumbIds[start]=pieces.mThumbIds[end];
        pieces.mThumbIds[end]=0;
        pieces.notifyDataSetChanged();
        start = -1;
        end = -1;
        if(turn=='w')
            turn='b';
        else
            turn='w';
        draw=false;
        if(Piece.moves!=null || Piece.moves.size()>0){
            Piece.moves.clear();
            Toast.makeText(DisplayBoard.this, "No longer in check!", Toast.LENGTH_LONG).show();
        }

    }

    public void whoseTurn(View v){
         if(turn=='w')
             Toast.makeText(DisplayBoard.this, "It is White's turn.", Toast.LENGTH_LONG).show();
         else
             Toast.makeText(DisplayBoard.this, "It is Black's turn.", Toast.LENGTH_LONG).show();
    }


}
