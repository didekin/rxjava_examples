package com.didekin.rxjava;

import org.apache.commons.lang3.tuple.Pair;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.schedulers.Schedulers;

import static com.didekin.Utils.log;

/**
 * User: pedro@didekin
 * Date: 14/12/16
 * Time: 14:18
 */
public class ObserveOn_A {
    private static final Observable<String> letters = Observable.just("A", "B", "B", "C", "A", "D", "A", "E", "C", "F");

    private static void checkExecutionOrder_1() throws InterruptedException
    {
        log("============== Printing pairs thread io first ============== ");
        letters.groupBy(letter -> letter)
                .flatMap(group -> group
                        .count()
                        .map(quantity -> {
                                    String productName = group.getKey();
                                    return Pair.of(productName, quantity);
                                }
                        )
                )
                .subscribeOn(Schedulers.io())
                .subscribe(pair -> log(pair.toString()));

        TimeUnit.MILLISECONDS.sleep(100);
    }

    private static void checkExecutionOrder_2() throws InterruptedException
    {
        log("============== Printing pairs in io second ============== ");
        letters.groupBy(letter -> letter)
                .flatMap(group -> group
                        .count()
                        .map(quantity -> {
                                    String productName = group.getKey();
                                    Pair<String, Integer> pair = Pair.of(productName, quantity);
                                    try {
                                        TimeUnit.MILLISECONDS.sleep(100);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                    log(pair.toString());
                                    return pair;
                                }
                        ).subscribeOn(Schedulers.newThread())  // NO SIRVE PARA NADA.
                )
                .flatMap(pair -> Observable.just(pair.getValue()))
                .subscribeOn(Schedulers.computation())
                .reduce(0, (base, number) -> (base + number))
                .single()
                .subscribe(number -> log("number"));   // IN COMPUTATION THREAD.

        TimeUnit.MILLISECONDS.sleep(900);
    }

    private static void checkExecutionOrder_3() throws InterruptedException
    {
        log("============== Printing pairs observedOn different threads ============== ");
        letters.groupBy(letter -> letter)
                .observeOn(Schedulers.computation()) // SÍ FUNCIONA MULTITHREAD FLATMAP.
                .flatMap(group -> group
                        .count()
                        .map(quantity -> {
                                    String productName = group.getKey();
                                    Pair<String, Integer> pair = Pair.of(productName, quantity);
                                    log(pair.toString());
                                    return pair;
                                }
                        ).subscribeOn(Schedulers.io())  // MULTITHREAD FLATMAP.
                )
                .subscribe(pair -> log("pair"));

        TimeUnit.MILLISECONDS.sleep(50);
    }

    private static void checkExecutionOrder_4() throws InterruptedException
    {
        log("============== Printing pairs observedOn different threads ============== ");
        letters.groupBy(letter -> letter)
                .subscribeOn(Schedulers.io())
                .flatMap(group -> group
                        .count()
                        .map(quantity -> {
                                    String productName = group.getKey();
                                    Pair<String, Integer> pair = Pair.of(productName, quantity);
                                    log(pair.toString());
                                    return pair;
                                }
                        ).subscribeOn(Schedulers.computation())
                )
                .observeOn(Schedulers.computation())
                .subscribe(pair -> log("pair"));

        TimeUnit.MILLISECONDS.sleep(50);
    }

    public static void main(String[] args) throws InterruptedException
    {
        checkExecutionOrder_1();
        checkExecutionOrder_2();
        checkExecutionOrder_3();
        checkExecutionOrder_4();
    }
}
