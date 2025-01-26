package javaproject;

public class Field {
    private int N;
    private Cell[][] cells;

    public Field(int N) {
        this.N = N;
        cells = new Cell[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                cells[i][j] = new Cell(i,j);
            }
        }
    }
    public Cell getCell(int x, int y) {
        return cells[x][y];
    }
    public int getN() {
        return N;
    }

}
