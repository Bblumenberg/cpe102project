import java.util.List;

public class MinerFull extends Miner{

    public MinerFull(String name, int resourceLimit, Point position, int rate, int animationRate){
        super(name, resourceLimit, position, rate, animationRate, resourceLimit);
    }

    public TwoTuple<List<Point>,Boolean> minerToSmith(WorldModel world, Blacksmith smith){
        if(smith == null){
            return new TwoTuple<List<Point>,Boolean>(new EasyList<Point>(position), false);
        }
        Point smithPt = smith.getPosition();
        if(position.adjacent(smithPt)){
            smith.setResourceCount(smith.getResourceCount() + resourceCount);
            resourceCount = 0;
            return new TwoTuple<List<Point>,Boolean>(new EasyList<Point>(), true);
        }
        else{
            Point newPt = nextPosition(world, smithPt);
            return new TwoTuple<List<Point>,Boolean>(world.moveEntity(this, newPt), false);
        }
    }
    
    public Miner tryTransformMinerFull(WorldModel world){
        Miner newMiner = new MinerNotFull(getName(), resourceLimit, position, getRate(), animationRate);
        return newMiner;
    }
}