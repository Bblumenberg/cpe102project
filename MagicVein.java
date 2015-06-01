import processing.core.*;
import java.util.List;
import java.util.ArrayList;
import java.lang.Math;

public class MagicVein extends Vein{
    
    public MagicVein(String name, List<PImage> imgs, int rate, Point position, int resourceDistance){
        super(name, imgs, rate, position, resourceDistance);
        List<PositionedEntity> quakeList = new ArrayList<PositionedEntity>(0);
        int mainX = getPosition().getX();
        int mainY = getPosition().getY();
        for(int y = mainY - 2; y <= mainY + 2; y++){
            for(int x = mainX - 2; x <= mainX + 2; x++){
                if(x == mainX && y == mainY){continue;}
                else if(x == mainX || y == mainY){quakeList.add(new MagicQuake(new Point(x,y), "bg"));}
                else if(Math.abs(mainX - x) == 1 && Math.abs(mainY - y) == 1){
                    quakeList.add(new Wyvern("wyvern_" + x + "_" + y, ProcessWorld.wyvernImgs, new Point(x,y), 1000, 70));
                    ProcessWorld.getWorld().getBackground(new Point(x,y)).currentImg = RandomGen.gen(2,5);
                }
                else{quakeList.add(new MagicQuake(new Point(x,y), "obs"));}
            }
        }
        for(PositionedEntity e : quakeList){
            ProcessWorld.getWorld().addEntity(e);
            if(e instanceof Wyvern){Wyvern w = (Wyvern) e; w.createNextAction(ProcessWorld.getWorld());}
        }
    }
    
    public MagicVein(String name, List<PImage> imgs, int rate, Point position){
        this(name, imgs, rate, position, 2);
    }
    
    public void createNextAction(WorldModel world){
        if(world.withinBounds(this.getPosition())){
            ScheduledAction myAction = new ScheduledAction(this, world, rate, new Action<Vein>(){
                public void method(Vein e, WorldModel world){
                    if(!world.withinBounds(e.getPosition())){return;}
                    Point openPt = e.findOpenAround(world, position, resourceDistance);
                    if(openPt != null){
                        Ore ore = new Ore("ore - " + e.getName() + " - " + System.currentTimeMillis(), ProcessWorld.oreImgs, openPt, RandomGen.gen(10000, 20000));
                        ore.setAsMagic();
                        world.addEntity(ore);
                        ore.createNextAction(world);
                    }
                    e.createNextAction(world);
                }
            });
            Actions.addAction(myAction);
        }
    }

}