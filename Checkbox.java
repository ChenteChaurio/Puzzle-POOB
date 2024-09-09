
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
    private boolean isOccuped;

    /**
     * Constructor for objects of class Checkbox
     */
    public Checkbox(int x,int y){
        this.x = x;
        this.y = y;
        this.color = "indigo";
        this.border = new Rectangle(40,40,color);
        this.border.moveTo(100,100);
        this.center = new Rectangle(30,30,"white");
        this.isOccuped = false;
        draw();
    }

    public void draw(){
        this.center.moveTo(this.border.getXPosition()+5,this.border.getYPosition()+5);    
    }
    
    public void makeVisible(){
        border.makeVisible();
        center.makeVisible();
    }
    
    public void makeInvisible(){
        border.makeInvisible();
    }
    
    public void setXPosition(int x){
        this.x = x;
    }
    
    public void setYPosition(int y){
        this.y = y;
    }
    
    public int getXPosition(){
        return x;
    }
    
    public int getYPosition(){
        return y;
    }
    
    public void setIsOccuped(boolean isOccuped){
        this.isOccuped = isOccuped;
    }
    
    public boolean getIsOccuped(){
        return this.isOccuped;   
    }
    
    public void changeColor(String color){
        this.color = color;
        border.changeColor(color);
    }
    
    public void moveTo(int x,int y){
        border.moveTo(x,y);
        draw();
    }
    
    public Rectangle getBorder(){
        return border;
    }
    
    public Rectangle getCenter(){
        return center;
    }
}
