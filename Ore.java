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
    
    public void createNextAction(WorldModel world){
        if(world.withinBounds(this.getPosition())){
            ScheduledAction myAction = new ScheduledAction(this, world, rate, new Action<Ore>(){
                public void method(Ore e, WorldModel world){
                    OreBlob blob = new OreBlob(e.getName() + " -- blob", ProcessWorld.blobImgs, e.getPosition(), e.getRate() / 4, 50*RandomGen.gen(1,3));
                    world.removeEntity(e);
                    blob.createNextAction(world);
                    world.addEntity(blob);
                }
            });
            Actions.addAction(myAction);
        }
    }
}