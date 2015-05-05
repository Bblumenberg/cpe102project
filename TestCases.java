import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;
import java.lang.Math;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;
import org.junit.Test;

public class TestCases
{
    private static final double DELTA = 0.00001;
    private Point position1 = new Point(0, 0);
    private Point position2 = new Point(1, 0);
    private Point position3 = new Point(0, 1);
    private Point position4 = new Point(1, 1);
    
    //  3  4
    //  1  2

    @Test
    public void TestPointAdjacent(){
        assertTrue(position1.adjacent(position2));
    }
    
    @Test
    public void TestPOintDistance(){
        assertEquals(position1.distanceSq(position2), 1, DELTA);
    }
    
    private Background bg = new Background("background");
    
    @Test
    public void TestBackgroundName(){
        assertTrue(bg.getName().equals("background"));
    }
    
    private Obstacle obs = new Obstacle("obs", position1);
    
    @Test
    public void TestObstaclePosition(){
        obs.setPosition(position2);
        assertTrue(obs.getPosition().getX() == 1 && obs.getPosition().getY() == 0);
    }
    
    @Test
    public void TestObstacleString(){
        assertTrue(obs.entityString().equals("obstacle obs 0 0"));
        //Interesting that changing the position above didn't affect this...
        //Due to UnitTest or this being a different mehtod?
    }

    private OccGrid mygrid = new OccGrid<Object>(2,2,null);
    
    @Test
    public void TestOccGrid(){
        mygrid.setCell(position1, obs);
        assertTrue(mygrid.getCell(position1) == obs);
        assertTrue(mygrid.getCell(position2) == null);
    }
    
    @Test
    public void TestOccGridReset(){
        mygrid.setCell(position1, obs);
        mygrid.setCell(position1, null);
        assertTrue(mygrid.getCell(position1) == null);
    }
    
    @Test
    public void TestTwoTuple(){
        TwoTuple<Point, Entity> myTwoTuple = new TwoTuple<>(obs.getPosition(), obs);
        assertTrue(myTwoTuple.getL() == position1 && myTwoTuple.getR() == obs);
    }
    
    WorldModel myWorld = new WorldModel(2,2, bg);
    
    @Test
    public void TestAddEntity(){
        myWorld.addEntity(obs);
        assertTrue(myWorld.getTileOccupant(position1) == obs);
        assertTrue(myWorld.getEntities().contains(obs));
    }
    

    @Test
    public void TestWithinBounds(){
        assertTrue(myWorld.withinBounds(position1));
        assertFalse(myWorld.withinBounds(new Point(-1,-1)));
    }
    
    @Test
    public void TestIsOccupied(){
        myWorld.addEntity(obs);
        assertTrue(myWorld.isOccupied(position1));
        assertFalse(myWorld.isOccupied(position2));
    }

    @Test
    public void TestFindNearest(){
        myWorld.addEntity(obs);
        assertTrue(myWorld.findNearest(position2, Obstacle.class) == obs);
    }
    
    @Test
    public void TestFindNearestNull(){
        assertTrue(myWorld.findNearest(position2, Vein.class) == null);
    }

    @Test
    public void TestMoveEntity(){
        List<Point> tiles = myWorld.moveEntity(obs, position2);
        assertTrue(obs.getPosition() == position2);
        assertTrue(tiles.size() == 2 && tiles.get(0) == position1 && tiles.get(1) == position2);
    }

    @Test
    public void TestRemoveEntity(){
        myWorld.addEntity(obs);
        myWorld.removeEntity(obs);
        assertTrue(myWorld.getEntities().size() == 0 && myWorld.getTileOccupant(obs.getPosition()) == null);
    }
    
    @Test
    public void TestRemoveEntityAt(){
        myWorld.addEntity(obs);
        myWorld.removeEntityAt(obs.getPosition());
        assertTrue(myWorld.getEntities().size() == 0 && myWorld.getTileOccupant(obs.getPosition()) == null);
    }
    
    @Test
    public void TestGetBackground(){
        assertTrue(myWorld.getBackground(position1) == bg);
    }
    
    @Test
    public void TestSetBackground(){
        myWorld.setBackground(position1, null);
        assertTrue(myWorld.getBackground(position1) == null);
    }
    
    @Test
    public void TestGetTileOccupant(){
        myWorld.addEntity(obs);
        assertTrue(myWorld.getTileOccupant(position1) == obs);
    }
    
    Vein myVein = new Vein("vein", 100, position2);
    
    @Test
    public void TestVeinFindOpenAround(){
        myWorld.addEntity(obs);
        myWorld.addEntity(myVein);
        Point openPt = myVein.findOpenAround(myWorld, myVein.getPosition(), 1);
        assertTrue(openPt.getX() == 0 && openPt.getY() == 1);
    }
    
    Ore myOre = new Ore("ore", position4);
    
    @Test
    public void TestOreRate(){
        assertTrue(myOre.getRate() == 5000.0);
    }
    
    Blacksmith mySmith = new Blacksmith("smith", position4, 10, 500);
    
    @Test
    public void TestSmithResource(){
        assertTrue(mySmith.getResourceCount() == 0);
        mySmith.setResourceCount(2);
        assertTrue(mySmith.getResourceCount() == 2);
        assertTrue(mySmith.getResourceLimit() == 10);
    }
    
    MinerNotFull myMiner = new MinerNotFull("miner", 2, position1, 100, 100);
    MinerFull myFullMiner = new MinerFull("miner", 2, position1, 100, 100);
    
    @Test
    public void TestMinerGetOreNull(){
        myWorld.addEntity(myOre);
        myWorld.addEntity(myMiner);
        TwoTuple<List<Point>,Boolean> tup = myMiner.minerToOre(myWorld, null);
        assertTrue(tup.getL().get(0) == myMiner.getPosition() && tup.getR() == false);
    }
    
    @Test
    public void TestMinerGetOre(){
        /*this tests transform, nextPosition, and moveEntity all at once to make sure they work together in the anticipated manner */
        myWorld.addEntity(myOre);
        myWorld.addEntity(myMiner);
        TwoTuple<List<Point>,Boolean> tup = myMiner.minerToOre(myWorld, myOre);
        assertTrue(tup.getL().get(1).getX() == 1 && tup.getL().get(1).getY() == 0 && tup.getR() == false);
        assertTrue(myMiner.getPosition().getX() == 1 && myMiner.getPosition().getY() == 0);
        tup = myMiner.minerToOre(myWorld, myOre);
        assertTrue(tup.getL().get(0).getX() == 1 && tup.getL().get(0).getX() == 1 && tup.getR() == true);
        assertFalse(myWorld.getEntities().contains(myOre));
        assertTrue(myMiner.getResourceCount() == 1);
    }
    
    @Test
    public void TestMinerNotFullTransformFail(){
        myWorld.addEntity(myMiner);
        assertTrue(myMiner.tryTransformMinerNotFull(myWorld) == myMiner);
    }
    
    @Test
    public void TestMinerNotFullTransform(){
        myWorld.addEntity(myMiner);
        myMiner.setResourceCount(2);
        assertTrue(myMiner.tryTransformMinerNotFull(myWorld) instanceof MinerFull);
    }
    
    @Test
    public void TestMinerToSmith(){
        myWorld.addEntity(myFullMiner);
        myWorld.addEntity(mySmith);
        TwoTuple<List<Point>,Boolean> tup = myFullMiner.minerToSmith(myWorld, mySmith);
        assertTrue(tup.getL().get(1).getX() == 1 && tup.getL().get(1).getY() == 0 && tup.getR() == false);
        assertTrue(myFullMiner.getPosition().getX() == 1 && myFullMiner.getPosition().getY() == 0);
        tup = myFullMiner.minerToSmith(myWorld, mySmith);
        assertTrue(tup.getL().size() == 0 && tup.getR() == true);
        assertTrue(mySmith.getResourceCount() == 2);
        assertTrue(myFullMiner.getResourceCount() == 0);
    }
    
    @Test
    public void TestMinerFullTransform(){
        myWorld.addEntity(myFullMiner);
        assertTrue(myFullMiner.tryTransformMinerFull(myWorld) instanceof MinerNotFull);
    }
    
    Quake myQuake = new Quake("quake", position1, 100, 100);
    
    @Test
    public void TestQuakeAnimRate(){
        assertTrue(myQuake.getAnimationRate() == 100);
    }
    
    OreBlob myBlob = new OreBlob("blob", position1, 100, 100);
    
    @Test
    public void 
    
    //Sign and EasyList were implicitly tested in several other method tests.
}































