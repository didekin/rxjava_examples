package com.didekin.rxjava;

import com.didekin.Utils;

import rx.Observable;

import static com.didekin.Utils.log;
import static com.didekin.rxjava.Retry_A.risky_2;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * User: pedro@didekin
 * Date: 16/12/16
 * Time: 13:36
 */
public class Retry_B {

    private static final int ATTEMPTS = 3;

    private static final Observable<String> risky1 = Observable.create(
            subscriber -> {
                subscriber.onNext("Hello in subscriber.onNext()");
                subscriber.onError(new RuntimeException("subscriber.onError"));
                subscriber.onCompleted();
            });


    private static void checkRetryWhen_1() throws InterruptedException
    {
        log("============== checkRetryWhen_1 ============== ");

        risky1
                .retryWhen(
                        attempts -> attempts
                                .zipWith(
                                        Observable.range(1, 3),
                                        (failures, i) -> i
                                )
                                .flatMap(
                                        i -> {
                                            log("delay retry by " + i + " second(s)");
                                            return Observable.timer(i, SECONDS);
                                        }
                                )
                )
                .toBlocking()
                .forEach(message -> log("for each: " + message));

        /*
        main: Hello in subscriber.onNext()
        main: delay retry by 1 second(s)
        RxComputationScheduler-1: Hello in subscriber.onNext()
        RxComputationScheduler-1: delay retry by 2 second(s)
        RxComputationScheduler-2: Hello in subscriber.onNext()
        RxComputationScheduler-2: delay retry by 3 second(s)
        RxComputationScheduler-3: Hello in subscriber.onNext()
        */
    }

    private static void checkRetryWhen_2() throws InterruptedException
    {
        log("============== checkRetryWhen_2 ============== ");

        risky_2()
                .timeout(1000, MILLISECONDS)
                .doOnError(th -> log(th.toString()))
                .retryWhen(
                        failures -> failures
                                .zipWith(
                                        Observable.range(1, ATTEMPTS),
                                        (err, attempt) -> attempt < ATTEMPTS ? Observable.timer(1, SECONDS) : Observable.error(err)
                                )
                                .flatMap(x -> x)
                )
                .doOnError(th -> log("DO ON ERROR"))
                .forEach(randomLong -> log("message = " + randomLong),
                        error -> log("ONERROR: " + error.getMessage()),
                        () -> log("ONCOMPLETE"));

        Thread.sleep(6000);

        /*
        main: 1329
        RxComputationScheduler-4: java.util.concurrent.TimeoutException
        RxComputationScheduler-1: 974
        RxComputationScheduler-1: java.lang.RuntimeException: crash!
        RxComputationScheduler-3: 631
        RxComputationScheduler-3: java.lang.RuntimeException: crash!
        RxComputationScheduler-3: ONERROR: crash!
        */

    }

    private static void checkRetryWhen_3() throws InterruptedException
    {
        log("============== checkRetryWhen_3 ============== ");

        risky_2()
                .timeout(1000, MILLISECONDS)
                .doOnError(th -> log(th.toString()))
                .retryWhen(failures -> failures
                        .zipWith(
                                Observable.range(1, ATTEMPTS),
                                (error, attempt) -> {
                                    switch (attempt) {
                                        case 1:
                                            return Observable.just(42L);
                                        case ATTEMPTS:
                                            return Observable.error(error);
                                        default:
                                            long expDelay = (long) Math.pow(2, attempt - 2);
                                            return Observable.timer(expDelay, SECONDS);
                                    }
                                }
                        )
                        .flatMap(x -> x))
                .doOnError(th -> log("DO ON ERROR"))
                .subscribe(Utils::log, error -> log("Subscriber error message: " + error.getMessage()));

        Thread.sleep(6000);
    }

    public static void main(String[] args) throws InterruptedException
    {
        checkRetryWhen_1();
        checkRetryWhen_2();
        checkRetryWhen_3();
    }
}