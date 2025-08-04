/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package espol.tresenrayafxg3.util;

/**
 *  Interfaz que define un suscriptor que recibe notificaciones
 *  cuando ocurre un evento de interés, como un cambio en el estado
 * @author elmay
 */
@FunctionalInterface
public interface Suscriber {
    <T> void onNotify(T object);
}
