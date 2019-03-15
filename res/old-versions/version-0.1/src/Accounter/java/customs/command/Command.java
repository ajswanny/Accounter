/*
alexanderjswanson@icloud.com | https://github.com/ajswanny

References:
    Design Patterns - Gama et. al
*/


/* Package */
package Accounter.java.customs.command;


import Accounter.java.customs.Memento;

/**
 *
 */
public abstract class Command<T> {

    T object;
    Memento<T> memento;

    public abstract void execute();

    public abstract void un_execute();

}
