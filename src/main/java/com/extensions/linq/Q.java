/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.extensions.linq;

import com.extensions.linq.exceptions.ReflectionOperationException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.*;
import java.util.Map.*;
import javax.management.ReflectionException;

/**
 *
 * @author tkhalilov
 * @param <T>
 *
 *
 */
public class Q<T> {

    private AbstractList<T> list;

    public Q(AbstractList<T> list) throws IllegalArgumentException {

        if (list == null) {
            throw new IllegalArgumentException("'list' argument must not be null.");
        }

        this.list = new ArrayList<>(list);
    }

    public T first() {
        return list.isEmpty() ? null : list.get(0);
    }

    public T last() {
        return list.isEmpty() ? null : list.get(list.size() - 1);
    }

    public int count() {
        return list.size();
    }

    public Q<T> where(String propertyName, Object value) throws ReflectionOperationException, IllegalArgumentException {

        if (isNull(propertyName)) {
            throw new IllegalArgumentException("'propertyName' argument must not be null.");
        }

        if (isNull(value)) {
            throw new IllegalArgumentException("'value' argument must not be null.");
        }

        ArrayList<T> result = new ArrayList<>();

        for (T o : list) {
            try {
                if (isFieldEqual(o, propertyName, value)) {
                    result.add(o);
                }
            } catch (IllegalAccessException | NoSuchFieldException | SecurityException ex) {
                throw new ReflectionOperationException(ex.getMessage(), ex.getCause());
            }
        }

        list = result;

        return this;
    }

    public Q<T> where(HashMap<String, Object> properties) throws ReflectionOperationException, IllegalArgumentException {

        if (isNull(properties)) {
            throw new IllegalArgumentException("'properties' argument must not be null.");
        }

        ArrayList<T> result = new ArrayList<>();

        for (T o : list) {

            boolean arePropertiesEqual = true;

            for (Entry<String, Object> entry : properties.entrySet()) {
                try {
                    if (!isFieldEqual(o, entry.getKey(), entry.getValue())) {
                        arePropertiesEqual = false;
                        break;
                    }
                } catch (IllegalAccessException | NoSuchFieldException | SecurityException ex) {
                    throw new ReflectionOperationException(ex.getMessage(), ex.getCause());
                }
            }

            if (arePropertiesEqual) {
                result.add(o);
            }
        }

        list = result;

        return this;
    }

    public Q<T> where(QCompare compare) throws IllegalArgumentException {

        if (isNull(compare)) {
            throw new IllegalArgumentException("'compare' argument must not be null.");
        }

        ArrayList<T> result = new ArrayList<>();

        // iterating through a list of objects
        for (T o : list) {

            // using the comparison function (like a delegate in C#) which implemente QCompare class
            if (compare.is(o)) {
                result.add(o);
            }
        }

        list = result;

        return this;
    }

    public boolean all(QCompare compare) {

        if (isNull(compare)) {
            throw new IllegalArgumentException("'compare' argument must not be null.");
        }

        boolean returnValue = true;

        for (T o : list) {
            if (!compare.is(o)) {
                returnValue = false;
                break;
            }
        }

        return returnValue;
    }

    public Q<T> add(T value) {

        return this;
    }

    public Q<T> addAt(T value, int index) {

        if (index < 0) {
            throw new IllegalArgumentException("'index' must be zero or greater.");
        }

        if (isNull(value)) {
            throw new IllegalArgumentException("'value' argument must not be null.");
        }

        list.add(index, value);

        return this;
    }

    public Q<T> addRange(AbstractList<T> values) {

        if (isNull(values)) {
            throw new IllegalArgumentException("'value' argument must not be null.");
        }

        list.addAll(values);

        return this;
    }
    
    public <V> Q<V> select(QSelect<T, V> select) throws IllegalArgumentException {

        AbstractList<V> result = new ArrayList<>();

        if (isNull(select)) {
            throw new IllegalArgumentException("'select' argument must not be null.");
        }

        for (T o : list) {
            result.add(select.select(o));
        }

        return new Q<V>(result);
    }

    public boolean contains(T value) throws ReflectionOperationException, IllegalArgumentException {

        if (isNull(value)) {
            throw new IllegalArgumentException("'value' argument must not be null.");
        }

        boolean returnValue = false;

        Field[] fields = value.getClass().getDeclaredFields();

        for (T o : list) {
            try {

                boolean contains = true;

                // iterating through all fields, to compare field's value of current object, and comparison object
                for (Field f : fields) {

                    Object first = getFieldValue(o, f.getName());
                    Object second = getFieldValue(value, f.getName());

                    if (first == null && second != null) { // if first is null, and second is not null, the definitely they aren't equal
                        contains = false;
                        break;
                    } else if (first != null && second == null) { // if first is not null, and second is null, the definitely they aren't equal
                        contains = false;
                        break;
                    } else if (first != null && second != null) { // if both aren't null, then we start the comparison
                        if (!first.equals(second)) {
                            contains = false;
                            break;
                        }
                    }
                }

                if (contains) {
                    returnValue = true;
                    break;
                }

            } catch (IllegalAccessException | NoSuchFieldException | SecurityException ex) {
                throw new ReflectionOperationException(ex.getMessage(), ex.getCause());
            }
        }

        return returnValue;
    }

    public boolean any() {
        return list.size() > 0;
    }

    public boolean any(String propertyName, Object value) throws ReflectionOperationException, IllegalArgumentException {

        if (isNull(propertyName)) {
            throw new IllegalArgumentException("'propertyName' argument must not be null.");
        }

        if (isNull(value)) {
            throw new IllegalArgumentException("'value' argument must not be null.");
        }

        boolean returnValue = false;

        for (T o : list) {
            try {
                returnValue = isFieldEqual(o, propertyName, value);
            } catch (IllegalAccessException | NoSuchFieldException | SecurityException ex) {
                throw new ReflectionOperationException(ex.getMessage(), ex.getCause());
            }
        }

        return returnValue;
    }

    public boolean any(HashMap<String, Object> properties) throws ReflectionOperationException, IllegalArgumentException {

        if (isNull(properties)) {
            throw new IllegalArgumentException("'properties' argument must not be null.");
        }

        boolean returnValue = false;

        for (T o : list) {

            boolean arePropertiesEqual = true;

            for (Entry<String, Object> entry : properties.entrySet()) {
                try {
                    if (!isFieldEqual(o, entry.getKey(), entry.getValue())) {
                        arePropertiesEqual = false;
                        break;
                    }
                } catch (IllegalAccessException | NoSuchFieldException | SecurityException ex) {
                    throw new ReflectionOperationException(ex.getMessage(), ex.getCause());
                }
            }

            if (arePropertiesEqual) {
                returnValue = true;
                break;
            }
        }

        return returnValue;
    }

    public boolean any(QCompare compare) throws IllegalArgumentException {

        if (isNull(compare)) {
            throw new IllegalArgumentException("'compare' argument must not be null.");
        }

        boolean returnValue = false;

        // iterating through a list of objects
        for (T o : list) {

            // using the comparison function (like a delegate in C#) which implemente QCompare class
            if (compare.is(o)) {

                // if any element found, we return and break
                returnValue = true;
                break;
            }
        }

        return returnValue;
    }

    public AbstractList<T> toList() {
        return list;
    }

    public T[] toArray() {
        return getArray(list);
    }

    private boolean isNull(Object value) {
        return value == null;
    }

    private boolean isFieldEqual(Object real, String property, Object value)
            throws IllegalAccessException, NoSuchFieldException, NoSuchFieldException, SecurityException {

        boolean returnValue = false;

        try {
            // get field via reflection
            Field field = real.getClass().getDeclaredField(property);

            if (field != null) {

                // check if the field is private (that means not accessible)
                if (!field.isAccessible()) {
                    field.setAccessible(true);   // if private, allow the field's value to be accessed
                }

                // compare between the field's value and the passed value
                if (field.get(real).equals(value)) {
                    returnValue = true;
                }

            }
        } catch (IllegalArgumentException ex) {
            throw ex;
        } catch (IllegalAccessException ex) {
            throw ex;
        } catch (NoSuchFieldException ex) {
            throw ex;
        } catch (SecurityException ex) {
            throw ex;
        }

        return returnValue;
    }

    private Object getFieldValue(Object value, String property)
            throws IllegalAccessException, NoSuchFieldException, NoSuchFieldException, SecurityException {

        Object returnValue = null;

        try {
            // get field via reflection
            Field field = value.getClass().getDeclaredField(property);

            // check if the field is private (that means not accessible)
            if (!field.isAccessible()) {
                field.setAccessible(true);   // if private, allow the field's value to be accessed
            }

            returnValue = field.get(value);

        } catch (IllegalArgumentException ex) {
            throw ex;
        } catch (IllegalAccessException ex) {
            throw ex;
        } catch (NoSuchFieldException ex) {
            throw ex;
        } catch (SecurityException ex) {
            throw ex;
        }

        return returnValue;
    }

    private T[] getArray(AbstractList<T> value) {

        T[] result = (T[]) new Object[value.size()];

        for (int i = 0; i < value.size(); i++) {
            result[i] = value.get(i);
        }

        return result;
    }

}
