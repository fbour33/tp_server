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
import java.util.Calendar;
import java.util.Date;
import java.util.Queue;
import java.util.concurrent.*;

public class Server {
    public void start() throws IOException {

        ServerSocket serverSocket = new ServerSocket(2134);

        ExecutorService pool = Executors.newFixedThreadPool(2);
        Queue<Future<Void>> resultList = new LinkedBlockingQueue<>();

        for (int id = 0; id<2; id++) {
            log("Pile up the thread " + id);
            Future<Void> result = pool.submit(new Worker(serverSocket, id));
            resultList.add(result);
        }

        for (Future<Void> result : resultList) {
            try {
                result.get();
            } catch (Exception e) {
                log(e.toString());
            }
        }

        log("Workers are in the pool, we get the results");

        log("Pool shutting down");
        pool.shutdown();

        log("Closing the server socket");
        serverSocket.close();
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

    private void log(String message) {
        DateFormat format = new SimpleDateFormat("hh:mm:ss.zzz");
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();

        System.out.printf("%s [Main]: %s \n", format.format(calendar.getTime()),  message);
    }
}
