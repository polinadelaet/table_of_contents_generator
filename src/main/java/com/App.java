package com;

import com.console.ConsoleWork;

import java.io.IOException;

public class App {
    public static void main(String[] args) throws IOException {
        ConsoleWork consoleWork = new ConsoleWork(System.in);
        consoleWork.start();
    }
}
