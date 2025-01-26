package javaproject;

import java.util.HashMap;
import java.util.Map;

public class Grid {

    private static final String RESET = "\u001B[0m";
    private static final String BLUE = "\u001B[34m";
    private static final String RED = "\u001B[31m";
    private static final String GREEN = "\u001B[32m";
    private static final String YELLOW = "\u001B[33m";
    private static final String ORANGE = "\u001B[38;5;214m";
    private static final String CYAN = "\u001B[36m";

    private static final Map<Character, String> ENTITY_COLORS = new HashMap<>();

    static {
        ENTITY_COLORS.put('R', RED);
        ENTITY_COLORS.put('D', GREEN);
        ENTITY_COLORS.put('F', YELLOW);
        ENTITY_COLORS.put('C', ORANGE);
        ENTITY_COLORS.put('X', CYAN);
    }

    public void printGrid(Field field) {
        printHorizontalBorder(field.getN());
        for (int i = 0; i < field.getN(); i++) {
            for (int subRow = 0; subRow < 3; subRow++) {
                System.out.print(BLUE + "|" + RESET);
                for (int j = 0; j < field.getN(); j++) {
                    printCellSubRow(field.getCell(i, j), subRow);
                    System.out.print(BLUE + "|" + RESET);
                }
                System.out.println();
            }
            printHorizontalBorder(field.getN());
        }
        System.out.println();
    }

    private void printCellSubRow(Cell cell, int subRow) {
        char[][] subcell = new char[3][8];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 8; j++) {
                subcell[i][j] = ' ';
            }
        }

        if (cell.hasRabbit()) subcell[1][3] = 'R';
        if (cell.hasDog()) subcell[0][0] = 'D';
        if (cell.hasFarmer()) subcell[0][7] = 'F';
        if (cell.hasCarrot()) subcell[2][3] = 'C';
        if (cell.isDamaged()) {
            subcell[0][3] = 'X';
            subcell[1][0] = 'X';
            subcell[1][7] = 'X';
            subcell[2][0] = 'X';
            subcell[2][7] = 'X';
        }

        for (int i=0; i<8; i++) {
            char ch = subcell[subRow][i];
            if (ENTITY_COLORS.containsKey(ch)) {
                System.out.print(ENTITY_COLORS.get(ch) + ch + RESET);
            } else {
                System.out.print(ch);
            }
        }
    }

    private void printHorizontalBorder(int n) {
        System.out.print(BLUE + "+" + RESET);
        for (int i = 0; i < n; i++) {
            System.out.print(BLUE + "--------+" + RESET);
        }
        System.out.println();
    }
}
