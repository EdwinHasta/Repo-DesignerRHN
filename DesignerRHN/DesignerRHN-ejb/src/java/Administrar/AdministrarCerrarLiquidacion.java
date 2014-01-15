/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Administrar;

import Entidades.Parametros;
import Entidades.ParametrosEstructuras;
import InterfaceAdministrar.AdministrarCerrarLiquidacionInterface;
import InterfacePersistencia.PersistenciaActualUsuarioInterface;
import InterfacePersistencia.PersistenciaCandadosInterface;
import InterfacePersistencia.PersistenciaCortesProcesosInterface;
import InterfacePersistencia.PersistenciaParametrosEstadosInterface;
import InterfacePersistencia.PersistenciaParametrosEstructurasInterface;
import InterfacePersistencia.PersistenciaParametrosInterface;
import InterfacePersistencia.PersistenciaSolucionesNodosInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;
/**
 * Clase Stateful. <br>
 * Clase encargada de realizar las operaciones lógicas para la pantalla 'CerrarLiquidacion'.
 * @author betelgeuse.
 */
@Stateful
public class AdministrarCerrarLiquidacion implements AdministrarCerrarLiquidacionInterface{
    //--------------------------------------------------------------------------
    //ATRIBUTOS
    //--------------------------------------------------------------------------    
    /**
     * Enterprise JavaBeans.<br>
     * Atributo que representa la comunicación con la persistencia 'persistenciaParametrosEstados'.
     */
    @EJB
    PersistenciaParametrosEstadosInterface persistenciaParametrosEstados;
    /**
     * Enterprise JavaBeans.<br>
     * Atributo que representa la comunicación con la persistencia 'persistenciaCandados'.
     */
    @EJB
    PersistenciaCandadosInterface persistenciaCandados;
    /**
     * Enterprise JavaBeans.<br>
     * Atributo que representa la comunicación con la persistencia 'persistenciaActualUsuario'.
     */
    @EJB
    PersistenciaActualUsuarioInterface persistenciaActualUsuario;
    /**
     * Enterprise JavaBeans.<br>
     * Atributo que representa la comunicación con la persistencia 'persistenciaParametrosEstructuras'.
     */
    @EJB
    PersistenciaParametrosEstructurasInterface persistenciaParametrosEstructuras;
    /**
     * Enterprise JavaBeans.<br>
     * Atributo que representa la comunicación con la persistencia 'persistenciaParametros'.
     */
    @EJB
    PersistenciaParametrosInterface  persistenciaParametros;
    /**
     * Enterprise JavaBeans.<br>
     * Atributo que representa la comunicación con la persistencia 'persistenciaSolucionesNodos'.
     */
    @EJB
    PersistenciaSolucionesNodosInterface persistenciaSolucionesNodos;
    /**
     * Enterprise JavaBeans.<br>
     * Atributo que representa la comunicación con la persistencia 'persistenciaCortesProcesos'.
     */
    @EJB
    PersistenciaCortesProcesosInterface persistenciaCortesProcesos; 
    //--------------------------------------------------------------------------
    //MÉTODOS
    //--------------------------------------------------------------------------
    @Override
    public Integer empleadosParaLiquidar() {
        return persistenciaParametrosEstados.empleadosParaLiquidar(usuarioBD());
    }

    @Override
    public boolean permisosLiquidar(String usuarioBD) {
        return persistenciaCandados.permisoLiquidar(usuarioBD);
    }

    @Override
    public String usuarioBD() {
        return persistenciaActualUsuario.actualAliasBD();
    }

    @Override
    public ParametrosEstructuras parametrosLiquidacion() {
        return persistenciaParametrosEstructuras.buscarParametro(usuarioBD());
    }
    
    @Override
    public List<Parametros> empleadosCerrarLiquidacion(String usuarioBD){
        return persistenciaParametros.parametrosComprobantes(usuarioBD);
    }
    
    @Override
    public void cerrarLiquidacionAutomatico(){
        persistenciaCandados.cerrarLiquidacionAutomatico();
    }
    
    @Override
    public void cerrarLiquidacionNoAutomatico(){
        persistenciaCandados.cerrarLiquidacionAutomatico();
    }
    
    @Override
    public Integer conteoProcesoSN(BigInteger secProceso){
        return persistenciaSolucionesNodos.ContarProcesosSN(secProceso);
    }
    
    @Override
    public Integer conteoLiquidacionesCerradas(BigInteger secProceso, String fechaDesde, String fechaHasta){
        return persistenciaCortesProcesos.contarLiquidacionesCerradas(secProceso, fechaDesde, fechaHasta);
    }
    
    @Override
    public void abrirLiquidacion(Short codigoProceso, String fechaDesde, String fechaHasta){
        persistenciaCortesProcesos.eliminarComprobante(codigoProceso, fechaDesde, fechaHasta);
    }
}
