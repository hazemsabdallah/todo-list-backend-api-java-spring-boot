package com.example.todolist.model;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Priority {

    HIGH("HIGH"),
    LOW("LOW"),
    NORMAL("NORMAL");

    private final String value;

    Priority(String value) {
        this.value = value;
    }

    @JsonValue
    public String getVale() {
        return value;
    }
}