package ist.java.client;

import ist.java.request.*;
import java.net.*;
import java.io.*;
import java.util.Scanner;

public class Client {
    
    private static PostRequest postReq = new PostRequest();

    public static void main(String... args){
        try{
            Socket skt = new Socket("127.0.0.1", 4040);

            // To be used when reading from the server
            InputStream in = skt.getInputStream();
            ObjectInputStream oin = new ObjectInputStream(in);

            // To be used when writing to the server
            OutputStream out = skt.getOutputStream();
            ObjectOutputStream oout = new ObjectOutputStream(out);

            BufferedReader bf = new BufferedReader(new InputStreamReader(in));
            
            String choice = "";
            String username = "";
            String message = "";
            int tweetCount;
            do{
                System.out.println("Enter 1 to write a tweet\n"+
                    "Enter 2 to read latest tweet\n"+
                    "Enter 3 to read all tweets\n"+
                    "Enter 4 to read your tweets\n"+
                    "Enter 0 to exit\n"+
                    "Selection: ");
                
                Scanner scanner = new Scanner(System.in);
                choice = scanner.nextLine();
                
                PrintWriter pr = new PrintWriter(skt.getOutputStream());
                // how to write to server
                oout.writeObject(choice);
                if (choice.equals("1")==true)
                {
                    System.out.println((String) oin.readObject());
                    username = scanner.nextLine();
                    oout.writeObject(username);

                    System.out.println((String) oin.readObject());
                    message = scanner.nextLine();
                    oout.writeObject(message);
                    oout.flush();
                    System.out.println("Post published!");
                }
                else if (choice.equals("2")==true)
                {            
                    String str = (String) oin.readObject();
                    String tweet = postReq.prettyTweet(str);
                    System.out.println("Latest Tweet:\n"+tweet);
                }

                else if (choice.equals("3")==true)
                {
                    System.out.println("All Tweets:\n");
                    tweetCount = (int) oin.readObject();
                    
                    String str = (String) oin.readObject();
                    for (int i = 0; i < tweetCount; i++){
                        String tweet = postReq.prettyTweet(str);
                        System.out.println("\n"+tweet);
                        if (i < (tweetCount - 1))
                            str = (String) oin.readObject();
                    }
                }
                else if (choice.equals("4")==true)
                {
                    // how to read from server: String b = (String) oin.readObject();
                    System.out.println((String) oin.readObject());
                    username = scanner.nextLine();
                    oout.writeObject(username);
                    System.out.println("Your Tweets:\n");
                    tweetCount = (int) oin.readObject();

                    if(tweetCount == 0){
                        System.out.println("Sorry, no tweets from this author");
                    }
                    else{
                        String str = (String) oin.readObject();
                        for (int i = 0; i < tweetCount; i++){
                            String tweet = postReq.prettyTweet(str);
                            System.out.println("\n"+tweet);
                            if (i < (tweetCount - 1))
                                str = (String) oin.readObject(); 
                        }
                    }
                }
                System.out.println("\n");

            }while(choice.equals("0")==false);

            System.out.println("Disconnecting from Server, Goodbye!");

            oin.close();
            oout.close();
            in.close();
        }
        catch(IOException e){
            System.out.println("No data found for the socket..");
            e.printStackTrace();
        }
        catch(Exception e){
            e.printStackTrace();
        }

    }
}
