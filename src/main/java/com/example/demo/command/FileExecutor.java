package com.example.demo.command;

import java.util.ArrayList;
import java.util.List;

public class FileExecutor {
    private final List<Command<?>> textFileOperations = new ArrayList<>();

    public <T> T executeOperation( Command<T> textFileOperation) {
        textFileOperations.add(textFileOperation);
        return textFileOperation.execute();
    }
}
