package ist.java.data;

import com.google.gson.Gson;

import java.io.Serializable;
import java.util.Date;

public class BlogPost extends AbstractPost {

    public BlogPost(){
    }

    public BlogPost(String author, String tweet, Date timestamp){
        this.author = author;
        this.tweet = tweet;
        this.timestamp = timestamp;
    }

    public BlogPost(String author, String tweet){
        this.author = author;
        this.tweet = tweet;
        this.timestamp = new Date();
    }

    public void setAuthor(String author){
        this.author = author;
    }

    public void setMessage(String tweet){
        this.tweet = tweet;
    }

    public String getMessage(){
        return this.tweet;
    }

    public void setTime(){
        this.timestamp = new Date();
    }
}