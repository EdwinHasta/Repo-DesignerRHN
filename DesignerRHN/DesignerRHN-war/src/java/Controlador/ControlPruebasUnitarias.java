package Controlador;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class ControlPruebasUnitarias implements Serializable{

    int a = 0;

    public ControlPruebasUnitarias() {
    }

    public int progreso() {
        a++;
        System.out.println(a);
        return a;
    }
}