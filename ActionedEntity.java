import java.util.List;
import processing.core.*;

public abstract class ActionedEntity extends PositionedEntity{
    
    protected int rate;
    public ActionedEntity(String name, List<PImage> imgs, Point position, int rate, String type){
        super(name, imgs, position, type);
        this.rate = rate;
    }
    
    public int getRate(){
        return this.rate;
    }
    
    public abstract void createNextAction(WorldModel world);
}