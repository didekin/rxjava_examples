package com.didekin.rxjava;

import com.didekin.Utils;

import java.util.concurrent.TimeoutException;

import rx.Observable;

import static com.didekin.Utils.log;
import static java.util.concurrent.TimeUnit.MILLISECONDS;

/**
 * User: pedro@didekin
 * Date: 16/12/16
 * Time: 11:55
 */
public class Retry_A {

    private static Observable<String> risky_1()
    {
        return Observable.fromCallable(
                () -> {
                    long randomLong = (long) (Math.random() * 2000);
                    log(String.valueOf(randomLong));
                    if (randomLong < 200) {
                        Thread.sleep(randomLong);
                        return "OK";
                    } else {
                        Thread.sleep(randomLong);
                        return "Go for timeout";
                    }
                }
        );
    }

    static Observable<String> risky_2()
    {
        return Observable.fromCallable(
                () -> {
                    long randomLong = (long) (Math.random() * 2000);
                    log(String.valueOf(randomLong));
                    if (randomLong < 200) {
                        Thread.sleep(randomLong);
                        return "OK";
                    } else {
                        Thread.sleep(randomLong);
                        throw new RuntimeException("crash!");
                    }
                }
        );
    }

    private static void checkRetryOne()
    {
        log("============== checkRetryOne ============== ");

        risky_1()
                .timeout(1000, MILLISECONDS)
                .doOnError(th -> log("Will retry " + th))
                .retry()
                .subscribe(Utils::log);

        /*  POSSIBLE OUTCOME:
            main: 1326
            RxComputationScheduler-1: Will retry java.util.concurrent.TimeoutException
            main: 1147
            RxComputationScheduler-2: Will retry java.util.concurrent.TimeoutException
            main: 1342
            RxComputationScheduler-3: Will retry java.util.concurrent.TimeoutException
            main: 985
            main: Go for timeout
        */

        /* POSSIBLE OUTCOME:
           main: 408
           main: Go for timeout
        */
    }

    private static void checkRetryTwo()
    {
        log("============== checkRetryTwo ============== ");

        risky_2()
                .timeout(1000, MILLISECONDS)
                .doOnError(th -> log("Will retry " + th))
                .retry()
                .subscribe(Utils::log);

        /*
        POSSIBLE OUTCOME:
        main: 1941
        RxComputationScheduler-1: Will retry java.util.concurrent.TimeoutException
        main: 668
        main: Will retry java.lang.RuntimeException: Transient
        main: 1566
        RxComputationScheduler-3: Will retry java.util.concurrent.TimeoutException
        main: 124
        main: OK
        */
    }

    private static void checkRetryThree()
    {
        log("============== checkRetryThree ============== ");

        risky_2()
                .timeout(1000, MILLISECONDS)
                .doOnError(th -> log(th.toString()))
                .retry((attempt, error) -> error instanceof TimeoutException && attempt < 10)
                .doOnError(th -> log("After runtime exception won't retry "))
                .subscribe(Utils::log,
                        error -> Utils.log("Subscriber message: " + error.getMessage()));

        /*
        POSSIBLE OUTCOME:
        main: 1905
        RxComputationScheduler-1: java.util.concurrent.TimeoutException
        main: 1025
        RxComputationScheduler-2: java.util.concurrent.TimeoutException
        main: 1963
        RxComputationScheduler-3: java.util.concurrent.TimeoutException
        main: 683
        main: java.lang.RuntimeException: crash!
        main: After runtime exception won't retry
        main: Subscriber message: crash!
        */
    }

    public static void main(String[] args)
    {
        checkRetryOne();
        checkRetryTwo();
        checkRetryThree();
    }
}
