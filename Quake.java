import processing.core.*;
import java.util.List;

public class Quake extends ActionedEntity{
    public Quake(String name, List<PImage> imgs, Point position, int rate, int animationRate){
        super(name, imgs, position, rate, "quake");
        this.animationRate = animationRate;
        this.nextAnimTime = System.currentTimeMillis() + this.animationRate;
    }
    
    public int getAnimationRate(){return animationRate;}
    
    public void createNextAction(WorldModel world){;}

}