package com.didekin.lambdas;

import java.util.function.UnaryOperator;

/**
 * User: pedro@didekin
 * Date: 13/08/16
 * Time: 18:28
 */
public class LambdaInstanceRecursive {

    private UnaryOperator<Integer> factorial;

    private LambdaInstanceRecursive()
    {
        factorial = i -> (i == 0 ? 1 : i * factorial.apply( i - 1 ));
    }

    public static void main(String[] args)
    {
        int number = 6;
        System.out.printf("Factorial %d = %d", number, new LambdaInstanceRecursive().factorial.apply(number));
    }
}
