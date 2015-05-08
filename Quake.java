public class Quake extends ActionedEntity{
    private int animationRate;
    public Quake(String name, Point position, int rate, int animationRate){
        super(name, position, rate, "quake");
        this.animationRate = animationRate;
    }
    
    public int getAnimationRate(){
        return animationRate;
    }

}