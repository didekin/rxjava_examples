package com.didekin.rxjava;

import rx.Observable;

import static com.didekin.Utils.log;

/**
 * User: pedro@didekin
 * Date: 06/12/16
 * Time: 14:53
 */
public class MapFlatMap_A {

    private static void checkFilter_1(String... strings)
    {

        log("============== Inside checkFilter_1() ============== ");

        Observable<String> observable1 = Observable.from(strings)
                .filter(s -> s.startsWith("#"));
        Observable<String> observable2 = Observable.from(strings)
                .filter(s -> s.startsWith(">"));
        Observable<String> observable3 = Observable.from(strings)
                .filter(s -> s.startsWith("&"));

        log("Subscription1");
        observable1.subscribe(System.out::println);
        log("Subscription2");
        observable2.subscribe(System.out::println);
        log("Subscription3");
        observable3.subscribe(System.out::println);
    }

    private static void checkFilterMap()
    {

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

    private static void checkFlatMap()
    {

        log("============== Inside checkFlatMap() ============== ");

        log("First observable");

        Observable.just(1, 2, 3, 4)
                .flatMap(number -> {
                    System.out.printf("(%d,%d)%n", number, ++number);
                    return Observable.empty();
                })
                .subscribe();

        log("Second observable");

        Observable.just(1, 2, 3, 4)
                .flatMap(number -> Observable.just(number, ++number))
                .map(number -> {
                    System.out.printf("(%d,%d)%n", number, ++number);
                    return Observable.empty();
                })
                .subscribe();

    }

    private static void checkFlatMap_2(Integer[] numbers)
    {
        new MapFlatMap_A().calculateOne(numbers);
        new MapFlatMap_A().calculateTwo(numbers);
    }

    public static void main(String[] args)
    {
        checkFilter_1("?A", ">B", "&C", "%D", "#E");
        checkFilterMap();
        checkFlatMap();
        checkFlatMap_2(new Integer[]{2, 11, 1, 3, 5, 2});
    }

//    ============================== HELPER METHODS AND CLASSES ==========================

    private void calculateOne(Integer[] numbers)
    {
        log("============== Inside calculateOne() ============== ");

        getNumber(numbers)
                .subscribe(
                        number -> {
                        },
                        error -> log(error.getMessage()),
                        () -> getSuma(numbers)
                );
    }

    private void calculateTwo(Integer[] numbers)
    {
        log("============== Inside calculateTwo() ============== ");

        getNumber(numbers)
                .flatMap(
                        number -> Observable.empty(),
                        Observable::error,
                        () -> getSuma(numbers)
                ).subscribe(suma -> {
            log("Nº sumandos = " + suma.numSumandos);
            log("Suma = " + suma.result);
        });
    }

    private Observable<Integer> getNumber(Integer[] numbers)
    {
        return Observable.from(numbers);
    }

    private Observable<Suma> getSuma(Integer[] numbers)
    {
        int sum = 0;
        int sumandos = 0;
        for (int number : numbers) {
            sum += number;
            ++sumandos;
        }
        return Observable.just(new Suma(sumandos, sum));
    }


    static class Suma {

        final int numSumandos;
        final int result;

        Suma(int numSumandos, int result)
        {
            this.numSumandos = numSumandos;
            this.result = result;
        }
    }

}
