package com.example.demo;

import com.example.demo.command.FileExecutor;
import com.example.demo.command.ReadCommand;
import com.example.demo.command.TextFileWrite;
import com.example.demo.model.TextFile;
import com.example.demo.service.TxParser;
import com.example.demo.service.UserServiceImpl;
import com.example.demo.visitor.ElementVisitor;
import com.example.demo.visitor.UserElement;
import com.example.demo.visitor.Visitor;
import org.apache.http.HttpException;

import java.util.List;

public class Demo2Application {

    public static void main(String[] args) throws HttpException {
//        00000000000000000007316856900e76b4f7a9139cfbfba89842c8d196cd5f91
        UserServiceImpl userService = new UserServiceImpl();
        List<String> tx = userService.getEvents();

        TxParser txParser = new TxParser();
        StringBuilder equalInputs = txParser.getEqualInputs(tx);
        System.out.println(equalInputs.toString());

//        Visitor visitor = new ElementVisitor();
//        visitor.accept(new UserElement("GGGhds"));
//        visitor.accept(new UserElement("yeryry("));
//
//        String string = """
//                jfdjfjjf 12334
//                kfjkfjk
//                lfkkfkfkk
//                """;
//        FileExecutor fileExecutor = new FileExecutor();
//        TextFile textFile = fileExecutor.executeOperation(new TextFileWrite(string, new TextFile("hfhfh")));
//        System.out.println(fileExecutor.executeOperation(new ReadCommand(textFile)));
    }
}
