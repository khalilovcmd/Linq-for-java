/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.extensions.exceptions;

/**
 *
 * @author tkhalilov
 */
public class ReflectionOperationException extends Exception {

    public ReflectionOperationException() {
        super();
    }

    public ReflectionOperationException(String message) {
        super(message);
    }

    public ReflectionOperationException(String message, Throwable cause) {
        super(message, cause);
    }

}
