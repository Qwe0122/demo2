package com.example.demo.command;

@FunctionalInterface
public interface Command<T> {
    T execute();
}
