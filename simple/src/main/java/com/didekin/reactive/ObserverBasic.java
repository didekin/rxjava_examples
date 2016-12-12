package com.didekin.reactive;

import java.util.Observable;
import java.util.Scanner;

import static com.didekin.Utils.log;

/**
 * User: pedro@didekin
 * Date: 12/08/16
 * Time: 16:25
 */
public class ObserverBasic {

    public static void main(String[] args)
    {
        System.out.println("Enter Text: ");
        EventSource eventSource = new EventSource();

        eventSource.addObserver((obj, arg) -> log("Received response: " + arg));

        new Thread(eventSource).start();
    }

    @SuppressWarnings("InfiniteLoopStatement")
    static class EventSource extends Observable implements Runnable {

        public void run()
        {
            while (true) {
                String response = new Scanner(System.in).next();
                setChanged();
                notifyObservers(response);
            }
        }
    }
}
