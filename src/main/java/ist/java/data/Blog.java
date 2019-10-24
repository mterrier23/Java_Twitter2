package ist.java.data;

import java.io.*;
import java.util.LinkedList;
import java.util.List;
import java.io.FileReader;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.json.simple.*;
import org.json.*;


public class Blog implements Readable, Writable{
    List<AbstractPost> tweets;

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
        catch (ParseException e){
            e.printStackTrace();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private void populateFromDisk() throws IOException, ClassNotFoundException, ParseException {

        // I finally understand !!!
        // readline for file, each line is a json object
        // need to undo the json stuff and add it to tweets
        // so make a while loop with tweets.add(new BlogPost(author, tweets));
        // !!!

        InputStream is = new FileInputStream("database");
        BufferedReader buf = new BufferedReader(new InputStreamReader(is));
        String json_tweet = buf.readLine();

        while(json_tweet != null){
            // need to undo json stuff to be able to add into this
            JSONObject obj = new JSONObject(json_tweet);
            tweets.add(new BlogPost(obj.getString("author"),obj.getString("tweet"), obj.getString("timestamp"));
            json_tweet = buf.readLine();
        }

        System.out.println("Read from database into tweets object");
        System.out.prinlnt("Top tweet = "+ tweets.getFirst());

       // File database = new File("database");
        //FileInputStream fin = new FileInputStream(database);
        // causing issues :/
       // ObjectInputStream objfin = new ObjectInputStream(new FileInputStream("database"));
       // AbstractPost tweet = (AbstractPost) objfin.readObject();
       // tweets.add(tweet);
       // OutputStream fout = new FileOutputStream("database2");
       // OutputStream fout2 = System.out;

  /*     
        System.out.println("Entering populate while loop");
        int val = fin.read();
        while(val != -1){
            fout2.write(val);
            tweets.add(fin.read().readObject());
            val = fin.read();
        }
        System.out.println("Exiting populate while loop");
*/
        // copies database into a new file database2


        //fin.close();
        //fout.close();
    }

    public AbstractPost readOne(){
        // we have database file 
        System.out.println("in readOne");
        System.out.println("Tweets # = "+ tweets.size());
        //System.out.println(tweets.get(0));
        // tweet is empty at this point :( 
        System.out.println("Returning latest tweet");
        return tweets.get(0);
    }

    public List<AbstractPost> readAll(){
       // tweets = new tweets<AbstractPost>();
        //tweets.add(post);
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

    public void addTweet(AbstractPost tweet){
        // WORKS (:
        tweets.add(tweet);
        System.out.println("Added tweet to the database");
        save();
    }

    public void save(){
        // WORKS FOR ONE ITEM AT LEAST!! WOOHOO!!
        File file = new File("database");
        FileWriter fr = null;
        try {
            fr = new FileWriter(file);
            for (AbstractPost tweet : tweets){
                fr.write(tweet.toJson());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fr.close();
            } catch (IOException e){
                e.printStackTrace();
            }
        }
        System.out.println("Rewrote database");
    }
}

