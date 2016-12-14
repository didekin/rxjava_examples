package com.didekin.lambdas;

import static com.didekin.Utils.log;

/**
 * User: pedro@didekin
 * Date: 12/08/16
 * Time: 18:20
 */
public class HelloSingleThreadLambda {

    private Runnable r1 = () -> log(this.toString());

    private Runnable r2 = () -> log(this.toString());

    public String toString()
    {
        return "Hello, world!";
    }

    public static void main(String... args)
    {
        new HelloSingleThreadLambda().r1.run();
        new HelloSingleThreadLambda().r2.run();
    }
}
