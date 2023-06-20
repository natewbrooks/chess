package chess;

public class Coordinates {
    public int row = 1;
    public int col = 1;
    public char charCol = 'A';
    public String position = "A1";

    public Coordinates(char col, int row) {
        this.row = row;
        this.col = col;
        this.position = Character.toString(charCol) + row;
    }

    public Coordinates(int row, int col) {
        this.row = row;
        this.col = col;
        updatePositionToChar(col);
        this.position = Character.toString(charCol) + row;
        System.out.println(position);
    }

    public Coordinates(Coordinates coord) {
        this.row = coord.row;
        this.col = coord.col;
        this.position = coord.position;
    }

    public void update(int row, char col) {
        this.row = row;
        updatePositionToChar(col);
        this.position = Character.toString(charCol) + row;
    }

    public void update(int row, int col) {
        this.row = row;
        this.col = Character.toChars(col)[0];
        this.position = Character.toString(charCol) + row;
    }

    public void update(Coordinates coord) {
        this.row = coord.row;
        this.col = coord.col;
        this.position = coord.position;
    }

    public void updatePositionToChar(int x) {
        switch (x) {
            case 1:
                this.charCol = 'A';
                break;
            case 2:
                this.charCol = 'B';
                break;
            case 3:
                this.charCol = 'C';
                break;
            case 4:
                this.charCol = 'D';
                break;
            case 5:
                this.charCol = 'E';
                break;
            case 6:
                this.charCol = 'F';
                break;
            case 7:
                this.charCol = 'G';
                break;
            case 8:
                this.charCol = 'H';
                break;
        }
    }

    public boolean equals(Object o) {
        if (o instanceof Coordinates) {
            Coordinates c = (Coordinates) o;
            return this.col == c.col && this.row == c.row;
        }
        return false;
    }

    public String toString() {
        return position;
    }
}
