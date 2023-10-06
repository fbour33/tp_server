package sockets;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Server {
    public void start() throws IOException {
        ServerSocket serverSocket = new ServerSocket(2134);

        System.out.println("Waiting for a new connection...");
        Socket socket = serverSocket.accept();

        System.out.println(" A client is connected, we're reading the content");
        InputStream is = socket.getInputStream();
        byte bytes[] = new byte[1024];
        int byteRead = is.read(bytes);
        String request = new String(bytes);

        System.out.println("Client is asking : " + request);
        OutputStream os = socket.getOutputStream();
        os.write(getContent(request));
        serverSocket.close();
    }

    private byte[] getContent(String request){
        String query = "HTTP/1.1 200 OK\n" +
                "Content-Length: 38\n" +
                "Content-Type: text/html\n\n" +
                "<h1>Je suis un génie (logiciel)</h1>\n";
        return query.getBytes(StandardCharsets.ISO_8859_1);
    }

    public static void main(String[] args) throws IOException {
        Server server = new Server();
        try {
            server.start();
        }
        catch(IOException ex) {
            System.out.println(ex.toString());
            System.out.println("Could not connect to the server");
        }
    }
}
