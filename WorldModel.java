public class WorldModel{

    private int num_rows;
    private int num_cols;
    private OccGrid background;
    private OccGrid occupancy;
    
    private List<Entity> entities;
    public WorldModel(int num_rows, int num_cols, Background bg){
        this.num_rows = num_rows;
        this.num_cols = num_cols;
        this.entities = new ArrayList<Entity>();
        this.background = new OccGrid(num_cols, num_rows, bg);
        this.occupancy = new OccGrid(num_cols, num_rows, null);
    }
    
    public boolean within_bounds(Point pt){
        return (pt.get_x() >= 0 && pt.get_x() < this.num_cols && pt.get_y() >= 0 && pt.get_y < this.num_rows);
    }
    
    public boolean is_occupied(Point pt){
        return (this.within_bounds(pt) && this.occupancy.get_cell(pt) != null);
    }
    
    public Entity find_nearest(Point pt, Class type){
        private List<Tuple<Entity, int>> oftype = new ArrayLIst<Tuple<Entity, int>>(this.entities.size());
        int i = 0;
        for(Entity e : this.entities){
            if(e.getClass() == type){
                oftype.add(new Tuple<Entity, int>(e, pt.distance_sq(e.get_position())));
            }
        }
        return this.nearest_entity(oftype)
    }
    
    public Entity nearest_entity(entity_dists){
        if(entity_dists.size() > 0){
            Tuple pair = entity_dists[0];
            for(Tuple other : entity_dists){
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
    
    public add_entity(Entity entity){
        Point pt = entity.get_position();
        if(this.within_bounds(pt)){
/*            Entity old_entity = this.occupancy.get_cell(pt);
            if(old_entity != null){
                old_entity.clear_pending_actions();
            }*/
            this.occupancy.set_cell(pt, entity);
            this.entities.add(entity);
        }
    }
}
