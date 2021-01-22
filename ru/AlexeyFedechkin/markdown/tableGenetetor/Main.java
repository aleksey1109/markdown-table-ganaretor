package ru.AlexeyFedechkin.markdown.tableGenetetor;

/**
 * a small program that generates a markdown table
 * @author Alexey Fedechkin https://github.com/aleksey1109
 * @version 2.0
 */

class Main {

    /**
     * @param args dimension of array. columns x lines
     */
    public static void main(String[] args) {
        MarkdownTable markdownTable;
        if (args.length == 0){
            markdownTable = new MarkdownTable(new ConsoleBackend());
        } else {
            int lines = Integer.parseInt(args[1]);
            int columns = Integer.parseInt(args[0]);
            markdownTable = new MarkdownTable(new ConsoleBackend(lines, columns));
        }
        markdownTable.showResult();
        ClipboardHelper.copyToClipBoard(markdownTable.asString());
    }
}