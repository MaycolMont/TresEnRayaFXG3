/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package espol.tresenrayafxg3.models;

import espol.tresenrayafxg3.util.Publisher;

/**
 * Casilla que almacena un valor char y limita la asignación del 
 * mismo a una sola vez, además de enviar una notifación a suscriptores
 * cuando se ha realizado la asignación.
 * @author maycmont
 */
public class Box extends Publisher{
    private char value;
    
    public Box() {
        this.value = 0; // Representa una casilla vacía
    }
    
    public void setValue(char value) throws Exception{
        if (value != 0 && this.value == 0) {
            this.value = value;
            notifySuscribers(this);
        } else {
            throw new Exception("Esta casilla ya tiene un valor");
        }
    }
    
    public char getValue() {
        return value;
    }
    
    public boolean isEmpty() {
        return value == 0;
    }
    
    public boolean contains(char value) {
        return this.value == value;
    }
    
    @Override
    public String toString() {
        if (isEmpty()) return "_";
        return String.valueOf(value);
    }
}
