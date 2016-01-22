package com.github.bijoysingh.starter.json;

/**
 * Created by bijoy on 1/21/16.
 */
public class JsonModelException extends RuntimeException {

    public JsonModelException() {
    }

    public JsonModelException(String detailMessage) {
        super(detailMessage);
    }

    public JsonModelException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public JsonModelException(Throwable throwable) {
        super(throwable);
    }

    public enum ErrorResponse {
        OK("OK"),
        JSON_FIELD_WRONG_CLASS("JSON_MODEL Field must extend JSONModel"),;

        private final String errorMessage;

        ErrorResponse(String s) {
            errorMessage = s;
        }

        public String toString() {
            return this.errorMessage;
        }
    }
}
