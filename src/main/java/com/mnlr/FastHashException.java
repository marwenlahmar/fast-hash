package com.mnlr;

public class FastHashException extends RuntimeException {

  public FastHashException(String message, Throwable e) {
    super(message, e);
  }

  public FastHashException(String message) {
    super(message);
  }
}
