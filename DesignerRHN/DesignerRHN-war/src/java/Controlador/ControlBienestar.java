/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import java.io.Serializable;
import java.math.BigInteger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author user
 */
@ManagedBean
@SessionScoped
public class ControlBienestar implements Serializable{

    private BigInteger secuenciaEmpleado;
    private String bienestar;

    /**
     * Creates a new instance of ControlBienestar
     */
    public ControlBienestar() {
    }

    public void recibirEmpleado(BigInteger secEmp, String bien) {
        secuenciaEmpleado = secEmp;
        bienestar = bien;
    }

    public String getBienestar() {
        bienestar = "Bienestar";
        return bienestar.toUpperCase();
    }
    
    
}
