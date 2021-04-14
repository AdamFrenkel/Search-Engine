package edu.yu.cs.com1320.project.stage4.impl;

import edu.yu.cs.com1320.project.impl.HashTableImpl;
import edu.yu.cs.com1320.project.stage4.Document;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class DocumentImpl implements Document {

    private URI uri = null; //not sure why "null" is grey
    private URI emptyUri;
    private long lastUseTime = 0;

    private String txt = null;
    private byte[] binaryData = null;

    private HashTableImpl<String, Integer> wordCount = new HashTableImpl<>();
    private Set<String> words = new HashSet<>();

    /**
     * From the Stage 1 assignment doc:
     * "DocumentImpl must provide the following two constructors,
     * which should throw an java.lang.IllegalArgumentException if either argument is null or empty/blank:
     * public DocumentImpl(URI uri, String txt)
     * public DocumentImpl(URI uri, byte[] binaryData)"
     */
    public DocumentImpl(URI uri, String txt)  {
        if(uri == null){
            throw new IllegalArgumentException("Attempted to construct a txt document with a null uri");
        }
        try {
            emptyUri = new URI("");
        }
        catch (URISyntaxException e){
            throw new IllegalArgumentException("URI Syntax exception");
        }
        if (uri.compareTo(emptyUri) == 0) { //I believe this is enough //although really should ask
            throw new IllegalArgumentException("Attempted to construct a txt document with an empty uri");
        }

        if(txt == null){
            throw new IllegalArgumentException("Attempted to construct a txt document with a null string");
        }
        if(txt.isEmpty()){
            throw new IllegalArgumentException("Attempted to construct a txt document with an empty string");
        }
        this.uri = uri;
        this.txt = txt;
        this.addWordsToHtAndSet(txt);
    }

    /**
     * Look at intro note to txt constructor above^.
     */
    public DocumentImpl(URI uri, byte[] binaryData)  {
        if(uri == null){
            throw new IllegalArgumentException("Attempted to construct a byte document with a null uri");
        }
        try {
            emptyUri = new URI("");
        }
        catch (URISyntaxException e){
            throw new IllegalArgumentException("URI Syntax exception");
        }
        if(uri.compareTo(emptyUri) == 0) { // * I believe this is enough
            throw new IllegalArgumentException("Attempted to construct a byte document with an empty uri");
        }
        if(binaryData == null){
            throw new IllegalArgumentException("Attempted to construct a byte document with a null array");
        }
        if(binaryData.length == 0){
            throw new IllegalArgumentException("Attempted to construct a byte document with an empty array");
        }
        this.uri = uri;
        this.binaryData = binaryData;

        String string = new String(binaryData);
        this.addWordsToHtAndSet(string);

    }

    private void addWordsToHtAndSet(String txt){

        //removing anything that's not txt or string
        txt = txt.replaceAll("[^a-zA-Z0-9\\s]", "");
        //this makes it case insensitive
        txt = txt.toLowerCase();
        //this gets the individual words
        String[] words = txt.split(" ");
        for(int i = 0; i < words.length; i++){
            if(!(words[i].equals(""))) {
                this.words.add(words[i]);
                if (wordCount.get(words[i]) == null) {
                    wordCount.put(words[i], 1);
                } else {
                    wordCount.put(words[i], wordCount.get(words[i]) + 1);
                }
            }
        }
    }

    /**
     * @return content of text document
     */
    public String getDocumentTxt(){
        return txt; //This will return null if this is a BD doc, as per piazza
    }

    /**
     * @return content of binary data document
     */
    public byte[] getDocumentBinaryData(){
        return binaryData; //This will return null if this is a txt doc, as per piazza
    }

    /**
     * @return URI which uniquely identifies this document
     */
    public URI getKey(){
        return uri;
    }

    /**
     * From the Stage 1 assignment doc:
     * "DocumentImpl must override the default equals and hashCode methods.
     * Two documents are considered equal if they have the same hashCode."
     */
    @Override
    public boolean equals(Object o) {
        if (this == o){
            return true;
        }
        if (o == null || this.getClass() != o.getClass()){
            return false;
        }
        DocumentImpl document = (DocumentImpl)o;
        return (this.hashCode() == document.hashCode());
    }

    /**
     * From the Stage 1 assignment doc:
     * "The hashCode for a Document differs depending on if the document format is a String,
     * in which case it is calculated using java.util.Objects.hash(URI,String), or if the document format is a byte[],
     * in which case it is calculated using java.util.Objects.hash (URI,byte[])."
     * *In the end this code was given to us on piazza to avoid confusion
     */
    @Override
    public int hashCode() {
        int result = uri.hashCode();
        result = 31 * result + (txt != null ? txt.hashCode() : 0);
        result = 31 * result + Arrays.hashCode(binaryData);
        return result;
    }


    @Override
    public int wordCount(String word) {
        String lowerCaseWord = word.toLowerCase();
        lowerCaseWord = lowerCaseWord.replaceAll("[^a-zA-Z0-9\\s]", "");
        return wordCount.get(lowerCaseWord) == null ? 0 : (Integer)wordCount.get(lowerCaseWord);

    }

    @Override
    public Set<String> getWords() {
        return words;
    }

    @Override
    public long getLastUseTime() {
        return lastUseTime;
    }

    @Override
    public void setLastUseTime(long timeInNanoseconds) {
        lastUseTime = timeInNanoseconds;
    }

    @Override
    public int compareTo(Document o) {
        if(this.getLastUseTime() > o.getLastUseTime()){
            return 1;
        }
        if(this.getLastUseTime() < o.getLastUseTime()){
            return -1;
        }
        return 0; //bc this means equal
    }
}
