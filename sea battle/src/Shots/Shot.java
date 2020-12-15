package Shots;
import java.awt.Color;
import java.awt.Graphics;
import java.io.Serializable;

public class Shot implements Serializable {
    private int x, y;

    public Shot(int x, int y, boolean shot) {
        this.x = x;
        this.y = y;
    }

    public int getX() { return x; }
    public int getY() { return y; }

    public void paint(Graphics g, int cellSize) {
        g.setColor(Color.gray);
        g.fillRect(x*cellSize + cellSize/2 - 3, y*cellSize + cellSize/2 - 3, 8, 8);
    }
}