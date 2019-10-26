package ist.java.data;

import java.io.*;
import java.util.LinkedList;
import java.util.List;
import java.io.FileReader;
import org.json.JSONObject;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Blog implements Readable, Writable{
    List<AbstractPost> tweets;

    public Blog(){
        tweets = new LinkedList<>();
        try{
            populateFromDisk();            
        }
        catch (IOException e){
            e.printStackTrace();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private void populateFromDisk() throws IOException, ClassNotFoundException{
        InputStream is = new FileInputStream("database");
        BufferedReader buf = new BufferedReader(new InputStreamReader(is));
        String json_tweet = buf.readLine();
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
        is.close();
    }

    public AbstractPost readOne(){
        System.out.println("Tweets # = "+ tweets.size()); 
        return tweets.get(tweets.size()-1);
    }

    public List<AbstractPost> readAll(){
        System.out.println("tweets count = "+tweets.size());
        return tweets;
    }

    public List<AbstractPost> readOwnPost(String username){
        System.out.println("tweets count = "+tweets.size());
        List<AbstractPost> myTweets = new LinkedList<>();
        for (AbstractPost tweet : tweets){
            if ((tweet.getAuthor().toLowerCase()).equals(username)==true){
                myTweets.add(tweet);
            }
        }
        return myTweets;
    }

    public void addTweet(AbstractPost tweet){
        tweets.add(tweet);
        System.out.println("Added tweet to the database");
        save();
    }

    public void save(){
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

