
package Administrar;


import Entidades.Contratos;
import Entidades.Empleados;
import Entidades.TiposContratos;
import Entidades.VigenciasContratos;
import InterfaceAdministrar.AdministrarVigenciasContratosInterface;
import InterfacePersistencia.PersistenciaContratosInterface;
import InterfacePersistencia.PersistenciaEmpleadoInterface;
import InterfacePersistencia.PersistenciaTiposContratosInterface;
import InterfacePersistencia.PersistenciaVigenciasContratosInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Remove;
import javax.ejb.Stateful;

/**
 *
 * @author AndresPineda
 */
@Stateful

public class AdministrarVigenciasContratos implements AdministrarVigenciasContratosInterface{

    @EJB
    PersistenciaContratosInterface persistenciaContratos;
    @EJB
    PersistenciaEmpleadoInterface persistenciaEmpleado;
    @EJB
    PersistenciaTiposContratosInterface persistenciaTiposContratos;
    @EJB
    PersistenciaVigenciasContratosInterface persistenciaVigenciasContratos;
    
    List<VigenciasContratos> vigenciasContratos;
    VigenciasContratos vigenciaContrato;
    Empleados empleado;
    List<Contratos> contratos;
    List<TiposContratos> tiposContratos;

    @Override
    public List<VigenciasContratos> VigenciasContratosEmpleado(BigInteger secEmpleado) {
        try {
            vigenciasContratos = persistenciaVigenciasContratos.buscarVigenciaContratoEmpleado(secEmpleado);
        } catch (Exception e) {
            System.out.println("Error en Administrar Vigencias Contratos (VigenciasContratosEmpleado)");
            vigenciasContratos = null;
        }
        return vigenciasContratos;
    }

    @Override
    public void modificarVC(List<VigenciasContratos> listVCModificadas) {
        for (int i = 0; i < listVCModificadas.size(); i++) {
            System.out.println("Modificando...");
            vigenciaContrato = listVCModificadas.get(i);
            persistenciaVigenciasContratos.editar(vigenciaContrato);
        }
    }

    @Override
    public void borrarVC(VigenciasContratos vigenciasContratos) {
        persistenciaVigenciasContratos.borrar(vigenciasContratos);
    }

    @Override
    public void crearVC(VigenciasContratos vigenciasContratos) {
        persistenciaVigenciasContratos.crear(vigenciasContratos);
    }

    @Override
    public Empleados buscarEmpleado(BigInteger secuencia) {
        try {
            empleado = persistenciaEmpleado.buscarEmpleadoSecuencia(secuencia);
            return empleado;
        } catch (Exception e) {
            empleado = null;
            return empleado;
        }
    }

    //@Override
    public List<Contratos> contratos() {
        try {
            contratos = persistenciaContratos.buscarContratos();
            return contratos;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<TiposContratos> tiposContratos() {
        try {
            tiposContratos = persistenciaTiposContratos.tiposContratos();
            return tiposContratos;
        } catch (Exception e) {
            return null;
        }
    }

    @Remove
    @Override
    public void salir() {
        vigenciaContrato = null;
    }

}
