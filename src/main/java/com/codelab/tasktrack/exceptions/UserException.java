package com.codelab.tasktrack.exceptions;

public class UserException extends RuntimeException{

    public static class UserExistsException extends RuntimeException {
        public UserExistsException(String message) {
            super(message);
        }
    }

    public static class UserNotFoundException extends RuntimeException {
        public UserNotFoundException(String message) {
            super(message);
        }
    }

    public static class UserUnauthorizedException extends RuntimeException {
        public UserUnauthorizedException(String message) {
            super(message);
        }
    }

}
