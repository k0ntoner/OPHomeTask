package Task3;

import jnr.unixsocket.UnixServerSocketChannel;
import jnr.unixsocket.UnixSocketAddress;
import jnr.unixsocket.UnixSocketChannel;


import java.io.File;
import java.io.IOException;

import java.nio.ByteBuffer;
import java.nio.file.Paths;


public class UnixServer {
//    public static void main(String[] args) {
//        SocketAddress address = UnixDomainSocketAddress.of("/tmp/unix_socket_java");
//        try (ServerSocketChannel serverSocketChannel= ServerSocketChannel.open()){
//            serverSocketChannel.bind(address);
//            System.out.println("Server is waiting for a client...");
//            try (SocketChannel clientChannel = serverSocketChannel.accept()){
//                System.out.println("Client connected");
//                ByteBuffer buffer = ByteBuffer.allocate(1024);
//                while(clientChannel.read(buffer)==-1){
//                    buffer.flip();
//                    System.out.println("Client received: " + new String(buffer.array()));
//                    buffer.clear();
//                }
//            }
//        }
//        catch (IOException e){
//            System.out.println(e);
//        }
//    }
    public static void main(String[] args) {
        //Створюємо файл у папці, до якої буде відключений сервер
        UnixSocketAddress address = new UnixSocketAddress(new File("/tmp/unix_socket_java"));
        try (UnixServerSocketChannel serverChannel = UnixServerSocketChannel.open()) {
            // Біндимо файл для нашого сокета
            serverChannel.configureBlocking(true);
            serverChannel.socket().bind(address);
            System.out.println("Server is waiting for a client...");
            //  Відкриваємо канал та очікуємо на клієнта
            try (UnixSocketChannel clientChannel = serverChannel.accept()) {
                System.out.println("Client connected");
                ByteBuffer buffer = ByteBuffer.allocate(1024);
                // Тепер поки наш клієнт передає дані, та залишається на звʼязку, ми продовжуємо виводити повідомлення отримані від нього
                while (true) {
                    buffer.clear();
                    int bytesRead = clientChannel.read(buffer);

                    if (bytesRead == -1) {
                        //Клієнт відключився
                        System.out.println("Client disconnected");
                        break;
                    }

                    buffer.flip();
                    String message = new String(buffer.array(), 0, buffer.limit());
                    System.out.println("Received from client: " + message);

                    if ("End".equals(message.trim())) {
                        System.out.println("Ending connection as 'End' received");
                        break;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
