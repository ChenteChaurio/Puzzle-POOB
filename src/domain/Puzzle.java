package domain;

import java.util.*;
import javax.swing.JOptionPane;

/**
 * Write a description of class TiltingTiles here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Puzzle {
    private Checkbox[][] tablero;
    private Checkbox[][] tableroEnding;
    private ArrayList<Tile> tiles;
    private ArrayList<TreeSet<Tile>> glueds;
    private char[][] ending;
    private final int height;
    private final int width;


    /**
     * First constructor for objects of class Puzzle
     * @param h the height of the board
     * @param w the width of the board
     */
    public Puzzle(int h, int w) {
        tablero = new Checkbox[h][w];
        tableroEnding = new Checkbox[h][w];
        tiles = new ArrayList<>();
        glueds = new ArrayList<>();
        height = h;
        width = w;
        this.ending = new char[height][width];
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                Checkbox celd = new Checkbox(i, j, this);
                Checkbox otherCeld = new Checkbox(i, j, this);
                tablero[i][j] = celd;
                tableroEnding[i][j] = otherCeld;
                celd.moveTo((celd.getBorder().getXPosition() + celd.getBorder().getWidth() * j), (celd.getBorder().getYPosition() + celd.getBorder().getHeight() * i));
                otherCeld.moveTo((otherCeld.getBorder().getXPosition() + otherCeld.getBorder().getWidth() * j) + 300, (otherCeld.getBorder().getYPosition() + otherCeld.getBorder().getHeight() * i));
                ending[i][j] = '.';
            }
        }
    }


    /**
     * The second constructor
     * @param ending the final matrix
     */
    public Puzzle(char[][] ending) {
        tablero = new Checkbox[ending.length][ending[0].length];
        tableroEnding = new Checkbox[ending.length][ending[0].length];
        tiles = new ArrayList<>();
        height = ending.length;
        width = ending[0].length;
        glueds = new ArrayList<>();
        this.ending = ending;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                Checkbox celd = new Checkbox(i, j, this);
                Checkbox otherCeld = new Checkbox(i, j, this);
                tablero[i][j] = celd;
                tableroEnding[i][j] = otherCeld;
                celd.moveTo((celd.getBorder().getXPosition() + celd.getBorder().getWidth() * j), (celd.getBorder().getYPosition() + celd.getBorder().getHeight() * i));
                otherCeld.moveTo((otherCeld.getBorder().getXPosition() + otherCeld.getBorder().getWidth() * j) + 300, (otherCeld.getBorder().getYPosition() + otherCeld.getBorder().getHeight() * i));
            }
        }
        createVisualEnding();

    }

    /**
     * Make the visual ending matrix
     */
    private void createVisualEnding() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (ending[i][j] != '.') {
                    String color = ColorMap.getColor(ending[i][j]);
                    Checkbox celd = tableroEnding[i][j];
                    Tile baldosa = new Tile(i, j, color, this);
                    celd.setIsOccuped(true);
                    celd.setTile(baldosa);
                    baldosa.move(celd.getCenter().getXPosition(), celd.getCenter().getYPosition());
                    if (tableroEnding[0][0].isVisible()) {
                        baldosa.makeVisible();
                    }
                }
            }
        }
    }


    /**
     * The third constructor
     * @param starting the initial matrix
     * @param ending the final matrix
     */
    public Puzzle(char[][] starting, char[][] ending) {
        tablero = new Checkbox[starting.length][starting[0].length];
        tableroEnding = new Checkbox[starting.length][starting[0].length];
        tiles = new ArrayList<>();
        height = starting.length;
        width = starting[0].length;
        glueds = new ArrayList<>();
        this.ending = ending;
        for (int i = 0; i < starting.length; i++) {
            for (int j = 0; j < starting[i].length; j++) {
                Checkbox celd = new Checkbox(i, j, this);
                Checkbox otherCeld = new Checkbox(i, j, this);
                tablero[i][j] = celd;
                tableroEnding[i][j] = otherCeld;
                celd.moveTo((celd.getBorder().getXPosition() + celd.getBorder().getWidth() * j), (celd.getBorder().getYPosition() + celd.getBorder().getHeight() * i));
                otherCeld.moveTo((otherCeld.getBorder().getXPosition() + otherCeld.getBorder().getWidth() * j) + 300, (otherCeld.getBorder().getYPosition() + otherCeld.getBorder().getHeight() * i));
                if (starting[i][j] != '.') {
                    String color = ColorMap.getColor(starting[i][j]);
                    addTile(i, j, color);
                }
            }
        }
        createVisualEnding();
    }

    /**
     * Allows to add a tile to the board
     * @param row the row in the board
     * @param column the column in the board
     * @param color the color of the tile
     */
    public void addTile(int row, int column, String color) {
        if(!isTileAt(row,column)){
            Tile baldosa = new Tile(row, column, color, this);
            Checkbox celd = this.tablero[row][column];
            celd.setIsOccuped(true);
            celd.setTile(baldosa);
            baldosa.move(celd.getCenter().getXPosition(), celd.getCenter().getYPosition());
            if (tablero[0][0].isVisible()) {
                baldosa.makeVisible();
            }
            tiles.add(baldosa);
        }
    }

    /**
     * Allows to delete a tile to the board
     * @param row the row in the board
     * @param column the column in the board
     */
    public void deleteTile(int row, int column) {
        tablero[row][column].getTile().makeInvisible();
        tiles.remove(tablero[row][column].getTile());
        tablero[row][column].setIsOccuped(false);
    }

    /**
     * Relocate a tile in other position
     * @param from int[] with the position of the tile to be moved
     * @param to
     */
    public void relocateTile(int[] from, int[] to) {
        if (!isTileAt(from[0], from[1])) {
            JOptionPane.showMessageDialog(null, "Error: No hay ninguna baldosa");
        }else{
            if(!tablero[to[0]][to[1]].isHasHole()){
                getTileAt(from[0], from[1]).moveTile(to[0], to[1]);
            }else{
                deleteTile(from[0], from[1]);
            }
        }
    }


    /**
     * Add glue in a tile
     * @param row the row in the board
     * @param column the column in the board
     */
    public void addGlue(int row, int column) {
        if (!tablero[row][column].isOccuped()) {
            JOptionPane.showMessageDialog(null, "Error: No hay ninguna baldosa");
        } else {
            tablero[row][column].getTile().addGlue(new Glue(tablero[row][column].getTile(), this));
            createGlueds(tablero[row][column].getTile().getConecciones());
        }
    }

    /**
     * Make the all connections of the tiles is glued
     * @param stickys the connections of the tile glue
     */
    private void createGlueds(TreeSet<Tile> stickys) {
        boolean mezclado = false;
        for (Set<Tile> glued : glueds) {
            Set<Tile> interseccion = new TreeSet<>(glued);
            interseccion.retainAll(stickys);
            if (!interseccion.isEmpty()) {
                glued.addAll(stickys);
                mezclado = true;
                break;
            }
        }
        if (!mezclado) {
            glueds.add(stickys);
        }
        setTileGlueds();
    }

    /**
     * Set all tiles glueds with her connections
     */
    private void setTileGlueds() {
        for (TreeSet<Tile> glued : glueds) {
            for (Tile tile : glued) {
                tile.setConecciones(glued);
            }
        }
    }

    /**
     * Delete glue in a tile
     * @param row the row in the board
     * @param column the column in the board
     */
    public void deleteGlue(int row, int column) {
        if (!tablero[row][column].isOccuped()) {
            JOptionPane.showMessageDialog(null, "Error: No hay ninguna baldosa");
        } else {
            tablero[row][column].getTile().deleteGlue();
        }
    }

    /**
     * Check if the position is valid on the board
     * @param row the row of the board
     * @param col the column of the board
     * @return
     */
    public boolean isValidPosition(int row, int col) {
        return row >= 0 && row < height && col >= 0 && col < width;
    }

    /**
     * Check if the actual matrix is equals to ending
     * @return boolean
     */
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

    /**
     * Make the Arraygement of the Puzzle
     * @return char[][]
     */
    public char[][] actualArraygement() {
        char[][] actualArray = new char[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (tablero[i][j].isOccuped()) {
                    actualArray[i][j] = tablero[i][j].getTile().getColor().charAt(0);
                } else {
                    actualArray[i][j] = '.';
                }
            }
        }
        return actualArray;
    }

    /**
     * Make visible all objets of puzzle
     */
    public void makeVisible() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                tablero[i][j].makeVisible();
                tableroEnding[i][j].makeVisible();
            }
        }
        for (Tile tile : tiles) {
            tile.makeVisible();
        }
    }

    /**
     * Make invisible all objets of puzzle
     */
    public void makeInvisible() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                tablero[i][j].makeInvisible();
                tableroEnding[i][j].makeInvisible();
            }
        }
        for (Tile tile : tiles) {
            tile.makeInvisible();
        }
    }

    /**
     * Move all tiles in the specific direction
     * @param direction char with the direction
     */
    public void tilt(char direction) {
        switch (direction) {
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

    /**
     * Finish the program
     */
    private void finish() {
        System.exit(0);
    }

    /**
     * Move all tiles in the up direction
     */
    private void tiltUp() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (isTileAt(i, j)) {
                    Tile tile = getTileAt(i, j);
                    if (!tile.isSticky()) {
                        boolean foundInSet = false;
                        for (Set<Tile> set : glueds) {
                            if (set.contains(tile)) {
                                foundInSet = true;
                                break;
                            }
                        }
                        if (foundInSet) {
                            continue;
                        }
                        ArrayList<Checkbox> camino = path('u', new int[]{i, j});
                        for (int x = camino.size() - 1; x >= 0; x--) {
                            if  (camino.get(x).isHasHole()) {
                                relocateTile(new int[]{i, j}, new int[]{camino.get(x).getXPosition(), (camino.get(x).getYPosition())});
                                break;
                            }
                            if (camino.get(x).isOccuped()) {
                                relocateTile(new int[]{i, j}, new int[]{camino.get(x).getXPosition() + 1, (camino.get(x).getYPosition())});
                                break;
                            }
                            if (x == 0 && !camino.get(x).isOccuped()) {
                                relocateTile(new int[]{i, j}, new int[]{camino.get(x).getXPosition(), camino.get(x).getYPosition()});
                                break;
                            }
                        }
                    } else {
                        TreeSet<Tile> auxy = tile.getConecciones();
                        for (int x = 0; x < auxy.first().getXPosition(); x++) {
                            if (!tablero[x][j].isOccuped()) {
                                relocateTile(new int[]{auxy.first().getXPosition(), auxy.first().getYPosition()}, new int[]{x, j});
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * change the actual matrix with the ending matrix
     */
    public void exchange(){
        makeInvisible();
        tiles.clear();
        char[][] copy =actualArraygement();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int posX = tablero[i][j].getBorder().getXPosition();
                int posY = tablero[i][j].getBorder().getYPosition();
                int posX2 = tableroEnding[i][j].getBorder().getXPosition();
                int posY2 = tableroEnding[i][j].getBorder().getYPosition();
                tablero[i][j].moveTo(posX2, posY2);
                tableroEnding[i][j].moveTo(posX, posY);
                if (copy[i][j] != '.') {
                    String color = ColorMap.getColor(copy[i][j]);
                    addTile(i, j, color);
                }
            }
        }
        createVisualEnding();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                Checkbox c = tablero[i][j];
                tablero[i][j] = tableroEnding[i][j];
                tableroEnding[i][j] = c;
            }
        }
        makeVisible();
    }

    /**
     * Makes visible the chips that will not move in case of any tilting
     * @return int[][] with the positions of the tiles
     */
    public int[][] fixedTiles() {
        HashSet<String> fixedTilePositionsSet = new HashSet<>();
        char[] tilts = {'u', 'd', 'l', 'r'};
        ArrayList<HashSet<String>> tiltTilePositionsSets = new ArrayList<>();
        for (char actualTilt : tilts) {
            Puzzle copy = new Puzzle(actualArraygement(), ending);
            copy.tilt(actualTilt);
            ArrayList<Tile> moveTiles = copy.getTiles();
            HashSet<String> tilePositionsSet = new HashSet<>();
            for (Tile tile : moveTiles) {
                String position = tile.getXPosition() + "," + tile.getYPosition();
                tilePositionsSet.add(position);
            }
            tiltTilePositionsSets.add(tilePositionsSet);
            if (fixedTilePositionsSet.isEmpty()) {
                fixedTilePositionsSet.addAll(tilePositionsSet);
            } else {
                fixedTilePositionsSet.retainAll(tilePositionsSet);
            }
        }
        ArrayList<String> fixedPositionsList = new ArrayList<>(fixedTilePositionsSet);
        int[][] fixedTilePositions = new int[fixedPositionsList.size()][2];
        for (int i = 0; i < fixedPositionsList.size(); i++) {
            String position = fixedPositionsList.get(i);
            String[] pos = position.split(",");
            fixedTilePositions[i][0] = Integer.parseInt(pos[0]);
            fixedTilePositions[i][1] = Integer.parseInt(pos[1]);
            for (int j = 0; j < 15; j++) {
                tablero[Integer.parseInt(pos[0])][Integer.parseInt(pos[1])].makeInvisible();
                tablero[Integer.parseInt(pos[0])][Integer.parseInt(pos[1])].makeVisible();
            }
            System.out.println("Ficha fija en posición: (" + pos[0] + ", " + pos[1] + ")");
        }
        return fixedTilePositions;
    }


    public ArrayList<Tile> getTiles(){
        return tiles;
    }

    /**
     * Displays the number of tiles remaining to be set
     * @return count with the number
     */
    public int misplacedTiles(){
        int count = getTiles().size();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (tableroEnding[i][j].isOccuped()&&tablero[i][j].isOccuped()) {
                    if(Objects.equals(tableroEnding[i][j].getTile().getColor(), tablero[i][j].getTile().getColor())){
                        count -=1;
                    }
                }
            }
        }
        return count;
    }

    /**
     * Move all tiles in the down direction
     */
    private void tiltDown() {
        for (int i = height - 1; i >= 0; i--) {
            for (int j = width - 1; j >= 0; j--) {
                if (isTileAt(i, j)) {
                    Tile tile = getTileAt(i, j);
                    if (!tile.isSticky()) {
                        boolean foundInSet = false;
                        for (Set<Tile> set : glueds) {
                            if (set.contains(tile)) {
                                foundInSet = true;
                                break;
                            }
                        }
                        if (foundInSet) {
                            continue;
                        }
                        ArrayList<Checkbox> camino = path('d', new int[]{i, j});
                        for (Checkbox checkbox : camino) {
                            if (checkbox.isHasHole()){
                                relocateTile(new int[]{i, j}, new int[]{checkbox.getXPosition(), checkbox.getYPosition()});
                                break;
                            }
                            if (checkbox.isOccuped()) {
                                relocateTile(new int[]{i, j}, new int[]{checkbox.getXPosition() - 1, checkbox.getYPosition()});
                                break;
                            }
                            if (checkbox == camino.get(camino.size() - 1) && !checkbox.isOccuped()) {
                                relocateTile(new int[]{i, j}, new int[]{checkbox.getXPosition(), checkbox.getYPosition()});
                                break;
                            }
                        }
                    } else {
                        TreeSet<Tile> auxy = tile.getConecciones();
                        for (int x = auxy.last().getXPosition(); x < height; x++) {
                            if (!tablero[x][j].isOccuped()) {
                                relocateTile(new int[]{auxy.last().getXPosition(), auxy.last().getYPosition()}, new int[]{x, j});
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Move all tiles in the right direction
     */
    private void tiltRight() {
        for (int j = width - 1; j >= 0; j--) {
            for (int i = 0; i < height; i++) {
                if (isTileAt(i, j)) {
                    Tile tile = getTileAt(i, j);
                    if (!tile.isSticky()) {
                        boolean foundInSet = false;
                        for (Set<Tile> set : glueds) {
                            if (set.contains(tile)) {
                                foundInSet = true;
                                break;
                            }
                        }
                        if (foundInSet) {
                            continue;
                        }
                        ArrayList<Checkbox> camino = path('r', new int[]{i, j});
                        for (Checkbox checkbox : camino) {
                            if (checkbox.isHasHole()){
                                relocateTile(new int[]{i, j}, new int[]{checkbox.getXPosition(), checkbox.getYPosition()});
                                break;
                            }
                            if (checkbox.isOccuped()) {
                                relocateTile(new int[]{i, j}, new int[]{checkbox.getXPosition(), checkbox.getYPosition() - 1});
                                break;
                            }
                            if (checkbox == camino.get(camino.size() - 1) && !checkbox.isOccuped()) {
                                relocateTile(new int[]{i, j}, new int[]{checkbox.getXPosition(), checkbox.getYPosition()});
                                break;
                            }
                        }
                    } else {
                        TreeSet<Tile> auxy = tile.getConecciones();
                        Tile tileMayorY = auxy.first();
                        for (Tile tileMoreRigth : auxy) {
                            if (tileMoreRigth.getYPosition() > tileMayorY.getYPosition()) {
                                tileMayorY = tileMoreRigth;
                            }
                        }
                        for (int x = tileMayorY.getYPosition(); x < width; x++) {
                            if (!tablero[i][x].isOccuped()) {
                                relocateTile(new int[]{tileMayorY.getXPosition(), tileMayorY.getYPosition()}, new int[]{i, x});
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Move all tiles in the left direction
     */
    private void tiltLeft() {
        for (int j = 0; j < width; j++) {
            for (int i = 0; i < height; i++) {
                if (isTileAt(i, j)) {
                    Tile tile = getTileAt(i, j);
                    if (!tile.isSticky()) {
                        boolean foundInSet = false;
                        for (Set<Tile> set : glueds) {
                            if (set.contains(tile)) {
                                foundInSet = true;
                                break;
                            }
                        }
                        if (foundInSet) {
                            continue;
                        }
                        ArrayList<Checkbox> camino = path('l', new int[]{i, j});
                        for (int x = camino.size() - 1; x >= 0; x--) {
                            if (camino.get(x).isHasHole()){
                                relocateTile(new int[]{i, j}, new int[]{camino.get(x).getXPosition(), (camino.get(x).getYPosition())});
                                break;
                            }
                            if (camino.get(x).isOccuped()) {
                                relocateTile(new int[]{i, j}, new int[]{camino.get(x).getXPosition(), (camino.get(x).getYPosition() + 1)});
                                break;
                            }
                            if (x == 0 && !camino.get(x).isOccuped()) {
                                relocateTile(new int[]{i, j}, new int[]{camino.get(x).getXPosition(), camino.get(x).getYPosition()});
                                break;
                            }
                        }
                    } else {
                        TreeSet<Tile> auxy = tile.getConecciones();
                        Tile tileMenorY = auxy.first();
                        for (Tile tileMoreleft : auxy) {
                            if (tileMoreleft.getYPosition() < tileMenorY.getYPosition()) {
                                tileMenorY = tileMoreleft;
                            }
                        }
                        for (int x = 0; x < tileMenorY.getYPosition(); x++) {
                            if (!tablero[i][x].isOccuped()) {
                                relocateTile(new int[]{tileMenorY.getXPosition(), tileMenorY.getYPosition()}, new int[]{i, x});
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Auxiliary method to create a path forward
     * @param direction the direction to where I want the path
     * @param position the position from which the path starts
     * @return path
     */
    private ArrayList<Checkbox> path(char direction, int[] position) {
        ArrayList<Checkbox> path = new ArrayList<>();
        if (direction == 'u') {
            for (int i = 0; i < position[0]; i++) {
                path.add(tablero[i][position[1]]);
            }
        } else if (direction == 'r') {
            for (int i = position[1] + 1; i < width; i++) {
                path.add(tablero[position[0]][i]);
            }
        } else if (direction == 'l') {
            for (int i = 0; i < position[1]; i++) {
                path.add(tablero[position[0]][i]);
            }
        } else if (direction == 'd') {
            for (int i = position[0] + 1; i < height; i++) {
                path.add(tablero[i][position[1]]);
            }
        }
        return path;
    }

    /**
     * Make a hole in the board
     * @param row the row in the board
     * @param column the column in the board
     */
    public void makeHole(int row, int column) {
        if(!isTileAt(row, column)&&isValidPosition(row, column)){
            tablero[row][column].setHasHole();
        }
    }

    /**
     * Make a aleatory tilt
     */
    public void tilt(){
        char[] direcciones = {'u', 'd', 'l', 'r'};
        Random random = new Random();
        int indiceAleatorio = random.nextInt(direcciones.length);
        tilt(direcciones[indiceAleatorio]);
    }


    /**
     * Check if the checkbox in this position has a tile
     * @param row the row of the board
     * @param col the column of the board
     * @return boolean
     */
    private boolean isTileAt(int row, int col) {
        return tablero[row][col].isOccuped();
    }

    /**
     * Get the Tile in this position
     * @param row the row of the board
     * @param col the column of the board
     * @return Tile
     */
    private Tile getTileAt(int row, int col) {
        return tablero[row][col].getTile();
    }

    public Checkbox[][] getTablero(){
        return tablero;
    }
}


