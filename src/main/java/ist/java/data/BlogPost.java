package ist.java.data;

import com.google.gson.Gson;

import java.io.Serializable;
import java.util.Date;

public class BlogPost extends AbstractPost {

    public BlogPost(String author, String tweet){
        this.author = author;
        this.tweet = tweet;
        this.timestamp = new Date();
    }

    public void setAuthor(String author){
        this.author = author;
    }

    public String getAuthor(){
        return this.author;
    }

    public void setTweet(String tweet){
        this.tweet = tweet;
    }

    public String getTweet(){
        return this.tweet;
    }

    // public String toJson() {
    //     return new Gson().toJson(this);
    // }

    /*
    @Override
    public String toString() {
        return new StringBuffer(" Author: ").append(this.author)
            .append(" Tweet: ").append(this.tweet).append(" Date: ").append(this.timestamp);
    }
    */
}