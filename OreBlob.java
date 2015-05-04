public class OreBlob extends ActionedEntity{
    private int animationRate;
    
    public OreBlob(String name, Point position, double rate, int animationRate){
        super(name, position, rate, "blob");
        this.animationRate = animationRate;
    }
    
    public int getAnimationRate(){
        return animationRate;
    }
    
    public Point blobNextPosition(WorldModel world, Point destPt){
        int horiz = Sign.compare(destPt.getX() - position.getX());
        Point newPt = new Point(position.getX() + horiz, position.getY());
        if(horiz == 0 || world.isOccupied(newPt) && !(world.getTileOccupant(newPt).isInstance(Ore.class))){
            int vert = Sign.compare(destPt.getY() - position.getY());
            newPt = Point(position.getX(), position.getY() + vert);
            if(vert == 0 || world.isOccupied(newPt) && !(world.getTileOccupant(newPt).isInstance(Ore.class))){
                Point newPt = position;
            }
        }
        return newPt;
    }
    
    public blobToVein
}