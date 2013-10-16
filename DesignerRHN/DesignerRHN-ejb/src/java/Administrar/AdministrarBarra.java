package Administrar;

import Entidades.ParametrosEstructuras;
import InterfaceAdministrar.AdministrarBarraInterface;
import InterfacePersistencia.PersistenciaActualUsuarioInterface;
import InterfacePersistencia.PersistenciaCandadosInterface;
import InterfacePersistencia.PersistenciaParametrosEstadosInterface;
import InterfacePersistencia.PersistenciaParametrosEstructurasInterface;
import javax.ejb.EJB;
import javax.ejb.Stateless;

@Stateless
public class AdministrarBarra implements AdministrarBarraInterface {

    @EJB
    PersistenciaParametrosEstadosInterface persistenciaParametrosEstados;
    @EJB
    PersistenciaCandadosInterface persistenciaCandados;
    @EJB
    PersistenciaActualUsuarioInterface persistenciaActualUsuario;
    @EJB
    PersistenciaParametrosEstructurasInterface persistenciaParametrosEstructuras;

    @Override
    public Integer empleadosParaLiquidar() {
        return persistenciaParametrosEstados.empleadosParaLiquidar();
    }

    @Override
    public Integer empleadosLiquidados() {
        return persistenciaParametrosEstados.empleadosLiquidados();
    }
    
    @Override
    public boolean permisosLiquidar(String usuarioBD){
        return persistenciaCandados.permisoLiquidar(usuarioBD);
    }
    

    public String usuarioBD(){
        return persistenciaActualUsuario.actualAliasBD();
    }
    
    @Override
    public void liquidarNomina(){
        persistenciaCandados.liquidar();
    }
    
    @Override
    public String estadoLiquidacion(String usuarioBD){
        return persistenciaCandados.estadoLiquidacion(usuarioBD);
    }
    
    public ParametrosEstructuras parametrosLiquidacion(){
        return persistenciaParametrosEstructuras.estructurasComprobantes(usuarioBD());
    }
    
    public void inicializarParametrosEstados(){
        persistenciaParametrosEstados.inicializarParametrosEstados();
    }
    
    public Integer progresoLiquidacion(Integer totalEmpleadoALiquidar){
        return persistenciaCandados.progresoLiquidacion(totalEmpleadoALiquidar);
    }
}
