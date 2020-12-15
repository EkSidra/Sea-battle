package Gameplay;
import Shots.*;
import Ships.*;
import Graphics.Graphic;

import java.awt.event.MouseEvent;
import java.util.Random;


public class Battle {

    //GameBattleShip(){}

    public static void afterShootTarget(int x, int y) /*throws InterruptedException*/ {
        //boolean start;
        label1: do {
            //start=false;
            switch (Graphic.manage) {
                case 1: { //идем вправо
                    for (int i = 1; i < 4; i++) {
                        x = x + 1;
                        if (x > Graphic.FIELD_SIZE - 1 || Graphic.aiShots.hitSamePlace(x, y)) //выходим за пределы поля или уже закрасили эту клетку
                        {
                            Graphic.manage++; //переход к след направлению
                            x = x - i; //восстанавливаем X
                            //start=true;
                            break label1;
                        }
                        Graphic.aiShots.add(x, y, true);
                        if (!Graphic.humanShips.checkHit(x, y)) { // промах компьютера
                            Graphic.kill=true;
                            Graphic.manage++;  //переход к след направлению
                            Graphic.board.append(
                                    "\n" + (x + 1) + ":" + (y + 1) + " AI missed.");
                            x = x - i;   //восстанавливаем X
                            return;
                        } else {  //попадание
                            Graphic.board.append(
                                    "\n" + (x + 1) + ":" + (y + 1) + " AI hit the target.");
                            Graphic.board.setCaretPosition(Graphic.board.getText().length());
                            //wait(1000);
//                        leftPanel.repaint();
//                        humanPanel.repaint();
//                        board.setCaretPosition(board.getText().length());
                            if (!Graphic.humanShips.checkSurvivorsShip(x, y, true)) {//убили корабль
                                if (!Graphic.humanShips.checkSurvivors()) { //проверка не попедил ли компьютер
                                    Graphic.board.append("\n" + Graphic.AI_WON);
                                    Graphic.gameOver = true;
                                    Graphic.record2(Graphic.counter);
                                    return;
                                }
                                Graphic.kill = false;  //восстанавливаем переменную убийства
                                Graphic.manage = Graphic.random.nextInt(3) + 1;
                                shootsAI();
                            }
                        }
                    }
                }
                break;
                case 2: {
                    for (int i = 1; i < 4; i++) {
                        x = x - 1;

                        if (x < 0 || Graphic.aiShots.hitSamePlace(x, y)) {
                            Graphic.manage++;
                            x = x + i;
                            //start=true;
                            break label1;
                        }
                        Graphic.aiShots.add(x, y, true);
                        if (!Graphic.humanShips.checkHit(x, y)) { // промах компьютера
                            Graphic.kill=true;
                            Graphic.manage++;
                            Graphic.board.append(
                                    "\n" + (x + 1) + ":" + (y + 1) + " AI missed.");
                            x = x + i;
                            return;
                        } else {
                            Graphic.board.append(
                                    "\n" + (x + 1) + ":" + (y + 1) + " AI hit the target.");
                            Graphic.board.setCaretPosition(Graphic.board.getText().length());
                            //wait(1000);
//                        leftPanel.repaint();
//                        humanPanel.repaint();
//                        board.setCaretPosition(board.getText().length());
                            if (!Graphic.humanShips.checkSurvivorsShip(x, y, true)) {
                                if (!Graphic.humanShips.checkSurvivors()) { //проверка не попедил ли компьютер
                                    Graphic.board.append("\n" + Graphic.AI_WON);
                                    Graphic.gameOver = true;
                                    Graphic.record2(Graphic.counter);
                                    return;
                                }
                                Graphic.kill = false;
                                Graphic.manage = Graphic.random.nextInt(3) + 1;
                                shootsAI();
                            }
                        }
                    }
                }
                break;
                case 3: {
                    for (int i = 1; i < 4; i++) {
                        y = y - 1;

                        if (y < 0 || Graphic.aiShots.hitSamePlace(x, y)) {
                            Graphic.manage++;
                            y = y + i;
                            //start=true;
                            break label1;
                        }
                        Graphic.aiShots.add(x, y, true);
                        if (!Graphic.humanShips.checkHit(x, y)) { // промах компьютера
                            Graphic.kill=true;
                            Graphic.manage++;
                            Graphic.board.append(
                                    "\n" + (x + 1) + ":" + (y + 1) + " AI missed.");
                            y = y + i;
                            return;
                        } else {
                            Graphic.board.append(
                                    "\n" + (x + 1) + ":" + (y + 1) + " AI hit the target.");
                            Graphic.board.setCaretPosition(Graphic.board.getText().length());
                            //wait(1000);
//                        leftPanel.repaint();
//                        humanPanel.repaint();
//                        board.setCaretPosition(board.getText().length());
                            if (!Graphic.humanShips.checkSurvivorsShip(x, y, true)) {
                                if (!Graphic.humanShips.checkSurvivors()) { //проверка не попедил ли компьютер
                                    Graphic.board.append("\n" + Graphic.AI_WON);
                                    Graphic.gameOver = true;
                                    Graphic.record2(Graphic.counter);
                                    return;
                                }
                                Graphic.kill = false;
                                Graphic.manage = Graphic.random.nextInt(3) + 1;
                                shootsAI();
                            }
                        }
                    }
                }
                break;
                case 4: {
                    for (int i = 1; i < 4; i++) {
                        y = y + 1;
                        if (y > Graphic.FIELD_SIZE - 1 || Graphic.aiShots.hitSamePlace(x, y)) {
                            Graphic.manage = 1;
                            y = y - i;
                            // start=true;
                            break label1;
                        }
                        Graphic.aiShots.add(x, y, true);
                        if (!Graphic.humanShips.checkHit(x, y)) { // промах компьютера
                            Graphic.manage = 1;
                            Graphic.kill=true;
                            Graphic.board.append(
                                    "\n" + (x + 1) + ":" + (y + 1) + " AI missed.");
                            y = y - i;
                            return;
                        } else {
                            Graphic.board.append(
                                    "\n" + (x + 1) + ":" + (y + 1) + " AI hit the target.");
                            Graphic.board.setCaretPosition(Graphic.board.getText().length());
                            //wait(1000);
//                        leftPanel.repaint();
//                        humanPanel.repaint();
//                        board.setCaretPosition(board.getText().length());
                            if (!Graphic.humanShips.checkSurvivorsShip(x, y, true)) {
                                if (!Graphic.humanShips.checkSurvivors()) { //проверка не попедил ли компьютер
                                    Graphic.board.append("\n" + Graphic.AI_WON);
                                    Graphic.gameOver = true;
                                    Graphic.record2(Graphic.counter);
                                    return;
                                }
                                Graphic.kill = false;
                                Graphic.manage = Graphic.random.nextInt(3) + 1;
                                shootsAI();
                            }
                        }
                    }
                }
                break;
            }
        }while(true);
    }

    public static void shootsAI() /*throws InterruptedException*/ { // стреляет компьютер
        int x, y;
        do {
            x = Graphic.random.nextInt(Graphic.FIELD_SIZE);
            y = Graphic.random.nextInt(Graphic.FIELD_SIZE);
        } while (Graphic.aiShots.hitSamePlace(x, y)); //не попал ли в тоже место
        Graphic.currentX=x;
        Graphic.currentY=y;
        Graphic.aiShots.add(x, y, true);
        if (!Graphic.humanShips.checkHit(x, y)) { // промах компьютера
            Graphic.board.append(
                    "\n" + (x + 1) + ":" + (y + 1) + " AI missed.");
            return;
        } else { // компьютер попал
            if(Graphic.humanShips.checkSurvivorsShip(x,y,true)) { //попали но не убили корабль
                Graphic.kill = true;
            }
            Graphic.board.append(
                    "\n" + (x + 1) + ":" + (y + 1) + " AI hit the target.");
            Graphic.board.setCaretPosition(Graphic.board.getText().length());
//            leftPanel.repaint();
//            humanPanel.repaint();
//            board.setCaretPosition(board.getText().length());
            if (!Graphic.humanShips.checkSurvivors()) { //проверка не попедил ли компьютер
                Graphic.board.append("\n" + Graphic.AI_WON);
                Graphic.gameOver = true;
                Graphic.record2(Graphic.counter);
                return;
            } else {
                //wait(1000);
                if(Graphic.kill==false) {
                    shootsAI();
                }
                if(Graphic.kill==true)
                {
                    if(Graphic.levelOfHardness == true) {
                        afterShootTarget(Graphic.currentX, Graphic.currentY);
                    }
                    if(Graphic.levelOfHardness == false)
                    {
                        shootsAI();
                    }
                }
            }
        }
    }
}

