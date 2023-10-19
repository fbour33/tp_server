package sockets;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;


public class UrlReader {

    //Return the request parsed into lines
    private static String[] getLinesRequest(String request) {
        return request.split("\r\n");
    }

    //Return one line of the request parsed
    private static String getOneLineRequest(String request, int index){
        String[] linesRequest = getLinesRequest(request); //Line not parsed
        return linesRequest[index];
    }

    //Return an array of the path and the querys
    public static String[] getPathQuery(String request){
        String getLine = getOneLineRequest(request, 0);
        String[] pathQuery = getLine.split(" ");
        if(pathQuery.length == 3 && pathQuery[0].equals("GET"))
            return pathQuery[1].split("\\?");
        return null;
    }

    //Return the path of the html file
    public static String getFile(String request){
        String[] pathQuery = getPathQuery(request);
        if(pathQuery != null && pathQuery[0].startsWith("/") && !pathQuery[0].equals("/"))
            return pathQuery[0];
        return null;
    }


    //Return an array of the name and the value of the variable in the URL
    // For instance, ["name1", "Florian", "name2", "Thomas", "name3", "Victor"]
    public static String[] getVariables(String request){
        String[] pathQuery = getPathQuery(request);
        if(pathQuery != null && pathQuery.length > 1) {
            return pathQuery[1].split("[&=]");
        }
        return null;
    }

    //Read the file and return the content
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
