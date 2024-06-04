package com.example.chat_test.model;

public class RoomIdGenerator {
    private static Long id = 0L;

    public static Long createId() {
        id += 1;
        return id;
    }
}
