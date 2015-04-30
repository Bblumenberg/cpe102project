import java.lang.String;

public abstract class PositionedEntity extends Entity{
    
    private Point position;
    private String type;
    public PositionedEntity(String name, Point position, String type){
        super(name);
        this.position = position;
        this.type = type;
    }
    
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
