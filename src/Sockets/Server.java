package Sockets;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class Server {
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    public void CreateServer(int port){
        try{
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Server started");
            System.out.println("Waiting for connection...");
            socket = serverSocket.accept();
            System.out.println("Connection accepted");
            in=new DataInputStream(socket.getInputStream());

            out=new DataOutputStream(socket.getOutputStream());

            String line="";
            while(!line.equals("End")){

                try {
                    line = in.readUTF();
                    System.out.println("Received: " + line);
                } catch (EOFException eof) {
                    
                    System.out.println("Sockets.Client disconnected.");
                    break;
                }

            }

            in.close();
            out.close();
            socket.close();

        }
        catch (UnknownHostException u) {

            System.out.println(u);
        }

        catch (IOException i) {

            System.out.println(i);
        }



    }
    public static void main(String[] args){
        Server server = new Server();
        server.CreateServer(8080);
    }
}
