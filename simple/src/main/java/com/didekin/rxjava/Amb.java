package com.didekin.rxjava;

import com.didekin.Utils;

import rx.Observable;

import static com.didekin.Utils.log;
import static java.util.concurrent.TimeUnit.MILLISECONDS;

/**
 * User: pedro@didekin
 * Date: 09/12/16
 * Time: 20:02
 */
public class Amb {

    private Observable<String> stream(int initialDelay, int interval, String name)
    {
        return Observable
                .interval(initialDelay, interval, MILLISECONDS)
                .map(x -> name + x)
                .doOnSubscribe(() ->
                        log("Subscribe to " + name))
                .doOnUnsubscribe(() ->
                        log("Unsubscribe from " + name));
    }

    private void checkAmb_1() throws InterruptedException
    {
        log("============== Inside checkAmb_1() ============== ");

        Observable.amb(
                stream(10, 17, "S"),
                stream(12, 10, "F")
        ).subscribe(Utils::log);

        Thread.sleep(100);
    }

    public static void main(String[] args) throws InterruptedException
    {
        new Amb().checkAmb_1();
    }
}
