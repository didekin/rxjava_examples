package com.didekin.lambdas;

import org.junit.Test;

import java.io.File;
import java.io.FileFilter;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * User: pedro@didekin
 * Date: 06/12/16
 * Time: 15:04
 */
public class SimpleFileFilterTest {

    @Test
    public void testFilter_1(){

        FileFilter fileFilter = (File f) -> f.getName().endsWith(".java");

        assertThat(fileFilter.accept(new File("path/hola.java")), is(true));
        assertThat(fileFilter.accept(new File("path/hola.c")), is(false));
    }
}