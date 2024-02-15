package com.example.demo.command;

import java.util.ArrayList;
import java.util.List;

public class FileExecutor {
    public <T> T executeOperation( Command<T> textFileOperation) {
        return textFileOperation.execute();
    }
}
