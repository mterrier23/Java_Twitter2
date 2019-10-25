package ist.java.server;

import ist.java.data.Blog;
import ist.java.data.BlogPost;
import ist.java.request.*;
import java.net.*;
import java.io.*;
import java.util.*;

public class BlogServer {

    private static Blog blog = new Blog();
    // TODO: consider going thru postreq or postsub each time we want to access Blog ? prevents us from calling it twice!
    private static PostRequest postReq = new PostRequest();

    public static void main(String... args){
        try{
            // read the file database for populating our blog
           // blog.Blog();
           

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


            // TODO: create a while loop!! but how??
            //while(choice.equals("0")!=true){
                BufferedReader bf = new BufferedReader(new InputStreamReader(in));
                PrintWriter pr = new PrintWriter(skt.getOutputStream());

                choice = (String) oin.readObject();
                
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
                    blog.addTweet(tweet);
                }
                if (choice.equals("2")==true){
                    // Send client latest tweet
                    BlogPost post = (BlogPost)blog.readOne();
                    pr.println(postReq.formatPost(post.toJson()));  // string
                    pr.flush();

                }
                else if (choice.equals("3")==true){
                    // Send client all tweets
                    // ISSUE: have to be AbstractPost or is blogpost ok?
                    List<BlogPost> posts = postReq.readTweets(1,"");
                    for (BlogPost post : posts){
                        pr.println(postReq.formatPost(post.toJson()));
                    }
                    pr.flush();
                }
                else if (choice.equals("4")==true){
                    // read your tweets

                    // How to write to client: 
                    oout.writeObject("Please enter your name: ");
                    // how to read from server: String b = (String) oin.readObject();
                    username = (String) oin.readObject();
                    System.out.println("Username = "+username);
                    List<BlogPost> posts = postReq.readTweets(0, username);
                    for (BlogPost post : posts){
                        oout.writeObject(postReq.formatPost(post.toJson()));
                    }
                    pr.flush();
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
