package com.example.demo;

import com.example.demo.command.FileExecutor;
import com.example.demo.command.ReadCommand;
import com.example.demo.command.TextFileWrite;
import com.example.demo.model.TextFile;

public class Demo2Application {

    public static void main(String[] args) {
        String string = """
                jfdjfjjf\s
                kfjkfjk
                lfkkfkfkk
                """;
        FileExecutor fileExecutor = new FileExecutor();
        TextFile textFile = fileExecutor.executeOperation(new TextFileWrite(string, new TextFile("hfhfh")));
        System.out.println(fileExecutor.executeOperation(new ReadCommand(textFile)));
    }

}
