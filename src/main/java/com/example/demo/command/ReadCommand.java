package com.example.demo.command;

import com.example.demo.model.TextFile;

public class ReadCommand implements Command<String>{

    private final TextFile textFile;

    public ReadCommand(TextFile textFile) {
        this.textFile = textFile;
    }

    @Override
    public String execute() {
        return textFile.open();
    }
}
