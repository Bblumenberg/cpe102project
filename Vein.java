public class Vein extends ActionedEntity{

    private int resource_distance;
    public Vein(String name, double rate, Point position, int resource_distance = 1){
        super(name, position, rate, "vein");
        this.resource_distance = resource_distance
    }
    
    public Point find_open_around(WorldModel world, Point pt, int distance){
        for(int dy = -distance : dy <= distance : dy++){
            for(int dx = -distance : dx <= distance : dx++){
                Point new_point = new Point(pt.get_x() + dx, pt.get_y() + dy);
                if(world.within_bounds(new_point) && !(world.is_occupied(new_point))){
                    return new_point
                }
            }
        }
    }
}