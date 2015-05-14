public class Animations{

    public static void AnimateAll(WorldModel world){
        for(PositionedEntity e : world.getEntities()){
            if(e.getClass() == MinerNotFull.class || e.getClass() == MinerFull.class || e.getClass() == OreBlob.class || e.getClass() == Quake.class){
                if(e.nextAnimTime <= System.currentTimeMillis()){
                    e.currentImg = (e.currentImg + 1) % e.imgs.size();
                    e.nextAnimTime = System.currentTimeMillis() + e.getAnimationRate();
                }
            }
        }
    }
}