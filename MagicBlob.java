import processing.core.*;
import java.util.List;
import java.util.ArrayList;

public class MagicBlob extends OreBlob{

    public MagicBlob(String name, List<PImage> imgs, Point position, int rate, int animationRate){
        super(name, imgs, position, rate, animationRate);
    }
    
    public boolean blobToVein(WorldModel world, Vein vein){
        if(vein == null){return false;}
        Point veinPt = vein.getPosition();
        if(position.adjacent(veinPt)){return true;}
        else{
            Point newPt = blobNextPosition(world, veinPt);
            PositionedEntity oldEntity = world.getTileOccupant(newPt);
            if(Ore.class.isInstance(oldEntity)){
                world.removeEntity(oldEntity);
                Ore oldOre = (Ore) oldEntity;
            }
            world.moveEntity(this, newPt);
            return false;
        }
    }
    
    private PositionedEntity magicBlobFindNearest(Point pt, WorldModel world){
        List<EntInt> oftype = new ArrayList<EntInt>(0);
        int i = 0;
        for(PositionedEntity e : world.getEntities()){
            if(Vein.class.isInstance(e) && !MagicVein.class.isInstance(e)){
                oftype.add(new EntInt(e, pt.distanceSq(e.getPosition())));
            }
        }
        return world.nearestEntity(oftype);
    }
    
    public void createNextAction(WorldModel world){
        if(world.withinBounds(this.getPosition())){
            ScheduledAction myAction = new ScheduledAction(this, world, rate, new Action<MagicBlob>(){
                public void method(MagicBlob e, WorldModel world){
                    Vein vein = (Vein) e.magicBlobFindNearest(e.getPosition(), world);
                    boolean found = e.blobToVein(world, vein);
                    if(found){
                        MagicQuake quake = new MagicQuake(getPosition(), "regVein");
                        world.removeEntity(e);
                        world.addEntity(quake);
                    }
                    e.createNextAction(world);
                }
            });
            Actions.addAction(myAction);
        }
    }
}