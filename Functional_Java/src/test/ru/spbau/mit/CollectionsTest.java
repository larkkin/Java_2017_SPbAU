package ru.spbau.mit;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class CollectionsTest {

    @Test
    public void CollectionsMapTest() {
        Function1<Integer, Integer> f = i -> i * 2;
        ArrayList<Integer> mapped = new ArrayList<>();
        ArrayList<Integer> unmapped = new ArrayList<>();
        final int size = 5;
        for (int i = 0; i < size; i++) {
            mapped.add(i*2);
            unmapped.add(i);
        }
        Function1<Integer, Integer> g = i -> i / 2;
        Assert.assertEquals(unmapped, Collections.map(g, mapped));
        Assert.assertEquals(mapped, Collections.map(f, unmapped));
    }

    @Test
    public void WildcardsCollectionMapTest() {
        Collection<Integer> collectionOfIntegers = new ArrayList<>();
        final int size = 10;
        for (int i = 0; i < size; i++) {
            collectionOfIntegers.add(i);
        }
        Function1<Object, Object> f = o -> o;
        Assert.assertEquals(collectionOfIntegers, Collections.map(f, collectionOfIntegers));
    }

    @Test
    public void CollectionsFilterTest() {
        Predicate<Integer> p = i -> i % 2 == 0;
        List<Integer> all = new ArrayList<>();
        final int size = 5;
        for (int i = 0; i < size; i++) {
            all.add(i);
        }
        List<Integer> filtered = all.stream()
                                    .filter(i -> i % 2 == 0)
                                    .collect(Collectors.toList());
        Assert.assertEquals(filtered, Collections.filter(p, all));
        Predicate<Integer> p1 = i -> i < 0;
        Assert.assertTrue(Collections.filter(p1, all).isEmpty());
    }

    @Test
    public void WildcardsCollectionFilterTest() {
        Predicate<Object> p = o -> false;
        Collection<Integer> collectionOfIntegers = Arrays.asList(1, 0, 1);
        Assert.assertTrue(Collections.filter(p, collectionOfIntegers).isEmpty());
    }

    @Test
    public void CollectionTakeWhileUnlessTest() {
        final int size = 10;
        final int separator = 4;
        Predicate<Integer> p = i -> i < separator;
        List<Integer> all = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            all.add(i);
        }
        List<Integer> prefix = all.subList(0, separator);
        Assert.assertEquals(prefix, Collections.takeWhile(p, all));
        Assert.assertTrue(Collections.takeWhile(p.not(), all).isEmpty());
        Assert.assertTrue(Collections.takeUnless(p, all).isEmpty());
        Assert.assertEquals(prefix, Collections.takeUnless(p.not(), all));
    }

    @Test
    public void WildcardsCollectionTakeWhileUnlessTest() {
        Predicate<Object> p = o -> false;
        Collection<Integer> collectionOfIntegers = Arrays.asList(1, 0, 1);
        Assert.assertTrue(Collections.takeWhile(p, collectionOfIntegers).isEmpty());
        Assert.assertTrue(Collections.takeUnless(p.not(), collectionOfIntegers).isEmpty());
    }

    @Test
    public void CollectionsFoldlFoldrTest() {
        Function2<Integer, Integer, Integer> f = (i1, i2) -> i1 - i2;
        final int size = 15;
        ArrayList<Integer> all = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            all.add(i);
        }
        Integer sumOfEven = all.stream().mapToInt(i -> i % 2 == 0 ? i : 0).sum();
        Integer sumOfOdd = all.stream().mapToInt(i -> i % 2 == 1 ? i : 0).sum();
        Integer foldlExpectedResult = -(sumOfEven + sumOfOdd);
        Integer foldrExpectedResult = sumOfEven - sumOfOdd;
        Assert.assertEquals(foldlExpectedResult, Collections.foldl(0, f, all));
        Assert.assertEquals(foldrExpectedResult, Collections.foldr(0, f, all));
    }

    @Test
    public void CollectionsFoldlFoldrTwoTypesTest() {
        Function2<Boolean, Boolean, Integer> fl = (b, i) -> b && i % 2 == 0;
        Function2<Boolean, Integer, Boolean> fr = (i, b) -> b && i % 2 == 0;;
        final int size = 15;
        ArrayList<Integer> all = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            all.add(i * 2);
        }
        Assert.assertTrue(Collections.foldl(true, fl, all));
        Assert.assertTrue(Collections.foldr(true, fr, all));
        Assert.assertFalse(Collections.foldl(false, fl, all));
        Assert.assertFalse(Collections.foldr(false, fr, all));
        all.add(1);
        Assert.assertFalse(Collections.foldl(true, fl, all));
        Assert.assertFalse(Collections.foldr(true, fr, all));
    }

    @Test
    public void WildcardCollectionFoldlFoldrTest() {
        Function2<Double, Object, Object> f =  (o1, o2) -> 0.0;
        Collection<Number> collectionOfNumbers= Arrays.asList(1, 0.0, 1);
        Assert.assertEquals(0.0, Collections
                .foldl((Number) 0.0, f, collectionOfNumbers));
        Assert.assertEquals(0.0, Collections
                .foldr((Number) 0.0, f, collectionOfNumbers));
    }
}