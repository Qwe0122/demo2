package com.example.demo;

import com.example.demo.service.UserServiceImpl;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RequiredArgsConstructor
public class Demo2Application {

    private final UserServiceImpl userService;
    public static void main(String[] args) {
        SpringApplication.run(Demo2Application.class,args);
    }

    @PostConstruct
    void init() {
        userService.getEvents();
    }

//    public static void main(String[] args) throws HttpException {
////        "00000000000000000007316856900e76b4f7a9139cfbfba89842c8d196cd5f91" - block 600000
//        UserServiceImpl userService = new UserServiceImpl();
//        var tx = userService.getEvents("00000000000000000007316856900e76b4f7a9139cfbfba89842c8d196cd5f91");
//
////        TxParser txParser = new TxParser();
////        StringBuilder equalInputs = txParser.getEqualInputs();
//        System.out.println(tx.toString());
//
////        Visitor visitor = new ElementVisitor();
////        visitor.accept(new UserElement("GGGhds"));
////        visitor.accept(new UserElement("yeryry("));
////
////        String string = """
////                jfdjfjjf 12334
////                kfjkfjk
////                lfkkfkfkk
////                """;
////        FileExecutor fileExecutor = new FileExecutor();
////        TextFile textFile = fileExecutor.executeOperation(new TextFileWrite(string, new TextFile("hfhfh")));
////        System.out.println(fileExecutor.executeOperation(new ReadCommand(textFile)));
//    }
}
