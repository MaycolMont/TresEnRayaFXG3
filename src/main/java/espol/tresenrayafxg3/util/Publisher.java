/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package espol.tresenrayafxg3.util;
import java.util.List;
import java.util.ArrayList;

/**
 * * Clase abstracta que representa un publicador en el patrón de diseño
 * Observer. Permite a los suscriptores registrarse para recibir notificaciones
 * cuando ocurra un evento relevante. Los suscriptores pueden ser añadidos o
 * eliminados dinámicamente.
 * @author maycmont
 */
public abstract class Publisher {
    private List<Suscriber> suscribers = new ArrayList<>();
    
    public void addSuscriber(Suscriber suscriber){
        suscribers.add(suscriber);
    }
    
    public void removeSuscriber(Suscriber suscriber) {
        if (!suscribers.contains(suscriber)) return;
        suscribers.remove(suscriber);
    }
    
    public <T> void notifySuscribers(T object) {
        for (Suscriber suscriber : suscribers) {
            suscriber.onNotify(object);
        }
    }
}
