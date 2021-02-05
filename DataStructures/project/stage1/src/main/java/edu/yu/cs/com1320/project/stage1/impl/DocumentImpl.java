package edu.yu.cs.com1320.project.stage1.impl;

import edu.yu.cs.com1320.project.stage1.Document; //not sure why it added this line

import java.net.URI;

public class DocumentImpl implements Document {

    private URI uri;
    private String txt = null;
    private byte[] binaryData = null;


    public DocumentImpl(URI uri, String txt){
        this.uri = uri;
        this.txt = txt;

    }

    public DocumentImpl(URI uri, byte[] binaryData){
        this.uri = uri;
        this.binaryData = binaryData;
    }

    /**
     * @return content of text document
     */
    public String getDocumentTxt(){

    }

    /**
     * @return content of binary data document
     */
    public byte[] getDocumentBinaryData(){

    }

    /**
     * @return URI which uniquely identifies this document
     */
    public URI getKey(){

    }
}
