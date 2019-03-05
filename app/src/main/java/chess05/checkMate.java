package chess05;

import java.util.ArrayList;

/**
 * @author Anmol Rattan
 * @author Timothy Loftus
 */
public class checkMate {
    static int pathRow;
    static int pathCol;
    static int difference;

    /**
     * @param b
     * @param row
     * @param col
     * @param check
     * @return
     */
    public static ArrayList<String> findCheckMate(ChessBoard b, int row, int col, char check) {
        //System.out.println("/////////////// Beginning findCheckMate ////////////////");
        pathCol = col;
        pathRow = row;
        int krow;
        int kcol;
        if (check == 'w') {
            krow = b.whiteKing.rowPos;
            kcol = b.whiteKing.colPos;
        } else {
            krow = b.blackKing.rowPos;
            kcol = b.blackKing.colPos;
        }
        String direction = getDirection(row, col, krow, kcol);
        int i = 0;
        int m = 0;
        ArrayList<String> moves = new ArrayList<String>();
        String result;
        int j = 0;
        int k = 0;
        int a = 0;
        if (check == 'w') {
            while (i < difference) {
                k = 0;
                while (k < 8) {
                    for (Piece p : b.board[k]) {
                        a++;
                        if (p == null)
                            continue;

                        if (p.color == 'w') {
                            //System.out.println("Piece found here at board["+k+"]["+a+"] = "+p.name);
                            if (p instanceof King) {
                                ArrayList<String> t = ((King) p).findKingMoves(b);
                                if (t != null && t.size() > 0) {
                                    int q = 0;
                                    while (q < t.size()) {
                                        moves.add(t.get(q));
                                        q++;
                                    }
                                }
                                continue;
                            }
                            if (p instanceof Pawn) {
                                if (((Pawn) p).checkPawnDiag(pathRow, pathCol, b)) {
                                    moves.add("" + p.rowPos + p.colPos + pathRow + pathCol);
                                    m++;
                                    continue;
                                }

                            }
                            if (p.checkMove(pathRow, pathCol, b, 'n')) {//if the move is allowable
                                result = p.checkPath(b, pathRow, pathCol);//check the path
                                System.out.println("result of checkPath after checkMove = " + result);
                                if (result.equals("" + pathRow + pathCol) || result.equals("n")) {
                                    moves.add("" + p.rowPos + p.colPos + pathRow + pathCol);
                                    m++;
                                }
                            }
                        }

                    }
                    k++;
                    a = 0;
                }

                setPathDim(direction);
                i++;
            }
        } else {
            while (i < difference) {
                k = 0;
                while (k < 8) {
                    for (Piece p : b.board[k]) {
                        if (p == null)
                            continue;
                        if (p.color == 'b') {
                            if (p instanceof King) {
                                j = 0;
                                while (j < 8) {//pass white
                                    ArrayList<String> t = ((King) p).checkKingPossMoves(b, j);
                                    if (t != null && t.size() > 0) {
                                        int q = 0;
                                        while (q < t.size()) {
                                            moves.add("" + p.rowPos + p.colPos + t.get(q));
                                            q++;
                                        }
                                    }
									/*String t=((King) p).checkKingPossMoves(b, j);
									if(t!=null) {
										int nr=Character.getNumericValue(t.charAt(0));
										int nc=Character.getNumericValue(t.charAt(1));
										moves.add(""+p.rowPos+p.colPos+nr+nc);
										m++;
									}*/
                                    j++;
                                }
                                continue;
                            }
                            if (p instanceof Pawn) {
                                if (((Pawn) p).checkPawnDiag(pathRow, pathCol, b)) {
                                    moves.add("" + p.rowPos + p.colPos + pathRow + pathCol);
                                    m++;
                                    continue;
                                }

                            }
                            if (p.checkMove(pathRow, pathCol, b, 'n')) {//if the move is allowable
                                result = p.checkPath(b, pathRow, pathCol);//check the path
                                if (result.equals("" + pathRow + pathCol) || result.equals("n")) {
                                    moves.add("" + p.rowPos + p.colPos + pathRow + pathCol);
                                    m++;
                                }
                            }
                        }

                    }
                    k++;
                }
                setPathDim(direction);
                i++;
            }
        }
        System.out.println("---- the following is the moves print of valid moves findCheckMate return: ----");
        for (String s : moves) {
            System.out.println(s);
        }
        System.out.println("-------------------------------------------------------------------------------");
        //System.out.println("/////////////// Ending findCheckMate ////////////////");
        return moves;
    }

    //color=color of this king

    /**
     * @param b
     * @param color
     * @param krow
     * @param kcol
     * @return
     */
    public static String checkKingMove(ChessBoard b, char color, int krow, int kcol) {

        //String direction=getDirection(row, col, krow, kcol);
        String result;
        int i = 0;
        if (color == 'b') {
            while (i < 7) {
                for (Piece p : b.board[i]) {
                    if (p == null)
                        continue;
                    if (p.color == 'w') {
                        if (p.checkMove(krow, kcol, b, 'n')) {//if the move is allowable
                            result = p.checkPath(b, krow, kcol);//check the path
                            if (result.equals("n") || result.equals("s")) {
                                return null;
                            }
                        }
                    }
                }
                i++;
            }
        } else {
            System.out.println("trying to move white king");
            while (i < 7) {
                for (Piece p : b.board[i]) {
                    if (p == null)
                        continue;
                    if (p.color == 'b') {
                        if (p.rowPos == krow && p.colPos == kcol)
                            continue;
                        System.out.println("this piece i found is: " + p.name);
                        if (p.checkMove(krow, kcol, b, 'n')) {//if the move is allowable
                            System.out.println("move is legal");
                            result = p.checkPath(b, krow, kcol);//check the path
                            System.out.println("result = " + result);
                            if (result.equals("n") || result.equals("s")) {
                                return null;
                            }
                        }
                    }
                }
                i++;
            }
        }

        return "" + krow + kcol;
    }

    /**
     * @param b
     * @param check
     * @return
     */
    public static String getKingDim(ChessBoard b, char check) {
        int i = 0;
        if (check == 'w') {
            while (i < 7) {
                for (Piece p : b.board[i]) {
                    if (p == null)
                        continue;
                    if (p instanceof King && p.color == 'w')
                        return "" + p.rowPos + p.colPos;
                }
                i++;
            }

        } else {
            while (i < 7) {
                for (Piece p : b.board[i]) {
                    if (p == null)
                        continue;
                    if (p instanceof King && p.color == 'b')
                        return "" + p.rowPos + p.colPos;
                }
                i++;
            }
        }
        return null;
    }

    /**
     * @param startRow
     * @param startCol
     * @param endRow
     * @param endCol
     * @return
     */
    public static String getDirection(int startRow, int startCol, int endRow, int endCol) {
        if (startRow == endRow && endCol < startCol) {
            difference = startCol - endCol;
            return "hl";
        }// pathCol=startCol-1;} //horiz left
        else if (startRow == endRow && endCol > startCol) {
            difference = endCol - startCol;
            return "hr";
        }// pathCol=startCol+1;} //horiz right
        else if (startCol == endCol && endRow < startRow) {
            difference = startRow - endRow;
            return "vu";
        }// pathRow=startRow-1;} //vert up
        else if (startCol == endCol && endRow > startRow) {
            difference = endRow - startRow;
            return "vd";
        }// pathRow=startRow+1;}//vert down
        else if (endRow < startRow && endCol < startCol) {
            difference = startRow - endRow;
            return "ul";
        }// pathRow=startRow-1; pathCol=startCol-1;}//up left
        else if (endRow > startRow && endCol < startCol) {
            difference = startCol - endCol;
            return "dl";
        }// pathRow=startRow+1; pathCol=startCol-1;}//down left
        else if (endRow < startRow && endCol > startCol) {
            difference = startRow - endRow;
            return "ur";
        }// pathRow=startRow-1; pathCol=startCol+1;}//up right
        else if (endRow > startRow && endCol > startCol) {
            difference = endCol - startCol;
            return "dr";
        }// pathRow=startRow+1; pathCol=startCol+1;} //down right
        return null;
    }

    /**
     * @param d
     */
    public static void setPathDim(String d) {
        switch (d) {
            case "hl":
                pathCol--;
                break;
            case "hr":
                pathCol++;
                break;
            case "vu":
                pathRow--;
                break;
            case "vd":
                pathRow++;
                break;
            case "ul":
                pathRow--;
                pathCol--;
                break;
            case "dl":
                pathRow++;
                pathCol--;
                break;
            case "ur":
                pathRow--;
                pathCol++;
                break;
            case "dr":
                pathRow++;
                pathCol++;
                break;
        }
    }


    /**
     * @param b
     * @param r
     * @param c
     * @param check
     * @return
     */
    public static boolean inCheck(ChessBoard b, int r, int c, char check) {

        String dim = getKingDim(b, check);
        int krow;
        int kcol;
		/*if(check=='w') {
			krow=ChessBoard.wKingPos[0];
			kcol=ChessBoard.wKingPos[1];
		}else {
			krow=ChessBoard.bKingPos[0];
			kcol=ChessBoard.bKingPos[1];
		}*/
        if (check == 'w') {
            krow = b.whiteKing.rowPos;
            kcol = b.whiteKing.colPos;
        } else {
            krow = b.blackKing.rowPos;
            kcol = b.blackKing.colPos;
        }
        Piece p = b.board[r][c];
        //addition here, 2 lines below
        //if(p==null)
            //return false;
        if(p== null)
            System.out.println("p is null at: "+r+c);
        try {
            if (p.checkMove(krow, kcol, b, 'n')) {
                String result = p.checkPath(b, krow, kcol);
                if (result.equals("" + krow + kcol)) {
                    return true;
                }
            }
        }catch(Exception e){}
        return false;
    }
    //returns false if the move puts you in checkmate

    /**
     * @param b
     * @param r
     * @param c
     * @param check
     * @return
     */

    //must fix
    //simply see if any opposing piece can now checkmate the king
    public static boolean friendlyCheck(ChessBoard b, int r, int c, char check) {

        int krow;
        int kcol;
		/*if(check=='w') {
			krow=ChessBoard.wKingPos[0];
			kcol=ChessBoard.wKingPos[1];
		}else {
			krow=ChessBoard.bKingPos[0];
			kcol=ChessBoard.bKingPos[1];
		}*/
        if (check == 'w') {
            krow = b.whiteKing.rowPos;
            kcol = b.whiteKing.colPos;
        } else {
            krow = b.blackKing.rowPos;
            kcol = b.blackKing.colPos;
        }

        System.out.println("in friendlyCheck:");
        System.out.println("\tking dim = " + krow + "," + kcol);
        System.out.println("\tpiece to move dim = " + r + "," + c);
        char temp = Piece.turn;
//below: if the piece you want to move is in line with it's king, then search the opposite direction from king to end of board starting at that piece
        if (checkInLine(b, r, c, krow, kcol)) {
            System.out.println("in line");
            temp = Piece.turn;
            String endDim = getEndDim(r, c, krow, kcol, b);
            System.out.println("end dim = " + endDim);
            if (endDim == null)
                return true;
            int endRow = Character.getNumericValue(endDim.charAt(0));
            int endCol = Character.getNumericValue(endDim.charAt(1));
            if (b.board[endRow][endCol].color == temp) {
                return true;
            }
            //the below two lines are not working
            Piece.changeTurn();
            String result1 = b.board[endRow][endCol].checkPath(b, krow, kcol);
            //Piece.changeTurn();
            String result2 = b.board[r][c].checkPath(b, krow, kcol);
            System.out.println("result1 = " + result1);
            System.out.println("result2 = " + result2);
            if (result1.equals("" + r + c) && result2.equals("" + krow + kcol)) {//nothing between oppPiece and the piece we want to move, && piece to move and king
                //if(result1.equals(""+r+c)){
                System.out.println("inside final if");
                //Piece.changeTurn();
                //if(b.board[endRow][endCol].checkMove(r, c, b, 'n')) { //this oppPiece can move to take out the king
                if (b.board[endRow][endCol].checkMove(krow, kcol, b, 'n')) {
                    Piece.turn = temp;
                    return false;
                }
            }
        }
        Piece.turn = temp;
        return true;
    }
    //see if the piece you want to move and the king are in line

    /**
     * @param b
     * @param r
     * @param c
     * @param krow
     * @param kcol
     * @return
     */
    private static boolean checkInLine(ChessBoard b, int r, int c, int krow, int kcol) {
        System.out.println("in checkInLine:");
        System.out.println("\tking dim = " + krow + "," + kcol);
        System.out.println("\tpiece to move dim = " + r + "," + c);
        if (r == krow || c == kcol || (Math.abs(r - krow) == Math.abs(c - kcol))) {
            if (b.board[r][c] != null) {
                String result = b.board[r][c].checkPath(b, krow, kcol);
                System.out.println("result of checking path of toMove and king: " + result);
                if (result.equals("sk")) {
                    return true;
                }
            }

        }


        return false;
    }
    //find end of board pos opposite of the king

    /**
     * @param r
     * @param c
     * @param krow
     * @param kcol
     * @return
     */
    private static String getEndDim(int r, int c, int krow, int kcol) {
        String direction = getDirection(r, c, krow, kcol);
        direction = flipDirection(direction);
        pathRow = r;
        pathCol = c;
        setPathDim(direction);
        while ((pathRow > 0 && pathRow < 7) && (pathCol > 0 && pathCol < 7)) {
            setPathDim(direction);

        }
        return "" + pathRow + pathCol;
    }

    /**
     * @param r
     * @param c
     * @param krow
     * @param kcol
     * @param b
     * @return
     */
    private static String getEndDim(int r, int c, int krow, int kcol, ChessBoard b) {
        String direction = getDirection(r, c, krow, kcol);
        direction = flipDirection(direction);
        pathRow = r;
        pathCol = c;
        setPathDim(direction);
        String lastPos = null;
        while ((pathRow > 0 && pathRow < 7) && (pathCol > 0 && pathCol < 7)) {
            if (b.board[pathRow][pathCol] != null) {
                lastPos = "" + pathRow + pathCol;
                break;
            }
            setPathDim(direction);

        }
		/*
		if(b.board[pathRow][pathCol]!=null)
			return ""+pathRow+pathCol;*/
        return lastPos;
    }

    /**
     * @param d
     * @return
     */
    public static String flipDirection(String d) {
        switch (d) {
            case "hl":
                d = "hr";
                break;
            case "hr":
                d = "hl";
                break;
            case "vu":
                d = "vd";
                break;
            case "vd":
                d = "vu";
                break;
            case "ul":
                d = "dr";
                break;
            case "dl":
                d = "ur";
                break;
            case "ur":
                d = "dl";
                break;
            case "dr":
                d = "ul";
                break;
        }
        return d;

    }

    public static ArrayList<String> removeDuplicates(ArrayList<String> moves) {
        System.out.println("remove dupes print");
        for (String s1 : moves) {
            System.out.println(s1);
        }
        for (int i = 0; i < moves.size(); i++) {
            String s = moves.get(i);
            for (int j = i + 1; j < moves.size(); j++) {
                if (moves.get(j).equals(s)) {
                    moves.remove(j);
                    j = i + 1;
                }
            }
        }
        return moves;
    }

    //int row, col are the row and col pos of the piece that has king in check
    //see if the piece that has king in check is killable by the king, who's killing move would not put itself in check
    public static ArrayList<String> reviveKing(ChessBoard b, int row, int col, char check) {
        int krow = -1;
        int kcol = -1;
        ArrayList<String> moves = new ArrayList<String>();
        if (check == 'w') {
            krow = b.whiteKing.rowPos;
            kcol = b.whiteKing.colPos;
        }
        if (check == 'b') {
            krow = b.blackKing.rowPos;
            kcol = b.blackKing.colPos;
        }
        int drow = Math.abs(krow - row);
        int dcol = Math.abs(kcol - col);
        switch (findDimDiff(drow, dcol)) {
            case 0:
                return moves;
            case 1:
                if (row == krow + 1) { //one to the right

                }
                if (row == krow - 1) { //one to the left

                }
            case 2:
            case 3:
        }
        return moves;
    }


    public static int findDimDiff(int r, int c) {
        if (r > 1 || c > 1)
            return 0;
        if (r == 1 && c == 0) {//horizontal
            return 1;
        }
        if (r == 0 && c == 1) {//vertical
            return 2;
        }
        if (r == 1 && c == 1) {//diagonal
            return 3;
        }
        return -1;

    }


}
