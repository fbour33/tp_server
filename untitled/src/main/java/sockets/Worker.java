package sockets;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.Callable;

public class Worker implements Callable<Void> {
    private int port;
    private int id;
    public Worker(int port, int id){
        this.port = port;
        this.id = id;
    }

    @Override
    public Void call() throws Exception {
        ServerSocket serverSocket = new ServerSocket(this.port);
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

        return null;
    }

    private byte[] getContent(String request) {
        String query = "HTTP/1.1 200 OK\n" +
                "Content-Length: 38\n" +
                "Content-Type: text/html\n\n" +
                "<h1>Je suis un génie (logiciel)</h1>\n";
        return query.getBytes(StandardCharsets.ISO_8859_1);
    }

    private String log(String msg) {
        DateFormat format = new SimpleDateFormat("hh:mm:ss.zzz");
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();

        System.out.printf("%s [Thread %d]: %s", format.format(calendar.getTime()),  this.id, msg);
    }
}
