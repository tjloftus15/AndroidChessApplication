package chess05;

public class ChessBoard {
    public Piece[][] board;
    public King whiteKing;
    public King blackKing;
    public Piece lastKilledPiece;

    public static int [] wKingPos = new int [2];
    public static int [] bKingPos = new int[2];

    /**
     *
     */
    public ChessBoard() {

        this.board = new Piece[8][8];

        this.board[1][0] = new Pawn('b', 'P', 1, 0);
        this.board[1][1] = new Pawn('b', 'P', 1, 1);
        this.board[1][2] = new Pawn('b', 'P', 1, 2);
        this.board[1][3] = new Pawn('b', 'P', 1, 3);
        this.board[1][4] = new Pawn('b', 'P', 1, 4);
        this.board[1][5] = new Pawn('b', 'P', 1, 5);
        this.board[1][6] = new Pawn('b', 'P', 1, 6);
        this.board[1][7] = new Pawn('b', 'P', 1, 7);
        this.board[0][0] = new Rook('b', 'R', 1, 0);
        this.board[0][1] = new Knight('b', 'N', 0, 1);
        this.board[0][2] = new Bishop('b', 'B', 0, 2);
        this.board[0][4] = blackKing = new King('b', 'K', 0, 4);
        this.board[0][3] = new Queen('b', 'Q', 0, 3);
        this.board[0][5] = new Bishop('b', 'B', 0, 5);
        this.board[0][6] = new Knight('b', 'N', 0, 6);
        this.board[0][7] = new Rook('b', 'R', 0, 7);

        this.board[6][0] = new Pawn('w', 'P', 6, 0);
        this.board[6][1] = new Pawn('w', 'P', 6, 1);
        this.board[6][2] = new Pawn('w', 'P', 6, 2);
        this.board[6][3] = new Pawn('w', 'P', 6, 3);
        this.board[6][4] = new Pawn('w', 'P', 6, 4);
        this.board[6][5] = new Pawn('w', 'P', 6, 5);
        this.board[6][6] = new Pawn('w', 'P', 6, 6);
        this.board[6][7] = new Pawn('w', 'P', 6, 7);
        this.board[7][0] = new Rook('w', 'R', 7, 0);
        this.board[7][1] = new Knight('w', 'N', 7, 1);
        this.board[7][2] = new Bishop('w', 'B', 7, 2);
        this.board[7][4] = whiteKing = new King('w', 'K', 7, 4);
        this.board[7][3] = new Queen('w', 'Q', 7, 3);
        this.board[7][5] = new Bishop('w', 'B', 7, 5);
        this.board[7][6] = new Knight('w', 'N', 7, 6);
        this.board[7][7] = new Rook('w', 'R', 7, 7);

        wKingPos[0]=7;
        wKingPos[1]=4;
        bKingPos[0]=0;
        bKingPos[1]=4;
    }

    /**
     * @param row
     * @param col
     * @return
     */
    public Piece spaceOccupied(int row, int col) {
        if(board[row][col] == null) return null;
        return board[row][col];
    }

    /**
     * @param row
     * @param col
     * @param piece
     */
    public void setPiece(int row, int col, Piece piece) {
        board[row][col] = piece;
    }

    /**
     *
     */
    public void drawBoard() {
        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                if(board[i][j] == null) System.out.print((i+j)%2 == 0 ? "  " : "##");
                else System.out.print(board[i][j].name);
                System.out.print(" ");
            }
            System.out.println(8-i);
        }
        System.out.println(" a  b  c  d  e  f  g  h  ");
    }

}
