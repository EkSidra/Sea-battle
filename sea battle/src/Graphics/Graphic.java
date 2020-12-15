package Graphics;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.plaf.basic.BasicOptionPaneUI;
import java.util.*;
import java.lang.*;
import Gameplay.Battle;
import Ships.Ships;
import Shots.Shots;
import java.io.*;
import Work_with_file.*;
import javax.imageio.ImageIO;


public class Graphic extends JFrame{

    private final String TITLE_OF_PROGRAM = "Battle Ship";
    public Image img = ImageIO.read(new File("Images\\ship1.jpg"));
    public static final int FIELD_SIZE = 10;  //размер поля 10 на 10 ячеек
    private final int AI_PANEL_SIZE = 800;  //размер поля компьютера
    private static final int AI_CELL_SIZE = 80;  //размер ячейки
    private static final int HUMAN_PANEL_SIZE = 400; //размер нашей панели
    private static final int HUMAN_CELL_SIZE = 40; //размер ячейки
    private static final String BTN_INIT = "New game";
    private static final String BTN_EXIT = "Exit";
    private static final String YOU_WON = "YOU WON!";
    public static final String AI_WON = "AI WON!";
    private static final int MOUSE_BUTTON_LEFT = 1; // для контроля мыши
    private final int MOUSE_BUTTON_RIGHT = 3;


    public static JTextArea board; // для записи результатов
    static Canvas leftPanel, humanPanel, Menu;

    public static Ships aiShips, humanShips; //корабли
    public static Shots humanShots, aiShots;  //массивы выстрелов
    public static boolean gameOver;  //проверка конец игры или нет
    public static int manage;  //куда двигаться компу после попадания
    public static int currentY;
    public static int currentX;
    public static Random random;
    public static boolean kill; //убили ли мы корабль
    public static int counter=0; //количество выстрелов
    public static boolean levelOfHardness = true;  //false - easy, true - hard
    public static boolean start_or_cont = false; //false new game, true - continue

    public static void main(String[] args) throws FileNotFoundException {
        startMenu();
        //new Graphic();
    }

    public Graphic() throws IOException {
        setTitle(TITLE_OF_PROGRAM);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);  //нельзя менять раззмеры окна
        leftPanel = new Canvas(); // панель для кораблей компьютера
        leftPanel.setPreferredSize(new Dimension(AI_PANEL_SIZE, AI_PANEL_SIZE)); //поле для кораблей компьютера
        leftPanel.setBackground(Color.white);
        leftPanel.setBorder(BorderFactory.createLineBorder(Color.blue));
        leftPanel.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseReleased(MouseEvent e) { //экземпляр класса обработчика мыши(кликаем на обьект)
                super.mouseReleased(e);
                int x = e.getX()/AI_CELL_SIZE; // запоминаем координаты когда щелкаем мышкой
                int y = e.getY()/AI_CELL_SIZE;
                if (e.getButton() == MOUSE_BUTTON_LEFT && !gameOver) // нажата левая кнопка мыши
                    if (!humanShots.hitSamePlace(x, y)) { //не выстрелили ли мы в место куда уже стреляли
                        counter++;
                        humanShots.add(x, y, true);
                        if (aiShips.checkHit(x, y)) { //попали ли мы в цель
                            if (!aiShips.checkSurvivors()) { //убили ли мы последний корабль
                                board.append("\n" + YOU_WON);
                                gameOver = true;
                                record(counter);
                            }
                            aiShips.checkSurvivorsShip(x,y,false);
                        } else // не попали
                        {
                            if(kill==false) {
//                                try {
//                                    shootsAI();
//                                } catch (InterruptedException ex) {
//                                    ex.printStackTrace();
//                                }
                                Battle.shootsAI();

                            }
                            else
                            {
//                                try {
//                                    afterShootTarget(currentX, currentY);
//                                } catch (InterruptedException ex) {
//                                    ex.printStackTrace();
//                                }
                                if(levelOfHardness == true){
                                    Battle.afterShootTarget(currentX,currentY);}
                                if(!levelOfHardness)    {Battle.shootsAI();}
                            }
                        }
                        leftPanel.repaint();
                        humanPanel.repaint();
                        board.setCaretPosition(board.getText().length());
                    }

            }
        });

        humanPanel = new Canvas(); //поле моих кораблей
        humanPanel.setPreferredSize(new Dimension(HUMAN_PANEL_SIZE, HUMAN_PANEL_SIZE));
        humanPanel.setBackground(Color.white);
        humanPanel.setBorder(BorderFactory.createLineBorder(Color.blue));

        JButton init = new JButton(BTN_INIT); //кнопка для начала новой игры
        init.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    new Graphic();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                leftPanel.repaint();
                humanPanel.repaint();
            }
        });
        JButton exit = new JButton(BTN_EXIT); // кнопка для выхода из игры
        exit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(gameOver == false)
                {
                    aiShips.writeFileShips("aiShips");
                    humanShips.writeFileShips("humanShips");
                    aiShots.writeFileShots("aiShots");
                    humanShots.writeFileShots("humanShots");
                }
                System.exit(0);
            }
        });

        board = new JTextArea(); //сообщения для пользователя
        board.setEditable(false);
        JScrollPane scroll = new JScrollPane(board); // для прокрутки сообщений

        JPanel buttonPanel = new JPanel(); // панель для кнопок
        buttonPanel.setLayout(new GridLayout());  //помещаем компоненты в таблицу
        buttonPanel.add(init);
        buttonPanel.add(exit);

        JPanel rightPanel = new JPanel();         // панель для наших кораблей
        rightPanel.setLayout(new BorderLayout()); //  поле и кнопки размещаем у границ

        rightPanel.add(humanPanel, BorderLayout.NORTH); //вверху
        rightPanel.add(scroll, BorderLayout.CENTER); //по центру
        rightPanel.add(buttonPanel, BorderLayout.SOUTH); //снизу

        setLayout(new BoxLayout(getContentPane(), BoxLayout.X_AXIS)); //извлекаем слой панели для добавления туда
        //обьектов слева направо
        add(leftPanel);
        add(rightPanel);
        pack(); //устанавливаем минимальный размер контейнера для отображения всех компонентов
        setLocationRelativeTo(null); // по центру
        setVisible(true);
        init();

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if(gameOver == false)
                {
                    aiShips.writeFileShips("aiShips");
                    humanShips.writeFileShips("humanShips");
                    aiShots.writeFileShots("aiShots");
                    humanShots.writeFileShots("humanShots");
                }
                //System.exit(0);
            }
        });
    }

    public class Canvas extends JPanel { // для раскрашивания
        @Override
        public void paint(Graphics g) {
            super.paint(g); //для вызова метода JPanel
            g.drawImage(img,0,0,null);
            int cellSize = (int) getSize().getWidth() / FIELD_SIZE;
            g.setColor(Color.black);
            for (int i = 1; i < FIELD_SIZE; i++) {
                g.drawLine(0, i*cellSize, FIELD_SIZE*cellSize, i*cellSize); //рисуем наше поле
                g.drawLine(i*cellSize, 0, i*cellSize, FIELD_SIZE*cellSize);
            }
            if (cellSize == AI_CELL_SIZE) {
                humanShots.paint(g);
                aiShips.paint(g);
            } else {
                aiShots.paint(g);
                humanShips.paint(g);
            }
        }
    }

    public void init() throws FileNotFoundException {
        if(start_or_cont == false) {
            aiShips = new Ships(Graphic.FIELD_SIZE, Graphic.AI_CELL_SIZE, true);
            humanShips = new Ships(Graphic.FIELD_SIZE, Graphic.HUMAN_CELL_SIZE, false);
            aiShots = new Shots(Graphic.HUMAN_CELL_SIZE);
            humanShots = new Shots(Graphic.AI_CELL_SIZE);
        }
        else
        {
            aiShips = new Ships(Graphic.FIELD_SIZE, Graphic.AI_CELL_SIZE, true,"aiShips");
            humanShips = new Ships(Graphic.FIELD_SIZE, Graphic.HUMAN_CELL_SIZE, false,"humanShips");
            aiShots = new Shots("aiShots",Graphic.HUMAN_CELL_SIZE);
            humanShots = new Shots("humanShots",Graphic.AI_CELL_SIZE);
        }
        Graphic.board.setText(Graphic.BTN_INIT);
        gameOver = false;
        random = new Random();
        kill = false;
        currentX = 0;
        currentY = 0;
        manage = 1;
    }



    public static void record(int count)
    {
        String[] line;
        line = new String[3];
        Mfile.readFromFile(count,line);
        String res = "Your result: " + String.valueOf(count);
        JButton button = new JButton("Exit");
        JLabel label_5 = new JLabel("TOP Players");
        JLabel label_6 = new JLabel("Enter your name");
        JLabel label_7 = new JLabel("");
        JLabel label_8 = new JLabel("");
        JLabel label_1 = new JLabel(line[0]);
        JLabel label_2 = new JLabel(line[1]);
        JLabel label_3 = new JLabel(line[2]);
        JLabel label_4 = new JLabel(res);
        JButton butt = new JButton("Exit");
        JTextField text = new JTextField("",10);
        JFrame window = new JFrame("Your result");
        window.setBounds(200,200,800,500);
        window.setDefaultCloseOperation(EXIT_ON_CLOSE);
        window.setContentPane(new JLabel(new ImageIcon("Images\\ship.jpg")));
        int temp=Mfile.search(line,count);
        Container container = window.getContentPane();
        container.setLayout(new GridLayout(4,3,2,2));
        container.add(label_5);
        container.add(label_4);
        container.add(label_1);
        if(temp !=0) {
            container.add(label_6);
            container.add(label_2);
            container.add(text);
        }
        else
        {
            container.add(label_7);
            container.add(label_2);
            container.add(label_8);
        }
        container.add(label_3);
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(temp!=0) {
                    String login = String.valueOf(temp) + ". " + text.getText() + " " + String.valueOf(count);
                    line[temp-1]=login;
                    try {
                        Mfile.writeFile(line);
                    } catch (FileNotFoundException ex) {
                        ex.printStackTrace();
                    }
                    window.dispose();
                    System.exit(0);
                }else
                {
                    window.dispose();
                    System.exit(0);
                }
            }
        });
        container.add(button);
        window.setVisible(true);
    }

    public static void record2(int count)
    {
        String[] line;
        line = new String[3];
        Mfile.readFromFile(count,line);
        String res = "Your result: " + String.valueOf(count);
        JButton button = new JButton("Exit");
        JLabel label_1 = new JLabel(line[0]);
        JLabel label_2 = new JLabel(line[1]);
        JLabel label_3 = new JLabel(line[2]);
        JLabel label_4 = new JLabel(res);
        JButton butt = new JButton("Exit");
        JFrame window = new JFrame("Your result");
        window.setBounds(200,200,800,500);
        window.setDefaultCloseOperation(EXIT_ON_CLOSE);
        window.setContentPane(new JLabel(new ImageIcon("Images\\ship.jpg")));
        Container container = window.getContentPane();
        container.setLayout(new GridLayout(3,2,2,2));
        container.add(label_1);
        container.add(label_4);
        container.add(label_2);
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                window.dispose();
                System.exit(0);
            }
        });
        container.add(button);
        container.add(label_3);
        window.setVisible(true);
    }

    public static void startMenu()
    {
        JFrame window = new JFrame("Start");
        window.setBounds(250,250,800,500);
        window.setDefaultCloseOperation(EXIT_ON_CLOSE);
        window.setContentPane(new JLabel(new ImageIcon("Images\\ship.jpg")));
        JLabel label_1 = new JLabel(""),label_2 = new JLabel(""),label_3 = new JLabel(""),
                label_4 = new JLabel(""),label_5 = new JLabel(""),label_6 = new JLabel(""),label_7 = new JLabel("");
        Container container = window.getContentPane();
        container.setLayout(new GridLayout(3,3,3,3));
        JButton close = new JButton(BTN_EXIT); // кнопка для выхода из игры
        JButton cont = new JButton("Continue"); // кнопка для продолжения игры
        JButton start = new JButton(BTN_INIT); //кнопка для начала новой игры
        start.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                start_or_cont = false;
                JFrame window_2 = new JFrame("Level of Hardness");
                window_2.setBounds(250,250,800,500);
                window_2.setDefaultCloseOperation(EXIT_ON_CLOSE);
                window_2.setContentPane(new JLabel(new ImageIcon("Images\\ship.jpg")));
                Container container_2 = window_2.getContentPane();
                container_2.setLayout(new GridLayout(3,3,3,3));
                JButton easy = new JButton("Easy"); // кнопка для выхода из игры
                window.dispose();
                easy.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        levelOfHardness = false;
                        window_2.dispose();
                        try {
                            new Graphic();
                        } catch (FileNotFoundException fileNotFoundException) {
                            fileNotFoundException.printStackTrace();
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                    }
                });
                JButton hard = new JButton("Hard"); // кнопка для выхода из игры
                hard.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        levelOfHardness = true;
                        window_2.dispose();
                        try {
                            new Graphic();
                        } catch (FileNotFoundException fileNotFoundException) {
                            fileNotFoundException.printStackTrace();
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                    }
                });
                container_2.add(label_1);
                container_2.add(label_2);
                container_2.add(label_3);
                container_2.add(easy);
                container_2.add(label_7);
                container_2.add(hard);
                container_2.add(label_4);
                container_2.add(label_5);
                container_2.add(label_6);
                window_2.setVisible(true);
            }
        });
        close.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        cont.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                start_or_cont = true;
                window.dispose();
                try {
                    new Graphic();
                } catch (FileNotFoundException fileNotFoundException) {
                    fileNotFoundException.printStackTrace();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });
        container.add(label_1);
        container.add(label_2);
        container.add(label_3);
        container.add(start);
        container.add(cont);
        container.add(close);
        container.add(label_4);
        container.add(label_5);
        container.add(label_6);
        window.setVisible(true);
    }


}

