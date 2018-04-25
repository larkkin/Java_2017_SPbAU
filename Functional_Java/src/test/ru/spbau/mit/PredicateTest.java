package ru.spbau.mit;

import org.junit.Assert;
import org.junit.Test;

public class PredicateTest {

    @Test
    public void TestApply() {
        Predicate<Integer> p = i -> i > 5;
        final int moreThanFive = 6;
        final int lessThanFive = 1;
        Assert.assertTrue(p.apply(moreThanFive));
        Assert.assertFalse(p.apply(lessThanFive));
    }

    @Test
    public void TestOperators() {
        Predicate<Integer> p1 = i -> i > 5;
        Predicate<Integer> p2 = i -> i % 2 == 0;
        final int moreThanFiveEven = 6;
        final int moreThanFiveOdd = 7;
        final int lessThanFiveOdd = 1;
        final int lessThanFiveEven = 2;
        Assert.assertTrue(p1.and(p2).apply(moreThanFiveEven));
        Assert.assertFalse(p1.and(p2).apply(moreThanFiveOdd));
        Assert.assertFalse(p1.and(p2).apply(lessThanFiveEven));
        Assert.assertFalse(p1.and(p2).apply(lessThanFiveOdd));
        Assert.assertTrue(p1.or(p2).apply(moreThanFiveEven));
        Assert.assertTrue(p1.or(p2).apply(moreThanFiveOdd));
        Assert.assertTrue(p1.or(p2).apply(lessThanFiveEven));
        Assert.assertFalse(p1.or(p2).apply(lessThanFiveOdd));
        Assert.assertTrue(p1.not().apply(lessThanFiveEven));
        Assert.assertFalse(p1.not().apply(moreThanFiveEven));
    }


    @Test
    public void TestAlwaysTrue() {
        Assert.assertTrue(Predicate.ALWAYS_TRUE.apply(null));
        Assert.assertTrue(Predicate.ALWAYS_TRUE.apply(new Object()));
    }

    @Test
    public void TestAlwaysFalse() {
        Assert.assertFalse(Predicate.ALWAYS_FALSE.apply(null));
        Assert.assertFalse(Predicate.ALWAYS_FALSE.apply(new Object()));
    }

    @Test
    public void TestWildcardOperators() {
        Predicate<Integer> p1 = i -> i > 5;
        Predicate<Object> pFalse = o -> false;
        Predicate<Object> pTrue = o -> true;
        final int moreThanFive = 6;

        Assert.assertFalse(p1.and(pFalse).apply(moreThanFive));
        Assert.assertTrue(p1.or(pFalse).apply(moreThanFive));
        Assert.assertTrue(p1.and(pTrue).apply(moreThanFive));
        Assert.assertTrue(p1.or(pTrue).apply(moreThanFive));
    }


    @Test
    public void TestLazinessNoExceptions() {
        final Predicate<Object> THROW = new Predicate<Object>() {
            @Override
            public Boolean apply(Object arg) {
                throw new IllegalStateException();
            }
        };

        Assert.assertTrue(Predicate.ALWAYS_TRUE.or(THROW).apply(true));
        Assert.assertFalse(Predicate.ALWAYS_FALSE.and(THROW).apply(true));
    }

    @Test(expected = IllegalStateException.class)
    public void TestLazinessExceptions() {
        final Predicate<Object> THROW = new Predicate<Object>() {
            @Override
            public Boolean apply(Object arg) {
                throw new IllegalStateException();
            }
        };

        Assert.assertTrue(Predicate.ALWAYS_FALSE.or(THROW).apply(true));
        Assert.assertFalse(Predicate.ALWAYS_TRUE.and(THROW).apply(true));
    }


}