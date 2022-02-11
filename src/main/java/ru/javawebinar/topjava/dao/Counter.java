package ru.javawebinar.topjava.dao;

public class Counter {
    private static Integer id = 1;

    public synchronized Integer getNextId() {
        return id++;
    }
}
