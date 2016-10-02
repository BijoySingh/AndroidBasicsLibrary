package com.github.bijoysingh.starter.json;

/**
 * JsonModel exceptions
 * Created by bijoy on 1/21/16.
 */
public class JsonModelException extends RuntimeException {

  /**
   * JsonModelException Constructor
   */
  public JsonModelException() {
  }

  /**
   * JsonModelException Constructor
   *
   * @param detailMessage the error message
   */
  public JsonModelException(String detailMessage) {
    super(detailMessage);
  }

  /**
   * JsonModelException Constructor
   *
   * @param detailMessage the error message
   * @param throwable     the throwable object
   */
  public JsonModelException(String detailMessage, Throwable throwable) {
    super(detailMessage, throwable);
  }

  /**
   * JsonModelException Constructor
   *
   * @param throwable the throwable object
   */
  public JsonModelException(Throwable throwable) {
    super(throwable);
  }

  /**
   * Common Error Responses for the JSONModelException
   */
  public enum ErrorResponse {
    OK("OK"),
    JSON_FIELD_WRONG_CLASS("JSON_MODEL Field must extend JSONModel"),
    UNKNOWN_AUTO("Could not understand the json field type automatically"),;

    private final String errorMessage;

    ErrorResponse(String s) {
      errorMessage = s;
    }

    public String toString() {
      return this.errorMessage;
    }
  }
}
