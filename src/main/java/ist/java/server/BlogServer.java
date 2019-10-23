package ist.java.server;

import ist.java.data.Blog;
import ist.java.data.BlogPost;
import java.net.*;
import java.io.*;
import java.util.*;

public class BlogServer {

    private static Blog blog = new Blog();

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
                    // read latest tweet
                    System.out.println("Going to blog to read latest tweet");
                    BlogPost post = (BlogPost)blog.readOne();
                    PrintWriter pr = new PrintWriter(skt.getOutputStream());
                    pr.println(post.toJson());
                    pr.flush();

                }
                else if (choice.equals("2")==true){
                    // read all tweets
                    blog.readAll();
                }
                else if (choice.equals("3")==true){
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
