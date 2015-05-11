import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Comparator;

public class WorldModel{

    public int numRows;
    public int numCols;
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

    public PositionedEntity findNearest(Point pt, Class type){
        List<EntInt> oftype = new ArrayList<EntInt>(this.entities.size());
        int i = 0;
        for(PositionedEntity e : this.entities){
            if(e.getClass() == type){
                oftype.add(new EntInt(e, pt.distanceSq(e.getPosition())));
            }
        }
        return this.nearestEntity(oftype);
    }
    
    public PositionedEntity nearestEntity(List<EntInt> entityDists){
        if(entityDists.size() > 0){
            EntInt pair = entityDists.get(0);
            for(EntInt other : entityDists){
                int pairint = pair.getR();
                int otherint = other.getR();
                if(otherint < pairint){
                    pair = other;
                }
            }
            return pair.getL();
        }
        else{
            return null;
        }
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
        List<Point> tiles = new ArrayList<Point>();
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
