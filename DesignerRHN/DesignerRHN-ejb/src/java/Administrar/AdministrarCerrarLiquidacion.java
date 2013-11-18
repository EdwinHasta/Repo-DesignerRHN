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

@Stateful
public class AdministrarCerrarLiquidacion implements AdministrarCerrarLiquidacionInterface{

     @EJB
    PersistenciaParametrosEstadosInterface persistenciaParametrosEstados;
    @EJB
    PersistenciaCandadosInterface persistenciaCandados;
    @EJB
    PersistenciaActualUsuarioInterface persistenciaActualUsuario;
    @EJB
    PersistenciaParametrosEstructurasInterface persistenciaParametrosEstructuras;
    @EJB
    PersistenciaParametrosInterface  persistenciaParametros;
    @EJB
    PersistenciaSolucionesNodosInterface persistenciaSolucionesNodos;
    @EJB
    PersistenciaCortesProcesosInterface persistenciaCortesProcesos; 

    
     @Override
    public Integer empleadosParaLiquidar() {
        return persistenciaParametrosEstados.empleadosParaLiquidar();
    }

    @Override
    public boolean permisosLiquidar(String usuarioBD) {
        return persistenciaCandados.permisoLiquidar(usuarioBD);
    }

    public String usuarioBD() {
        return persistenciaActualUsuario.actualAliasBD();
    }

    public ParametrosEstructuras parametrosLiquidacion() {
        return persistenciaParametrosEstructuras.estructurasComprobantes(usuarioBD());
    }
    
    public List<Parametros> empleadosCerrarLiquidacion(String usuarioBD){
        return persistenciaParametros.parametrosComprobantes(usuarioBD);
    }
    
    public void cerrarLiquidacionAutomatico(){
        persistenciaCandados.cerrarLiquidacionAutomatico();
    }
    
    public void cerrarLiquidacionNoAutomatico(){
        persistenciaCandados.cerrarLiquidacionAutomatico();
    }
    
    public Integer conteoProcesoSN(BigInteger secProceso){
        return persistenciaSolucionesNodos.ContarProcesosSN(secProceso);
    }
    
    public Integer conteoLiquidacionesCerradas(BigInteger secProceso, String fechaDesde, String fechaHasta){
        return persistenciaCortesProcesos.contarLiquidacionesCerradas(secProceso, fechaDesde, fechaHasta);
    }
    
    public void abrirLiquidacion(Short codigoProceso, String fechaDesde, String fechaHasta){
        persistenciaCortesProcesos.eliminarComprobante(codigoProceso, fechaDesde, fechaHasta);
    }
}
