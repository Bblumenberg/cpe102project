public class WorldModel{

    private int numRows;
    private int numCols;
    private OccGrid background;
    private OccGrid occupancy;
    
    private List<Entity> entities;
    public WorldModel(int numRows, int numCols, Background bg){
        this.numRows = numRows;
        this.numCols = numCols;
        this.entities = new ArrayList<Entity>();
        this.background = new OccGrid(numCols, numRows, bg);
        this.occupancy = new OccGrid(numCols, numRows, null);
    }
    
    public boolean withinBounds(Point pt){
        return (pt.getX() >= 0 && pt.getX() < this.numCols && pt.getY() >= 0 && pt.getY < this.numRows);
    }
    
    public boolean isOccupied(Point pt){
        return (this.withinBounds(pt) && this.occupancy.getCell(pt) != null);
    }
    
    public Entity findNearest(Point pt, Class type){
        private List<Tuple<Entity, int>> oftype = new ArrayLIst<Tuple<Entity, int>>(this.entities.size());
        int i = 0;
        for(Entity e : this.entities){
            if(e.getClass() == type){
                oftype.add(new Tuple<Entity, int>(e, pt.distanceSq(e.getPosition())));
            }
        }
        return this.nearestEntity(oftype)
    }
    
    public Entity nearestEntity(entityDists){
        if(entityDists.size() > 0){
            Tuple pair = entityDists[0];
            for(Tuple other : entityDists){
                if(other.R < pair.R){
                    pair = other;
                }
            }
            Entity nearest = pair.L;
        }
        else{
            Entity nearest = null;
        }
        return nearest;
    }
    
    public void addEntity(Entity entity){
        Point pt = entity.getPosition();
        if(this.withinBounds(pt)){
/*            Entity oldEntity = this.occupancy.getCell(pt);
            if(oldEntity != null){
                oldEntity.clearPendingActions();
            }*/
            this.occupancy.setCell(pt, entity);
            this.entities.add(entity);
        }
    }
    
    public List<Point> moveEntity(Entity entity, Point pt){
        List<Point> tiles = new LinkedList<Point>();
        if(this.withinBounds(pt)){
            Point oldPt = entity.getPosition();
            this.occupancy.setCell(oldPt, null);
            tiles.add(oldPt);
            self.occupancy.setCell(pt, entity);
            tiles.add(pt);
            entity.setPosition(pt);
        }
        return tiles;
    }
    
    public void removeEntity(Entity entity){
        this.removeEntityAt(entity.getPosition());
    }
    
    public void removeEntityAt(Point point){
        if(this.withinBounds(point) && this.occupancy.getCell(pt) != null){
            Entity entity = self.occupancy.getCell(pt);
            entity.setPosition(new Point(-1,-1));
            this.entities.remove(entity);
            this.occupancy.setCell(pt, null);
        }
    }
    
    public Background getBackground(Point pt){
        if(this.withinBounds(pt)){
            return this.background.getCell(pt);
        }
    }
    
    public void setBackground(Point pt, Background bg){
        if(this.withinBounds(pt)){
            this.background.setCell(pt, bg);
        }
    }
    
    public Entity getTileOccupant(Point pt){
        if(this.withinBounds(pt)){
            return this.occupancy.getCell(pt);
        }
    }
    
    public List<Entity> getEntities(){
        return this.entities;
    }
}
