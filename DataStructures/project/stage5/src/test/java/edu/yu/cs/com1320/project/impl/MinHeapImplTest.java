package edu.yu.cs.com1320.project.impl;

import edu.yu.cs.com1320.project.stage5.Document;
import edu.yu.cs.com1320.project.stage5.impl.DocumentImpl;
import org.junit.Test;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.*;

public class MinHeapImplTest {
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

    private String txt6 = "Penguin Park Piccalo Pants Pain Possum and I'm here to STAY! awawawawawawawawawawawawawawawawawawawawawawaewaeaeaeaesrewarewsewrarewarewserwsercrcxvftvhgdbkjdbnwucbnuhfruynbwxuyfizwmbwfyxnuybnregh byugcnbgyxfrebygxbfrmuyzbvuybmfrbubfvubvexuybmzybmfubvmugbxmnxuvbrvnubnruzbuxyrewyhrfmuyzhbnuycbyftxbjnuynbxchgwybnxnbergrhghyrhxfytreuhxyhuxgccyhuebcgyhbwbfgyhegfddyhfgyehdfgyehbfgdhefdgyehedfgdhbvgyhbggyhbgvyhdbfgyhuefcgyhubfgeyehudxcgyhxucnhyunbxcghnbcbghjbcghbcghcfghbcghbcbghnbcgbhbcghnbcfghnbcfghbfcghnbcfghbcfghbghbcfghbgcfgdhbcf";
    @Test
    public void testHeap() throws IOException {
        //whole test turns out to be unneccesary bc of piazza 364
        System.out.println("txt1 bytes = " + txt1.getBytes().length);
        System.out.println("txt2 bytes = " + txt2.getBytes().length);
        System.out.println("txt3 bytes = " + txt3.getBytes().length);
        System.out.println("txt4 bytes = " + txt4.getBytes().length);
        System.out.println("txt5 bytes = " + txt5.getBytes().length);
        System.out.println("txt6 bytes = " + txt6.getBytes().length);
        MinHeapImpl<Document> heap = new MinHeapImpl<>();
        DocumentImpl doc1 = new DocumentImpl(this.uri1,this.txt1);
        doc1.setLastUseTime(System.nanoTime());
        heap.insert(doc1);
        DocumentImpl doc2 = new DocumentImpl(this.uri2,this.txt2);
        doc2.setLastUseTime(System.nanoTime());
        heap.insert(doc2);
        DocumentImpl doc3 = new DocumentImpl(this.uri3,this.txt3);
        doc3.setLastUseTime(System.nanoTime());
        heap.insert(doc3);
        DocumentImpl doc4 = new DocumentImpl(this.uri4,this.txt4);
        doc4.setLastUseTime(System.nanoTime());
        heap.insert(doc4);
        DocumentImpl doc5 = new DocumentImpl(this.uri5,this.txt5);
        doc5.setLastUseTime(System.nanoTime());
        heap.insert(doc5);
        DocumentImpl doc6 = new DocumentImpl(this.uri6,this.txt6);
        doc6.setLastUseTime(System.nanoTime());
        heap.insert(doc6);
        assertEquals(1,heap.getArrayIndex(doc1));
        doc1.setLastUseTime(System.nanoTime());
        heap.reHeapify(doc1);
        assertEquals(1,heap.getArrayIndex(doc2));
        heap.remove();
        assertEquals(1,heap.getArrayIndex(doc3));

        //Assertheap.remove();
//        DocumentStore store = new DocumentStoreImpl();
//        store.putDocument(new ByteArrayInputStream(this.txt1.getBytes()),this.uri1, DocumentStore.DocumentFormat.TXT);
//        store.putDocument(new ByteArrayInputStream(this.txt2.getBytes()),this.uri1, DocumentStore.DocumentFormat.TXT);
//        store.putDocument(new ByteArrayInputStream(this.txt3.getBytes()),this.uri1, DocumentStore.DocumentFormat.TXT);
//        store.setMaxDocumentCount(1);
//        store.putDocument(new ByteArrayInputStream(this.txt4.getBytes()),this.uri4, DocumentStore.DocumentFormat.TXT);

    }

}