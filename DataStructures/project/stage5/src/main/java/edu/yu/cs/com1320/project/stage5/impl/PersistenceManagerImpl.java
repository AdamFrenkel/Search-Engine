package edu.yu.cs.com1320.project.stage5.impl;

import com.google.gson.*;
import com.google.gson.GsonBuilder;
import edu.yu.cs.com1320.project.stage5.Document;
import edu.yu.cs.com1320.project.stage5.PersistenceManager;
import javax.xml.bind.DatatypeConverter;
import java.lang.reflect.Type;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
//import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
//import java.util.HashMap;

public class PersistenceManagerImpl<Key, Value> implements PersistenceManager<Key, Value> {
    private Gson gson = new GsonBuilder().registerTypeAdapter(DocumentImpl.class, new DocSerializer()).registerTypeAdapter(DocumentImpl.class,new DocDeserializer()).setPrettyPrinting().create();
    private String dir;
    public PersistenceManagerImpl(File dir){
        if (dir!= null) {
            String dirString = dir.toString();
            this.dir = dirString;
        }else{
            this.dir = System.getProperty("user.dir");
        }
    }
    private class DocSerializer implements JsonSerializer<DocumentImpl>{
        @Override
        public JsonElement serialize(DocumentImpl doc, Type type, JsonSerializationContext jsonSerializationContext) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            JsonObject json = new JsonObject();
            if(doc.getDocumentTxt() != null) {
                json.addProperty("Txt", doc.getDocumentTxt()); //Still haveto deal woth BinaryData
            }else{
                json.addProperty("Binary Data",DatatypeConverter.printBase64Binary(doc.getDocumentBinaryData()));
            }
            json.addProperty("URI", String.valueOf((URI) doc.getKey()));
            json.addProperty("wordMap", gson.toJson(doc.getWordMap()));
            return json;
        }
    }
    private class DocDeserializer implements JsonDeserializer<DocumentImpl>{
        @Override
        public DocumentImpl deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String txt = null;
            String preBD = null;
            byte[] bD = null;
            if(jsonObject.has("Txt")){
                txt = jsonObject.get("Txt").getAsString();
               // System.out.println(txt);
            }else {
                preBD = jsonObject.get("Binary Data").getAsString();
              //  System.out.println("pre-bd" + preBD);
                bD = DatatypeConverter.parseBase64Binary(preBD);
             //  System.out.println(("bD " + Arrays.toString(bD)));
            }
            String stringURI = jsonObject.get("URI").toString();
            if(stringURI.startsWith("\"")){
                stringURI = stringURI.substring(1);
            }
            if(stringURI.endsWith("\"")){
                stringURI = stringURI.substring(0,stringURI.length()-1);
            }
            URI uri = null;
            try {
                uri = new URI(stringURI);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
            //System.out.println(uri);
            DocumentImpl doc;
            if(txt!=null){
                doc = new DocumentImpl(uri,txt);
            }else{
                doc = new DocumentImpl(uri,bD);
            }
            String json = jsonObject.get("wordMap").toString();
//            System.out.println(json);
//            if(json.startsWith("\"")){
//                json = json.substring(1);
//            }
//            if(json.endsWith("\"")){
//                json = json.substring(0,json.length()-1);
//            }
            System.out.println(json);
            json = json.replaceAll("\\\\n","");
            json = json.replaceAll("\\\\\"","");
            json = json.replaceAll("\"","");
            System.out.println(json);
            //Map<String,Integer> map = new HashMap<>();
            Type type1 = new TypeToken<Map<String, String>>(){}.getType();
            Map<String, Integer> myMap = gson.fromJson(json, type1);
            doc.setWordMap(myMap);
//            Map<String,Integer> wordWap = new HashMap<>();
//            Type type2 = wordWap{}.getType();
          //  Type type2 =new TypeToken<Map<String,Integer>>{}.getT
//            JsonElement json = jsonObject.get("wordMap");
//            Map<String,Integer> map = new HashMap<>();
//            map = (Map<String,Integer>) gson.fromJson(jsonObject, map.getClass());

//            Map<String, Object> map = new Gson().fromJson(json, Map.class);

//            wordMap = gson.fromJson(jsonElement1 , Map.class);
        //    HashMap wordWap = gson.fromJson(gson.toJson(jsonObject.get("wordMap")),HashMap.class);
            return doc;
        }
    }

    @Override
    public void serialize(Key key, Value val) throws IOException {
        DocumentImpl doc = (DocumentImpl) val;
        //JsonObject json = new JsonObject();
//        new GSonBuilder().registerTypeAdapter(Document.class, DocSerializer)
//        DocSerializer must implement JsonSerializer<Document>
//                new GSonBuilder().registerTypeAdapter(Document.class, DocDeserializer)
//        DocDeserializer must implement JsonDeserializer<Document>
//        json.addProperty("Txt", doc.getDocumentTxt());
//        json.addProperty("URI", String.valueOf((URI) doc.getKey()));
//        json.addProperty("wordMap", gson.toJson(doc.getWordMap()));
        URI uri = ( URI) key;
       // String stringUri = uri.toString();
        //Path workingDir = Paths.get("");
        //System.out.println(stringUri.substring(7) + ".json");
       // String dir = System.getProperty("user.dir");
        String fileName = dir + uri.getRawSchemeSpecificPart();
       // System.out.println("filename: " + fileName);
        String directory = fileName.substring(0,fileName.lastIndexOf("/"));
       // System.out.println("dir: " + directory);
        File file = new File(directory);
        //file.mkdir();
        if (file.mkdirs()) {
            System.out.println("New dir created");
        } else {
            System.out.println("dir here already");
        }
//        System.out.println(gson.toJson(doc));
//        System.out.println(fileName +".json");
       // String name = stringUri.substring(stringUri.lastIndexOf("/")+1);
        FileWriter fileWriter = new FileWriter(fileName +".json");

       // fileWriter.write("!Hey tzadik!!");
        String jsonString = gson.toJson(doc);
        fileWriter.write(jsonString);
      // fileWriter.write("Hey tzadik!");
       // fileWriter.write("Good luck on the bechina");
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
        URI uri = (URI) key;
        String fileName = dir + uri.getRawSchemeSpecificPart();
        String directory = fileName.substring(0,fileName.lastIndexOf("/"));
        byte[] content = Files.readAllBytes(Paths.get(fileName + ".json"));
        String docInJson = new String(content);
        System.out.println("what deserializeing will return: " + gson.fromJson(docInJson,DocumentImpl.class));

       // String has a constructor that takes in a byte[]

       // FileWriter fileWriter = new FileWriter(fileName +".json");
//        File directoryPath = new File(fileName);
//        try (InputStream in = Files.newInputStream(Path.of(directory));
//             BufferedReader reader =
//                     new BufferedReader(new InputStreamReader(in))) {
//            String line = null;
//            while ((line = reader.readLine()) != null) {
//                System.out.println(line);
//            }
//        } catch (IOException x) {
//            System.err.println(x);
//        }

        //Lists all files + directories
//        String contents[] = directoryPath.list();
//        for(int i=0; i<contents.length; i++) {
//            if(contents[i] == fileName){
//                System.out.println("works");
//            }
//        }

        return null;
    }

    @Override
    public boolean delete(Key key) throws IOException {
        return false;
    }
}
