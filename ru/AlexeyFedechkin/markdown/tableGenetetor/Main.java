package ru.AlexeyFedechkin.markdown.tableGenetetor;

import java.awt.datatransfer.Clipboard;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.awt.datatransfer.StringSelection;
import java.awt.Toolkit;

/**
 * a small program that generates a markdown table
 * @author Alexey Fedechkin https://github.com/aleksey1109
 * @version 1.0
 */

class Main {

     private static final Scanner        scanner = new Scanner(System.in);
     private static final BufferedReader s = new BufferedReader(new InputStreamReader(System.in));
     private static byte                 columns;
     private static byte                 lines;
     private static String[][]           table;
     private static final int            WIDTH = 110;
     private static int                  cellWidth;
     private static int                  realCellWidth;
     private static final StringBuilder  markdownTable = new StringBuilder();
    /**
     * @param args dimension of array. columns x lines
     */
    public static void main(String[] args) throws IOException {
        if (args.length == 0){
            System.out.println("Введите размерность таблицы, сначала количество столбцов, затем количество строк через пробел");
            var dimension = scanner.nextLine();
            String[] dimensions = dimension.split(" ");
            try {
                columns = Byte.parseByte(dimensions[0]);
                lines = Byte.parseByte(dimensions[1]);
                cellWidth = WIDTH / columns;
            } catch (NumberFormatException e) {
                System.err.println("Не корректный данные");
                Main.main(null);
            }
        } else {
            try {
                columns = Byte.parseByte(args[0]);
                lines = Byte.parseByte(args[1]);
                cellWidth = WIDTH / columns;
            } catch (NumberFormatException e) {
                System.err.println("Не корректный данные");
                Main.main(null);
            }
            System.out.println("Используется таблица размерности: "+ columns + "x" + lines);
        }
        table = new String[columns][lines];
        System.out.println("Выберите способ ввода." +
                "\n>1: с произвольным доступом к ячейкам" +
                "\n>2: с последовательным вводом");
        var answer = scanner.nextInt();
        if (answer == 1){
            randomAccessInput();
        } else if (answer == 2){
            consistentInput();
        }
        if (max(table) < cellWidth){
            cellWidth = max(table);
        }
        realCellWidth = max(table);
        generateMarkdownTable();
        System.out.println(markdownTable.toString());
        copyToClipBoard();
        System.out.println("Таблица скопировано в буфер обмена");
        System.in.read();
    }

    /**
     * copy string with markdown table to clipboard
     * @warning past string from clipboard can be only while program is running.
     * @since 1.0
     */
    private static void copyToClipBoard(){
        StringSelection stringSelection = new StringSelection(markdownTable.toString());
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);
    }

    /**
     * a method that,
     * using a two-dimensional array,
     * generates a string with a markdown table
     * @since 1.0
     */
    private static void generateMarkdownTable(){
        for (int i = 0; i < lines; i++){
            for (int j = 0; j < columns; j++){
                markdownTable.append(" |");
                markdownTable.append(table[j][i]);
                if (table[j][i].length() < realCellWidth){
                    markdownTable.append(generateString(realCellWidth - table[j][i].length(),' '));
                }
            }
            markdownTable.append("|");
            markdownTable.append("\n");
            if (i == 0){
                for (int q = 0; q < columns; q++) {
                    markdownTable.append(" ");
                    markdownTable.append("|");
                    markdownTable.append(":");
                    markdownTable.append(generateString(realCellWidth -1,'-'));
                }
                markdownTable.append("|");
                markdownTable.append("\n");
            }
        }
    }

    /**
     *method for data entry in
     *cells with sequential data entry
     *@since 1.0
     */
    private static void consistentInput() {
        for (int i = 0; i < lines; i++) {
            for (int j = 0; j < columns; j++) {
                System.out.println("Введите текст ячейки: " + i + "x" + j );
                String input = null;
                try {
                    input = s.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                table[j][i] = input;
                printArray(table);
            }
        }
    }

    /**
     *method for entering data
     *from table cells using random access
     *to the input cell
     *@since 1.0
     */
    private static void randomAccessInput(){
        while (true){
            System.out.println("Введите ячейку. Для выхода введите \"-1\"");
            var cell = scanner.nextLine();
            if (cell.equals("-1")){
                break;
            }
            var c = cell.split(" ");
            System.out.println("Введите текст ячейки");
            String input = scanner.nextLine();
            table[Integer.parseInt(c[0])-1][Integer.parseInt(c[1])-1] = input;
            printArray(table);
        }
    }

    /**
     * find max string length in two-dimensional array
     * @param table two-dimensional array
     * @return maximum value of two-dimensional array
     * @since 1.0
     */
    private static int max(String[][] table) {
        var max = 0;
        for(String[] arr: table){
            for(String val: arr){
                if (val.length() > max){
                    max = val.length();
                }
            }
        }
        return max;
    }

    /**
     * printArray two-dimensional array
     * @param table two-dimensional array which is needed to printArray
     * @since 1.0
     */
    private static void printArray(String[][] table) {
        if (isContainNull(table)){
            for (int i = 0; i < lines; i++) {
                for (int j = 0; j < columns; j++) {
                    System.out.print(table[j][i]);
                    System.out.print("\t");
                }
                System.out.println();
            }
        }
    }

    /**
     * check two-dimension on null value
     * @param table two-dimension array
     * @return true if two-dimension array contain null
     */
    private static boolean isContainNull(String[][] table){
        for (String[] arr: table){
            for (String str : arr){
                if (str == null){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * the method generates a sequence with a symbol of a given length
     * @param countSpaces count of chars for generated string
     * @param ch the character from which the string will be composed
     * @return generated string
     * @since 1.0
     */
    private static String generateString(int countSpaces, char ch){
        return String.valueOf(ch).repeat(Math.max(0, countSpaces));
    }
}