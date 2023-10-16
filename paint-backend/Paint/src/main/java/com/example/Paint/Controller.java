package com.example.Paint;


import com.example.Paint.shapes.Shape;
import com.example.Paint.shapes.ShapeFactory;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/backend")
public class Controller {


    @GetMapping("/shape")
    public void  drawShape(@RequestParam String type, @RequestParam String fill, @RequestParam long previd, @RequestParam long id, @RequestParam String border, @RequestParam boolean flag, @RequestParam double additionalAttributes[]){
        ShapeFactory factory= new ShapeFactory();
        UndoAndRedo undoAndRedo = new UndoAndRedo();
        Shape shape = factory.makeShape(type);
        shape.draw(type,fill,previd,id,border,flag,additionalAttributes);
        undoAndRedo.clearRedo();
        undoAndRedo.insertShape(shape);
    }

    @GetMapping("/copy")
    public String copy(@RequestParam String type, @RequestParam String fill , @RequestParam long id, @RequestParam String border, @RequestParam double additionalAttributes[])
    {
        Shape s = new Shape().makeCopy(type,fill,id+1,id+1,border,true,additionalAttributes);
        UndoAndRedo undoAndRedo = new UndoAndRedo();
        undoAndRedo.insertShape(s);
        SendCopied sendCopied = new SendCopied();
        return sendCopied.ConvertJson(s);
    }

    @GetMapping("/undo")
    public String Undo(@RequestParam Boolean isUndo){
        UndoAndRedo undoAndRedo = new UndoAndRedo();
        if(isUndo){
            undoAndRedo.clearRedo();
            undoAndRedo.clearUndo();
            return  "";
        }
        if(UndoAndRedo.undo_hashmap.isEmpty()){
            return  null;
        }
        HashMap<Long,String[]> p = undoAndRedo.undo();
        return undoAndRedo.convertMapToJson(p);
    }

    @GetMapping("/redo")
    public String Redo(@RequestParam Boolean isRedo){
        UndoAndRedo undoAndRedo = new UndoAndRedo();
        HashMap<Long,String[]> r = undoAndRedo.redo();
        return undoAndRedo.convertMapToJson(r);
    }


    @GetMapping("/store")
    public void storing(@RequestParam String type, @RequestParam String fill, @RequestParam long previd, @RequestParam long id, @RequestParam String border,
                        @RequestParam boolean flag,@RequestParam double additionalAttributes[]){
        StoreToSave storeToSave = new StoreToSave();
        storeToSave.storeInHash(type,fill,previd,id,border,flag,additionalAttributes);
    }

    @GetMapping("/saveJSON")
    public String SaveJSON(@RequestParam String path){
        SaveAndLoad save = new SaveAndLoad();
        save.saveInJson(path);
        return "Finished";
    }

    @GetMapping("/loadJSON")
    public String loadJSON(@RequestParam String path) throws StreamReadException, DatabindException, IOException{
        InputStream getLocalJsonFile = new FileInputStream(path);
        HashMap<Long,String[]> jMap = new ObjectMapper().readValue(getLocalJsonFile,HashMap.class);
        UndoAndRedo un = new UndoAndRedo();
        return  un.convertMapToJson(jMap);
    }

    @GetMapping("/saveXML")
    public String saveXML(@RequestParam String path){
        SaveAndLoad save = new SaveAndLoad();
        save.saveInXML(path);
        return "Finished";
    }

    @GetMapping("/loadXML")
    public String loadXML(@RequestParam String path){
        SaveAndLoad load = new SaveAndLoad();
        UndoAndRedo ur = new UndoAndRedo();
        return ur.convertMapToJson(load.loadFromXML(path));
    }

}
