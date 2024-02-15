package com.example.demo.command;

import com.example.demo.model.TextFile;

public class TextFileWrite implements Command<TextFile>{
    private final String text;
    private final TextFile textFile;

    public TextFileWrite(String text, TextFile textFile) {
        this.text = text;
        this.textFile = textFile;
    }

    @Override
    public TextFile execute() {
         textFile.write(text);
         return textFile;
    }
}
