package edu.yu.cs.com1320.project.stage5.impl;


import edu.yu.cs.com1320.project.CommandSet;
import edu.yu.cs.com1320.project.GenericCommand;
import edu.yu.cs.com1320.project.Undoable;
//import edu.yu.cs.com1320.project.impl.HashTableImpl;
import edu.yu.cs.com1320.project.impl.*;
import edu.yu.cs.com1320.project.stage5.Document;
import edu.yu.cs.com1320.project.stage5.DocumentStore;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.*;
import java.util.function.Function;

public class DocumentStoreImpl implements DocumentStore {
    private StackImpl<Undoable> commandStack = new StackImpl<>();
    //    private HashTableImpl<URI, Document> hashTable = new HashTableImpl<>();
    private BTreeImpl<URI, Document> docStoreBTree = new BTreeImpl<>();
    // private WrongBTree <URI, Document> docStoreBTree = new WrongBTree<>();
    private Map<URI, StackImpl> deletedDocsHT = new HashMap<>();
    private Map<URI, StackImpl> replacedDocsHT = new HashMap<>(); //This will map uris of the OG doc to the doc repaced by it
    private TrieImpl<URI> trie = new TrieImpl<>();
    private boolean maxDocCountBoolean = false;
    private boolean maxDocBytesBoolean = false;
    private int maxDocumentCount = Integer.MAX_VALUE;
    private int maxDocumentBytes = Integer.MAX_VALUE;
    private Map<URI, StackImpl<Set<URI>>> putUriCausedTheseDocsToGetMovedToDisc= new HashMap<>();
    private Map<URI, StackImpl<Set<URI>>> replacedUriCausedTheseDocsToGetMovedToDisc= new HashMap<>();
    private MinHeapImpl<MinHeapEntry> leastUsedDocsNew = new MinHeapImpl();
    private MinHeapImpl<Document> leastUsedDocs = new MinHeapImpl();
    private int docCount = 0;
    private int docBytesAmount = 0;
    private Set<URI> urisOnDisc = new HashSet<>();
    private Map<URI, Long> urisLastTimeUse = new HashMap<>();
    public DocumentStoreImpl(File baseDir){
        DocumentPersistenceManager pm = new DocumentPersistenceManager(baseDir);
        docStoreBTree.setPersistenceManager(pm);
    }
    public DocumentStoreImpl(){
        DocumentPersistenceManager pm = new DocumentPersistenceManager(null);
        docStoreBTree.setPersistenceManager(pm);
    }
    private class MinHeapEntry implements Comparable<MinHeapEntry>{
        private URI uri;
        private MinHeapEntry(URI u) {
            uri =u;

        }
        private URI getURI(){
            return uri;
        }
        @Override
        public int compareTo(MinHeapEntry m) {
//            if(docStoreBTree.get(m.getURI()) == null && docStoreBTree.get(this.uri) == null){
//                return 0;
//            }
//            if(docStoreBTree.get(m.getURI()) == null){
//                return 1;
//            }
//            if(docStoreBTree.get(this.uri) == null){
//                return -1;
//            }
//            if(docStoreBTree.get(this.uri) == docStoreBTree.get(m.getURI())){
//                return 1;
//            }
//            if(urisLastTimeUse.get(this.getURI())>urisLastTimeUse.get(m.getURI())){
//                return 1;
//            }
//            if(urisLastTimeUse.get(this.getURI())<urisLastTimeUse.get(m.getURI())){
//                return -1;
//            }
            return 0;


        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            MinHeapEntry that = (MinHeapEntry) o;
            return uri.equals(that.uri);
        }

        @Override
        public int hashCode() {
            return Objects.hash(uri);
        }
    }
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
            docReturn = (DocumentImpl)docStoreBTree.put(uri, null);
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
        if(docStoreBTree.get(uri) ==null) {
            Set<URI> urisOfDocsThatWereSentToDisc = this.ensureHaveEnoughRoom(uri, bD,false);
            if (putUriCausedTheseDocsToGetMovedToDisc.get(uri) == null) {
                StackImpl<Set<URI>> newStack = new StackImpl<>();
                putUriCausedTheseDocsToGetMovedToDisc.put(uri, newStack);
            }
            StackImpl<Set<URI>> stack = putUriCausedTheseDocsToGetMovedToDisc.get(uri);   //This isn't docReturn's uri, because wont have access to that when trying to undo
            stack.push(urisOfDocsThatWereSentToDisc);
            putUriCausedTheseDocsToGetMovedToDisc.put(uri, stack);
        }else{
            boolean replacingDocOnDisc = false;
            if(urisOnDisc.contains(uri)){
                replacingDocOnDisc = true;
            }
            Set<URI> urisOfDocsThatWereSentToDisc = this.ensureHaveEnoughRoom(uri, bD, replacingDocOnDisc);
            if (replacedUriCausedTheseDocsToGetMovedToDisc.get(uri) == null) {
                StackImpl<Set<URI>> newStack = new StackImpl<>();
                replacedUriCausedTheseDocsToGetMovedToDisc.put(uri, newStack);
            }
            StackImpl<Set<URI>> stack = replacedUriCausedTheseDocsToGetMovedToDisc.get(uri);   //This isn't docReturn's uri, because wont have access to that when trying to undo
            stack.push(urisOfDocsThatWereSentToDisc);
            replacedUriCausedTheseDocsToGetMovedToDisc.put(uri, stack);
        }
        //need for a few stages
        docReturn = (DocumentImpl)docStoreBTree.put(uri, doc);
        //new stuff for stage 3 ie: trie stuff
        putStage3(doc, docReturn);
        return this.putFromPut(uri,docReturn,format,doc);
    }
    private Set<URI> ensureHaveEnoughRoom(URI uri, byte[] newBD,boolean replacingDocOnDisc) {
        Set<URI> urisOfDocsThatWereSentToDisc = new HashSet<>();
        Document doc2 = null;
        int lengthOfBdGettingReplaced = 0;
        if(!replacingDocOnDisc) {
            doc2 = docStoreBTree.get(uri);
//        if(urisOnDisc.contains(uri)){
//            this.bringBack(doc2);
//            urisOnDisc.remove(uri);
//        }
            if (doc2 != null) {
                if (doc2.getDocumentTxt() != null) {
                    lengthOfBdGettingReplaced = doc2.getDocumentTxt().getBytes().length;
                } else {
                    lengthOfBdGettingReplaced = doc2.getDocumentBinaryData().length;
                }
            }
        }
        //This if statement is checking to see if we have to remove a doc, before placing a new one in.  The docStoreBTree piece is checking if this is just a replace in which case we don't need to worry about going over doc limit
        while((maxDocCountBoolean && docCount + 1 > maxDocumentCount && (doc2 == null || replacingDocOnDisc)) || (maxDocBytesBoolean && (docBytesAmount + newBD.length - lengthOfBdGettingReplaced) > maxDocumentBytes)){
            boolean haventFoundDocInStore = true;
            while(haventFoundDocInStore) {
                DocumentImpl leastUsedDoc = (DocumentImpl) leastUsedDocs.remove();
              //  DocumentImpl leastUsedDoc = (DocumentImpl) docStoreBTree.get(leastUsedDocs.remove().getURI());
                URI uri3 = leastUsedDoc.getKey();
                Document doc3 = docStoreBTree.get(leastUsedDoc.getKey()); //I do see potential danger in constantly getting the least used doc and
//                if(urisOnDisc.contains(uri3)){
//                    this.bringBack(doc3);
//                    urisOnDisc.remove(uri3);
//                }
                if (doc3 == leastUsedDoc) { //this means that the doc is in the docStore and not just in leastUsedDoc through som mistake somewhere
                    haventFoundDocInStore = false;
                    URI uriToDelete = leastUsedDoc.getKey();
                    if(uriToDelete == null){
                        throw new IllegalArgumentException("Tried to undo a null URI");
                    }
                    Document doc = docStoreBTree.get(uriToDelete);

                    urisOfDocsThatWereSentToDisc.add(uriToDelete);
//                    if(urisOnDisc.contains(uriToDelete)){
//                        this.bringBack(doc);
//                        urisOnDisc.remove(uriToDelete);
//                    }
                    try{
                        docStoreBTree.moveToDisk(uriToDelete);
                        urisOnDisc.add(uriToDelete);
                    }catch(Exception e){
                        throw new IllegalArgumentException("I don't know what happened- but apparently there can be an exception called when moving to disc, but it's not really an illeagal argument one? GL");
                    }

                    //gets rid of all undos related to this uri BeH
//                    if(doc == null){
//                        return false;
//                    }
                    if(doc != null) {
                        haventFoundDocInStore = false;
//                        Set<String> words = doc.getWords();
//                        for (String w : words) {
//                            trie.delete(w, doc);
//                        }
                        //stage 4 stuff
                        docCount--;
                        if(doc.getDocumentTxt() != null) {
                            docBytesAmount -= doc.getDocumentTxt().getBytes().length;
                        }else{
                            docBytesAmount -= doc.getDocumentBinaryData().length;
                        }
                        this.removeDocFromUndos(uriToDelete);
                        //check out Piazza 364
                        //StackImpl potStack = deletedDocsHT.get(uriToDelete);
                        //if(potStack != null) {
                        deletedDocsHT.put(uriToDelete, null);
                        //}
                        // potStack = replacedDocsHT.get(uriToDelete);
                        // if(potStack != null) {
                        replacedDocsHT.put(uriToDelete, null);
                        //  }
//                        doc.setLastUseTime(-10000);
//                        leastUsedDocs.reHeapify(doc);
//                        leastUsedDocs.remove();
                    }

                }
            }
        }
        return urisOfDocsThatWereSentToDisc;
    }
    private void putStage3(Document doc, Document docReturn){
        Set<String> words = doc.getWords();
        URI uriOfDoc = doc.getKey();
        for (String w : words) {
            w = w.replaceAll("[^a-zA-Z0-9\\s]", "");
            trie.put(w, uriOfDoc);
        }
        if(docReturn != null){
            Set<String> deletedWords = docReturn.getWords();
            URI uriOfDocReturn = docReturn.getKey();
            for (String w : deletedWords) {
                w = w.replaceAll("[^a-zA-Z0-9\\s]", "");
                trie.delete(w, uriOfDocReturn);
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

        if(docReturn == null) {
            doc.setLastUseTime(System.nanoTime());
            urisLastTimeUse.put(doc.getKey(),doc.getLastUseTime());
            leastUsedDocs.insert(doc);
            //need for heap
            docCount++;
            //These next few lines are need for undo purposes
            Function<URI,Boolean> undoPutFunction = uri1 -> this.undoPutDocument(uri1);
            commandStack.push(new GenericCommand<>(uri, undoPutFunction));
            //These previous few lines are need for undo purposes
            //stage 4 stuff
            return 0;
        }else{

            docReturn.setLastUseTime(-10000);
            urisLastTimeUse.put(docReturn.getKey(),docReturn.getLastUseTime());
            if(!urisOnDisc.contains(docReturn.getKey())) {
                leastUsedDocs.reHeapify(docReturn);
                leastUsedDocs.remove();
                doc.setLastUseTime(System.nanoTime());
                leastUsedDocs.insert(doc);
                //need for heap
                if(docReturn.getDocumentTxt() != null){
                    docBytesAmount -= docReturn.getDocumentTxt().getBytes().length;
                }else{
                    docBytesAmount -= docReturn.getDocumentBinaryData().length;
                }
                if(replacedDocsHT.get(uri) == null){
                    StackImpl<DocumentImpl> newStack = new StackImpl<>();
                    replacedDocsHT.put(uri, newStack);
                }
                StackImpl<DocumentImpl> stack = replacedDocsHT.get(uri);   //This isn't docReturn's uri, because wont have access to that when trying to undo
                stack.push(docReturn);//Remember this can be null
                Function<URI,Boolean> undoReplaceFunction= uri1 -> this.undoReplaceDocument(uri1);
                commandStack.push(new GenericCommand<>(uri, undoReplaceFunction));
            }else {
                doc.setLastUseTime(System.nanoTime());
                urisLastTimeUse.put(doc.getKey(),doc.getLastUseTime());
                leastUsedDocs.insert(doc);
                if(replacedDocsHT.get(uri) == null){
                    StackImpl<DocumentImpl> newStack = new StackImpl<>();
                    replacedDocsHT.put(uri, newStack);
                }
                StackImpl<DocumentImpl> stack = replacedDocsHT.get(uri);   //This isn't docReturn's uri, because wont have access to that when trying to undo
                stack.push(docReturn);//Remember this can be null
                Function<URI,Boolean> undoReplaceDocumentFromDiskFunction= uri1 -> this.undoReplaceDocumentFromDisk(uri1);
                commandStack.push(new GenericCommand<>(uri, undoReplaceDocumentFromDiskFunction));
                docCount++;
            }
            return docReturn.hashCode();
        }
    }

    //Whole purpose is to make put method shorter
    private void deleteFromPut(URI uri,DocumentImpl docReturn){
        //need for heap
        if(docReturn != null){
            if(!urisOnDisc.contains(docReturn.getKey())) {
                docReturn.setLastUseTime(-10000);
                urisLastTimeUse.put(docReturn.getKey(),docReturn.getLastUseTime());
                leastUsedDocs.reHeapify(docReturn);
                leastUsedDocs.remove();
                docCount--;
                if(docReturn.getDocumentTxt() != null){
                    docBytesAmount -= docReturn.getDocumentTxt().getBytes().length;
                }else{
                    docBytesAmount -= docReturn.getDocumentBinaryData().length;
                }
            }else {
                urisOnDisc.remove(docReturn.getKey());
            }
        }
        //new stuff for stage 3
        if(docReturn != null){
            Set<String> words = docReturn.getWords();
            URI uriOfDocReturn = docReturn.getKey();
            for(String w : words){
                trie.delete(w, uriOfDocReturn);
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



    private Boolean undoReplaceDocument(URI uri1) {//********HAVE PROBLEM NNEEDD TO FIX DOC COUNT _BeH fixed
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
        this.ensureHaveEnoughRoom(uri1,newBdToAdd, false);
        DocumentImpl docThatReplaced = (DocumentImpl) docStoreBTree.put(uri1, (docThatWASreplaced)); //This is the doc that replaced that were getting rid of
        if(docThatReplaced != null) {
            Set<String> words = docThatReplaced.getWords();
            URI uriOfDocThatReplaced = docThatReplaced.getKey();
            for (String w : words) {
                trie.delete(w, uriOfDocThatReplaced);
            }
            //new for stage 4
            docCount--;
            if(docThatReplaced.getDocumentTxt() != null) {
                docBytesAmount -= docThatReplaced.getDocumentTxt().getBytes().length;
            }else{
                docBytesAmount -= docThatReplaced.getDocumentBinaryData().length;
            }
            docThatReplaced.setLastUseTime(-10000);
            urisLastTimeUse.put(docThatReplaced.getKey(),docThatReplaced.getLastUseTime());
            leastUsedDocs.reHeapify(docThatReplaced);
            leastUsedDocs.remove();
        }
        if(docThatWASreplaced != null) {
            Set<String> words = docThatWASreplaced.getWords();
            URI uriOfDocThatWASreplaced = docThatWASreplaced.getKey();
            for (String w : words) {
                trie.put(w, uriOfDocThatWASreplaced);
            }
            docCount++;
            docBytesAmount += newBdToAdd.length;
            docThatWASreplaced.setLastUseTime(System.nanoTime());
            urisLastTimeUse.put(docThatWASreplaced.getKey(),docThatWASreplaced.getLastUseTime());
            leastUsedDocs.insert(docThatWASreplaced);
        }
        if(replacedUriCausedTheseDocsToGetMovedToDisc.containsKey(uri1)) {
            Set<URI> urisToBringBack = replacedUriCausedTheseDocsToGetMovedToDisc.get(uri1).pop();
            for (URI u : urisToBringBack) {
                this.bringBack(docStoreBTree.get(u));
            }
        }
        return true;
    }
    private Boolean undoReplaceDocumentFromDisk(URI uri1) {
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
        this.ensureHaveEnoughRoom(uri1,newBdToAdd,false);
        docCount++;
        DocumentImpl docThatReplaced = (DocumentImpl) docStoreBTree.put(uri1, (docThatWASreplaced)); //This is the doc that replaced that were getting rid of
        try {
            docStoreBTree.moveToDisk(docThatWASreplaced.getKey());
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(docThatReplaced != null) {
            Set<String> words = docThatReplaced.getWords();
            URI uriOfDocThatReplaced = docThatReplaced.getKey();
            for (String w : words) {
                trie.delete(w, uriOfDocThatReplaced);
            }
            //new for stage 4
//            if(!urisOnDisc.contains(docThatReplaced.getKey())) {
//                docThatReplaced.setLastUseTime(-10000);
//                leastUsedDocs.reHeapify(docThatReplaced);
//                leastUsedDocs.remove();
//                docCount--;
//                if(docThatReplaced.getDocumentTxt() != null) {
//                    docBytesAmount -= docThatReplaced.getDocumentTxt().getBytes().length;
//                }else{
//                    docBytesAmount -= docThatReplaced.getDocumentBinaryData().length;
//                }
//            }else{
//                urisOnDisc.remove(docThatReplaced.getKey());
//            }

            urisOnDisc.remove(docThatReplaced.getKey());
        }
        if(replacedUriCausedTheseDocsToGetMovedToDisc.containsKey(uri1)) {
            Set<URI> urisToBringBack = replacedUriCausedTheseDocsToGetMovedToDisc.get(uri1).pop();
            for (URI u : urisToBringBack) {
                this.bringBack(docStoreBTree.get(u));
            }
        }
        return true;
    }

    private Boolean undoPutDocument(URI uri1) {
        if(uri1 == null){
            throw new IllegalArgumentException("Tried to undo a null URI");
        }
        Document doc = docStoreBTree.put(uri1, null);
        if(doc == null){
            return false;
        }
        //new stuff for stage 3
        if(doc != null) {
            Set<String> words = doc.getWords();
            URI docURI = doc.getKey();
            for (String w : words) {
                trie.delete(w, docURI);
            }
            //stage 4 stuff
            if(!urisOnDisc.contains(docURI)) {
                doc.setLastUseTime(-10000);
                urisLastTimeUse.put(doc.getKey(),doc.getLastUseTime());
                leastUsedDocs.reHeapify(doc);
                leastUsedDocs.remove();
                docCount--;
                if(doc.getDocumentTxt() != null) {
                    docBytesAmount -= doc.getDocumentTxt().getBytes().length;
                }else{
                    docBytesAmount -= doc.getDocumentBinaryData().length;
                }
            }else{

                urisOnDisc.remove(doc.getKey());
            }
            if(putUriCausedTheseDocsToGetMovedToDisc.containsKey(uri1)) {
                Set<URI> urisToBringBack = putUriCausedTheseDocsToGetMovedToDisc.get(uri1).pop();
                for (URI u : urisToBringBack) {
                    this.bringBack(docStoreBTree.get(u));
                }
            }
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
        Document returnDoc = (Document) docStoreBTree.get(uri);
        if(urisOnDisc.contains(uri)){
            this.bringBack(returnDoc);
            urisOnDisc.remove(uri);
        }
        if(returnDoc != null) {
            returnDoc.setLastUseTime(System.nanoTime());
            urisLastTimeUse.put(returnDoc.getKey(),returnDoc.getLastUseTime());
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
        DocumentImpl doc = (DocumentImpl) docStoreBTree.put(uri, null);
        //new stuff for stage 3
        if(doc != null){
            Set<String> words = doc.getWords();
            URI docURI = doc.getKey();
            for(String w : words){
                trie.delete(w, docURI);
            }
            //stage 4 stuff
            if(!urisOnDisc.contains(docURI)) {
                doc.setLastUseTime(-10000);
                urisLastTimeUse.put(doc.getKey(),doc.getLastUseTime());
                leastUsedDocs.reHeapify(doc);
                leastUsedDocs.remove();
                docCount--;
                if(doc.getDocumentTxt() != null){
                    docBytesAmount -= doc.getDocumentTxt().getBytes().length;
                }else{
                    docBytesAmount -= doc.getDocumentBinaryData().length;
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
            }else{
                urisOnDisc.remove(doc.getKey());
                //Stage 2&5 stuff
                //These next few lines are all needed for undo purposes
                if(deletedDocsHT.get(uri) == null){
                    StackImpl<DocumentImpl> newStack = new StackImpl<>();
                    deletedDocsHT.put(uri, newStack);
                }
                StackImpl<DocumentImpl> stack = deletedDocsHT.get(uri);

                stack.push(doc);//Remember this can be null
                Function<URI,Boolean> undoDeleteDocFromDiskFunction= uri1 -> this.undoDeleteDocFromDisk(uri1);
                commandStack.push(new GenericCommand<>(uri, undoDeleteDocFromDiskFunction));
            }

        }else{
            if(deletedDocsHT.get(uri) == null){
                StackImpl<DocumentImpl> newStack = new StackImpl<>();
                deletedDocsHT.put(uri, newStack);
            }
            StackImpl<DocumentImpl> stack = deletedDocsHT.get(uri);

            stack.push(doc);//Remember this can be null
            Function<URI,Boolean> undoDeleteFunction= uri1 -> this.undoDeleteDocument(uri1);
            commandStack.push(new GenericCommand<>(uri, undoDeleteFunction));
        }

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
            this.ensureHaveEnoughRoom(uri1, newBdToAdd,false);
        }
        docStoreBTree.put(uri1, doc);
        //new stuff for stage 3
        if(doc != null) {
            Set<String> words = doc.getWords();
            URI docURI = doc.getKey();
            for (String w : words) {

                trie.put(w, docURI);
            }
            //new stage 4
            docCount++;
            if(doc.getDocumentTxt() != null){
                docBytesAmount += doc.getDocumentTxt().getBytes().length;
            }else{
                docBytesAmount += doc.getDocumentBinaryData().length;
            }
            doc.setLastUseTime(System.nanoTime());
            urisLastTimeUse.put(doc.getKey(),doc.getLastUseTime());
            leastUsedDocs.insert(doc);
        }
        return true;
    }
    private Boolean undoDeleteDocFromDisk(URI uri1) {
        if(uri1 == null){
            throw new IllegalArgumentException("Tried to undo a null URI");
        }
        DocumentImpl doc = (DocumentImpl) (deletedDocsHT.get(uri1).pop());
//        if(doc != null) {
////            byte[] newBdToAdd;
////            if (doc.getDocumentTxt() != null) {
////                newBdToAdd = doc.getDocumentTxt().getBytes();
////            } else {
////                newBdToAdd = doc.getDocumentBinaryData();
////            }
////           // this.ensureHaveEnoughRoom(uri1, newBdToAdd);
//        }
        docStoreBTree.put(uri1, doc);
        try {
            docStoreBTree.moveToDisk(uri1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //new stuff for stage 3
        if(doc != null) {
            Set<String> words = doc.getWords();
            URI docURI = doc.getKey();
            for (String w : words) {

                trie.put(w, docURI);
            }
            //new stage 4
            //docCount++;
//            if(doc.getDocumentTxt() != null){
//                docBytesAmount += doc.getDocumentTxt().getBytes().length;
//            }else{
//                docBytesAmount += doc.getDocumentBinaryData().length;
//            }
//            doc.setLastUseTime(System.nanoTime());
//            leastUsedDocs.insert(doc);
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
        Comparator<URI> documentComparator = new Comparator<>( ) {
            @Override
            public int compare(URI u1, URI u2) {
                Document d1 = docStoreBTree.get(u1);
                Document d2 = docStoreBTree.get(u2);
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
        List<URI> returnURIList = trie.getAllSorted(keyword,documentComparator); //Forgot how to make comparator
        List<Document> returnList = new ArrayList<>();
        for(URI u : returnURIList){
            Document doc =docStoreBTree.get(u);
            returnList.add(doc);
            if(urisOnDisc.contains(u)){
                this.bringBack(doc);
                urisOnDisc.remove(u);
            }
        }
        for (Document d : returnList){
            /*
            For now putting in a simple fix, but doesn't solve the underlying question of what
            to do when search for multiple docs that need to bring back to the store, but there
            isn't enough room for all them. (Referring to the if statement)
            - Shmuel Newmark says this is correct (piazza)
             */
            if(!urisOnDisc.contains(d.getKey())) {
                d.setLastUseTime(equivalentTimeToSetAllDocsTo);
                urisLastTimeUse.put(d.getKey(),d.getLastUseTime());
                leastUsedDocs.reHeapify(d);
            }
        }
        return returnList;
    }

    @Override
    public List<Document> searchByPrefix(String keywordPrefix) {
        //long startTime = System.nanoTime();
        String lowerCase = keywordPrefix.toLowerCase();
        Comparator<URI> documentComparator = new Comparator<>( ) {
            @Override
            public int compare(URI u1, URI u2) {
                Document d1 = docStoreBTree.get(u1);
                Document d2 = docStoreBTree.get(u2);
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
        List<URI> returnURIList = trie.getAllWithPrefixSorted(lowerCase, documentComparator);
        List<Document> returnList = new ArrayList<>();
        for(URI u : returnURIList){
            Document doc =docStoreBTree.get(u);
            returnList.add(doc);
            if(urisOnDisc.contains(u)){
                this.bringBack(doc);
                urisOnDisc.remove(u);
            }
        }
        for (Document d : returnList){
            /*
            For now putting in a simple fix, but doesn't solve the underlying question of what
            to do when search for multiple docs that need to bring back to the store, but there
            isn't enough room for all them. (Referring to the if statement)
            - Shmuel Newmark says this is correct (piazza)
             */
            if(!urisOnDisc.contains(d.getKey())) {
                d.setLastUseTime(equivalentTimeToSetAllDocsTo);
                urisLastTimeUse.put(d.getKey(),d.getLastUseTime());
                leastUsedDocs.reHeapify(d);
            }
        }
        return returnList;
    }

    @Override
    public Set<URI> deleteAll(String keyword) {
        keyword = keyword.toLowerCase();
        Set<URI> deletedURIs = trie.deleteAll(keyword);
        Set<Document> deletedDocs = new HashSet<>();
        for(URI u : deletedURIs){
            deletedDocs.add(docStoreBTree.get(u));
        }
        CommandSet<URI> undoDelAllComands = new CommandSet<>();
        Set<URI> uris = new HashSet<>();
        Boolean areThereAnyDocs = false;
        for(Document d : deletedDocs){
            docStoreBTree.put(d.getKey(),null);
            uris.add(d.getKey());
            d.setLastUseTime(-10000);
            urisLastTimeUse.put(d.getKey(),d.getLastUseTime());
            if(!urisOnDisc.contains(d.getKey())) {
                leastUsedDocs.reHeapify(d);
                leastUsedDocs.remove();
                docCount--;
                //stage 4 stuff
                if(d.getDocumentTxt() != null){
                    docBytesAmount -= d.getDocumentTxt().getBytes().length;
                }else{
                    docBytesAmount -= d.getDocumentBinaryData().length;
                }
            }else{
                urisOnDisc.remove(d.getKey());
            }
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
        Set<URI> deletedURIs = trie.deleteAllWithPrefix(keywordPrefix);
        Set<Document> deletedDocs = new HashSet<>();
        for(URI u : deletedURIs){
            deletedDocs.add(docStoreBTree.get(u));
        }
        //Set<Document> deletedDocs = trie.deleteAllWithPrefix(keywordPrefix);
        CommandSet<URI> undoDelAllPreCommands = new CommandSet<>();
        Set<URI> uris = new HashSet<>();
        Boolean areThereAnyDocs = false;
        for(Document d : deletedDocs){
            docStoreBTree.put(d.getKey(),null);
            uris.add(d.getKey());
            d.setLastUseTime(-10000);
            urisLastTimeUse.put(d.getKey(),d.getLastUseTime());
            if(!urisOnDisc.contains(d.getKey())) {
                leastUsedDocs.reHeapify(d);
                leastUsedDocs.remove();
                docCount--;
                //stage 4 stuff

                if(d.getDocumentTxt() != null){
                    docBytesAmount -= d.getDocumentTxt().getBytes().length;
                }else{
                    docBytesAmount -= d.getDocumentBinaryData().length;
                }
            }else{
                urisOnDisc.remove(d.getKey());
            }


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
            URI uriOfD = d.getKey();
            for(String w : words){
                trie.delete(w, uriOfD);
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
        this.ensureHaveEnoughRoom();
    }

    @Override
    public void setMaxDocumentBytes(int limit) {
        maxDocBytesBoolean = true;
        maxDocumentBytes = limit;
        this.ensureHaveEnoughRoom();
    }

    private void ensureHaveEnoughRoom(){
        //This if statement is checking to see if we have to remove a doc, before placing a new one in.  The docStoreBTree piece is checking if this is just a replace in which case we don't need to worry about going over doc limit
        while((maxDocCountBoolean && docCount > maxDocumentCount) || (maxDocBytesBoolean && docBytesAmount > maxDocumentBytes)){
            boolean haventFoundDocInStore = true;
            while(haventFoundDocInStore) {
                DocumentImpl leastUsedDoc = (DocumentImpl) leastUsedDocs.remove();
                URI uri2 = leastUsedDoc.getKey();
                Document doc2 = docStoreBTree.get(uri2);
//                if(urisOnDisc.contains(uri2)){
//                    this.bringBack(doc2);
//                    urisOnDisc.remove(uri2);
//                }
                if (doc2 == leastUsedDoc) { //this means that the doc is in the docStore and not just in leastUsedDoc through som mistake somewhere
                    haventFoundDocInStore = false;
                    URI uriToDelete = leastUsedDoc.getKey();
                    if(uriToDelete == null){
                        throw new IllegalArgumentException("Tried to undo a null URI");
                    }
                    Document doc = docStoreBTree.get(uriToDelete);
//                    if(urisOnDisc.contains(uriToDelete)){
//                        this.bringBack(doc);
//                        urisOnDisc.remove(uriToDelete);
//                    }
                    try{
                        docStoreBTree.moveToDisk(uriToDelete);
                        urisOnDisc.add(uriToDelete);
                    }catch(Exception e){
                        throw new IllegalArgumentException("I don't know what happened- but apparently there can be an exception called when moving to disc, but it's not really an illeagal argument one? GL");
                    }
                    if(doc != null) {
                        haventFoundDocInStore = false;
//                        Set<String> words = doc.getWords();
//                        for (String w : words) {
//                            trie.delete(w, doc);
//                        }
                        //stage 4 stuff
                        docCount--;
                        if(doc.getDocumentTxt() != null) {
                            docBytesAmount -= doc.getDocumentTxt().getBytes().length;
                        }else{
                            docBytesAmount -= doc.getDocumentBinaryData().length;
                        }
                        this.removeDocFromUndos(uriToDelete);
                        //check out Piazza 364
                        //StackImpl potStack = deletedDocsHT.get(uriToDelete);
                        //if(potStack != null) {
                        deletedDocsHT.put(uriToDelete, null);
                        //}
                        // potStack = replacedDocsHT.get(uriToDelete);
                        // if(potStack != null) {
                        replacedDocsHT.put(uriToDelete, null);
                        //  }
//                        doc.setLastUseTime(-10000);
//                        leastUsedDocs.reHeapify(doc);
//                        leastUsedDocs.remove();
                    }

                }
            }
        }
    }


    private void removeDocFromUndos(URI uri) throws IllegalStateException {
        if(uri == null){
            throw new IllegalArgumentException("Tried to delete a null URI");
        }

        StackImpl holderStack = new StackImpl();
        while(commandStack.size()>0){
            // Boolean haventFoundURI = true;
            if(commandStack.size() > 0) {
                Undoable currentCommand = (Undoable) commandStack.pop();
                if (currentCommand instanceof GenericCommand) {
                    if (!(((GenericCommand) currentCommand).getTarget() == uri)) {
                        // currentCommand.undo();
                        //haventFoundURI = false;
                        holderStack.push(currentCommand);
                    }
                } else {
                    if (((CommandSet) currentCommand).containsTarget(uri)) {
                        Iterator itr = ((CommandSet) currentCommand).iterator();

                        while(itr.hasNext()) {
                            GenericCommand generic = (GenericCommand) itr.next();
                            if(generic.getTarget() == uri){
                                itr.remove();
                            }
                        }
                        // ((CommandSet) currentCommand).undo(uri);
                        // haventFoundURI = false;
                    }
                    if (((CommandSet) currentCommand).size() != 0) {
                        holderStack.push(currentCommand);
                    }
                }
            }
        }
        while (holderStack.size() > 0) {
            commandStack.push((Undoable) holderStack.pop());
        }
        // throw new IllegalStateException("there are no actions on the command stack for the given URI");

        while (holderStack.size()>0){
            commandStack.push((Undoable) holderStack.pop());
        }
    }
    private void bringBack(Document doc) {
//        URI uri = doc.getKey();
//        String txt;
//        byte[] bD;
//        DocumentStore.DocumentFormat format;
//        ByteArrayInputStream stream;
//        if (doc.getDocumentTxt() != null) {
//            txt = doc.getDocumentTxt();
//            format = DocumentFormat.TXT;
//            stream = new ByteArrayInputStream(txt.getBytes());
//        }else{
//            bD =doc.getDocumentBinaryData();
//            format = DocumentFormat.BINARY;
//            stream = new ByteArrayInputStream(bD);
//        }
//        try {
//            this.putDocument(stream,uri,format);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        doc.setLastUseTime(System.nanoTime());
        urisLastTimeUse.put(doc.getKey(),doc.getLastUseTime());
        leastUsedDocs.insert(doc);
        docStoreBTree.put(doc.getKey(),doc);
        docCount++;
        if(doc.getDocumentTxt() != null) {
            docBytesAmount += doc.getDocumentTxt().getBytes().length;
        }else{
            docBytesAmount += doc.getDocumentBinaryData().length;
        }
        urisOnDisc.remove(doc.getKey());
        this.ensureHaveEnoughRoom();

    }
}