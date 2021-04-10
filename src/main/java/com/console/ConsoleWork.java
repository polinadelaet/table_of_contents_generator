package com.console;

import com.Content;

import java.io.*;
import java.util.Scanner;

public class ConsoleWork {

    private Scanner scanner;

    public ConsoleWork(InputStream inputStream) {
        this.scanner = new Scanner(inputStream);
    }

    public String readLine(){
        return scanner.nextLine();
    }

    public void start() {

        System.out.println("Welcome! You can enter a file path for adding table of " +
                            "contents or enter \"exit\" to exit the program.");

        while (true) {
            System.out.println("\nEnter file path or exit:");

            String str = readLine();
            if (str.equals("exit")) {
                System.exit(0);
            }

            File file = new File(str);

            if (file.exists()) {
                if (!file.canRead()){
                    System.out.println("You don't have permission to read this file.");
                    System.exit(0);
                }
            } else {
                System.out.println("File does not exist or cannot be opened for reading.");
                continue;
            }

            Content content = new Content(file);
            System.out.println(content.createContents());
        }
    }
}
