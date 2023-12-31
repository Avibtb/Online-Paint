package com.example.Paint;

import com.example.Paint.shapes.Shape;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.HashMap;

public class UndoAndRedo {

    public static HashMap<Long,String[]> undo_hashmap = new HashMap<Long,String[]>();

    public static HashMap<Long,String[]> redo_hashmap = new HashMap<Long,String[]>();

    public static String[] arr = new String[10];

    //undo function
    public static HashMap<Long, String[]> undo() {
        int len = undo_hashmap.keySet().size();
        Long[] keys_arr = undo_hashmap.keySet().toArray(new Long[len]);
        if(undo_hashmap.get(keys_arr[len-1])[8].equals("false")){
            arr = undo_hashmap.get(keys_arr[len-1]);
            final String[] tmp = new String[10];
            for(int i=0;i<10;i++){
                tmp[i] = arr[i];
            }
            redo_hashmap.put(Long.parseLong(arr[9]),tmp);
            undo_hashmap.remove(keys_arr[len-1]);
            return undo_hashmap;
        }
        String[] tempArr = new String[10];
        for(int i=0;i<10;i++){
            tempArr[i] = undo_hashmap.get(keys_arr[len-1])[i];

        }
        redo_hashmap.put(keys_arr[len-1],tempArr);
        undo_hashmap.remove(keys_arr[len-1]);
        return undo_hashmap;
    }

    public void insertShape(Shape shape){
        String[] shapeAttributes = new String[10];
        shapeAttributes[0] = shape.getType();
        shapeAttributes[1] = shape.getFill();
        shapeAttributes[2] = shape.getBorder();
        shapeAttributes[3] = String.valueOf(shape.getX1());
        shapeAttributes[4] = String.valueOf(shape.getY1());
        shapeAttributes[5] = String.valueOf(shape.getX2());
        shapeAttributes[6] = String.valueOf(shape.getY2());
        shapeAttributes[7] = String.valueOf(shape.getRotation_angle());
        if(shape.isExist())
            shapeAttributes[8] = "true";
        else
            shapeAttributes[8] = "false";
        shapeAttributes[9] = String.valueOf(shape.getPrevid());
        undo_hashmap.put(shape.getId(),shapeAttributes);
    }

    // To convert a given hashmap into JSON file
    public String convertMapToJson(HashMap<Long,String[]>map){
        // make an instance object of the mapper class
        ObjectMapper mapper = new ObjectMapper();
        // initialize the json file as an empty string
        String json = "";
        try{
            //Convert Map to JSON
            json = mapper.writeValueAsString(map);
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        }catch (JsonMappingException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }
        return  json;
    }

    public static HashMap<Long,String[]> redo(){
        int len = redo_hashmap.keySet().size();
        if(!redo_hashmap.isEmpty()){
            Long[] key_arr = redo_hashmap.keySet().toArray(new Long[0]);
            undo_hashmap.put(key_arr[0],redo_hashmap.get(key_arr[0]));
            redo_hashmap.remove(key_arr[0]);
        }
        return undo_hashmap;
    }

    //functions to clearout the hashmaps
    public static void clearUndo(){
        undo_hashmap.clear();
    }

    public static void clearRedo(){
        redo_hashmap.clear();
    }
    public HashMap<Long,String[]>getUndo(){
        return undo_hashmap;
    }
    public void setUndo(HashMap<Long,String[] >un){
        this.undo_hashmap = un;
    }
}
