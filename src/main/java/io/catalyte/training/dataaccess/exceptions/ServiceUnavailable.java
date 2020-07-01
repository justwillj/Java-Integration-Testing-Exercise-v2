package io.catalyte.training.dataaccess.exceptions;

import org.hibernate.JDBCException;

public class ServiceUnavailable extends RuntimeException {

  public ServiceUnavailable() {}

  public ServiceUnavailable(String message) {
    super(message);
  }

  public ServiceUnavailable(Exception e){
    super(e.getCause());
  }
}
