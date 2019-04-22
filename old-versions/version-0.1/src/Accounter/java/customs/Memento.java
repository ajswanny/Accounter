/*
Copyright (c) 2019, Alexander Joseph Swanson Villares
alexanderjswanson@icloud.com | https://github.com/ajswanny

References:
    Design Patterns - Gama et. al
*/


/* Package */
package Accounter.java.customs;


/**
 *
 * @param <T>
 */
public class Memento<T> {

    /**
     *
     */
    private T object;

    /**
     *
     * @return
     */
    public T get_state() {

        return object;

    }

    /**
     *
     * @param object
     */
    public void set_state(T object) {

        this.object = object;

    }

}
