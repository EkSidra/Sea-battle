package Ships;
import java.awt.Graphics;
import java.io.Serializable;
import java.util.ArrayList;
import Graphics.Graphic;


public class Ship implements Serializable {
    private ArrayList<Cell> cells = new ArrayList<Cell>();
    private int length, x, y, position;
    public Ship(int x, int y, int length, int position) {
        this.length = length;
        this.x = x;
        this.y = y;
        this.position = position;
        for (int i = 0; i < length; i++)
            cells.add(
                    new Cell(x + i * ((position == 1)? 0 : 1), //позиция это положение горизонтальное или вертикальное
                            y + i * ((position == 1)?1:0)));
    }

    //корабль за границами поля
    public boolean isOutOfField(int bottom, int top) {
        for (Cell cell : cells)
            if (cell.getX() < bottom || cell.getX() > top ||
                    cell.getY() < bottom || cell.getY() > top)
                return true;
        return false;
    }

    public boolean isOverlayOrTouch(Ship ctrlShip) { //соприкасается ли ячейка с кораблем
        for (Cell cell : cells)
            if (ctrlShip.isOverlayOrTouchCell(cell))
                return true;
        return false;
    }

    public boolean isOverlayOrTouchCell(Cell ctrlCell) { //соприкасаются ли 2 ячейки
        for (Cell cell : cells)
            for (int dx = -1; dx < 2; dx++)
                for (int dy = -1; dy < 2; dy++)
                    if (ctrlCell.getX() == cell.getX() + dx &&
                            ctrlCell.getY() == cell.getY() + dy)
                        return true;
        return false;
    }

    public boolean checkHit(int x, int y) {  //попали ли мы в корабль
        for (Cell cell : cells)
            if (cell.checkHit(x, y))
                return true;
        return false;
    }

    public boolean checkCoordinates(int x,int y)
    {
        for (Cell cell : cells) {
            if (cell.getX() == x && cell.getY() == y)
                return true;
        }
        return false;
    }


    public boolean isAlive() {    //проверка не убили ли мы корабль
        for (Cell cell : cells) {
            if (cell.isAlive())
                return true;
        }
        return false;
    }

    public int getLength()
    {
        return this.length;
    }

    public void setLength(int length)
    {
        this.length = length;
    }

    public int getX()
    {
        return this.x;
    }

    public int getY()
    {
        return this.y;
    }

    public int getPosition()
    {
        return this.position;
    }

    public void paintOverShip(boolean hide)
    {
        int count = cells.size();
        int position=2;
        if(count>1){
            if(cells.get(0).getX()+1 == cells.get(1).getX()) {
                position = 1;//горизонтальный
            }
            else position = 0;//вертикальный}
        }
        if(count==1 && hide) //убили одинарный корабль компа
        {
            if(cells.get(0).getX()<9 && cells.get(0).getY()<9 && !Graphic.aiShots.hitSamePlace(cells.get(0).getX()+1,cells.get(0).getY()+1))   Graphic.aiShots.add(cells.get(0).getX()+1,cells.get(0).getY()+1,true);
            if(cells.get(0).getX()<9 && cells.get(0).getY()>0 && !Graphic.aiShots.hitSamePlace(cells.get(0).getX()+1,cells.get(0).getY()-1))   Graphic.aiShots.add(cells.get(0).getX()+1,cells.get(0).getY()-1,true);
            if(cells.get(0).getX()>0 && cells.get(0).getY()<9 && !Graphic.aiShots.hitSamePlace(cells.get(0).getX()-1,cells.get(0).getY()+1))  Graphic.aiShots.add(cells.get(0).getX()-1,cells.get(0).getY()+1,true);
            if(cells.get(0).getX()>0 && cells.get(0).getY()>0 && !Graphic.aiShots.hitSamePlace(cells.get(0).getX()-1,cells.get(0).getY()-1))   Graphic.aiShots.add(cells.get(0).getX()-1,cells.get(0).getY()-1,true);
            if(cells.get(0).getX()<9 && !Graphic.aiShots.hitSamePlace(cells.get(0).getX()+1,cells.get(0).getY()))   Graphic.aiShots.add(cells.get(0).getX()+1,cells.get(0).getY(),true);
            if(cells.get(0).getX()>0 && !Graphic.aiShots.hitSamePlace(cells.get(0).getX()-1,cells.get(0).getY()))   Graphic.aiShots.add(cells.get(0).getX()-1,cells.get(0).getY(),true);
            if(cells.get(0).getY()<9 && !Graphic.aiShots.hitSamePlace(cells.get(0).getX(),cells.get(0).getY()+1))   Graphic.aiShots.add(cells.get(0).getX(),cells.get(0).getY()+1,true);
            if(cells.get(0).getY()>0 && !Graphic.aiShots.hitSamePlace(cells.get(0).getX(),cells.get(0).getY()-1))   Graphic.aiShots.add(cells.get(0).getX(),cells.get(0).getY()-1,true);
        }
        if(count==1 && !hide) //убили одинарный корабль человека
        {
            if(cells.get(0).getX()<9 && cells.get(0).getY()<9 && !Graphic.humanShots.hitSamePlace(cells.get(0).getX()+1,cells.get(0).getY()+1))   Graphic.humanShots.add(cells.get(0).getX()+1,cells.get(0).getY()+1,true);
            if(cells.get(0).getX()<9 && cells.get(0).getY()>0 && !Graphic.humanShots.hitSamePlace(cells.get(0).getX()+1,cells.get(0).getY()-1))   Graphic.humanShots.add(cells.get(0).getX()+1,cells.get(0).getY()-1,true);
            if(cells.get(0).getX()>0 && cells.get(0).getY()<9 && !Graphic.humanShots.hitSamePlace(cells.get(0).getX()-1,cells.get(0).getY()+1))   Graphic.humanShots.add(cells.get(0).getX()-1,cells.get(0).getY()+1,true);
            if(cells.get(0).getX()>0 && cells.get(0).getY()>0 && !Graphic.humanShots.hitSamePlace(cells.get(0).getX()-1,cells.get(0).getY()-1))   Graphic.humanShots.add(cells.get(0).getX()-1,cells.get(0).getY()-1,true);
            if(cells.get(0).getX()<9 && !Graphic.humanShots.hitSamePlace(cells.get(0).getX()+1,cells.get(0).getY()))   Graphic.humanShots.add(cells.get(0).getX()+1,cells.get(0).getY(),true);
            if(cells.get(0).getX()>0 && !Graphic.humanShots.hitSamePlace(cells.get(0).getX()-1,cells.get(0).getY()))   Graphic.humanShots.add(cells.get(0).getX()-1,cells.get(0).getY(),true);
            if(cells.get(0).getY()<9 && !Graphic.humanShots.hitSamePlace(cells.get(0).getX(),cells.get(0).getY()+1))   Graphic.humanShots.add(cells.get(0).getX(),cells.get(0).getY()+1,true);
            if(cells.get(0).getY()>0 && !Graphic.humanShots.hitSamePlace(cells.get(0).getX(),cells.get(0).getY()-1))   Graphic.humanShots.add(cells.get(0).getX(),cells.get(0).getY()-1,true);
        }
        if(count>1 && hide && position==1){
            for (Cell cell : cells)
            {
                if(count==cells.size()) {
                    if(cell.getX()>0 && cell.getY()<9 && !Graphic.aiShots.hitSamePlace(cell.getX()-1,cell.getY()+1))   Graphic.aiShots.add(cell.getX()-1,cell.getY()+1,true);
                    if(cell.getX()>0 && cell.getY()>0 && !Graphic.aiShots.hitSamePlace(cell.getX()-1,cell.getY()-1))   Graphic.aiShots.add(cell.getX()-1,cell.getY()-1,true);
                    if(cell.getX()>0 && !Graphic.aiShots.hitSamePlace(cell.getX()-1,cell.getY()))   Graphic.aiShots.add(cell.getX()-1,cell.getY(),true);
                    if(cell.getY()<9 && !Graphic.aiShots.hitSamePlace(cell.getX(),cell.getY()+1))   Graphic.aiShots.add(cell.getX(),cell.getY()+1,true);
                    if(cell.getY()>0 && !Graphic.aiShots.hitSamePlace(cell.getX(),cell.getY()-1))   Graphic.aiShots.add(cell.getX(),cell.getY()-1,true);
                }
                if(count==1)
                {
                    if(cell.getX()<9 && cell.getY()<9 && !Graphic.aiShots.hitSamePlace(cell.getX()+1,cell.getY()+1))   Graphic.aiShots.add(cell.getX()+1,cell.getY()+1,true);
                    if(cell.getX()<9 && cell.getY()>0 && !Graphic.aiShots.hitSamePlace(cell.getX()+1,cell.getY()-1))   Graphic.aiShots.add(cell.getX()+1,cell.getY()-1,true);
                    if(cell.getX()<9 && !Graphic.aiShots.hitSamePlace(cell.getX()+1,cell.getY()))   Graphic.aiShots.add(cell.getX()+1,cell.getY(),true);
                    if(cell.getY()<9 && !Graphic.aiShots.hitSamePlace(cell.getX(),cell.getY()+1))   Graphic.aiShots.add(cell.getX(),cell.getY()+1,true);
                    if(cell.getY()>0 && !Graphic.aiShots.hitSamePlace(cell.getX(),cell.getY()-1))   Graphic.aiShots.add(cell.getX(),cell.getY()-1,true);
                }
                else {
                    if(cell.getY()<9 && !Graphic.aiShots.hitSamePlace(cell.getX(),cell.getY()+1))   Graphic.aiShots.add(cell.getX(),cell.getY()+1,true);
                    if(cell.getY()>0 && !Graphic.aiShots.hitSamePlace(cell.getX(),cell.getY()-1))   Graphic.aiShots.add(cell.getX(),cell.getY()-1,true);
                }
                count--;
            }
        }
        if(count>1 && hide && position==0){
            for (Cell cell : cells)
            {
                if(count==cells.size()) {
                    if(cell.getX()<9 && cell.getY()>0 && !Graphic.aiShots.hitSamePlace(cell.getX()+1,cell.getY()-1))   Graphic.aiShots.add(cell.getX()+1,cell.getY()-1,true);
                    if(cell.getX()>0 && cell.getY()>0 && !Graphic.aiShots.hitSamePlace(cell.getX()-1,cell.getY()-1))   Graphic.aiShots.add(cell.getX()-1,cell.getY()-1,true);
                    if(cell.getX()<9 && !Graphic.aiShots.hitSamePlace(cell.getX()+1,cell.getY()))   Graphic.aiShots.add(cell.getX()+1,cell.getY(),true);
                    if(cell.getX()>0 && !Graphic.aiShots.hitSamePlace(cell.getX()-1,cell.getY()))   Graphic.aiShots.add(cell.getX()-1,cell.getY(),true);
                    if(cell.getY()>0 && !Graphic.aiShots.hitSamePlace(cell.getX(),cell.getY()-1))   Graphic.aiShots.add(cell.getX(),cell.getY()-1,true);
                }
                if(count==1)
                {
                    if(cell.getX()<9 && cell.getY()<9 && !Graphic.aiShots.hitSamePlace(cell.getX()+1,cell.getY()+1))   Graphic.aiShots.add(cell.getX()+1,cell.getY()+1,true);
                    if(cell.getX()>0 && cell.getY()<9 && !Graphic.aiShots.hitSamePlace(cell.getX()-1,cell.getY()+1))   Graphic.aiShots.add(cell.getX()-1,cell.getY()+1,true);
                    if(cell.getX()<9 && !Graphic.aiShots.hitSamePlace(cell.getX()+1,cell.getY()))   Graphic.aiShots.add(cell.getX()+1,cell.getY(),true);
                    if(cell.getX()>0 && !Graphic.aiShots.hitSamePlace(cell.getX()-1,cell.getY()))   Graphic.aiShots.add(cell.getX()-1,cell.getY(),true);
                    if(cell.getY()<9 && !Graphic.aiShots.hitSamePlace(cell.getX(),cell.getY()+1))   Graphic.aiShots.add(cell.getX(),cell.getY()+1,true);
                }
                else {
                    if(cell.getX()<9 && !Graphic.aiShots.hitSamePlace(cell.getX()+1,cell.getY()))   Graphic.aiShots.add(cell.getX()+1,cell.getY(),true);
                    if(cell.getX()>0 && !Graphic.aiShots.hitSamePlace(cell.getX()-1,cell.getY()))   Graphic.aiShots.add(cell.getX()-1,cell.getY(),true);
                }
                count--;
            }
        }
        if(count>1 && !hide && position==1){
            for (Cell cell : cells)
            {
                if(count==cells.size()) {
                    if(cell.getX()>0 && cell.getY()<9 && !Graphic.humanShots.hitSamePlace(cell.getX()-1,cell.getY()+1))   Graphic.humanShots.add(cell.getX()-1,cell.getY()+1,true);
                    if(cell.getX()>0 && cell.getY()>0 && !Graphic.humanShots.hitSamePlace(cell.getX()-1,cell.getY()-1))   Graphic.humanShots.add(cell.getX()-1,cell.getY()-1,true);
                    if(cell.getX()>0 && !Graphic.humanShots.hitSamePlace(cell.getX()-1,cell.getY()))   Graphic.humanShots.add(cell.getX()-1,cell.getY(),true);
                    if(cell.getY()<9 && !Graphic.humanShots.hitSamePlace(cell.getX(),cell.getY()+1))   Graphic.humanShots.add(cell.getX(),cell.getY()+1,true);
                    if(cell.getY()>0 && !Graphic.humanShots.hitSamePlace(cell.getX(),cell.getY()-1))   Graphic.humanShots.add(cell.getX(),cell.getY()-1,true);
                }
                if(count==1)
                {
                    if(cell.getX()<9 && cell.getY()<9 && !Graphic.humanShots.hitSamePlace(cell.getX()+1,cell.getY()+1))   Graphic.humanShots.add(cell.getX()+1,cell.getY()+1,true);
                    if(cell.getX()<9 && cell.getY()>0 && !Graphic.humanShots.hitSamePlace(cell.getX()+1,cell.getY()-1))   Graphic.humanShots.add(cell.getX()+1,cell.getY()-1,true);
                    if(cell.getX()<9 && !Graphic.humanShots.hitSamePlace(cell.getX()+1,cell.getY()))   Graphic.humanShots.add(cell.getX()+1,cell.getY(),true);
                    if(cell.getY()<9 && !Graphic.humanShots.hitSamePlace(cell.getX(),cell.getY()+1))   Graphic.humanShots.add(cell.getX(),cell.getY()+1,true);
                    if(cell.getY()>0 && !Graphic.humanShots.hitSamePlace(cell.getX(),cell.getY()-1))   Graphic.humanShots.add(cell.getX(),cell.getY()-1,true);
                }
                else {
                    if(cell.getY()<9 && !Graphic.humanShots.hitSamePlace(cell.getX(),cell.getY()+1))   Graphic.humanShots.add(cell.getX(),cell.getY()+1,true);
                    if(cell.getY()>0 && !Graphic.humanShots.hitSamePlace(cell.getX(),cell.getY()-1))   Graphic.humanShots.add(cell.getX(),cell.getY()-1,true);
                }
                count--;
            }
        }
        if(count>1 && !hide && position==0){
            for (Cell cell : cells)
            {
                if(count==cells.size()) {
                    if(cell.getX()<9 && cell.getY()>0 && !Graphic.humanShots.hitSamePlace(cell.getX()+1,cell.getY()-1))   Graphic.humanShots.add(cell.getX()+1,cell.getY()-1,true);
                    if(cell.getX()>0 && cell.getY()>0 && !Graphic.humanShots.hitSamePlace(cell.getX()-1,cell.getY()-1))   Graphic.humanShots.add(cell.getX()-1,cell.getY()-1,true);
                    if(cell.getX()<9 && !Graphic.humanShots.hitSamePlace(cell.getX()+1,cell.getY()))   Graphic.humanShots.add(cell.getX()+1,cell.getY(),true);
                    if(cell.getX()>0 && !Graphic.humanShots.hitSamePlace(cell.getX()-1,cell.getY()))   Graphic.humanShots.add(cell.getX()-1,cell.getY(),true);
                    if(cell.getY()>0 && !Graphic.humanShots.hitSamePlace(cell.getX(),cell.getY()-1))   Graphic.humanShots.add(cell.getX(),cell.getY()-1,true);
                }
                if(count==1)
                {
                    if(cell.getX()<9 && cell.getY()<9 && !Graphic.humanShots.hitSamePlace(cell.getX()+1,cell.getY()+1))   Graphic.humanShots.add(cell.getX()+1,cell.getY()+1,true);
                    if(cell.getX()>0 && cell.getY()<9 && !Graphic.humanShots.hitSamePlace(cell.getX()-1,cell.getY()+1))   Graphic.humanShots.add(cell.getX()-1,cell.getY()+1,true);
                    if(cell.getY()<9 && !Graphic.humanShots.hitSamePlace(cell.getX(),cell.getY()+1))   Graphic.humanShots.add(cell.getX(),cell.getY()+1,true);
                    if(cell.getX()<9 && !Graphic.humanShots.hitSamePlace(cell.getX()+1,cell.getY()))   Graphic.humanShots.add(cell.getX()+1,cell.getY(),true);
                    if(cell.getX()>0 && !Graphic.humanShots.hitSamePlace(cell.getX()-1,cell.getY()))   Graphic.humanShots.add(cell.getX()-1,cell.getY(),true);
                }
                else {
                    if(cell.getX()<9 && !Graphic.humanShots.hitSamePlace(cell.getX()+1,cell.getY()))   Graphic.humanShots.add(cell.getX()+1,cell.getY(),true);
                    if(cell.getX()>0 && !Graphic.humanShots.hitSamePlace(cell.getX()-1,cell.getY()))   Graphic.humanShots.add(cell.getX()-1,cell.getY(),true);
                }
                count--;
            }
        }
        //GameBattleShip.leftPanel.repaint();
        //GameBattleShip.humanPanel.repaint();
        //GameBattleShip.board.setCaretPosition(GameBattleShip.board.getText().length());
    }

    public void paint(Graphics g, int cellSize, boolean hide) {
        for (Cell cell : cells)
            cell.paint(g, cellSize, hide);
    }
}
