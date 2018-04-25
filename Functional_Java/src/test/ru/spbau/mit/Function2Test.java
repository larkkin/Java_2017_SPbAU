package ru.spbau.mit;


import org.junit.Assert;
import org.junit.Test;


public class Function2Test {
    @Test
    public void TestApply() {
        Function2<Integer, Integer, Integer> mult = (i1, i2) -> i1 * i2;

        final int size = 15;
        for (int i = 0; i < size; i++) {
            Assert.assertEquals(i * 2, (int) mult.apply(i, 2));
        }
    }

    @Test
    public void TestCompose() {
        Function2<Integer, Integer, Integer> mult = (i1, i2) -> i1 * i2;
        Function1<Integer, Integer> divByTwo = i -> i / 2;

        final int size = 15;
        for (int i = 0; i < size; i++) {
            Assert.assertEquals((Integer) i, mult.compose(divByTwo).apply(i, 2));
        }
    }

    @Test
    public void TestWildcardCompose() {
        Function2<Integer, Integer, Integer> mult = (i1, i2) -> i1 * i2;
        Function1<Double, Number> zero = i -> 0.0;

        final int size = 15;
        for (int i = 0; i < size; i++) {
            Assert.assertEquals((Double) 0.0, mult.compose(zero).apply(i, 2));
        }
    }

    @Test
    public void testBind() {
        Function2<Integer, Integer, Integer> mult = (i1, i2) -> i1 * i2;
        Function1<Integer, Integer> multByTwoRight = mult.bind2(2);
        Function1<Integer, Integer> multByTwoLeft = mult.bind1(2);

        final int size = 15;
        for (int i = 0; i < size; i++) {
            Assert.assertEquals(mult.apply(i, 2), multByTwoRight.apply(i));
            Assert.assertEquals(mult.apply(2, i), multByTwoLeft.apply(i));
        }
    }

    @Test
    public void testCurry() {
        Function2<Integer, Integer, Integer> mult = (i1, i2) -> i1 * i2;

        final int size = 15;
        for (int i = 0; i < size; i++) {
            Function1<Integer, Integer> multByTwo = mult.curry().apply(2);
            Assert.assertEquals(mult.apply(i, 2), multByTwo.apply(i));
        }
    }
}
