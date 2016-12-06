package com.didekin.rxjava;

import java.util.concurrent.TimeUnit;

import rx.Observable;

import static com.didekin.Utils.log;
import static java.util.concurrent.TimeUnit.MICROSECONDS;

/**
 * User: pedro@didekin
 * Date: 03/12/16
 * Time: 12:15
 */
public class ObservableTimer {

    private static void checkTimer() throws InterruptedException
    {
        log("============== Inside checkTimer() ============== ");

        Observable
                .timer(100, TimeUnit.MILLISECONDS)
                .subscribe(aLong -> log("hello " + String.valueOf(aLong)));
        Thread.sleep(1000);
    }

    private static void checkInterval() throws InterruptedException
    {
        log("============== Inside checkInterval() ============== ");

        Observable
                .interval(1_000_000 / 60, MICROSECONDS)
                .subscribe((Long i) -> log(String.valueOf(i)));

        Thread.sleep(100);
    }

    public static void main(String[] args) throws InterruptedException
    {
        checkTimer();
        checkInterval();
    }
}
