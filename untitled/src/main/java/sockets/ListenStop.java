package sockets;

import java.net.ServerSocket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.Callable;
import java.net.Socket;

public class ListenStop implements Callable<Void> {

    private final Thread threadMain;

    public ListenStop(Thread threadMain){
        this.threadMain = threadMain;
    }
    private void log(String msg) {
        DateFormat format = new SimpleDateFormat("hh:mm:ss.zzz");
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();

        System.out.printf("%s [Thread ListenStop]: %s \n", format.format(calendar.getTime()), msg);
    }

    @Override
    public Void call() throws Exception {

        ServerSocket serverSocket = new ServerSocket(2135);
        Socket socket = serverSocket.accept();
        threadMain.interrupt();







        return null;
    }


}
