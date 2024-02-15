package com.example.demo.model;

import java.io.*;
import java.util.Scanner;

public class TextFile {
    private final String fileName;


    public TextFile(String fileName) {
        this.fileName = fileName;
    }

    public String open() {
        StringBuilder builder = new StringBuilder();
        try {
            FileReader fileReader = new FileReader(fileName);
            Scanner scanner = new Scanner(fileReader);
            while (scanner.hasNextLine()) {
                builder.append(scanner.nextLine()).append("\n");
            }
        fileReader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return builder.toString();
    }

    public void write(String text) {
        try {
            FileWriter fileWriter = new FileWriter(fileName);
            fileWriter.write(text);
            fileWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
