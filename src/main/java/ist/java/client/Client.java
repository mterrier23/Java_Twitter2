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

            // To be used when reading from the server !
            InputStream in = skt.getInputStream();
            ObjectInputStream oin = new ObjectInputStream(in);

            // To be used when writing to the server
            OutputStream out = skt.getOutputStream();
            ObjectOutputStream oout = new ObjectOutputStream(out);

            BufferedReader bf = new BufferedReader(new InputStreamReader(in));
            

            String choice = "";
            String username = "";
            // TODO: create a while loop!! but how?
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
                //pr.println(choice);
                // TODO: should these strings be on the server side? I think so ...
                if (choice.equals("1")==true){
                    System.out.println("Please enter your username: ");
                    pr.println(scanner.nextLine());
                    System.out.println("What do you want to talk about ? (120 characters)");
                    pr.println(scanner.nextLine());
                    // NOTE: This works!! (with server code)
                }
                pr.flush();

                if (choice.equals("2")==true)
                {            
                    String str = bf.readLine();
                    String tweet = postReq.prettyTweet(str);
                    System.out.println("Latest Tweet:\n"+tweet);
                }

                if (choice.equals("3")==true)
                {
                    String str = bf.readLine();
                    System.out.println("All Tweets:\n");
                    while(str != null){
                        String tweet = postReq.prettyTweet(str);
                        System.out.println(tweet);
                        str = bf.readLine();
                    }
                }
                if (choice.equals("4")==true)
                {
                    // how to read from server: String b = (String) oin.readObject();
                    System.out.println((String) oin.readObject());
                    username = scanner.nextLine();
                    // how to write to server
                    oout.writeObject(username);
                    System.out.println("Your Tweets:\n");
                    String str = (String) oin.readObject();
                    while(str != null){
                        System.out.println(postReq.prettyTweet(str)); 
                        str = (String) oin.readObject();    // this is throwing the error when next has nothing
                        //if(oin.readObject() != null){ str = (String) oin.readObject(); } // gives an error too :(
                    }
                }

            }while(choice != 0);
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
