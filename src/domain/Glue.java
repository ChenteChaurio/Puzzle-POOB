package domain;

import java.util.TreeSet;

public class Glue {
    private Tile baldoza;
    private Puzzle tablero;

    /**
     * Constructor for objects of class Glue
     * @param tile the tile to which glue is to be applied
     * @param puzzle the main class(has the board)
     */
    public Glue(Tile tile,Puzzle puzzle){
        baldoza = tile;
        tablero = puzzle;
    }

    /**
     * Create the connections of the tile
     * @return conections a TreeSet with the tiles connected with the tile
     */
    public TreeSet<Tile> createConections(){
        TreeSet<Tile> conections = new TreeSet<>();
        conections.add(baldoza);
        int[][] tiles = {{1,0},{-1,0},{0,-1},{0,1}};
        for(int[] tile : tiles){
            if(tablero.isValidPosition(tile[0],tile[1])){Tile newTile = tablero.getTablero()[baldoza.getXPosition()+tile[0]][baldoza.getYPosition()+tile[1]].getTile();
                if(newTile != null && !(newTile instanceof Freelance)){
                    conections.add(newTile);
                }}
        }
        return conections;
    }
}
