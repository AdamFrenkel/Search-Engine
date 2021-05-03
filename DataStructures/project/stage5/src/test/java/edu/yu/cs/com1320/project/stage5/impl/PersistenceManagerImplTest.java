package edu.yu.cs.com1320.project.stage5.impl;

import edu.yu.cs.com1320.project.stage5.Document;
import org.junit.Test;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.*;

public class PersistenceManagerImplTest {
    private URI uri4;

    { try {
        uri4 = new URI("http://www.yu.edu/documents/doc6");
    } catch (URISyntaxException e) {
        e.printStackTrace(); }
    }
    //uri1 = new URI("http://edu.yu.cs/com1320/project/doc1");
    private String txt4 = "This is the 6th doc, and it's here to stay!";
    //variables to hold possible values for doc2
    @Test
    public void testBasic(){
        Document document1 = new DocumentImpl(uri4, txt4);
        PersistenceManagerImpl pm = new PersistenceManagerImpl();
        try {
            pm.serialize(uri4, document1);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}