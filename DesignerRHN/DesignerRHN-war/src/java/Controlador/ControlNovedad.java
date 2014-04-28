/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author user
 */
@ManagedBean
@SessionScoped
public class ControlNovedad implements Serializable{

    private String dialogo;
    /**
     * Creates a new instance of ControlNovedad
     */
    public ControlNovedad() {
    }

    public String getDialogo() {
        return dialogo;
    }

    public void setDialogo(String dialogo) {
        this.dialogo = dialogo;
    }
    
    public void recibir(String texto)
    {
    dialogo=texto;
    }
}
