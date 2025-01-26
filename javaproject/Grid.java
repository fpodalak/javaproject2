package javaproject;

public class Grid {
    public void printGrid(Field field) {
        printHorizontalBorder(field.getN());
        for (int i = 0; i < field.getN(); i++) {
            for (int subRow = 0; subRow < 3; subRow++) {
                System.out.print("|");
                for (int j = 0; j < field.getN(); j++) {
                    printCellSubRow(field.getCell(i, j), subRow);
                    System.out.print("|");
                }
                System.out.println();
            }
            printHorizontalBorder(field.getN());
        }
        System.out.println();
    }

    private void printCellSubRow(Cell cell, int subRow) {
        char[][] subcell = new char[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                subcell[i][j] = ' ';
            }
        }

        if (cell.hasRabbit()) subcell[1][1] = 'R';
        if (cell.hasDog()) subcell[0][0] = 'D';
        if (cell.hasFarmer()) subcell[0][2] = 'F';
        if (cell.hasCarrot()) subcell[2][1] = 'C';
        if (cell.isDamaged()) {
            subcell[0][1] = 'X';
            subcell[1][0] = 'X';
            subcell[1][2] = 'X';
            subcell[2][0] = 'X';
            subcell[2][2] = 'X';
        }

        System.out.print(subcell[subRow][0]);
        System.out.print(subcell[subRow][1]);
        System.out.print(subcell[subRow][2]);
    }

    private void printHorizontalBorder(int n) {
        System.out.print("+");
        for (int i = 0; i < n; i++) {
            System.out.print("---+");
        }
        System.out.println();
    }
}
