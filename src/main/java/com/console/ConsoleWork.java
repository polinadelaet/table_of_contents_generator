package com.console;

import com.Content;

import java.io.*;
import java.util.Scanner;

public class ConsoleWork {

    private Scanner skan;
    private StringBuilder tableOfContents;

    public ConsoleWork(InputStream inputStream, OutputStream outputStream) {
        this.skan = new Scanner(inputStream);
    }

    public String readLine(){
        return skan.nextLine();
    }



    public void start() throws IOException {

        System.out.println("Welcome! You can enter the file name for adding table of contents.");

        while (true) {
            System.out.println("file name:");

            String filePath = readLine();
            File file = new File(filePath);
            FileReader fileReader = null;
            try {
                fileReader = new FileReader(file);
                Content content = new Content(file);
                System.out.println(content.createContents());
            } catch (IOException e) {
                System.out.println("Такого файла не существует или нет доступа к файлу.");
            }
        }

    }


}
