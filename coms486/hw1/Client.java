import java.net.*;
import java.io.*;

public class Client
{
    static Socket socket;
    static String address;
    static int port;

    public static void main(String[] args)
    {
        InputStreamReader in = null;
        BufferedReader user = null;
        BufferedWriter out = null;
        String message;
        int length;

        //make sure enough args are supplied
        if(args.length != 2)
        {
            System.err.print("must have two arguments, first being address second being port");
        }
        
        //address must be first
        address = args[0];
        try
        {
            //try to parse the port number from the user input
            port = Integer.parseInt(args[1]); 
        }
        catch(Exception e)
        {
            System.err.println(String.format("could not parse port: %s",  args[1]));
        }

        try
        {
            //try to open the socket connection to the server base on the user args
            socket = new Socket(address, port);
        }
        catch(Exception e)
        {
            System.err.println(String.format("Exception attempting to open socket: %s", e.getMessage()));
        }

        try
        {
            //attempt to open the io resources needed to run program
            user = new BufferedReader(new InputStreamReader(System.in));
            in = new InputStreamReader(socket.getInputStream());
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        }
        catch(Exception e)
        {
            System.err.println(String.format("Execption opening input and output streams: %s", e.getMessage()));
            System.exit(-1);
        }

        //have to have nested trys because the closing of streams must be in this try
        try
        {
            //while the connect has not been closed by the server
            while(socket.isConnected())
            {
                //read the users line of input
                message = user.readLine();
                //check if we should exit the program
                if(message.equals("QUIT"))
                    break;
                //if not then write the message to the server
                out.write(message, 0, message.length());
                out.newLine();
                out.flush();
                
                //read the servers response
                length = in.read();
                //print the result
                System.out.println(String.format("From server: the last input's length is %s", length));
            }
        }
        catch(Exception e)
        {
            System.err.println(String.format("Exception during connection: %s", e.getMessage()));
        }
        
        try
        {
            //close all resources after verifying that they exist
            if(in != null)
                in.close();
            if(out != null)
                out.close();
            if(user != null)
                user.close();
            if(socket != null)
                socket.close();
        }
        catch(Exception e)
        {
            System.err.println(String.format("Exception when closing resources: %s", e.getMessage()));
        }
    }
}
