package Administrar;

import Entidades.Ciudades;
import Entidades.Empleados;
import Entidades.MotivosContratos;
import Entidades.TiposContratos;
import Entidades.VigenciasTiposContratos;
import InterfaceAdministrar.AdministrarVigenciasTiposContratosInterface;
import InterfacePersistencia.PersistenciaCiudadesInterface;
import InterfacePersistencia.PersistenciaEmpleadoInterface;
import InterfacePersistencia.PersistenciaMotivosContratosInterface;
import InterfacePersistencia.PersistenciaTiposContratosInterface;
import InterfacePersistencia.PersistenciaVigenciasTiposContratosInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;

@Stateful
public class AdministrarVigenciasTiposContratos implements AdministrarVigenciasTiposContratosInterface {

    @EJB
    PersistenciaVigenciasTiposContratosInterface persistenciaVigenciasTiposContratos;
    @EJB
    PersistenciaEmpleadoInterface persistenciaEmpleado;
    @EJB
    PersistenciaCiudadesInterface persistenciaCiudades;
    @EJB
    PersistenciaMotivosContratosInterface persistenciaMotivosContratos;
    @EJB
    PersistenciaTiposContratosInterface persistenciaTiposContratos;
    private List<VigenciasTiposContratos> vigenciasTiposContratos;
    private VigenciasTiposContratos vtc;
    private Empleados empleado;
    private List<Ciudades> ciudades;
    private List<MotivosContratos> motivosContratos;
    private List<TiposContratos> tiposContratos;

    public List<VigenciasTiposContratos> vigenciasTiposContratosEmpleado(BigInteger secEmpleado) {
        try {
            vigenciasTiposContratos = persistenciaVigenciasTiposContratos.buscarVigenciaTipoContratoEmpleado(secEmpleado);
        } catch (Exception e) {
            System.out.println("Error :/");
            vigenciasTiposContratos = null;
        }
        return vigenciasTiposContratos;
    }

    public void modificarVTC(List<VigenciasTiposContratos> listVTCModificadas) {
        for (int i = 0; i < listVTCModificadas.size(); i++) {
            System.out.println("Modificando...");
            if (listVTCModificadas.get(i).getCiudad().getSecuencia() == null) {
                listVTCModificadas.get(i).setCiudad(null);
                vtc = listVTCModificadas.get(i);
            } else {
                vtc = listVTCModificadas.get(i);
            }
            persistenciaVigenciasTiposContratos.editar(vtc);
        }
    }

    public void borrarVTC(VigenciasTiposContratos vigenciasTipoContrato) {
        persistenciaVigenciasTiposContratos.borrar(vigenciasTipoContrato);
    }

    public void crearVTC(VigenciasTiposContratos vigenciasTipoContrato) {
        persistenciaVigenciasTiposContratos.crear(vigenciasTipoContrato);
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

    public List<Ciudades> ciudades() {
        try {
            ciudades = persistenciaCiudades.ciudades();
            return ciudades;
        } catch (Exception e) {
            return null;
        }
    }

    public List<MotivosContratos> motivosContratos() {
        try {
            motivosContratos = persistenciaMotivosContratos.motivosContratos();
            return motivosContratos;
        } catch (Exception e) {
            return null;
        }
    }

    public List<TiposContratos> tiposContratos() {
        try {
            tiposContratos = persistenciaTiposContratos.tiposContratos();
            return tiposContratos;
        } catch (Exception e) {
            return null;
        }
    }
}
