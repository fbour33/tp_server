package sockets;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Queue;
import java.util.concurrent.*;

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

        log("Client is asking : " + request);
        OutputStream os = socket.getOutputStream();
        os.write(getContent(request));
        serverSocket.close();
    }

    public void startApp() throws InterruptedException, ExecutionException {
        ExecutorService pool = Executors.newFixedThreadPool(2);
        Queue<Future<Void>> resultList = new LinkedBlockingQueue<>();
        for (int id = 0; id < 2; id++){
            log("Empile le thread " + id);
            Future<Void> result = pool.submit(new Worker((id == 0) ? 2134 : 2135, id));
            resultList.add(result);
        }

        log("Les travaux sont dans le pool, on récupère les résultats");
        Future<Void> result;
        while ((result = resultList.poll()) != null) {
            log("J'ai un résultat: " + result.get());
        }

        log("Fermeture du pool");
        pool.shutdown();

        log("Fin de la démo");
    }

    private byte[] getContent(String request){
        String query = "HTTP/1.1 200 OK\n" +
                "Content-Length: 38\n" +
                "Content-Type: text/html\n\n" +
                "<h1>Je suis un génie (logiciel)</h1>\n";
        return query.getBytes(StandardCharsets.ISO_8859_1);
    }

    private void log(String msg){
        DateFormat format = new SimpleDateFormat("hh:mm:ss.zzz");
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();

        System.out.printf("%s [main]: %s\n", format.format(calendar.getTime()), msg);
    }

    public static void main(String[] args) throws IOException {
        Server server = new Server();
        try {
            server.startApp();
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
