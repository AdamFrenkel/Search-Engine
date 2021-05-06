package edu.yu.cs.com1320.project.stage5.impl;

import edu.yu.cs.com1320.project.stage5.Document;
import edu.yu.cs.com1320.project.stage5.DocumentStore;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class PersistenceManagerImplTest {
    private URI uri4;
    String str1 = "1";
    byte[] array1 = str1.getBytes();
    ByteArrayInputStream stream11 = new ByteArrayInputStream(array1);
    URI uri1;

    {
        try {
            uri1 = new URI("http://www.yu.edu/documents/extra3/tester/tester2/doc10");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }
    Document binaryDoc = new DocumentImpl(uri1, stream11.readAllBytes());
    { try {
        uri4 = new URI("http://www.yu.edu/documents/extra3/doc9");
    } catch (URISyntaxException e) {
        e.printStackTrace(); }
    }
    //uri1 = new URI("http://edu.yu.cs/com1320/project/doc1");
    private String txt4 = "This is the 7th 7th doc, and it's here to stay!";
    PersistenceManagerImpl pm = new PersistenceManagerImpl(null);
    //variables to hold possible values for doc2
    @Test
    public void testSerealize(){
        Document document1 = new DocumentImpl(uri4, txt4);
        try {
            pm.serialize(uri4, document1);
        } catch (IOException e) {
            e.printStackTrace();
        }
       // System.out.println("og array:" + Arrays.toString(array1));
        try {
            pm.serialize(uri1, binaryDoc);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    @Test
    public void testDeserealize(){
        try {
            pm.deserialize(uri4);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            pm.deserialize(uri1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}