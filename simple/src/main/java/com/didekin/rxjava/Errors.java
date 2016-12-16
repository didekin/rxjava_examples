package com.didekin.rxjava;

import rx.Observable;

import static com.didekin.Utils.log;
import static rx.Observable.just;

/**
 * User: pedro@didekin
 * Date: 15/12/16
 * Time: 15:05
 */
public class Errors {

    private static void checkDoOnError_A()
    {
        log("============== checkDoOnError_A first trial ============== ");

        just(1, 0)
                .map(x -> 10 / x)
                .doOnError(error -> log("doOnError message = " + error.getMessage()))
                .subscribe(number -> log("OK, division = " + String.valueOf(number)),
                        error -> log("ERROR"),
                        () -> log("COMPLETED"));

        log("============== checkDoOnError_A second trial ============== ");

        // La colocación afecta: no hay 'doOnError message' porque va delante del error.
        just(1, 0)
                .doOnError(error -> log("doOnError message = " + error.getMessage()))
                .map(x -> 10 / x)
                .subscribe(number -> log("OK, division = " + String.valueOf(number)),
                        error -> log("ERROR"),
                        () -> log("COMPLETED"));
    }

    private static void checkOnErrorReturn()
    {

        log("============== checkOnErrorReturn first trial ============== ");

        just(1, 0)
                .onErrorReturn(error -> 99999)
                .map(x -> 10 / x)
                .subscribe(number -> log("OK, division = " + String.valueOf(number)),
                        error -> log("ERROR"),
                        () -> log("COMPLETED"));

        log("============== checkOnErrorReturn second trial ============== ");

        // Complete without error.
        just(1, 0)
                .map(x -> 10 / x)
                .onErrorReturn(error -> 99999)
                .subscribe(number -> log("OK, division = " + String.valueOf(number)),
                        error -> log("ERROR"),
                        () -> log("COMPLETED"));
    }

    private static void checkOnErrorResume()
    {
        log("============== checkOnErrorResume first trial ============== ");
        just(1, 0)
                .map(x -> 10 / x)
                .onErrorResumeNext(error -> {
                    if (error instanceof ArithmeticException) {
                        return just(99999);
                    } else {
                        return Observable.error(error);
                    }
                })
                .subscribe(number -> log("OK, division = " + String.valueOf(number)),
                        error -> log("ERROR"),
                        () -> log("COMPLETED"));

        log("============== checkOnErrorResume second trial ============== ");
        just(1, 0)
                .map(x -> 10 / x)
                .onErrorResumeNext(error -> {
                    if (error instanceof NullPointerException) {
                        return just(99999);
                    } else {
                        return Observable.error(error);
                    }
                })
                .subscribe(number -> log("OK, division = " + String.valueOf(number)),
                        error -> log("ERROR"),
                        () -> log("COMPLETED"));

    }

    public static void main(String[] args)
    {
        checkDoOnError_A();
        checkOnErrorReturn();
        checkOnErrorResume();
    }
}
