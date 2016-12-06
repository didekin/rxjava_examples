package com.didekin;

/**
 * User: pedro@didekin
 * Date: 02/12/16
 * Time: 20:21
 */
public class Utils {

    public static void log(String msg) {
        System.out.println(Thread.currentThread().getName() + ": " + msg);
    }
}
