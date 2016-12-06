package com.didekin.lambdas;

/**
 * User: pedro@didekin
 * Date: 12/08/16
 * Time: 18:20
 */
public class HelloLambda {

    private Runnable r1 = () -> {
        System.out.println(this);
    };

    private Runnable r2 = () -> {
        System.out.println(toString());
    };

    public String toString()
    {
        return "Hello, world!";
    }

    public static void main(String... args)
    {
        new HelloLambda().r1.run();
        new HelloLambda().r2.run();
    }
}
