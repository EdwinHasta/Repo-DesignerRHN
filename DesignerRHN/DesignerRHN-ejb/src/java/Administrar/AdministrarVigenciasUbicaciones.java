package Administrar;

import Entidades.Empleados;
import Entidades.UbicacionesGeograficas;
import Entidades.VigenciasUbicaciones;
import InterfaceAdministrar.AdministrarVigenciasUbicacionesInterface;
import InterfacePersistencia.PersistenciaEmpleadoInterface;
import InterfacePersistencia.PersistenciaUbicacionesGeograficasInterface;
import InterfacePersistencia.PersistenciaVigenciasUbicacionesInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;

@Stateful
public class AdministrarVigenciasUbicaciones implements AdministrarVigenciasUbicacionesInterface{

    @EJB
    PersistenciaUbicacionesGeograficasInterface persistenciaUbicacionesGeograficas;
    @EJB
    PersistenciaVigenciasUbicacionesInterface persistenciaVigenciasUbicaciones;
    @EJB
    PersistenciaEmpleadoInterface persistenciaEmpleado;
    private List<VigenciasUbicaciones> vigenciasUbicaciones;
    private VigenciasUbicaciones vu;
    private Empleados empleado;
    private List<UbicacionesGeograficas> ubicacionesGeograficas;

    public List<VigenciasUbicaciones> vigenciasUbicacionesEmpleado(BigInteger secEmpleado) {
        try {
            vigenciasUbicaciones = persistenciaVigenciasUbicaciones.buscarVigenciaUbicacionesEmpleado(secEmpleado);
        } catch (Exception e) {
            System.out.println("Error en Administrar Vigencias Ubiaciones (vigenciasUbicacionesEmpleado)");
            vigenciasUbicaciones = null;
        }
        return vigenciasUbicaciones;
    }

    public void modificarVU(List<VigenciasUbicaciones> listVUModificadas) {
        for (int i = 0; i < listVUModificadas.size(); i++) {
            System.out.println("Modificando...");
            vu = listVUModificadas.get(i);
            persistenciaVigenciasUbicaciones.editar(vu);
        }
    }

    public void borrarVU(VigenciasUbicaciones vigenciasUbicaciones) {
        persistenciaVigenciasUbicaciones.borrar(vigenciasUbicaciones);
    }

    public void crearVU(VigenciasUbicaciones vigenciasUbicaciones) {
        persistenciaVigenciasUbicaciones.crear(vigenciasUbicaciones);
    }

    public Empleados buscarEmpleado(BigInteger secuencia) {
        try {
            empleado = persistenciaEmpleado.buscarEmpleadoSecuencia(secuencia);
            return empleado;
        } catch (Exception e) {
            empleado = null;
            return empleado;
        }
    }

    public List<UbicacionesGeograficas> ubicacionesGeograficas() {
        try {
            ubicacionesGeograficas = persistenciaUbicacionesGeograficas.buscarUbicacionesGeograficas();
            return ubicacionesGeograficas;
        } catch (Exception e) {
            return null;
        }
    }
}
