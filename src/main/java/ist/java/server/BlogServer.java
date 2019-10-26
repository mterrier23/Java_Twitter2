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
            ServerSocket connection = new ServerSocket(4040);
            System.out.println("Waiting for request...");
            Socket skt = connection.accept();
            System.out.println("Client connected");
            
            // used for sending to the client <-- close at the bottom
            OutputStream out = skt.getOutputStream();
            ObjectOutputStream oout = new ObjectOutputStream(out);

            // used when reading from the client !
            InputStream in = skt.getInputStream();
            ObjectInputStream oin = new ObjectInputStream(in);

            String choice = "";
            String username = "";

            do{
                BufferedReader bf = new BufferedReader(new InputStreamReader(in));
                PrintWriter pr = new PrintWriter(skt.getOutputStream());

                choice = (String) oin.readObject();
                
                System.out.println("server choice : " + choice);
                if (choice.equals("1")==true){                    
                    BlogPost tweet = new BlogPost();
                    oout.writeObject("Please enter your name: ");
                    username = (String) oin.readObject();
                    tweet.setAuthor(username);
                    oout.writeObject("Please write your message (maximum 120 characters): ");
                    tweet.setMessage((String) oin.readObject());
                    tweet.setTime();
                    blog.addTweet(tweet);
                    System.out.println("Finished adding tweet to database");
                }
                if (choice.equals("2")==true)
                {
                    // Send client latest tweet
                    BlogPost post = (BlogPost)blog.readOne();
                    oout.writeObject(postReq.formatPost(post.toJson()));
                    oout.flush();
                }
                else if (choice.equals("3")==true)
                {
                    // Send client all tweets
                    List<BlogPost> posts = postReq.readTweets(1,""); 
                    oout.writeObject(posts.size());
                    for (BlogPost post : posts){
                        oout.writeObject(postReq.formatPost(post.toJson())+"\n");
                    }
                    oout.flush();
                    System.out.println("Finished writing all posts to client");
                }
                else if (choice.equals("4")==true)
                {
                    // Send client own tweets
                    oout.writeObject("Please enter your name: ");
                    username = (String) oin.readObject();
                    System.out.println("Username = "+username);

                    List<BlogPost> posts = postReq.readTweets(0, username.toLowerCase());
                    oout.writeObject(posts.size());
                    for (BlogPost post : posts){
                        oout.writeObject(postReq.formatPost(post.toJson())+"\n");
                    }
                    oout.flush();
                    System.out.println("Finished writing user's posts to client");
                }
                else if (choice.equals("0")==true){
                    // exit from loop + connection
                    break;
                }
            }while(choice.equals("0")==false);

            System.out.println("Disconnecting from Client, Goodbye!");
 
            oout.close();
            in.close();
            
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
