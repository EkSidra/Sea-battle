package Work_with_file;
import java.io.*;
import Graphics.Graphic.*;

public class Mfile {
    private static final String File_Name = "Files\\Best.txt";
    private static final String sp = System.getProperty("line.separator");

    public static void readFromFile(int count,String[] line)
    {
        try {
            File file = new File(File_Name);
            //создаем объект FileReader для объекта File
            FileReader fr = new FileReader(file);
            //создаем BufferedReader с существующего FileReader для построчного считывания
            BufferedReader reader = new BufferedReader(fr);
            // считаем сначала первую строку
            int number=1;
            do{
                line[number-1] = reader.readLine();
                System.out.println(line[number-1]);
                number++;
            }while (number!=4);
            System.out.println("------------------------------------------------");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return;
    }

    public static int search(String[] line,int count)
    {
        for(int i=0;i<3;i++) {
            String lastWord = line[i].substring(line[i].lastIndexOf(" ") + 1);
            //System.out.println(lastWord);
            if (count < Integer.parseInt(lastWord)) {
                return i+1;
            }
            else
            {
                return 0;
            }
        }
        return 0;
    }

    public static void writeFile(String[] line) throws FileNotFoundException {
        clearFile();
        File file = new File(File_Name);
        PrintWriter write = new PrintWriter(file);
        int number=1;
        do{
            if(number==3){
                write.print(line[number-1]);
            }else{
                write.print(line[number-1]+sp);
            }
            System.out.println(line[number-1]);
            number++;
        }while (number!=4);
        write.close();
        return;
    }

    public static void clearFile() throws FileNotFoundException {
        File file = new File(File_Name);
        PrintWriter writer = new PrintWriter(file);
        writer.print("");
        writer.close();
        return;
    }

}

