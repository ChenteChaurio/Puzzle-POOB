package domain;

public class Fixed extends Tile{
    /**
     * Constructor for objects of class Tile
     *
     * @param x
     * @param y
     * @param color
     * @param puzzle
     */
    public Fixed(int x, int y, String color, Puzzle puzzle) {
        super(x, y, color, puzzle);
    }

    @Override
    public void move(int dx, int dy){

    }

}
