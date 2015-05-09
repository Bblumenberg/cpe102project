public class Ore extends ActionedEntity{
 
    public Ore(String name, Point position, int rate){
        super(name, position, rate, "ore");
        imgs.add("images/ore.bmp");
    }
    
    public Ore(String name, Point position){
        super(name, position, 5000, "ore");
        imgs.add("images/ore.bmp");
    }
}