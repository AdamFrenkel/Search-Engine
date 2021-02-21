package edu.yu.cs.com1320.project.stage1.impl;

import edu.yu.cs.com1320.project.HashTable;
import edu.yu.cs.com1320.project.impl.HashTableImpl;
import edu.yu.cs.com1320.project.stage1.Document;
import org.junit.Test;

import java.net.URI;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.*;

public class DocumentImplTest {
    @Test
    public void hashTableImplSimplePutAndGet() {
        HashTable<Integer,Integer> hashTable = new HashTableImpl<Integer,Integer>();
        hashTable.put(1,2);
        hashTable.put(3,6);
        hashTable.put(7,14);
        int x = hashTable.get(1);
        int y = hashTable.get(3);
        int z = hashTable.get(7);
        assertEquals(2, x);
        assertEquals(6, y);
        assertEquals(14, z);



    }

    @Test
    public void hashTableImplALotOfInfoTest() {
        HashTable<Integer,Integer> hashTable = new HashTableImpl<Integer,Integer>();
        for (int i = 0; i<1000; i++) {
            hashTable.put(i,2*i);
        }

        int aa = hashTable.get(450);
        assertEquals(900, aa);
    }


    @Test
    public void hashTableImplCollisionTest() {
        HashTable<Integer,Integer> hashTable = new HashTableImpl<Integer,Integer>();
        hashTable.put(1, 9);
        hashTable.put(6,12);
        hashTable.put(11,22);
        int a = hashTable.get(1);
        int b = hashTable.get(6);
        int c = hashTable.get(11);
        assertEquals(9, a);
        assertEquals(12, b);
        assertEquals(22, c);
    }

    @Test
    public void hashTableImplReplacementTest() {
        HashTable<Integer,Integer> hashTable = new HashTableImpl<Integer,Integer>();
        hashTable.put(1,2);
        int a = hashTable.put(1, 3);
        assertEquals(2, a);
        int b = hashTable.put(1, 4);
        assertEquals(3,b);
        int c = hashTable.put(1, 9);
        assertEquals(4, c);
    }

    @Test
    public void hashTableDelNullPut() {
        HashTable<String,Integer> hashTable = new HashTableImpl<String,Integer>();

        hashTable.put("Defied", (Integer)22345);
        Integer test1a = hashTable.get("Defied");
        assertEquals(test1a, (Integer)22345);
        hashTable.put("Defied", null);
        Integer test1b = hashTable.get("Defied");
        assertEquals(test1b,null);
        hashTable.put("Oakland", 87123);

        Integer test2a = hashTable.get("Oakland");
        assertEquals(test2a, (Integer)87123);
        hashTable.put("Oakland", null);
        hashTable.get("Oakland");
        Integer test2b = hashTable.get("Oakland");
        assertEquals(test2b,null);

        hashTable.put("Sanguine", (Integer)4682);
        Integer test3a = hashTable.get("Sanguine");
        assertEquals(test3a, (Integer)4682);
        hashTable.put("Sanguine", null);
        hashTable.get("Sanguine");
        Integer test3b = hashTable.get("Sanguine");
        assertEquals(test3b,null);
    }

    @Test
    public void HashEqualButNotEqual() {
        HashTable<String,Integer> hashTable = new HashTableImpl<String,Integer>();

        hashTable.put("tensada", 3521);
        hashTable.put("friabili", 1253);
        Integer test1a = hashTable.get("tensada");
        assertEquals(test1a, (Integer)3521);
        Integer test1b = hashTable.get("friabili");
        assertEquals(test1b, (Integer)1253);

        hashTable.put("abyz", 8948);
        hashTable.put("abzj", 84980);
        Integer test2a = hashTable.get("abyz");
        assertEquals(test2a, (Integer)8948);
        Integer test2b = hashTable.get("abzj");
        assertEquals(test2b, 84980);

        hashTable.put("Siblings", 27128);
        hashTable.put("Teheran", 82172);
        Integer test3a = hashTable.get("Siblings");
        assertEquals(test3a, (Integer)27128);
        Integer test3b = hashTable.get("Teheran");
        assertEquals(test3b, (Integer)82172);

    }

    //Tests from piazza
    /**
     * Testing Basic Text Document
     *
     * @throws URISyntaxException
     */
    @Test
    public void docTest() throws URISyntaxException {
        URI me1 = new URI("hello1");
        URI me2 = new URI("hello2");
        String s1 = "Four xcor and ajdfjajfjf this i the fghhghg jdfjdjkjfdkfkdsfk sdjfdf this is my password backwards ffsfsdf%^&*^%&^%&^";
        byte[] b1 = { 1, 2, 4, 56, 7, 7, 6, 5, 43, 4, 6, 7, 8, 8, 8, 55, 52, 5, 2, 52, 75, 95, 25, 85, 74, 52, 52, 5, 67,
                61 };
        Document doc1 = new DocumentImpl(me1, s1);
        Document doc2 = new DocumentImpl(me2, b1);
        assertEquals("Four xcor and ajdfjajfjf this i the fghhghg jdfjdjkjfdkfkdsfk sdjfdf this is my password backwards ffsfsdf%^&*^%&^%&^", doc1.getDocumentTxt());
        assertEquals(b1, doc2.getDocumentBinaryData());
    }
    @Test
    public void TestBasicTxtDocument() throws URISyntaxException {
        URI uri = new URI ("Hello");
        DocumentImpl text = new DocumentImpl(uri, "Hello");
        assertEquals("Hello", text.getDocumentTxt());
        assertEquals(null, text.getDocumentBinaryData());
        assertEquals(uri, text.getKey());
        assertEquals(true, text.equals(new DocumentImpl(uri, "Hello")));
    }
    @Test
    public void TestBasicBinaryDocument() throws URISyntaxException {
        String str = "Hello";
        URI uri = new URI ("Hello");
        byte[] test = str.getBytes();
        DocumentImpl binary = new DocumentImpl(uri, test);
        assertEquals(null, binary.getDocumentTxt());
        assertEquals(test, binary.getDocumentBinaryData());
        assertEquals(uri, binary.getKey());
        assertTrue(binary.equals(new DocumentImpl(uri, test)));
    }

    @Test
    public void illegal () throws URISyntaxException {
        boolean test = false;
        String str = "Hello";
        URI uri = new URI ("Hello");
        byte[] byteArray = str.getBytes();
        String nullString = null;
        URI nullUri = null;
        byte[] nullArray = null;
        String emptyString = "";
        byte[] emptyArray = new byte[0];
        URI emptyUri = new URI("");
        try{
            DocumentImpl testNullUriInString = new DocumentImpl(nullUri, str);
        } catch (IllegalArgumentException e) {
            test=true;
        }
        assertEquals(true, test);
        test=false;
        try{
            DocumentImpl TestNullUriInByte = new DocumentImpl(nullUri, byteArray);
        } catch (IllegalArgumentException e) {
            test=true;
        }
        assertEquals(true, test);
        test=false;
        try{
            DocumentImpl TestNullStringInString = new DocumentImpl(uri, nullString);
        } catch (IllegalArgumentException e) {
            test=true;
        }
        assertEquals(true, test);
        test=false;
        try{
            DocumentImpl TestNullByteInByte = new DocumentImpl(uri, nullArray);
        } catch (IllegalArgumentException e) {
            test=true;
        }
        assertEquals(true, test);
        test=false;
        try{
            DocumentImpl TestEmptyStringInString = new DocumentImpl(uri, emptyString);
        } catch (IllegalArgumentException e) {
            test=true;
        }
        assertEquals(true, test);
        test=false;
        try{
            DocumentImpl TestEmptyByteInByte = new DocumentImpl(uri, emptyArray);
        } catch (IllegalArgumentException e) {
            test=true;
        }
        assertEquals(true, test);
        test=false;
        try{
            DocumentImpl TestEmptyUriInByte = new DocumentImpl(emptyUri, byteArray);
        } catch (IllegalArgumentException e) {
            test=true;
        }
        assertEquals(true, test);
        test=false;
        try{
            DocumentImpl TestEmptyUriInString = new DocumentImpl(emptyUri, str);
        } catch (IllegalArgumentException e) {
            test=true;
        }
        assertEquals(true, test);
        test=false;

    }
}

//package edu.yu.cs.com1320.project.stage1.impl;
//
//import edu.yu.cs.com1320.project.HashTable;
//import edu.yu.cs.com1320.project.impl.HashTableImpl;
//import edu.yu.cs.com1320.project.stage1.Document;
//import org.junit.jupiter.api.Test;
//
//import java.net.URI;
//import java.net.URISyntaxException;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class DocumentImplTest {
//
//    @Test
//    public void hashTableImplSimplePutAndGet() {
//        HashTable<Integer,Integer> hashTable = new HashTableImpl<Integer,Integer>();
//        hashTable.put(1,2);
//        hashTable.put(3,6);
//        hashTable.put(7,14);
//        int x = hashTable.get(1);
//        int y = hashTable.get(3);
//        int z = hashTable.get(7);
//        assertEquals(2, x);
//        assertEquals(6, y);
//        assertEquals(14, z);
//
//
//
//    }
//
//    @Test
//    public void hashTableImplALotOfInfoTest() {
//        HashTable<Integer,Integer> hashTable = new HashTableImpl<Integer,Integer>();
//        for (int i = 0; i<1000; i++) {
//            hashTable.put(i,2*i);
//        }
//
//        int aa = hashTable.get(450);
//        assertEquals(900, aa);
//    }
//
//
//    @Test
//    public void hashTableImplCollisionTest() {
//        HashTable<Integer,Integer> hashTable = new HashTableImpl<Integer,Integer>();
//        hashTable.put(1, 9);
//        hashTable.put(6,12);
//        hashTable.put(11,22);
//        int a = hashTable.get(1);
//        int b = hashTable.get(6);
//        int c = hashTable.get(11);
//        assertEquals(9, a);
//        assertEquals(12, b);
//        assertEquals(22, c);
//    }
//
//    @Test
//    public void hashTableImplReplacementTest() {
//        HashTable<Integer,Integer> hashTable = new HashTableImpl<Integer,Integer>();
//        hashTable.put(1,2);
//        int a = hashTable.put(1, 3);
//        assertEquals(2, a);
//        int b = hashTable.put(1, 4);
//        assertEquals(3,b);
//        int c = hashTable.put(1, 9);
//        assertEquals(4, c);
//    }
//
//    @Test
//    public void hashTableDelNullPut() {
//        HashTable<String,Integer> hashTable = new HashTableImpl<String,Integer>();
//
//        hashTable.put("Defied", (Integer)22345);
//        Integer test1a = hashTable.get("Defied");
//        assertEquals(test1a, (Integer)22345);
//        hashTable.put("Defied", null);
//        Integer test1b = hashTable.get("Defied");
//        assertEquals(test1b,null);
//        hashTable.put("Oakland", 87123);
//
//        Integer test2a = hashTable.get("Oakland");
//        assertEquals(test2a, (Integer)87123);
//        hashTable.put("Oakland", null);
//        hashTable.get("Oakland");
//        Integer test2b = hashTable.get("Oakland");
//        assertEquals(test2b,null);
//
//        hashTable.put("Sanguine", (Integer)4682);
//        Integer test3a = hashTable.get("Sanguine");
//        assertEquals(test3a, (Integer)4682);
//        hashTable.put("Sanguine", null);
//        hashTable.get("Sanguine");
//        Integer test3b = hashTable.get("Sanguine");
//        assertEquals(test3b,null);
//    }
//
//    @Test
//    public void HashEqualButNotEqual() {
//        HashTable<String,Integer> hashTable = new HashTableImpl<String,Integer>();
//
//        hashTable.put("tensada", 3521);
//        hashTable.put("friabili", 1253);
//        Integer test1a = hashTable.get("tensada");
//        assertEquals(test1a, (Integer)3521);
//        Integer test1b = hashTable.get("friabili");
//        assertEquals(test1b, (Integer)1253);
//
//        hashTable.put("abyz", 8948);
//        hashTable.put("abzj", 84980);
//        Integer test2a = hashTable.get("abyz");
//        assertEquals(test2a, (Integer)8948);
//        Integer test2b = hashTable.get("abzj");
//        assertEquals(test2b, 84980);
//
//        hashTable.put("Siblings", 27128);
//        hashTable.put("Teheran", 82172);
//        Integer test3a = hashTable.get("Siblings");
//        assertEquals(test3a, (Integer)27128);
//        Integer test3b = hashTable.get("Teheran");
//        assertEquals(test3b, (Integer)82172);
//
//    }
//
//    //Tests from piazza
//    /**
//     * Testing Basic Text Document
//     *
//     * @throws URISyntaxException
//     */
//    @Test
//    public void docTest() throws URISyntaxException {
//        URI me1 = new URI("hello1");
//        URI me2 = new URI("hello2");
//        String s1 = "Four xcor and ajdfjajfjf this i the fghhghg jdfjdjkjfdkfkdsfk sdjfdf this is my password backwards ffsfsdf%^&*^%&^%&^";
//        byte[] b1 = { 1, 2, 4, 56, 7, 7, 6, 5, 43, 4, 6, 7, 8, 8, 8, 55, 52, 5, 2, 52, 75, 95, 25, 85, 74, 52, 52, 5, 67,
//                61 };
//        Document doc1 = new DocumentImpl(me1, s1);
//        Document doc2 = new DocumentImpl(me2, b1);
//        assertEquals("Four xcor and ajdfjajfjf this i the fghhghg jdfjdjkjfdkfkdsfk sdjfdf this is my password backwards ffsfsdf%^&*^%&^%&^", doc1.getDocumentTxt());
//        assertEquals(b1, doc2.getDocumentBinaryData());
//    }
//    @Test
//    void TestBasicTxtDocument() throws URISyntaxException {
//        URI uri = new URI ("Hello");
//        DocumentImpl text = new DocumentImpl(uri, "Hello");
//        assertEquals("Hello", text.getDocumentTxt());
//        assertEquals(null, text.getDocumentBinaryData());
//        assertEquals(uri, text.getKey());
//        assertEquals(true, text.equals(new DocumentImpl(uri, "Hello")));
//    }
//    @Test
//    void TestBasicBinaryDocument() throws URISyntaxException {
//        String str = "Hello";
//        URI uri = new URI ("Hello");
//        byte[] test = str.getBytes();
//        DocumentImpl binary = new DocumentImpl(uri, test);
//        assertEquals(null, binary.getDocumentTxt());
//        assertEquals(test, binary.getDocumentBinaryData());
//        assertEquals(uri, binary.getKey());
//        assertTrue(binary.equals(new DocumentImpl(uri, test)));
//    }
//
//    @Test
//    void illegal () throws URISyntaxException {
//        boolean test = false;
//        String str = "Hello";
//        URI uri = new URI ("Hello");
//        byte[] byteArray = str.getBytes();
//        String nullString = null;
//        URI nullUri = null;
//        byte[] nullArray = null;
//        String emptyString = "";
//        byte[] emptyArray = new byte[0];
//        URI emptyUri = new URI("");
//        try{
//            DocumentImpl testNullUriInString = new DocumentImpl(nullUri, str);
//        } catch (IllegalArgumentException e) {
//            test=true;
//        }
//        assertEquals(true, test);
//        test=false;
//        try{
//            DocumentImpl TestNullUriInByte = new DocumentImpl(nullUri, byteArray);
//        } catch (IllegalArgumentException e) {
//            test=true;
//        }
//        assertEquals(true, test);
//        test=false;
//        try{
//            DocumentImpl TestNullStringInString = new DocumentImpl(uri, nullString);
//        } catch (IllegalArgumentException e) {
//            test=true;
//        }
//        assertEquals(true, test);
//        test=false;
//        try{
//            DocumentImpl TestNullByteInByte = new DocumentImpl(uri, nullArray);
//        } catch (IllegalArgumentException e) {
//            test=true;
//        }
//        assertEquals(true, test);
//        test=false;
//        try{
//            DocumentImpl TestEmptyStringInString = new DocumentImpl(uri, emptyString);
//        } catch (IllegalArgumentException e) {
//            test=true;
//        }
//        assertEquals(true, test);
//        test=false;
//        try{
//            DocumentImpl TestEmptyByteInByte = new DocumentImpl(uri, emptyArray);
//        } catch (IllegalArgumentException e) {
//            test=true;
//        }
//        assertEquals(true, test);
//        test=false;
//        try{
//            DocumentImpl TestEmptyUriInByte = new DocumentImpl(emptyUri, byteArray);
//        } catch (IllegalArgumentException e) {
//            test=true;
//        }
//        assertEquals(true, test);
//        test=false;
//        try{
//            DocumentImpl TestEmptyUriInString = new DocumentImpl(emptyUri, str);
//        } catch (IllegalArgumentException e) {
//            test=true;
//        }
//        assertEquals(true, test);
//        test=false;
//
//    }
//}