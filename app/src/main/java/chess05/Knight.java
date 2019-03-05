package chess05;

public class Knight extends Piece{

    /**
     * @param color
     * @param type
     * @param rowPos
     * @param colPos
     */
    public Knight(char color, char type, int rowPos, int colPos) {
        super(color, type, rowPos, colPos);
    }

    /* (non-Javadoc)
     * @see chess.Piece#checkMove(int, int, chess.ChessBoard, char)
     */
    @Override
    public boolean checkMove(int row, int col, ChessBoard board, char toMove) {
        int rowDiff = Math.abs(row-this.rowPos);
        int colDiff = Math.abs(col-this.colPos);
        if(rowDiff > 2 || colDiff > 2) return false; //Moving too many spaces
        if(rowDiff == 2 && colDiff != 1) return false; //Not an L shape movement
        if(rowDiff == 1 && colDiff != 2) return false; //Not an L shape movement
        if(toMove=='y') {
            movePieceHelper(row, col, board);
            return true;
        }
        return true;
    }
}