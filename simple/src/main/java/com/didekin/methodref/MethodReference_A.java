package com.didekin.methodref;

import java.util.Arrays;
import java.util.function.Consumer;

import static com.didekin.Utils.log;

/**
 * User: pedro@didekin
 * Date: 06/12/16
 * Time: 15:34
 */
public class MethodReference_A {

    private final Consumer<String[]> b2 = Arrays::sort;  // void sort(Object[] a)

    public static void main(String[] args) {
        String[] letters = new String[]{"a","b","x","y","d","c"};
        new MethodReference_A().b2.accept(letters);
        log(Arrays.toString(letters));
    }
}
