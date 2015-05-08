public abstract class ResourceEntity extends ActionedEntity{

    protected int resourceLimit;
    protected int resourceCount;
    public ResourceEntity(String name, Point position, int rate, int resourceLimit, int resourceCount, String type){
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