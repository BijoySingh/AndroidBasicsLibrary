package com.github.bijoysingh.starter.database;

/**
 * Created by bijoy on 1/13/16.
 */
public class DatabaseException extends RuntimeException {

    public DatabaseException() {
    }

    public DatabaseException(String detailMessage) {
        super(detailMessage);
    }

    public DatabaseException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public enum ErrorResponse {
        OK("OK"),
        MULTIPLE_PRIMARY_KEYS("Multiple Primary Keys Defined"),
        NON_INTEGER_AUTO_INCREMENT_PRIMARY_KEY("Autoincrement must a Integer Key"),
        AUTO_INCREMENT_NOT_PRIMARY("Auto Incremented Key must be Primary");

        private final String errorMessage;

        ErrorResponse(String s) {
            errorMessage = s;
        }

        public String toString() {
            return this.errorMessage;
        }
    }
}

