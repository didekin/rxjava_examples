package com.didekin.lambdas;

import java.util.function.UnaryOperator;

/**
 * User: pedro@didekin
 * Date: 13/08/16
 * Time: 18:28
 */
public class LambdaRecursiveTwo {

    UnaryOperator<Integer> factorial;

    public LambdaRecursiveTwo()
    {
        factorial = i -> (i == 0 ? 1 : i * factorial.apply( i - 1 ));
    }

    public static void main(String[] args)
    {
        int number = 6;
        System.out.printf("Factorial %d = %d", number, new LambdaRecursiveTwo().factorial.apply(number));
    }
}
