package sockets;

import java.io.IOException;
import java.lang.*;
import java.net.ServerSocket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Queue;
import java.util.concurrent.*;
import java.net.Socket;

public class Server {
    private int nbThread = 0;

    public void start() throws IOException {

        ServerSocket serverSocket = new ServerSocket(2134);

        ExecutorService pool = Executors.newFixedThreadPool(3);
        Queue<Future<Void>> resultList = new LinkedBlockingQueue<>();

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
                log("IOException break the loop");
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

    public static void main(String[] args) {
        Server server = new Server();
            try {
                server.start();
            } catch (IOException ex) {
                System.out.println(ex.toString());
                System.out.println("Could not connect to the server");
            }
    }

    private void log(String message) {
        DateFormat format = new SimpleDateFormat("hh:mm:ss.SSS");
        Calendar calendar = Calendar.getInstance();

        System.out.printf("%s [Main]: %s \n", format.format(calendar.getTime()),  message);
    }
}
