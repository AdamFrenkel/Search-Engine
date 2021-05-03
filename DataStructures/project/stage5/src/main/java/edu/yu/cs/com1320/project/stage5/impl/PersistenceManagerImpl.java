package edu.yu.cs.com1320.project.stage5.impl;

import com.google.gson.*;
import com.google.gson.GsonBuilder;
import edu.yu.cs.com1320.project.stage5.PersistenceManager;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

public class PersistenceManagerImpl<Key, Value> implements PersistenceManager<Key, Value> {
    Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Override
    public void serialize(Key key, Value val) throws IOException {
        DocumentImpl doc = (DocumentImpl) val;
        JsonObject json = new JsonObject();
        json.addProperty("Txt", doc.getDocumentTxt());
        json.addProperty("URI", String.valueOf((URI) doc.getKey()));
        json.addProperty("wordMap", gson.toJson(doc.getWordMap()));
        URI uri = ( URI) key;
        String stringUri = uri.toString();

        //Path workingDir = Paths.get("");
        //System.out.println(stringUri.substring(7) + ".json");
        String dir = System.getProperty("user.dir");
        String fileName = dir + uri.getRawSchemeSpecificPart();
        System.out.println("preFilename:" + fileName);
        String directory = fileName.substring(0,fileName.lastIndexOf("/"));
        System.out.println(directory);
        File file = new File(directory);
        //file.mkdir();
        if (file.mkdirs()) {
            System.out.println("File: " + file.getName());
        } else {
            System.out.println("File here already");
        }
       // String name = stringUri.substring(stringUri.lastIndexOf("/")+1);
        FileWriter fileWriter = new FileWriter(fileName +".json");
        fileWriter.write(String.valueOf(json));
        fileWriter.write("Hey Jake!");
        fileWriter.flush();
        fileWriter.close();
//        System.out.println(uri.getPath());
//        System.out.println(uri.getRawPath());
//        System.out.println(uri.getAuthority());
//        System.out.println(uri.getRawAuthority());
//        System.out.println(uri.getHost());
//       // System.out.println(uri.getRawHost());
//        System.out.println(uri.getFragment());
//        System.out.println(uri.getScheme());
//        System.out.println(uri.getRawSchemeSpecificPart());
//        System.out.println(uri.getPort());

        //String stringUri = uri.toString();
        //stringUri.
        //uri.getPath();
    //    File file = new FileWriter((key);

        //gson.toJson(doc.getWordMap());
       // System.out.println(json);
        //GsonBuilder.pu
        //Map<> map = new HashMap();

        //{String = String, URI)
        //gson.toJson(doc.getWordMap());
        //System.out.println(gson);
        //GsonBuilder.pu
        //Map<> map = new HashMap();

        //{String = String, URI = URI, wordMap = {apple, 3}}
        //JSONObject obj = new JSONObject();
//        if(doc.getDocumentTxt() != null) {
//            //GsonBuilder.
//           // gson.toJson(doc.getDocumentTxt());
//        }else{
//            gson.toJson(doc.getDocumentBinaryData()); //another link for this to look at this
//        }
//        gson.toJson(key);
//        gson.toJson(doc.getWordMap());

    }

    @Override
    public Value deserialize(Key key) throws IOException {
        return null;
    }

    @Override
    public boolean delete(Key key) throws IOException {
        return false;
    }
}
