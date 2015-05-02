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


class ResourceEntity(ActionedEntity):#Parent
def __init__(self, name, imgs, position, rate, resource_limit, resource_count, type):
ActionedEntity.__init__(self, name, imgs, position, rate, type)
self.resource_limit = resource_limit
self.resource_count = resource_count
def set_resource_count(self, n):
self.resource_count = n
def get_resource_count(self):
return self.resource_count
def get_resource_limit(self):
return self.resource_limit

