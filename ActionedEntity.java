public abstract class ActionedEntity extends PositionedEntity{
    
    private double rate;
    public ActionedEntity(String name, Point position, double rate, String type){
        super(name, position, type);
        this.rate = rate;
    }
    
    public double getRate(){
        return this.rate;
    }
}