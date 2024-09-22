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
        tiles = new HashMap<>(); //---------------------
        connections = new HashMap<>();
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
    

    public void addGlue(int row, int column) {
        ArrayList<Integer> position = new ArrayList<>();
        position.add(row);
        position.add(column);
        if (tiles.get(position) == null) {
            JOptionPane.showMessageDialog(null, "Error: No hay ninguna baldosa");
        } else {
            Tile tile = tiles.get(position);
            tile.setIsSticky(true);
            createConnections(position);
        }
    }


    public void deleteGlue(int row, int column) {
        ArrayList<Integer> position = new ArrayList<>();
        position.add(row);
        position.add(column);
        if (tiles.get(position) == null) {
            JOptionPane.showMessageDialog(null, "Error: No hay ninguna baldosa");
        } else {
            Tile tile = tiles.get(position);
            tile.setIsSticky(false);
            removeConnections(position);
        }
    }

    private void createConnections(ArrayList<Integer> position) {
        int row = position.get(0);
        int col = position.get(1);
        Tile currentTile = tiles.get(position);

        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}}; // Up, Down, Left, Right

        for (int[] dir : directions) {
            int newRow = row + dir[0];
            int newCol = col + dir[1];
            ArrayList<Integer> neighborPosition = new ArrayList<>();
            neighborPosition.add(newRow);
            neighborPosition.add(newCol);

            if (isValidPosition(newRow, newCol) && tiles.containsKey(neighborPosition)) {
                Tile neighborTile = tiles.get(neighborPosition);
                if (neighborTile.getIsSticky()) {
                    if (!connections.containsKey(currentTile)) {
                        connections.put(currentTile, new ArrayList<>());
                    }
                    connections.get(currentTile).add(neighborTile);

                    if (!connections.containsKey(neighborTile)) {
                        connections.put(neighborTile, new ArrayList<>());
                    }
                    connections.get(neighborTile).add(currentTile);
                }
            }
        }
    }

    private void removeConnections(ArrayList<Integer> position) {
        Tile tile = tiles.get(position);
        if (connections.containsKey(tile)) {
            for (Tile connectedTile : connections.get(tile)) {
                connections.get(connectedTile).remove(tile);
            }
            connections.remove(tile);
        }
    }

    private boolean isValidPosition(int row, int col) {
        return row >= 0 && row < height && col >= 0 && col < width;
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

    public void tilt(char direction){
        switch (direction){
            case 'r':
                tiltRight();
                break;
            case 'l':    
                tiltLeft();
                break;
            case 'u':
                tiltUp();
                break;
            case 'd':
                tiltDown();
                break;
        }
    }
    
    private void tiltRight() {
        for (int i = 0; i < height; i++) {
            int lastEmptyColumn = width - 1;
            for (int j = width - 1; j >= 0; j--) {
                if (isTileAt(i, j)) {
                    Tile tile = getTileAt(i, j);
                    if (j != lastEmptyColumn) {
                        moveTile(i, j, i, lastEmptyColumn);
                    }
                    lastEmptyColumn--;
                }
            }
        }
    }

    private void tiltLeft() {
        for (int i = 0; i < height; i++) {
            int lastEmptyColumn = 0;
            for (int j = 0; j < width; j++) {
                if (isTileAt(i, j)) {
                    Tile tile = getTileAt(i, j);
                    if (j != lastEmptyColumn) {
                        moveTile(i, j, i, lastEmptyColumn);
                    }
                    lastEmptyColumn++;
                }
            }
        }
    }

    private void tiltDown() {
        for (int j = 0; j < width; j++) {
            int lastEmptyRow = height - 1;
            for (int i = height - 1; i >= 0; i--) {
                if (isTileAt(i, j)) {
                    Tile tile = getTileAt(i, j);
                    if (i != lastEmptyRow) {
                        moveTile(i, j, lastEmptyRow, j);
                    }
                    lastEmptyRow--;
                }
            }
        }
    }

    private void tiltUp() {
        for (int j = 0; j < width; j++) {
            int lastEmptyRow = 0;
            for (int i = 0; i < height; i++) {
                if (isTileAt(i, j)) {
                    Tile tile = getTileAt(i, j);
                    if (i != lastEmptyRow) {
                        moveTile(i, j, lastEmptyRow, j);
                    }
                    lastEmptyRow++;
                }
            }
        }
    }

    private boolean isTileAt(int row, int col) {
        ArrayList<Integer> position = new ArrayList<>();
        position.add(row);
        position.add(col);
        return tiles.containsKey(position);
    }

    private Tile getTileAt(int row, int col) {
        ArrayList<Integer> position = new ArrayList<>();
        position.add(row);
        position.add(col);
        return tiles.get(position);
    }

    private void moveTile(int fromRow, int fromCol, int toRow, int toCol) {
        Tile tile = getTileAt(fromRow, fromCol);
        ArrayList<Integer> oldPosition = new ArrayList<>();
        oldPosition.add(fromRow);
        oldPosition.add(fromCol);
        ArrayList<Integer> newPosition = new ArrayList<>();
        newPosition.add(toRow);
        newPosition.add(toCol);

        tiles.remove(oldPosition);
        tiles.put(newPosition, tile);

        Checkbox oldCell = celdas.get(oldPosition);
        Checkbox newCell = celdas.get(newPosition);

        oldCell.setIsOccuped(false);
        newCell.setIsOccuped(true);

        tile.moveTo(newCell.getCenter().getXPosition(), newCell.getCenter().getYPosition());
    }
    
    
    
    

    
}


