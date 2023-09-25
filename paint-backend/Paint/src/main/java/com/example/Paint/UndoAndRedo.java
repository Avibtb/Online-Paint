package com.example.Paint;

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
}
