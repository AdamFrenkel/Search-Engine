package edu.yu.cs.com1320.project.stage1.impl;

import edu.yu.cs.com1320.project.stage1.Document;
import edu.yu.cs.com1320.project.stage1.DocumentStore;
import org.junit.jupiter.api.Test;


import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.*;

class DocumentStoreImplTest {
    @Test
    void testPutDocumentStoreAsText() throws URISyntaxException {
        DocumentStore documentStore = new DocumentStoreImpl();
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
    void testPutDocumentStoreAsBinary() throws URISyntaxException {
        DocumentStore documentStore = new DocumentStoreImpl();
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
    void testThrowException() {
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

}