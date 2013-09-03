package ClasesAyuda;

import Entidades.Empleados;
import Entidades.HVHojasDeVida;
import java.io.Serializable;

public class EmpleadoIndividualExportar implements Serializable {

    private Empleados empleado;
    private HVHojasDeVida hojaDeVida;

    public EmpleadoIndividualExportar(Empleados empleado, HVHojasDeVida hojaDeVida) {
        this.empleado = empleado;
        this.hojaDeVida = hojaDeVida;
    }

    public Empleados getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleados empleado) {
        this.empleado = empleado;
    }

    public HVHojasDeVida getHojaDeVida() {
        return hojaDeVida;
    }

    public void setHojaDeVida(HVHojasDeVida hojaDeVida) {
        this.hojaDeVida = hojaDeVida;
    }
}
