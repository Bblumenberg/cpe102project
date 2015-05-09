public class Blacksmith extends ResourceEntity{

    private int resourceDistance;
    public Blacksmith(String name, Point position, int resourceLimit, int rate, int resourceDistance){
        super(name, position, rate, resourceLimit, 0, "blacksmith");
        this.resourceDistance = resourceDistance;
        imgs.add("images/blacksmith.bmp");
    }
    
    public Blacksmith(String name, Point position, int resourceLimit, int rate){
        super(name, position, rate, resourceLimit, 0, "blacksmith");
        this.resourceDistance = 1;
        imgs.add("images/blacksmith.bmp");
    }
}