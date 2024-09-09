
/**
 * Write a description of class Tile here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Tile
{
    private int x;
    private int y;
    private String color;
    private boolean isSticky;
    private Rectangle figure;

    /**
     * Constructor for objects of class Tile
     */
    public Tile(int x,int y,String color){
        this.x = x;
        this.y = y;
        this.color = color;
        this.figure = new Rectangle(30,30,color);
        isSticky = false;
    }

    public void makeVisible(){
        figure.makeVisible();
    }
    
    public void makeInvisible(){
        figure.makeInvisible();
    }
    
    public void setXPosition(int x){
        this.x = x;
    }
    
    public void setYPosition(int y){
        this.y = y;
    }
    
    public void setIsSticky(boolean isSticky){
        this.isSticky = isSticky;
    }
    
    public boolean getIsSticky(){
        return this.isSticky; 
    }
    
    public String getColor(){
        return this.color;
    }
    
    public void changeColor(String color){
        this.color = color;
        figure.changeColor(color);
    }
    
    public void moveTo(int x,int y){
        figure.moveTo(x,y);
    }
}
