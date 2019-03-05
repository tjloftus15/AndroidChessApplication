package chess05;

public class Queen extends Piece {

    /**
     * @param color
     * @param type
     * @param rowPos
     * @param colPos
     */
    public Queen(char color, char type, int rowPos, int colPos) {
        super(color, type, rowPos, colPos);
    }

    /* (non-Javadoc)
     * @see chess.Piece#checkMove(int, int, chess.ChessBoard, char)
     */
    @Override
    public boolean checkMove(int row, int col, ChessBoard board, char toMove) {
        if(toMove=='n') {
            if((this.rowPos == row && this.colPos != col) || (this.colPos == col && this.rowPos != row)) return true; //Horizontal or vertical movement
            if(Math.abs(this.rowPos-row) == Math.abs(this.colPos-col)) return true; //Diagonal movement
            return false;
        }
        if((this.rowPos == row && this.colPos != col) || (this.colPos == col && this.rowPos != row)) return checkPathHelper(board, row, col); //Horizontal or vertical movement
        if(Math.abs(this.rowPos-row) == Math.abs(this.colPos-col)) return checkPathHelper(board, row, col);; //Diagonal movement
        return false;
    }
}
