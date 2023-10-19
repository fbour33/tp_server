package sockets;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.atomic.*;



public class UrlReader {

    /*public static String getFileName(String request) {
        String[] requestLines = request.split("\r\n");
        String[] hostParts = requestLines[1].split(": ");
        String[] requestParts = requestLines[0].split(" ");
        if (requestParts.length == 3 && hostParts.length == 2) {
            String method = requestParts[0];
            String path = requestParts[1];
            if (method.equals("GET") && path.startsWith("/"))
                return path;
        }
        return null;
    }*/

    private static String[] getLinesRequest(String request) {
        return request.split("\r\n");
    }

    private static String[] parseLine(String line, String parser){
        return line.split(parser);
    }

    private static String getOneLineRequest(String request, int index){
        String[] linesRequest = getLinesRequest(request); //Line not parsed
        return linesRequest[index];
    }

    private static String[] getPathQuery(String request){
        String getLine = getOneLineRequest(request, 0);
        String[] pathQuery = parseLine(getLine, " ");
        if(pathQuery.length == 3 && pathQuery[0].equals("GET"))
            return parseLine(pathQuery[1], "?");
        return null;
    }

    public static String getFile(String request){
        String[] pathQuery = getPathQuery(request);
        if(pathQuery != null && pathQuery[0].startsWith("/") && !pathQuery[0].equals("/"))
            return pathQuery[0];
        return null;
    }

    public static String getVariable(String request, String variable){
        String[] pathQuery = getPathQuery(request);
        if(pathQuery != null) {
            String[] parsedQuery = parseLine(pathQuery[1], "=");
            if (parsedQuery[0].equals(variable)) {
                return parsedQuery[1];
            }
        }
        return null;
    }

    public static StringBuilder readFile(URL url) {
        if(url != null){
            try (InputStream inputStream = url.openStream()){
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
                StringBuilder content = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    content.append(line).append("\n");
                }
                return content;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
