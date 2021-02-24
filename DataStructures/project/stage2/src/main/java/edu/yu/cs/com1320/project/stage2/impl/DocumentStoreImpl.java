package edu.yu.cs.com1320.project.stage2.impl;


import edu.yu.cs.com1320.project.Command;
import edu.yu.cs.com1320.project.impl.HashTableImpl;
import edu.yu.cs.com1320.project.impl.StackImpl;
import edu.yu.cs.com1320.project.stage2.Document;
import edu.yu.cs.com1320.project.stage2.DocumentStore;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.function.Function;

public class DocumentStoreImpl implements DocumentStore {
    private StackImpl stack = new StackImpl();
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
    @Override
    public int putDocument(InputStream input, URI uri, DocumentStore.DocumentFormat format) throws IOException {
        Function<URI,Boolean> function= uri1 -> this.undoPutDocument(uri1);
        stack.push(new Command(uri, function));
        DocumentImpl docReturn;
        if (input == null) {
            docReturn = (DocumentImpl)hashTable.put(uri, null);
            return docReturn == null ? 0 : docReturn.hashCode();
        }

        byte[] bD = input.readAllBytes();
        DocumentImpl doc;
        switch(format){
            case TXT:
                String txt = new String(bD);
                doc = new DocumentImpl(uri, txt);
                docReturn = (DocumentImpl)hashTable.put(uri, doc);
                return docReturn == null ? 0 : docReturn.hashCode();
            case BINARY:
                doc = new DocumentImpl(uri, bD);
                docReturn = (DocumentImpl)hashTable.put(uri, doc);
                return docReturn == null ? 0 : docReturn.hashCode();
            default:
                throw new IllegalArgumentException("format must be either TXT or BINARY.");
        }
    }

    private Boolean undoPutDocument(URI uri) {
        if(hashTable.put(uri, null) == null){
            return false;
        }
        return true;
    }

    /**
     * @param uri the unique identifier of the document to get
     * @return the given document
     */
    @Override
    public Document getDocument(URI uri){
        return (Document) hashTable.get(uri);
    }

    /**
     * @param uri the unique identifier of the document to delete
     * @return true if the document is deleted, false if no document exists with that URI
     */
    @Override
    public boolean deleteDocument(URI uri){
        Function<URI,Boolean> function= new Function<URI, Boolean>() {
            @Override
            public Boolean apply(URI uri1) {
                return DocumentStoreImpl.this.undoDeleteDocument(uri1);
            }
        };
        stack.push(new Command(uri, function));
        if(hashTable.put(uri, null) == null){
            return false;
        }
        return true;
    }

    private Boolean undoDeleteDocument(URI uri) {
        DocumentImpl doc= (DocumentImpl) this.getDocument(uri);
        String txt = doc.getDocumentTxt();
        i

        return null;
    }

    /**
     * undo the last put or delete command
     * @throws IllegalStateException if there are no actions to be undone, i.e. the command stack is empty
     */
    @Override
    public void undo() throws IllegalStateException {

    }

    /**
     * undo the last put or delete that was done with the given URI as its key
     * @param uri
     * @throws IllegalStateException if there are no actions on the command stack for the given URI
     */
    @Override
    public void undo(URI uri) throws IllegalStateException {

    }

}
