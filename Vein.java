import processing.core.*;
import java.util.List;

public class Vein extends ActionedEntity{

    private int resourceDistance;
    public Vein(String name, List<PImage> imgs, int rate, Point position, int resourceDistance){
        super(name, imgs, position, rate, "vein");
        this.resourceDistance = resourceDistance;
    }
    
    public Vein(String name, List<PImage> imgs, int rate, Point position){
        super(name, imgs, position, rate, "vein");
        this.resourceDistance = 1;
    }
    
    public Point findOpenAround(WorldModel world, Point pt, int distance){
        for(int dy = -distance; dy <= distance; dy++){
            for(int dx = -distance; dx <= distance; dx++){
                Point newPoint = new Point(pt.getX() + dx, pt.getY() + dy);
                if(world.withinBounds(newPoint) && !(world.isOccupied(newPoint))){
                    return newPoint;
                }
            }
        }
        return null;
    }
    
    public void createNextAction(WorldModel world){
        if(world.withinBounds(this.getPosition())){
            ScheduledAction myAction = new ScheduledAction(this, world, rate, new Action<Vein>(){
                public void method(Vein e, WorldModel world){
                    if(!world.withinBounds(e.getPosition())){return;}
                    Point openPt = e.findOpenAround(world, position, resourceDistance);
                    if(openPt != null){
                        Ore ore = new Ore("ore - " + e.getName() + " - " + System.currentTimeMillis(), ProcessWorld.oreImgs, openPt, RandomGen.gen(20000, 30000));
                        world.addEntity(ore);
                        ore.createNextAction(world);
                    }
                    e.createNextAction(world);
                }
            });
            Actions.addAction(myAction);
        }
    }
    
    public int getAnimationRate(){return 0;}
}