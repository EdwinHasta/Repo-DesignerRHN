package Administrar;

import Entidades.DetallesFormulas;
import Entidades.Parametros;
import Entidades.ParametrosEstructuras;
import Entidades.SolucionesNodos;
import InterfaceAdministrar.AdministrarComprobantesInterface;
import InterfacePersistencia.PersistenciaActualUsuarioInterface;
import InterfacePersistencia.PersistenciaDetallesFormulasInterface;
import InterfacePersistencia.PersistenciaHistoriasformulasInterface;
import InterfacePersistencia.PersistenciaParametrosEstructurasInterface;
import InterfacePersistencia.PersistenciaParametrosInterface;
import InterfacePersistencia.PersistenciaSolucionesNodosInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;

@Stateful
public class AdministrarComprobantes implements AdministrarComprobantesInterface {

    @EJB
    PersistenciaSolucionesNodosInterface persistenciaSolucionesNodos;
    @EJB
    PersistenciaParametrosInterface persistenciaParametros;
    @EJB
    PersistenciaActualUsuarioInterface persistenciaActualUsuario;
    @EJB
    PersistenciaParametrosEstructurasInterface persistenciaParametrosEstructuras;
    @EJB
    PersistenciaDetallesFormulasInterface persistenciaDetallesFormulas;
    @EJB
    PersistenciaHistoriasformulasInterface persistenciaHistoriasformulas;


    public List<Parametros> parametrosComprobantes() {
        String usuarioBD;
        usuarioBD = persistenciaActualUsuario.actualAliasBD();
        return persistenciaParametros.parametrosComprobantes(usuarioBD);
    }

    @Override
    public ParametrosEstructuras parametroEstructura() {
        String usuarioBD;
        usuarioBD = persistenciaActualUsuario.actualAliasBD();
        return persistenciaParametrosEstructuras.estructurasComprobantes(usuarioBD);
    }
    
    @Override
    public List<SolucionesNodos> solucionesNodosEmpleado(BigInteger secEmpleado){
        return persistenciaSolucionesNodos.solucionNodoEmpleado(secEmpleado);
    }
    
    @Override
    public List<SolucionesNodos> solucionesNodosEmpleador(BigInteger secEmpleado){
        return persistenciaSolucionesNodos.solucionNodoEmpleador(secEmpleado);
    }
    
    @Override
    public List<DetallesFormulas> detallesFormula(BigInteger secEmpleado, String fechaDesde, String fechaHasta, BigInteger secProceso, BigInteger secHistoriaFormula){
        return persistenciaDetallesFormulas.detallesFormula(secEmpleado, fechaDesde, fechaHasta, secProceso, secHistoriaFormula);
    }
    
    public BigInteger obtenerHistoriaFormula(BigInteger secFormula, String fechaDesde){
        return persistenciaHistoriasformulas.obtenerSecuenciaHistoriaFormula(secFormula, fechaDesde);
    }
}
