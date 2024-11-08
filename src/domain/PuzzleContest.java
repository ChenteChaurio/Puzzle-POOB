package domain;

import javax.swing.*;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.*;

public class PuzzleContest {
    List<String> camino = new LinkedList<>();

    /**
     * Class to make the solution of the proposed problem
     */
    public PuzzleContest() {
    }

    /**
     * Solve the problem
     * @param starting the initial matrix
     * @param ending the final matrix
     * @return boolean
     */
    public boolean solve(char[][] starting, char[][] ending) {
        Set<String> visitadas = new HashSet<>();
        Queue<char[][]> cola = new LinkedList<>();
        cola.offer(starting);
        Map<String, String> movimientos = new HashMap<>();
        movimientos.put(matrizToString(starting), "");
        Map<String, char[][]> origen = new HashMap<>();
        origen.put(matrizToString(starting), null);
        String matrizFinalString = matrizToString(ending);
        while (!cola.isEmpty()) {
            char[][] estadoActual = cola.poll();
            String estadoString = matrizToString(estadoActual);
            if (visitadas.contains(estadoString)) {
                continue;
            }
            visitadas.add(estadoString);
            if (estadoString.equals(matrizFinalString)) {
                camino = reconstruirCamino(starting, ending, movimientos, origen);
                return true;
            }
            for (char direccion : new char[] {'r', 'l', 'u', 'd'}) {
                char[][] nuevaMatriz = tiltMatriz(estadoActual, direccion);
                String nuevaMatrizString = matrizToString(nuevaMatriz);

                if (!visitadas.contains(nuevaMatrizString)) {
                    cola.offer(nuevaMatriz);
                    movimientos.put(nuevaMatrizString, String.valueOf(direccion));
                    origen.put(nuevaMatrizString, estadoActual);
                }
            }
        }
        return false;
    }

    /**
     * Make the simulation of the solution
     * @param starting the initial matrix
     * @param ending the final matrix
     */
    public void simulate(char[][] starting, char[][] ending){
        Puzzle solution = new Puzzle(starting,ending);
        if(solve(starting,ending)){
            solution.makeVisible();
            char[] movimientos = new char[camino.size()];
            for (int i = 0; i < camino.size(); i++) {
                movimientos[i] = camino.get(i).charAt(0);
            }
            for(char a:movimientos){
                solution.tilt(a);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }else{
            JOptionPane.showMessageDialog(null,"Lo sentimos no hay solucion en este caso");
        }

    }

    /**
     * Auxiliary method to make the way solution
     * @param matrizInicio the initial matrix
     * @param matrizFinal the final matrix
     * @param movimientos the winning movements
     * @return the way solution
     */
    private List<String> reconstruirCamino(char[][] matrizInicio, char[][] matrizFinal,
                                           Map<String, String> movimientos,
                                           Map<String, char[][]> origen) {
        List<String> camino = new LinkedList<>();
        String estadoString = matrizToString(matrizFinal);
        while (estadoString != null && !estadoString.equals(matrizToString(matrizInicio))) {
            String direccion = movimientos.get(estadoString);
            camino.add(0, direccion); // Insertamos el movimiento al principio de la lista
            estadoString = matrizToString(origen.get(estadoString));
        }
        return camino;
    }

    /**
     * The method tilt but in matrix[][]
     * @param matriz the matrix to be moved
     * @param direccion the direction to move
     * @return the matrix after the movement
     */
    private char[][] tiltMatriz(char[][] matriz, char direccion) {
        int filas = matriz.length;
        int columnas = matriz[0].length;
        char[][] nuevaMatriz = new char[filas][columnas];
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                nuevaMatriz[i][j] = '.';
            }
        }

        switch (direccion) {
            case 'r':
                for (int i = 0; i < filas; i++) {
                    int index = columnas - 1;
                    for (int j = columnas - 1; j >= 0; j--) {
                        if (matriz[i][j] != '.') {
                            nuevaMatriz[i][index--] = matriz[i][j];
                        }
                    }
                }
                break;

            case 'l':
                for (int i = 0; i < filas; i++) {
                    int index = 0;
                    for (int j = 0; j < columnas; j++) {
                        if (matriz[i][j] != '.') {
                            nuevaMatriz[i][index++] = matriz[i][j];
                        }
                    }
                }
                break;

            case 'u':
                for (int j = 0; j < columnas; j++) {
                    int index = 0;
                    for (int i = 0; i < filas; i++) {
                        if (matriz[i][j] != '.') {
                            nuevaMatriz[index++][j] = matriz[i][j];
                        }
                    }
                }
                break;

            case 'd':
                for (int j = 0; j < columnas; j++) {
                    int index = filas - 1;
                    for (int i = filas - 1; i >= 0; i--) {
                        if (matriz[i][j] != '.') {
                            nuevaMatriz[index--][j] = matriz[i][j];
                        }
                    }
                }
                break;
        }
        return nuevaMatriz;
    }


    /**
     * Create char[][] in a string
     * @param matriz to make in a string
     * @return the string
     */
    private String matrizToString(char[][] matriz) {
        StringBuilder sb = new StringBuilder();
        for (char[] fila : matriz) {
            for (char c : fila) {
                sb.append(c);
            }
        }
        return sb.toString();
    }
}

