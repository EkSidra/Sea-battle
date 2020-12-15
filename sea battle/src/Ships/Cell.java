package Ships;
import java.awt.Color;
import java.awt.Graphics;
import java.io.Serializable;


public class Cell implements Serializable {
    private final Color RED = Color.red;
    private int x, y;   //координаты
    private Color color;  //цвет

    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
        color = Color.gray; // первоначальный цвет
    }

    public int getX() { return x; }
    public int getY() { return y; }

    public boolean checkHit(int x, int y) {
        if (this.x == x && this.y == y) {
            color = RED;
            return true;
        }
        return false;
    }

    public boolean isAlive() {                             //проверка на цвет
        return color != RED;
    }

    public void paint(Graphics g, int cellSize, boolean hide) {    //закрасить ячейку
        if (!hide || (hide && color == RED)) { //закрашивать мои и компьютера ячейки
            g.setColor(color);
            g.fill3DRect(x*cellSize + 1, y*cellSize + 1, cellSize - 2, cellSize - 2, true);
        }
    }
}