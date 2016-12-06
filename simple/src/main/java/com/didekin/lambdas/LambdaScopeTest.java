package com.didekin.lambdas;

import java.util.function.Consumer;

/**
 * User: pedro
 * Date: 25/07/15
 * Time: 16:29
 */
public class LambdaScopeTest {

    public int x = 0;

    class FirstLevel {

        public final int x = 1;

        void methodInFirstLevel(int x)
        {
            Consumer<Integer> myConsumer = (y) ->
            {
                System.out.println("x = " + x); // Statement A
                System.out.println("y = " + y);
                System.out.println("this.x = " + this.x);
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

