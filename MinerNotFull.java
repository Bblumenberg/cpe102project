import java.util.List;
import processing.core.*;

public class MinerNotFull extends Miner{

    public MinerNotFull(String name, List<PImage> imgs, int resourceLimit, Point position, int rate, int animationRate){
        super(name, imgs, resourceLimit, position, rate, animationRate, 0);
    }
    
    public TwoTuple<List<Point>,Boolean> minerToOre(WorldModel world, PositionedEntity ore){
        if(ore == null){
            return new TwoTuple<List<Point>,Boolean>(new EasyList<Point>(position), false);
        }
        Point orePt = ore.getPosition();
        if(position.adjacent(orePt)){
            this.resourceCount += 1;
            world.removeEntity(ore);
            return new TwoTuple<List<Point>,Boolean>(new EasyList<Point>(orePt), true);
        }
        else{
            Point newPt = nextPosition(world, orePt);
            return new TwoTuple<List<Point>,Boolean>(world.moveEntity(this, newPt), false);
        }
    }
    
    public Miner tryTransformMinerNotFull(WorldModel world){
        if(resourceCount < resourceLimit){return this;}
        else{
            Miner newMiner = new MinerFull(getName(), imgs, resourceLimit, position, getRate(), animationRate);
            return newMiner;
        }
    }
    
    public void createNextAction(WorldModel world){;}
}