package sockets;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.Callable;

public class Worker implements Callable<Void> {
    private final Socket socket;
    private final int id;
    public Worker(Socket socket, int id){
        this.socket = socket;
        this.id = id;
    }

    @Override
    public Void call() throws Exception {

        log(" A client is connected, we're reading the content");
        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
        StringBuilder requestBuilder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            requestBuilder.append(line).append("\r\n");
            if (line.isEmpty()) {
                break;
            }
        }
        String request = requestBuilder.toString();
        log("Client is asking : " + request);
        OutputStream os = socket.getOutputStream();
        CalculatorHeader.incrementCounter(request);
        os.write(getContent(request));
        os.flush();
        socket.close();
        Thread.sleep(10*1000);
        log("Response sent: Total headers received : " + CalculatorHeader.getValue());
        return null;
    }


    private String getFileName(String request) {
        String[] requestLines = request.split("\r\n");
        String[] hostParts = requestLines[1].split(": ");
        String[] requestParts = requestLines[0].split(" ");
        if (requestParts.length == 3 && hostParts.length == 2) {
            String method = requestParts[0];
            String path = requestParts[1];
            String host = hostParts[0];
            String baseUrl = hostParts[1];
            if (method.equals("GET") && path.startsWith("/"))
                return path.substring(path.indexOf("/") + 1); //path.substring is the fileName without the /
        }
        return null;
    }

    private StringBuilder readFile(String fileName) throws IOException {
        File file = new File(fileName);
        if(file.exists() && file.isFile()){
            BufferedReader answerReader = new BufferedReader(new FileReader(fileName));
            StringBuilder answerBuilder = new StringBuilder();
            String line;
            while ((line = answerReader.readLine()) != null) {
                answerBuilder.append(line).append("\r\n");
                if (line.isEmpty()) {
                    break;
                }
            }
            answerReader.close();
            return answerBuilder;
        }
        return null;
    }

    private byte[] getContent(String request) {
        try {
            String fileName = getFileName(request);
            StringBuilder answerBuilder = readFile(fileName);
            if(fileName != null && answerBuilder != null) {
                String answer = answerBuilder.toString();
                //String answer = "<h1>Je suis un génie (logiciel)</h1>";
                String query = "HTTP/1.1 200 OK\n" +
                        "Content-Length: " + answer.getBytes(StandardCharsets.UTF_8).length + "\n" +
                        "Content-Type: text/html\n\n" +
                        answer + "\n";
                return query.getBytes(StandardCharsets.UTF_8);
            }
        }catch(IOException e){
            e.printStackTrace();
        }

        String answer = "<h1>File not found</h1>";
        String query = "HTTP/1.1 404 Not Found\n" +
                "Content-Length: " + answer.getBytes(StandardCharsets.UTF_8).length + "\n" +
                "Content-Type: text/html\n\n" +
                answer + "\n";
        return query.getBytes(StandardCharsets.UTF_8);
    }

    private void log(String msg) {
        DateFormat format = new SimpleDateFormat("hh:mm:ss.SSS");
        Calendar calendar = Calendar.getInstance();
        System.out.printf("%s [Thread %d]: %s \n", format.format(calendar.getTime()),  this.id, msg);
    }
}
