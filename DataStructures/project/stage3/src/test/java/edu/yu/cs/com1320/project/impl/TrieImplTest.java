package edu.yu.cs.com1320.project.impl;

import edu.yu.cs.com1320.project.Trie;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;


//import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import java.util.Comparator;

import org.junit.jupiter.api.*;

import edu.yu.cs.com1320.project.Trie;

import static org.junit.jupiter.api.Assertions.*;

class TrieImplTest {

//
//    Trie<Integer> trie = new TrieImpl<>();
//    String string1 = "It was a dark and stormy night";
//    String string2 = "It was the best of times it was the worst of times";
//    String string3 = "It was a bright cold day in April and the clocks were striking thirteen";
//    String string4 = "I am free no matter what rules surround me";
//
//    @BeforeEach
//    public void init() {
//        for (String word : string1.split(" ")) {
//            trie.put(word, string1.indexOf(word));
//        }
//        for (String word : string2.split(" ")) {
//            trie.put(word, string2.indexOf(word));
//        }
//        for (String word : string3.split(" ")) {
//            trie.put(word, string3.indexOf(word));
//        }
//        for (String word : string4.split(" ")) {
//            trie.put(word, string4.indexOf(word));
//        }
//    }
//
//    @Test
//    public void testPut() {
//        assertThrows(IllegalArgumentException.class, () -> {
//            trie.put(null, 100);
//        });
//
//        trie.put("", 100);
//
//        trie.put("the", null);
//
//        assertEquals(trie.getAllSorted("the", Comparator.naturalOrder()).size(), 2);
//    }
//
//    @Test
//    public void testGetAllSorted() {
//        assertThrows(IllegalArgumentException.class, () -> {
//            trie.getAllSorted("the", null);
//        });
//
//        assertThrows(IllegalArgumentException.class, () -> {
//            trie.getAllSorted(null, Comparator.naturalOrder());
//        });
//
//        assertEquals(trie.getAllSorted("", Comparator.naturalOrder()).size(), 0);
//    }
//
//    @Test
//    public void testGetAllPrefixSorted() {
//        assertThrows(IllegalArgumentException.class, () -> {
//            trie.getAllWithPrefixSorted("the", null);
//        });
//
//        assertThrows(IllegalArgumentException.class, () -> {
//            trie.getAllWithPrefixSorted(null, Comparator.naturalOrder());
//        });
//
//        assertEquals(trie.getAllWithPrefixSorted("", Comparator.naturalOrder()).size(), 0);
//    }
//
//    @Test
//    public void testDeleteWithPrefix() {
//        assertThrows(IllegalArgumentException.class, () -> {
//            trie.deleteAllWithPrefix(null);
//        });
//
//        assertTrue(trie.deleteAllWithPrefix("").size() == 0);
//
//        assertEquals(trie.getAllWithPrefixSorted("the", Comparator.naturalOrder()).size(), 2);
//    }
//
//    @Test
//    public void testDeleteAll() {
//        assertThrows(IllegalArgumentException.class, () -> {
//            trie.deleteAll(null);
//        });
//
//        assertTrue(trie.deleteAll("").size() == 0);
//
//        assertEquals(trie.getAllSorted("the", Comparator.naturalOrder()).size(), 2);
//    }
//
//    @Test
//    public void testDelete() {
//        assertThrows(IllegalArgumentException.class, () -> {
//            trie.delete("the", null);
//        });
//
//        assertThrows(IllegalArgumentException.class, () -> {
//            trie.delete(null, 100);
//        });
//
//        assertTrue(trie.delete("", 0) == null);
//        //Deliberately don't use Integer factory
//        @SuppressWarnings("deprecation")
//        Integer largeInteger = new Integer(1000);
//        @SuppressWarnings("deprecation")
//        Integer largeInteger2 = new Integer(1000);
//        trie.put("largeInteger", largeInteger);
//        assertTrue(largeInteger != largeInteger2);
//        assertTrue(trie.delete("largeInteger", largeInteger2) == largeInteger);
//    }
//}

    @Test
    public void simpleTrieTest() {
        Trie trie = new TrieImpl<Integer>();
        trie.put("APPLE123", 1);
        trie.put("APPLE123", 2);
        trie.put("APPLE123", 3);
        trie.put("WORD87", 8);
        trie.put("WORD87", 7);

        List<Integer> apple123List = trie.getAllSorted("apple123", (int1, int2) -> {
            if ((int) int1 < (int) int2) {
                return -1;
            } else if ((int) int2 < (int) int1) {
                return 1;
            }
            return 0;});//this comparator will order integers from lowest to highest
        List<Integer> word87List = trie.getAllSorted("word87", (int1, int2) -> {
            if ((int) int1 < (int) int2) {
                return -1;
            } else if ((int) int2 < (int) int1) {
                return 1;
            }
            return 0;});

       assertEquals(3, apple123List.size());
        assertEquals(2, word87List.size());
        assertEquals(1, apple123List.get(0));
        assertEquals(2, apple123List.get(1));
        assertEquals(3, apple123List.get(2));
        assertEquals(7, word87List.get(0));
        assertEquals(8, word87List.get(1));

        trie.put("app", 12);
        trie.put("app", 5);
        trie.put("ap", 4);

        List<Integer> apList = trie.getAllWithPrefixSorted("AP", (int1, int2) -> {
            if ((int) int1 < (int) int2) {
                return -1;
            } else if ((int) int2 < (int) int1) {
                return 1;
            }
            return 0;});
        List<Integer> appList = trie.getAllWithPrefixSorted("APP", (int1, int2) -> {
            if ((int) int1 < (int) int2) {
                return -1;
            } else if ((int) int2 < (int) int1) {
                return 1;
            }
            return 0;});

        assertEquals(6, apList.size());
        assertEquals(5, appList.size());
        assertEquals(12, apList.get(5));
        assertEquals(12, appList.get(4));

        Set<Integer> deletedAppPrefix = trie.deleteAllWithPrefix("aPp");
        assertEquals(5, deletedAppPrefix.size());
        assertTrue(deletedAppPrefix.contains(3));
        assertTrue(deletedAppPrefix.contains(5));

        apList = trie.getAllWithPrefixSorted("AP", (int1, int2) -> {
            if ((int) int1 < (int) int2) {
                return -1;
            } else if ((int) int2 < (int) int1) {
                return 1;
            }
            return 0;});
        appList = trie.getAllWithPrefixSorted("APP", (int1, int2) -> {
            if ((int) int1 < (int) int2) {
                return -1;
            } else if ((int) int2 < (int) int1) {
                return 1;
            }
            return 0;});

        assertEquals(1, apList.size());
        assertEquals(0, appList.size());

        trie.put("deleteAll", 100);
        trie.put("deleteAll", 200);
        trie.put("deleteAll", 300);

        List<Integer> deleteList = trie.getAllSorted("DELETEALL", (int1, int2) -> {
            if ((int) int1 < (int) int2) {
                return -1;
            } else if ((int) int2 < (int) int1) {
                return 1;
            }
            return 0;});

        assertEquals(3, deleteList.size());
        Set<Integer> thingsActuallyDeleted = trie.deleteAll("DELETEall");
        assertEquals(3, thingsActuallyDeleted.size());
        assertTrue(thingsActuallyDeleted.contains(100));

        deleteList = trie.getAllSorted("DELETEALL", (int1, int2) -> {
            if ((int) int1 < (int) int2) {
                return -1;
            } else if ((int) int2 < (int) int1) {
                return 1;
            }
            return 0;});

        assertEquals(0, deleteList.size());

        trie.put("deleteSome", 100);
        trie.put("deleteSome", 200);
        trie.put("deleteSome", 300);

        List<Integer> deleteList2 = trie.getAllSorted("DELETESOME", (int1, int2) -> {
            if ((int) int1 < (int) int2) {
                return -1;
            } else if ((int) int2 < (int) int1) {
                return 1;
            }
            return 0;});

        assertEquals(3, deleteList2.size());
        Integer twoHundred = (Integer) trie.delete("deleteSome", 200);
        Integer nullInt = (Integer) trie.delete("deleteSome", 500);

        assertEquals(200, twoHundred);
        assertNull(nullInt);

        deleteList2 = trie.getAllSorted("DELETESOME", (int1, int2) -> {
            if ((int) int1 < (int) int2) {
                return -1;
            } else if ((int) int2 < (int) int1) {
                return 1;
            }
            return 0;});

        assertEquals(2, deleteList2.size());
        assertFalse(deleteList2.contains(200));
    }


}