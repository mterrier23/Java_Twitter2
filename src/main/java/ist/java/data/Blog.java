package ist.java.data;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

public class Blog implements Readable, Writable{
    List<BlogPost> tweets;

    public Blog(){
        tweets = new LinkedList<>();
        try{
            populateFromDisk();            
            // NOTE: reads from the database
        }
        //TODO
        catch (IOException e){
            e.printStackTrace();
            System.out.println("IOException caught");
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private void populateFromDisk() throws IOException {
        // TODO: should this be void ?
        File database = new File("database");
        InputStream fin = new FileInputStream(database);
        OutputStream fout = new FileOutputStream("database2");
        OutputStream fout2 = System.out;
/*
        int val = fin.read();
        while(val != -1){
            fout.write(val);
            //System.out.println();
           // tweets.add(fin.read())
            val = fin.read();
        }
        // copies database into a new file database2
*/

        fin.close();
        fout.close();
    }

    public AbstractPost readOne(){
        AbstractPost post = new BlogPost("Aaron", "Hey guys!");
        // DO SOMETHING
        // returns latest tweet from server
        System.out.println("Returning latest tweet");
        return post;
    }

    public List<AbstractPost> readAll(){
        System.out.println("Return all tweets");
        // DO SOMETHING
        // returns all tweets from server

        return null;
    }

    public List<AbstractPost> readOwnPost(){
        System.out.println("Return own tweets");
        // DO SOMETHING
        // returns only tweets from the same author from server
        return null;
    }

    public void save(){
        System.out.println("Blog save");
        // write to the database
        //the entire linkedlist tweets
/*
        OutputStream fout = new FileOutputStream("database");
        // use PostSubmission here?
        fout.write(tweets);
        fout.close();
*/

    }
}

