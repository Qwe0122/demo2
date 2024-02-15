package com.example.demo.facade;

import com.example.demo.model.TextFile;
import com.example.demo.model.User;

import java.util.List;

public class Tracker {

    private final User user;

    public Tracker(User user) {
        this.user = user;
    }

    public List<TextFile> getFiles() {
       return user.getFiles();
    }
}
