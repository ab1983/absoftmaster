/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.algoboss.core.exceptions;

import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerFactory;

/**
 *
 * @author Agnaldo
 */
public class MyExceptionHandlerFactory extends ExceptionHandlerFactory {

  private ExceptionHandlerFactory parent;

  // this injection handles jsf
  public MyExceptionHandlerFactory(ExceptionHandlerFactory parent) {
    this.parent = parent;
  }

  //create your own ExceptionHandler
  @Override
  public ExceptionHandler getExceptionHandler() {
    ExceptionHandler result =
        new MyExceptionHandler(parent.getExceptionHandler());
    return result;
  }
}
