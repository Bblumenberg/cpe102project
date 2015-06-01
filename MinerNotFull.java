import java.util.List;
import processing.core.*;

public class MinerNotFull extends Miner{

    public MinerNotFull(String name, List<PImage> imgs, int resourceLimit, Point position, int rate, int animationRate){
        super(name, imgs, resourceLimit, position, rate, animationRate, 0);
    }
    
    public boolean minerToOre(WorldModel world, Ore ore){
        if(ore == null){
            return false;
        }
        Point orePt = ore.getPosition();
        if(position.adjacent(orePt)){
            this.resourceCount += 1;
            if(ore.isMagic() && this.resourceLimit < 10){this.resourceLimit += 1;}
            world.removeEntity(ore);
            return true;
        }
        else{
            Point newPt = nextPosition(world, orePt);
            world.moveEntity(this, newPt);
            return false;
        }
    }
    
    public Miner tryTransformMinerNotFull(){
        if(resourceCount < resourceLimit){return this;}
        else{
            Miner newMiner = new MinerFull(getName(), imgs, resourceLimit, position, getRate(), animationRate);
            return newMiner;
        }
    }
    
    public void createNextAction(WorldModel world){
        if(world.withinBounds(this.getPosition())){
            ScheduledAction myAction = new ScheduledAction(this, world, rate, new Action<MinerNotFull>(){
                public void method(MinerNotFull e, WorldModel world){
                    Ore ore = (Ore) world.findNearest(e.getPosition(), Ore.class);
                    boolean found = e.minerToOre(world, ore);
                    if(found){
                        Miner newEntity = e.tryTransformMinerNotFull();
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