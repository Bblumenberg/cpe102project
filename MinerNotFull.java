import java.util.List;

public class MinerNotFull extends Miner{

    public MinerNotFull(String name, int resourceLimit, Point position, double rate, double animationRate){
        super(name, resourceLimit, position, rate, animationRate, 0);
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
            Miner newMiner = new MinerFull(getName(), resourceLimit, position, getRate(), animationRate);
            return newMiner;
        }
    }
}