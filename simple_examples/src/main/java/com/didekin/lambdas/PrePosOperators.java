package com.didekin.lambdas;

/**
 * User: pedro
 * Date: 25/07/15
 * Time: 13:58
 */
public class PrePosOperators {

    static int[] array = new int[]{1,4,6,8,11};

    public static void main(String[] args){
        int i = 0;
        System.out.printf("a[i++]++: %d  %d %n", i++, array[i++]++);
        System.out.printf("a[i]: %d %d%n",i, array[i]);
        i = 0;
        System.out.printf("a[0]: %d %d%n",i, array[0]);
        array[i++] = array[i++] + 1;
        i++;
        System.out.printf("a[0]: %d %d%n",i, array[0]);
    }
}
