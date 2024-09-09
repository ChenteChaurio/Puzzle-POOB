import java.awt.*;
import java.awt.geom.*;
import javax.swing.JOptionPane;

/**
 * A circle that can be manipulated and that draws itself on a canvas.
 * 
 * @author  Michael Kolling and David J. Barnes
 * @version 1.0.  (15 July 2000) 
 */

public class Circle{

    public static final float PI=3.1416f;
    
    private int diameter;
    private int xPosition;
    private int yPosition;
    private float area;
    private String color;
    private boolean isVisible;
    

    public Circle(){
        diameter = 30;
        xPosition = 20;
        yPosition = 15;
        color = "blue";
        isVisible = false;
        valueArea();
    }
    
    public Circle(int diameter,String color,int xPos,int yPos){
        this.diameter = diameter;
        this.color = color;
        this.xPosition = xPos;
        this.yPosition = yPos;
        isVisible = false;
        valueArea();
    }

       
    public void makeVisible(){
        isVisible = true;
        draw();
    }
    

    public void makeInvisible(){
        erase();
        isVisible = false;
    }

    private void draw(){
        if(isVisible) {
            Canvas canvas = Canvas.getCanvas();
            canvas.draw(this, color, 
                new Ellipse2D.Double(xPosition, yPosition, 
                diameter, diameter));
            canvas.wait(10);
        }
    }

    private void erase(){
        if(isVisible) {
            Canvas canvas = Canvas.getCanvas();
            canvas.erase(this);
        }
    }
    
    /**
     * Move the circle a few pixels to the right.
     */
    public void moveRight(){
        moveHorizontal(20);
    }

    /**
     * Move the circle a few pixels to the left.
     */
    public void moveLeft(){
        moveHorizontal(-20);
    }

    /**
     * Move the circle a few pixels up.
     */
    public void moveUp(){
        moveVertical(-20);
    }

    /**
     * Move the circle a few pixels down.
     */
    public void moveDown(){
        moveVertical(20);
    }

    /**
     * Move the circle horizontally.
     * @param distance the desired distance in pixels
     */
    public void moveHorizontal(int distance){
        erase();
        xPosition += distance;
        draw();
    }

    /**
     * Move the circle vertically.
     * @param distance the desired distance in pixels
     */
    public void moveVertical(int distance){
        erase();
        yPosition += distance;
        draw();
    }

    /**
     * Slowly move the circle horizontally.
     * @param distance the desired distance in pixels
     */
    public void slowMoveHorizontal(int distance){
        int delta;

        if(distance < 0) {
            delta = -1;
            distance = -distance;
        } else {
            delta = 1;
        }

        for(int i = 0; i < distance; i++){
            xPosition += delta;
            draw();
        }
    }

    /**
     * Slowly move the circle vertically
     * @param distance the desired distance in pixels
     */
    public void slowMoveVertical(int distance){
        int delta;

        if(distance < 0) {
            delta = -1;
            distance = -distance;
        }else {
            delta = 1;
        }

        for(int i = 0; i < distance; i++){
            yPosition += delta;
            draw();
        }
    }

    /**
     * Change the size.
     * @param newDiameter the new size (in pixels). Size must be >=0.
     */
    public void changeSize(int newDiameter){
        erase();
        diameter = newDiameter;
        draw();
    }

    /**
     * Change the color. 
     * @param color the new color. Valid colors are "red", "yellow", "blue", "green",
     * "magenta" and "black".
     */
    public void changeColor(String newColor){
        color = newColor;
        draw();
    }
    
    /**
     * Prints the perimeter value
     */
    public void perimeter(){
        JOptionPane.showMessageDialog(null, "El perimetro es: " + valuePerimeter());
    }
    
    /**
     * Return the perimeter value
     */
    private float valuePerimeter(){
        return (float)(PI*this.diameter);
    }
    
    /**
     * Return the Area value
     */
    private float valueArea(){
        this.area = (float)(PI * Math.pow((this.diameter / 2.0), 2));
        return this.area;
    }
    
    /**
     * Return the Radio value
     */
    private int valueRadio(){
        int radio = (int)(Math.sqrt(this.area/PI));
        return radio;
    }
    
    /**
     * Change the area value whit an specific operation
     * @param operator the operation to perform
     * @param value value at which the area is operated
     */
    public void change(char operator, int value) {
        switch (operator) {
            case '+':
                if (this.area + value > 0){
                    this.area += value;
                    changeSize(valueRadio()*2);
                } else{
                    JOptionPane.showMessageDialog(null, "Error: Area negativa");
                } 
                break;
            case '-':
                if (this.area - value > 0){
                    this.area -= value;
                    changeSize(valueRadio()*2);
                } else{
                    JOptionPane.showMessageDialog(null, "Error: Area negativa");
                }  
                break;
            case '*':
                if (this.area * value > 0){
                    this.area *= value;
                    changeSize(valueRadio()*2);
                } else{
                    JOptionPane.showMessageDialog(null, "Error: Area negativa");
                }  
                break;
            case '/':
                if (value != 0) {
                    this.area /= value;
                    changeSize(valueRadio()*2);
                } else {
                    JOptionPane.showMessageDialog(null, "Error: Divición por 0");
                }
                break;
        }
    }
    
    /**
     * Moves the circle a random distance and a random angle
     */
    public void roll(int times, int maxDistance){
        int angle = genRandomNum(0,360);
        int distance = genRandomNum(1,maxDistance);
        int newXPos = (int)(distance * Math.cos(Math.toRadians(angle)));
        int xPos = newXPos + xPosition;
        int newYPos = (int)(distance * Math.sin(Math.toRadians(angle)));
        int yPos = newYPos + yPosition;
        moveToMovement(xPos,yPos,times,this.xPosition,this.yPosition);
    }
    
    /**
     * Gens a random number
     */
    private int genRandomNum(int initial, int ranFinal){
        return (int)(Math.random() * (ranFinal - initial + 1)) + initial;
    }
    
    /**
     * Moves the circle whit movement
     */
    private void moveToMovement(int xPos,int yPos,int time,int xPosition,int yPosition){
        int dx = xPos - xPosition;
        int dy = yPos - yPosition;
        for (int i = 0; i <= time; i++) {
            float t = (float) i / (time-1);
            int x = (int) (xPosition + dx * t);
            int y = (int) (yPosition + dy * t);
            moveTo(x, y);
        }
    }
    
    /**
     * Moves the circle to a desired position
     */
    private void moveTo(int xPos,int yPos){
        erase();
        this.yPosition = yPos;
        this.xPosition = xPos;
        draw();
    }

    /**
     * Reduces the circle until it disappears 
     */
    public void reduceCircle(){
        while (diameter >= 0) {
            changeSize(diameter - 1);  
            try {
                Thread.sleep(50);  
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
