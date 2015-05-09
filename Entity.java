import processing.core.*;
import java.util.List;
import java.util.ArrayList;

public abstract class Entity{
    
    public List<PImage> imgs;
    public int currentImg;
    private String name;
    public Entity(String name){
        this.name = name;
        imgs = new ArrayList<PImage>(0);
        this.currentImg = 0
    }
    
    public String getName(){
        return this.name;
    }
}