public class MagicQuake extends Quake{

    private String toBe;
    public MagicQuake(Point position, String toBe){
        super("magicQuake", ProcessWorld.quakeImgs, position, 100);
        this.toBe = toBe;
    }
    
    public void createNextAction(WorldModel world){
        if(world.withinBounds(this.getPosition())){
            ScheduledAction myAction = new ScheduledAction(this, world, rate, new Action<Quake>(){
                public void method(Quake e, WorldModel world){
                    Point oldPosition = getPosition();
                    world.removeEntity(e);
                    newEntity(oldPosition, world);
                }
            });
            Actions.addAction(myAction);
        }
    }
    
    private void newEntity(Point pt, WorldModel world){
        if(toBe.equals("vein")){
            MagicVein vein = new MagicVein("magicVein_" + pt.getX() + "_" + pt.getY(), ProcessWorld.magicVeinImgs, 5000, pt, 2);
            world.addEntity(vein);
            world.getBackground(pt).currentImg = RandomGen.gen(2,5);
            vein.createNextAction(world);
        }
        else if(toBe.equals("obs")){
            world.addEntity(new Obstacle("obstacle" + pt.getX() + "_" + pt.getY(), ProcessWorld.obstacleImgs, pt));
        }
        else if(toBe.equals("bg")){
            world.getBackground(pt).currentImg = RandomGen.gen(2,5);
        }
        else if(toBe.equals("regVein")){
            Vein vein = new Vein("vein_" + pt.getX() + "_" + pt.getY(), ProcessWorld.veinImgs, RandomGen.gen(9000,11000), pt);
            world.addEntity(vein);
            vein.createNextAction(world);
        }
    }
}