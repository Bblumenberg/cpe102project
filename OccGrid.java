public class OccGrid{

    private int width;
    private int height;
    private Entity[][] grid;
    public OccGrid(int width, int height, Entity value){
        this.width = width;
        this.height = height;
        this.grid  = new Entity[height][width];
        for(int y = 0; y < height; y++){
            for(int x = 0; x < width; x++){
                this.grid[y][x] = value;
            }
        }
    }
    
    public void setCell(Point pt, Entity value){
        this.grid[pt.getY()][pt.getX()] = value;
    }
    
    public Entity getCell(Point pt){
        return this.grid[pt.getY()][pt.getX()];
    }
}