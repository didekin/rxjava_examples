package com.didekin.rxjava;

import rx.Observable;

import static com.didekin.Utils.log;

/**
 * User: pedro@didekin
 * Date: 03/12/16
 * Time: 12:58
 */
public class ObservableCache {

    private final Observable<Integer> ints = Observable.create(
            subscriber -> {
                log("Inside Create NO cache");
                subscriber.onNext(42);
                subscriber.onCompleted();
            }
    );

    private void checkNoCache()
    {

        log("============== Inside checkNoCache() ============== ");

        log("After defining Observable.create()");
        ints.subscribe(i -> log("Subscription A: " + i));
        ints.subscribe(i -> log("Subscription B: " + i));
    }

    private void checkCache()
    {

        log("============== Inside checkCache() ============== ");

        Observable<Integer> newInts =  ints.cache();

        log("After defining Observable.create()");
        newInts.subscribe(i -> log("Subscription A: " + i));
        newInts.subscribe(i -> log("Subscription B: " + i));
    }

    public static void main(String[] args)
    {
        ObservableCache observableCache = new ObservableCache();
        observableCache.checkNoCache();
        observableCache.checkCache();
    }

}
