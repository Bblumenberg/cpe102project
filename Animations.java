public class Animations{

    public static void AnimateAll(WorldModel world){
        for(PositionedEntity e : world.getEntities()){
            if(Miner.class.isInstance(e) || OreBlob.class.isInstance(e) || Quake.class.isInstance(e) || Wyvern.class.isInstance(e)){
                if(e.nextAnimTime <= System.currentTimeMillis()){
                    e.currentImg = (e.currentImg + 1) % e.imgs.size();
                    e.nextAnimTime = System.currentTimeMillis() + e.getAnimationRate();
                }
            }
        }
    }
}