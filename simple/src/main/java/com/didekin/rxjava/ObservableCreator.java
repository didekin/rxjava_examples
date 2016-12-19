package com.didekin.rxjava;

import java.math.BigInteger;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscription;
import rx.subscriptions.Subscriptions;

import static com.didekin.Utils.log;
import static java.math.BigInteger.ONE;
import static java.math.BigInteger.ZERO;


/**
 * User: pedro@didekin
 * Date: 02/12/16
 * Time: 09:57
 */
@SuppressWarnings("SameParameterValue")
public class ObservableCreator {

    private static void createWithJust(String s)
    {
        log("============== Inside createWithJust() ===============");

        Observable.create(subscriber -> {
            subscriber.onNext(s);
            subscriber.onCompleted();
        }).subscribe(str -> System.out.printf("onNExt: %s%n", str),
                str -> System.out.printf("ON ERROR %n"),
                () -> System.out.printf("Completed%n")
        );
    }

    private static Observable<Integer> range(int begin, int numbers)
    {
        log("============== Inside range() ============== ");

        return Observable.create(subscriber -> {
                    for (int i = 0; i < numbers; ++i) {
                        subscriber.onNext(begin + i);
                    }
                    subscriber.onCompleted();
                }
        );
    }

    private static void createSequence_A()
    {
        log("============== Inside createSequence_AAA ============== ");

        Observable<BigInteger> naturalNumbers = Observable.create(
                subscriber -> {
                    Runnable r = () -> {
                        BigInteger i = ZERO;
                        while (!subscriber.isUnsubscribed() && i.compareTo(BigInteger.valueOf(10)) < 1) {
                            subscriber.onNext(i);
                            i = i.add(ONE);
                        }
                    };
                    Thread threadB = new Thread(r);
                    threadB.start();
                    log(threadB.getName() + " Inside lambda AA subscriber");
                }
        );
        naturalNumbers.subscribe(str -> log(String.valueOf(str)));
    }

    private static void createSequence_B() throws InterruptedException
    {
        log("============== Inside createSequence_BBB ============== ");

        Observable<BigInteger> naturalNumbers = Observable.create(
                subscriber -> {
                    Runnable r = () -> {
                        BigInteger i = ZERO;
                        while (!subscriber.isUnsubscribed()) {
                            subscriber.onNext(i);
                            i = i.add(ONE);
                        }
                        subscriber.onCompleted();
                    };
                    Thread threadB = new Thread(r);
                    threadB.start();
                    log(threadB.getName() + " Inside lambda BB subscriber");
                });
        Subscription subscription = naturalNumbers.subscribe(str -> log(String.valueOf(str)));
        Thread.sleep(1);
        subscription.unsubscribe();
    }

    // To remark: the way the source thread is interrupted with subscriber.add(..).
    private static <T> void createSequence_C(T i) throws InterruptedException
    {
        log("============== Inside createSequence_CCC ============== ");

        Observable<T> naturalN = Observable.create(subscriber -> {
            Runnable r = () -> {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    log(e.getMessage());
                    log("Unsubscribed: " + String.valueOf(subscriber.isUnsubscribed()));
                }
                if (!subscriber.isUnsubscribed()) {
                    subscriber.onNext(i);
                    subscriber.onCompleted();
                }
            };
            Thread threadB = new Thread(r);
            threadB.start();
            log(threadB.getName() + " Inside lambda CCC subscriber");
            // 'Create method in Subscriptions' creates and returns a Subscription that invokes the given Action when unsubscribed.
            subscriber.add(Subscriptions.create(threadB::interrupt));
        });

        Subscription subscription = naturalN.subscribe(str -> log(String.valueOf(str)));
        Thread.sleep(900);
        subscription.unsubscribe();
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException
    {
        createWithJust("Just Hello");
        ObservableCreator.range(1, 4).subscribe(
                str -> System.out.printf("onNExt: %d%n", str),
                str -> System.out.printf("ON ERROR %n"),
                () -> System.out.printf("Completed%n")
        );
        createSequence_A();
        createSequence_B();
        createSequence_C(111);
    }
}
