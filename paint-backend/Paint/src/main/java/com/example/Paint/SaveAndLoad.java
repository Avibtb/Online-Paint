package com.example.Paint;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.catalina.Store;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class SaveAndLoad {
    public void saveInJson(String path){
        ObjectMapper mapper = new ObjectMapper();
        StoreToSave storeToSave = new StoreToSave();
        try{
            String jsonFile = mapper.writeValueAsString(StoreToSave.storeHashmap);
            UndoAndRedo.clearUndo();
            UndoAndRedo.clearRedo();
            Iterator it = StoreToSave.storeHashmap.entrySet().iterator();
            while (it.hasNext()){
                Map.Entry pair = (Map.Entry)it.next();
                UndoAndRedo.undo_hashmap.put((Long) pair.getKey(),(String[]) pair.getValue());
                it.remove();
            }
            storeToSave.clearHash();
            FileWriter writer = new FileWriter(path);
            writer.write(jsonFile);
            writer.flush();
            writer.close();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void saveInXML(String path){
        StoreToSave storeToSave = new StoreToSave();
        try(FileOutputStream out = new FileOutputStream(new File(path).getAbsolutePath())){
            XMLEncoder encoder = new XMLEncoder(out);
            encoder.writeObject(StoreToSave.storeHashmap);
            UndoAndRedo.clearUndo();
            UndoAndRedo.clearRedo();
            Iterator it = StoreToSave.storeHashmap.entrySet().iterator();
            while (it.hasNext()){
                Map.Entry pair = (Map.Entry)it.next();
                UndoAndRedo.undo_hashmap.put((Long) pair.getKey(),(String[]) pair.getValue());
                it.hasNext();
            }
            storeToSave.clearHash();
            encoder.close();
        }catch (FileNotFoundException e){
            throw new RuntimeException(e);
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    public HashMap<Long,String[]> loadFromXML(String path){
        HashMap<Long,String[]> loadMap = new HashMap<>();
        try(InputStream in = new FileInputStream(new File(path).getAbsolutePath())){
            XMLDecoder decoder = new XMLDecoder(in);
            loadMap = (HashMap<Long, String[]>) decoder.readObject();
            decoder.close();
        }catch (FileNotFoundException e){
            throw new RuntimeException(e);
        }catch (IOException e){
            throw new RuntimeException(e);
        }
        StoreToSave storeToSave = new StoreToSave();
        storeToSave.setStoreHashmap(loadMap);
        return loadMap;
    }
}

