package ru.AlexeyFedechkin.markdown.tableGenetetor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class ConsoleBackend implements InputBackend{

    private final Scanner scanner = new Scanner(System.in);
    private static final BufferedReader s = new BufferedReader(new InputStreamReader(System.in));
    private int lines = -1;
    private int columns = -1;

    public ConsoleBackend(int lines, int columns){
        this.lines = lines;
        this.columns = columns;
    }

    public ConsoleBackend(){}

    @Override
    public int getColumns() {
        if (columns != -1) return columns;
        System.out.println("Input column count");
        String input = scanner.nextLine();
        try {
            columns = Integer.parseInt(input);
            if (Validator.validateCount(columns)) throw new IllegalArgumentException();
            return columns;
        } catch (NumberFormatException e){
            System.out.println("incorrect value");
            return getColumns();
        }
    }

    @Override
    public int getLines() {
        if (lines != -1) return columns;
        System.out.println("Input lines count");
        String input = scanner.nextLine();
        try {
            lines = Integer.parseInt(input);
            if (Validator.validateCount(lines)) throw new IllegalArgumentException();
            return lines;
        } catch (NumberFormatException e){
            System.out.println("incorrect value");
            return getColumns();
        }
    }

    @Override
    public String[][] getData() {
        System.out.println("Используется таблица размерности: "+ columns + "x" + lines);
        System.out.println("""
                Выберите способ ввода.
                >1: с произвольным доступом к ячейкам
                >2: с последовательным вводом""");
        return scanner.nextInt() == 1? randomAccessInput() : consistentInput();
    }

    @Override
    public void showResult(String result) {
        System.out.println(result);
    }

    /**
     *method for data entry in
     *cells with sequential data entry
     *@since 1.0
     */
    private String[][] consistentInput() {
        String[][] data = new String[columns][lines];
        for (int i = 0; i < lines; i++) {
            for (int j = 0; j < columns; j++) {
                System.out.println("Введите текст ячейки: " + i + "x" + j );
                String input = null;
                try {
                    input = s.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                data[j][i] = input;
                printArray(data);
            }
        }
        return data;
    }

    /**
     *method for entering data
     *from table cells using random access
     *to the input cell
     *@since 1.0
     */
    private String[][] randomAccessInput(){
        String[][] data = new String[columns][lines];
        while (true){
            System.out.println("Введите ячейку. Для выхода введите \"-1\"");
            var cell = scanner.nextLine();
            if (cell.equals("-1")){
                break;
            }
            var c = cell.split(" ");
            System.out.println("Введите текст ячейки");
            String input = scanner.nextLine();
            data[Integer.parseInt(c[0])-1][Integer.parseInt(c[1])-1] = input;
            printArray(data);
        }
        return data;
    }

    /**
     * printArray two-dimensional array
     * @param table two-dimensional array which is needed to printArray
     * @since 1.0
     */
    private void printArray(String[][] table) {
        for (int i = 0; i < lines; i++) {
            for (int j = 0; j < columns; j++) {
                System.out.print(table[j][i] + "\t");
            }
            System.out.println();
        }
    }

}
