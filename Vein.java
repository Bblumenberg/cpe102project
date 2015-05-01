public class Vein extends ActionedEntity{

    private int resourceDistance;
    public Vein(String name, double rate, Point position, int resourceDistance){
        super(name, position, rate, "vein");
        this.resourceDistance = resourceDistance;
    }
    
    public Vein(String name, double rate, Point position){
        super(name, position, rate, "vein");
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
    }
}