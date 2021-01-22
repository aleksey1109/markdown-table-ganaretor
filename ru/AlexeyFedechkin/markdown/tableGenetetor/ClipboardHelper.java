package ru.AlexeyFedechkin.markdown.tableGenetetor;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

public class ClipboardHelper {

    /**
     * copy string with markdown table to clipboard
     * @warning past string from clipboard can be only while program is running.
     * @since 1.0
     */
    public static void copyToClipBoard(String str){
        StringSelection stringSelection = new StringSelection(str);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);
    }

}
