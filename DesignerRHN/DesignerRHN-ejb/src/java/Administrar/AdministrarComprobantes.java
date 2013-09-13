package Administrar;

import Entidades.Comprobantes;
import Entidades.CortesProcesos;
import Entidades.Empleados;
import Entidades.SolucionesNodos;
import InterfaceAdministrar.AdministrarComprobantesInterface;
import InterfacePersistencia.PersistenciaComprobantesInterface;
import InterfacePersistencia.PersistenciaCortesProcesosInterface;
import InterfacePersistencia.PersistenciaEmpleadoInterface;
import InterfacePersistencia.PersistenciaSolucionesNodosInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;

@Stateful
public class AdministrarComprobantes implements AdministrarComprobantesInterface {

    @EJB
    PersistenciaComprobantesInterface persistenciaComprobantes;
    @EJB
    PersistenciaCortesProcesosInterface persistenciaCortesProcesos;
    @EJB
    PersistenciaSolucionesNodosInterface persistenciaSolucionesNodos;
    @EJB
    PersistenciaEmpleadoInterface persistenciaEmpleado;

    @Override
    public Empleados buscarEmpleado(BigInteger secuencia) {
        Empleados empleado;
        try {
            empleado = persistenciaEmpleado.buscarEmpleadoSecuencia(secuencia);
            return empleado;
        } catch (Exception e) {
            empleado = null;
            return empleado;
        }
    }

    @Override
    public List<Comprobantes> comprobantesEmpleado(BigInteger secuenciaEmpleado) {
        return persistenciaComprobantes.comprobantesEmpleado(secuenciaEmpleado);
    }

    @Override
    public List<CortesProcesos> cortesProcesosComprobante(BigInteger secuenciaComprobante) {
        return persistenciaCortesProcesos.cortesProcesosComprobante(secuenciaComprobante);
    }

    @Override
    public List<SolucionesNodos> solucionesNodosCorteProcesoEmpleado(BigInteger secuenciaCorteProceso, BigInteger secuenciaEmpleado) {
        return persistenciaSolucionesNodos.solucionNodoCorteProcesoEmpleado(secuenciaCorteProceso, secuenciaEmpleado);
    }

    @Override
    public List<SolucionesNodos> solucionesNodosCorteProcesoEmpleador(BigInteger secuenciaCorteProceso, BigInteger secuenciaEmpleado) {
        return persistenciaSolucionesNodos.solucionNodoCorteProcesoEmpleador(secuenciaCorteProceso, secuenciaEmpleado);
    }
}
