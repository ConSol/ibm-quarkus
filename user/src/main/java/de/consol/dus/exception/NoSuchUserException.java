package de.consol.dus.exception;

public class NoSuchUserException extends RuntimeException {
  public NoSuchUserException(String message) {
    super(message);
  }
}
