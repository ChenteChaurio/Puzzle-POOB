package domain;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.TreeSet;

/**
 * Write a description of class Tile here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Tile implements Comparable<Tile>
{
    private int Posx;
    private int Posy;
    private String color;
    private Glue glue;
    private Rectangle figure;
    private Puzzle tablero;
    private TreeSet<Tile> conecciones;
    private ArrayList<Circle> pegante;

    /**
     * Constructor for objects of class Tile
     */
    public Tile(int x, int y, String color, Puzzle puzzle){
        this.Posx = x;
        this.Posy = y;
        this.color = color;
        this.tablero = puzzle;
        this.figure = new Rectangle(30,30,color);
        conecciones = new TreeSet<>();
        glue = null;
        draw();
    }

    private void draw(){
        pegante = new ArrayList<>();
        int[][] offsets = {
                {-10, -10}, {5, -10},{20, -10},{-10, 5},{-10, 20}, {20, 5},{20, 20},{5, 20}};
        if (glue instanceof Glue){
            for (int[] offset : offsets) {
                Circle glue = new Circle("cyan",figure.getXPosition() + offset[0],figure.getYPosition() + offset[1]);
                glue.makeVisible();
                pegante.add(glue);
            }
        }
    }

    private void erase(){
        for(Circle cir :pegante){
            cir.makeInvisible();
        }
    }

    public void makeVisible(){
        figure.makeVisible();
    }
    
    public void makeInvisible(){
        figure.makeInvisible();
    }
    
    public int getXPosition(){
        return this.Posx;
    }
    
    public int getYPosition(){
        return this.Posy;
    }
    
    public void setXPosition(int x){
        this.Posx = x;
    }
    
    public void setYPosition(int y){
        this.Posy = y;
    }
    
    public void addGlue(Glue sticky){
        this.glue = sticky;
        conecciones = sticky.createConections();
        draw();
    }

    public void deleteGlue(){
        this.glue = null;
        conecciones = null;
        draw();
    }

    public void setConecciones(TreeSet<Tile> newConecciones){
        this.conecciones = newConecciones;
    }

    public TreeSet<Tile> getConecciones() {
        return conecciones;
    }

    public void moveTile(int dx, int dy){
        if(conecciones.isEmpty()){
            // if(!tablero.isValidPosition(dx,dy)) throw new PuzzleException();
            // if(tablero.getTablero()[dx][dy].isOccuped()) throw new PuzzleException();
            if(tablero.isValidPosition(dx,dy)&&!tablero.getTablero()[dx][dy].isOccuped()){
                tablero.getTablero()[getXPosition()][getYPosition()].setIsOccuped(false);
                setXPosition(dx);
                setYPosition(dy);
                int newX = tablero.getTablero()[dx][dy].getCenter().getXPosition();
                int newY = tablero.getTablero()[dx][dy].getCenter().getYPosition();
                move(newX, newY);
                tablero.getTablero()[dx][dy].setIsOccuped(true);
                tablero.getTablero()[dx][dy].setTile(this);
            }
        }else{
            int x = dx-getXPosition();
            int y = dy-getYPosition();
            boolean validMove = true;
            for(Tile t : conecciones){
                if(!t.isValidMove(t.getXPosition()+x,t.getYPosition()+y)){ //throw new PuzzleException();
                    validMove = false;
                    break;
                }
            }
            if(validMove){
                for(Tile t : conecciones){
                    tablero.getTablero()[t.getXPosition()][t.getYPosition()].setIsOccuped(false);
                    int newX = t.getXPosition()+x;
                    int newY = t.getYPosition()+y;
                    t.setXPosition(newX);
                    t.setYPosition(newY);
                    int newGraficX = tablero.getTablero()[newX][newY].getCenter().getXPosition();
                    int newGraficY = tablero.getTablero()[newX][newX].getCenter().getYPosition();
                    t.move(newGraficX, newGraficY);
                    tablero.getTablero()[newX][newY].setIsOccuped(true);
                    tablero.getTablero()[newX][newY].setTile(t);
                }
            }
        }
    }

    public boolean isValidMove(int dx, int dy){
        boolean auxy = true;
        if(tablero.getTablero()[dx][dy].isOccuped()){
            auxy = false;
            if (conecciones.contains(tablero.getTablero()[dx][dy].getTile())) {
                auxy = true;
            }
        }
        return(tablero.isValidPosition(dx,dy)&&auxy);
    }






    public boolean isSticky(){
        return (glue != null);
    }
    
    public String getColor(){
        return this.color;
    }
    
    public void changeColor(String color){
        this.color = color;
        figure.changeColor(color);
    }
    public void move(int x,int y){
        erase();
        figure.moveTo(x,y);
        draw();
    }

    @Override
    public int compareTo(Tile otro) {
        if (this.Posx != otro.Posx) {
            return Integer.compare(this.Posx, otro.Posx);
        }
        return Integer.compare(this.Posy, otro.Posy);
    }
}
