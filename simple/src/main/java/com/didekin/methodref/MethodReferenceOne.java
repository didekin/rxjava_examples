package com.didekin.methodref;

import java.util.Arrays;
import java.util.function.Consumer;

/**
 * User: pedro@didekin
 * Date: 06/12/16
 * Time: 15:34
 */
public class MethodReferenceOne {

    private final Consumer<String[]> b2 = Arrays::sort;  // void sort(Object[] a)

    public static void main(String[] args) {
        String[] letters = new String[]{"a","b","x","y","d","c"};
        new MethodReferenceOne().b2.accept(letters);
        System.out.println(Arrays.toString(letters));
    }
}
