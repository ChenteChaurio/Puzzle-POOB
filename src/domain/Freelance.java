package domain;

public class Freelance extends Tile{
    /**
     * Constructor for objects of class Tile
     *
     * @param x the position x in the board
     * @param y the position y in the board
     * @param color the color of the tile
     * @param puzzle the main class(has the board)
     */
    public Freelance(int x, int y, String color, Puzzle puzzle) {
        super(x, y, color, puzzle);
    }

    /**
     * Overwriting of the method so that it cannot stick
     * @param sticky the type of glue
     */
    @Override
    public void addGlue(Glue sticky){
    }

    /**
     * Overwrite the method so that it does not generate logic errors.
     */
    @Override
    public void deleteGlue(){
    }
}
