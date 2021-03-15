package edu.yu.cs.com1320.project.stage3.impl;


import edu.yu.cs.com1320.project.CommandSet;
import edu.yu.cs.com1320.project.impl.HashTableImpl;
import edu.yu.cs.com1320.project.impl.StackImpl;
import edu.yu.cs.com1320.project.impl.TrieImpl;
import edu.yu.cs.com1320.project.stage3.Document;
import edu.yu.cs.com1320.project.stage3.DocumentStore;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

public class DocumentStoreImpl implements DocumentStore {
    private StackImpl commandStack = new StackImpl();
    private HashTableImpl<URI, Document> hashTable = new HashTableImpl<>();
    private HashTableImpl<URI, StackImpl> deletedDocsHT = new HashTableImpl<>();
    private HashTableImpl<URI, StackImpl> replacedDocsHT = new HashTableImpl<>(); //This will map uris of the OG doc to the doc repaced by it
    private TrieImpl<Document> trie = new TrieImpl<>();

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
        if(uri == null || format == null){
            throw new IllegalArgumentException("uri or format is null");
        }
        //First Piece is a delete
        DocumentImpl docReturn;
        if (input == null) {
            docReturn = (DocumentImpl)hashTable.put(uri, null);
            this.deleteFromPut(uri, docReturn);
            if(docReturn != null){
                //new stuff for stage 3
                Set<String> words = docReturn.getWords();
                for(String w : words){
                    trie.delete(w, docReturn);
                }
            }
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
        //new stuff for stage 3
        Set<String> words = docReturn.getWords();
        for(String w : words){
            trie.put(w, docReturn);
        }
        return this.putFromPut(uri,docReturn);
    }

    //Whole purpose is to make put method shorter
    private int putFromPut(URI uri,DocumentImpl docReturn){
        if(docReturn == null) {
            //These next few lines are need for undo purposes
            Function<URI,Boolean> undoPutFunction = uri1 -> this.undoPutDocument(uri1);
            commandStack.push(new Command(uri, undoPutFunction));
            //These previous few lines are need for undo purposes
            return 0;
        }else{
            if(replacedDocsHT.get(uri) == null){
                StackImpl<DocumentImpl> newStack = new StackImpl<>();
                replacedDocsHT.put(uri, newStack);
            }
            StackImpl<DocumentImpl> stack = replacedDocsHT.get(uri);   //This isn't docReturn's uri, because wont have access to that when trying to undo
            stack.push(docReturn);//Remember this can be null
            Function<URI,Boolean> undoReplaceFunction= uri1 -> this.undoReplaceDocument(uri1);
            commandStack.push(new Command(uri, undoReplaceFunction));
            return docReturn.hashCode();
        }
    }

    //Whole purpose is to make put method shorter
    private void deleteFromPut(URI uri,DocumentImpl docReturn){
        //These next few lines are need for undo purposes
        if(!(deletedDocsHT.get(uri) instanceof StackImpl)){
            StackImpl<DocumentImpl> newStack = new StackImpl<>();
            deletedDocsHT.put(uri, newStack);
        }
        StackImpl<DocumentImpl> stack = deletedDocsHT.get(uri);
        stack.push(docReturn);//Remember this can be null
        Function<URI,Boolean> undoDeleteFunction = uri1 -> this.undoDeleteDocument(uri1);
        commandStack.push(new Command(uri, undoDeleteFunction));
        //These previous few lines are need for undo purposes
    }



    private Boolean undoReplaceDocument(URI uri1) {
        if(uri1 == null){
            throw new IllegalArgumentException("Tried to undo a null URI");
        }
        hashTable.put(uri1, (DocumentImpl) (replacedDocsHT.get(uri1).pop()));
        return true;
    }

    private Boolean undoPutDocument(URI uri1) {
        if(uri1 == null){
            throw new IllegalArgumentException("Tried to undo a null URI");
        }
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
        if(uri == null){
            throw new IllegalArgumentException("Tried to get a null URI");
        }
        return (Document) hashTable.get(uri);
    }

    /**
     * @param uri the unique identifier of the document to delete
     * @return true if the document is deleted, false if no document exists with that URI
     */
    @Override
    public boolean deleteDocument(URI uri){
        if(uri == null){
            throw new IllegalArgumentException("Tried to delete a null URI");
        }
        DocumentImpl doc = (DocumentImpl) hashTable.put(uri, null);
        //These next few lines are all needed for undo purposes
        if(deletedDocsHT.get(uri) == null){
            StackImpl<DocumentImpl> newStack = new StackImpl<>();
            deletedDocsHT.put(uri, newStack);
        }
        StackImpl<DocumentImpl> stack = deletedDocsHT.get(uri);

        stack.push(doc);//Remember this can be null
        Function<URI,Boolean> undoDeleteFunction= uri1 -> this.undoDeleteDocument(uri1);
        commandStack.push(new Command(uri, undoDeleteFunction));
        //Previous lines were needed for undo purposes
        if(doc == null){
            return false;
        }

        return true;
    }

    private Boolean undoDeleteDocument(URI uri1) {
        if(uri1 == null){
            throw new IllegalArgumentException("Tried to undo a null URI");
        }
        DocumentImpl doc = (DocumentImpl) (deletedDocsHT.get(uri1).pop());
        hashTable.put(uri1, doc);
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
        if(uri == null){
            throw new IllegalArgumentException("Tried to undo a null URI");
        }
        Boolean haventFoundURI = true;
        StackImpl holderStack = new StackImpl();
        while(haventFoundURI){
            if(commandStack.size()>0){
                Command currentCommand = (Command) commandStack.pop();
                if(currentCommand.getUri() == uri){
                    currentCommand.undo();
                    haventFoundURI = false;
                }
                if(haventFoundURI == true) {
                    holderStack.push(currentCommand);
                }
            }else{
                while (holderStack.size()>0){
                    commandStack.push(holderStack.pop());
                }
                throw new IllegalStateException("there are no actions on the command stack for the given URI");
            }
        }
        while (holderStack.size()>0){
            commandStack.push(holderStack.pop());
        }
    }



    @Override
    public List<Document> search(String keyword) {
        Comparator<Document> documentComparator = new Comparator<Document>( ) {
            @Override
            public int compare(Document d1, Document d2) {
                if(d1.wordCount(keyword)> d2.wordCount(keyword)){
                    return 1;
                }
                if(d1.wordCount(keyword)< d2.wordCount(keyword)){
                    return -1;
                }else{
                    return 0;
                }
            }
        };
        return trie.getAllSorted(keyword,documentComparator); //Forgot how to make comparator
    }

    @Override
    public List<Document> searchByPrefix(String keywordPrefix) {
        Comparator<Document> documentComparator = new Comparator<Document>( ) {
            @Override
            public int compare(Document d1, Document d2) {
                if(d1.wordCount(keywordPrefix)> d2.wordCount(keywordPrefix)){
                    return 1;
                }
                if(d1.wordCount(keywordPrefix)< d2.wordCount(keywordPrefix)){
                    return -1;
                }else{
                    return 0;
                }
            }
        };
        return trie.getAllWithPrefixSorted(keywordPrefix, documentComparator);
    }

    @Override
    public Set<URI> deleteAll(String keyword) {
        Set<Document> deletedDocs = trie.deleteAll(keyword);
        Set<URI> uris = new HashSet<>();
        for(Document d : deletedDocs){
            uris.add(d.getKey());
        }
        return uris;
    }

    @Override
    public Set<URI> deleteAllWithPrefix(String keywordPrefix) {
        Set<Document> deletedDocs = trie.deleteAllWithPrefix(keywordPrefix);
        Set<URI> uris = new HashSet<>();
        for(Document d : deletedDocs){
            uris.add(d.getKey());
        }
        return uris;
    }
}
