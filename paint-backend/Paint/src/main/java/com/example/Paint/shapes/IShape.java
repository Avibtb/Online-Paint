package com.example.Paint.shapes;

public interface IShape extends Cloneable{
    void draw(String type,String fill,long previd,long id,String border, boolean exist ,double addiitionalAttributes[]);
    Shape makeCopy(String type, String fill, long previd,long id, String border,boolean exist,double additionalAttributes[]);
}
