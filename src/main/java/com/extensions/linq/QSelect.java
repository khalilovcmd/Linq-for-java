/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.extensions.linq;

/**
 *
 * @author tkhalilov
 */
public interface QSelect<T, V> {
    V select(T o);
}
