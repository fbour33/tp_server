package sockets;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.Callable;
import java.net.Socket;

public class ListenStop implements Callable<Void> {

    private final ServerSocket serverSocket;

    public ListenStop(ServerSocket serverSocket){
        this.serverSocket = serverSocket;
    }
    private void log(String msg) {
        DateFormat format = new SimpleDateFormat("hh:mm:ss.zzz");
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();

        System.out.printf("%s [Thread ListenStop]: %s \n", format.format(calendar.getTime()), msg);
    }

    @Override
    public Void call() throws Exception {

        log("Waiting for a new connection...");
        Socket socket = serverSocket.accept();




        return null;
    }


}
