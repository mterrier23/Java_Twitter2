package ist.java.server;

import ist.java.data.Blog;
import ist.java.data.BlogPost;
import ist.java.request.*;
import java.net.*;
import java.io.*;
import java.util.*;

public class BlogServer {

    private static Blog blog = new Blog();
    private static PostRequest postReq = new PostRequest();

    public static void main(String... args){
        try{
            // read the file database for populating our blog
           // blog.Blog();
           

            ServerSocket connection = new ServerSocket(4040);
            System.out.println("Waiting for request...");
            Socket skt = connection.accept();

            System.out.println("Client connected");
            String choice = "";


            // TODO: create a while loop!! but how??
            //while(choice.equals("0")!=true){
                InputStreamReader in = new InputStreamReader(skt.getInputStream());
                BufferedReader bf = new BufferedReader(in);

                choice = bf.readLine();
                System.out.println("server choice : " + choice);
                // NOTE: wants us to use "instanceof" here
                // CURRENT: choice = null?
                if (choice.equals("1")==true){
                    BlogPost tweet = new BlogPost();
                    tweet.setAuthor(bf.readLine());
                    tweet.setMessage(bf.readLine());
                    tweet.setTime();
                    System.out.println("Tweet message = "+tweet.getMessage());
                    // NOTE: this works!!! (with client code)
                    // However: Document says that this should be wrapped in 
                    // PostSubmission class before being sent over (ignoring for now)
                    // TODO: add this tweet to tweets
                    blog.addTweet(tweet);
                }
                if (choice.equals("2")==true){
                    // read latest tweet
                    System.out.println("Going to blog to read latest tweet");
                    BlogPost post = (BlogPost)blog.readOne();
                    // TODO: need to convert post into a readable thing
                    PrintWriter pr = new PrintWriter(skt.getOutputStream());
                    pr.println(postReq.formatPost(post.toJson())); // param has to be string
                    // post.toJson() looks like JSON object
                    pr.flush();

                }
                else if (choice.equals("3")==true){
                    // read all tweets
                    blog.readAll();
                }
                else if (choice.equals("4")==true){
                    // read your tweets
                    blog.readOwnPost();
                }
                else if (choice.equals("0")==true){
                    //exit TODO - not sure what to do here!
                }
           // }


            // to write back to client!!
        /*
            PrintWriter pr = new PrintWriter(skt.getOutputStream());
            pr.println("yes");
            pr.flush();
        */
            
        }
        catch(IOException e){
            e.printStackTrace();
            System.out.println("IOException caught");
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
