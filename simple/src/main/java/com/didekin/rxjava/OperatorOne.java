package com.didekin.rxjava;

import com.didekin.Utils;

import rx.Observable;

import static com.didekin.Utils.log;

/**
 * User: pedro@didekin
 * Date: 06/12/16
 * Time: 14:53
 */
public class OperatorOne {

    private static void checkFilter_1(String... strings){

        log("============== Inside checkFilter_1() ============== ");

        Observable<String> observable1 = Observable.from(strings)
                .filter(s -> s.startsWith("#"));
        Observable<String> observable2 = Observable.from(strings)
                .filter(s -> s.startsWith(">"));
        Observable<String> observable3 = Observable.from(strings)
                .filter(s -> s.startsWith("&"));

        Utils.log("Subscription1");
        observable1.subscribe(System.out::println);
        Utils.log("Subscription2");
        observable2.subscribe(System.out::println);
        Utils.log("Subscription3");
        observable3.subscribe(System.out::println);
    }

    private static void checkFilterMap(){

        log("============== Inside checkFilterMap() ============== ");

        log("First observable");

        Observable
                .just(8, 9, 10)
                .filter(i -> i % 3 > 0)
                .map(i -> "#" + i * 10)
                .filter(s -> s.length() < 4)
                .subscribe(System.out::println);

        log("Second observable");

        Observable
                .just(8, 9, 10)
                .doOnNext(i -> System.out.println("A: " + i))
                .filter(i -> i % 3 > 0)
                .doOnNext(i -> System.out.println("B: " + i))
                .map(i -> "#" + i * 10)
                .doOnNext(s -> System.out.println("C: " + s))
                .filter(s -> s.length() < 4)
                .subscribe(s -> System.out.println("D: " + s));
    }

    private static void checkFlatMap(){

        log("============== Inside checkFlatMap() ============== ");

        log("First observable");

        Observable.just(1,2,3,4)
                .flatMap(number -> {
                    System.out.printf("(%d,%d)%n", number, ++number);
                    return Observable.empty();
                })
                .subscribe();

        log("Second observable");

        Observable.just(1,2,3,4)
                .flatMap(number -> Observable.just(number, ++number))
                .map(number -> {
                    System.out.printf("(%d,%d)%n", number, ++number);
                    return Observable.empty();
                })
                .subscribe();

    }

    public static void main(String[] args)
    {
        checkFilter_1("?A", ">B","&C","%D","#E");
        checkFilterMap();
        checkFlatMap();
    }
}