package com.didekin.rxjava;

import rx.Observable;

import static com.didekin.Utils.log;
import static java.util.concurrent.TimeUnit.MICROSECONDS;

/**
 * User: pedro@didekin
 * Date: 06/12/16
 * Time: 12:50
 */
public class ConnectableOne {

    private static void checkInterval() throws InterruptedException
    {
        log("============== Inside checkInterval() ============== ");

        Observable
                .interval(1_000_000 / 60, MICROSECONDS)
                .subscribe((Long i) -> log(String.valueOf(i)));

        Thread.sleep(100);
    }
}
