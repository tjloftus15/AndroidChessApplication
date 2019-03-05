package chess05;

public class Rook extends Piece{

    boolean moved;

    /**
     * @param color
     * @param type
     * @param rowPos
     * @param colPos
     */
    public Rook(char color, char type, int rowPos, int colPos) {
        super(color, type, rowPos, colPos);
        moved = false;
    }

    /* (non-Javadoc)
     * @see chess.Piece#checkMove(int, int, chess.ChessBoard, char)
     */
    @Override
    public boolean checkMove(int row, int col, ChessBoard board, char toMove) {
        if(this.colPos != col && this.rowPos != row) return false; //The piece can only move horizontally or vertically
        if(toMove=='y')
            return checkPathHelper(board, row, col);
        return true;
    }
}
