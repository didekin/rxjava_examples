package com.didekin.rxjava;

import com.didekin.Utils;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;

/**
 * User: pedro@didekin
 * Date: 18/12/16
 * Time: 10:39
 */
public class ConcatMapIterable {

    private static void checkOne(){
        Observable.just(1, 3, 5)
                .concatMapIterable(number -> {
                    List<Integer> list = new ArrayList<>(3);
                    list.add(number);
                    list.add(++number);
                    list.add(++number);
                    return list;
                })
                .subscribe(number -> Utils.log(String.valueOf(number)));
    }

    public static void main(String[] args)
    {
        checkOne();
    }
}
