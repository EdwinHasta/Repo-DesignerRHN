
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
import InterfaceAdministrar.AdministrarSesionesInterface;
import javax.persistence.EntityManager;


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
    /**
     * Enterprise JavaBean.<br>
     * Atributo que representa todo lo referente a la conexión del usuario que
     * está usando el aplicativo.
     */
    @EJB
    AdministrarSesionesInterface administrarSesiones;
    
    List<VigenciasReformasLaborales> vigenciasRefLab;
    VigenciasReformasLaborales vigenciaRefLab;
    Empleados empleado;
    List<ReformasLaborales> reformasLaborales;
    private EntityManager em;
    
    @Override
    public void obtenerConexion(String idSesion) {
        em = administrarSesiones.obtenerConexionSesion(idSesion);
    }
    
    @Override
    public List<VigenciasReformasLaborales> vigenciasReformasLaboralesEmpleado(BigInteger secEmpleado) {
        try {
            vigenciasRefLab = persistenciaVigenciasReformasLaborales.buscarVigenciasReformasLaboralesEmpleado(em, secEmpleado);
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
            persistenciaVigenciasReformasLaborales.editar(em, vigenciaRefLab);
        }
    }

    @Override
    public void borrarVRL(VigenciasReformasLaborales vigenciasReformasLaborales) {
        persistenciaVigenciasReformasLaborales.borrar(em, vigenciasReformasLaborales);
    }

    @Override
    public void crearVRL(VigenciasReformasLaborales vigenciasReformasLaborales) {
        persistenciaVigenciasReformasLaborales.crear(em, vigenciasReformasLaborales);
    }

    @Override
    public Empleados buscarEmpleado(BigInteger secuencia) {
        try {
            empleado = persistenciaEmpleado.buscarEmpleadoSecuencia(em, secuencia);
            return empleado;
        } catch (Exception e) {
            empleado = null;
            return empleado;
        }
    }

    @Override
    public List<ReformasLaborales> reformasLaborales() {
        try {
            reformasLaborales = persistenciaReformasLaborales.buscarReformasLaborales(em);
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
