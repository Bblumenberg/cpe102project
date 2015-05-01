public class TwoTuple<L, R>{
    private final L l;
    private final R r;
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

assign to local int
or .getValue()