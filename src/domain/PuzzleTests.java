package domain;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class PuzzleTests {
    private Puzzle puzzle;

    @Before
    public void setUp() {
        puzzle = new Puzzle(3, 3);
    }

    @Test
    public void shouldCreatePuzzleWithCorrectDimensions() {
        assertEquals(3, puzzle.getTablero().length);
        assertEquals(3, puzzle.getTablero()[0].length);
    }

    @Test
    public void shouldAddAndDeleteTile() {
        // Añadir ficha
        puzzle.addTile(0, 0, "red");
        assertTrue(puzzle.getTablero()[0][0].isOccuped());

        // Eliminar ficha
        puzzle.deleteTile(0, 0);
        assertFalse(puzzle.getTablero()[0][0].isOccuped());
    }

    @Test
    public void shouldRelocateTile() {
        // Añadir ficha y moverla
        puzzle.addTile(0, 0, "red");
        puzzle.relocateTile(new int[]{0, 0}, new int[]{1, 1});

        assertFalse(puzzle.getTablero()[0][0].isOccuped());
        assertTrue(puzzle.getTablero()[1][1].isOccuped());
    }

    @Test
    public void shouldCheckValidPosition() {
        assertTrue(puzzle.isValidPosition(0, 0));
        assertTrue(puzzle.isValidPosition(2, 2));
        assertFalse(puzzle.isValidPosition(-1, 0));
        assertFalse(puzzle.isValidPosition(3, 3));
    }

    @Test
    public void shouldTiltRight() {
        puzzle.addTile(0, 0, "red");
        puzzle.tilt('r');

        assertFalse(puzzle.getTablero()[0][0].isOccuped());
        assertTrue(puzzle.getTablero()[0][2].isOccuped());
    }

    @Test
    public void shouldTiltLeft() {
        puzzle.addTile(0, 2, "red");
        puzzle.tilt('l');

        assertFalse(puzzle.getTablero()[0][2].isOccuped());
        assertTrue(puzzle.getTablero()[0][0].isOccuped());
    }

    @Test
    public void shouldTiltUp() {
        puzzle.addTile(2, 0, "red");
        puzzle.tilt('u');

        assertFalse(puzzle.getTablero()[2][0].isOccuped());
        assertTrue(puzzle.getTablero()[0][0].isOccuped());
    }

    @Test
    public void shouldTiltDown() {
        puzzle.addTile(0, 0, "red");
        puzzle.tilt('d');

        assertFalse(puzzle.getTablero()[0][0].isOccuped());
        assertTrue(puzzle.getTablero()[2][0].isOccuped());
    }

    @Test
    public void shouldCreateHole() {
        puzzle.makeHole(1, 1);
        assertTrue(puzzle.getTablero()[1][1].isHasHole());
    }

    @Test
    public void testIsGoal_WhenPuzzleIsInGoalState() {
        char[][] ending = {
                {'r', 'b', '.'},
                {'g', '.', '.'},
                {'.', '.', '.'}
        };
        puzzle = new Puzzle(ending);
        puzzle.addTile(0, 0, "red");
        puzzle.addTile(0, 1, "blue");
        puzzle.addTile(1, 0, "green");

        boolean result = puzzle.isGoal();

        assertTrue(result);
    }

    @Test
    public void testIsGoal_WhenPuzzleIsNotInGoalState() {
        char[][] ending = {
                {'r', 'b', '.'},
                {'g', '.', '.'},
                {'.', '.', '.'}
        };
        puzzle = new Puzzle(ending);
        puzzle.addTile(0, 0, "red");
        puzzle.addTile(0, 1, "green"); // incorrecta
        puzzle.addTile(1, 0, "blue");  // posición incorrecta

        boolean result = puzzle.isGoal();

        assertFalse( result);
    }

    @Test
    public void shouldAddAndRemoveGlue() {
        puzzle.addTile(0, 0, "red");
        puzzle.addGlue(0, 0);
        assertTrue(puzzle.getTablero()[0][0].getTile().isSticky());

        puzzle.deleteGlue(0, 0);
        assertFalse(puzzle.getTablero()[0][0].getTile().isSticky());
    }

    @Test
    public void testMisplacedTiles_AllTilesCorrect() {
        // Configurar
        char[][] ending = {
                {'r', 'b', '.'},
                {'g', '.', '.'},
                {'.', '.', '.'}
        };
        puzzle = new Puzzle(ending);
        puzzle.addTile(0, 0, "red");
        puzzle.addTile(0, 1, "blue");
        puzzle.addTile(1, 0, "green");

        int misplacedCount = puzzle.misplacedTiles();

        assertEquals(0, misplacedCount);
    }


    @Test
    public void testMisplacedTiles_AllTilesMisplaced() {
        char[][] ending = {
                {'r', 'b', '.'},
                {'g', '.', '.'},
                {'.', '.', '.'}
        };
        puzzle = new Puzzle(ending);
        puzzle.addTile(0, 0, "blue");
        puzzle.addTile(0, 1, "green");
        puzzle.addTile(1, 0, "red");

        int misplacedCount = puzzle.misplacedTiles();

        assertEquals(3, misplacedCount);
    }

}