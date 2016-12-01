package com.didekin.lambdas;

/**
 * User: pedro@didekin
 * Date: 12/08/16
 * Time: 18:20
 */
public class HelloInner {

    Runnable r1 = new Runnable() {
        @Override
        public void run()
        {
            System.out.println(HelloInner.this);
        }
    };

    Runnable r2 = new Runnable() {
        @Override
        public void run()
        {
            System.out.println(HelloInner.this);
        }
    };

    public String toString()
    {
        return "Hello, world!";
    }

    public static void main(String... args)
    {
        new HelloInner().r1.run();
        new HelloInner().r2.run();
    }
}
