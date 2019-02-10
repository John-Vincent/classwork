import java.net.*;
import java.io.*;


public class Server 
{
    static ServerSocket socket;
    static int port;


    public static void main(String[] args)
    {
        int i;
        boolean set = false;
        
        //loop through args take last int arg as port number
        for(i = 0; i < args.length; i++)
        {
            try
            {
                port = Integer.parseInt(args[i]);
                set = true;
            }
            catch(Exception e)
            {
                System.err.println(String.format("could not parse port number from %s", args[i]));
            }
        }

        if(!set)
        {
            System.err.println(String.format("must provided port number as argument")); 
            System.exit(-1);
        }
        else if( port < 0 || port > 65535)
        {
            System.err.println(String.format("provided port number is invalid %d, must be in range 0 - 65535", port));
            System.exit(-1);
        }

        try
        {
            socket = new ServerSocket(port);
        }
        catch(IOException e)
        {
            System.err.println(String.format("Error opening port: %s", e.getMessage()));
            System.exit(-1);
        }

        while(true)
        {
            try
            {       
                handleConnection(socket.accept());
            }
            catch(Exception e)
            {
                System.err.println(String.format("Exception when waiting for connection: %s", e.getMessage()));
            }
        }
    }

    public static void handleConnection(Socket socket)
    {
        BufferedReader in = null;
        BufferedWriter out = null;
        String message;
        
        System.out.println("client connected");

        try
        {
            //open input and output streams
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            //while the socket has not been closed
            while(socket.isConnected())
            {
                //try to read a message from client
                message = in.readLine();
                System.out.println(String.format("got message %s", message));
                //then write the length of the message back to the user
                out.write(message.length());
                out.flush();
            }

        }
        catch(Exception e)
        {
            if(e.getMessage() == null)
                System.out.println("client closed connection");
            else
                System.err.println(String.format("Exception when handling client connection: %s", e.getMessage()));
        }

        //handle exceptions when closing io resources before returning to the main server loop 
        try
        {
            in.close();
            out.close();
            socket.close();
        }
        catch(Exception e)
        {
            System.err.println(String.format("failure closing socket: %s", e.getMessage()));
        }
    }
}
