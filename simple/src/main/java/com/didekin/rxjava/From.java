package com.didekin.rxjava;

import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * User: pedro@didekin
 * Date: 30/11/16
 * Time: 17:37
 */
public class From {

    private static void helloWorld()
    {
        // Lambda expression for a one argument function with a parameter of type Subscriber.
        Observable.create(s -> {
            s.onNext("Hello World!");
            s.onCompleted();
        }).subscribe(System.out::println);

    }

    private static void helloNames(String... names)
    {
        Observable.from(names).subscribe(s -> System.out.println("Hello " + s + "!"));
    }

    private static void simpleExampleMap(Integer... numbers)
    {
        Observable.from(numbers).map(n -> "Number: " + n).subscribe(System.out::println);
    }

    /**
     *  It shows synchronous execution of methods doOnNext, filter, map.
    */
    private static void simmpleExampleAsync(Integer... numbers)
    {
        Observable.from(numbers)
                .observeOn(Schedulers.io())
                .doOnNext(i -> System.out.println(Thread.currentThread().getName()))
                .filter(i -> i % 2 == 0)
                .map(i -> "Value " + i + " processed on " + Thread.currentThread().getName())
                .subscribe(s -> System.out.println("SOME VALUE =>" + s));

        System.out.println("Will print BEFORE values are emitted");

        /* Result:
        RxIoScheduler-2
        Will print BEFORE values are emitted
        SOME VALUE =>Value 44 processed on RxIoScheduler-2
        RxIoScheduler-2
        RxIoScheduler-2
        RxIoScheduler-2
        SOME VALUE =>Value 66 processed on RxIoScheduler-2
        */
    }

    public static void main(String[] args)
    {
        helloWorld();
        helloNames("Pepe", "Juan", "Luis");
        simpleExampleMap(11, 22, 33);
        simmpleExampleAsync(44,33,55,66);
    }
}
