package com.codelab.tasktrack.exceptions;

public class ToDoException extends RuntimeException {

    public static class ToDoNotFoundException extends RuntimeException {
        public ToDoNotFoundException(String message) {
            super(message);
        }
    }

}
