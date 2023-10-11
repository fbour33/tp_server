package sockets;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.Callable;

public class Worker implements Callable<Void> {
    private final ServerSocket serverSocket;
    private final int id;
    public Worker(ServerSocket serverSocket, int id){
        this.serverSocket = serverSocket;
        this.id = id;
    }

    @Override
    public Void call() throws Exception {

        log("Waiting for a new connection...");
        Socket socket = serverSocket.accept();

        log(" A client is connected, we're reading the content");
        InputStream is = socket.getInputStream();
        byte[] bytes = new byte[1024];
        String request = new String(bytes);

        log("Client is asking : " + request);
        OutputStream os = socket.getOutputStream();
        os.write(getContent(request));

        Thread.sleep(10*1000);
        return null;
    }

    private byte[] getContent(String request) {
        String query = "HTTP/1.1 200 OK\n" +
                "Content-Length: 38\n" +
                "Content-Type: text/html\n\n" +
                "<h1>Je suis un génie (logiciel)</h1>\n";
        return query.getBytes(StandardCharsets.ISO_8859_1);
    }

    private void log(String msg) {
        DateFormat format = new SimpleDateFormat("hh:mm:ss.zzz");
        Calendar calendar = Calendar.getInstance();

        System.out.printf("%s [Thread %d]: %s \n", format.format(calendar.getTime()),  this.id, msg);
    }
}
