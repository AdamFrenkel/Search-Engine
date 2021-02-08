package edu.yu.cs.com1320.project.stage1.impl;

import edu.yu.cs.com1320.project.stage1.Document;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;

public class DocumentImpl implements Document {

    private URI uri = null; //not sure why this is grey
    private URI nullUri = null;
    private URI emptyUri = new URI("");

    private String txt = null;
    private byte[] binaryData = null;

    private boolean isTxt = false;
    private boolean isBD = false;

    public DocumentImpl(URI uri, String txt) throws URISyntaxException {
        if(uri.compareTo(nullUri) == 0){
            throw new IllegalArgumentException("Attempted to construct a txt document with a null uri");
        }
        if(uri.compareTo(emptyUri) == 0) { //I believe this is enough
            throw new IllegalArgumentException("Attempted to construct a txt document with an empty uri");
        }
        if(txt.equals(null)){
            throw new IllegalArgumentException("Attempted to construct a txt document with a null string");
        }
        if(txt.isEmpty()){
            throw new IllegalArgumentException("Attempted to construct a txt document with an empty string");
        }
        this.uri = uri;
        this.txt = txt;
        this.isTxt = true;
    }

    public DocumentImpl(URI uri, byte[] binaryData) throws URISyntaxException {
        if(uri.compareTo(nullUri) == 0){
            throw new IllegalArgumentException("Attempted to construct a byte document with a null uri");
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
        this.isBD = true;
    }

    /**
     * @return content of text document
     */
    public String getDocumentTxt(){
        return txt;
    }

    /**
     * @return content of binary data document
     */
    public byte[] getDocumentBinaryData(){
        return binaryData;
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
     */
    @Override
    public int hashCode() {
        if (isTxt) {
            return Objects.hash(uri, txt);
        }else{
            return Objects.hash(uri, binaryData);
        }
    }
}
