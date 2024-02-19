package com.example.demo.visitor;

public class ElementVisitor implements Visitor {

    @Override
    public void accept(UserElement element) {
        System.out.println(element.name);
    }
}
