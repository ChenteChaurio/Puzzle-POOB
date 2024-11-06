package domain;

/**
 * Write a description of class Checkbox here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Checkbox
{
    private int x;
    private int y;
    private String color;
    private Rectangle border;
    private Rectangle center;
    private Rectangle roto;
    private boolean isOccuped;
    private boolean hasHole;
    private boolean isVisible;
    private Tile tile;
    private Puzzle puzzle;

    /**
     * Constructor for objects of class Checkbox
     * @param x is the position x in the board
     * @param y is the position y in the board
     * @param tablero is the main class
     */
    public Checkbox(int x,int y,Puzzle tablero){
        this.x = x;
        this.y = y;
        this.color = "indigo";
        this.isOccuped = false;
        this.hasHole = false;
        this.puzzle = tablero;
        this.tile = null;
        this.border = new Rectangle(40,40,color);
        this.border.moveTo(100,100);
        draw();
    }

    /**
     * Create de visual objet of Checkbox
     */
    public void draw(){
        this.center = new Rectangle(30,30,"white");
        this.center.moveTo(this.border.getXPosition() + 5, this.border.getYPosition() + 5);
        if(hasHole){
            this.roto = new Rectangle(15,15,"black");
            this.roto.moveTo(this.center.getXPosition() + 7, this.center.getYPosition() + 7);
        }
    }

    /**
     * Make visible the objet
     */
    public void makeVisible(){
        border.makeVisible();
        center.makeVisible();
        if(tile != null){
            tile.makeVisible();
        }
        if(hasHole){
            roto.makeVisible();
        }
        this.isVisible = true;
    }

    /**
     * Make invisible the objet
     */
    public void makeInvisible(){
        border.makeInvisible();
        if(tile != null){
            tile.makeInvisible();
        }
        if(hasHole){
            roto.makeInvisible();
        }
        this.isVisible = false;
    }
    
    public int getXPosition(){
        return x;
    }
    
    public int getYPosition(){
        return y;
    }

    /**
     * Set the boolean in case of the checkbox is occuped;
     * @param isOccuped determines whether it has a tile or not
     */
    public void setIsOccuped(boolean isOccuped){
        this.isOccuped = isOccuped;
        if(!isOccuped){
            setTile(null);
        }
    }

    /**
     * Add the tile that can be in the checkbox
     * @param tile the tile that is assigned to the checkbox
     */
    public void setTile(Tile tile){
        this.tile = tile;
    }


    public boolean isHasHole(){
        return this.hasHole;
    }

    /**
     * Set the boolean in case of the checkbox has a Hole;
     */
    public void setHasHole(){
        this.hasHole=true;
        draw();
    }

    public boolean isOccuped(){
        return this.isOccuped;   
    }

    /**
     * Move the object visually in the space(not in the board)
     * @param x the new position in x
     * @param y the new position in y
     */
    public void moveTo(int x,int y){
        border.moveTo(x,y);
        draw();
    }

    public Tile getTile(){
        return tile;
    }

    public boolean isVisible(){
        return this.isVisible;
    }

    public Rectangle getBorder(){
        return border;
    }
    
    public Rectangle getCenter(){
        return center;
    }
}
