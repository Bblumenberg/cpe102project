import processing.core.*;
import java.util.List;

public class Obstacle extends PositionedEntity{
    
    public Obstacle(String name, List<PImage> imgs, Point position){
        super(name, imgs, position, "obstacle");
    }
    
    public int getAnimationRate(){return 0;}
}