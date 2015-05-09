public abstract class Miner extends ResourceEntity{

    protected int animationRate;
    public Miner(String name, int resourceLimit, Point position, int rate, int animationRate, int resourceCount){
        super(name, position, rate, resourceLimit, resourceCount, "miner");
        for(int i = 1; i <= 5; i++){
            imgs.add("images/miner" + i.asString() + ".bmp");
        }
    }
    
    public int getAnimationRate(){return animationRate;}
    
    public Point nextPosition(WorldModel world, Point destPt){
        int horiz = Sign.compare(destPt.getX(), position.getX());
        Point newPt = new Point(position.getX() + horiz, position.getY());
        if(horiz == 0 || world.isOccupied(newPt)){
            int vert = Sign.compare(destPt.getY(), position.getY());
            newPt = new Point(position.getX(), position.getY() + vert);
            if(vert == 0 || world.isOccupied(newPt)){
                newPt = new Point(position.getX(), position.getY());
            }
        }
        return newPt;
    }
}