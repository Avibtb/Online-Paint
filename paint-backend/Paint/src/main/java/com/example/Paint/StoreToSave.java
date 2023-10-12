package com.example.Paint;

import java.util.HashMap;

public class StoreToSave {
    public static HashMap<Long,String[]> storeHashmap = new HashMap<Long,String[]>();

     public static HashMap<Long,String[]> getStoreHashmap(){
         return storeHashmap;
     }

     public static void setStoreHashmap(HashMap<Long,String[]> storeHashmap1){
         StoreToSave.storeHashmap = storeHashmap1;
     }

     public void storeInHash(String type,String fill, Long previd, Long id, String border,boolean flag,double[] additional){
         String[] shapeAttributes = new String[10];
         shapeAttributes[0] = type;
         shapeAttributes[1] = fill;
         shapeAttributes[2] = border;
         shapeAttributes[3] = String.valueOf(additional[0]);
         shapeAttributes[4] = String.valueOf(additional[1]);
         shapeAttributes[5] = String.valueOf(additional[2]);
         shapeAttributes[6] = String.valueOf(additional[3]);
         shapeAttributes[7] = String.valueOf(additional[4]);
         shapeAttributes[8] = "true";
         shapeAttributes[9] = String.valueOf(previd);

         storeHashmap.put(id,shapeAttributes);

     }

     public void clearHash(){
         storeHashmap.clear();
     }
}
