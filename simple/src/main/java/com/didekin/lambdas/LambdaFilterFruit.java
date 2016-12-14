package com.didekin.lambdas;

import java.util.ArrayList;
import java.util.List;

/**
 * User: pedro
 * Date: 25/07/15
 * Time: 10:52
 */
public class LambdaFilterFruit {

    private static <T> List<T> filterByPredicate(List<T> tAlls, Predicate<T> predicate)
    {
        List<T> filterList = new ArrayList<>();
        for (T t : tAlls) {
            if (predicate.test(t)) {
                filterList.add(t);
            }
        }
        return filterList;
    }

    public static void main(String[] args){
        Apple appleOne = new Apple("green",125);
        Apple appleTwo = new Apple("blue",175);
        List<Apple> apples = new ArrayList<>(2);
        apples.add(appleOne);
        apples.add(appleTwo);
        List<Apple> filterList = filterByPredicate(apples,apple -> "green".equals(apple.getColor()));
        System.out.printf("Filter apple: %s%n", filterList.get(0).getColor());
    }

    static class Apple {
        String color;
        int weight;

        Apple(String color, int weight)
        {
            this.color = color;
            this.weight = weight;
        }

        String getColor()
        {
            return color;
        }

    }

    interface Predicate<T> {
        boolean test(T t);
    }
}
