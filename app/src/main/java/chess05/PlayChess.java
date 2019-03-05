package chess05;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.Scanner;

public class PlayChess {
    /**
     * @param args
     * @throws IOException
     */
    public static void main(String args[]) throws IOException {
        String moves = PlayChess.playGame();
		/*Scanner s2 = new Scanner(System.in);

		String input="_";
		System.out.print("The game has ended. Would you like to save this game? (y or n): ");
		input=s2.nextLine();
		//System.out.println();

		if(!input.equals("n") && !input.equals("y")){
			//improper input
			while(!input.equals("y") && !input.equals("n")) {
				System.out.print("Please enter 'y' or 'n'.");
				input=s2.nextLine();
				System.out.println();
			}

		}
		if(input.equals("y")) {
			//save game
			String saveGame=null;
			System.out.print("Please provide a name below for the game to be saved: ");
			saveGame=s2.nextLine();
			System.out.println();

			File saver = new File(System.getProperty("user.dir") +"/src/games/"+saveGame + ".txt");
			while(saver.exists()) {
				System.out.print("There is already a game saved with that name. Please try again below: ");
				saveGame=s2.nextLine();
				System.out.println();
				saver = new File(System.getProperty("user.dir") +"/src/games/"+saveGame + ".txt");
			}
			if(!saver.exists()){ saver.createNewFile(); }
			BufferedWriter output = new BufferedWriter(new FileWriter(saver));
			output.write(new Date().toString()+"\n");
			output.write(saveGame+"\n");
			output.write(moves);
			output.close();
		}
		s2.close();*/

    }

    private static String playGame() {
        ChessBoard cb = new ChessBoard();
        String moves=null;
        //cb.drawBoard();
        boolean draw=false;
        String input="f";
        int i=1;
        Scanner s = new Scanner(System.in);
        while (!input.equals("q")) {
            System.out.println();
            cb.drawBoard();
            if(i%2 == 1) {
                Piece.turn='w';
                System.out.print("White's Move: ");
            } else {
                System.out.print("Black's Move: ");
                Piece.turn='b';
            }

            input=s.nextLine();
            System.out.println();
            System.out.println("-"+input+"-");
            if(input.equals("draw")) {
                if(Piece.turn=='w')
                    System.out.print("White has offered a draw. Will Black accept? (yes or no):");
                else
                    System.out.print("Black has offered a draw. Will White accept? (yes or no):");
                draw=true;
                input=s.nextLine();
                System.out.println();
                if(input.equals("yes")) {
                    System.out.println("Draw accepted. Game over");
                    //System.exit(0);
                    s.close();
                    return moves;
                }else if(!input.equals("yes")) {
                    if(input.equals("no")) {
                        draw=false; continue;
                    }else {
                        while(!input.equals("yes") && !input.equals("no")) {
                            System.out.println();
                            System.out.print("please enter yes or no: ");
                            input=s.nextLine();
                        }if(input.equals("yes")) {
                            System.out.println("Draw accepted. Game over");
                            System.exit(0);
                        }else if(!input.equals("no")) {
                            draw=false; continue;
                        }

                        continue;
                    }

                }
            }

            if(input.equals("resign") || input.equals("Resign")) {
                if(Piece.turn=='w') {
                    System.out.println("White has resigned... Black Wins!!");
                }
                else
                    System.out.println("Black has resigned... White Wins!!");
                //System.exit(0);
                s.close();
                return moves;
            }
            if(input.length()!=5 || input.charAt(2)!=' ') {
                System.out.println("Error: incorrect input. Please input proper board positions");
                System.out.println();
                continue;
            }
            int space = Piece.findSpace(input);
            String start=input.substring(0, space);
            String end=input.substring(space+1);
            int sr=8-Character.getNumericValue(start.charAt(1));
            int sc=(start.charAt(0))-97;
            int row=8-Character.getNumericValue(end.charAt(1));
            int col=(end.charAt(0))-97;
            if(cb.board[sr][sc] == null) {
                System.out.println("You have attempted an illegal move. Please try again");
                continue;
            }
            boolean move=cb.board[sr][sc].movePiece(row, col, cb);
            if(move==false) {
                System.out.println("You have attempted an illegal move. Please try again");
                continue;
            }
            moves=moves+input+"\n";
            i++;
        }
        s.close();
        return moves;

    }
}
