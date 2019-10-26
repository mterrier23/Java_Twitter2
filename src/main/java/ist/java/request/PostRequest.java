package ist.java.request;

import com.google.gson.Gson;
import ist.java.data.*;
import java.util.*;
import java.util.Date;
import java.util.regex.*;
import org.json.JSONObject;
import java.text.SimpleDateFormat;

public class PostRequest{

    public PostRequest(){}

    private static Blog blog = new Blog();
  
    public static String formatPost(String jsonString){
      String tweet = "";
      try{
        JSONObject obj = new JSONObject(jsonString); 
        SimpleDateFormat formatter = new SimpleDateFormat("MMM dd, yyyy H:m:s a");
        Date timestamp = formatter.parse(obj.getString("timestamp"));
        
        tweet = ("Author: "+obj.getString("author")+
                      " Message: "+obj.getString("tweet")+
                      " Date: "+timestamp+"\n"
                      );
      }catch(Exception e){
        e.printStackTrace();
      }
        return tweet;
    }

    public static String prettyTweet(String tweet){
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

    public static List<BlogPost> readTweets(int num, String username){
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
