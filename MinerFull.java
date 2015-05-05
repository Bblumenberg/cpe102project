public class MinerFull extends Miner{

    public MinerFull(String name, int resourceLimit, Point position, double rate, double animationRate){
        super(name, resourceLimit, position, rate, animationRate, resourceLimit);
    }
    
    public TwoTuple<List<Point>,boolean> minerToSmith(WorldModel world, Blacksmith smith){
        if(smith == null){
            return new TwoTuple<List<Point>,boolean>(new EasyList<Point>(position), false);
        }
        smithPt = smith.getPosition();
        if(position.adjacent(smithPt)){
            smith.setResourceCount(smith.getResourceCount() + resourceCount);
            resourceCount = 0;
            return new TwoTuple<List<Point>,boolean>(new EasyList<Point>(), true);
        }
        else{
            Point newPt = nextPosition(world, smithPt);
            return new TwoTuple<List<Point>,boolean>(world.moveEntity(this, newPt), false);
        }
    }
    
    public Miner tryTransformMinerFull(WorldModel world){
        Miner newMiner = new MinerNotFull(name, resourceLimit, position, rate, animationRate)
        return newMiner;
    }
}