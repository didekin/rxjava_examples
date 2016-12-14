package com.didekin.lambdas;

import java.util.concurrent.Callable;
import java.util.function.Consumer;

/**
 * User: pedro@didekin
 * Date: 12/08/16
 * Time: 18:34
 */
public class HelloLambdaCallable {
    public static void main(String[] args)
    {
        Callable<Void> callable = () -> {
            System.out.println("Hello silly");
            return null;
        };

        try {
            callable.call();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Consumer<String> b3 = HelloSingleThreadLambda::main;
        b3.accept(null);
    }
}
