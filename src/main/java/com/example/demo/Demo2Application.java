package com.example.demo;

import com.example.demo.command.FileExecutor;
import com.example.demo.command.ReadCommand;
import com.example.demo.command.TextFileWrite;
import com.example.demo.model.TextFile;
import com.example.demo.visitor.ElementVisitor;
import com.example.demo.visitor.UserElement;
import com.example.demo.visitor.Visitor;

public class Demo2Application {

    public static void main(String[] args) {

        Visitor visitor = new ElementVisitor();
        visitor.accept(new UserElement("GGGhds"));
        visitor.accept(new UserElement("yeryry("));

        String string = """
                jfdjfjjf 12334
                kfjkfjk
                lfkkfkfkk
                """;
        FileExecutor fileExecutor = new FileExecutor();
        TextFile textFile = fileExecutor.executeOperation(new TextFileWrite(string, new TextFile("hfhfh")));
        System.out.println(fileExecutor.executeOperation(new ReadCommand(textFile)));
    }

}
