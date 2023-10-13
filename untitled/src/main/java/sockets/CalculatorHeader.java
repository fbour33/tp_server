package sockets;

import java.sql.PreparedStatement;
import java.util.concurrent.atomic.AtomicInteger;

public class CalculatorHeader {

    private static final AtomicInteger counter = new AtomicInteger(0);


    private static int lineCounterHeader(String request){
        return request.split("\n").length;
    }
    public static void incrementCounter(String request){
        counter.getAndAdd(lineCounterHeader(request));
        System.out.println("Nb Header : " + CalculatorHeader.getValue());
    }

    public static int getValue(){
        return counter.get();
    }


}
