package chess05;

import android.widget.Toast;

import com.example.chess05.chess.DisplayBoard;

import java.util.ArrayList;
import java.util.Random;

public abstract class Piece {

    public char color, type;
    public int rowPos, colPos;
    String name;
    public int moved;
    public static char turn;
    public static ArrayList<String> moves;


    /**
     * @param color
     * @param type
     * @param rowPos
     * @param colPos
     */
    public Piece(char color, char type, int rowPos, int colPos) {
        this.name = color + "" + type;
        this.color = color;
        this.type = type;
        this.rowPos = rowPos;
        this.colPos = colPos;
        moved = 0;
    }
    /**
     * @param input
     * @return
     */
    public static int findSpace(String input) {
        int i=0;
        while(input.charAt(i)!=' ')
            i++;
        return i;
    }
    /**
     * @param row
     * @param col
     * @return
     */
    public boolean moveOutOfBounds(int row, int col) {
        if(row < 0 || col < 0) return true;
        if(row > 7 || col > 7) return true;
        return false;
    }
    /**
     * @param r
     * @param c
     * @return
     */
    public boolean checkAvoid(int r, int c){
        int i=0;
        String thisMove=""+this.rowPos+this.colPos+r+c;
        while(i<moves.size()) {
            if(thisMove.equals(moves.get(i))) {
                moves=null;
                return true;
            }
            i++;
        }

        return false;
    }

    /**
     * @param row
     * @param col
     * @param board
     * @return
     */
    public boolean movePiece(int row, int col, ChessBoard board) {
        turn=DisplayBoard.getTurn();
        if(this==null) return false;
        if(this.color != turn) return false;
        //First check to see if the board supports the move, then check to see if the piece supports the move

        if(moves!=null && moves.size()>0) {
            System.out.println("---- the following is the moves print of valid moves before checkAvoid: ----");
            for(String s:moves) {
                System.out.println(s);
            }
            System.out.println("----------------------------------------------------------------------------");
            if(!checkAvoid(row, col)) {
                return false;
            }
        }

        if(moveOutOfBounds(row, col)) return false; //The move is outside the chess board
        if(board.spaceOccupied(row, col) != null) { //There is a piece already at that position
            if(board.spaceOccupied(row, col).color == this.color) return false; //The piece at the position is of the same color
            if(this instanceof Pawn) { //Check to see if you can diagonally take out a piece
                if(Math.abs(col-this.colPos) != Math.abs(row-this.rowPos)) {
                    return false; //The row and col must form a diagonal
                }
                if(this.color == 'w') {
                    if(row != this.rowPos-1) {
                        return false; //
                    }
                } else {
                    if(row != this.rowPos+1) {
                        return false;
                    }
                }
                if(turn=='w') {
                    if(checkMate.friendlyCheck(board, this.rowPos, this.colPos, 'w'))
                        movePieceHelper(row, col, board);
                    else
                        return false;
                }else  if(turn=='b') {
                    if(checkMate.friendlyCheck(board, this.rowPos, this.colPos, 'b'))
                        movePieceHelper(row, col, board);
                    else
                        return false;
                }
                return true;
            }
        }
        return checkMove(row, col, board, 'y');
    }

    /**
     * @param row
     * @param col
     * @param board
     */
    protected void movePieceHelper(int row, int col, ChessBoard board) {
        board.setPiece(row, col, this); //Move the piece to the correct position, kill any opposing piece
        if(this instanceof Pawn && Math.abs(this.rowPos-row) == 2) {
            this.moved = 2;
        } else {
            this.moved = 1;
        }
        board.setPiece(this.rowPos, this.colPos, null); //Change the old position to not have a piece anymore
        this.rowPos = row; //Update the fields of the moved piece
        this.colPos = col;
        King otherKing = this.color == 'w' ? board.blackKing : board.whiteKing;

        //----POTENTIAL CHECK------
		/*if(this.color == 'w') {
			for(int i = 0; i < 8; i++) {
				for(Piece p : board.board[i]) {
					if(p.color == this.color && checkMove(otherKing.rowPos, otherKing.colPos, board, 'n')) {
						System.out.print(otherKing.color == 'b' ? "Black King " : "White King ");
						System.out.println("is in check.");
						board.inCheck = true;
					}
				}
			}
		}*/
    }

    /**
     * @param cb
     * @param row
     * @param col
     * @return
     */
    public boolean checkPathHelper(ChessBoard cb, int row, int col) {
        String move=checkPath(cb, row, col);
        System.out.println("Move = "+move);
        if(checkMate.friendlyCheck(cb, this.rowPos, this.colPos, turn )) {
            //if(checkMate.friendlyCheck(cb, row, col, turn )) {
            if(move.equals("s") || move.equals("sk"))
                return false;
            else if(move.equals("n")) {
                movePieceHelper(row, col, cb);
            } else {
                int oppRow=Character.getNumericValue(move.charAt(0));
                int oppCol=Character.getNumericValue(move.charAt(1));
                movePieceHelper(oppRow, oppCol, cb);
            }
            if(turn=='b') {
                if(checkMate.inCheck(cb, row, col, 'w')) {
                    moves=checkMate.findCheckMate(cb, row, col, 'w');
                    if(moves.size()==0) {
                        DisplayBoard.checkMate('b');
                        return false;
                        //System.out.println("Checkmate acheived... Black Wins!!");
                        //System.exit(0);
                    }
                    System.out.println("White is in Check!");
                    DisplayBoard.checkOnly('w');
                    moves=checkMate.removeDuplicates(moves);
                    System.out.println("---- the following is the moves print of valid moves before return: ----");
                    for(String s:moves) {
                        System.out.println(s);
                    }
                    System.out.println("------------------------------------------------------------------------");
                }


            }else {
                if(checkMate.inCheck(cb, row, col, 'b')) {
                    moves=checkMate.findCheckMate(cb, row, col, 'b');
                    if(moves.size()==0) {
                        DisplayBoard.checkMate('w');
                        return false;
                       // System.out.println("Checkmate acheived... White Wins!!");
                        //System.exit(0);;
                    }
                    //System.out.println("Black is in Check!");
                    DisplayBoard.checkOnly('b');
                    moves=checkMate.removeDuplicates(moves);
                    System.out.println("---- the following is the moves print of valid moves before return: ----");

                    for(String s:moves) {
                        System.out.println(s);
                    }
                    System.out.println("------------------------------------------------------------------------");

                }

            }

            return true;
        }
        return false;
    }

    /**
     * @param cb
     * @param row
     * @param col
     * @return
     */
    public String checkPath(ChessBoard cb, int row, int col) {

        int sr=this.rowPos; int sc=this.colPos; int er=row; int ec=col;
        System.out.println("-------");
        System.out.println("("+sr+","+sc+") --> ("+er+","+ec+")");
        if(sr==er) {//horizontal movement, for: Queen, Rook, King
            if(sc<ec) {//going from left to right
                int currCol=sc+1;
                while(currCol<=ec) {
                    if(cb.board[sr][currCol]!=null) {
                        if(cb.board[sr][currCol].color==turn) {//if your own piece is in the way, must abort move
                            if(cb.board[sr][currCol] instanceof King) {
                                return "sk";
                            }
                            return "s";
                        }
                        if(cb.board[sr][currCol].color!=turn && cb.board[sr][currCol]!=null){
                            return ""+sr+currCol;
                        }
                    }
                    currCol++;
                }


            }
            else if(sc>ec) {//going from right to left
                int currCol=sc-1;
                while(currCol>=ec) {
                    if(cb.board[sr][currCol]!=null) {
                        if(cb.board[sr][currCol].color==turn) {//if your own piece is in the way, must abort move
                            if(cb.board[sr][currCol] instanceof King) {
                                return "sk";
                            }
                            return "s";
                        }
                        if(cb.board[sr][currCol].color!=turn && cb.board[sr][currCol]!=null){
                            return ""+sr+currCol;
                        }
                    }
                    currCol--;
                }


            }
            return "n";
        }
        else if(sc == ec) {//vertical movement, for: Queen, Rook, Pawn, King
            if(sr<er) {
                int currRow=sr+1;
                while(currRow<=er) {
                    if(cb.board[currRow][sc]!=null) {
                        if(cb.board[currRow][sc].color==turn) {//if your own piece is in the way, must abort move
                            if(cb.board[currRow][sc] instanceof King) {
                                return "sk";
                            }
                            return "s";
                        }
                        if(cb.board[currRow][sc].color!=turn && cb.board[currRow][sc]!=null){//if opposing piece in the way
                            return ""+currRow+sc;
                        }
                    }
                    currRow++;
                }
            }
            else if(sr>er) {
                int currRow=sr-1;
                while(currRow>=er) {
                    if(cb.board[currRow][sc]!=null) {
                        if(cb.board[currRow][sc].color==turn) {//if your own piece is in the way, must abort move
                            if(cb.board[currRow][sc] instanceof King) {
                                return "sk";
                            }
                            return "s";
                        }
                        if(cb.board[currRow][sc].color!=turn && cb.board[currRow][sc]!=null){
                            return ""+currRow+sc;
                        }
                    }
                    currRow--;
                }
            }
            return "n";
        }
        else {//diagonal or L movement, for: Queen, Knight, King, Pawn, Bishop
            if(cb.board[sr][sc].type=='N') {//if knight, since knight has unique movement, really only check if final spot has a piece
                if(cb.board[er][ec]==null)
                    return "n";
                if(cb.board[er][ec].color==turn) {//if your own piece is in the way, must abort move
                    if(cb.board[er][ec] instanceof King) {
                        return "sk";
                    }
                    return "s";
                }
                if(cb.board[er][ec].color!=turn){
                    return ""+er+ec;
                }
                else {
                    return "n";
                }
            }
            else {//if not a knight, 4 ways to go diagonal. up and right, down right, up left, down left

                if(sr < er && sc < ec) { //down and right
                    int cr=sr+1; int cc=sc+1;
                    while(cr<=er) {
                        if(cb.board[cr][cc] != null) {
                            if(cb.board[cr][cc].color==turn) {//if your own piece is in the way, must abort move
                                if(cb.board[cr][cc] instanceof King) {
                                    return "sk";
                                }
                                return "s";
                            }
                            if(cb.board[cr][cc].color!=turn && cb.board[cr][cc]!=null){
                                return ""+cr+cc;
                            }
                        }
                        cr++; cc++;
                    }
                } else if(sr < er && sc > ec) { //down and left
                    int cr=sr+1; int cc=sc-1;
                    while(cr<=er) {
                        if(cb.board[cr][cc] != null) {
                            if(cb.board[cr][cc].color==turn) {//if your own piece is in the way, must abort move
                                if(cb.board[cr][cc] instanceof King) {
                                    return "sk";
                                }
                                return "s";
                            }
                            if(cb.board[cr][cc].color!=turn && cb.board[cr][cc]!=null){
                                return ""+cr+cc;
                            }
                        }
                        cr++;
                        cc--;
                    }
                } else if(sr > er && sc < ec) { //up and right
                    int cr=sr-1; int cc=sc+1;
                    while(cr>=er) {
                        if(cb.board[cr][cc] != null) {
                            if(cb.board[cr][cc].color==turn) {//if your own piece is in the way, must abort move
                                if(cb.board[cr][cc] instanceof King) {
                                    return "sk";
                                }
                                return "s";
                            }
                            if(cb.board[cr][cc].color!=turn && cb.board[cr][cc]!=null){
                                return ""+cr+cc;
                            }
                        }
                        cr--;
                        cc++;
                    }
                } else if(sr>er && sc>ec){//up and left
                    /*if(sr>7 || sc>7)
                        return "n";*/
                    int cr=sr-1; int cc=sc-1;
                    while(cr>=er) {
                        if(cb.board[cr][cc] != null) {
                            if(cb.board[cr][cc].color==turn) {//if your own piece is in the way, must abort move
                                if(cb.board[cr][cc] instanceof King) {
                                    return "sk";
                                }
                                return "s";
                            }
                            if(cb.board[cr][cc].color!=turn && cb.board[cr][cc]!=null){
                                return ""+cr+cc;
                            }
                        }
                        cr--; cc++;
                    }
                }
            }
        }
        return "n";
    }

    /**
     *
     */
    public static void changeTurn() {
        if(Piece.turn=='w')Piece.turn='b';
        else Piece.turn='b';
    }

    /**
     * @param row
     * @param col
     * @param board
     * @param toMove
     * @return
     */
    public abstract boolean checkMove(int row, int col, ChessBoard board, char toMove);


    public static String AI_Move(char turn, ChessBoard cb){
        Random rand = new Random();

        int a=0; int b=0; int count=0; int init;
        while(a<8){
            b=0;
            for(Piece p: cb.board[a]){
                if(p==null || p.color!=turn){
                    b++;
                    continue;
                }
                count++;
                b++;
            }
            a++;
        }
        int  n = rand.nextInt(count) ;
        count=n;
        init=n;
        int k=0;
        int j=0;
        int frow=-1;
        int fcol=-1;
        String move=null;
        while(k<8){
            j=0;
            for(Piece p: cb.board[k]){

                if(p==null) {
                    j++;
                    continue;
                }if(p.color!=turn) {
                    j++;
                    continue;
                }
                if(count>0){
                    count--; j++; continue;
                }
                //if(count==init)
                //    return null;
                switch(p.getClass().getName()){
                    case "chess05.Pawn":
                        move=AI_Pawn(k, j, turn, cb);
                        break;
                    case "chess05.Bishop":
                        move=AI_Bishop(k, j, cb);
                        break;
                    case "chess05.Rook":
                        move=AI_Rook(k, j, cb);
                        break;
                    case "chess05.Knight":
                        move=AI_Knight(k, j, cb);
                        break;
                    case "chess05.Queen":
                        move=AI_Queen_King(k, j, cb);
                        break;
                    case "chess05.King":
                        move=AI_Queen_King(k, j, cb);
                        break;
                }
                if(move!=null) {

                    //add stuff to rearrange image
                    //use k,j as start index, and alteration to k,j as end index
                    int r = Character.getNumericValue(move.charAt(0));
                    int c = Character.getNumericValue(move.charAt(1));

                    return ""+k+j+move;
                }
                j++;
            }
            k++;
            if(k==8 && move==null){
                k=0;
                count=0;
                //init++;
            }
        }

        return null;
    }

    public static String AI_Queen_King(int k, int j, ChessBoard cb){
        boolean move=false; int frow=-1; int fcol=-1;
        move=cb.board[k][j].movePiece(k-1, j-1, cb);
        if (move == true) {
            frow = k - 1;
            fcol = j-1;
            return "" + frow + fcol;
        }
        move=cb.board[k][j].movePiece(k-1, j, cb);
        if (move == true) {
            frow = k - 1;
            fcol = j;
            return "" + frow + fcol;
        }
        move=cb.board[k][j].movePiece(k-1, j+1, cb);
        if (move == true) {
            frow = k - 1;
            fcol = j+1;
            return "" + frow + fcol;
        }
        move=cb.board[k][j].movePiece(k, j+1, cb);
        if (move == true) {
            frow = k ;
            fcol = j+1;
            return "" + frow + fcol;
        }
        move=cb.board[k][j].movePiece(k+1, j+1, cb);
        if (move == true) {
            frow = k + 1;
            fcol = j+1;
            return "" + frow + fcol;
        }
        move=cb.board[k][j].movePiece(k+1, j, cb);
        if (move == true) {
            frow = k + 1;
            fcol = j;
            return "" + frow + fcol;
        }
        move=cb.board[k][j].movePiece(k+1, j-1, cb);
        if (move == true) {
            frow = k + 1;
            fcol = j-1;
            return "" + frow + fcol;
        }
        move=cb.board[k][j].movePiece(k, j-1, cb);
        if (move == true) {
            frow = k ;
            fcol = j-1;
            return "" + frow + fcol;
        }
        return null;
    }


    public static String AI_Pawn(int k, int j, char turn, ChessBoard cb) {
        boolean move = false;
        int frow = -1;
        int fcol = -1;
        if (turn == 'w') {
            move = cb.board[k][j].movePiece(k - 1, j, cb);
            if (move == true) {
                frow = k - 1;
                fcol = j;
                return "" + frow + fcol;
            }
        } else {
            move = cb.board[k][j].movePiece(k + 1, j, cb);
            if (move == true) {
                frow = k +1;
                fcol = j;
                return "" + frow + fcol;
            }
        }
        return null;
    }


    public static String AI_Bishop(int k, int j, ChessBoard cb){
        boolean move=false; int frow=-1; int fcol=-1;
        System.out.println("k, j = "+k+", "+j);
        if(cb.board[0][1] ==null)
            System.out.println("this is null");
        move=cb.board[k][j].movePiece(k+1, j+1, cb);
        if(move==true) {
            frow=k+1;
            fcol=j+1;
            return ""+frow+fcol;
        }
        move=cb.board[k][j].movePiece(k+1, j-1, cb);
        if(move==true) {
            frow=k+1;
            fcol=j-1;
            return ""+frow+fcol;
        }
        move=cb.board[k][j].movePiece(k-1, j+1, cb);
        if(move==true) {
            frow=k-1;
            fcol=j+1;
            return ""+frow+fcol;
        }
        move=cb.board[k][j].movePiece(k-1, j-1, cb);
        if(move==true) {
            frow=k-1;
            fcol=j-1;
            return ""+frow+fcol;
        }
        return null;
    }

    public static String AI_Rook(int k, int j, ChessBoard cb){
        boolean move=false; int frow=-1; int fcol=-1;
        move=cb.board[k][j].movePiece(k+1, j, cb);
        if(move==true) {
            frow=k+1;
            fcol=j;
            return ""+frow+fcol;
        }
        move=cb.board[k][j].movePiece(k-1, j, cb);
        if(move==true) {
            frow=k-1;
            fcol=j;
            return ""+frow+fcol;
        }
        move=cb.board[k][j].movePiece(k, j+1, cb);
        if(move==true) {
            frow=k;
            fcol=j+1;
            return ""+frow+fcol;
        }
        move=cb.board[k][j].movePiece(k, j-1, cb);
        if(move==true) {
            frow=k;
            fcol=j-1;
            return ""+frow+fcol;
        }
        return null;


    }

    public static String AI_Knight(int k, int j, ChessBoard cb){
        boolean move=false; int frow=-1; int fcol=-1;
        move=cb.board[k][j].movePiece(k+2, j+1, cb);
        if(move==true) {
            frow=k+2;
            fcol=j+1;
            return ""+frow+fcol;
        }
        move=cb.board[k][j].movePiece(k+2, j-1, cb);
        if(move==true) {
            frow=k+2;
            fcol=j-1;
            return ""+frow+fcol;
        }
        move=cb.board[k][j].movePiece(k-2, j+1, cb);
        if(move==true) {
            frow=k-2;
            fcol=j+1;
            return ""+frow+fcol;
        }
        move=cb.board[k][j].movePiece(k-2, j-1, cb);
        if(move==true) {
            frow=k-2;
            fcol=j-1;
            return ""+frow+fcol;
        }
        /////////

        move=cb.board[k][j].movePiece(k+1, j+2, cb);
        if(move==true) {
            frow=k+1;
            fcol=j+2;
            return ""+frow+fcol;
        }
        move=cb.board[k][j].movePiece(k-1, j+2, cb);
        if(move==true) {
            frow=k-1;
            fcol=j+2;
            return ""+frow+fcol;
        }
        move=cb.board[k][j].movePiece(k+1, j-2, cb);
        if(move==true) {
            frow=k+1;
            fcol=j-2;
            return ""+frow+fcol;
        }
        move=cb.board[k][j].movePiece(k-1, j-2, cb);
        if(move==true) {
            frow=k-1;
            fcol=j-2;
            return ""+frow+fcol;
        }
        return null;
    }
}

