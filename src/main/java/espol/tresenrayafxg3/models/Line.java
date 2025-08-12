/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package espol.tresenrayafxg3.models;

import espol.tresenrayafxg3.util.Suscriber;
import espol.tresenrayafxg3.util.Publisher;

/**
 * Almacena casillas que pertenecen a una línea funcional del tablero
 * es decir, filas, columnas y diagonales. Encapsulando funciones para
 * verificar su estado de forma individual.
 * Implementa la interfaz Suscriber para recibir notificaciones de cambios
 * en las casillas y notificar a sus suscriptores cuando todas las casillas
 * de la línea son iguales.
 * 
 * @author maycmont
 */
public class Line extends Publisher implements Suscriber {
    private final Box[] casillas = new Box[3];
    private int size = 0;

    public Line() {
        // Inicializa las casillas como vacías
        for (int i = 0; i < casillas.length; i++) {
            casillas[i] = new Box();
        }
    }

    /**
     * Agrega una casilla a la línea y la suscribe a las notificaciones
     * de cambios en su valor.
     * 
     * @param box la casilla a agregar
     */
    public void add(Box box) {
        box.addSuscriber(this);
        casillas[size] = box;
        size++;
    }

    /**
     * Verifica si todas las casillas de esta línea tienen el mismo valor
     * y no están vacías.
     * 
     * @return true si todas las casillas son iguales, false en caso contrario
     */
    public boolean isEqual() {
        if (casillas[0].isEmpty())
            return false;
        char first = casillas[0].getValue();
        for (Box casilla : casillas) {
            if (!casilla.contains(first)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Verifica si value puede ser agregada en esta línea aportando
     * para llenarla con el mismo valor.
     * 
     * @param value el valor a verificar
     * @return true si value puede ser agregada, false en caso contrario
     */
    public boolean isAvailableFor(char value) {
        for (Box casilla : casillas) {
            if (!casilla.contains(value) && !casilla.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    public boolean isEmpty() {
        for (Box casilla : casillas) {
            if (!casilla.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    public Box getAt(int index) {
        return casillas[index];
    }

    @Override
    public String toString() {
        String line = "| ";
        for (Box box : casillas) {
            line += box.toString() + " | ";
        }
        return line;
    }

    /**
     * Notifica a los suscriptores si las casillas de la línea son iguales.
     * Se invoca cuando una de las casillas cambia su valor.
     */
    @Override
    public <T> void onNotify(T object) {
        if (isEqual()) {
            Box boxWinner = (Box) object;
            notifySuscribers(boxWinner.getValue());
        }
    }
}
