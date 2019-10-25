package ist.java.request;

import com.google.gson.Gson;
import ist.java.data.*;
import java.util.*;
import java.util.Date;
import java.util.regex.*;
import org.json.JSONObject;
import java.text.SimpleDateFormat;

//PostRequest is a class used to modelize a request to the server.
//It should contain the action you want to accomplish.
public class PostRequest{

       // TODO: not sure how to use this yet!

    public PostRequest(){}

    private static Blog blog = new Blog();
  

    // convert JSON object to readable string?
    public static String formatPost(String jsonString){
      String tweet = "";
      try{
        // currently looks like : {"author":"Mailys","tweet":"I\u0027m on a train to Paris","timestamp":"Oct 25, 2019 1:52:14 PM"}
        JSONObject obj = new JSONObject(jsonString); 
        SimpleDateFormat formatter = new SimpleDateFormat("MMM dd, yyyy H:m:s a");
        Date timestamp = formatter.parse(obj.getString("timestamp"));
        
        tweet = ("Author: "+obj.getString("author")+
                      " Message: "+obj.getString("tweet")+
                      " Date: "+timestamp+"\n"
                      );
                      // ISSUE: can't put \n at the end of each line because bufferedreader stops at each line
      }catch(Exception e){
        e.printStackTrace();
      }
        return tweet;
    }

    public static String prettyTweet(String tweet){
        // currently looks like:  Author: Aaron Message: Welcome to my crib! Date: Thu Oct 24 09:47:42 CEST 2019
        Pattern p = Pattern.compile("Author: (.+) Message: (.+) Date: (.+)");
        Matcher m = p.matcher(tweet.trim());
        String prettyTweet = "";
        if (m.matches()) {
            prettyTweet = "Author: "+m.group(1) +"\nMessage: " + m.group(2) + "\nDate: " + m.group(3);
        } else {
            System.out.println("No matches to Pattern");
        }
        return prettyTweet.trim();
    }

  // TODO : currently working on!
    public static List<BlogPost> readTweets(int num, String username){
      // TODO change to username = "" when you get wifi to google how to initialize a parameter in java!
        // TODO : cheating a bit but oh well
        List<AbstractPost> posts = new LinkedList<>();
        if (num == 1){
          posts = blog.readAll();
        }
        if (num == 0){
          posts = blog.readOwnPost(username);
        }
        List<BlogPost> blogPosts = new LinkedList<>();
        for (AbstractPost post : posts){
          blogPosts.add((BlogPost)post);
        }
        return blogPosts;
    }
    
}
