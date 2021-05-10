package edu.yu.cs.com1320.project.stage4.impl;



//import edu.yu.cs.com1320.project.HashTable;
//import edu.yu.cs.com1320.project.impl.HashTableImpl;
import edu.yu.cs.com1320.project.stage5.Document;
import edu.yu.cs.com1320.project.stage5.DocumentStore;
import edu.yu.cs.com1320.project.stage5.impl.DocumentImpl;
import edu.yu.cs.com1320.project.stage5.impl.DocumentStoreImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import edu.yu.cs.com1320.project.impl.MinHeapImpl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class DocumentStoreImplTest {

    /**
    my stage 5 tests
     */
    @Test
    public void testStage5Undo2() throws IOException {
        DocumentStore store = new DocumentStoreImpl(null);
        System.out.println("txt1 bytes = " + txt1.getBytes().length);
        System.out.println("txt2 bytes = " + txt2.getBytes().length);
        System.out.println("txt3 bytes = " + txt3.getBytes().length);
        System.out.println("txt4 bytes = " + txt4.getBytes().length);
        System.out.println("txt5 bytes = " + txt5.getBytes().length);
        System.out.println("txt6 bytes = " + txt6.getBytes().length);
        store.putDocument(new ByteArrayInputStream(this.txt1.getBytes()), this.uri1, DocumentStore.DocumentFormat.TXT);
//        store.putDocument(new ByteArrayInputStream(this.txt2.getBytes()), this.uri2, DocumentStore.DocumentFormat.TXT);
//        store.putDocument(new ByteArrayInputStream(this.txt3.getBytes()), this.uri3, DocumentStore.DocumentFormat.TXT);
//        store.putDocument(new ByteArrayInputStream(this.txt4.getBytes()), this.uri4, DocumentStore.DocumentFormat.TXT);
//        store.putDocument(new ByteArrayInputStream(this.txt5.getBytes()), this.uri5, DocumentStore.DocumentFormat.TXT);
//        store.putDocument(new ByteArrayInputStream(this.txt6.getBytes()), this.uri6, DocumentStore.DocumentFormat.TXT);
        String fileNameUri1 = System.getProperty("user.dir") + uri1.getRawSchemeSpecificPart();
        String fileNameUri2 = System.getProperty("user.dir") + uri2.getRawSchemeSpecificPart();
        String fileNameUri3 = System.getProperty("user.dir") + uri3.getRawSchemeSpecificPart();
        String fileNameUri4 = System.getProperty("user.dir") + uri4.getRawSchemeSpecificPart();
        String fileNameUri5 = System.getProperty("user.dir") + uri5.getRawSchemeSpecificPart();
        String fileNameUri6 = System.getProperty("user.dir") + uri6.getRawSchemeSpecificPart();
        boolean pass = false;
        try {
            Files.readAllBytes(Paths.get(fileNameUri1 + ".json"));
        } catch (NoSuchFileException n) {
            pass = true;
        }
        assertTrue(pass);
        assertNotNull(store.getDocument(uri1));
        store.setMaxDocumentBytes(0);
        assertNotNull(Files.readAllBytes(Paths.get(fileNameUri1 + ".json")));
        store.deleteDocument(uri1);
        pass = false;
        try {
            Files.readAllBytes(Paths.get(fileNameUri1 + ".json"));
        } catch (NoSuchFileException n) {
            pass = true;
        }
        assertTrue(pass);
        store.setMaxDocumentBytes(10000);
        store.setMaxDocumentCount(1);
        store.putDocument(new ByteArrayInputStream(this.txt2.getBytes()), this.uri2, DocumentStore.DocumentFormat.TXT);
        store.putDocument(new ByteArrayInputStream(this.txt3.getBytes()), this.uri3, DocumentStore.DocumentFormat.TXT);
        assertNotNull(Files.readAllBytes(Paths.get(fileNameUri2 + ".json")));
        store.putDocument(new ByteArrayInputStream(this.txt4.getBytes()), this.uri2, DocumentStore.DocumentFormat.TXT);
        pass = false;
        try {
            Files.readAllBytes(Paths.get(fileNameUri2 + ".json"));
        } catch (NoSuchFileException n) {
            pass = true;
        }
        assertTrue(pass);
        assertNotNull(Files.readAllBytes(Paths.get(fileNameUri3 + ".json")));
        assertEquals(1,store.search("4th").size());
        assertNotNull(store.getDocument(uri2));
        store.undo();
        assertNotNull(Files.readAllBytes(Paths.get(fileNameUri2 + ".json")));
        pass = false;
        try {
            Files.readAllBytes(Paths.get(fileNameUri3 + ".json"));
        } catch (NoSuchFileException n) {
            pass = true;
        }
        assertTrue(pass);
        assertNotNull(store.getDocument(uri3));
    }
    @Test
    public void testStage5Undo() throws IOException {
        DocumentStore store = new DocumentStoreImpl(null);
        System.out.println("txt1 bytes = " + txt1.getBytes().length);
        System.out.println("txt2 bytes = " + txt2.getBytes().length);
        System.out.println("txt3 bytes = " + txt3.getBytes().length);
        System.out.println("txt4 bytes = " + txt4.getBytes().length);
        System.out.println("txt5 bytes = " + txt5.getBytes().length);
        System.out.println("txt6 bytes = " + txt6.getBytes().length);
        store.putDocument(new ByteArrayInputStream(this.txt1.getBytes()), this.uri1, DocumentStore.DocumentFormat.TXT);
//        store.putDocument(new ByteArrayInputStream(this.txt2.getBytes()), this.uri2, DocumentStore.DocumentFormat.TXT);
//        store.putDocument(new ByteArrayInputStream(this.txt3.getBytes()), this.uri3, DocumentStore.DocumentFormat.TXT);
//        store.putDocument(new ByteArrayInputStream(this.txt4.getBytes()), this.uri4, DocumentStore.DocumentFormat.TXT);
//        store.putDocument(new ByteArrayInputStream(this.txt5.getBytes()), this.uri5, DocumentStore.DocumentFormat.TXT);
//        store.putDocument(new ByteArrayInputStream(this.txt6.getBytes()), this.uri6, DocumentStore.DocumentFormat.TXT);
        String fileNameUri1 = System.getProperty("user.dir") + uri1.getRawSchemeSpecificPart();
        String fileNameUri2 = System.getProperty("user.dir") + uri2.getRawSchemeSpecificPart();
        String fileNameUri3 = System.getProperty("user.dir") + uri3.getRawSchemeSpecificPart();
        String fileNameUri4 = System.getProperty("user.dir") + uri4.getRawSchemeSpecificPart();
        String fileNameUri5 = System.getProperty("user.dir") + uri5.getRawSchemeSpecificPart();
        String fileNameUri6 = System.getProperty("user.dir") + uri6.getRawSchemeSpecificPart();
        boolean pass = false;
        try {
            Files.readAllBytes(Paths.get(fileNameUri1 + ".json"));
        }catch (NoSuchFileException n){
            pass = true;
        }
        assertTrue(pass);
        assertNotNull(store.getDocument(uri1));
        //test undo put normal
        store.undo(uri1);
        boolean pass2 = false;
        try {
            Files.readAllBytes(Paths.get(fileNameUri1 + ".json"));
        }catch (NoSuchFileException n){
            pass2 = true;
        }
        assertTrue(pass2);
        assertNull(store.getDocument(uri1));
        //test undo put that kicks another out of storage
        store.setMaxDocumentBytes(45);
        store.putDocument(new ByteArrayInputStream(this.txt1.getBytes()), this.uri1, DocumentStore.DocumentFormat.TXT);
        store.putDocument(new ByteArrayInputStream(this.txt2.getBytes()), this.uri2, DocumentStore.DocumentFormat.TXT);
        assertNotNull(Files.readAllBytes(Paths.get(fileNameUri1 + ".json")));
        store.undo();
        boolean pass3 = false;
        try {
            Files.readAllBytes(Paths.get(fileNameUri1 + ".json"));
        }catch (NoSuchFileException n){
            pass3 = true;
        }
        assertTrue(pass3);
        boolean pass4 = false;
        try {
            Files.readAllBytes(Paths.get(fileNameUri2 + ".json"));
        }catch (NoSuchFileException n){
            pass4 = true;
        }
        assertTrue(pass4);
        assertNotNull(store.getDocument(uri1));
        assertNull(store.getDocument(uri2));
        store.setMaxDocumentBytes(105);
        store.putDocument(new ByteArrayInputStream(this.txt2.getBytes()), this.uri2, DocumentStore.DocumentFormat.TXT);
        store.putDocument(new ByteArrayInputStream(this.txt3.getBytes()), this.uri3, DocumentStore.DocumentFormat.TXT);
        boolean pass5 = false;
        try {
            Files.readAllBytes(Paths.get(fileNameUri1 + ".json"));
        }catch (NoSuchFileException n){
            pass5 = true;
        }
        assertTrue(pass5);
        store.putDocument(new ByteArrayInputStream(this.txt5.getBytes()), this.uri2, DocumentStore.DocumentFormat.TXT);
        //currently there should be uri1 on disc and uri2 (w txt5) and uri3 txt3 in memory, but when undo
        //is called there should be regular uri1 2 and 3 in memory and nothing on disc
        assertNotNull(Files.readAllBytes(Paths.get(fileNameUri1 + ".json")));
        boolean pass6 = false;
        try {
            Files.readAllBytes(Paths.get(fileNameUri2 + ".json"));
        }catch (NoSuchFileException n){
            pass6 = true;
        }
        assertTrue(pass6);
        boolean pass7 = false;
        try {
            Files.readAllBytes(Paths.get(fileNameUri3 + ".json"));
        }catch (NoSuchFileException n){
            pass7 = true;
        }
        assertTrue(pass7);
        assertNotNull(store.getDocument(uri3));
        assertNotNull(store.getDocument(uri2));
        store.undo(uri2);
        boolean pass8 = false;
        try {
            Files.readAllBytes(Paths.get(fileNameUri2 + ".json"));
        }catch (NoSuchFileException n){
            pass8 = true;
        }
        assertTrue(pass8);
        boolean pass9 = false;
        try {
            Files.readAllBytes(Paths.get(fileNameUri3 + ".json"));
        }catch (NoSuchFileException n){
            pass9 = true;
        }
        assertTrue(pass9);
        boolean pass10 = false;
        try {
            Files.readAllBytes(Paths.get(fileNameUri1 + ".json"));
        }catch (NoSuchFileException n){
            pass10 = true;
        }
        assertTrue(pass10);
        assertNotNull(store.getDocument(uri3));
        assertNotNull(store.getDocument(uri2));
        assertNotNull(store.getDocument(uri1));


    }

    @Test
    public void testStage5Bytes() throws IOException {
        DocumentStore store = new DocumentStoreImpl(null);
        System.out.println("txt1 bytes = " + txt1.getBytes().length);
        System.out.println("txt2 bytes = " + txt2.getBytes().length);
        System.out.println("txt3 bytes = " + txt3.getBytes().length);
        System.out.println("txt4 bytes = " + txt4.getBytes().length);
        System.out.println("txt5 bytes = " + txt5.getBytes().length);
        System.out.println("txt6 bytes = " + txt6.getBytes().length);
        store.setMaxDocumentCount(6);
        store.putDocument(new ByteArrayInputStream(this.txt1.getBytes()), this.uri1, DocumentStore.DocumentFormat.TXT);
        store.putDocument(new ByteArrayInputStream(this.txt2.getBytes()), this.uri2, DocumentStore.DocumentFormat.TXT);
        store.putDocument(new ByteArrayInputStream(this.txt3.getBytes()), this.uri3, DocumentStore.DocumentFormat.TXT);
        store.putDocument(new ByteArrayInputStream(this.txt4.getBytes()), this.uri4, DocumentStore.DocumentFormat.TXT);
        store.putDocument(new ByteArrayInputStream(this.txt5.getBytes()), this.uri5, DocumentStore.DocumentFormat.TXT);
        store.setMaxDocumentBytes(533);
        store.putDocument(new ByteArrayInputStream(this.txt6.getBytes()), this.uri6, DocumentStore.DocumentFormat.TXT);
        String fileNameUri1 = System.getProperty("user.dir") + uri1.getRawSchemeSpecificPart();
        assertNotNull(Files.readAllBytes(Paths.get(fileNameUri1 + ".json")));
        String fileNameUri2 = System.getProperty("user.dir") + uri2.getRawSchemeSpecificPart();
        assertNotNull(Files.readAllBytes(Paths.get(fileNameUri2 + ".json")));
        String fileNameUri3 = System.getProperty("user.dir") + uri3.getRawSchemeSpecificPart();
        assertNotNull(Files.readAllBytes(Paths.get(fileNameUri3 + ".json")));
        String fileNameUri4 = System.getProperty("user.dir") + uri4.getRawSchemeSpecificPart();
        assertNotNull(Files.readAllBytes(Paths.get(fileNameUri4 + ".json")));
        String fileNameUri5 = System.getProperty("user.dir") + uri5.getRawSchemeSpecificPart();
        assertNotNull(Files.readAllBytes(Paths.get(fileNameUri5 + ".json")));
        String fileNameUri6 = System.getProperty("user.dir") + uri6.getRawSchemeSpecificPart();
        boolean pass = false;
        try {
            Files.readAllBytes(Paths.get(fileNameUri6 + ".json"));
        }catch (NoSuchFileException n){
            pass = true;
        }
        assertTrue(pass);
        store.deleteDocument(uri6);
        boolean pass2 = false;
        try {
            Files.readAllBytes(Paths.get(fileNameUri6 + ".json"));
        }catch (NoSuchFileException n){
            pass2 = true;
        }
        assertTrue(pass2);
        store.deleteAllWithPrefix("p");
        boolean pass3 = false;
        try {
            Files.readAllBytes(Paths.get(fileNameUri1 + ".json"));
        }catch (NoSuchFileException n){
            pass3 = true;
        }
        assertTrue(pass3);
        store.deleteAllWithPrefix("d");
        boolean pass4 = false;
        try {
            Files.readAllBytes(Paths.get(fileNameUri4 + ".json"));
        }catch (NoSuchFileException n){
            pass4 = true;
        }
        assertTrue(pass4);
        store.setMaxDocumentBytes(80);
        store.putDocument(new ByteArrayInputStream(this.txt1.getBytes()), this.uri1, DocumentStore.DocumentFormat.TXT);
        store.putDocument(new ByteArrayInputStream(this.txt2.getBytes()), this.uri2, DocumentStore.DocumentFormat.TXT);
        store.putDocument(new ByteArrayInputStream(this.txt4.getBytes()), this.uri4, DocumentStore.DocumentFormat.TXT);
        assertNotNull(Files.readAllBytes(Paths.get(fileNameUri1 + ".json")));
        boolean pass5 = false;
        try {
            Files.readAllBytes(Paths.get(fileNameUri4 + ".json"));
        }catch (NoSuchFileException n){
            pass5 = true;
        }
        assertTrue(pass5);
        assertNotNull(store.getDocument(uri2));
        assertNotNull(store.getDocument(uri4));
        store.deleteAllWithPrefix("pIZz");
        store.deleteDocument(uri4);
        assertNull(store.getDocument(uri2));
        assertNull(store.getDocument(uri1));
        assertNull(store.getDocument(uri4));
        boolean pass6 = false;
        try {
            Files.readAllBytes(Paths.get(fileNameUri2 + ".json"));
        }catch (NoSuchFileException n){
            pass6 = true;
        }
        assertTrue(pass6);
        boolean pass7 = false;
        try {
            Files.readAllBytes(Paths.get(fileNameUri4 + ".json"));
        }catch (NoSuchFileException n){
            pass7 = true;
        }
        assertTrue(pass7);
        boolean pass8 = false;
        try {
            Files.readAllBytes(Paths.get(fileNameUri1 + ".json"));
        }catch (NoSuchFileException n){
            pass8 = true;
        }
        assertTrue(pass8);



    }
    @Test
    public void testStage5Basic2() throws IOException {
        DocumentStore store = new DocumentStoreImpl(null);
        store.setMaxDocumentCount(1);
        store.putDocument(new ByteArrayInputStream(this.txt2.getBytes()), this.uri2, DocumentStore.DocumentFormat.TXT);
        store.putDocument(new ByteArrayInputStream(this.txt3.getBytes()), this.uri3, DocumentStore.DocumentFormat.TXT);
        String fileNameUri2 = System.getProperty("user.dir") + uri2.getRawSchemeSpecificPart();
        assertNotNull(Files.readAllBytes(Paths.get(fileNameUri2 + ".json")));
        assertEquals(2,store.deleteAllWithPrefix("P").size());
        boolean pass = false;
        try {
            Files.readAllBytes(Paths.get(fileNameUri2 + ".json"));
        }catch (NoSuchFileException n){
            pass = true;
        }
        assertTrue(pass);
        store.setMaxDocumentCount(3);
        store.putDocument(new ByteArrayInputStream(this.txt1.getBytes()),this.uri1, DocumentStore.DocumentFormat.TXT);
        store.putDocument(new ByteArrayInputStream(this.txt3.getBytes()), this.uri3, DocumentStore.DocumentFormat.TXT);
        store.putDocument(new ByteArrayInputStream(this.txt2.getBytes()),this.uri2, DocumentStore.DocumentFormat.TXT);
        store.putDocument(new ByteArrayInputStream(this.txt4.getBytes()),this.uri4, DocumentStore.DocumentFormat.TXT);
        store.putDocument(new ByteArrayInputStream(this.txt5.getBytes()),this.uri5, DocumentStore.DocumentFormat.TXT);
        String fileNameUri1 = System.getProperty("user.dir") + uri1.getRawSchemeSpecificPart();
        assertNotNull(Files.readAllBytes(Paths.get(fileNameUri1 + ".json")));
       // assertNotNull(Files.readAllBytes(Paths.get(fileNameUri2 + ".json")));
        String fileNameUri3 = System.getProperty("user.dir") + uri3.getRawSchemeSpecificPart();
        assertNotNull(Files.readAllBytes(Paths.get(fileNameUri3 + ".json")));
        assertEquals(3,store.searchByPrefix("P").size());
        String fileNameUri4 = System.getProperty("user.dir") + uri4.getRawSchemeSpecificPart();
        assertNotNull(Files.readAllBytes(Paths.get(fileNameUri4 + ".json")));
        String fileNameUri5 = System.getProperty("user.dir") + uri5.getRawSchemeSpecificPart();
        assertNotNull(Files.readAllBytes(Paths.get(fileNameUri5 + ".json")));
        boolean pass2 = false;
        try {
            Files.readAllBytes(Paths.get(fileNameUri1 + ".json"));
        }catch (NoSuchFileException n){
            pass2 = true;
        }
        assertTrue(pass2);
        boolean pass3 = false;
        try {
            Files.readAllBytes(Paths.get(fileNameUri2 + ".json"));
        }catch (NoSuchFileException n){
            pass3 = true;
        }
        assertTrue(pass3);
        boolean pass4 = false;
        try {
            Files.readAllBytes(Paths.get(fileNameUri3 + ".json"));
        }catch (NoSuchFileException n){
            pass4 = true;
        }
        assertTrue(pass4);
        assertEquals(3,store.deleteAllWithPrefix("P").size());
        assertNotNull(Files.readAllBytes(Paths.get(fileNameUri4 + ".json")));
        assertNotNull(Files.readAllBytes(Paths.get(fileNameUri5 + ".json")));
//        boolean pass5 = false;
//        try {
//            Files.readAllBytes(Paths.get(fileNameUri5 + ".json"));
//        }catch (NoSuchFileException n){
//            pass5 = true;
//        }
//        assertTrue(pass5);
//        boolean pass6 = false;
//        try {
//            Files.readAllBytes(Paths.get(fileNameUri4 + ".json"));
//        }catch (NoSuchFileException n){
//            pass6 = true;
//        }
//        assertTrue(pass6);
        boolean pass7 = false;
        try {
            Files.readAllBytes(Paths.get(fileNameUri1 + ".json"));
        }catch (NoSuchFileException n){
            pass7 = true;
        }
        assertTrue(pass7);



//        private String txt1 = "Apple Apple Pizza Fish Pie Pizza Apple";
//        private String txt2 = "Pizza Pizza Pizza Pizza Pizza";
//        private String txt3 = "Penguin Park Piccalo Pants Pain Possum";
//        private String txt4 = "This is the 4th doc, and it's here to stay!";
//        private String txt5 = "Hello I am the 5th doc, and I'm here to stay!";
//        private String txt6 = "Penguin Park Piccalo Pants Pain Possum and I'm here to STAY! awawawawawawawawawawawawawawawawawawawawawawaewaeaeaeaesrewarewsewrarewarewserwsercrcxvftvhgdbkjdbnwucbnuhfruynbwxuyfizwmbwfyxnuybnregh byugcnbgyxfrebygxbfrmuyzbvuybmfrbubfvubvexuybmzybmfubvmugbxmnxuvbrvnubnruzbuxyrewyhrfmuyzhbnuycbyftxbjnuynbxchgwybnxnbergrhghyrhxfytreuhxyhuxgccyhuebcgyhbwbfgyhegfddyhfgyehdfgyehbfgdhefdgyehedfgdhbvgyhbggyhbgvyhdbfgyhuefcgyhubfgeyehudxcgyhxucnhyunbxcghnbcbghjbcghbcghcfghbcghbcbghnbcgbhbcghnbcfghnbcfghbfcghnbcfghbcfghbghbcfghbgcfgdhbcf";

    }
    @Test
    public void testStage5Basic() throws IOException {
        DocumentStore store = new DocumentStoreImpl(null);
        store.setMaxDocumentCount(2);
        store.putDocument(new ByteArrayInputStream(this.txt1.getBytes()), this.uri1, DocumentStore.DocumentFormat.TXT);
        store.putDocument(new ByteArrayInputStream(this.txt2.getBytes()), this.uri2, DocumentStore.DocumentFormat.TXT);
        store.putDocument(new ByteArrayInputStream(this.txt3.getBytes()), this.uri3, DocumentStore.DocumentFormat.TXT);
        String fileNameUri1 = System.getProperty("user.dir") + uri1.getRawSchemeSpecificPart();
        assertNotNull(Files.readAllBytes(Paths.get(fileNameUri1 + ".json")));
        assertEquals(1,store.searchByPrefix("Park").size());
        assertEquals(1,store.searchByPrefix("Apple").size());
        assertEquals(1,store.searchByPrefix("Apple").size());
        store.getDocument(uri1);
        boolean pass = false;
        try {
            Files.readAllBytes(Paths.get(fileNameUri1 + ".json"));
        }catch (NoSuchFileException n){
            pass = true;
        }
        assertTrue(pass);
        assertEquals(1,store.searchByPrefix("Apple").size());
        store.setMaxDocumentCount(1);
        assertEquals(1,store.searchByPrefix("Apple").size());
        assertEquals(2,store.searchByPrefix("Pizza").size());
        assertTrue(store.deleteDocument(uri2));
        assertEquals(1,store.searchByPrefix("Pizza").size());


//        private String txt1 = "Apple Apple Pizza Fish Pie Pizza Apple";
//        private String txt2 = "Pizza Pizza Pizza Pizza Pizza";
//        private URI uri3;
//        private String txt3 = "Penguin Park Piccalo Pants Pain Possum";

//        assertNull(store.getDocument(uri1));
//        assertNotNull(store.getDocument(uri2));
//        assertNotNull(store.getDocument(uri3));
//        store.setMaxDocumentCount(1);
//        store.putDocument(new ByteArrayInputStream(this.txt1.getBytes()), this.uri1, DocumentStore.DocumentFormat.TXT);
//        assertNotNull(store.getDocument(uri1));
//        assertNull(store.getDocument(uri2));
//        assertNull(store.getDocument(uri3));
//        store.setMaxDocumentCount(3);
//        System.out.println("txt1 bytes = " + txt1.getBytes().length);
//        System.out.println("txt2 bytes = " + txt2.getBytes().length);
//        System.out.println("txt3 bytes = " + txt3.getBytes().length);
//        store.setMaxDocumentBytes(67); //i.e. should only be able to fit two docs
//        store.putDocument(new ByteArrayInputStream(this.txt2.getBytes()), this.uri2, DocumentStore.DocumentFormat.TXT);
//        store.putDocument(new ByteArrayInputStream(this.txt3.getBytes()), this.uri3, DocumentStore.DocumentFormat.TXT);
//        assertNull(store.getDocument(uri1));
//        assertNotNull(store.getDocument(uri2));
//        assertNotNull(store.getDocument(uri3));
//        store.getDocument(uri2); //this updates doc2's time, so doesn't get kicked out
//        store.putDocument(new ByteArrayInputStream(this.txt1.getBytes()), this.uri1, DocumentStore.DocumentFormat.TXT);
//        assertNotNull(store.getDocument(uri1));
//        assertNotNull(store.getDocument(uri2));
//        assertNull(store.getDocument(uri3));
//        store.setMaxDocumentBytes(38);
//        store.putDocument(new ByteArrayInputStream(this.txt3.getBytes()), this.uri3, DocumentStore.DocumentFormat.TXT);
//        assertNull(store.getDocument(uri1));
//        assertNull(store.getDocument(uri2));
//        assertNotNull(store.getDocument(uri3));
//        store.setMaxDocumentBytes(105);
//        store.setMaxDocumentCount(2);
//        store.putDocument(new ByteArrayInputStream(this.txt1.getBytes()), this.uri1, DocumentStore.DocumentFormat.TXT);
//        store.putDocument(new ByteArrayInputStream(this.txt2.getBytes()), this.uri2, DocumentStore.DocumentFormat.TXT);
//        assertNotNull(store.getDocument(uri1));
//        assertNotNull(store.getDocument(uri2));
//        assertNull(store.getDocument(uri3));
    }

    //my Stage 4 Tests
    //variables to hold possible values for doc1
    private URI uri4;

    { try {
        uri4 = new URI("http://edu.yu.cs/com1320/project/doc4");
    } catch (URISyntaxException e) {
        e.printStackTrace(); }
    }
    //uri1 = new URI("http://edu.yu.cs/com1320/project/doc1");
    private String txt4 = "This is the 4th doc, and it's here to stay!";
    //variables to hold possible values for doc2
    private URI uri5;
    { try {
        uri5 = new URI("http://edu.yu.cs/com1320/project/doc5");
    } catch (URISyntaxException e) {
        e.printStackTrace(); }
    }
    private String txt5 = "Hello I am the 5th doc, and I'm here to stay!";
    private URI uri6;
    { try {
        uri6 = new URI("http://edu.yu.cs/com1320/project/doc6");
    } catch (URISyntaxException e) {
        e.printStackTrace();
    }
    }
    private String txt6 = "Penguin Park Piccalo Pants Pain Possum and I'm here to STAY! awawawawawawawawawawawawawawawawawawawawawawaewaeaeaeaesrewarewsewrarewarewserwsercrcxvftvhgdbkjdbnwucbnuhfruynbwxuyfizwmbwfyxnuybnregh byugcnbgyxfrebygxbfrmuyzbvuybmfrbubfvubvexuybmzybmfubvmugbxmnxuvbrvnubnruzbuxyrewyhrfmuyzhbnuycbyftxbjnuynbxchgwybnxnbergrhghyrhxfytreuhxyhuxgccyhuebcgyhbwbfgyhegfddyhfgyehdfgyehbfgdhefdgyehedfgdhbvgyhbggyhbgvyhdbfgyhuefcgyhubfgeyehudxcgyhxucnhyunbxcghnbcbghjbcghbcghcfghbcghbcbghnbcgbhbcghnbcfghnbcfghbfcghnbcfghbcfghbghbcfghbgcfgdhbcf";
//    @Test
//    public void testHeap() throws IOException {
//        //whole test turns out to be unneccesary bc of piazza 364
//        System.out.println("txt1 bytes = " + txt1.getBytes().length);
//        System.out.println("txt2 bytes = " + txt2.getBytes().length);
//        System.out.println("txt3 bytes = " + txt3.getBytes().length);
//        System.out.println("txt4 bytes = " + txt4.getBytes().length);
//        System.out.println("txt5 bytes = " + txt5.getBytes().length);
//        System.out.println("txt6 bytes = " + txt6.getBytes().length);
//        MinHeapImpl<Document> heap = new MinHeapImpl<>();
//        DocumentImpl doc1 = new DocumentImpl(this.uri1,this.txt1);
//        heap.insert(doc1);
//        DocumentImpl doc2 = new DocumentImpl(this.uri2,this.txt2);
//        heap.insert(doc2);
//        DocumentImpl doc3 = new DocumentImpl(this.uri3,this.txt3);
//        heap.insert(doc3);
//        DocumentImpl doc4 = new DocumentImpl(this.uri4,this.txt4);
//        heap.insert(doc4);
//        DocumentImpl doc5 = new DocumentImpl(this.uri5,this.txt5);
//        heap.insert(doc5);
//        DocumentImpl doc6 = new DocumentImpl(this.uri6,this.txt6);
//        heap.insert(doc6);
//        heap.getArrayIndex
//        //Assertheap.remove();
////        DocumentStore store = new DocumentStoreImpl();
////        store.putDocument(new ByteArrayInputStream(this.txt1.getBytes()),this.uri1, DocumentStore.DocumentFormat.TXT);
////        store.putDocument(new ByteArrayInputStream(this.txt2.getBytes()),this.uri1, DocumentStore.DocumentFormat.TXT);
////        store.putDocument(new ByteArrayInputStream(this.txt3.getBytes()),this.uri1, DocumentStore.DocumentFormat.TXT);
////        store.setMaxDocumentCount(1);
////        store.putDocument(new ByteArrayInputStream(this.txt4.getBytes()),this.uri4, DocumentStore.DocumentFormat.TXT);
//
//    }

//    @Test
//    public void testThatProperlyManageStorageWithUndo() throws IOException {
//        System.out.println("txt1 bytes = " + txt1.getBytes().length);
//        System.out.println("txt2 bytes = " + txt2.getBytes().length);
//        System.out.println("txt3 bytes = " + txt3.getBytes().length);
//        System.out.println("txt4 bytes = " + txt4.getBytes().length);
//        System.out.println("txt5 bytes = " + txt5.getBytes().length);
//        System.out.println("txt6 bytes = " + txt6.getBytes().length);
//        DocumentStore store = new DocumentStoreImpl(null);
//        store.putDocument(new ByteArrayInputStream(this.txt1.getBytes()),this.uri1, DocumentStore.DocumentFormat.TXT);
//        store.putDocument(new ByteArrayInputStream(this.txt2.getBytes()),this.uri2, DocumentStore.DocumentFormat.TXT);
//        store.putDocument(new ByteArrayInputStream(this.txt3.getBytes()),this.uri3, DocumentStore.DocumentFormat.TXT);
//        store.putDocument(new ByteArrayInputStream(this.txt4.getBytes()),this.uri2, DocumentStore.DocumentFormat.TXT);
//        store.putDocument(new ByteArrayInputStream(this.txt5.getBytes()),this.uri5, DocumentStore.DocumentFormat.TXT);
//        store.undo(uri2);
//        boolean pretest = true;
//        try {
//            store.undo(uri1);
//        } catch (IllegalStateException e) {
//            pretest = false;
//        }
//        assertTrue(pretest);
//        store.setMaxDocumentCount(2);
//        store.putDocument(new ByteArrayInputStream(this.txt6.getBytes()),this.uri6, DocumentStore.DocumentFormat.TXT);
//        assertNull(store.getDocument(uri1));
//        assertEquals(txt2,store.getDocument(uri2).getDocumentTxt());
//        assertNull(store.getDocument(uri3));
//        assertNull(store.getDocument(uri5));
//        assertNotNull(store.getDocument(uri6));
//        boolean test = false;
//        try {
//            store.undo(uri1);
//        } catch (IllegalStateException e) {
//            test = true;
//        }
//        assertTrue(test);
//        store.deleteDocument(uri2);
//        store.getDocument(uri6);
//        store.undo(uri2);
//        store.putDocument(new ByteArrayInputStream(this.txt1.getBytes()),this.uri1, DocumentStore.DocumentFormat.TXT);
//        assertNotNull(store.getDocument(uri1));
//        assertEquals(txt2,store.getDocument(uri2).getDocumentTxt());
//        assertNull(store.getDocument(uri3));
//        assertNull(store.getDocument(uri5));
//        assertNull(store.getDocument(uri6));
//        store.setMaxDocumentBytes(126);
//        store.setMaxDocumentCount(4);
//        store.putDocument(new ByteArrayInputStream(this.txt4.getBytes()),this.uri4, DocumentStore.DocumentFormat.TXT);
//        store.deleteAll("pizza"); //1 and 2, but not 4
//        store.getDocument(uri4);
//        store.undo();
//        store.putDocument(new ByteArrayInputStream(this.txt5.getBytes()),this.uri5, DocumentStore.DocumentFormat.TXT);
//        assertNotNull(store.getDocument(uri1));
//        assertNotNull(store.getDocument(uri2));
//        assertNull(store.getDocument(uri3));
//        assertNull(store.getDocument(uri4));
//        assertNotNull(store.getDocument(uri5));
//        assertNull(store.getDocument(uri6));
//        store.setMaxDocumentBytes(533);
//        boolean pretest2 = true;
//        try {
//            store.undo(uri1);
//        } catch (IllegalStateException e) {
//            pretest2 = false;
//        }
//        assertTrue(pretest2);
//        store.putDocument(new ByteArrayInputStream(this.txt6.getBytes()),this.uri6, DocumentStore.DocumentFormat.TXT);
//        boolean test2 = false;
//        try {
//            store.undo(uri1);
//        } catch (IllegalStateException e) {
//            test2 = true;
//        }
//        assertTrue(test2);
//        store.undo();
//        assertNull(store.getDocument(uri1));
//        assertNull(store.getDocument(uri2));
//        assertNull(store.getDocument(uri3));
//        assertNull(store.getDocument(uri4));
//        assertNull(store.getDocument(uri5));
//        assertNull(store.getDocument(uri6));
//    }
//    @Test
//    public void testThatProperlyManageStorageAdvanced() throws IOException {
//        System.out.println("txt1 bytes = " + txt1.getBytes().length);
//        System.out.println("txt2 bytes = " + txt2.getBytes().length);
//        System.out.println("txt3 bytes = " + txt3.getBytes().length);
//        System.out.println("txt4 bytes = " + txt4.getBytes().length);
//        System.out.println("txt5 bytes = " + txt5.getBytes().length);
//        System.out.println("txt6 bytes = " + txt6.getBytes().length);
//        String fileNameUri1 = System.getProperty("user.dir") + uri1.getRawSchemeSpecificPart();
//       // assertNotNull(Files.readAllBytes(Paths.get(fileNameUri1 + ".json")));
//        String fileNameUri2 = System.getProperty("user.dir") + uri2.getRawSchemeSpecificPart();
//      //  assertNotNull(Files.readAllBytes(Paths.get(fileNameUri2 + ".json")));
//        String fileNameUri3 = System.getProperty("user.dir") + uri3.getRawSchemeSpecificPart();
//      //  assertNotNull(Files.readAllBytes(Paths.get(fileNameUri3 + ".json")));
//        String fileNameUri4 = System.getProperty("user.dir") + uri4.getRawSchemeSpecificPart();
//      //  assertNotNull(Files.readAllBytes(Paths.get(fileNameUri4 + ".json")));
//        String fileNameUri5 = System.getProperty("user.dir") + uri5.getRawSchemeSpecificPart();
//       // assertNotNull(Files.readAllBytes(Paths.get(fileNameUri5 + ".json")));
//        String fileNameUri6 = System.getProperty("user.dir") + uri6.getRawSchemeSpecificPart();
//        DocumentStore store = new DocumentStoreImpl(null);
//        store.putDocument(new ByteArrayInputStream(this.txt1.getBytes()),this.uri1, DocumentStore.DocumentFormat.TXT);
//        store.putDocument(new ByteArrayInputStream(this.txt2.getBytes()),this.uri2, DocumentStore.DocumentFormat.TXT);
//        store.putDocument(new ByteArrayInputStream(this.txt3.getBytes()),this.uri3, DocumentStore.DocumentFormat.TXT);
//        assertEquals(1, store.search("PiE").size()); //this returns doc1
//        store.setMaxDocumentCount(3);
//        store.putDocument(new ByteArrayInputStream(this.txt4.getBytes()),this.uri4, DocumentStore.DocumentFormat.TXT);
//        assertNotNull(store.getDocument(uri1));
//        assertNotNull(Files.readAllBytes(Paths.get(fileNameUri2 + ".json")));
//        assertNotNull(store.getDocument(uri3));
//        assertNotNull(store.getDocument(uri4));
//        store.setMaxDocumentCount(5);
//        assertEquals(3, store.searchByPrefix("p").size()); //every doc but 4th (and 1st isn't in now)
//        store.putDocument(new ByteArrayInputStream(this.txt2.getBytes()),this.uri2, DocumentStore.DocumentFormat.TXT);
//        store.putDocument(new ByteArrayInputStream(this.txt5.getBytes()),this.uri5, DocumentStore.DocumentFormat.TXT);
//        store.putDocument(new ByteArrayInputStream(this.txt6.getBytes()),this.uri6, DocumentStore.DocumentFormat.TXT);
//        assertNotNull(store.getDocument(uri1));
//        assertNotNull(store.getDocument(uri2));
//        assertNotNull(store.getDocument(uri3));
//        assertNotNull(Files.readAllBytes(Paths.get(fileNameUri4 + ".json")));
//        assertNotNull(store.getDocument(uri5));
//        assertNotNull(store.getDocument(uri6));
//        store.deleteDocument(uri6);
//        store.putDocument(new ByteArrayInputStream(this.txt4.getBytes()),this.uri4, DocumentStore.DocumentFormat.TXT);
//        store.setMaxDocumentBytes(533);
//        store.putDocument(new ByteArrayInputStream(this.txt6.getBytes()),this.uri6, DocumentStore.DocumentFormat.TXT);
//        assertNotNull(Files.readAllBytes(Paths.get(fileNameUri1 + ".json")));
//        assertNotNull(Files.readAllBytes(Paths.get(fileNameUri2 + ".json")));
//        assertNotNull(Files.readAllBytes(Paths.get(fileNameUri3 + ".json")));
//        assertNotNull(Files.readAllBytes(Paths.get(fileNameUri4 + ".json")));
//        assertNotNull(Files.readAllBytes(Paths.get(fileNameUri5 + ".json")));
//        assertNotNull(store.getDocument(uri6));
//        store.deleteAllWithPrefix("p");
//        store.deleteAllWithPrefix("t"); //interesting, only time that tested this method on a null trie, and actually had n error, so i fixed
//        assertNull(store.getDocument(uri1));
//        assertNull(store.getDocument(uri2));
//        assertNull(store.getDocument(uri3));
//        assertNull(store.getDocument(uri4));
//        assertNull(store.getDocument(uri5));
//        assertNull(store.getDocument(uri6));




//        assertEquals(0, store.searchByPrefix("x").size());
//        assertEquals(3, store.searchByPrefix("pi").size());
//        assertEquals(5, store.search("PiZza").get(0).wordCount("pizza"));
//        assertEquals(6, store.searchByPrefix("p").get(0).getWords().size());
 //   }
//
//    @Test
//    public void testThatProperlyManageStorageBasic() throws IOException {
//        DocumentStore store = new DocumentStoreImpl(null);
//        store.setMaxDocumentCount(2);
//        store.putDocument(new ByteArrayInputStream(this.txt1.getBytes()), this.uri1, DocumentStore.DocumentFormat.TXT);
//        store.putDocument(new ByteArrayInputStream(this.txt2.getBytes()), this.uri2, DocumentStore.DocumentFormat.TXT);
//        store.putDocument(new ByteArrayInputStream(this.txt3.getBytes()), this.uri3, DocumentStore.DocumentFormat.TXT);
//        assertNull(store.getDocument(uri1));
//        assertNotNull(store.getDocument(uri2));
//        assertNotNull(store.getDocument(uri3));
//        store.setMaxDocumentCount(1);
//        store.putDocument(new ByteArrayInputStream(this.txt1.getBytes()), this.uri1, DocumentStore.DocumentFormat.TXT);
//        assertNotNull(store.getDocument(uri1));
//        assertNull(store.getDocument(uri2));
//        assertNull(store.getDocument(uri3));
//        store.setMaxDocumentCount(3);
//        System.out.println("txt1 bytes = " + txt1.getBytes().length);
//        System.out.println("txt2 bytes = " + txt2.getBytes().length);
//        System.out.println("txt3 bytes = " + txt3.getBytes().length);
//        store.setMaxDocumentBytes(67); //i.e. should only be able to fit two docs
//        store.putDocument(new ByteArrayInputStream(this.txt2.getBytes()), this.uri2, DocumentStore.DocumentFormat.TXT);
//        store.putDocument(new ByteArrayInputStream(this.txt3.getBytes()), this.uri3, DocumentStore.DocumentFormat.TXT);
//        assertNull(store.getDocument(uri1));
//        assertNotNull(store.getDocument(uri2));
//        assertNotNull(store.getDocument(uri3));
//        store.getDocument(uri2); //this updates doc2's time, so doesn't get kicked out
//        store.putDocument(new ByteArrayInputStream(this.txt1.getBytes()), this.uri1, DocumentStore.DocumentFormat.TXT);
//        assertNotNull(store.getDocument(uri1));
//        assertNotNull(store.getDocument(uri2));
//        assertNull(store.getDocument(uri3));
//        store.setMaxDocumentBytes(38);
//        store.putDocument(new ByteArrayInputStream(this.txt3.getBytes()), this.uri3, DocumentStore.DocumentFormat.TXT);
//        assertNull(store.getDocument(uri1));
//        assertNull(store.getDocument(uri2));
//        assertNotNull(store.getDocument(uri3));
//        store.setMaxDocumentBytes(105);
//        store.setMaxDocumentCount(2);
//        store.putDocument(new ByteArrayInputStream(this.txt1.getBytes()), this.uri1, DocumentStore.DocumentFormat.TXT);
//        store.putDocument(new ByteArrayInputStream(this.txt2.getBytes()), this.uri2, DocumentStore.DocumentFormat.TXT);
//        assertNotNull(store.getDocument(uri1));
//        assertNotNull(store.getDocument(uri2));
//        assertNull(store.getDocument(uri3));
////        assertEquals(1, store.search("PiE").size());
////        assertEquals(3, store.searchByPrefix("p").size());
////        assertEquals(0, store.searchByPrefix("x").size());
////        assertEquals(3, store.searchByPrefix("pi").size());
////        assertEquals(5, store.search("PiZza").get(0).wordCount("pizza"));
////        assertEquals(6, store.searchByPrefix("p").get(0).getWords().size());
//    }
    //Stage 3 Tests

    @Test
    public void wordCountAndGetWordsTest() throws URISyntaxException {
        DocumentImpl txtDoc = new DocumentImpl(new URI("placeholder"), " The!se ARE? sOme   W@o%$rds with^ s**ymbols (m)ixed [in]. Hope    this test test passes!");
        assertEquals(0, txtDoc.wordCount("bundle"));
        assertEquals(1, txtDoc.wordCount("these"));
        assertEquals(1, txtDoc.wordCount("WORDS"));
        assertEquals(1, txtDoc.wordCount("S-Y-M-B-O-??-LS"));
        assertEquals(1, txtDoc.wordCount("p@A$$sse$s"));
        assertEquals(2, txtDoc.wordCount("tEst"));
        Set<String> words = txtDoc.getWords();
        assertEquals(12, words.size());
        assertTrue(words.contains("some"));

        DocumentImpl binaryDoc = new DocumentImpl(new URI("0110"), new byte[] {0,1,1,0});
        assertEquals(0, binaryDoc.wordCount("anythingYouPutHereShouldBeZero"));
        Set<String> words2 = binaryDoc.getWords();
        assertEquals(0, words2.size());
    }



    //variables to hold possible values for doc1
    private URI uri1;
    { try {
        uri1 = new URI("http://edu.yu.cs/com1320/project/doc1");
    } catch (URISyntaxException e) {
        e.printStackTrace(); }
    }
    //uri1 = new URI("http://edu.yu.cs/com1320/project/doc1");
    private String txt1 = "Apple Apple Pizza Fish Pie Pizza Apple";
    //variables to hold possible values for doc2
    private URI uri2;
    { try {
        uri2 = new URI("http://edu.yu.cs/com1320/project/doc2");
    } catch (URISyntaxException e) {
        e.printStackTrace(); }
    }
    private String txt2 = "Pizza Pizza Pizza Pizza Pizza";
    private URI uri3;
    { try {
        uri3 = new URI("http://edu.yu.cs/com1320/project/doc3");
    } catch (URISyntaxException e) {
        e.printStackTrace();
    }
    }
    private String txt3 = "Penguin Park Piccalo Pants Pain Possum";

    // @BeforeEach
    //public void init() throws Exception {
    //init possible values for doc1
    //this.uri1 = new URI("http://edu.yu.cs/com1320/project/doc1");
    // this.txt1 = "Apple Apple Pizza Fish Pie Pizza Apple";

    //init possible values for doc2
    //this.uri2 = new URI("http://edu.yu.cs/com1320/project/doc2");
    //this.txt2 = "Pizza Pizza Pizza Pizza Pizza";

    //init possible values for doc3
    //this.uri3 = new URI("http://edu.yu.cs/com1320/project/doc3");
    //this.txt3 = "Penguin Park Piccalo Pants Pain Possum";
    //}

    @Test
    public void basicSearchAndOrganizationTest() throws IOException {
        DocumentStore store = new DocumentStoreImpl(null);
        store.putDocument(new ByteArrayInputStream(this.txt1.getBytes()),this.uri1, DocumentStore.DocumentFormat.TXT);
        store.putDocument(new ByteArrayInputStream(this.txt2.getBytes()),this.uri2, DocumentStore.DocumentFormat.TXT);
        store.putDocument(new ByteArrayInputStream(this.txt3.getBytes()),this.uri3, DocumentStore.DocumentFormat.TXT);
        assertEquals(1, store.search("PiE").size());
        assertEquals(3, store.searchByPrefix("p").size());
        assertEquals(0, store.searchByPrefix("x").size());
        assertEquals(3, store.searchByPrefix("pi").size());
        assertEquals(5, store.search("PiZza").get(0).wordCount("pizza"));
        assertEquals(6, store.searchByPrefix("p").get(0).getWords().size());
    }
    @Test
    public void basicSearchDeleteTest() throws IOException {
        DocumentStore store = new DocumentStoreImpl(null);
        store.putDocument(new ByteArrayInputStream(this.txt1.getBytes()),this.uri1, DocumentStore.DocumentFormat.TXT);
        store.putDocument(new ByteArrayInputStream(this.txt2.getBytes()),this.uri2, DocumentStore.DocumentFormat.TXT);
        store.putDocument(new ByteArrayInputStream(this.txt3.getBytes()),this.uri3, DocumentStore.DocumentFormat.TXT);
        assertEquals(1, store.search("PiE").size());
        assertEquals(3, store.searchByPrefix("p").size());
        assertEquals(1, store.search("possum").size());
        store.deleteDocument(this.uri3);
        DocumentImpl doc1 = new DocumentImpl(this.uri1, this.txt1);
        DocumentImpl doc2 = new DocumentImpl(this.uri2, this.txt2);
        DocumentImpl doc3 = new DocumentImpl(this.uri3, this.txt3);
        for (char c = 'a'; c<='z'; c++) {
            List<Document> list = store.searchByPrefix(Character.toString(c));
            if (list.size()!=0) {
                assertNotEquals(doc3, list.get(0));
                if ((!list.get(0).equals(doc1))&&(!list.get(0).equals(doc2))) {
                    fail();
                }
            }
        }
        for (char c = '0'; c<='9'; c++) {
            List<Document> list = store.searchByPrefix(Character.toString(c));
            if (list.size()!=0) {
                assertNotEquals(doc3, list.get(0));
                if ((!list.get(0).equals(doc1))&&(!list.get(0).equals(doc2))) {
                    fail();
                }
            }
        }
        assertEquals(0, store.search("possum").size());
        assertEquals(2, store.search("pizza").size());
        store.deleteDocument(this.uri2);
        assertEquals(1, store.search("pizza").size());
    }
    @Test
    public void basicPutOverwriteTest() throws IOException {
        DocumentStore store = new DocumentStoreImpl(null);
        store.putDocument(new ByteArrayInputStream(this.txt1.getBytes()),this.uri1, DocumentStore.DocumentFormat.TXT);
        store.putDocument(new ByteArrayInputStream(this.txt2.getBytes()),this.uri2, DocumentStore.DocumentFormat.TXT);
        assertEquals(2, store.search("pizza").size());
        store.putDocument(new ByteArrayInputStream(this.txt3.getBytes()),this.uri2, DocumentStore.DocumentFormat.TXT);
        assertEquals(1, store.search("pizza").size());
    }
    @Test
    public void testDeleteAndDeleteAll() throws IOException {
        DocumentStore store = new DocumentStoreImpl(null);
        store.putDocument(new ByteArrayInputStream(this.txt1.getBytes()),this.uri1, DocumentStore.DocumentFormat.TXT);
        store.putDocument(new ByteArrayInputStream(this.txt2.getBytes()),this.uri2, DocumentStore.DocumentFormat.TXT);
        store.putDocument(new ByteArrayInputStream(this.txt3.getBytes()),this.uri3, DocumentStore.DocumentFormat.TXT);
        assertEquals(2, store.search("pizza").size());
        store.deleteAll("PiZZa");
        assertEquals(0, store.search("pizza").size());
        assertNull(store.getDocument(this.uri1));assertNull(store.getDocument(this.uri2));
        store.putDocument(new ByteArrayInputStream(this.txt2.getBytes()),this.uri2, DocumentStore.DocumentFormat.TXT);
        store.putDocument(new ByteArrayInputStream(this.txt1.getBytes()),this.uri1, DocumentStore.DocumentFormat.TXT);
        assertEquals(2, store.search("pizza").size());
        assertNotNull(store.getDocument(this.uri1));assertNotNull(store.getDocument(this.uri2));assertNotNull(store.getDocument(this.uri3));
        store.deleteAllWithPrefix("p");
        assertNull(store.getDocument(this.uri1));assertNull(store.getDocument(this.uri2));assertNull(store.getDocument(this.uri3));
    }
    @Test
    public void testUndoNoArgs() throws IOException {
        DocumentStore store = new DocumentStoreImpl(null);
        store.putDocument(new ByteArrayInputStream(this.txt1.getBytes()),this.uri1, DocumentStore.DocumentFormat.TXT);
        store.putDocument(new ByteArrayInputStream(this.txt2.getBytes()),this.uri2, DocumentStore.DocumentFormat.TXT);
        store.putDocument(new ByteArrayInputStream(this.txt3.getBytes()),this.uri3, DocumentStore.DocumentFormat.TXT);
        store.undo();
        assertEquals(null, store.getDocument(this.uri3));
        assertEquals(0, store.search("penguin").size());
        store.putDocument(new ByteArrayInputStream(this.txt3.getBytes()),this.uri3, DocumentStore.DocumentFormat.TXT);
        store.deleteAll("pizza");
        assertEquals(0, store.search("pizza").size());
        assertNull(store.getDocument(this.uri1));
        store.undo();
        assertEquals(2, store.search("pizza").size());
    }
    @Test
    public void testUndoWithArgs() throws IOException {
        DocumentStore store = new DocumentStoreImpl(null);
        store.putDocument(new ByteArrayInputStream(this.txt1.getBytes()),this.uri1, DocumentStore.DocumentFormat.TXT);
        store.putDocument(new ByteArrayInputStream(this.txt2.getBytes()),this.uri2, DocumentStore.DocumentFormat.TXT);
        store.putDocument(new ByteArrayInputStream(this.txt3.getBytes()),this.uri3, DocumentStore.DocumentFormat.TXT);
        assertEquals(1, store.search("apple").size());
        assertEquals(1, store.searchByPrefix("a").size());
        store.undo(this.uri1);
        assertEquals(0, store.search("apple").size());
        assertEquals(0, store.searchByPrefix("a").size());
    }
    @Test
    public void testUndoCommandSet() throws IOException {
        DocumentStore store = new DocumentStoreImpl(null);
        store.putDocument(new ByteArrayInputStream(this.txt1.getBytes()),this.uri1, DocumentStore.DocumentFormat.TXT);
        store.putDocument(new ByteArrayInputStream(this.txt2.getBytes()),this.uri2, DocumentStore.DocumentFormat.TXT);
        assertEquals(2, store.deleteAll("pizza").size());
        store.putDocument(new ByteArrayInputStream(this.txt3.getBytes()),this.uri3, DocumentStore.DocumentFormat.TXT);
        assertNotNull(store.getDocument(this.uri3));
        assertEquals(0, store.search("pizza").size());
        store.undo(uri1);
        assertEquals(1, store.search("pizza").size());
        assertEquals(4, store.search("pizza").get(0).getWords().size());
        store.undo(uri2);
        assertEquals(2, store.search("pizza").size());
        assertEquals(1, store.search("pizza").get(0).getWords().size());
        store.undo();
        assertNull(store.getDocument(this.uri3));
        assertEquals(0, store.search("penguin").size());
    }
    @Test
    public void testUndoCommandSet2() throws IOException {
        DocumentStore store = new DocumentStoreImpl(null);
        store.putDocument(new ByteArrayInputStream(this.txt1.getBytes()),this.uri1, DocumentStore.DocumentFormat.TXT);
        store.putDocument(new ByteArrayInputStream(this.txt2.getBytes()),this.uri2, DocumentStore.DocumentFormat.TXT);
        store.deleteAll("pizza");
        assertEquals(0, store.search("pizza").size());
        store.undo(uri2);
        assertEquals(1, store.search("pizza").size());
        store.undo(uri2);
        assertEquals(0, store.search("pizza").size());
        boolean test = false;
        try {
            store.undo(uri2);
        } catch (IllegalStateException e) {
            test = true;
        }
        assertTrue(test);
        assertEquals(0, store.search("pizza").size());
        store.undo(uri1);
        assertEquals(1, store.searchByPrefix("app").size());
        assertEquals(1, store.search("pizza").size());
    }
    @Test
    public void removeCommandSet() throws IOException {
        DocumentStore store = new DocumentStoreImpl(null);
        store.putDocument(new ByteArrayInputStream(this.txt1.getBytes()),this.uri1, DocumentStore.DocumentFormat.TXT);
        store.putDocument(new ByteArrayInputStream(this.txt2.getBytes()),this.uri2, DocumentStore.DocumentFormat.TXT);
        store.deleteAll("pizza");
        assertEquals(0, store.search("pizza").size());
        store.undo(uri2);
        assertEquals(1, store.search("pizza").size());
        store.undo(uri1);
        assertEquals(2, store.search("pizza").size());
        store.undo();
        assertNull(store.getDocument(uri2));
        assertNotNull(store.getDocument(uri1));
        assertEquals(1, store.search("pizza").size());
    }

    //Shmuel's tests

    URI[] uriArray2 = new URI[21];
    Document[] docArray2 = new Document[21];
    String[] stringArray2 = {"The blue parrot drove by the hitchhiking mongoose.",
            "She thought there'd be sufficient time if she hid her watch.",
            "Choosing to do nothing is still a choice, after all.",
            "He found the chocolate covered roaches quite tasty.",
            "The efficiency we have at removing trash has made creating trash more acceptable.",
            "Peanuts don't grow on trees, but cashews do.",
            "A song can make or ruin a person's day if they let it get to them.",
            "You bite up because of your lower jaw.",
            "He realized there had been several deaths on this road, but his concern rose when he saw the exact number.",
            "So long and thanks for the fish.",
            "Three years later, the coffin was still full of Jello.",
            "Weather is not trivial - it's especially important when you're standing in it.",
            "He walked into the basement with the horror movie from the night before playing in his head.",
            "He wondered if it could be called a beach if there was no sand.",
            "Jeanne wished she has chosen the red button.",
            "It's much more difficult to play tennis with a bowling ball than it is to bowl with a tennis ball.",
            "Pat ordered a ghost pepper pie.",
            "Everyone says they love nature until they realize how dangerous she can be.",
            "The memory we used to share is no longer coherent.",
            "My harvest will come Tiny valorous straw Among the millions Facing to the sun",
            "A dreamy-eyed child staring into night On a journey to storyteller's mind Whispers a wish speaks with the stars the words are silent in him"};

    @Test
    public void testUndo() {
        for (int i = 0; i < 7; i++) {
            uriArray2[i] = URI.create("www.google"+i+".com");
        }

        for (int i = 0; i < 7; i++) {
            docArray2[i] = new DocumentImpl(uriArray2[i], stringArray2[i]);
        }
        for (int i = 0; i < 7; i++) {
            docArray2[i+7] = new DocumentImpl(uriArray2[i], stringArray2[i+7].getBytes());
        }
        for (int i = 0; i < 7; i++) {
            docArray2[i+14] = new DocumentImpl(uriArray2[i], stringArray2[i+14]);
        }
        DocumentStore documentStore = new DocumentStoreImpl(null);
        try {
            int testa1 = documentStore.putDocument(new ByteArrayInputStream(stringArray2[0].getBytes()), uriArray2[0], DocumentStore.DocumentFormat.TXT);
            int testa2 = documentStore.putDocument(new ByteArrayInputStream(stringArray2[1].getBytes()), uriArray2[1], DocumentStore.DocumentFormat.TXT);
            int testa3 = documentStore.putDocument(new ByteArrayInputStream(stringArray2[2].getBytes()), uriArray2[2], DocumentStore.DocumentFormat.TXT);
            int testa4 = documentStore.putDocument(new ByteArrayInputStream(stringArray2[3].getBytes()), uriArray2[3], DocumentStore.DocumentFormat.TXT);
            int testa5 = documentStore.putDocument(new ByteArrayInputStream(stringArray2[4].getBytes()), uriArray2[4], DocumentStore.DocumentFormat.TXT);
            int testa6 = documentStore.putDocument(new ByteArrayInputStream(stringArray2[5].getBytes()), uriArray2[5], DocumentStore.DocumentFormat.TXT);
            int testa7 = documentStore.putDocument(new ByteArrayInputStream(stringArray2[6].getBytes()), uriArray2[6], DocumentStore.DocumentFormat.TXT);
            assertEquals(testa1, 0);
            assertEquals(testa2, 0);
            assertEquals(testa3, 0);
            assertEquals(testa4, 0);
            assertEquals(testa5, 0);
            assertEquals(testa6, 0);
            assertEquals(testa7, 0);
        } catch (java.io.IOException e) {
            fail();
        }

        documentStore.undo();

        assertEquals(docArray2[0], documentStore.getDocument(uriArray2[0]));
        assertEquals(docArray2[1], documentStore.getDocument(uriArray2[1]));
        assertEquals(docArray2[2], documentStore.getDocument(uriArray2[2]));
        assertEquals(docArray2[3], documentStore.getDocument(uriArray2[3]));
        assertEquals(docArray2[4], documentStore.getDocument(uriArray2[4]));
        assertEquals(docArray2[5], documentStore.getDocument(uriArray2[5]));
        assertEquals(null, documentStore.getDocument(uriArray2[6]));

        documentStore.undo(uriArray2[1]);

        try {
            int testb1 = documentStore.putDocument(new ByteArrayInputStream(stringArray2[7].getBytes()), uriArray2[0], DocumentStore.DocumentFormat.BINARY);
            int testb2 = documentStore.putDocument(new ByteArrayInputStream(stringArray2[8].getBytes()), uriArray2[1], DocumentStore.DocumentFormat.BINARY);
            int testb3 = documentStore.putDocument(new ByteArrayInputStream(stringArray2[9].getBytes()), uriArray2[2], DocumentStore.DocumentFormat.BINARY);
            int testb4 = documentStore.putDocument(new ByteArrayInputStream(stringArray2[10].getBytes()), uriArray2[3], DocumentStore.DocumentFormat.BINARY);
            int testb5 = documentStore.putDocument(new ByteArrayInputStream(stringArray2[11].getBytes()), uriArray2[4], DocumentStore.DocumentFormat.BINARY);
            int testb6 = documentStore.putDocument(new ByteArrayInputStream(stringArray2[12].getBytes()), uriArray2[5], DocumentStore.DocumentFormat.BINARY);
            int testb7 = documentStore.putDocument(new ByteArrayInputStream(stringArray2[13].getBytes()), uriArray2[6], DocumentStore.DocumentFormat.BINARY);
            assertEquals(testb1, docArray2[0].hashCode());
            assertEquals(testb2, 0);
            assertEquals(testb3, docArray2[2].hashCode());
            assertEquals(testb4, docArray2[3].hashCode());
            assertEquals(testb5, docArray2[4].hashCode());
            assertEquals(testb6, docArray2[5].hashCode());
            assertEquals(testb7, 0);
        } catch (java.io.IOException e) {
            fail();
        }

        documentStore.undo(uriArray2[1]);
        documentStore.undo(uriArray2[4]);
        documentStore.undo(uriArray2[5]);

        assertEquals(docArray2[7], documentStore.getDocument(uriArray2[0]));
        assertEquals(null, documentStore.getDocument(uriArray2[1]));
        assertEquals(docArray2[9], documentStore.getDocument(uriArray2[2]));
        assertEquals(docArray2[10], documentStore.getDocument(uriArray2[3]));
        assertEquals(docArray2[4], documentStore.getDocument(uriArray2[4]));
        assertEquals(docArray2[5], documentStore.getDocument(uriArray2[5]));
        assertEquals(docArray2[13], documentStore.getDocument(uriArray2[6]));

        try {
            int testc1 = documentStore.putDocument(new ByteArrayInputStream(stringArray2[14].getBytes()), uriArray2[0], DocumentStore.DocumentFormat.TXT);
            int testc2 = documentStore.putDocument(new ByteArrayInputStream(stringArray2[15].getBytes()), uriArray2[1], DocumentStore.DocumentFormat.TXT);
            int testc3 = documentStore.putDocument(new ByteArrayInputStream(stringArray2[16].getBytes()), uriArray2[2], DocumentStore.DocumentFormat.TXT);
            int testc4 = documentStore.putDocument(new ByteArrayInputStream(stringArray2[17].getBytes()), uriArray2[3], DocumentStore.DocumentFormat.TXT);
            int testc5 = documentStore.putDocument(new ByteArrayInputStream(stringArray2[18].getBytes()), uriArray2[4], DocumentStore.DocumentFormat.TXT);
            int testc6 = documentStore.putDocument(new ByteArrayInputStream(stringArray2[19].getBytes()), uriArray2[5], DocumentStore.DocumentFormat.TXT);
            int testc7 = documentStore.putDocument(new ByteArrayInputStream(stringArray2[20].getBytes()), uriArray2[6], DocumentStore.DocumentFormat.TXT);

            documentStore.undo(uriArray2[1]);
            documentStore.undo(uriArray2[6]);
            documentStore.undo();

            assertEquals(testc1, docArray2[7].hashCode());
            assertEquals(testc2, 0);
            assertEquals(testc3, docArray2[9].hashCode());
            assertEquals(testc4, docArray2[10].hashCode());
            assertEquals(testc5, docArray2[4].hashCode());
            assertEquals(testc6, docArray2[5].hashCode());
            assertEquals(testc7, docArray2[13].hashCode());
        } catch (java.io.IOException e) {
            fail();
        }

        assertEquals(docArray2[14], documentStore.getDocument(uriArray2[0]));
        assertEquals(null, documentStore.getDocument(uriArray2[1]));
        assertEquals(docArray2[16], documentStore.getDocument(uriArray2[2]));
        assertEquals(docArray2[17], documentStore.getDocument(uriArray2[3]));
        assertEquals(docArray2[18], documentStore.getDocument(uriArray2[4]));
        assertEquals(docArray2[5], documentStore.getDocument(uriArray2[5]));
        assertEquals(docArray2[13], documentStore.getDocument(uriArray2[6]));

        for (int i = 0; i < 7; i++) {
            documentStore.undo();
        }

        assertEquals(docArray2[7], documentStore.getDocument(uriArray2[0]));
        assertEquals(null, documentStore.getDocument(uriArray2[1]));
        assertEquals(docArray2[2], documentStore.getDocument(uriArray2[2]));
        assertEquals(docArray2[3], documentStore.getDocument(uriArray2[3]));
        assertEquals(docArray2[4], documentStore.getDocument(uriArray2[4]));
        assertEquals(docArray2[5], documentStore.getDocument(uriArray2[5]));
        assertEquals(null, documentStore.getDocument(uriArray2[6]));
    }


    //Jonathan Wenger's Tests (Stage 2)

    @Test
    public void testStackUndo() throws IOException, URISyntaxException {
        DocumentStoreImpl store = new DocumentStoreImpl(null);
        String str1 = "1"; byte[] array1 = str1.getBytes();
        ByteArrayInputStream stream1 = new ByteArrayInputStream(array1);
        ByteArrayInputStream stream11 = new ByteArrayInputStream(array1);
        URI uri1 = new URI("1");
        assertEquals(0, store.putDocument(stream1, uri1, DocumentStore.DocumentFormat.BINARY));
        Document doc = new DocumentImpl(uri1, stream11.readAllBytes());
        assertEquals(doc, store.getDocument(uri1));
        store.undo(); assertEquals(null, store.getDocument(uri1));
        boolean test = false;
        try {
            store.undo();
        } catch (IllegalStateException e) {
            test = true;
        }
        assertTrue(test);
    }
    @Test
    public void testStackUndoUri() throws IOException, URISyntaxException {
        DocumentStoreImpl store = new DocumentStoreImpl(null);
        String str1 = "1"; byte[] array1 = str1.getBytes();
        ByteArrayInputStream stream1 = new ByteArrayInputStream(array1); ByteArrayInputStream stream11 = new ByteArrayInputStream(array1);
        URI uri1 = new URI("1");
        assertEquals(0, store.putDocument(stream1, uri1, DocumentStore.DocumentFormat.BINARY));
        Document doc = new DocumentImpl(uri1, stream11.readAllBytes());
        assertEquals(doc, store.getDocument(uri1));
        String str2 = "2";
        byte[] array2 = str2.getBytes();
        ByteArrayInputStream stream2 = new ByteArrayInputStream(array2);
        ByteArrayInputStream stream22 = new ByteArrayInputStream(array2);
        URI uri2 = new URI("2");
        assertEquals(0, store.putDocument(stream2, uri2, DocumentStore.DocumentFormat.BINARY));
        Document doc2 = new DocumentImpl(uri2, stream22.readAllBytes());
        assertEquals(doc2, store.getDocument(uri2));
        store.undo(uri1);
        assertEquals(null, store.getDocument(uri1));
        assertEquals(doc2, store.getDocument(uri2));
    }
    @Test
    public void testStackUriPutOverwrite() throws IOException, URISyntaxException {
        DocumentStoreImpl store = new DocumentStoreImpl(null);
        String str1 = "1"; byte[] array1 = str1.getBytes();
        ByteArrayInputStream stream1 = new ByteArrayInputStream(array1); ByteArrayInputStream stream11 = new ByteArrayInputStream(array1);
        URI uri = new URI("1");
        assertEquals(0, store.putDocument(stream1, uri, DocumentStore.DocumentFormat.BINARY));
        Document doc = new DocumentImpl(uri, stream11.readAllBytes());
        assertEquals(doc, store.getDocument(uri));
        String str2 = "2"; byte[] array2 = str2.getBytes();
        ByteArrayInputStream stream2 = new ByteArrayInputStream(array2); ByteArrayInputStream stream22 = new ByteArrayInputStream(array2);
        assertEquals(doc.hashCode(), store.putDocument(stream2, uri, DocumentStore.DocumentFormat.BINARY));
        Document doc2 = new DocumentImpl(uri, stream22.readAllBytes());
        assertEquals(doc2, store.getDocument(uri));
        store.undo();
        assertNotEquals(doc2, store.getDocument(uri));
        assertEquals(doc, store.getDocument(uri));
        store.undo(); assertEquals(null, store.getDocument(uri));
    }
    @Test
    public void testStackUriDeleteOverwrite() throws IOException, URISyntaxException {
        DocumentStoreImpl store = new DocumentStoreImpl(null);
        String str1 = "1"; byte[] array1 = str1.getBytes();
        ByteArrayInputStream stream1 = new ByteArrayInputStream(array1); ByteArrayInputStream stream11 = new ByteArrayInputStream(array1);
        URI uri = new URI("1");
        assertEquals(0, store.putDocument(stream1, uri, DocumentStore.DocumentFormat.BINARY));
        Document doc = new DocumentImpl(uri, stream11.readAllBytes());
        assertEquals(doc, store.getDocument(uri));
        String str2 = "2"; byte[] array2 = str2.getBytes();
        ByteArrayInputStream stream2 = new ByteArrayInputStream(array2); ByteArrayInputStream stream22 = new ByteArrayInputStream(array2);
        assertEquals(doc.hashCode(), store.putDocument(stream2, uri, DocumentStore.DocumentFormat.BINARY));
        Document doc2 = new DocumentImpl(uri, stream22.readAllBytes());
        assertEquals(doc2, store.getDocument(uri));
        assertTrue(store.deleteDocument(uri)); assertEquals(null, store.getDocument(uri));
        String str3 = "3"; byte[] array3 = str3.getBytes();
        ByteArrayInputStream stream3 = new ByteArrayInputStream(array3); ByteArrayInputStream stream33 = new ByteArrayInputStream(array3);
        assertEquals(0, store.putDocument(stream3, uri, DocumentStore.DocumentFormat.BINARY));
        Document doc3 = new DocumentImpl(uri, stream33.readAllBytes());
        assertEquals(doc3, store.getDocument(uri));
        store.undo(uri); assertEquals(null, store.getDocument(uri));
        store.undo(uri); assertEquals(doc2, store.getDocument(uri));
        store.undo(uri); assertEquals(doc, store.getDocument(uri));
        store.undo(uri); assertEquals(null, store.getDocument(uri));
    }
    @Test
    public void testStackUriDeleteOverwriteNoParams() throws IOException, URISyntaxException {
        DocumentStoreImpl store = new DocumentStoreImpl(null);
        String str1 = "1"; byte[] array1 = str1.getBytes();
        ByteArrayInputStream stream1 = new ByteArrayInputStream(array1); ByteArrayInputStream stream11 = new ByteArrayInputStream(array1);
        URI uri = new URI("1");
        assertEquals(0, store.putDocument(stream1, uri, DocumentStore.DocumentFormat.BINARY));
        Document doc = new DocumentImpl(uri, stream11.readAllBytes());
        assertEquals(doc, store.getDocument(uri));
        String str2 = "2"; byte[] array2 = str2.getBytes();
        ByteArrayInputStream stream2 = new ByteArrayInputStream(array2); ByteArrayInputStream stream22 = new ByteArrayInputStream(array2);
        assertEquals(doc.hashCode(), store.putDocument(stream2, uri, DocumentStore.DocumentFormat.BINARY));
        Document doc2 = new DocumentImpl(uri, stream22.readAllBytes());
        assertEquals(doc2, store.getDocument(uri));
        assertTrue(store.deleteDocument(uri)); assertEquals(null, store.getDocument(uri));
        String str3 = "3"; byte[] array3 = str3.getBytes();
        ByteArrayInputStream stream3 = new ByteArrayInputStream(array3); ByteArrayInputStream stream33 = new ByteArrayInputStream(array3);
        assertEquals(0, store.putDocument(stream3, uri, DocumentStore.DocumentFormat.BINARY));
        Document doc3 = new DocumentImpl(uri, stream33.readAllBytes());
        assertEquals(doc3, store.getDocument(uri));
        store.undo(); assertEquals(null, store.getDocument(uri));
        store.undo(); assertEquals(doc2, store.getDocument(uri));
        store.undo(); assertEquals(doc, store.getDocument(uri));
        store.undo(); assertEquals(null, store.getDocument(uri));
    }
    @Test
    public void testStackUriDeleteOverwriteMultipleDocs() throws IOException, URISyntaxException {
        DocumentStoreImpl store = new DocumentStoreImpl(null);
        String str1 = "1";
        byte[] array1 = str1.getBytes();
        ByteArrayInputStream stream1 = new ByteArrayInputStream(array1);
        ByteArrayInputStream stream11 = new ByteArrayInputStream(array1);
        URI uri = new URI("1");
        assertEquals(0, store.putDocument(stream1, uri, DocumentStore.DocumentFormat.BINARY));
        Document doc = new DocumentImpl(uri, stream11.readAllBytes());
        assertEquals(doc, store.getDocument(uri));
        String str2 = "2";
        byte[] array2 = str2.getBytes();
        ByteArrayInputStream stream2 = new ByteArrayInputStream(array2);
        ByteArrayInputStream stream22 = new ByteArrayInputStream(array2);
        assertEquals(doc.hashCode(), store.putDocument(stream2, uri, DocumentStore.DocumentFormat.BINARY));
        Document doc2 = new DocumentImpl(uri, stream22.readAllBytes());
        assertEquals(doc2, store.getDocument(uri));
        assertTrue(store.deleteDocument(uri));
        assertEquals(null, store.getDocument(uri));
        String str3 = "3";
        byte[] array3 = str3.getBytes();
        URI uri2 = new URI("Hello");
        ByteArrayInputStream stream3 = new ByteArrayInputStream(array3);
        ByteArrayInputStream stream33 = new ByteArrayInputStream(array3);
        assertEquals(0, store.putDocument(stream3, uri2, DocumentStore.DocumentFormat.BINARY));
        Document doc3 = new DocumentImpl(uri2, stream33.readAllBytes());
        assertEquals(doc3, store.getDocument(uri2));
        store.undo(uri);
        assertEquals(doc3, store.getDocument(uri2));
        assertEquals(doc2, store.getDocument(uri));
        store.undo();
        assertEquals(null, store.getDocument(uri2));
        store.undo();
        assertEquals(doc, store.getDocument(uri));
        store.undo();
        assertEquals(null, store.getDocument(uri));
    }
    @Test
    public void undoTest2() throws IOException {
        DocumentStore documentStore = new DocumentStoreImpl(null);

        String string1 = "It was a dark and stormy night";
        String string2 = "It was the best of times, it was the worst of times";
        String string3 = "It was a bright cold day in April, and the clocks were striking thirteen";
        InputStream inputStream1 = new ByteArrayInputStream(string1.getBytes());
        InputStream inputStream2 = new ByteArrayInputStream(string2.getBytes());
        InputStream inputStream3 = new ByteArrayInputStream(string3.getBytes());
        URI uri1 = URI.create("www.wrinkleintime.com");

        documentStore.putDocument(inputStream1, uri1, DocumentStore.DocumentFormat.TXT);
        assertEquals(string1, documentStore.getDocument(uri1).getDocumentTxt());
        documentStore.putDocument(inputStream2, uri1, DocumentStore.DocumentFormat.TXT);
        assertEquals(string2, documentStore.getDocument(uri1).getDocumentTxt());
        documentStore.undo();
        assertEquals(string1, documentStore.getDocument(uri1).getDocumentTxt());
        documentStore.undo();
        assertEquals(null, documentStore.getDocument(uri1));

        documentStore.putDocument(inputStream3, uri1, DocumentStore.DocumentFormat.TXT);
        assertEquals(string3, documentStore.getDocument(uri1).getDocumentTxt());
        documentStore.deleteDocument(uri1);
        assertEquals(null, documentStore.getDocument(uri1));
        documentStore.undo();
        assertEquals(string3, documentStore.getDocument(uri1).getDocumentTxt());
    }
    @Test
    public void testThrowsException() throws URISyntaxException, IOException {
        DocumentStoreImpl store = new DocumentStoreImpl(null);
        boolean test = false;
        try {
            store.undo();
        } catch (IllegalStateException e) {
            test = true;
        }
        assertTrue(test);
        test = false;
        String str1 = "1"; byte[] array1 = str1.getBytes();
        ByteArrayInputStream stream1 = new ByteArrayInputStream(array1); ByteArrayInputStream stream11 = new ByteArrayInputStream(array1);
        URI uri = new URI("1");
        assertEquals(0, store.putDocument(stream1, uri, DocumentStore.DocumentFormat.BINARY));
        Document doc = new DocumentImpl(uri, stream11.readAllBytes());
        assertEquals(doc, store.getDocument(uri));
        URI uriFake = new URI("ThisIsAFake");
        try {
            store.undo(uriFake);
        } catch (IllegalStateException e) {
            test = true;
        }
        assertTrue(test);
    }
    @Test
    public void testPointlessDeleteEmptyUndo() throws URISyntaxException {
        DocumentStoreImpl store = new DocumentStoreImpl(null);
        URI uri = new URI("Pizza");
        assertFalse(store.deleteDocument(uri));
        store.undo();
    }

    @Test
    public void testPointlessDeleteFullUndo() throws URISyntaxException {
        DocumentStoreImpl store = new DocumentStoreImpl(null);
        URI uri = new URI("Pizza");
        assertFalse(store.deleteDocument(uri));
        store.undo(uri);
    }
    @Test
    public void testPointlessPutEmptyUndo() throws URISyntaxException, IOException {
        DocumentStoreImpl store = new DocumentStoreImpl(null);
        String str1 = "1"; byte[] array1 = str1.getBytes();
        URI uri = new URI("1");
        assertEquals(0, store.putDocument(null, uri, DocumentStore.DocumentFormat.TXT));
        store.undo();
    }
    @Test
    public void testPointlessPutFullUndo() throws URISyntaxException, IOException {
        DocumentStoreImpl store = new DocumentStoreImpl(null);
        String str1 = "1";
        URI uri = new URI("1");
        assertEquals(0, store.putDocument(null, uri, DocumentStore.DocumentFormat.TXT));
        assertNull(store.getDocument(uri));
        boolean test = false;
        try {
            store.undo(new URI("Pizza"));
        } catch (IllegalStateException e) {
            test = true;
        }
        assertTrue(test);
        store.undo(uri);
    }

    //Tests 2

    @Test
    public void undoTest() throws IOException {
        DocumentStore documentStore = new DocumentStoreImpl(null);
        Boolean test = false;
        try {
            documentStore.undo();
        } catch (IllegalStateException e) {
            test = true;
        }
        assertEquals(true, test);
        String string1 = "It was a dark and stormy night";
        String string2 = "It was the best of times, it was the worst of times";
        String string3 = "It was a bright cold day in April, and the clocks were striking thirteen";
        InputStream inputStream1 = new ByteArrayInputStream(string1.getBytes());
        InputStream inputStream2 = new ByteArrayInputStream(string2.getBytes());
        InputStream inputStream3 = new ByteArrayInputStream(string3.getBytes());
        URI uri1 = URI.create("www.wrinkleintime.com");

        documentStore.putDocument(inputStream1, uri1, DocumentStore.DocumentFormat.TXT);
        assertEquals(string1, documentStore.getDocument(uri1).getDocumentTxt());

        documentStore.putDocument(inputStream2, uri1, DocumentStore.DocumentFormat.TXT);
        assertEquals(string2, documentStore.getDocument(uri1).getDocumentTxt());

        documentStore.undo();

        assertEquals(string1, documentStore.getDocument(uri1).getDocumentTxt());
        documentStore.undo();
        assertEquals(null, documentStore.getDocument(uri1));

        documentStore.putDocument(inputStream3, uri1, DocumentStore.DocumentFormat.TXT);
        assertEquals(string3, documentStore.getDocument(uri1).getDocumentTxt());
        documentStore.deleteDocument(uri1);
        assertEquals(null, documentStore.getDocument(uri1));
        documentStore.undo();
        assertEquals(string3, documentStore.getDocument(uri1).getDocumentTxt());
    }
    @Test
    public void testUndoSpecificUri() throws IOException {
        DocumentStore documentStore = new DocumentStoreImpl(null);

        String string1 = "It was a dark and stormy night";
        String string2 = "It was the best of times, it was the worst of times";
        String string3 = "It was a bright cold day in April, and the clocks were striking thirteen";
        String string4 = "I am free, no matter what rules surround me.";
        InputStream inputStream1 = new ByteArrayInputStream(string1.getBytes());
        InputStream inputStream2 = new ByteArrayInputStream(string2.getBytes());
        InputStream inputStream3 = new ByteArrayInputStream(string3.getBytes());
        InputStream inputStream4 = new ByteArrayInputStream(string4.getBytes());
        URI uri1 = URI.create("www.wrinkleintime.com");
        URI uri2 = URI.create("www.taleoftwocities.com");
        URI uri3 = URI.create("www.1984.com");

        documentStore.putDocument(inputStream1, uri1, DocumentStore.DocumentFormat.TXT);
        assertEquals(string1, documentStore.getDocument(uri1).getDocumentTxt());
        documentStore.putDocument(inputStream2, uri2, DocumentStore.DocumentFormat.TXT);
        assertEquals(string2, documentStore.getDocument(uri2).getDocumentTxt());
        documentStore.undo(uri1);
        assertEquals(null, documentStore.getDocument(uri1));
        assertEquals(string2, documentStore.getDocument(uri2).getDocumentTxt());
        documentStore.putDocument(inputStream3, uri1, DocumentStore.DocumentFormat.TXT);
        assertEquals(string3, documentStore.getDocument(uri1).getDocumentTxt());
        documentStore.putDocument(inputStream4, uri1, DocumentStore.DocumentFormat.TXT);
        assertEquals(string4, documentStore.getDocument(uri1).getDocumentTxt());
        documentStore.deleteDocument(uri1);
        assertEquals(null, documentStore.getDocument(uri1));
        documentStore.undo(uri2);
        assertEquals(null, documentStore.getDocument(uri2));
        documentStore.undo();
        assertEquals(string4, documentStore.getDocument(uri1).getDocumentTxt());
        documentStore.undo(uri1);
        assertEquals(string3, documentStore.getDocument(uri1).getDocumentTxt());

        Boolean test = false;
        try {
            documentStore.undo(uri3);
        } catch (IllegalStateException e) {
            test = true;
        }
        assertEquals(true, test);
    }
    //This has all stage 1 tests in it now.  Some duplicates - Adam
    @Test
    public void DocumentStoreImplTest2() throws URISyntaxException, IOException {
        String initialString = "string";
        String initialString1 = "string1";
        String initialString2 = "string2";
        String initialString3 = "string3";
        String initialString4 = "string4";
        String initialString5 = "string5";
        String initialString6 = "string6";
        String initialString7 = "string7";
        String initialString8 = "string8";
        String initialString9 = "string9";
        String initialString13 = "string13";
        String initialString11 = "string11";
        String initialString12 = "string12";

        InputStream targetStream = new ByteArrayInputStream(initialString.getBytes());
        InputStream targetStream1 = new ByteArrayInputStream(initialString1.getBytes());
        InputStream targetStream2 = new ByteArrayInputStream(initialString2.getBytes());
        InputStream targetStream3 = new ByteArrayInputStream(initialString3.getBytes());
        InputStream targetStream4 = new ByteArrayInputStream(initialString4.getBytes());
        InputStream targetStream5 = new ByteArrayInputStream(initialString5.getBytes());
        InputStream targetStream6 = new ByteArrayInputStream(initialString6.getBytes());
        InputStream targetStream7 = new ByteArrayInputStream(initialString7.getBytes());
        InputStream targetStream8 = new ByteArrayInputStream(initialString8.getBytes());
        InputStream targetStream9 = new ByteArrayInputStream(initialString9.getBytes());
        InputStream targetStream13 = new ByteArrayInputStream(initialString13.getBytes());
        InputStream targetStream11 = new ByteArrayInputStream(initialString11.getBytes());
        InputStream targetStream12 = new ByteArrayInputStream(initialString12.getBytes());

        InputStream targetStream10 = new ByteArrayInputStream(initialString.getBytes());


        DocumentStore documentStore = new DocumentStoreImpl(null);

        String byteTxt = "https://www.HashTableImplByte.org/";
        String stringTxt = "https://www.HashTableImplString.org/";
        URI byteUri = new URI(byteTxt);
        URI stringUri = new URI(stringTxt);

        String byteTxt1 = "https://www.HashTableImplByteOne.org/";
        String stringTxt1 = "https://www.HashTableImplStringOne.org/";
        URI byteUri1 = new URI(byteTxt1);
        URI stringUri1 = new URI(stringTxt1);

        String byteTxt2 = "https://www.HashTableImplByteTwo.org/";
        String stringTxt2 = "https://www.HashTableImplStringTwo.org/";
        URI byteUri2 = new URI(byteTxt2);
        URI stringUri2 = new URI(stringTxt2);

        String byteTxt3 = "https://www.HashTableImplByteThree.org/";
        String stringTxt3 = "https://www.ThreeHashTableImplString.org/";
        URI byteUri3 = new URI(byteTxt3);
        URI stringUri3 = new URI(stringTxt3);

        String byteTxt4 = "https://www.HashTableImplByteFour.org/";
        String stringTxt4 = "https://www.HashTableImplStringFour.org/";
        URI byteUri4 = new URI(byteTxt4);
        URI stringUri4 = new URI(stringTxt4);

        String byteTxt5 = "https://www.HashTableImplByteFive.org/";
        String stringTxt5 = "https://www.HashTableImplStringFive.org/";
        URI byteUri5 = new URI(byteTxt5);
        URI stringUri5 = new URI(stringTxt5);

        String byteTxt6 = "https://www.SixHashTableImplByte.org/";
        String stringTxt6 = "https://www.SixHashTableImplString.org/";
        URI byteUri6 = new URI(byteTxt6);
        URI stringUri6 = new URI(stringTxt6);

        String byteTxt7 = "https://www.HashTableImplByteSeven.org/";
        String stringTxt7 = "https://www.HashTableImplStringSeven.org/";
        URI byteUri7 = new URI(byteTxt7);
        URI stringUri7 = new URI(stringTxt7);

        String byteTxt8 = "https://www.HashTableImplByteEight.org/";
        String stringTxt8 = "https://www.HashTableImplStringEight.org/";
        URI byteUri8 = new URI(byteTxt8);
        URI stringUri8 = new URI(stringTxt8);

        String byteTxt9 = "https://www.HashTableImplByteNine.org/";
        String stringTxt9 = "https://www.HashTableImplStringNine.org/";
        URI byteUri9 = new URI(byteTxt9);
        URI stringUri9 = new URI(stringTxt9);

        byte byteInput[] = {20,10,30,5};
        InputStream targetStreamBytes = new ByteArrayInputStream(byteInput);

        byte byteInput1[] = {20,10,30,5,6};
        InputStream targetStreamBytes1 = new ByteArrayInputStream(byteInput1);

        byte byteInput2[] = {20,10,30,5,7};
        InputStream targetStreamBytes2 = new ByteArrayInputStream(byteInput2);

        byte byteInput3[] = {20,10,30,5,8};
        InputStream targetStreamBytes3 = new ByteArrayInputStream(byteInput3);

        byte byteInput4[] = {20,10,30,5,9};
        InputStream targetStreamBytes4 = new ByteArrayInputStream(byteInput4);

        byte byteInput5[] = {20,10,30,5,5};
        InputStream targetStreamBytes5 = new ByteArrayInputStream(byteInput5);

        byte byteInput6[] = {20,10,30,5,4};
        InputStream targetStreamBytes6 = new ByteArrayInputStream(byteInput6);

        byte byteInput7[] = {20,10,30,5,3};
        InputStream targetStreamBytes7 = new ByteArrayInputStream(byteInput7);

        byte byteInput8[] = {20,10,30,5,2};
        InputStream targetStreamBytes8 = new ByteArrayInputStream(byteInput8);

        byte byteInput9[] = {20,10,30,5,1};
        InputStream targetStreamBytes9 = new ByteArrayInputStream(byteInput9);


        byte byteInput10[] = {20,10,30,5};
        InputStream targetStreamBytes10 = new ByteArrayInputStream(byteInput10);



        int x = documentStore.putDocument(targetStream, stringUri, DocumentStore.DocumentFormat.TXT);
        assertEquals(x,0);
        int y = documentStore.putDocument(targetStreamBytes, byteUri, DocumentStore.DocumentFormat.TXT);
        assertEquals(y,0);

        //int previousDocHashcode = documentStore.getDocument(stringUri).hashCode();

        int z = documentStore.putDocument(targetStream1, stringUri1, DocumentStore.DocumentFormat.TXT);
        assertEquals(z, 0);

        //int previousDocHashcode1 = documentStore.getDocument(stringUri1).hashCode();

        int zz = documentStore.putDocument(targetStream2, stringUri2, DocumentStore.DocumentFormat.TXT);
        assertEquals(0, zz);

        int zzzss = documentStore.putDocument(targetStream8, stringUri8, DocumentStore.DocumentFormat.TXT);
        assertEquals(0, zzzss);

        int zzzq = documentStore.putDocument(targetStream3, stringUri3, DocumentStore.DocumentFormat.TXT);
        assertEquals(0, zzzq);

        int zzzw = documentStore.putDocument(targetStream4, stringUri4, DocumentStore.DocumentFormat.TXT);
        assertEquals(0, zzzw);

        int zzze = documentStore.putDocument(targetStream5, stringUri5, DocumentStore.DocumentFormat.TXT);
        assertEquals(0, zzze);

        int zzzs = documentStore.putDocument(targetStream6, stringUri6, DocumentStore.DocumentFormat.TXT);
        assertEquals(0, zzzs);

        int zzza = documentStore.putDocument(targetStream7, stringUri7, DocumentStore.DocumentFormat.TXT);
        assertEquals(0, zzza);



        assertEquals(documentStore.getDocument(stringUri8).getDocumentTxt(), "string8");
        assertEquals(documentStore.getDocument(stringUri7).getDocumentTxt(), "string7");
        assertEquals(documentStore.getDocument(stringUri6).getDocumentTxt(), "string6");
        assertEquals(documentStore.getDocument(stringUri5).getDocumentTxt(), "string5");
        assertEquals(documentStore.getDocument(stringUri4).getDocumentTxt(), "string4");
        assertEquals(documentStore.getDocument(stringUri3).getDocumentTxt(), "string3");
        assertEquals(documentStore.getDocument(stringUri2).getDocumentTxt(), "string2");
        assertEquals(documentStore.getDocument(stringUri1).getDocumentTxt(), "string1");
        assertEquals(documentStore.getDocument(stringUri).getDocumentTxt(), "string");


        //assertEquals(documentStore.getDocument(stringUri1).getDocumentTxt(), "string7");
        //int qqqq = documentStore.getDocument(stringUri1).hashCode();
        int asdg = documentStore.putDocument(targetStream12, stringUri7, DocumentStore.DocumentFormat.TXT);
        //assertEquals(asdg, qqqq);
        assertEquals(documentStore.getDocument(stringUri7).getDocumentTxt(), "string12");

        int wwww = documentStore.getDocument(stringUri7).hashCode();
        assertEquals(documentStore.putDocument(targetStream11, stringUri7, DocumentStore.DocumentFormat.TXT), wwww);

        assertEquals(documentStore.getDocument(stringUri7).getDocumentTxt(), "string11");


        int wwwwq = documentStore.getDocument(stringUri7).hashCode();
        assertEquals(documentStore.putDocument(targetStream13, stringUri7, DocumentStore.DocumentFormat.TXT), wwwwq);
        assertEquals(documentStore.getDocument(stringUri7).getDocumentTxt(), "string13");


    }
//    @Test
//    public void basicGetAndPut() {
//        HashTableImpl<Integer, Integer> table = new HashTableImpl<>();
//        table.put(1, 1);
//        assertEquals(1, (int)table.get(1));
//        assertEquals(1, (int)table.put(1, 2));
//        assertNull(table.put(2, 2));
//        assertEquals(2, (int)table.get(2));
//        assertNull(table.get(100));
//    }
//    @Test
//    public void basicSameCell() {
//        HashTableImpl<Integer, Integer> table = new HashTableImpl<>();
//        table.put(0, 0);
//        table.put(5, 5);
//        assertEquals(0, (int)table.get(0));
//        assertEquals(5, (int)table.get(5));
//        assertEquals(5, (int)table.put(5, 10));
//        assertEquals(10, (int)table.get(5));
//        assertEquals(10, (int)table.put(5, 100));
//        assertNull(table.put(10, 1000));
//        assertEquals(0, (int)table.put(0, 1));
//        assertEquals(1, (int)table.get(0));
//        table.put(1, 1);
//        assertEquals(1, (int)table.put(1, 6));
//        assertEquals(6, (int)table.get(1));
//        assertEquals(100, (int)table.put(5, 5));
//    }
//    @Test
//    public void testGetAndPut() {
//        HashTableImpl<Integer, Integer> table = new HashTableImpl<>();
//        for (int i=0; i<1000; i++) {
//            assertNull(table.put(i, i));
//        }
//        for (int i=0; i<1000; i++) {
//            assertEquals(i, (int)table.get(i));
//        }
//        for (int i=0; i<1000; i++) {
//            assertEquals(i, (int)table.put(i, i+1));
//        }
//        for (int i=0; i<100; i++) {
//            assertEquals(i+1, (int)table.get(i));
//        }
//    }
//    @Test
//    public void hashTableImplSimplePutAndGet2() {
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

//    @Test
//    public void hashTableImplALotOfInfoTest2() {
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
//    public void hashTableImplCollisionTest2() {
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
//    public void hashTableImplReplacementTest4() {
//        HashTable<Integer,Integer> hashTable = new HashTableImpl<Integer,Integer>();
//        hashTable.put(1,2);
//        int a = hashTable.put(1, 3);
//        assertEquals(2, a);
//        int b = hashTable.put(1, 4);
//        assertEquals(3,b);
//        int c = hashTable.put(1, 9);
//        assertEquals(4, c);
//    }
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
//    @Test
//    public void hashTableImplReplacementTest3() {
//        HashTable<Integer, Integer> hashTable = new HashTableImpl<Integer, Integer>();
//        hashTable.put(1, 2);
//        int a = hashTable.put(1, 3);
//        assertEquals(2, a);
//        int b = hashTable.put(1, 4);
//        assertEquals(3, b);
//        int c = hashTable.put(1, 9);
//        assertEquals(4, c);
//    }
//    @Test
//    public void basicCollision2() {
//        HashTable<Integer, String> hashTable = new HashTableImpl<Integer, String>();
//        hashTable.put(1, "Avi");
//        hashTable.put(5, "dinsky");
//        hashTable.put(6, "Radinsky");
//        hashTable.put(11, "gami");
//        assertEquals("gami", hashTable.put(11, "gthir"));
//        assertEquals("gthir", hashTable.get(11));
//        assertEquals("Avi", hashTable.get(1));
//        assertEquals("Radinsky", hashTable.get(6));
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
//        assertEquals(test3a, 27128);
//        Integer test3b = hashTable.get("Teheran");
//        assertEquals(test3b, (Integer)82172);
//
//    }
    public void testPutNullDeletion2() throws IOException {
        DocumentStore documentStore = new DocumentStoreImpl(null);
        String string1 = "It was a dark and stormy night";
        String string2 = "It was the best of times, it was the worst of times";
        String string3 = "It was a bright cold day in April, and the clocks were striking thirteen";
        String string4 = "I am free, no matter what rules surround me.";
        byte[] bytes3 = string3.getBytes();
        byte[] bytes4 = string4.getBytes();
        InputStream inputStream1 = new ByteArrayInputStream(string1.getBytes());
        InputStream inputStream2 = new ByteArrayInputStream(string2.getBytes());
        InputStream inputStream3 = new ByteArrayInputStream(bytes3);
        InputStream inputStream4 = new ByteArrayInputStream(bytes4);
        URI uri1 =  URI.create("www.wrinkleintime.com");
        URI uri2 =  URI.create("www.taleoftwocities.com");
        URI uri3 =  URI.create("www.1984.com");
        URI uri4 =  URI.create("www.themoonisaharshmistress.com");
        int putTXT1 = documentStore.putDocument(inputStream1,uri1,DocumentStore.DocumentFormat.TXT);
        int putTXT2 = documentStore.putDocument(inputStream2,uri2,DocumentStore.DocumentFormat.TXT);
        int putBINARY1 = documentStore.putDocument(inputStream3,uri3,DocumentStore.DocumentFormat.BINARY);
        int putBINARY2 = documentStore.putDocument(inputStream4,uri4,DocumentStore.DocumentFormat.BINARY);
        assertEquals(putTXT1,0);
        assertEquals(putTXT2,0);
        assertEquals(putBINARY1,0);
        assertEquals(putBINARY2,0);
        documentStore.putDocument(null,uri1,DocumentStore.DocumentFormat.TXT);
        documentStore.putDocument(null,uri2,DocumentStore.DocumentFormat.TXT);
        documentStore.putDocument(null,uri3,DocumentStore.DocumentFormat.BINARY);
        documentStore.putDocument(null,uri4,DocumentStore.DocumentFormat.BINARY);
        Document nullDoc1 = documentStore.getDocument(uri1);
        Document nullDoc2 = documentStore.getDocument(uri2);
        Document nullDoc3 = documentStore.getDocument(uri3);
        Document nullDoc4 = documentStore.getDocument(uri4);
        assertEquals(nullDoc1,null);
        assertEquals(nullDoc2,null);
        assertEquals(nullDoc3,null);
        assertEquals(nullDoc4,null);
    }
    @Test
    public void testSimplePutValues2() throws IOException {
        DocumentStore documentStore = new DocumentStoreImpl(null);
        String string1 = "It was a dark and stormy night";
        String string2 = "It was the best of times, it was the worst of times";
        String string3 = "It was a bright cold day in April, and the clocks were striking thirteen";
        String string4 = "I am free, no matter what rules surround me.";
        byte[] bytes3 = string3.getBytes();
        byte[] bytes4 = string4.getBytes();
        InputStream inputStream1 = new ByteArrayInputStream(string1.getBytes());
        InputStream inputStream2 = new ByteArrayInputStream(string2.getBytes());
        InputStream inputStream3 = new ByteArrayInputStream(bytes3);
        InputStream inputStream4 = new ByteArrayInputStream(bytes4);
        URI uri1 =  URI.create("www.wrinkleintime.com");
        URI uri2 =  URI.create("www.taleoftwocities.com");
        URI uri3 =  URI.create("www.1984.com");
        URI uri4 =  URI.create("www.themoonisaharshmistress.com");
        int putTXT1 = documentStore.putDocument(inputStream1,uri1,DocumentStore.DocumentFormat.TXT);
        int putTXT2 = documentStore.putDocument(inputStream2,uri2,DocumentStore.DocumentFormat.TXT);
        int putBINARY1 = documentStore.putDocument(inputStream3,uri3,DocumentStore.DocumentFormat.BINARY);
        int putBINARY2 = documentStore.putDocument(inputStream4,uri4,DocumentStore.DocumentFormat.BINARY);
        assertEquals(putTXT1,0);
        assertEquals(putTXT2,0);
        assertEquals(putBINARY1,0);
        assertEquals(putBINARY2,0);
    }
    @Test
    public void testCollisionPutValues2() throws IOException {
        DocumentStore documentStore = new DocumentStoreImpl(null);
        String string1 = "It was a dark and stormy night";
        String string2 = "It was the best of times, it was the worst of times";
        String string3 = "It was a bright cold day in April, and the clocks were striking thirteen";
        String string4 = "I am free, no matter what rules surround me.";
        byte[] bytes3 = string3.getBytes();
        byte[] bytes4 = string4.getBytes();
        InputStream inputStream1 = new ByteArrayInputStream(string1.getBytes());
        InputStream inputStream2 = new ByteArrayInputStream(string2.getBytes());
        InputStream inputStream3 = new ByteArrayInputStream(bytes3);
        InputStream inputStream4 = new ByteArrayInputStream(bytes4);
        URI uri1 =  URI.create("www.wrinkleintime.com");
        URI uri2 =  URI.create("www.taleoftwocities.com");
        URI uri3 =  URI.create("www.1984.com");
        URI uri4 =  URI.create("www.themoonisaharshmistress.com");
        documentStore.putDocument(inputStream1,uri1,DocumentStore.DocumentFormat.TXT);
        int putTXT2 = documentStore.putDocument(inputStream2,uri2,DocumentStore.DocumentFormat.TXT);
        int putBINARY1 = documentStore.putDocument(inputStream3,uri3,DocumentStore.DocumentFormat.BINARY);
        int putBINARY2 = documentStore.putDocument(inputStream4,uri4,DocumentStore.DocumentFormat.BINARY);
        int test1 = documentStore.getDocument(uri1).hashCode();
        int test2 = documentStore.getDocument(uri2).hashCode();
        int test3 = documentStore.getDocument(uri3).hashCode();
        int test4 = documentStore.getDocument(uri4).hashCode();
        InputStream inputStream1b = new ByteArrayInputStream(string1.getBytes());
        InputStream inputStream2b = new ByteArrayInputStream(string2.getBytes());
        InputStream inputStream3b = new ByteArrayInputStream(bytes3);
        InputStream inputStream4b = new ByteArrayInputStream(bytes4);
        int putBINARY1Collision = documentStore.putDocument(inputStream1b,uri1,DocumentStore.DocumentFormat.BINARY);
        int putBINARY2Collision = documentStore.putDocument(inputStream2b,uri2,DocumentStore.DocumentFormat.BINARY);
        int putTXT1Collision = documentStore.putDocument(inputStream3b,uri3,DocumentStore.DocumentFormat.TXT);
        int putTXT2Collision = documentStore.putDocument(inputStream4b,uri4,DocumentStore.DocumentFormat.TXT);
        Document document1 = new DocumentImpl(uri1, string1.getBytes());
        Document document2 = new DocumentImpl(uri2, string2.getBytes());
        Document document3 = new DocumentImpl(uri3, string3);
        Document document4 = new DocumentImpl(uri4, string4);
        assertEquals(putBINARY1Collision,test1);
        assertEquals(putBINARY2Collision,test2);
        assertEquals(putTXT1Collision,test3);
        assertEquals(putTXT2Collision,test4);
    }

    @Test
    public void addNewToStore() throws URISyntaxException, IOException {
        DocumentStoreImpl store = new DocumentStoreImpl(null);
        String str = "Hello";
        byte[] array = str.getBytes();
        ByteArrayInputStream stream = new ByteArrayInputStream(array);
        ByteArrayInputStream stream2 = new ByteArrayInputStream(array);
        URI uri = new URI("Hello");
        assertEquals(0, store.putDocument(stream, uri, DocumentStore.DocumentFormat.BINARY));
        Document doc = new DocumentImpl(uri, stream2.readAllBytes());
        assertEquals(doc, store.getDocument(uri));
    }
    @Test
    public void addOldToStore() throws URISyntaxException, IOException {
        DocumentStoreImpl store = new DocumentStoreImpl(null);
        String str = "Hello";
        byte[] array = str.getBytes();
        ByteArrayInputStream stream = new ByteArrayInputStream(array);
        URI uri = new URI("Hello");
        String str2 = "Hi";
        byte[] array2 = str2.getBytes();
        ByteArrayInputStream stream2 = new ByteArrayInputStream(array2);
        DocumentImpl doc = new DocumentImpl(uri, array);
        store.putDocument(stream, uri, DocumentStore.DocumentFormat.BINARY);
        assertEquals(doc.hashCode(), store.putDocument(stream2, uri, DocumentStore.DocumentFormat.BINARY));
        DocumentImpl doc2 = new DocumentImpl(uri, array2);
        assertEquals(doc2, store.getDocument(uri));

    }
    @Test
    public void TestDeleteDocument() throws URISyntaxException, IOException {
        DocumentStoreImpl store = new DocumentStoreImpl(null);
        String str = "Hello";
        byte[] array = str.getBytes();
        ByteArrayInputStream stream = new ByteArrayInputStream(array);
        ByteArrayInputStream stream2 = new ByteArrayInputStream(array);
        ByteArrayInputStream stream3 = new ByteArrayInputStream(array);
        ByteArrayInputStream stream4 = new ByteArrayInputStream(array);
        ByteArrayInputStream stream5 = new ByteArrayInputStream(array);
        ByteArrayInputStream stream6 = new ByteArrayInputStream(array);
        URI uri = new URI("Hello");
        URI uri1 = new URI("Hi");
        assertEquals(0, store.putDocument(stream, uri, DocumentStore.DocumentFormat.BINARY));
        assertEquals(0, store.putDocument(stream6, uri1, DocumentStore.DocumentFormat.BINARY));
        Document doc = new DocumentImpl(uri, stream2.readAllBytes());
        assertEquals(doc, store.getDocument(uri));
        assertNotNull(store.getDocument(uri1));
        assertTrue(store.deleteDocument(uri1));
        assertFalse(store.deleteDocument(new URI("Pizza")));
        assertNull(store.getDocument(uri1));
        assertNotNull(store.getDocument(uri));
        assertEquals(0, store.putDocument(stream3, uri1, DocumentStore.DocumentFormat.BINARY));
        Document doc2 = new DocumentImpl(uri, stream4.readAllBytes());
        assertEquals(doc2, store.getDocument(uri));
        assertEquals(doc2.hashCode(), store.putDocument(null, uri, DocumentStore.DocumentFormat.BINARY));
        assertNull(store.getDocument(uri));
        assertNotNull(store.getDocument(uri1));
    }
    @Test
    public void testPutDocumentStoreAsText2() {
        DocumentStore documentStore = new DocumentStoreImpl(null);
        String string1 = "It was a dark and stormy night";
        String string2 = "It was the best of times, it was the worst of times";
        String string3 = "It was a bright cold day in April, and the clocks were striking thirteen";
        String string4 = "I am free, no matter what rules surround me.";
        InputStream inputStream1 = new ByteArrayInputStream(string1.getBytes());
        InputStream inputStream2 = new ByteArrayInputStream(string2.getBytes());
        InputStream inputStream3 = new ByteArrayInputStream(string3.getBytes());
        InputStream inputStream4 = new ByteArrayInputStream(string4.getBytes());
        URI uri1 =  URI.create("www.wrinkleintime.com");
        URI uri2 =  URI.create("www.taleoftwocities.com");
        URI uri3 =  URI.create("www.1984.com");
        URI uri4 =  URI.create("www.themoonisaharshmistress.com");
        try {
            documentStore.putDocument(inputStream1, uri1, DocumentStore.DocumentFormat.TXT);
            documentStore.putDocument(inputStream2, uri2, DocumentStore.DocumentFormat.TXT);
            documentStore.putDocument(inputStream3, uri3, DocumentStore.DocumentFormat.TXT);
            documentStore.putDocument(inputStream4, uri4, DocumentStore.DocumentFormat.TXT);
        } catch (java.io.IOException e) {
            fail();
        }
        Document document1 = new DocumentImpl(uri1, string1);
        Document document2 = new DocumentImpl(uri2, string2);
        Document document3 = new DocumentImpl(uri3, string3);
        Document document4 = new DocumentImpl(uri4, string4);
        int test1 = documentStore.getDocument(uri1).hashCode();
        int test2 = documentStore.getDocument(uri2).hashCode();
        int test3 = documentStore.getDocument(uri3).hashCode();
        int test4 = documentStore.getDocument(uri4).hashCode();
        assertEquals(document1.hashCode(),test1);
        assertEquals(document2.hashCode(),test2);
        assertEquals(document3.hashCode(),test3);
        assertEquals(document4.hashCode(),test4);
    }
    @Test
    public void testPutDocumentStoreAsBinary2() {
        DocumentStore documentStore = new DocumentStoreImpl(null);
        String string1 = "It was a dark and stormy night";
        String string2 = "It was the best of times, it was the worst of times";
        String string3 = "It was a bright cold day in April, and the clocks were striking thirteen";
        String string4 = "I am free, no matter what rules surround me.";
        byte[] bytes1 = string1.getBytes();
        byte[] bytes2 = string2.getBytes();
        byte[] bytes3 = string3.getBytes();
        byte[] bytes4 = string4.getBytes();
        InputStream inputStream1 = new ByteArrayInputStream(bytes1);
        InputStream inputStream2 = new ByteArrayInputStream(bytes2);
        InputStream inputStream3 = new ByteArrayInputStream(bytes3);
        InputStream inputStream4 = new ByteArrayInputStream(bytes4);
        URI uri1 =  URI.create("www.wrinkleintime.com");
        URI uri2 =  URI.create("www.taleoftwocities.com");
        URI uri3 =  URI.create("www.1984.com");
        URI uri4 =  URI.create("www.themoonisaharshmistress.com");
        try {
            documentStore.putDocument(inputStream1, uri1, DocumentStore.DocumentFormat.BINARY);
            documentStore.putDocument(inputStream2, uri2, DocumentStore.DocumentFormat.BINARY);
            documentStore.putDocument(inputStream3, uri3, DocumentStore.DocumentFormat.BINARY);
            documentStore.putDocument(inputStream4, uri4, DocumentStore.DocumentFormat.BINARY);
        } catch (java.io.IOException e) {
            fail();
        }
        Document document1 = new DocumentImpl(uri1, bytes1);
        Document document2 = new DocumentImpl(uri2, bytes2);
        Document document3 = new DocumentImpl(uri3, bytes3);
        Document document4 = new DocumentImpl(uri4, bytes4);
        int test1 = documentStore.getDocument(uri1).hashCode();
        int test2 = documentStore.getDocument(uri2).hashCode();
        int test3 = documentStore.getDocument(uri3).hashCode();
        int test4 = documentStore.getDocument(uri4).hashCode();
        assertEquals(document1.hashCode(),test1);
        assertEquals(document2.hashCode(),test2);
        assertEquals(document3.hashCode(),test3);
        assertEquals(document4.hashCode(),test4);
    }
    @Test
    public void testThrowException2() {
        String string = "Not empty!";
        String nullString = null;
        byte[] bytes = string.getBytes();
        byte[] nullBytes = null;
        URI uriNotEmpty = URI.create("www.notempty.com");
        URI uriEmpty = URI.create("");
        assertThrows(IllegalArgumentException.class,
                ()->{
                    new DocumentImpl(uriNotEmpty, nullBytes);
                });
        assertThrows(IllegalArgumentException.class,
                ()->{
                    new DocumentImpl(uriNotEmpty, nullString);
                });
        assertThrows(IllegalArgumentException.class,
                ()->{
                    new DocumentImpl(null, bytes);
                });
        assertThrows(IllegalArgumentException.class,
                ()->{
                    new DocumentImpl(null, string);
                });
        assertThrows(IllegalArgumentException.class,
                ()->{
                    new DocumentImpl(uriEmpty, string);
                });
    }
    //does not test get, delete, or put return value on DocumentStore - will add those here sometime tonight

//or apparently at the end of the week


    URI[] uriArray = new URI[21];
    Document[] docArray = new Document[21];
    String[] stringArray = {"The blue parrot drove by the hitchhiking mongoose.",
            "She thought there'd be sufficient time if she hid her watch.",
            "Choosing to do nothing is still a choice, after all.",
            "He found the chocolate covered roaches quite tasty.",
            "The efficiency we have at removing trash has made creating trash more acceptable.",
            "Peanuts don't grow on trees, but cashews do.",
            "A song can make or ruin a person’s day if they let it get to them.",
            "You bite up because of your lower jaw.",
            "He realized there had been several deaths on this road, but his concern rose when he saw the exact number.",
            "So long and thanks for the fish.",
            "Three years later, the coffin was still full of Jello.",
            "Weather is not trivial - it's especially important when you're standing in it.",
            "He walked into the basement with the horror movie from the night before playing in his head.",
            "He wondered if it could be called a beach if there was no sand.",
            "Jeanne wished she has chosen the red button.",
            "It's much more difficult to play tennis with a bowling ball than it is to bowl with a tennis ball.",
            "Pat ordered a ghost pepper pie.",
            "Everyone says they love nature until they realize how dangerous she can be.",
            "The memory we used to share is no longer coherent.",
            "My harvest will come Tiny valorous straw Among the millions Facing to the sun",
            "A dreamy-eyed child staring into night On a journey to storyteller's mind Whispers a wish speaks with the stars the words are silent in him"};
    @Test
    public void docTest2() throws URISyntaxException {
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
    @Test
    public void testPutDocumentStoreAsText() {
        DocumentStore documentStore = new DocumentStoreImpl(null);
        String string1 = "It was a dark and stormy night";
        String string2 = "It was the best of times, it was the worst of times";
        String string3 = "It was a bright cold day in April, and the clocks were striking thirteen";
        String string4 = "I am free, no matter what rules surround me.";
        InputStream inputStream1 = new ByteArrayInputStream(string1.getBytes());
        InputStream inputStream2 = new ByteArrayInputStream(string2.getBytes());
        InputStream inputStream3 = new ByteArrayInputStream(string3.getBytes());
        InputStream inputStream4 = new ByteArrayInputStream(string4.getBytes());
        URI uri1 = URI.create("www.wrinkleintime.com");
        URI uri2 = URI.create("www.taleoftwocities.com");
        URI uri3 = URI.create("www.1984.com");
        URI uri4 = URI.create("www.themoonisaharshmistress.com");
        try {
            documentStore.putDocument(inputStream1, uri1, DocumentStore.DocumentFormat.TXT);
            documentStore.putDocument(inputStream2, uri2, DocumentStore.DocumentFormat.TXT);
            documentStore.putDocument(inputStream3, uri3, DocumentStore.DocumentFormat.TXT);
            documentStore.putDocument(inputStream4, uri4, DocumentStore.DocumentFormat.TXT);
        } catch (java.io.IOException e) {
            fail();
        }
        Document document1 = new DocumentImpl(uri1, string1);
        Document document2 = new DocumentImpl(uri2, string2);
        Document document3 = new DocumentImpl(uri3, string3);
        Document document4 = new DocumentImpl(uri4, string4);
        int test1 = documentStore.getDocument(uri1).hashCode();
        int test2 = documentStore.getDocument(uri2).hashCode();
        int test3 = documentStore.getDocument(uri3).hashCode();
        int test4 = documentStore.getDocument(uri4).hashCode();
        assertEquals(document1.hashCode(), test1);
        assertEquals(document2.hashCode(), test2);
        assertEquals(document3.hashCode(), test3);
        assertEquals(document4.hashCode(), test4);
    }

    @Test
    public void testPutDocumentStoreAsBinary() {
        DocumentStore documentStore = new DocumentStoreImpl(null);
        String string1 = "It was a dark and stormy night";
        String string2 = "It was the best of times, it was the worst of times";
        String string3 = "It was a bright cold day in April, and the clocks were striking thirteen";
        String string4 = "I am free, no matter what rules surround me.";
        byte[] bytes1 = string1.getBytes();
        byte[] bytes2 = string2.getBytes();
        byte[] bytes3 = string3.getBytes();
        byte[] bytes4 = string4.getBytes();
        InputStream inputStream1 = new ByteArrayInputStream(bytes1);
        InputStream inputStream2 = new ByteArrayInputStream(bytes2);
        InputStream inputStream3 = new ByteArrayInputStream(bytes3);
        InputStream inputStream4 = new ByteArrayInputStream(bytes4);
        URI uri1 = URI.create("www.wrinkleintime.com");
        URI uri2 = URI.create("www.taleoftwocities.com");
        URI uri3 = URI.create("www.1984.com");
        URI uri4 = URI.create("www.themoonisaharshmistress.com");
        try {
            documentStore.putDocument(inputStream1, uri1, DocumentStore.DocumentFormat.BINARY);
            documentStore.putDocument(inputStream2, uri2, DocumentStore.DocumentFormat.BINARY);
            documentStore.putDocument(inputStream3, uri3, DocumentStore.DocumentFormat.BINARY);
            documentStore.putDocument(inputStream4, uri4, DocumentStore.DocumentFormat.BINARY);
        } catch (java.io.IOException e) {
            fail();
        }
        Document document1 = new DocumentImpl(uri1, bytes1);
        Document document2 = new DocumentImpl(uri2, bytes2);
        Document document3 = new DocumentImpl(uri3, bytes3);
        Document document4 = new DocumentImpl(uri4, bytes4);
        int test1 = documentStore.getDocument(uri1).hashCode();
        int test2 = documentStore.getDocument(uri2).hashCode();
        int test3 = documentStore.getDocument(uri3).hashCode();
        int test4 = documentStore.getDocument(uri4).hashCode();
        assertEquals(document1.hashCode(), test1);
        assertEquals(document2.hashCode(), test2);
        assertEquals(document3.hashCode(), test3);
        assertEquals(document4.hashCode(), test4);
    }

    @Test
    public void testGetAndPutReturnValues() {
        for (int i = 0; i < 7; i++) {
            uriArray[i] = URI.create("www.google" + i + ".com");
        }
        //first 7 docs in array are txt, next are bit, last are txt
        for (int i = 0; i < 7; i++) {
            docArray[i] = new DocumentImpl(uriArray[i], stringArray[i]);
        }
        for (int i = 0; i < 7; i++) {
            docArray[i + 7] = new DocumentImpl(uriArray[i], stringArray[i + 7].getBytes());
        }
        for (int i = 0; i < 7; i++) {
            docArray[i + 14] = new DocumentImpl(uriArray[i], stringArray[i + 14]);
        }
        DocumentStore documentStore = new DocumentStoreImpl(null);
        try {
            int testa1 = documentStore.putDocument(new ByteArrayInputStream(stringArray[0].getBytes()), uriArray[0], DocumentStore.DocumentFormat.TXT);
            int testa2 = documentStore.putDocument(new ByteArrayInputStream(stringArray[1].getBytes()), uriArray[1], DocumentStore.DocumentFormat.TXT);
            int testa3 = documentStore.putDocument(new ByteArrayInputStream(stringArray[2].getBytes()), uriArray[2], DocumentStore.DocumentFormat.TXT);
            int testa4 = documentStore.putDocument(new ByteArrayInputStream(stringArray[3].getBytes()), uriArray[3], DocumentStore.DocumentFormat.TXT);
            int testa5 = documentStore.putDocument(new ByteArrayInputStream(stringArray[4].getBytes()), uriArray[4], DocumentStore.DocumentFormat.TXT);
            int testa6 = documentStore.putDocument(new ByteArrayInputStream(stringArray[5].getBytes()), uriArray[5], DocumentStore.DocumentFormat.TXT);
            int testa7 = documentStore.putDocument(new ByteArrayInputStream(stringArray[6].getBytes()), uriArray[6], DocumentStore.DocumentFormat.TXT);
            assertEquals(testa1, 0);
            assertEquals(testa2, 0);
            assertEquals(testa3, 0);
            assertEquals(testa4, 0);
            assertEquals(testa5, 0);
            assertEquals(testa6, 0);
            assertEquals(testa7, 0);
        } catch (java.io.IOException e) {
            fail();
        }
        assertEquals(docArray[0], documentStore.getDocument(uriArray[0]));
        assertEquals(docArray[1], documentStore.getDocument(uriArray[1]));
        assertEquals(docArray[2], documentStore.getDocument(uriArray[2]));
        assertEquals(docArray[3], documentStore.getDocument(uriArray[3]));
        assertEquals(docArray[4], documentStore.getDocument(uriArray[4]));
        assertEquals(docArray[5], documentStore.getDocument(uriArray[5]));
        assertEquals(docArray[6], documentStore.getDocument(uriArray[6]));

        try {
            int testb1 = documentStore.putDocument(new ByteArrayInputStream(stringArray[7].getBytes()), uriArray[0], DocumentStore.DocumentFormat.BINARY);
            int testb2 = documentStore.putDocument(new ByteArrayInputStream(stringArray[8].getBytes()), uriArray[1], DocumentStore.DocumentFormat.BINARY);
            int testb3 = documentStore.putDocument(new ByteArrayInputStream(stringArray[9].getBytes()), uriArray[2], DocumentStore.DocumentFormat.BINARY);
            int testb4 = documentStore.putDocument(new ByteArrayInputStream(stringArray[10].getBytes()), uriArray[3], DocumentStore.DocumentFormat.BINARY);
            int testb5 = documentStore.putDocument(new ByteArrayInputStream(stringArray[11].getBytes()), uriArray[4], DocumentStore.DocumentFormat.BINARY);
            int testb6 = documentStore.putDocument(new ByteArrayInputStream(stringArray[12].getBytes()), uriArray[5], DocumentStore.DocumentFormat.BINARY);
            int testb7 = documentStore.putDocument(new ByteArrayInputStream(stringArray[13].getBytes()), uriArray[6], DocumentStore.DocumentFormat.BINARY);
            assertEquals(testb1, docArray[0].hashCode());
            assertEquals(testb2, docArray[1].hashCode());
            assertEquals(testb3, docArray[2].hashCode());
            assertEquals(testb4, docArray[3].hashCode());
            assertEquals(testb5, docArray[4].hashCode());
            assertEquals(testb6, docArray[5].hashCode());
            assertEquals(testb7, docArray[6].hashCode());
        } catch (java.io.IOException e) {
            fail();
        }

        assertEquals(docArray[7], documentStore.getDocument(uriArray[0]));
        assertEquals(docArray[8], documentStore.getDocument(uriArray[1]));
        assertEquals(docArray[9], documentStore.getDocument(uriArray[2]));
        assertEquals(docArray[10], documentStore.getDocument(uriArray[3]));
        assertEquals(docArray[11], documentStore.getDocument(uriArray[4]));
        assertEquals(docArray[12], documentStore.getDocument(uriArray[5]));
        assertEquals(docArray[13], documentStore.getDocument(uriArray[6]));

        try {
            int testc1 = documentStore.putDocument(new ByteArrayInputStream(stringArray[14].getBytes()), uriArray[0], DocumentStore.DocumentFormat.TXT);
            int testc2 = documentStore.putDocument(new ByteArrayInputStream(stringArray[15].getBytes()), uriArray[1], DocumentStore.DocumentFormat.TXT);
            int testc3 = documentStore.putDocument(new ByteArrayInputStream(stringArray[16].getBytes()), uriArray[2], DocumentStore.DocumentFormat.TXT);
            int testc4 = documentStore.putDocument(new ByteArrayInputStream(stringArray[17].getBytes()), uriArray[3], DocumentStore.DocumentFormat.TXT);
            int testc5 = documentStore.putDocument(new ByteArrayInputStream(stringArray[18].getBytes()), uriArray[4], DocumentStore.DocumentFormat.TXT);
            int testc6 = documentStore.putDocument(new ByteArrayInputStream(stringArray[19].getBytes()), uriArray[5], DocumentStore.DocumentFormat.TXT);
            int testc7 = documentStore.putDocument(new ByteArrayInputStream(stringArray[20].getBytes()), uriArray[6], DocumentStore.DocumentFormat.TXT);
            assertEquals(testc1, docArray[7].hashCode());
            assertEquals(testc2, docArray[8].hashCode());
            assertEquals(testc3, docArray[9].hashCode());
            assertEquals(testc4, docArray[10].hashCode());
            assertEquals(testc5, docArray[11].hashCode());
            assertEquals(testc6, docArray[12].hashCode());
            assertEquals(testc7, docArray[13].hashCode());
        } catch (java.io.IOException e) {
            fail();
        }

        assertEquals(docArray[14], documentStore.getDocument(uriArray[0]));
        assertEquals(docArray[15], documentStore.getDocument(uriArray[1]));
        assertEquals(docArray[16], documentStore.getDocument(uriArray[2]));
        assertEquals(docArray[17], documentStore.getDocument(uriArray[3]));
        assertEquals(docArray[18], documentStore.getDocument(uriArray[4]));
        assertEquals(docArray[19], documentStore.getDocument(uriArray[5]));
        assertEquals(docArray[20], documentStore.getDocument(uriArray[6]));
    }

    @Test
    public void testDelete() {
        DocumentStore documentStore = new DocumentStoreImpl(null);
        for (int i = 0; i < 21; i++) {
            uriArray[i] = URI.create("www.google" + i + ".com");
        }
        for (int i = 0; i < 21; i++) {
            try {
                documentStore.putDocument(new ByteArrayInputStream(stringArray[i].getBytes()), uriArray[i], DocumentStore.DocumentFormat.BINARY);
            } catch (java.io.IOException e) {
                fail();
            }
        }

        boolean test1a = documentStore.deleteDocument(uriArray[16]);
        boolean test1b = documentStore.deleteDocument(uriArray[16]);
        boolean test1c = documentStore.deleteDocument(URI.create("www.notinstore.com"));

        assertTrue(test1a);
        assertFalse(test1b);
        assertFalse(test1c);

        boolean test2a = documentStore.deleteDocument(uriArray[8]);
        boolean test2b = documentStore.deleteDocument(uriArray[8]);
        boolean test2c = documentStore.deleteDocument(URI.create("www.obviouslyfake.com"));

        assertTrue(test2a);
        assertEquals(test2b, false);
        assertEquals(test2c, false);

        boolean test3a = documentStore.deleteDocument(uriArray[2]);
        boolean test3b = documentStore.deleteDocument(uriArray[2]);
        boolean test3c = documentStore.deleteDocument(URI.create("www.notinstore.net"));

        assertEquals(test3a, true);
        assertEquals(test3b, false);
        assertEquals(test3c, false);
    }

    @Test
    public void testThrowException() {
        String string = "Not empty!";
        String nullString = null;
        byte[] bytes = string.getBytes();
        byte[] nullBytes = null;
        URI uriNotEmpty = URI.create("www.notempty.com");
        URI uriEmpty = URI.create("");
        assertThrows(IllegalArgumentException.class,
                () -> {
                    new DocumentImpl(uriNotEmpty, nullBytes);
                });
        assertThrows(IllegalArgumentException.class,
                () -> {
                    new DocumentImpl(uriNotEmpty, nullString);
                });
        assertThrows(IllegalArgumentException.class,
                () -> {
                    new DocumentImpl(null, bytes);
                });
        assertThrows(IllegalArgumentException.class,
                () -> {
                    new DocumentImpl(null, string);
                });
        assertThrows(IllegalArgumentException.class,
                () -> {
                    new DocumentImpl(uriEmpty, string);
                });
    }
    @Test
    public void DocumentStoreImplTest() throws URISyntaxException, IOException {
        String initialString = "string";
        String initialString1 = "string1";
        String initialString2 = "string2";
        String initialString3 = "string3";
        String initialString4 = "string4";
        String initialString5 = "string5";
        String initialString6 = "string6";
        String initialString7 = "string7";
        String initialString8 = "string8";
        String initialString9 = "string9";
        String initialString13 = "string13";
        String initialString11 = "string11";
        String initialString12 = "string12";

        InputStream targetStream = new ByteArrayInputStream(initialString.getBytes());
        InputStream targetStream1 = new ByteArrayInputStream(initialString1.getBytes());
        InputStream targetStream2 = new ByteArrayInputStream(initialString2.getBytes());
        InputStream targetStream3 = new ByteArrayInputStream(initialString3.getBytes());
        InputStream targetStream4 = new ByteArrayInputStream(initialString4.getBytes());
        InputStream targetStream5 = new ByteArrayInputStream(initialString5.getBytes());
        InputStream targetStream6 = new ByteArrayInputStream(initialString6.getBytes());
        InputStream targetStream7 = new ByteArrayInputStream(initialString7.getBytes());
        InputStream targetStream8 = new ByteArrayInputStream(initialString8.getBytes());
        InputStream targetStream9 = new ByteArrayInputStream(initialString9.getBytes());
        InputStream targetStream13 = new ByteArrayInputStream(initialString13.getBytes());
        InputStream targetStream11 = new ByteArrayInputStream(initialString11.getBytes());
        InputStream targetStream12 = new ByteArrayInputStream(initialString12.getBytes());

        InputStream targetStream10 = new ByteArrayInputStream(initialString.getBytes());


        DocumentStore documentStore = new DocumentStoreImpl(null);

        String byteTxt = "https://www.HashTableImplByte.org/";
        String stringTxt = "https://www.HashTableImplString.org/";
        URI byteUri = new URI(byteTxt);
        URI stringUri = new URI(stringTxt);

        String byteTxt1 = "https://www.HashTableImplByteOne.org/";
        String stringTxt1 = "https://www.HashTableImplStringOne.org/";
        URI byteUri1 = new URI(byteTxt1);
        URI stringUri1 = new URI(stringTxt1);

        String byteTxt2 = "https://www.HashTableImplByteTwo.org/";
        String stringTxt2 = "https://www.HashTableImplStringTwo.org/";
        URI byteUri2 = new URI(byteTxt2);
        URI stringUri2 = new URI(stringTxt2);

        String byteTxt3 = "https://www.HashTableImplByteThree.org/";
        String stringTxt3 = "https://www.ThreeHashTableImplString.org/";
        URI byteUri3 = new URI(byteTxt3);
        URI stringUri3 = new URI(stringTxt3);

        String byteTxt4 = "https://www.HashTableImplByteFour.org/";
        String stringTxt4 = "https://www.HashTableImplStringFour.org/";
        URI byteUri4 = new URI(byteTxt4);
        URI stringUri4 = new URI(stringTxt4);

        String byteTxt5 = "https://www.HashTableImplByteFive.org/";
        String stringTxt5 = "https://www.HashTableImplStringFive.org/";
        URI byteUri5 = new URI(byteTxt5);
        URI stringUri5 = new URI(stringTxt5);

        String byteTxt6 = "https://www.SixHashTableImplByte.org/";
        String stringTxt6 = "https://www.SixHashTableImplString.org/";
        URI byteUri6 = new URI(byteTxt6);
        URI stringUri6 = new URI(stringTxt6);

        String byteTxt7 = "https://www.HashTableImplByteSeven.org/";
        String stringTxt7 = "https://www.HashTableImplStringSeven.org/";
        URI byteUri7 = new URI(byteTxt7);
        URI stringUri7 = new URI(stringTxt7);

        String byteTxt8 = "https://www.HashTableImplByteEight.org/";
        String stringTxt8 = "https://www.HashTableImplStringEight.org/";
        URI byteUri8 = new URI(byteTxt8);
        URI stringUri8 = new URI(stringTxt8);

        String byteTxt9 = "https://www.HashTableImplByteNine.org/";
        String stringTxt9 = "https://www.HashTableImplStringNine.org/";
        URI byteUri9 = new URI(byteTxt9);
        URI stringUri9 = new URI(stringTxt9);

        byte byteInput[] = {20,10,30,5};
        InputStream targetStreamBytes = new ByteArrayInputStream(byteInput);

        byte byteInput1[] = {20,10,30,5,6};
        InputStream targetStreamBytes1 = new ByteArrayInputStream(byteInput1);

        byte byteInput2[] = {20,10,30,5,7};
        InputStream targetStreamBytes2 = new ByteArrayInputStream(byteInput2);

        byte byteInput3[] = {20,10,30,5,8};
        InputStream targetStreamBytes3 = new ByteArrayInputStream(byteInput3);

        byte byteInput4[] = {20,10,30,5,9};
        InputStream targetStreamBytes4 = new ByteArrayInputStream(byteInput4);

        byte byteInput5[] = {20,10,30,5,5};
        InputStream targetStreamBytes5 = new ByteArrayInputStream(byteInput5);

        byte byteInput6[] = {20,10,30,5,4};
        InputStream targetStreamBytes6 = new ByteArrayInputStream(byteInput6);

        byte byteInput7[] = {20,10,30,5,3};
        InputStream targetStreamBytes7 = new ByteArrayInputStream(byteInput7);

        byte byteInput8[] = {20,10,30,5,2};
        InputStream targetStreamBytes8 = new ByteArrayInputStream(byteInput8);

        byte byteInput9[] = {20,10,30,5,1};
        InputStream targetStreamBytes9 = new ByteArrayInputStream(byteInput9);


        byte byteInput10[] = {20,10,30,5};
        InputStream targetStreamBytes10 = new ByteArrayInputStream(byteInput10);



        int x = documentStore.putDocument(targetStream, stringUri, DocumentStore.DocumentFormat.TXT);
        assertEquals(x,0);
        int y = documentStore.putDocument(targetStreamBytes, byteUri, DocumentStore.DocumentFormat.TXT);
        assertEquals(y,0);

        //int previousDocHashcode = documentStore.getDocument(stringUri).hashCode();

        int z = documentStore.putDocument(targetStream1, stringUri1, DocumentStore.DocumentFormat.TXT);
        assertEquals(z, 0);

        //int previousDocHashcode1 = documentStore.getDocument(stringUri1).hashCode();

        int zz = documentStore.putDocument(targetStream2, stringUri2, DocumentStore.DocumentFormat.TXT);
        assertEquals(0, zz);

        int zzzss = documentStore.putDocument(targetStream8, stringUri8, DocumentStore.DocumentFormat.TXT);
        assertEquals(0, zzzss);

        int zzzq = documentStore.putDocument(targetStream3, stringUri3, DocumentStore.DocumentFormat.TXT);
        assertEquals(0, zzzq);

        int zzzw = documentStore.putDocument(targetStream4, stringUri4, DocumentStore.DocumentFormat.TXT);
        assertEquals(0, zzzw);

        int zzze = documentStore.putDocument(targetStream5, stringUri5, DocumentStore.DocumentFormat.TXT);
        assertEquals(0, zzze);

        int zzzs = documentStore.putDocument(targetStream6, stringUri6, DocumentStore.DocumentFormat.TXT);
        assertEquals(0, zzzs);

        int zzza = documentStore.putDocument(targetStream7, stringUri7, DocumentStore.DocumentFormat.TXT);
        assertEquals(0, zzza);



        assertEquals(documentStore.getDocument(stringUri8).getDocumentTxt(), "string8");
        assertEquals(documentStore.getDocument(stringUri7).getDocumentTxt(), "string7");
        assertEquals(documentStore.getDocument(stringUri6).getDocumentTxt(), "string6");
        assertEquals(documentStore.getDocument(stringUri5).getDocumentTxt(), "string5");
        assertEquals(documentStore.getDocument(stringUri4).getDocumentTxt(), "string4");
        assertEquals(documentStore.getDocument(stringUri3).getDocumentTxt(), "string3");
        assertEquals(documentStore.getDocument(stringUri2).getDocumentTxt(), "string2");
        assertEquals(documentStore.getDocument(stringUri1).getDocumentTxt(), "string1");
        assertEquals(documentStore.getDocument(stringUri).getDocumentTxt(), "string");


        //assertEquals(documentStore.getDocument(stringUri1).getDocumentTxt(), "string7");
        //int qqqq = documentStore.getDocument(stringUri1).hashCode();
        int asdg = documentStore.putDocument(targetStream12, stringUri7, DocumentStore.DocumentFormat.TXT);
        //assertEquals(asdg, qqqq);
        assertEquals(documentStore.getDocument(stringUri7).getDocumentTxt(), "string12");

        int wwww = documentStore.getDocument(stringUri7).hashCode();
        assertEquals(documentStore.putDocument(targetStream11, stringUri7, DocumentStore.DocumentFormat.TXT), wwww);

        assertEquals(documentStore.getDocument(stringUri7).getDocumentTxt(), "string11");


        int wwwwq = documentStore.getDocument(stringUri7).hashCode();
        assertEquals(documentStore.putDocument(targetStream13, stringUri7, DocumentStore.DocumentFormat.TXT), wwwwq);
        assertEquals(documentStore.getDocument(stringUri7).getDocumentTxt(), "string13");


    }
//    //avi radinsky tests
//    @Test
//    public void hashTableImplSimplePutAndGet() {
//        HashTable<Integer, Integer> hashTable = new HashTableImpl<Integer, Integer>();
//        hashTable.put(1, 2);
//        hashTable.put(3, 6);
//        hashTable.put(7, 14);
//        int x = hashTable.get(1);
//        int y = hashTable.get(3);
//        int z = hashTable.get(7);
//        assertEquals(2, x);
//        assertEquals(6, y);
//        assertEquals(14, z);
//
//    }
//
//    @Test
//    public void hashTableImplALotOfInfoTest() {
//        HashTable<Integer, Integer> hashTable = new HashTableImpl<Integer, Integer>();
//        for (int i = 0; i < 1000; i++) {
//            hashTable.put(i, 2 * i);
//        }
//
//        int aa = hashTable.get(450);
//        assertEquals(900, aa);
//        p("passed Test: hashTableImplALotOfInfoTest");
//    }
//
//    @Test
//    public void hashTableImplCollisionTest() {
//        HashTable<Integer, Integer> hashTable = new HashTableImpl<Integer, Integer>();
//        hashTable.put(1, 9);
//        hashTable.put(6, 12);
//        hashTable.put(11, 22);
//        int a = hashTable.get(1);
//        int b = hashTable.get(6);
//        int c = hashTable.get(11);
//        assertEquals(9, a);
//        assertEquals(12, b);
//        assertEquals(22, c);
//        p("passed Test: hashTableImplCollisionTest");
//    }
//
//    @Test
//    public void hashTableImplReplacementTest() {
//        HashTable<Integer, Integer> hashTable = new HashTableImpl<Integer, Integer>();
//        hashTable.put(1, 2);
//        int a = hashTable.put(1, 3);
//        assertEquals(2, a);
//        int b = hashTable.put(1, 4);
//        assertEquals(3, b);
//        int c = hashTable.put(1, 9);
//        assertEquals(4, c);
//        p("passed Test: hashTableImplReplacementTest");
//    }
//
//    @Test
//    public void hashTableImplReplacementTest2() {
//        HashTable<Integer, Integer> hashTable = new HashTableImpl<Integer, Integer>();
//        hashTable.put(1, 2);
//        p("passed Test: hashTableImplReplacementTest2");
//    }

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
//        p("passed Test: docTest");
//    }

//    @Test
//    public void basicCollision() {
//        HashTable<Integer, String> hashTable = new HashTableImpl<Integer, String>();
//        hashTable.put(1, "Avi");
//        hashTable.put(5, "dinsky");
//        hashTable.put(6, "Radinsky");
//        hashTable.put(11, "gami");
//        assertEquals("gami", hashTable.put(11, "gthir"));
//        assertEquals("gthir", hashTable.get(11));
//        assertEquals("Avi", hashTable.get(1));
//        assertEquals("Radinsky", hashTable.get(6));
//        p("passed Test: basicCollision");
//    }
//    private void p(String s) {
//        System.out.println(s);
//    }

    @Test
    public void testPutNullDeletion() throws IOException {
        DocumentStore documentStore = new DocumentStoreImpl(null);
        String string1 = "It was a dark and stormy night";
        String string2 = "It was the best of times, it was the worst of times";
        String string3 = "It was a bright cold day in April, and the clocks were striking thirteen";
        String string4 = "I am free, no matter what rules surround me.";
        byte[] bytes3 = string3.getBytes();
        byte[] bytes4 = string4.getBytes();
        InputStream inputStream1 = new ByteArrayInputStream(string1.getBytes());
        InputStream inputStream2 = new ByteArrayInputStream(string2.getBytes());
        InputStream inputStream3 = new ByteArrayInputStream(bytes3);
        InputStream inputStream4 = new ByteArrayInputStream(bytes4);
        URI uri1 =  URI.create("www.wrinkleintime.com");
        URI uri2 =  URI.create("www.taleoftwocities.com");
        URI uri3 =  URI.create("www.1984.com");
        URI uri4 =  URI.create("www.themoonisaharshmistress.com");
        int putTXT1 = documentStore.putDocument(inputStream1,uri1,DocumentStore.DocumentFormat.TXT);
        int putTXT2 = documentStore.putDocument(inputStream2,uri2,DocumentStore.DocumentFormat.TXT);
        int putBINARY1 = documentStore.putDocument(inputStream3,uri3,DocumentStore.DocumentFormat.BINARY);
        int putBINARY2 = documentStore.putDocument(inputStream4,uri4,DocumentStore.DocumentFormat.BINARY);
        assertEquals(putTXT1,0);
        assertEquals(putTXT2,0);
        assertEquals(putBINARY1,0);
        assertEquals(putBINARY2,0);
        documentStore.putDocument(null,uri1,DocumentStore.DocumentFormat.TXT);
        documentStore.putDocument(null,uri2,DocumentStore.DocumentFormat.TXT);
        documentStore.putDocument(null,uri3,DocumentStore.DocumentFormat.BINARY);
        documentStore.putDocument(null,uri4,DocumentStore.DocumentFormat.BINARY);
        Document nullDoc1 = documentStore.getDocument(uri1);
        Document nullDoc2 = documentStore.getDocument(uri2);
        Document nullDoc3 = documentStore.getDocument(uri3);
        Document nullDoc4 = documentStore.getDocument(uri4);
        assertEquals(nullDoc1,null);
        assertEquals(nullDoc2,null);
        assertEquals(nullDoc3,null);
        assertEquals(nullDoc4,null);
    }
    @Test
    public void testSimplePutValues() throws IOException {
        DocumentStore documentStore = new DocumentStoreImpl(null);
        String string1 = "It was a dark and stormy night";
        String string2 = "It was the best of times, it was the worst of times";
        String string3 = "It was a bright cold day in April, and the clocks were striking thirteen";
        String string4 = "I am free, no matter what rules surround me.";
        byte[] bytes3 = string3.getBytes();
        byte[] bytes4 = string4.getBytes();
        InputStream inputStream1 = new ByteArrayInputStream(string1.getBytes());
        InputStream inputStream2 = new ByteArrayInputStream(string2.getBytes());
        InputStream inputStream3 = new ByteArrayInputStream(bytes3);
        InputStream inputStream4 = new ByteArrayInputStream(bytes4);
        URI uri1 =  URI.create("www.wrinkleintime.com");
        URI uri2 =  URI.create("www.taleoftwocities.com");
        URI uri3 =  URI.create("www.1984.com");
        URI uri4 =  URI.create("www.themoonisaharshmistress.com");
        int putTXT1 = documentStore.putDocument(inputStream1,uri1,DocumentStore.DocumentFormat.TXT);
        int putTXT2 = documentStore.putDocument(inputStream2,uri2,DocumentStore.DocumentFormat.TXT);
        int putBINARY1 = documentStore.putDocument(inputStream3,uri3,DocumentStore.DocumentFormat.BINARY);
        int putBINARY2 = documentStore.putDocument(inputStream4,uri4,DocumentStore.DocumentFormat.BINARY);
        assertEquals(putTXT1,0);
        assertEquals(putTXT2,0);
        assertEquals(putBINARY1,0);
        assertEquals(putBINARY2,0);
    }
    @Test
    public void testCollisionPutValues() throws IOException {
        DocumentStore documentStore = new DocumentStoreImpl(null);
        String string1 = "It was a dark and stormy night";
        String string2 = "It was the best of times, it was the worst of times";
        String string3 = "It was a bright cold day in April, and the clocks were striking thirteen";
        String string4 = "I am free, no matter what rules surround me.";
        byte[] bytes3 = string3.getBytes();
        byte[] bytes4 = string4.getBytes();
        InputStream inputStream1 = new ByteArrayInputStream(string1.getBytes());
        InputStream inputStream2 = new ByteArrayInputStream(string2.getBytes());
        InputStream inputStream3 = new ByteArrayInputStream(bytes3);
        InputStream inputStream4 = new ByteArrayInputStream(bytes4);
        URI uri1 =  URI.create("www.wrinkleintime.com");
        URI uri2 =  URI.create("www.taleoftwocities.com");
        URI uri3 =  URI.create("www.1984.com");
        URI uri4 =  URI.create("www.themoonisaharshmistress.com");
        documentStore.putDocument(inputStream1,uri1,DocumentStore.DocumentFormat.TXT);
        int putTXT2 = documentStore.putDocument(inputStream2,uri2,DocumentStore.DocumentFormat.TXT);
        int putBINARY1 = documentStore.putDocument(inputStream3,uri3,DocumentStore.DocumentFormat.BINARY);
        int putBINARY2 = documentStore.putDocument(inputStream4,uri4,DocumentStore.DocumentFormat.BINARY);
        int test1 = documentStore.getDocument(uri1).hashCode();
        int test2 = documentStore.getDocument(uri2).hashCode();
        int test3 = documentStore.getDocument(uri3).hashCode();
        int test4 = documentStore.getDocument(uri4).hashCode();
        InputStream inputStream1b = new ByteArrayInputStream(string1.getBytes());
        InputStream inputStream2b = new ByteArrayInputStream(string2.getBytes());
        InputStream inputStream3b = new ByteArrayInputStream(bytes3);
        InputStream inputStream4b = new ByteArrayInputStream(bytes4);
        int putBINARY1Collision = documentStore.putDocument(inputStream1b,uri1,DocumentStore.DocumentFormat.BINARY);
        int putBINARY2Collision = documentStore.putDocument(inputStream2b,uri2,DocumentStore.DocumentFormat.BINARY);
        int putTXT1Collision = documentStore.putDocument(inputStream3b,uri3,DocumentStore.DocumentFormat.TXT);
        int putTXT2Collision = documentStore.putDocument(inputStream4b,uri4,DocumentStore.DocumentFormat.TXT);
        Document document1 = new DocumentImpl(uri1, string1.getBytes());
        Document document2 = new DocumentImpl(uri2, string2.getBytes());
        Document document3 = new DocumentImpl(uri3, string3);
        Document document4 = new DocumentImpl(uri4, string4);
        assertEquals(putBINARY1Collision,test1);
        assertEquals(putBINARY2Collision,test2);
        assertEquals(putTXT1Collision,test3);
        assertEquals(putTXT2Collision,test4);
    }

}