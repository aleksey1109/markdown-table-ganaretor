package ru.AlexeyFedechkin.markdown.tableGenetetor;

public interface InputBackend {

    int getColumns();
    int getLines();
    String[][] getData();
    void showResult(String result);

}
