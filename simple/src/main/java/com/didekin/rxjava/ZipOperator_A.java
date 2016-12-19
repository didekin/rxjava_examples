package com.didekin.rxjava;

import rx.Observable;
import rx.Observable.Transformer;

import static com.didekin.Utils.log;
import static rx.Observable.empty;
import static rx.Observable.just;

/**
 * User: pedro@didekin
 * Date: 10/12/16
 * Time: 15:20
 */
public class ZipOperator_A {

    private static final Observable<Character> letters = Observable
            .range(0, 'Z' - 'A' + 1)
            .map(c -> (char) ('A' + c));

    private static final Observable<Boolean> trueFalse = Observable.just(true, false).repeat();

    @SuppressWarnings("unchecked")
    private <T> Observable<T> getCharTrue(Observable<T> upstream)
    {
        return (Observable<T>) upstream
                .zipWith(trueFalse, (t, bool) -> bool ? just(t) : empty())
                .flatMap(obs -> obs);
    }

    @SuppressWarnings("unchecked")
    private <T> Transformer<T,T> getCharAllTrans()
    {
        Observable<Boolean> trueFalse = Observable.just(true, false).repeat();
        return upstream -> upstream
                .zipWith(trueFalse, (t, bool) -> bool ? just(t) : empty()).flatMap(obs -> (Observable<T>) obs)
                .filter(letter -> letter.equals('A') || letter.equals('C'));
    }

    public static void main(String[] args)
    {
        log("LETTERS");
        letters.subscribe(System.out::println);
        log("TRUE-FALSE");
        trueFalse.take(10).subscribe(System.out::println);
        log("CHARS_BOOLEAN");
        ZipOperator_A thisOperator = new ZipOperator_A();
        thisOperator.getCharTrue(letters).subscribe(System.out::println);
        log("CHARS_BOOLEAN_FILTERED 1");
        thisOperator.getCharTrue(letters).filter(letter -> letter.equals('A') || letter.equals('C')).subscribe(System.out::println);
        log("CHARS_BOOLEAN_FILTERED 2");
        letters.compose(thisOperator.getCharAllTrans()).subscribe(System.out::println);
    }
}
