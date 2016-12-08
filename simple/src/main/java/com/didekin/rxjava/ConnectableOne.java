package com.didekin.rxjava;

import rx.Observable;
import rx.Subscription;
import rx.observables.ConnectableObservable;
import rx.subscriptions.Subscriptions;

import static com.didekin.Utils.log;

/**
 * User: pedro@didekin
 * Date: 06/12/16
 * Time: 12:50
 */
public class ConnectableOne {

    private static void connectWithoutShare() throws InterruptedException
    {
        log("============== Inside connectWithoutShare() ============== ");

        Observable<Object> myObservable = Observable.create(subscriber -> {

            log("Inside Observable.create()");

            subscriber.add(
                    Subscriptions.create(
                            () -> log("Disconnecting... ")
                    )
            );
        });

        log("-------- Before subscribers ---------");

        subsAndUnsubscribe(myObservable);
    }

    private static void connectWithShare() throws InterruptedException
    {
        log("============== Inside connectWithShare() ============== ");

        Observable<Object> myObservable = Observable.create(subscriber -> {

            log("Inside Observable.create()");

            subscriber.add(
                    Subscriptions.create(
                            () -> log("Disconnecting... ")
                    )
            );
        }).share();

        log("-------- Before subscribers ---------");

        subsAndUnsubscribe(myObservable);
    }

    private static void connectWithPublish() throws InterruptedException
    {
        log("============== Inside connectWithPublish() ============== ");

        ConnectableObservable<Object> myObservable = Observable.create(subscriber -> {

            log("Inside Observable.create()");

            subscriber.add(
                    Subscriptions.create(
                            () -> log("Disconnecting... ")
                    )
            );
        }).publish();

        log("-------- Before subscribers ---------");

        Subscription subscription1 = myObservable.subscribe();
        Subscription subscription2 = myObservable.subscribe();
        Subscription subscription3 = myObservable.subscribe();
        myObservable.connect();
        Thread.sleep(900);
        log("Subscriber1");
        subscription1.unsubscribe();
        log("Subscriber2");
        subscription2.unsubscribe();
        log("Subscriber3");
        subscription3.unsubscribe();
        log("--- After unsubscribe subscribers ----");

    }

    private static void subsAndUnsubscribe(Observable<Object> myObservable) throws InterruptedException
    {
        Subscription subscription1 = myObservable.subscribe();
        Subscription subscription2 = myObservable.subscribe();
        Subscription subscription3 = myObservable.subscribe();
        Thread.sleep(900);
        log("Subscriber1");
        subscription1.unsubscribe();
        log("Subscriber2");
        subscription2.unsubscribe();
        log("Subscriber3");
        subscription3.unsubscribe();
        log("--- After unsubscribe subscribers ----");
    }

    public static void main(String[] args) throws InterruptedException
    {
        connectWithoutShare();
        connectWithShare();
        connectWithPublish();
    }
}
