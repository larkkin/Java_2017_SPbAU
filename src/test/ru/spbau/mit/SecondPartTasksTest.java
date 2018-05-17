package ru.spbau.mit;

import org.junit.Assert;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static ru.spbau.mit.SecondPartTasks.findPrinter;

public class SecondPartTasksTest {

    @Test
    public void testFindQuotes() {
        List<String> fileName = Arrays.asList(
                "./src/test/resources/love_me_do.txt",
                "./src/test/resources/octopus_s_garden.txt"
        );
        List<String> expected = Arrays.asList(
                "love, love me do",
                "you know i love you",
                "so please, love me do",
                "whoa, love me do",
                "love, love me do",
                "you know i love you",
                "so please, love me do",
                "whoa, love me do",
                "someone to love",
                "someone to love",
                "love, love me do",
                "you know i love you",
                "so please, love me do",
                "whoa, love me do",
                "love, love me do",
                "you know i love you",
                "so please, love me do",
                "whoa, love me do",
                "yeah, love me do",
                "whoa, oh, love me do"
        );

        assertEquals(expected, SecondPartTasks.findQuotes(fileName, "love"));
    }

    @Test
    public void testPiDividedBy4() {
        assertEquals(Math.PI / 4, SecondPartTasks.piDividedBy4(), 0.01);
    }

    @Test
    public void testFindPrinter() {
        Map<String, List<String>> compositions = new HashMap<>();
        compositions.put("Paul", SecondPartTasks.findQuotes(
                Arrays.asList("src/test/resources/love_me_do.txt"), "love"));
        compositions.put("Ringo", SecondPartTasks.findQuotes(
                Arrays.asList("src/test/resources/octopus_s_garden.txt"), "love"));
        assertEquals("Paul", findPrinter(compositions));
    }

    @Test
    public void testCalculateGlobalOrder() {
        Map<String, Integer> store1 = new HashMap<>();
        store1.put("BareEscentuals", 10);
        store1.put("Naked", 30);
        store1.put("Dior", 45);

        Map<String, Integer> store2 = new HashMap<>();
        store2.put("Dior", 100);
        store2.put("NYX", 50);
        store2.put("TooFaced", 25);
        store2.put("BareEscentuals", 15);

        Map<String, Integer> cosmetics = SecondPartTasks.calculateGlobalOrder(Arrays.asList(store1, store2));

        Assert.assertEquals(25, (int) cosmetics.get("BareEscentuals"));
        Assert.assertEquals(30, (int) cosmetics.get("Naked"));
        Assert.assertEquals(145, (int) cosmetics.get("Dior"));
        Assert.assertEquals(50, (int) cosmetics.get("NYX"));
        Assert.assertEquals(25, (int) cosmetics.get("TooFaced"));

    }
}