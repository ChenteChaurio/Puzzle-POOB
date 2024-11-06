package domain;

public class Freelance extends Tile{
    /**
     * Constructor for objects of class Tile
     *
     * @param x
     * @param y
     * @param color
     * @param puzzle
     */
    public Freelance(int x, int y, String color, Puzzle puzzle) {
        super(x, y, color, puzzle);
    }

    @Override
    public void addGlue(Glue sticky){
    }

    @Override
    public void deleteGlue(){
    }
}
