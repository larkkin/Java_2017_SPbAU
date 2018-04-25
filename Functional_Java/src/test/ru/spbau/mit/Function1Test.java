package ru.spbau.mit;


import org.junit.Assert;
import org.junit.Test;


public class Function1Test {

    @Test
    public void TestApply() {
        Function1<Integer, Integer> multByTwo = i -> i * 2;
        Function1<Integer, Integer> divByTwo = i -> i / 2;

        final int size = 15;
        for (int i = 0; i < size; i++) {
            Assert.assertEquals(i * 2, (int) multByTwo.apply(i));
            Assert.assertEquals(i / 2, (int) divByTwo.apply(i));
        }
    }

    @Test
    public void TestCompose() {
        Function1<Integer, Integer> multByTwo = i -> i * 2;
        Function1<Integer, Integer> divByTwo = i -> i / 2;

        final int size = 15;
        for (int i = 0; i < size; i++) {
            Assert.assertEquals((Integer) i, multByTwo.compose(divByTwo).apply(i));
        }
    }

    @Test
    public void TestWildcardCompose() {
        Function1<Integer, Integer> multByTwo = i -> i * 2;
        Function1<Double, Number> zero = i -> 0.0;

        final int size = 15;
        for (int i = 0; i < size; i++) {
            Assert.assertEquals((Double) 0.0, multByTwo.compose(zero).apply(i));
        }
    }
}