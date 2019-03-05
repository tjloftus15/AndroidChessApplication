package chess05;

public class Bishop extends Piece{

    /**
     * @param color
     * @param type
     * @param rowPos
     * @param colPos
     */
    public Bishop(char color, char type, int rowPos, int colPos) {
        super(color, type, rowPos, colPos);
    }

    /* (non-Javadoc)
     * @see chess.Piece#checkMove(int, int, chess.ChessBoard, char)
     */
    @Override
    public boolean checkMove(int row, int col, ChessBoard board, char toMove) {
        if(Math.abs(row-this.rowPos) != Math.abs(col-this.colPos)) return false; //Can only move diagonally
        if(toMove=='y')
            return checkPathHelper(board, row, col);
        return true;
    }

}
