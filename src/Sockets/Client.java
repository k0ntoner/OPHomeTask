package Sockets;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    public void ConnectClientToServer(String address,int port){
        try{

            System.out.println("Connecting to " + address + ":" + port);
            socket = new Socket("localhost", 8080);
            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
            in=new DataInputStream(socket.getInputStream());
            out=new DataOutputStream(socket.getOutputStream());
            System.out.println("Sesion start");
            String line="";
            while(!line.equals("End")){

                line =userInput.readLine();
                out.writeUTF("Sockets.Client: "+line);
                out.flush();

            }
            userInput.close();
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
        Client client = new Client();
        client.ConnectClientToServer("localhost",8080);
    }
}
