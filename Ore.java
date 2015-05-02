public class Ore extends ActionedEntity{
 
    public Ore(String name, Point position, double rate){
        super(name, position, rate, "ore");
    }
    
    public Ore(String name, Point position){
        super(name, position, double 5000, "ore");
    }
}