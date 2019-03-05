package games;

import android.content.Context;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;

import chess05.ChessBoard;



public class Game{
    public String name;
    public ArrayList<String> moves;
    public Date date;
    public static ArrayList<Game> master = new ArrayList<Game>();

    public Game() {
        moves=new ArrayList<String>();
        date = new Date();
        name=null;
    }

    public Game(String n) {
        moves=new ArrayList<String>();
        date = new Date();
        name=n;
    }

    public boolean saveGame(){
        for(Game g : master){
            if(g.name.equals(this.name))
                return false;
        }
        master.add(this);
        return true;
    }

    public String removeLast(){

        String last = this.moves.get(this.moves.size()-1);
        this.moves.remove(last);
        return last;
    }

    public static int indexGet(Game g){
        int i=0;
        for(Game game: master){

            if(game.name.equals(g.name)){
                return i;
            }
            i++;
        }
        return -1;
    }


    public static Game searchGames(String target){
        for(Game g : master){
            if(g.name.equals(target))
                return g;
        }
        return null;

    }
    public static void sortByDate() {
        master.sort(new Comparator<Game>(){
            @Override
            public int compare(Game g1, Game g2){

                if(g1.date.compareTo(g2.date)>0)
                    return -1;
                else
                    return 1;
            }
        });
    }

    public static void sortByName(){
        master.sort(new Comparator<Game>(){
            @Override
            public int compare(Game g1, Game g2){
                if(g1.name.compareTo(g2.name)>0)
                    return 1;
                else
                    return -1;
            }
        });

    }







    /**
    public static Game pastGame(String game, Context context) throws IOException, ClassNotFoundException {
       /* File file = new File("./"+game + ".txt");
        if(!file.exists()) {
            return null;
        }
        //System.out.println(file.getPath());
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
        Game g = (Game) ois.readObject();
        ois.close();
        return g;
        return Game.openFile(context, game);

    }

    public static Game getAllGames(Context context) throws FileNotFoundException, IOException, ClassNotFoundException {
       // File file = new File(System.getProperty("user.dir") +"app/src/main/java/chess05/MasterGameList.txt");
        /* File file = new File("./MasterGameList.txt");
        if(!file.exists())
            return null;
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
        Game g = (Game) ois.readObject();
        ois.close();
        return g;


        Game g=null;
        File directory = context.getFilesDir();
        File file = new File(directory, "MasterGameList.txt");
        if(!file.exists())return null;
        FileInputStream in;
        in = context.openFileInput("MasterGameList.txt");
        InputStreamReader input = new InputStreamReader(in);
        BufferedReader buff = new BufferedReader(input);
        String line;
        line=buff.readLine();
        ArrayList<String> lines = new ArrayList<String>();
        while(line!=null){
            lines.add(line);
            line=buff.readLine();
        }
        for(String s : lines){
            g.moves.add(s);
        }
        return g;



    }

    //master list of games. the arraylist hold all games instead of moves, name is null
    public static boolean saveGame(Game g, Context context){
        File directory = context.getFilesDir();
        File file = new File(directory, "MasterGameList.txt");
        try {
            if (!file.exists()) {
                file.createNewFile();
                g.saveGameFile(context);
                Game master = new Game();
                master.moves.clear();
                master.moves.add(g.name);
                master.name = null;

                master.saveMasterFile(context);
                /*
                ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
                oos.writeObject(master);
                oos.close();
                return true;
            } else {
               // ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
               // Game master = (Game) ois.readObject();
                //ois.close();
                Game master=Game.openFile(context, "MasterGameFile");

                if (master.moves.indexOf(g.name) != -1)
                    return false;
                g.saveGameFile(context);
                master.moves.add(g.name);
                master.moves.sort(null);
                master.saveMasterFile(context);
                /*
                ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
                oos.writeObject(master);
                oos.close();
                return true;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public void undo(String last, ChessBoard b) {
        this.moves.remove(last);
        String start = last.substring(0,2);
        String end = last.substring(3);
        int sr=8-Character.getNumericValue(start.charAt(1));
        int sc=(start.charAt(0))-97;
        int er=8-Character.getNumericValue(end.charAt(1));
        int ec=(end.charAt(0))-97;
        b.board[sr][sc]=b.board[er][ec];
        b.board[er][ec]=null;
    }

    public void saveGameFile(Context context) throws IOException, ClassNotFoundException {
       /* File file = new File("./"+this.name + ".txt");
        if(!file.exists()){
            file.createNewFile();
        }
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
        oos.writeObject(this);
        oos.close();
       try{
           File file = new File(context.getFilesDir(), this.name+".txt");

           FileOutputStream out;
           out = context.openFileOutput(file.getName(), Context.MODE_PRIVATE);
           out.write(this.name.getBytes());
          for(String s : moves){
              out.write(s.getBytes());
          }
       }catch(Exception e){

       }

    }

    public void saveMasterFile(Context context) {
        try {
            File directory = context.getFilesDir();
            File file = new File(directory, "MasterGameList.txt");
            this.name = "MasterGameList";
            FileOutputStream out;
            out = context.openFileOutput(file.getName(), Context.MODE_PRIVATE);
            out.write(this.name.getBytes());
            for (String s : moves) {
                out.write(s.getBytes());
            }
        } catch (Exception e) {

        }

    }

    public static Game openFile(Context context, String gg){
        try {
            File directory = context.getFilesDir();
            File file = new File(directory, gg + ".txt");
            Game g = null;
            if (!file.exists()) return null;
            FileInputStream in;
            in = context.openFileInput("MasterGameList.txt");
            InputStreamReader input = new InputStreamReader(in);
            BufferedReader buff = new BufferedReader(input);
            String line;
            line = buff.readLine();
            ArrayList<String> lines = new ArrayList<String>();
            while (line != null) {
                lines.add(line);
                line = buff.readLine();
            }
            for (String s : lines) {
                g.moves.add(s);
            }
            return g;
        } catch(Exception e){
        e.printStackTrace();}
        return null;

    }

**/

}
