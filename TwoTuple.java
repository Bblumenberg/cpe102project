public class TwoTuple<L, R>{
    private L l;
    private R r;
    public TwoTuple(L l, R r){
        this.l = l;
        this.r = r;
    }
    
    public L getL(){
        return this.l;
    }
    
    public R getR(){
        return this.r;
    }
}