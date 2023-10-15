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

    private Thread threadMain;
    private ServerSocket serverSocketMain;

    public ListenStop(Thread threadMain, ServerSocket serverSocketMain){
        this.threadMain = threadMain;
        this.serverSocketMain = serverSocketMain;
    }
    private void log(String msg) {
        DateFormat format = new SimpleDateFormat("hh:mm:ss.zzz");
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();

        System.out.printf("%s [Thread ListenStop]: %s \n", format.format(calendar.getTime()), msg);
    }

    @Override
    public Void call() throws Exception {
        log("Start to listen the stop port");
        ServerSocket serverSocket = new ServerSocket(2135);
        Socket socket = serverSocket.accept();
        log("Interrupt the main thread");
        threadMain.interrupt();
        serverSocketMain.close();
        return null;
    }


}
