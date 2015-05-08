public abstract class ActionedEntity extends PositionedEntity{
    
    private int rate;
    public ActionedEntity(String name, Point position, int rate, String type){
        super(name, position, type);
        this.rate = rate;
    }
    
    public int getRate(){
        return this.rate;
    }
}