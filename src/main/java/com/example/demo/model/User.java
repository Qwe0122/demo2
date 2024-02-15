package com.example.demo.model;

import java.util.List;

public class User {

    private Long id;
    private String firstName;
    private String lastName;

    private List<TextFile> files;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<TextFile> getFiles() {
        return files;
    }

    public void setFiles(List<TextFile> files) {
        this.files = files;
    }
}
