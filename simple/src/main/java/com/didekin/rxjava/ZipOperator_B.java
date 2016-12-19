package com.didekin.rxjava;

import com.didekin.Utils;

import rx.observables.ConnectableObservable;

import static com.didekin.Utils.log;

/**
 * User: pedro@didekin
 * Date: 17/12/16
 * Time: 12:38
 * <p>
 * Zip operator requires an implicit subscription to the observable. That is why we use publish().
 */
public class ZipOperator_B {

    private static void checkObservable_1()
    {
        log("============== Inside checkObservable_1() ============== ");

        ConnectableObservable<Integer> observableInt = ConnectableObservable
                .from(new Integer[]{1, 2, 3, 4, 5})
                .doOnCompleted(() -> log("Completing"))
                .doOnUnsubscribe(() -> log("Unsubscribing"))
                .doOnSubscribe(() -> log("Subscribing"))
                .publish();

        observableInt.zipWith(observableInt, (i, j) -> i + j)
                .map(String::valueOf)
                .subscribe(Utils::log);

        observableInt.connect();
        // No need for explicitly unsubscribing.

         /*
            main: Subscribing
            main: 2
            main: 4
            main: 6
            main: 8
            main: 10
            main: Completing
            main: Unsubscribing
         */
    }

    public static void main(String[] args) throws InterruptedException
    {
        checkObservable_1();
    }
}
