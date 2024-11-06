package domain;

public class Fixed extends Tile{
    /**
     * Constructor for objects of class Tile
     *
     * @param x the position x in the board
     * @param y the position y in the board
     * @param color the color of the tile
     * @param puzzle the main class(has the board)
     */
    public Fixed(int x, int y, String color, Puzzle puzzle) {
        super(x, y, color, puzzle);
    }

    /**
     * Override the method so that it cannot be moved on the board.
     * @param dx
     * @param dy
     */
    @Override
    public void move(int dx, int dy){

    }

}
