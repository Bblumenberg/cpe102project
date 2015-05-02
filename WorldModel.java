import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Comparator;

public class WorldModel{

    private int numRows;
    private int numCols;
    private OccGrid<Background> background;
    private OccGrid<PositionedEntity> occupancy;
    private List<PositionedEntity> entities;
    public WorldModel(int numRows, int numCols, Background bg){
        this.numRows = numRows;
        this.numCols = numCols;
        this.entities = new ArrayList<PositionedEntity>();
        this.background = new OccGrid<Background>(numCols, numRows, bg);
        this.occupancy = new OccGrid<PositionedEntity>(numCols, numRows, null);
    }
    
    public boolean withinBounds(Point pt){
        return (pt.getX() >= 0 && pt.getX() < this.numCols && pt.getY() >= 0 && pt.getY() < this.numRows);
    }
    
    public boolean isOccupied(Point pt){
        return (this.withinBounds(pt) && this.occupancy.getCell(pt) != null);
    }

    public Entity findNearest(Point pt, Class type){
        List<TwoTuple<PositionedEntity, Integer>> oftype = new ArrayList<TwoTuple<PositionedEntity, Integer>>(this.entities.size());
        int i = 0;
        for(PositionedEntity e : this.entities){
            if(e.getClass() == type){
                oftype.add(new TwoTuple<PositionedEntity, Integer>(e, pt.distanceSq(e.getPosition())));
            }
        }
        return this.nearestEntity(oftype);
    }
    
    public Entity nearestEntity(List<TwoTuple<PositionedEntity, Integer>> entityDists){
        if(entityDists.size() > 0){
            TwoTuple pair = entityDists.get(0);
            int otherint = other.getR()
            int pairint = pair.getR()
            for(TwoTuple other : entityDists){
                if(otherint - pairint){
                    pair = other;
                }
            }
            PositionedEntity nearest = pair.getL();
        }
        else{
            PositionedEntity nearest = null;
        }
        return nearest;
    }

    public void addEntity(PositionedEntity entity){
        Point pt = entity.getPosition();
        if(this.withinBounds(pt)){
/*            PositionedEntity oldEntity = this.occupancy.getCell(pt);
            if(oldEntity != null){
                oldEntity.clearPendingActions();
            }*/
            this.occupancy.setCell(pt, entity);
            this.entities.add(entity);
        }
    }
    
    public List<Point> moveEntity(PositionedEntity entity, Point pt){
        List<Point> tiles = new LinkedList<Point>();
        if(this.withinBounds(pt)){
            Point oldPt = entity.getPosition();
            this.occupancy.setCell(oldPt, null);
            tiles.add(oldPt);
            this.occupancy.setCell(pt, entity);
            tiles.add(pt);
            entity.setPosition(pt);
        }
        return tiles;
    }
    
    public void removeEntity(PositionedEntity entity){
        this.removeEntityAt(entity.getPosition());
    }
    
    public void removeEntityAt(Point point){
        if(this.withinBounds(point) && this.occupancy.getCell(point) != null){
            PositionedEntity entity = this.occupancy.getCell(point);
            entity.setPosition(new Point(-1,-1));
            this.entities.remove(entity);
            this.occupancy.setCell(point, null);
        }
    }
    
    public Background getBackground(Point pt){
        if(this.withinBounds(pt)){
            return this.background.getCell(pt);
        }
        else{return null;}
    }
    
    public void setBackground(Point pt, Background bg){
        if(this.withinBounds(pt)){
            this.background.setCell(pt, bg);
        }
    }
    
    public PositionedEntity getTileOccupant(Point pt){
        if(this.withinBounds(pt)){
            return this.occupancy.getCell(pt);
        }
        else{return null;}
    }
    
    public List<PositionedEntity> getEntities(){
        return this.entities;
    }
}
