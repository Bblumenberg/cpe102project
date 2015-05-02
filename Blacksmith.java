public class Blacksmith extends ResourceEntity{

    private int resourceDistance;
    public Blacksmith(String name, Point position, int resourceLimit, double rate, int resourceDistance){
        super(name, position, rate, resourceLimit, 0, "blacksmith")
        this.resourceDistance = resourceDistance;
    }
}