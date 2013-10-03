package Administrar;

import Entidades.Empleados;
import InterfaceAdministrar.AdministrarComprobantesInterface;
import InterfacePersistencia.PersistenciaActualUsuarioInterface;
import InterfacePersistencia.PersistenciaEmpleadoInterface;
import InterfacePersistencia.PersistenciaSolucionesNodosInterface;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;

@Stateful
public class AdministrarComprobantes implements AdministrarComprobantesInterface{

    @EJB
    PersistenciaSolucionesNodosInterface persistenciaSolucionesNodos;
    @EJB
    PersistenciaEmpleadoInterface persistenciaEmpleado;
    @EJB
    PersistenciaActualUsuarioInterface persistenciaActualUsuario;

    @Override
    public List<Empleados> empleadosComprobantes() {
        String usuarioBD;
        usuarioBD = persistenciaActualUsuario.actualAliasBD();
        return persistenciaEmpleado.empleadosComprobantes(usuarioBD);
    }
}
