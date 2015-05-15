import java.util.List;
import processing.core.*;

public class OreBlob extends ActionedEntity{
    public OreBlob(String name, List<PImage> imgs, Point position, int rate, int animationRate){
        super(name, imgs, position, rate, "blob");
        this.animationRate = animationRate;
        this.nextAnimTime = System.currentTimeMillis() + this.animationRate;
    }
    
    public int getAnimationRate(){return animationRate;}
    
    public Point blobNextPosition(WorldModel world, Point destPt){
        int horiz = Sign.compare(destPt.getX(), position.getX());
        Point newPt = new Point(position.getX() + horiz, position.getY());
        if(horiz == 0 || world.isOccupied(newPt) && !(world.getTileOccupant(newPt) instanceof Ore)){
            int vert = Sign.compare(destPt.getY(), position.getY());
            newPt = new Point(position.getX(), position.getY() + vert);
            if(vert == 0 || world.isOccupied(newPt) && !(world.getTileOccupant(newPt) instanceof Ore)){
                newPt = position;
            }
        }
        return newPt;
    }
    
    public TwoTuple<List<Point>,Boolean> blobToVein(WorldModel world, Vein vein){
        if(vein == null){
            return new TwoTuple<List<Point>,Boolean>(new EasyList<Point>(position), false);
        }
        Point veinPt = vein.getPosition();
        if(position.adjacent(veinPt)){
            world.removeEntity(vein);
            return new TwoTuple<List<Point>,Boolean>(new EasyList<Point>(veinPt), true);
        }
        else{
            Point newPt = blobNextPosition(world, veinPt);
            PositionedEntity oldEntity = world.getTileOccupant(newPt);
            if(oldEntity instanceof Ore){
                world.removeEntity(oldEntity);
            }
            return new TwoTuple<List<Point>,Boolean>(world.moveEntity(this, newPt), false);
        }
    }
    
    public void createNextAction(WorldModel world){;}
}