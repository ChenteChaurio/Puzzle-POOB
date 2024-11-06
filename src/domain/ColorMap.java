package domain;

import java.util.HashMap;

public class ColorMap{
    /**
     * Static map that associates a character with its corresponding color name.
     * The map is statically initialized with a predefined set of colors.
     */
    private static HashMap<Character, String> colorMap = new HashMap<>();;
    static {
        colorMap.put('b', "blue");
        colorMap.put('r', "red");
        colorMap.put('g', "green");
        colorMap.put('y', "yellow");
        colorMap.put('m', "magenta");
        colorMap.put('i', "indigo");
        colorMap.put('a', "gray");
        colorMap.put('d', "darkGray");
        colorMap.put('l', "lightGray");
        colorMap.put('o', "orange");
        colorMap.put('c', "cyan");
        colorMap.put('p', "purple");
        colorMap.put('v', "violet");
        colorMap.put('n', "navy");
        colorMap.put('t', "turquoise");
        colorMap.put('k', "gold");
        colorMap.put('s', "silver");
    }
    public static String getColor(char character) {
        return colorMap.get(character);
    }
}
