import java.lang.String;

public abstract class PositionedEntity extends Entity{
    
    private Point position;
    private String type;
    public PositionedEntity(String name, Point position, String type){
        super(name);
        this.position = position;
        this.type = type;
    }
    
    public void set_position(Point pt){
        this.position = pt;
    }
    
    public Point get_position(){
        return this.position;
    }
    
    public String entity_string(){
        return this.type + " " + this.get_name() + " " + String.valueOf(this.position.get_x()) + " " + String.valueOf(this.position.get_y());
    }
}
