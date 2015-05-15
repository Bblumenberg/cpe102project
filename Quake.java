import processing.core.*;
import java.util.List;

public class Quake extends ActionedEntity{
    public Quake(String name, List<PImage> imgs, Point position, int animationRate){
        super(name, imgs, position, /*rate = */ animationRate * ProcessWorld.quakeImgs.size(), "quake");
        this.animationRate = animationRate;
        this.nextAnimTime = System.currentTimeMillis() + this.animationRate;
        this.createNextAction(ProcessWorld.getWorld());
    }
    
    public int getAnimationRate(){return animationRate;}
    
    public void createNextAction(WorldModel world){
        ScheduledAction myAction = new ScheduledAction(this, world, rate, new Action<Quake>(){
            public void method(Quake e, WorldModel world){
                world.removeEntity(e);
            }
        });
        Actions.addAction(myAction);

    }
}