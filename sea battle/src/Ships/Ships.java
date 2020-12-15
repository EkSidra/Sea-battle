package Ships;
import java.awt.Graphics;
import java.io.*;
import java.util.ArrayList;
import java.util.Random;

public class Ships implements Serializable {
    private final int CELL_SIZE;
    private ArrayList<Ship> ships = new ArrayList<Ship>(); // массив для кораблей
    private final int[] PATTERN = {4, 3, 3, 2, 2, 2, 1, 1, 1, 1}; // размеры кораблей
    private Random random;
    private boolean hide;

    public Ships(int fieldSize, int cellSize, boolean hide) {
        random = new Random();
        for (int i = 0; i < PATTERN.length; i++) {
            Ship ship;
            do {
                int x = random.nextInt(fieldSize);
                int y = random.nextInt(fieldSize);
                int position = random.nextInt(2);
                ship = new Ship(x, y, PATTERN[i], position);
            } while (ship.isOutOfField(0, fieldSize - 1) || isOverlayOrTouch(ship));
            ships.add(ship);
        }
        CELL_SIZE = cellSize; //размер поля
        this.hide = hide;
    }


    public Ships(int fieldSize, int cellSize, boolean hide, String name)
    {
        int x=0,y=0,position=0,length = 0;
        CELL_SIZE = cellSize; //размер поля
        this.hide = hide;
        int i = 0;
        Ship[] temp;
        temp = new Ship[10];
        try (final ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("Files\\" + name + ".txt"))) {
            while(inputStream.toString() != "") {
                temp[i] = (Ship) inputStream.readObject();
                i++;
            }
            for(int j = 0; j < PATTERN.length; j++){
                Ship ship;
                for(int k = 0; k < i;k++)
                {
                    x = temp[k].getX();
                    y = temp[k].getY();
                    position = temp[k].getPosition();
                    length = temp[k].getLength();
                }
                ship = new Ship(x, y, length, position);
                ships.add(ship);
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public boolean isOverlayOrTouch(Ship ctrlShip) {
        for (Ship ship : ships) if (ship.isOverlayOrTouch(ctrlShip)) return true;
        return false;
    }

    public boolean checkHit(int x, int y) {
        for (Ship ship : ships) if (ship.checkHit(x, y)) return true;
        return false;
    }

    public boolean checkSurvivors() {
        for (Ship ship : ships) if (ship.isAlive()) return true;
        return false;
    }

    public boolean checkSurvivorsShip(int x, int y,boolean hide) {
        for (Ship ship : ships) {
            if (ship.checkCoordinates(x, y))
            {
                if(ship.isAlive()) return true;
                else {
                    ship.paintOverShip(hide); //закрасить клетки вокруг корабля
                    return false;
                }
            }
        }
        return false;
    }

    public void writeFileShips(String name)
    {
        try (final ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("Files\\" + name + ".txt"))) {
            for(Ship ship : ships){
                outputStream.writeObject(ship);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void paint(Graphics g) {
        for (Ship ship : ships) ship.paint(g, CELL_SIZE, hide);
    }
}