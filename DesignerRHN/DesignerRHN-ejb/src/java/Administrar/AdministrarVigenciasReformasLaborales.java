
package Administrar;

import Entidades.Empleados;
import Entidades.ReformasLaborales;
import Entidades.VigenciasReformasLaborales;
import InterfaceAdministrar.AdministrarVigenciasReformasLaboralesInterface;
import javax.ejb.EJB;
import javax.ejb.Stateful;
import InterfacePersistencia.*;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Remove;


/**
 *
 * @author AndresPineda
 */
@Stateful
public class AdministrarVigenciasReformasLaborales  implements AdministrarVigenciasReformasLaboralesInterface{

    @EJB
    PersistenciaEmpleadoInterface persistenciaEmpleado;
    @EJB
    PersistenciaReformasLaboralesInterface persistenciaReformasLaborales;
    @EJB
    PersistenciaVigenciasReformasLaboralesInterface persistenciaVigenciasReformasLaborales;
    
    List<VigenciasReformasLaborales> vigenciasRefLab;
    VigenciasReformasLaborales vigenciaRefLab;
    Empleados empleado;
    List<ReformasLaborales> reformasLaborales;
    
    @Override
    public List<VigenciasReformasLaborales> vigenciasReformasLaboralesEmpleado(BigInteger secEmpleado) {
        try {
            vigenciasRefLab = persistenciaVigenciasReformasLaborales.buscarVigenciasReformasLaboralesEmpleado(secEmpleado);
        } catch (Exception e) {
            System.out.println("Error en Administrar Vigencias Reformas Laborales (vigenciasReformasLaboralessEmpleado)");
            vigenciasRefLab = null;
        }
        return vigenciasRefLab;
    }

    @Override
    public void modificarVRL(List<VigenciasReformasLaborales> listVRLModificadas) {
        for (int i = 0; i < listVRLModificadas.size(); i++) {
            System.out.println("Modificando...");
            vigenciaRefLab = listVRLModificadas.get(i);
            persistenciaVigenciasReformasLaborales.editar(vigenciaRefLab);
        }
    }

    @Override
    public void borrarVRL(VigenciasReformasLaborales vigenciasReformasLaborales) {
        persistenciaVigenciasReformasLaborales.borrar(vigenciasReformasLaborales);
    }

    @Override
    public void crearVRL(VigenciasReformasLaborales vigenciasReformasLaborales) {
        persistenciaVigenciasReformasLaborales.crear(vigenciasReformasLaborales);
    }

    @Override
    public Empleados buscarEmpleado(BigInteger secuencia) {
        try {
            empleado = persistenciaEmpleado.buscarEmpleadoSecuencia(secuencia);
            return empleado;
        } catch (Exception e) {
            empleado = null;
            return empleado;
        }
    }

    @Override
    public List<ReformasLaborales> reformasLaborales() {
        try {
            reformasLaborales = persistenciaReformasLaborales.buscarReformasLaborales();
            return reformasLaborales;
        } catch (Exception e) {
            return null;
        }
    }
    
    @Remove
    @Override
    public void salir() {
        vigenciaRefLab = null;
    }

}
