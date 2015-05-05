public class EntInt{
    private PositionedEntity l;
    private int r;
    public EntInt(PositionedEntity l, int r){
        this.l = l;
        this.r = r;
    }
    
    public PositionedEntity getL(){
        return this.l;
    }
    
    public int getR(){
        return this.r;
    }
}