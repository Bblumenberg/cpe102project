import processing.core.*;
import java.util.List;

public class Blacksmith extends ResourceEntity{

    private int resourceDistance;
    public Blacksmith(String name, List<PImage> imgs, Point position, int resourceLimit, int rate, int resourceDistance){
        super(name, imgs, position, rate, resourceLimit, 0, "blacksmith");
        this.resourceDistance = resourceDistance;
    }
    
    public Blacksmith(String name, List<PImage> imgs, Point position, int resourceLimit, int rate){
        super(name, imgs, position, rate, resourceLimit, 0, "blacksmith");
        this.resourceDistance = 1;
    }
    
    public void setResourceCount(int n){
        resourceCount = n;
        if(n > 100){
            resourceCount = 0;
            ProcessWorld.increaseMagicLimit();
        }
    }
    
    public int getAnimationRate(){return 0;}
    
    public void createNextAction(WorldModel world){;}
}