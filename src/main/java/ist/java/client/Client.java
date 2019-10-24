package ist.java.client;

import java.net.*;
import java.io.*;
import java.util.Scanner;

public class Client {
    public static void main(String... args){
        try{
            Socket skt = new Socket("127.0.0.1", 4040);
            String choice = "";
            // TODO: create a while loop!! but how?
            //while(choice.equals("0") != true){
                System.out.println("Enter 1 to write a tweet\n"+
                    "Enter 2 to read latest tweet\n"+
                    "Enter 3 to read all tweets\n"+
                    "Enter 4 to read your tweets\n"+
                    "Enter 0 to exit\n"+
                    "Selection: ");
                
                
                Scanner scanner = new Scanner(System.in);
                choice = scanner.nextLine();
                
                PrintWriter pr = new PrintWriter(skt.getOutputStream());
                // NOTE : client = value at this point!!
                pr.println(choice);
                if (choice.equals("1")==true){
                    System.out.println("Please enter your username: ");
                    pr.println(scanner.nextLine());
                    System.out.println("What do you want to talk about ? (120 characters)");
                    pr.println(scanner.nextLine());
                    // NOTE: This works!! (with server code)
                }
                pr.flush();

           // }

            // To be used when reading from the server !
            InputStreamReader in = new InputStreamReader(skt.getInputStream());
            BufferedReader bf = new BufferedReader(in);

            String str = bf.readLine();
            System.out.println("Server's post = "+ str);

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
