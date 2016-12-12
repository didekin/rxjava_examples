package com.didekin.rxjava;

import com.didekin.Utils;

import java.util.concurrent.TimeUnit;

import rx.Observable;

import static com.didekin.Utils.log;
import static java.util.concurrent.TimeUnit.SECONDS;
import static rx.Observable.timer;

/**
 * User: pedro@didekin
 * Date: 09/12/16
 * Time: 10:24
 */
public class DelayOperator {


    private static void checkDelayOne() throws InterruptedException
    {
        log("============== Inside checkDelayOne ============== ");

        log("============== With delay ============== ");

        Observable
                .just("Lorem", "ipsum", "dolor", "sit", "amet",
                        "consectetur", "adipiscing", "elit")
                .delay(word -> timer(word.length() / 5, SECONDS))
                .subscribe(Utils::log);

        SECONDS.sleep(5);

        log("============== With timer and flatMap ============== ");

        Observable
                .timer(1, TimeUnit.SECONDS)
                .flatMap(i -> Observable.just("Lorem", "ipsum", "dolor", "sit", "amet",
                        "consectetur", "adipiscing", "elit"))
                .subscribe(Utils::log);

        SECONDS.sleep(2);

        log("============== With flatMap, timer and map ============== ");

        // Equivalent to delay.
        Observable
                .just("Lorem", "ipsum", "dolor", "sit", "amet",
                        "consectetur", "adipiscing", "elit")
                .flatMap(
                        word -> timer(word.length()/5, SECONDS).map(x -> word)
                )
                .subscribe(Utils::log);

        SECONDS.sleep(5);
    }

    public static void main(String[] args) throws InterruptedException
    {
        checkDelayOne();
    }
}
