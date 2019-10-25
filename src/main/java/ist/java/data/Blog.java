package ist.java.data;

import java.io.*;
import java.util.LinkedList;
import java.util.List;
import java.io.FileReader;
import org.json.JSONObject;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.json.simple.*;
import org.json.*;


public class Blog implements Readable, Writable{
    List<AbstractPost> tweets;

    public Blog(){
        tweets = new LinkedList<>();
        try{
            // NOTE: this gets called twice ! how many times does Blog() get set? 
            populateFromDisk();            
            // NOTE: reads from the database
        }
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
        System.out.println("Going in while loop");
        try {
            while(json_tweet != null){
                JSONObject obj = new JSONObject(json_tweet); 
                SimpleDateFormat formatter = new SimpleDateFormat("MMM dd, yyyy H:m:s a");
                Date timestamp = formatter.parse(obj.getString("timestamp"));
                tweets.add(new BlogPost(obj.getString("author"),obj.getString("tweet"), timestamp));
                json_tweet = buf.readLine();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        System.out.println("Read from database into tweets object");
        is.close();
    }

    public AbstractPost readOne(){
        // we have database file 
        System.out.println("Tweets # = "+ tweets.size()); 
        // System.out.println("Returning latest tweet"); // make sure it's from top and not bottom? or vice versa
        return tweets.get(0);
    }

    public List<AbstractPost> readAll(){
        System.out.println("In Blog readAll");
        return tweets;
    }


    public List<AbstractPost> readOwnPost(String username){
        System.out.println("Return own tweets");
        // DO SOMETHING
        // returns only tweets from the same author from server

        // TODO need a new variable!!
        List<AbstractPost> myTweets = new LinkedList<>();
        for (AbstractPost tweet : tweets){
            if (tweet.getAuthor().equals(username)==true){
                myTweets.add(tweet);
            }
        }
        return myTweets;
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
                fr.write(tweet.toJson()+"\n");
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

