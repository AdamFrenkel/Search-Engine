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
    private StackImpl commandStack = new StackImpl();
    private HashTableImpl<URI, Document> hashTable = new HashTableImpl<>();
    private HashTableImpl<URI, Document> deletedDocsHT = new HashTableImpl<>();
    private HashTableImpl<URI, Document> replacedDocsHT = new HashTableImpl<>(); //This will map uris of the OG doc to the doc repaced by it
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
        //First Piece is a delete
        DocumentImpl docReturn;
        if (input == null) {
            Function<URI,Boolean> undoDeleteFunction = uri1 -> this.undoDeleteDocument(uri1);
            commandStack.push(new Command(uri, undoDeleteFunction));
            docReturn = (DocumentImpl)hashTable.put(uri, null);
            deletedDocsHT.put(uri, docReturn);//Remember this can be null
            return docReturn == null ? 0 : docReturn.hashCode();
        }
        //Second piece is an add
        byte[] bD = input.readAllBytes();
        DocumentImpl doc;
        if(format == DocumentStore.DocumentFormat.TXT) {
            String txt = new String(bD);
            doc = new DocumentImpl(uri, txt);
        }else{
                doc = new DocumentImpl(uri, bD);
        }
        docReturn = (DocumentImpl)hashTable.put(uri, doc);
        if(docReturn == null) {
            Function<URI,Boolean> undoPutFunction= uri1 -> this.undoPutDocument(uri1);
            commandStack.push(new Command(uri, undoPutFunction));
            return 0;
        }else{
            replacedDocsHT.put(uri, docReturn); //This isn't docReturn's uri, because wont have access to that when trying to undo
            Function<URI,Boolean> undoReplaceFunction= uri1 -> this.undoReplaceDocument(uri1);
            return docReturn.hashCode();
        }
    }

    private Boolean undoReplaceDocument(URI uri1) {
        hashTable.put(uri1, replacedDocsHT.get(uri1));
        return true;
    }

    private Boolean undoPutDocument(URI uri1) {
        if(hashTable.put(uri1, null) == null){
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
        Function<URI,Boolean> function= undoDel -> DocumentStoreImpl.this.undoDeleteDocument(uri);
        commandStack.push(new Command(uri, function));
        DocumentImpl doc = (DocumentImpl) hashTable.put(uri, null);
        if(doc == null){
            return false;
        }
        return true;
    }

    private Boolean undoDeleteDocument(URI uri1) {
        DocumentImpl doc = (DocumentImpl) deletedDocsHT.get(uri1);
        hashTable.put(uri1,doc);
        return true;
    }

    /**
     * undo the last put or delete command
     * @throws IllegalStateException if there are no actions to be undone, i.e. the command stack is empty
     */
    @Override
    public void undo() throws IllegalStateException {
        if(commandStack.size()>0){
            Command undoCommand = (Command) commandStack.pop();
            undoCommand.undo();
        }else{
            throw new IllegalStateException("there are no actions to be undone");
        }

    }

    /**
     * undo the last put or delete that was done with the given URI as its key
     * @param uri
     * @throws IllegalStateException if there are no actions on the command stack for the given URI
     */
    @Override
    public void undo(URI uri) throws IllegalStateException {
        Boolean haventFoundURI = true;
        StackImpl holderStack = new StackImpl();
        while(haventFoundURI){
            if(commandStack.size()>0){
                Command currentCommand = (Command) commandStack.pop();
                if(currentCommand.getUri() == uri){
                    currentCommand.undo();
                    haventFoundURI = false;
                }
                holderStack.push(currentCommand);
            }else{
                throw new IllegalStateException("there are no actions on the command stack for the given URI");
            }
        }
        while (holderStack.size()>0){
            commandStack.push(holderStack.pop());
        }
    }
}
