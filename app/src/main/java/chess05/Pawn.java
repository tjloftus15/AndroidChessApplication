package chess05;

import java.util.Scanner;

public class Pawn extends Piece{

    /**
     * @param color
     * @param type
     * @param rowPos
     * @param colPos
     */
    public Pawn(char color, char type, int rowPos, int colPos) {
        super(color, type, rowPos, colPos);
    }

    /* (non-Javadoc)
     * @see chess.Piece#checkMove(int, int, chess.ChessBoard, char)
     */
    public boolean checkMove(int row, int col, ChessBoard board, char toMove) {
        /*int enpassantIndex = row;
        if(this.color == 'w') enpassantIndex = row+1;
        if(this.color == 'b') enpassantIndex = row-1;
        if(enpassantIndex<7 && enpassantIndex>0) {
            if(board.board[enpassantIndex][col] instanceof Pawn) {
                if(isEnpassant(this, (Pawn)board.board[enpassantIndex][col], row)){
                    if(toMove=='y') {
                        movePieceHelper(row, col, board);
                        board.setPiece(enpassantIndex, col, null);
                        return true;
                    }
                }
            }
        }*/
        if(checkDiagonalMove(row, col, board)) {
            if(toMove=='y') {
                if(checkPathHelper(board, row, col)) {
                    if(checkForPromotion()) promote(board);
                }
            }
            return true;
        }
        if(this.colPos != col) return false; //Can only move up or down, not diagonally or sideways
        if(this.color == 'b') { //White pawns can only move "up"
            if((this.rowPos < row-2) || (this.rowPos >= row)) return false; //Can not move backwards, and can only move up a max of 2 spots
            if(this.moved != 0) if(this.rowPos != row-1) return false; //If the piece is already moved, can only go one step
        } else { //Black pieces can only move "down"
            if((this.rowPos > row+2) || (this.rowPos <= row)) return false; //Can not move backwards, and can only move down a max of 2 spots
            if(this.moved != 0) if(this.rowPos != row+1) return false; //If the piece is already moved, can only go one step
        }
        if(toMove=='y') {
            if(checkPathHelper(board, row, col)) {
                if(checkForPromotion()) promote(board);
            }
        }
        return true;
    }

    /**
     * @param tryingToKill
     * @param beingKilled
     * @param row
     * @return
     */
    private boolean isEnpassant(Pawn tryingToKill, Pawn beingKilled, int row) {
        if(beingKilled.moved != 2) return false;//Must have just moved 2 spots
        if(tryingToKill.rowPos != beingKilled.rowPos) return false;//Must be in line
        if(Math.abs(tryingToKill.colPos-beingKilled.colPos) != 1) return false;//Must move one space
        if(tryingToKill.color == 'b') {
            if(tryingToKill.rowPos > row) return false;//Cant move backwards
        } else {
            if(tryingToKill.rowPos < row) return false;//Cant move backwards
        }
        return true;
    }

    /**
     * @param moved
     * @param p
     */
    public static void changeMoved(int moved, Pawn p) {
        p.moved = moved;
    }

    /**
     * @return
     */
    private boolean checkForPromotion() {
        if(this.color == 'w') {
            if(this.rowPos == 0) {
                return true;
            }
        } else {
            if(this.rowPos == 7) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param board
     */
    private void promote(ChessBoard board) {
        /*System.out.println("You may promote your pawn. Please type in 'N' to promote to Knight, 'B' to promote to Bishop,  'R' to promote to Rook, or 'Q' to promote to Queen.");
        Scanner sc2 = new Scanner(System.in);
        String entry = sc2.next();
        if(entry.equals("N")) {
            board.board[this.rowPos][this.colPos] = new Knight(this.color, 'N', this.rowPos, this.colPos);
        } else if(entry.equals("B")) {
            board.board[this.rowPos][this.colPos] = new Bishop(this.color, 'B', this.rowPos, this.colPos);
        } else if(entry.equals("R")) {
            board.board[this.rowPos][this.colPos] = new Rook(this.color, 'R', this.rowPos, this.colPos);
        } else if(entry.equals("Q")) {*/
            board.board[this.rowPos][this.colPos] = new Queen(this.color, 'Q', this.rowPos, this.colPos);
        /*} else {
            System.out.println("Incorrect input, please try again");
            promote(board);
        }
        System.out.println("Your pawn has been promoted");
        System.out.println();*/
    }

    /**
     * @param row
     * @param col
     * @param board
     * @return
     */
    public boolean checkDiagonalMove(int row, int col, ChessBoard board) {
        if(this.color == 'w') if(row != this.rowPos-1) return false;
        else if(row != this.rowPos+1) return false;

        if(board.board[row][col]==null || board.board[row][col].color==this.color)return false;

        if(Math.abs(col-this.colPos) != Math.abs(row-this.rowPos)) return false; //The row and col must form a diagonal

        if(Math.abs(row-this.rowPos)!=1) return false;
        return true;
    }


    public boolean checkPawnDiag(int pr, int pc, ChessBoard b) {

        if(Math.abs(pc-this.colPos) == Math.abs(pr-this.rowPos))
            if(b.board[pr][pc]!=null && b.board[pr][pc].color!=this.color) {
                if(this.color=='w' && this.rowPos-1==pr)
                    return true;
                else if(this.color=='b' && this.rowPos+1==pr)
                    return true;
            }

        return false;
    }









}
