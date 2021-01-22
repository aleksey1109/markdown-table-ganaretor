package ru.AlexeyFedechkin.markdown.tableGenetetor;

import java.util.Arrays;

public class MarkdownTable {


    private final InputBackend inputBackend;
    private final int lines;
    private final int columns;
    private final String[][] data;
    private final int realCellWidth;
    private String asString = "";

    public MarkdownTable(InputBackend inputBackend) {
        this.inputBackend = inputBackend;
        lines = inputBackend.getLines();
        columns = inputBackend.getColumns();
        data = inputBackend.getData();
        realCellWidth = maxValueLength();
    }

    /**
     * a method that,
     * using a two-dimensional array,
     * generates a string with a markdown table
     * @since 1.0
     */
    public String asString(){
        if (!asString.isEmpty()) return asString;
        var markdownTable = new StringBuilder();
        for (int i = 0; i < lines; i++){
            for (int j = 0; j < columns; j++){
                markdownTable.append(" |");
                markdownTable.append(data[j][i]);
                if (data[j][i].length() < realCellWidth){
                    markdownTable.append(generateString(realCellWidth - data[j][i].length(),' '));
                }
            }
            markdownTable.append("|");
            markdownTable.append("\n");
            if (i == 0){
                for (int q = 0; q < columns; q++) {
                    markdownTable.append(" |:");
                    markdownTable.append(generateString(realCellWidth -1,'-'));
                }
                markdownTable.append("|" + "\n");
            }
        }
        asString = markdownTable.toString();
        return asString;
    }

    public void showResult(){
        inputBackend.showResult(asString());
    }

    private int maxValueLength() {
        return Arrays.stream(data).mapToInt(v -> v.length).max().getAsInt();
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
