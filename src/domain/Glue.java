package domain;

import java.util.TreeSet;

public class Glue {
    private Tile baldoza;
    private Puzzle tablero;
    public Glue(Tile tile,Puzzle puzzle){
        baldoza = tile;
        tablero = puzzle;
    }
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
