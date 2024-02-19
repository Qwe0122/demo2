package com.example.demo.visitor;

public class UserElement extends Element{


    public UserElement(String name) {
        super(name);
    }
    @Override
    public void accept(Visitor v) {
        v.accept(this);
    }
}
