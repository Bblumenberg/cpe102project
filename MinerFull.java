import java.util.List;
import processing.core.*;

public class MinerFull extends Miner{

    public MinerFull(String name, List<PImage> imgs, int resourceLimit, Point position, int rate, int animationRate){
        super(name, imgs, resourceLimit, position, rate, animationRate, resourceLimit);
    }

    public boolean minerToSmith(WorldModel world, Blacksmith smith){
        if(smith == null){
            return false;
        }
        Point smithPt = smith.getPosition();
        if(position.adjacent(smithPt)){
            smith.setResourceCount(smith.getResourceCount() + resourceCount);
            resourceCount = 0;
            return true;
        }
        else{
            Point newPt = nextPosition(world, smithPt);
            world.moveEntity(this, newPt);
            return false;
        }
    }
    
    public Miner tryTransformMinerFull(){
        Miner newMiner = new MinerNotFull(getName(), imgs, resourceLimit, position, getRate(), animationRate);
        return newMiner;
    }
    
    public void createNextAction(WorldModel world){
        if(world.withinBounds(this.getPosition())){
            ScheduledAction myAction = new ScheduledAction(this, world, rate, new Action<MinerFull>(){
                public void method(MinerFull e, WorldModel world){
                    Blacksmith smith = (Blacksmith) world.findNearest(e.getPosition(), Blacksmith.class);
                    boolean found = e.minerToSmith(world, smith);
                    if(found){
                        Miner newEntity = e.tryTransformMinerFull();
                        if(newEntity != e){
                            world.removeEntity(e);
                            world.addEntity(newEntity);
                            newEntity.createNextAction(world);
                        }
                        else{e.createNextAction(world);}
                    }
                    else{e.createNextAction(world);}
                }
            });
            Actions.addAction(myAction);
        }
    }

}