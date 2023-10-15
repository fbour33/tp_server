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

    //private static boolean runServer = true;

    private int nbThread = 0;

    public void start() throws IOException {

        ServerSocket serverSocket = new ServerSocket(2134);

        ExecutorService pool = Executors.newFixedThreadPool(3);
        Queue<Future<Void>> resultList = new LinkedBlockingQueue<>();

        /*for (int id = 0; id<2; id++) {
            log("Pile up the thread " + id);
            Future<Void> result = pool.submit(new Worker(serverSocket, id));
            resultList.add(result);
        }*/

        pool.submit(new ListenStop(Thread.currentThread(), serverSocket));

        log("Waiting for a new connection...");
        while(!Thread.interrupted()){
            log("Thread.interrupted : " + Thread.interrupted());

            try {
                Socket socket = serverSocket.accept();
                log("Pile up the thread " + nbThread);
                Future<Void> result = pool.submit(new Worker(socket, nbThread));
                this.nbThread++;
                resultList.add(result);
            } catch (IOException e) {
                log("IOException break the boucle");
                break;
            }
        }
        log("I'm interrupted.");
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
            } catch (IOException ex) {
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
