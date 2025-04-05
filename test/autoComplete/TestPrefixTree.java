package autoComplete;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

/**
 * Class to test the tree implementation
 */
public class TestPrefixTree {
    
    @Test
    public void testAdd(){
        PrefixTree tree = new PrefixTree();
        tree.add("cat");
        tree.add("muse");
        tree.add("muscle");
        tree.add("musk");
        tree.add("po");
        tree.add("pot");
        tree.add("pottery");
        tree.add("potato");
        tree.add("possible");
        tree.add("possum");
        tree.add("pot");
        assertEquals(10, tree.size());
    }

    @Test
    public void testContains(){
        PrefixTree tree = new PrefixTree();
        tree.add("cat");
        tree.add("muse");
        tree.add("muscle");
        tree.add("musk");
        tree.add("po");
        tree.add("pot");
        tree.add("pottery");
        tree.add("potato");
        tree.add("possible");
        tree.add("possum");
        tree.add("pot");
        assertFalse(tree.contains("mu"));
        assertFalse(tree.contains("dog"));
        assertTrue(tree.contains("pot"));
        assertTrue(tree.contains("pottery"));
        assertTrue(tree.contains("possum"));
        assertEquals(10, tree.size());
    }
    /**
     * Tests an edge case I found with the "compute" prefix, which only returned "compute" and "computed" despite
     * "computes" and "computer" also being in the dictionary.
     * It's fixed now. Obviously.
     */
    @Test
    public void testComputes(){
        PrefixTree tree = new PrefixTree();
        tree.add("compute");
        tree.add("computed");
        tree.add("computer");
        tree.add("computes");
        ArrayList<String> result = tree.getWordsForPrefix("comput");
        System.out.println(result.toString());
        assertEquals(4, result.size());
        assertTrue(result.contains("compute"));
        assertTrue(result.contains("computes"));
        assertTrue(result.contains("computed"));
        assertTrue(result.contains("computer"));
    }

    @Test
    public void testPrefix(){
        PrefixTree tree = new PrefixTree();
        tree.add("cat");
        tree.add("muse");
        tree.add("muscle");
        tree.add("musk");
        tree.add("poe");
        tree.add("pot");
        tree.add("pottery");
        tree.add("potato");
        tree.add("possible");
        tree.add("possum");
        tree.add("pot");
        ArrayList<String> result = tree.getWordsForPrefix("pot");
        System.out.println(result.toString());
        assertEquals(3, result.size());
        assertTrue(result.contains("pot"));
        assertTrue(result.contains("pottery"));
        assertTrue(result.contains("potato"));
        
        result = tree.getWordsForPrefix("mu");
        assertEquals(3, result.size());
        assertTrue(result.contains("muse"));
        assertTrue(result.contains("muscle"));
        assertTrue(result.contains("musk"));
    }
}
