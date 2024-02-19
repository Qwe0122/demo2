package com.example.demo.visitor;

public abstract class Element {

    public String name;

    public Element(String name) {
        this.name = name;
    }
    public abstract void accept(Visitor v);
}
