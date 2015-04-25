public abstract class Entity{
    
    private String name;
    public Entity(String name){
        this.name = name;
    }
    
    public String get_name(){
        return this.name;
    }
}