import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JOptionPane;

/**
 * Write a description of class TiltingTiles here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Puzzle
{
    private ArrayList<ArrayList<Checkbox>> tablero;
    private HashMap<ArrayList<Integer>,Checkbox> celdas;
    private HashMap<Tile,ArrayList<Tile>> connections;
    private HashMap<ArrayList<Integer>,Tile> tiles;
    private char[][] ending;
    private int height;
    private int width;
    /**
     * Constructor for objects of class TiltingTiles
     */
    public Puzzle(int h,int w){
        tablero = new ArrayList<>();
        celdas = new HashMap<>();
        tiles = new HashMap<>();
        height = h;
        width = w;
        this.ending = new char[height][width];
        for (int i = 0 ;i<h; i++){
            ArrayList<Checkbox> celds = new ArrayList<>();
            for (int j = 0 ; j<w; j++){
                Checkbox celd = new Checkbox(i,j);
                ArrayList<Integer> position = new ArrayList<>();
                position.add(i); 
                position.add(j);
                celdas.put(position, celd);
                celd.moveTo(celd.getBorder().getXPosition()+(celd.getBorder().getWidth()*j)-5,celd.getBorder().getYPosition()+(celd.getBorder().getWidth()*i)-5);
                celds.add(celd);
                ending[i][j] = '.';
            }
            tablero.add(celds);
        }
    }
    
    public Puzzle(char[][] ending){
        tablero = new ArrayList<>();
        celdas = new HashMap<>();
        tiles = new HashMap<>();
        height = ending.length;
        width = ending[0].length;
        this.ending = ending;
        for (int i = 0 ;i<height; i++){
            ArrayList<Checkbox> celds = new ArrayList<>();
            for (int j = 0 ; j<width; j++){
                Checkbox celd = new Checkbox(i,j);
                ArrayList<Integer> position = new ArrayList<>();
                position.add(i); 
                position.add(j);
                celdas.put(position, celd);
                celd.moveTo(celd.getBorder().getXPosition()+(celd.getBorder().getWidth()*j)-5,celd.getBorder().getYPosition()+(celd.getBorder().getWidth()*i)-5);
                celds.add(celd);
            }
            tablero.add(celds);
        }
    }
    
    public Puzzle(char[][]starting,char[][] ending){
        tablero = new ArrayList<>();
        celdas = new HashMap<>();
        tiles = new HashMap<>();
        height = starting.length;
        width = starting[0].length;
        this.ending = ending;
        for (int i = 0; i < starting.length; i++) {  
            ArrayList<Checkbox> celds = new ArrayList<>();
            for (int j = 0; j < starting[i].length; j++) {  
                Checkbox celd = new Checkbox(i,j);
                ArrayList<Integer> position = new ArrayList<>();
                position.add(i); 
                position.add(j);
                celdas.put(position, celd);
                celd.moveTo(celd.getBorder().getXPosition()+(celd.getBorder().getWidth()*j)-5,celd.getBorder().getYPosition()+(celd.getBorder().getWidth()*i)-5);
                celds.add(celd);
                if (starting[i][j] != '.'){
                    String color = ColorMap.getColor(starting[i][j]);
                    addTile(i,j,color);
                }
            }
            System.out.println();  
        }
    }
    
    public void addTile(int row,int column,String color){
        Tile baldosa = new Tile(row,column,color);
        ArrayList<Integer> position = new ArrayList<>();
        position.add(row); 
        position.add(column);
        Checkbox celd = celdas.get(position);
        celd.setIsOccuped(true);
        baldosa.moveTo(celd.getCenter().getXPosition(),celd.getCenter().getYPosition());
        baldosa.makeVisible();
        tiles.put(position,baldosa);
    }
    
    public void deleteTile(int row,int column){
        ArrayList<Integer> position = new ArrayList<>();
        position.add(row); 
        position.add(column);
        tiles.get(position).makeInvisible();
        tiles.remove(position);
        celdas.get(position).setIsOccuped(false);
    }
    
    public void relocateTile(int[]from,int[]to){
        ArrayList<Integer> position = new ArrayList<>();
        position.add(from[0]); 
        position.add(from[1]);
        if (tiles.get(position)==null){
            JOptionPane.showMessageDialog(null, "Error: No hay ninguna baldosa");
        }else{
            String color = tiles.get(position).getColor();
            deleteTile(from[0],from[1]);
            if (celdas.get(position).getIsOccuped()){
                addTile(from[0],from[1],color);
            }else{
            addTile(to[0],to[1],color);
            //completar con el setIsSticky
            }
        }
    }
    
    public void addGlue(int row,int column){
        ArrayList<Integer> position = new ArrayList<>();
        position.add(row); 
        position.add(column);
        if (tiles.get(position)==null){
            JOptionPane.showMessageDialog(null, "Error: No hay ninguna baldosa");
        }else{
            tiles.get(position).setIsSticky(true);
            //createConeccions(position);
            //continuar
        }
    }
    
    public void deleteGlue(int row,int column){
        ArrayList<Integer> position = new ArrayList<>();
        position.add(row); 
        position.add(column);
        if (tiles.get(position)==null){
            JOptionPane.showMessageDialog(null, "Error: No hay ninguna baldosa");
        }else{
            tiles.get(position).setIsSticky(false);
            //deleteConeccions(position);
            //continuar
        }
    }
    
    public boolean isGoal() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (actualArraygement()[i][j] != ending[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }
    
    public char[][] actualArraygement(){
        char [][] actualArray = new char[height][width];
        for (ArrayList<Checkbox> fila : tablero) {
            for (Checkbox celd : fila) {
                if (celd.getIsOccuped()){
                    ArrayList<Integer> position = new ArrayList<>();
                    position.add(celd.getXPosition()); 
                    position.add(celd.getYPosition());
                    actualArray[celd.getXPosition()][celd.getYPosition()] = tiles.get(position).getColor().charAt(0);
                }else{
                    actualArray[celd.getXPosition()][celd.getYPosition()] = '.';
                }
            }
        }
        return actualArray;
    }
    
    public void makeVisible(){
        for (ArrayList<Checkbox> fila : tablero) {
            for (Checkbox celd : fila) {
                celd.makeVisible();
            }
        }
    }
    
    public void makeInvisible(){
        for (ArrayList<Checkbox> fila : tablero) {
            for (Checkbox celd : fila) {
                celd.makeInvisible();
            }
        }
    }
    
    private class ColorMap{
        private static HashMap<Character, String> colorMap;
        static {
            HashMap<Character, String> colorMap = new HashMap<>();
            colorMap.put('b', "blue");
            colorMap.put('r', "red");
            colorMap.put('g', "green");
            colorMap.put('y', "yellow");
            colorMap.put('m', "magenta");
            colorMap.put('i', "indigo");
            colorMap.put('g', "gray");
            colorMap.put('d', "darkGray");
            colorMap.put('l', "lightGray");
            colorMap.put('o', "orange");
            colorMap.put('c', "cyan");
            colorMap.put('p', "purple");
            colorMap.put('v', "violet");
            colorMap.put('n', "navy");
            colorMap.put('t', "turquoise");
            colorMap.put('g', "gold");
            colorMap.put('s', "silver");
        }
        public static String getColor(char character) {
            return colorMap.get(character);
        }
    }
}


