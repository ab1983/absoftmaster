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
public class CustomExceptionHandlerFactory extends ExceptionHandlerFactory {
 
  private ExceptionHandlerFactory parent;
 
  public CustomExceptionHandlerFactory(ExceptionHandlerFactory parent) {
    this.parent = parent;
  }
 
  @Override
  public ExceptionHandler getExceptionHandler() {
    ExceptionHandler result = new CustomExceptionHandler(parent.getExceptionHandler());
    return result;
  }
}
