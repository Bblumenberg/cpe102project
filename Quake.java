public class Quake extends ActionedEntity{
    private int animationRate;
    public Quake(String name, Point position, int rate, int animationRate){
        super(name, position, rate, "quake");
        this.animationRate = animationRate;
        for(int i = 1; i <= 6; i++){
            imgs.add("images/quake" + i.asString() + ".bmp");
        }
    }
    
    public int getAnimationRate(){
        return animationRate;
    }

}