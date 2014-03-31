/**
 * Documentación a cargo de AndresPineda
 */
package Administrar;

import Entidades.Cargos;
import Entidades.CentrosCostos;
import Entidades.Empleados;
import Entidades.Estructuras;
import Entidades.MotivosCambiosCargos;
import Entidades.Papeles;
import Entidades.Personas;
import InterfaceAdministrar.AdministrarVigenciasCargosBusquedaAvanzadaInterface;
import InterfacePersistencia.PersistenciaCargosInterface;
import InterfacePersistencia.PersistenciaCentrosCostosInterface;
import InterfacePersistencia.PersistenciaEmpleadoInterface;
import InterfacePersistencia.PersistenciaEstructurasInterface;
import InterfacePersistencia.PersistenciaMotivosCambiosCargosInterface;
import InterfacePersistencia.PersistenciaPapelesInterface;
import InterfacePersistencia.PersistenciaPersonasInterface;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;

/**
 * Clase Stateful. <br>
 * Clase encargada de realizar las operaciones lógicas para la pantalla
 * 'BusquedaAvanzada'.
 *
 * @author Andres Pineda.
 */
@Stateful
public class AdministrarVigenciasCargosBusquedaAvanzada implements AdministrarVigenciasCargosBusquedaAvanzadaInterface{
    //--------------------------------------------------------------------------
    //ATRIBUTOS
    //--------------------------------------------------------------------------    
    /**
     * Enterprise JavaBeans.<br>
     * Atributo que representa la comunicación con la persistencia
     * 'persistenciaEstructuras'.
     */
    @EJB
    PersistenciaEstructurasInterface persistenciaEstructuras;
    /**
     * Enterprise JavaBeans.<br>
     * Atributo que representa la comunicación con la persistencia
     * 'persistenciaMotivosCambiosCargos'.
     */
    @EJB
    PersistenciaMotivosCambiosCargosInterface persistenciaMotivosCambiosCargos;
    /**
     * Enterprise JavaBeans.<br>
     * Atributo que representa la comunicación con la persistencia
     * 'persistenciaCentrosCostos'.
     */
    @EJB
    PersistenciaCentrosCostosInterface persistenciaCentrosCostos;
    /**
     * Enterprise JavaBeans.<br>
     * Atributo que representa la comunicación con la persistencia
     * 'persistenciaPapeles'.
     */
    @EJB
    PersistenciaPapelesInterface persistenciaPapeles;
    /**
     * Enterprise JavaBeans.<br>
     * Atributo que representa la comunicación con la persistencia
     * 'persistenciaCargos'.
     */
    @EJB
    PersistenciaCargosInterface persistenciaCargos;
    /**
     * Enterprise JavaBeans.<br>
     * Atributo que representa la comunicación con la persistencia
     * 'persistenciaEmpleado'.
     */
    @EJB
    PersistenciaEmpleadoInterface persistenciaEmpleado;

    //--------------------------------------------------------------------------
    //MÉTODOS
    //--------------------------------------------------------------------------
    @Override
    public List<Estructuras> lovEstructura() {
        try {
            List<Estructuras> lista = persistenciaEstructuras.buscarEstructuras();
            return lista;
        } catch (Exception e) {
            System.out.println("Error lovEstructura Admi : " + e.toString());
            return null;
        }
    }
    
    @Override
    public List<MotivosCambiosCargos> lovMotivosCambiosCargos() {
        try {
            List<MotivosCambiosCargos> lista = persistenciaMotivosCambiosCargos.buscarMotivosCambiosCargos();
            return lista;
        } catch (Exception e) {
            System.out.println("Error lovMotivosCambiosCargos Admi : " + e.toString());
            return null;
        }
    }
    
    @Override
    public List<CentrosCostos> lovCentrosCostos() {
        try {
            List<CentrosCostos> lista = persistenciaCentrosCostos.buscarCentrosCostos();
            return lista;
        } catch (Exception e) {
            System.out.println("Error lovCentrosCostos Admi : " + e.toString());
            return null;
        }
    }
    
    @Override
    public List<Papeles> lovPapeles() {
        try {
            List<Papeles> lista = persistenciaPapeles.consultarPapeles();
            return lista;
        } catch (Exception e) {
            System.out.println("Error lovPapeles Admi : " + e.toString());
            return null;
        }
    }
    
    @Override
    public List<Cargos> lovCargos() {
        try {
            List<Cargos> lista = persistenciaCargos.consultarCargos();
            return lista;
        } catch (Exception e) {
            System.out.println("Error lovCargos Admi : " + e.toString());
            return null;
        }
    }
    
    @Override
    public List<Empleados> lovEmpleados() {
        try {
            List<Empleados> lista = persistenciaEmpleado.buscarEmpleados();
            return lista;
        } catch (Exception e) {
            System.out.println("Error lovEmpleados Admi : " + e.toString());
            return null;
        }
    }
}
