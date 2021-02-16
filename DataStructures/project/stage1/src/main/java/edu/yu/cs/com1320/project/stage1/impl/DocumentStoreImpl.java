package edu.yu.cs.com1320.project.stage1.impl;

import edu.yu.cs.com1320.project.impl.HashTableImpl;
import edu.yu.cs.com1320.project.stage1.Document;
import edu.yu.cs.com1320.project.stage1.DocumentStore;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;

public class DocumentStoreImpl implements DocumentStore {

    private HashTableImpl<URI, Document> hashTable = new HashTableImpl<>();

    /**
     * the two document formats supported by this document store.
     * Note that TXT means plain text, i.e. a String.
     */
    public enum DocumentFormat{
        TXT,BINARY
    };
    /**
     * @param input the document being put
     * @param uri unique identifier for the document
     * @param format indicates which type of document format is being passed
     * @return if there is no previous doc at the given URI, return 0. If there is a previous doc,
     * return the hashCode of the String version of the previous doc. If InputStream is null, this is a delete,
     * and thus return either the hashCode of the deleted doc or 0 if there is no doc to delete.
     */
    public int putDocument(InputStream input, URI uri, DocumentStore.DocumentFormat format) throws IOException {
        //***Read the entire contents of the document from the InputStream into a byte[] ****Don't know how to do this
        DocumentImpl docReturn;
        byte[] bD = input.readAllBytes();
        DocumentImpl doc;
        switch(format){
            case TXT:
                String txt = new String(bD);
                doc = new DocumentImpl(uri, txt);
                docReturn = (DocumentImpl)hashTable.put(uri, doc);
                if(docReturn == null){
                    return 0;
                }else{
                    return docReturn.hashCode();
                }
            case BINARY:
                doc = new DocumentImpl(uri, bD);
                docReturn = (DocumentImpl)hashTable.put(uri, doc);
                if(docReturn == null){
                    return 0;
                }else{
                    return docReturn.hashCode();
                }
            default:
                throw new IllegalArgumentException("format must be either TXT or BINARY.");
        }
    }

    /**
     * @param uri the unique identifier of the document to get
     * @return the given document
     */
    public Document getDocument(URI uri){
        return (Document) hashTable.get(uri);
    }

    /**
     * @param uri the unique identifier of the document to delete
     * @return true if the document is deleted, false if no document exists with that URI
     */
    public boolean deleteDocument(URI uri){
        if(hashTable.put(uri, null) == null){
            return false;
        }
        return true;
    }

}
