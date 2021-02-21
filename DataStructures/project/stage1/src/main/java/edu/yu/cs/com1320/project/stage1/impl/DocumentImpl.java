package edu.yu.cs.com1320.project.stage1.impl;

import edu.yu.cs.com1320.project.stage1.Document;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Objects;

public class DocumentImpl implements Document {

    private URI uri = null; //not sure why "null" is grey
    private URI emptyUri;

    private String txt = null;
    private byte[] binaryData = null;

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

}
