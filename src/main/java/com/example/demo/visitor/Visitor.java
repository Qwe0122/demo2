package com.example.demo.visitor;

@FunctionalInterface
public interface Visitor {
    void accept(UserElement element);
}
