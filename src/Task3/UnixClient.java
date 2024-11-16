package Task3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnixDomainSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class UnixClient {
    public static void main(String[] args) {
        SocketAddress socketAddress = UnixDomainSocketAddress.of("/tmp/unix_socket_java");
        System.out.println("Connecting to server... ");
        // Спроба підключитися до сервера
        try(SocketChannel socketChannel = SocketChannel.open(socketAddress)){
            System.out.println("Connected");
            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
            String line="";
            // Тепер можемо передавати повідомлення
            while(!line.equals("End")){
                line= userInput.readLine();
                ByteBuffer buffer = ByteBuffer.wrap(line.getBytes());
                socketChannel.write(buffer);
            }

        }
        catch(IOException e){
            System.out.println(e);
        }
    }
}
