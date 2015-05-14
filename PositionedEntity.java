import java.lang.String;
import processing.core.*;
import java.util.List;

public abstract class PositionedEntity extends Entity{
    protected Point position;
    private String type;
    protected int animationRate;
    public long nextAnimTime;
    public PositionedEntity(String name, List<PImage> imgs, Point position, String type){
        super(name, imgs);
        this.position = position;
        this.type = type;
    }
    
    public abstract int getAnimationRate();
    
    public void setPosition(Point pt){
        this.position = pt;
    }
    
    public Point getPosition(){
        return this.position;
    }
    
    public String entityString(){
        return this.type + " " + this.getName() + " " + String.valueOf(this.position.getX()) + " " + String.valueOf(this.position.getY());
    }
}
