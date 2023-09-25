package com.example.Paint.shapes;

public class Circle extends Shape{

    @Override
    public void draw(String type, String fill, long previd, long id, String border, boolean exist, double[] addiitionalAttributes) {
       this.setType("circle");
       this.setFill(fill);
       this.setPrevid(previd);
       this.setId(id);
       this.setBorder(border);
       this.setExist(exist);
       this.setX1(addiitionalAttributes[0]);
       this.setY1(addiitionalAttributes[1]);
       this.setX2(addiitionalAttributes[2]);
       this.setY2(addiitionalAttributes[3]);
       this.setRotation_angle(addiitionalAttributes[4]);

    }
    public Shape makeCopy(){
        Circle circleObject = null;
        try{
            circleObject = (Circle) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
        return circleObject;
    }


}
