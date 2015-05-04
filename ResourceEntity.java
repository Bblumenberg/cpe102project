public abstract class ResourceEntity extends ActionedEntity{

    private int resourceLimit;
    private int resourceCount;
    public ResourceEntity(String name, Point position, double rate, int resourceLimit, int resourceCount, String type){
        super(name, position, rate, type);
        this.resourceLimit = resourceLimit;
        this.resourceCount = resourceCount;
    }
    
    public void setResourceCount(int n){
        resourceCount = n;
    }
    
    public int getResourceCount(){
        return resourceCount;
    }
    
    public int getResourceLimit(){
        return resourceLimit;
    }
}