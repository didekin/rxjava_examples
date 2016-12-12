package com.didekin.rxjava;

import com.didekin.Utils;

import rx.Observable;

import static com.didekin.Utils.log;

/**
 * User: pedro@didekin
 * Date: 09/12/16
 * Time: 17:00
 */
public class CartersianProduct {

    private final Observable<Integer> oneToEight;
    private final Observable<String> ranks;
    private final Observable<String> files;
    private static final CartersianProduct INSTANCE = new CartersianProduct();

    private CartersianProduct()
    {
        oneToEight = Observable.range(1, 8);

        ranks = oneToEight
                .map(Object::toString);

        files = oneToEight
                .map(x -> 'A' + x - 1)
                .map(ascii -> (char) ascii.intValue())
                .map(ch -> Character.toString(ch));
    }

    private static void prepairCartesian()
    {
        log("============== Inside prepairCartesian() ============== ");

        log("....... Ranks ........ ");
        INSTANCE.ranks.subscribe(Utils::log);
        log("....... onToEight first map files ........ ");
        INSTANCE.oneToEight
                .map(x -> 'A' + x - 1)
                .subscribe(z -> Utils.log(String.valueOf(z)));
        log("....... onToEight second map files ........ ");
        INSTANCE.oneToEight
                .map(x -> 'A' + x - 1)
                .map(ascii -> (char) ascii.intValue())
                .subscribe(z -> Utils.log(String.valueOf(z)));
        log("....... files: onToEight third map ........ ");
        INSTANCE.files
                .subscribe(z -> Utils.log(String.valueOf(z)));
        log("....... Ranks map to A + rank ........ ");
        INSTANCE.ranks.map(rank -> "A" + rank)
                .subscribe(Utils::log);
    }

    private static void doCartesian_1()
    {
        log("============== Inside doCartesian_1() ============== ");

        INSTANCE.files
                .flatMap(file -> INSTANCE.ranks.map(rank -> file + rank))
                .subscribe(Utils::log);
    }

    public static void main(String[] args)
    {
        prepairCartesian();
        doCartesian_1();
    }
}
