package com.didekin.lambdas;

import java.util.function.Consumer;

/**
 * User: pedro
 * Date: 25/07/15
 * Time: 16:29
 */
public class LambdaScopeTest {

    private int x = 0;

    class FirstLevel {

        final int x = 1;

        void methodInFirstLevel(int x)
        {
            Consumer<Integer> myConsumer = y -> {
                // parameter x.
                System.out.println("x = " + x); // Statement A
                // since y is the parameter of the lambda, then y == x.
                System.out.println("y = " + y);
                // this refers to the enclosing class instance of the lambda exp.: x == 1.
                System.out.println("this.x = " + this.x);
                // x == 0.
                System.out.println("LambdaScopeTest.this.x = " +
                        LambdaScopeTest.this.x);
            };
            myConsumer.accept(x);
        }
    }

    public static void main(String... args)
    {
        LambdaScopeTest st = new LambdaScopeTest();
        LambdaScopeTest.FirstLevel fl = st.new FirstLevel();
        fl.methodInFirstLevel(23);
    }
}

