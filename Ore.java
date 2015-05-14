import processing.core.*;
import java.util.List;

public class Ore extends ActionedEntity{
 
    public Ore(String name, List<PImage> imgs, Point position, int rate){
        super(name, imgs, position, rate, "ore");
    }
    
    public Ore(String name, List<PImage> imgs, Point position){
        super(name, imgs, position, 5000, "ore");
    }
    
    public int getAnimationRate(){return 0;}
}