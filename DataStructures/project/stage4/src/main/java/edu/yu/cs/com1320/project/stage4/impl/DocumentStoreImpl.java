package edu.yu.cs.com1320.project.stage4.impl;


import edu.yu.cs.com1320.project.CommandSet;
import edu.yu.cs.com1320.project.GenericCommand;
import edu.yu.cs.com1320.project.Undoable;
import edu.yu.cs.com1320.project.impl.HashTableImpl;
import edu.yu.cs.com1320.project.impl.StackImpl;
import edu.yu.cs.com1320.project.impl.MinHeapImpl;
import edu.yu.cs.com1320.project.impl.TrieImpl;
import edu.yu.cs.com1320.project.stage4.Document;
import edu.yu.cs.com1320.project.stage4.DocumentStore;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.*;
import java.util.function.Function;

public class DocumentStoreImpl implements DocumentStore {
    private StackImpl<Undoable> commandStack = new StackImpl<>();
    private HashTableImpl<URI, Document> hashTable = new HashTableImpl<>();
    private HashTableImpl<URI, StackImpl> deletedDocsHT = new HashTableImpl<>();
    private HashTableImpl<URI, StackImpl> replacedDocsHT = new HashTableImpl<>(); //This will map uris of the OG doc to the doc repaced by it
    private TrieImpl<Document> trie = new TrieImpl<>();
    private boolean maxDocCountBoolean = false;
    private boolean maxDocBytesBoolean = false;
    private int maxDocumentCount = Integer.MAX_VALUE;
    private int maxDocumentBytes = Integer.MAX_VALUE;
    private MinHeapImpl<Document> leastUsedDocs = new MinHeapImpl();
    private int docCount = 0;
    private int docBytesAmount = 0;
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
        //new stuff for stage 4
        //putting a new doc into the heap
//        if(hashTable.get(uri) == null) {
//            leastUsedDocs.insert(doc);
//        }
        this.ensureHaveEnoughRoom(uri,bD);
        //need for a few stages
        docReturn = (DocumentImpl)hashTable.put(uri, doc);
        //new stuff for stage 3 ie: trie stuff
        putStage3(doc, docReturn);
        return this.putFromPut(uri,docReturn,format,doc);
    }
    private void ensureHaveEnoughRoom(URI uri, byte[] newBD){
        int lengthOfBdGettingReplaced = 0;
        if(hashTable.get(uri) != null){
            if(hashTable.get(uri).getDocumentTxt() != null) {
                lengthOfBdGettingReplaced = hashTable.get(uri).getDocumentTxt().getBytes().length;
            }else{
                lengthOfBdGettingReplaced = hashTable.get(uri).getDocumentBinaryData().length;
            }
        }
        //This if statement is checking to see if we have to remove a doc, before placing a new one in.  The hashtable piece is checking if this is just a replace in which case we don't need to worry about going over doc limit
        while((maxDocCountBoolean && docCount + 1 > maxDocumentCount && hashTable.get(uri) == null) || (maxDocBytesBoolean && (docBytesAmount + newBD.length - lengthOfBdGettingReplaced) > maxDocumentBytes)){
            boolean haventFoundDocInStore = true;
            while(haventFoundDocInStore) {
                DocumentImpl leastUsedDoc = (DocumentImpl) leastUsedDocs.remove();
                if (hashTable.get(leastUsedDoc.getKey()) == leastUsedDoc) { //this means that the doc is in the docStore and not just in leastUsedDoc through som mistake somewhere
                    haventFoundDocInStore = false;
                    URI uriToDelete = leastUsedDoc.getKey();
                    if(uriToDelete == null){
                        throw new IllegalArgumentException("Tried to undo a null URI");
                    }
                    Document doc = hashTable.put(uriToDelete, null);
                    //gets rid of all undos related to this uri BeH
//                    if(doc == null){
//                        return false;
//                    }
                    if(doc != null) {
                        haventFoundDocInStore = false;
                        Set<String> words = doc.getWords();
                        for (String w : words) {
                            trie.delete(w, doc);
                        }
                        //stage 4 stuff
                        docCount--;
                        if(doc.getDocumentTxt() != null) {
                            docBytesAmount -= doc.getDocumentTxt().getBytes().length;
                        }else{
                            docBytesAmount -= doc.getDocumentBinaryData().length;
                        }
//                        doc.setLastUseTime(-10000);
//                        leastUsedDocs.reHeapify(doc);
//                        leastUsedDocs.remove();
                    }

                }
            }
        }
    }
    private void putStage3(Document doc, Document docReturn){
        Set<String> words = doc.getWords();
        for (String w : words) {
            w = w.replaceAll("[^a-zA-Z0-9\\s]", "");
            trie.put(w, doc);
        }
        if(docReturn != null){
            Set<String> deletedWords = docReturn.getWords();
            for (String w : deletedWords) {
                w = w.replaceAll("[^a-zA-Z0-9\\s]", "");
                trie.delete(w, docReturn);
            }
        }
    }
    //********************************NEED TO MAKE SHORTER*******************************************************
    //Whole purpose is to make put method shorter
    private int putFromPut(URI uri, DocumentImpl docReturn, DocumentFormat format, DocumentImpl doc){
        //need 1st piece for heap
        if(format == DocumentFormat.TXT) {
            docBytesAmount += doc.getDocumentTxt().getBytes().length;
        }else{
            docBytesAmount += doc.getDocumentBinaryData().length;
        }
        doc.setLastUseTime(System.nanoTime());
        leastUsedDocs.insert(doc);
        if(docReturn == null) {
            //need for heap
            docCount++;
            //These next few lines are need for undo purposes
            Function<URI,Boolean> undoPutFunction = uri1 -> this.undoPutDocument(uri1);
            commandStack.push(new GenericCommand<>(uri, undoPutFunction));
            //These previous few lines are need for undo purposes
            //stage 4 stuff
            return 0;
        }else{
            if(replacedDocsHT.get(uri) == null){
                StackImpl<DocumentImpl> newStack = new StackImpl<>();
                replacedDocsHT.put(uri, newStack);
            }
            StackImpl<DocumentImpl> stack = replacedDocsHT.get(uri);   //This isn't docReturn's uri, because wont have access to that when trying to undo
            stack.push(docReturn);//Remember this can be null
            Function<URI,Boolean> undoReplaceFunction= uri1 -> this.undoReplaceDocument(uri1);
            commandStack.push(new GenericCommand<>(uri, undoReplaceFunction));
            //need for heap
            if(docReturn.getDocumentTxt() != null){
                docBytesAmount -= docReturn.getDocumentTxt().getBytes().length;
            }else{
                docBytesAmount -= docReturn.getDocumentBinaryData().length;
            }
            docReturn.setLastUseTime(-10000);
            leastUsedDocs.reHeapify(docReturn);
            leastUsedDocs.remove();
            return docReturn.hashCode();
        }
    }

    //Whole purpose is to make put method shorter
    private void deleteFromPut(URI uri,DocumentImpl docReturn){
        //need for heap
        if(docReturn != null){
            docCount--;
            if(docReturn.getDocumentTxt() != null){
                docBytesAmount -= docReturn.getDocumentTxt().getBytes().length;
            }else{
                docBytesAmount -= docReturn.getDocumentBinaryData().length;
            }
            docReturn.setLastUseTime(-10000);
            leastUsedDocs.reHeapify(docReturn);
            leastUsedDocs.remove();
        }
        //new stuff for stage 3
        if(docReturn != null){
            Set<String> words = docReturn.getWords();
            for(String w : words){
                trie.delete(w, docReturn);
            }
        }
        //stage 2 stuff
        //These next few lines are need for undo purposes
        if(!(deletedDocsHT.get(uri) instanceof StackImpl)){
            StackImpl<DocumentImpl> newStack = new StackImpl<>();
            deletedDocsHT.put(uri, newStack);
        }
        StackImpl<DocumentImpl> stack = deletedDocsHT.get(uri);
        stack.push(docReturn);//Remember this can be null
        Function<URI,Boolean> undoDeleteFunction = uri1 -> this.undoDeleteDocument(uri1);
        commandStack.push(new GenericCommand<>(uri, undoDeleteFunction));
        //These previous few lines are need for undo purposes
    }



    private Boolean undoReplaceDocument(URI uri1) {
        if(uri1 == null){
            throw new IllegalArgumentException("Tried to undo a null URI");
        }
        DocumentImpl docThatWASreplaced = (DocumentImpl) replacedDocsHT.get(uri1).pop();  //This is the doc that was replaced that were putting back
        byte[] newBdToAdd;
        if(docThatWASreplaced.getDocumentTxt() != null) {
            newBdToAdd = docThatWASreplaced.getDocumentTxt().getBytes();
        }else{
            newBdToAdd = docThatWASreplaced.getDocumentBinaryData();
        }
        this.ensureHaveEnoughRoom(uri1,newBdToAdd);
        DocumentImpl docThatReplaced = (DocumentImpl) hashTable.put(uri1, (docThatWASreplaced)); //This is the doc that replaced that were getting rid of
        if(docThatReplaced != null) {
            Set<String> words = docThatReplaced.getWords();
            for (String w : words) {
                trie.delete(w, docThatReplaced);
            }
            //new for stage 4
            docCount--;
            if(docThatReplaced.getDocumentTxt() != null) {
                docBytesAmount -= docThatReplaced.getDocumentTxt().getBytes().length;
            }else{
                docBytesAmount -= docThatReplaced.getDocumentBinaryData().length;
            }
            docThatReplaced.setLastUseTime(-10000);
            leastUsedDocs.reHeapify(docThatReplaced);
            leastUsedDocs.remove();
        }
        if(docThatWASreplaced != null) {
            Set<String> words = docThatWASreplaced.getWords();
            for (String w : words) {
                trie.put(w, docThatWASreplaced);
            }
            docCount++;
            docBytesAmount += newBdToAdd.length;
            docThatWASreplaced.setLastUseTime(System.nanoTime());
            leastUsedDocs.insert(docThatWASreplaced);
        }
        return true;
    }

    private Boolean undoPutDocument(URI uri1) {
        if(uri1 == null){
            throw new IllegalArgumentException("Tried to undo a null URI");
        }
        Document doc = hashTable.put(uri1, null);
        if(doc == null){
            return false;
        }
        //new stuff for stage 3
        if(doc != null) {
            Set<String> words = doc.getWords();
            for (String w : words) {
                trie.delete(w, doc);
            }
            //stage 4 stuff
            docCount--;
            if(doc.getDocumentTxt() != null) {
                docBytesAmount -= doc.getDocumentTxt().getBytes().length;
            }else{
                docBytesAmount -= doc.getDocumentBinaryData().length;
            }
            doc.setLastUseTime(-10000);
            leastUsedDocs.reHeapify(doc);
            leastUsedDocs.remove();

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
        Document returnDoc = (Document) hashTable.get(uri);
        if(returnDoc != null) {
            returnDoc.setLastUseTime(System.nanoTime());
            leastUsedDocs.reHeapify(returnDoc);
        }
        return returnDoc;
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
        //new stuff for stage 3
        if(doc != null){
            Set<String> words = doc.getWords();
            for(String w : words){
                trie.delete(w, doc);
            }
            //stage 4 stuff
            docCount--;
            if(doc.getDocumentTxt() != null){
                docBytesAmount -= doc.getDocumentTxt().getBytes().length;
            }else{
                docBytesAmount -= doc.getDocumentBinaryData().length;
            }
            doc.setLastUseTime(-10000);
            leastUsedDocs.reHeapify(doc);
            leastUsedDocs.remove();

        }
        //Stage 2 stuff
        //These next few lines are all needed for undo purposes
        if(deletedDocsHT.get(uri) == null){
            StackImpl<DocumentImpl> newStack = new StackImpl<>();
            deletedDocsHT.put(uri, newStack);
        }
        StackImpl<DocumentImpl> stack = deletedDocsHT.get(uri);

        stack.push(doc);//Remember this can be null
        Function<URI,Boolean> undoDeleteFunction= uri1 -> this.undoDeleteDocument(uri1);
        commandStack.push(new GenericCommand<>(uri, undoDeleteFunction));
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
        if(doc != null) {
            byte[] newBdToAdd;
            if (doc.getDocumentTxt() != null) {
                newBdToAdd = doc.getDocumentTxt().getBytes();
            } else {
                newBdToAdd = doc.getDocumentBinaryData();
            }
            this.ensureHaveEnoughRoom(uri1, newBdToAdd);
        }
        hashTable.put(uri1, doc);
        //new stuff for stage 3
        if(doc != null) {
            Set<String> words = doc.getWords();
            for (String w : words) {

                trie.put(w, doc);
            }
            //new stage 4
            docCount++;
            if(doc.getDocumentTxt() != null){
                docBytesAmount += doc.getDocumentTxt().getBytes().length;
            }else{
                docBytesAmount += doc.getDocumentBinaryData().length;
            }
            doc.setLastUseTime(System.nanoTime());
            leastUsedDocs.insert(doc);
        }
        return true;
    }

    /**
     * undo the last put or delete command
     * @throws IllegalStateException if there are no actions to be undone, i.e. the command stack is empty
     */
    @Override
    public void undo() throws IllegalStateException {

        if(commandStack.size()>0){

            Undoable undoCommand = (Undoable) commandStack.pop();

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
            if(commandStack.size()>0) {
                Undoable currentCommand = (Undoable) commandStack.pop();
                if(currentCommand instanceof GenericCommand) {
                    if (((GenericCommand) currentCommand).getTarget() == uri) {
                        currentCommand.undo();
                        haventFoundURI = false;
                    }
                    if (haventFoundURI == true) {
                        holderStack.push(currentCommand);
                    }
                }else{
                    if(((CommandSet) currentCommand).containsTarget(uri)){
                        ((CommandSet) currentCommand).undo(uri);
                        haventFoundURI = false;
                    }
                    if (((CommandSet) currentCommand).size() != 0) {
                        holderStack.push(currentCommand);
                    }
                }
            }else {
                while (holderStack.size() > 0) {
                    commandStack.push((Undoable) holderStack.pop());
                }
                throw new IllegalStateException("there are no actions on the command stack for the given URI");
            }
        }
        while (holderStack.size()>0){
            commandStack.push((Undoable) holderStack.pop());
        }
    }




    @Override
    public List<Document> search(String keyword) {
        keyword.toLowerCase();
        Comparator<Document> documentComparator = new Comparator<Document>( ) {
            @Override
            public int compare(Document d1, Document d2) {
                if(d1.wordCount(keyword)< d2.wordCount(keyword)){
                    return 1;
                }
                if(d1.wordCount(keyword)> d2.wordCount(keyword)){
                    return -1;
                }else{
                    return 0;
                }
            }
        };
        long equivalentTimeToSetAllDocsTo = System.nanoTime();
        List<Document> returnList = trie.getAllSorted(keyword,documentComparator); //Forgot how to make comparator
        for (Document d : returnList){
            d.setLastUseTime(equivalentTimeToSetAllDocsTo);
            leastUsedDocs.reHeapify(d);
        }
        return returnList;
    }

    @Override
    public List<Document> searchByPrefix(String keywordPrefix) {
        //long startTime = System.nanoTime();
        String lowerCase = keywordPrefix.toLowerCase();
        Comparator<Document> documentComparator = new Comparator<Document>( ) {
            @Override
            public int compare(Document d1, Document d2) {
                int d1size = 0;
                Set<String> d1Words = d1.getWords();
                for(String w : d1Words){
                    if(w.startsWith(lowerCase)){
                        d1size+= d1.wordCount(w);
                    }
                }
                int d2size = 0;
                Set<String> d2Words = d2.getWords();
                for(String w : d2Words){
                    if(w.startsWith(lowerCase)){
                        d2size+= d2.wordCount(w);
                    }
                }
                if(d1size> d2size){
                    return -1;
                }
                if(d1size< d2size){
                    return 1;
                }else{
                    return 0;
                }
            }
        };
        //  List<Document> returnList = trie.getAllWithPrefixSorted(lowerCase, documentComparator);
        //long elapsedNanos = System.nanoTime() - startTime;
        //for()
        //return returnList;
        long equivalentTimeToSetAllDocsTo = System.nanoTime();
        List<Document> returnList = trie.getAllWithPrefixSorted(lowerCase, documentComparator);
        for (Document d : returnList){
            d.setLastUseTime(equivalentTimeToSetAllDocsTo);
            leastUsedDocs.reHeapify(d);
        }
        return returnList;
    }

    @Override
    public Set<URI> deleteAll(String keyword) {
        keyword = keyword.toLowerCase();
        Set<Document> deletedDocs = trie.deleteAll(keyword);
        CommandSet<URI> undoDelAllComands = new CommandSet<>();
        Set<URI> uris = new HashSet<>();
        Boolean areThereAnyDocs = false;
        for(Document d : deletedDocs){
            hashTable.put(d.getKey(),null);
            uris.add(d.getKey());
            //stage 4 stuff
            docCount--;
            if(d.getDocumentTxt() != null){
                docBytesAmount -= d.getDocumentTxt().getBytes().length;
            }else{
                docBytesAmount -= d.getDocumentBinaryData().length;
            }
            d.setLastUseTime(-10000);
            leastUsedDocs.reHeapify(d);
            leastUsedDocs.remove();
            //These next few lines are need for undo purposes
            if (!(deletedDocsHT.get(d.getKey()) instanceof StackImpl)) {
                StackImpl<DocumentImpl> newStack = new StackImpl<>();
                deletedDocsHT.put(d.getKey(), newStack);
            }
            StackImpl<DocumentImpl> stack = deletedDocsHT.get(d.getKey());
            stack.push((DocumentImpl) d);
            Function<URI, Boolean> undoDeleteFunction = uri1 -> this.undoDeleteDocument(uri1);
            undoDelAllComands.addCommand(new GenericCommand<>(d.getKey(), undoDeleteFunction));
            areThereAnyDocs = true;
        }
        if(areThereAnyDocs = true) {//have to fix this still
            commandStack.push(undoDelAllComands);
        }
        return uris;
    }

    @Override
    public Set<URI> deleteAllWithPrefix(String keywordPrefix) {
        keywordPrefix.toLowerCase();
        Set<Document> deletedDocs = trie.deleteAllWithPrefix(keywordPrefix);
        CommandSet<URI> undoDelAllPreCommands = new CommandSet<>();
        Set<URI> uris = new HashSet<>();
        Boolean areThereAnyDocs = false;
        for(Document d : deletedDocs){
            hashTable.put(d.getKey(),null);
            uris.add(d.getKey());
            //stage 4 stuff
            docCount--;
            if(d.getDocumentTxt() != null){
                docBytesAmount -= d.getDocumentTxt().getBytes().length;
            }else{
                docBytesAmount -= d.getDocumentBinaryData().length;
            }
            d.setLastUseTime(-10000);
            leastUsedDocs.reHeapify(d);
            leastUsedDocs.remove();
            //These next few lines are need for undo purposes
            if (!(deletedDocsHT.get(d.getKey()) instanceof StackImpl)) {
                StackImpl<DocumentImpl> newStack = new StackImpl<>();
                deletedDocsHT.put(d.getKey(), newStack);
            }
            StackImpl<DocumentImpl> stack = deletedDocsHT.get(d.getKey());
            stack.push((DocumentImpl) d);
            Function<URI, Boolean> undoDeleteFunction = uri1 -> this.undoDeleteDocument(uri1);
            undoDelAllPreCommands.addCommand(new GenericCommand<>(d.getKey(), undoDeleteFunction));
            areThereAnyDocs = true;
            //new stuff for mistake from stage 3(I think)
            Set<String> words = d.getWords();
            for(String w : words){
                trie.delete(w, d);
            }
        }
        if(areThereAnyDocs == true) {//have to fix this still
            commandStack.push(undoDelAllPreCommands);
        }
        return uris;
    }

    @Override
    public void setMaxDocumentCount(int limit) {
        maxDocCountBoolean = true;
        maxDocumentCount = limit;
    }

    @Override
    public void setMaxDocumentBytes(int limit) {
        maxDocBytesBoolean = true;
        maxDocumentBytes = limit;
    }
}
