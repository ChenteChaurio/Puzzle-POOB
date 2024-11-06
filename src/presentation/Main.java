package presentation;

import domain.Checkbox;
import domain.Glue;
import domain.Puzzle;
import domain.Tile;

public class Main {
    public static void main(String[] args) {
        Puzzle puzzle = new Puzzle(2,2);
        puzzle.addTile(0,0,"red");
        puzzle.addTile(0,1,"red");
        puzzle.addTile(1,0,"red");
        puzzle.addTile(1,1,"red");
        puzzle.makeVisible();
    }
}