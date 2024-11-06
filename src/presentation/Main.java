package presentation;

import domain.*;

public class Main {
    public static void main(String[] args) {
        PuzzleContest a = new PuzzleContest();
        a.simulate(new char[][]{{'.','r','.','.'},{'r','g','y','b'},{'.','b','.','.'},{'.','y','r','.'}},new char[][]{{'y','r','b','r'},{'.','.','y','r'},{'.','.','.','g'},{'.','.','.','b'}});
    }
}