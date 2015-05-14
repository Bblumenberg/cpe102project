import processing.core.*;
import java.util.List;

public abstract class Entity{
    
    public List<PImage> imgs;
    public int currentImg;
    private String name;
    public Entity(String name, List<PImage> imgs){
        this.name = name;
        this.imgs = imgs;
        this.currentImg = 0;
    }
    
    public String getName(){
        return this.name;
    }
    
    public PImage getCurrentImage(){
        return this.imgs.get(this.currentImg);
    }
}