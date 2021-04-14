package org.example;

public class SystemConsolePrinter implements PrinterService {

    @Override
    public void print(String string) {

        System.out.println(string);
    }
}
