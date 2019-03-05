package chess05;

import java.util.ArrayList;

public class King extends Piece{
    public King(char color, char type, int rowPos, int colPos) {
        super(color, type, rowPos, colPos);
    }

    /* (non-Javadoc)
     * @see chess.Piece#checkMove(int, int, chess.ChessBoard, char)
     */
    @Override
    public boolean checkMove(int row, int col, ChessBoard board, char toMove) {
        System.out.println("CHECKING MOVE OF KING ===============/=/=/=/=/=/=/=========");
        /*if((Math.abs(row-this.rowPos) > 1) || (Math.abs(col-this.colPos) > 1) && toMove=='n') return false;
        if((Math.abs(row-this.rowPos) > 1) || (Math.abs(col-this.colPos) > 1) && toMove=='y') { //The only way a king can move more than one spot is via Castling
            return isCastling(row, col, board);
        }*/
        if(!checkPawnPos(row, col, board))
            return false;
        if(toMove=='y') {
            System.out.println("//// check move \\\\");
            ArrayList<String> km = new ArrayList<String>();
            km = findKingMoves(board);
            if(km.size()>0) {
                for(String s : km) {
                    if(s.equals(""+this.rowPos+this.colPos+row+col)) {
                        return checkPathHelper(board, row, col);
                    }
                }

            }
            return false;

        }
        return true;
    }

    /**
     * @param r
     * @param c
     * @param b
     * @return
     */
//if(b.board[r+1][c-1] instanceof Pawn && b.board[r+1][c-1].color!=b.board[r][c].color && b.board[r+1][c-1].color=='w') {

    private boolean checkPawnPos(int r, int c, ChessBoard b){
        System.out.println("BEFORE FIRST ONE ONE /////////////////////////////////////////////////////");
            if ((r+1<8 && c+1<8) && b.board[r + 1][c + 1] != null) {
                System.out.println("IN FIRST ONE /////////////////////////////////////////////////////");
                if (b.board[r + 1][c + 1] instanceof Pawn && b.board[r + 1][c + 1].color != this.color && b.board[r + 1][c + 1].color == 'w') {
                    return false;
                }
            }if( (r+1<8 && c-1>=0) && b.board[r+1][c-1]!=null){
                System.out.println("IN SECOND ONE /////////////////////////////////////////////////////");
                if(b.board[r+1][c-1] instanceof Pawn && b.board[r+1][c-1].color!=this.color && b.board[r+1][c-1].color=='w') {
                    return false;
                }
            }
            if ((r-1>=0 && c+1<8) && b.board[r-1][c+1]!=null){
                System.out.println("IN third ONE /////////////////////////////////////////////////////");
                if(b.board[r-1][c+1] instanceof Pawn && b.board[r-1][c+1].color!=this.color && b.board[r-1][c+1].color=='b'){
                    return false;
                }
            }
            if( (r-1>=0 && c-1>=0) && b.board[r-1][c-1]!=null){
                System.out.println("IN LAST ONE /////////////////////////////////////////////////////");
                if(b.board[r-1][c-1] instanceof Pawn && b.board[r-1][c-1].color!=this.color && b.board[r-1][c-1].color=='b'){
                    return false;
                }
            }


        return true;
    }
    private boolean isCastling(int row, int col, ChessBoard board) {
        //cant castle while in check
        if(this.moved != 0) {
            return false; //Can only castle with an unmoved king
        }
        if(this.rowPos != row) {
            return false; //Castling is only within the same row
        }
        if(Math.abs(this.colPos-col) != 2) {
            return false; //Must move two spots
        }

        String pathLeft = checkPath(board, row, 1);
        String pathRight = checkPath(board, row, 6);
        if(!pathLeft.equals("n") && !pathRight.equals("n")) {
            return false; //Piece in the way
        }

        //Find rooks, check to see if they have been moved yet
        for(int i = 0; i < 8; i++) {
            if(board.board[row][i].name.equals(this.color+"R")) { //Found a rook
                if(board.board[row][i].moved == 0) {
                    if(i < this.colPos) {
                        if(col == this.colPos-2) {
                            movePieceHelper(row, col, board);
                            board.board[row][i].movePieceHelper(row, this.colPos+1, board);
                            return true;
                        }
                    } else if(i > this.colPos) {
                        if(col == this.colPos + 2) {
                            movePieceHelper(row, col, board);
                            board.board[row][i].movePieceHelper(row, this.colPos-1, board);
                            return true;
                        }
                    }
                }
            }
        }
        return false;

    }
    //8 possible moves
    //must check if any of the pieces of blackPieces can take out white king on move and vice versa

    /**
     * @param b
     * @param i
     * @return
     */
    public ArrayList<String> checkKingPossMoves(ChessBoard b, int i) {
        ArrayList<String> move = new ArrayList<String>();
        int row=this.rowPos;
        int col=this.colPos;
        int cr;
        int cc; char thisKing=this.color;
        String [] directions = {"hl", "hr", "vu", "vd", "ul", "dl", "ur", "dr"};
        String res=null;;
        String result;
        int j=0;
        res=getKingMoveDim(i, row, col);

        cr=Character.getNumericValue(res.charAt(0));
        cc=Character.getNumericValue(res.charAt(1));
        if( (cr>7 || cr<0) || (cc>7 || cc<0) )  return null;
        System.out.println("checking this position: "+res);
        j=0;
        //while(j<8) {//check all possible angles
        String d=directions[j];
        if(b.board[cr][cc]!=null && b.board[cr][cc].color==thisKing) {
            System.out.println("this color = king color");
            return null;
        }
        else if(b.board[cr][cc]!=null && b.board[cr][cc].color!=thisKing) { //if there is an enemy piece
            System.out.println("this color = "+this.color);
            j=0;
            while(j<8) {
                for(Piece p : b.board[j]) {
                    if(p==null)
                        continue;
                    if(p.color==this.color)
                        continue;
                    System.out.println("foudn the piece of: "+this.name);
                    Piece temp = b.board[cr][cc];
                    b.board[cr][cc]=null;
                    if(p.checkMove(cr, cc, b, 'n')) {//if the move is allowable
                        System.out.println("move is allowable");
                        result=p.checkPath(b, cr, cc);//check the path
                        System.out.println("result of checkPath after checkMove in checkKingPossMoves = "+result);
                        if(!result.equals("s")) {
                            move.add(""+cr+cc);
                        }
                    }
                    b.board[cr][cc]=temp;
                }
                j++;
            }
        }
        else if(checkMate.checkKingMove(b, thisKing, cr, cc)==null) {
            System.out.println("final move");
            return null;
        }
        else {
            move.add(""+cr+cc);
        }
        //j++;
        //}
        return move;
    }
    /**
     * @param i
     * @param row
     * @return
     */
    private static String getKingMoveDim(int i, int row, int col) {
        switch(i) {
            case 0:return ""+(row-1)+col;
            case 1:return ""+(row-1)+(col+1);
            case 2:return ""+row+(col+1);
            case 3:return ""+(row+1)+(col+1);
            case 4:return ""+(row+1)+col;
            case 5:return ""+(row+1)+(col-1);
            case 6:return ""+row+(col-1);
            case 7:return ""+(row-1)+(col-1);
        }
        return null;
    }


    //takes in the board. returns the list of moves this king can make without endangering itself
    public ArrayList<String> findKingMoves(ChessBoard b){

        int krow=-1; int kcol=-1;
        if(this.color == 'w') { krow=b.whiteKing.rowPos; kcol=b.whiteKing.colPos;}
        else { krow=b.blackKing.rowPos; kcol=b.blackKing.colPos;}
        Piece temp=null;
        int cr; int cc; String dim;
        ArrayList<String> kmoves = new ArrayList<String>();
        int i=0; int j=0; int k=0;
        boolean cant=false;
        while(i<8) {
            dim=getKingMoveDim(i, krow, kcol);
            System.out.println("dim = "+dim);
            cr = Character.getNumericValue(dim.charAt(0));
            cc = Character.getNumericValue(dim.charAt(1));
            if( (cr>7 || cr<0 || cc>7 || cc<0) || (b.board[cr][cc]!=null && b.board[cr][cc].color == this.color)) {
                i++;
                continue;
            }
            if(b.board[cr][cc]!=null) {
                temp=b.board[cr][cc];
                b.board[cr][cc]=null;
            }
            cant=false;
            while(j<8) {
                System.out.println("- - j = "+j+" - -");
                for(Piece p : b.board[j]) {

                    if(p==null || p.color == this.color)
                        continue;
                    else
                        System.out.println("found --> "+p.name);
                    if(temp!=null) {
                        if(temp.name.equals(p.name))
                            continue;
                    }
                    if(p instanceof Pawn) {
                        if(((Pawn) p).checkPawnDiag(cr, cc, b)) {
                            cant=true;
                            break;
                        }

                    }

                    else if(p.checkMove(cr, cc, b, 'n')) {//if the move is allowable
                        System.out.println(p.name+" can make the move to "+cr+cc);
                        String result=p.checkPath(b, cr, cc);//check the path
                        System.out.println("the result of that move: "+result);
                        if(result.equals("n")||result.equals("s")) {
                            cant=true;
                            break;
                        }
                    }
					/*else{
						System.out.println("INSIDE ELSE");
						kmoves.add(""+krow+kcol+cr+cc);
					}*/

                }
                if(cant==true)
                    break;
                j++;
            }
            if(cant==false)
                kmoves.add(""+krow+kcol+cr+cc);
            b.board[cr][cc]=temp;
            temp=null;
            i++;
            j=0;

        }

        return kmoves;
    }
}
