package Shots;
import java.awt.Graphics;
import java.io.*;
import java.util.ArrayList;

public class Shots implements Serializable {
    private final int CELL_SIZE;
    private ArrayList<Shot> shots; //храним все наши выстрелы

    public Shots(int cellSize) {
        CELL_SIZE = cellSize;
        shots = new ArrayList<Shot>();
    }
    public Shots(String name, int cellSize) throws FileNotFoundException {
        CELL_SIZE = cellSize;
        shots = new ArrayList<Shot>();
        try (final ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("Files\\" + name + ".txt"))) {
            while (inputStream.toString() != "") {
                final Shot temp = (Shot) inputStream.readObject();
                this.add(temp.getX(), temp.getY(), true);
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    public void add(int x, int y, boolean shot) {
        shots.add(new Shot(x, y, shot));
    }

    public boolean hitSamePlace(int x, int y) { //выстрелили в тоже место
        for (Shot shot : shots)
            if (shot.getX() == x && shot.getY() == y)
                return true;
        return false;
    }

    public Shot getLabel(int x, int y) {  //узнать ячейку в которую мы уже стреляли
        for (Shot label : shots)
            if (label.getX() == x && label.getY() == y)
                return label;
        return null;
    }

    public void writeFileShots(String name) {
        try (final ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("Files\\" + name + ".txt"))) {
            for(Shot shot : shots){
                outputStream.writeObject(shot);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void removeLabel(Shot label) { //удалить ячейку
        shots.remove(label);
    }

    public void paint(Graphics g) {
        for (Shot shot : shots) shot.paint(g, CELL_SIZE);
    }
}
