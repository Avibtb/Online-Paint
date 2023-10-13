package com.example.Paint;

import com.example.Paint.shapes.Shape;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.HashMap;

public class SendCopied {
    public String ConvertJson(Shape s) {
        HashMap<Long, String[]> copy_hashmap = new HashMap<Long, String[]>();
        String[] shapeAttributes = new String[10];
        shapeAttributes[0] = s.getType();
        shapeAttributes[1] = s.getFill();
        shapeAttributes[2] = s.getBorder();
        shapeAttributes[3] = String.valueOf(s.getX1());
        shapeAttributes[4] = String.valueOf(s.getY1());
        shapeAttributes[5] = String.valueOf(s.getX2());
        shapeAttributes[6] = String.valueOf(s.getY2());
        shapeAttributes[7] = String.valueOf(s.getRotation_angle());
        shapeAttributes[8] = "true";
        shapeAttributes[9] = String.valueOf(s.getPrevid());
        copy_hashmap.put(s.getId(), shapeAttributes);
        ObjectMapper mapper = new ObjectMapper();
        String json = "";
        try {
            json = mapper.writeValueAsString(copy_hashmap);
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return json;
    }
}
