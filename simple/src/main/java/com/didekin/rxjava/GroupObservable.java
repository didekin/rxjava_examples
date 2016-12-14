package com.didekin.rxjava;

import com.didekin.Utils;

import org.apache.commons.lang3.tuple.Pair;

import rx.Observable;

import static com.didekin.Utils.log;

/**
 * User: pedro@didekin
 * Date: 14/12/16
 * Time: 12:06
 */
public class GroupObservable {

    private static final Observable<String> letters = Observable.just("A", "B", "B", "C", "A", "D", "A", "E");

    private static void checkFlatMap()
    {
        letters.groupBy(letter -> letter)
                .flatMap(group -> group)
                .subscribe(Utils::log);
    }

    private static void checkFlatMapCount()
    {
        letters.groupBy(letter -> letter)
                .flatMap(Observable::count)
                .subscribe(counter -> log(String.valueOf(counter)));
    }

    private static void checkFlatMapCountMap()
    {
        letters.groupBy(letter -> letter)
                .flatMap(group -> group
                        .count()
                        .map(quantity -> {
                                    String productName = group.getKey();
                                    return Pair.of(productName, quantity);
                                }
                        )
                )
                .subscribe(counter -> log(String.valueOf(counter)));
    }

    private static void checkFlatMapCountMapReduce()
    {
        letters.groupBy(letter -> letter)
                .flatMap(group -> group
                        .count()
                        .map(quantity -> {
                                    String productName = group.getKey();
                                    return Pair.of(productName, quantity);
                                }
                        )
                )
                .flatMap(pair -> Observable.just(pair.getValue()))
                .reduce(0, (base, number) -> (base + number))
                .subscribe(counter -> log(String.valueOf(counter)));
    }

    public static void main(String[] args)
    {
        log("checkFlatMap");
        checkFlatMap();
        log("checkFlatMapCount");
        checkFlatMapCount();
        log("checkFlatMapCountMap");
        checkFlatMapCountMap();
        log("checkFlatMapCountMapReduce");
        checkFlatMapCountMapReduce();
    }
}
