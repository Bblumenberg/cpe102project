public class MinerNotFull extends Miner{

    public MinerNotFull(String name, int resourceLimit, Point position, double rate, double animationRate){
        super(name, resourceLimit, position, rate, animationRate, 0);
    }
    
    public TwoTuple<List<Point>,boolean> minerToOre(WorldModel world, PositionedEntity ore){
        if(ore == null){
            return new TwoTuple<List<Point>,boolean>(new EasyList<Point>(position), false);
        }
        orePt = ore.getPosition();
        if(position.adjacent(orePt)){
            this.resourceCount += 1;
//          actions.removeEntity(world, ore);
            return new TwoTuple<List<Point>,boolean>(new EasyList<Point>(orePt), true);
        }
        else{
            Point newPt = nextPosition(world, orePt);
            return new TwoTuple<List<Point>,boolean>(world.moveEntity(this, newPt), false);
        }
    }
    
    public Miner tryTransforMinerNotFull(WorldModel world){
        if(resourceCount < resourceLimit){return this;}
        else{
            Miner newMiner = new MinerFull(name, resourceLimit, position, rate, animationRate);
            return newMiner;
        }
    }
}