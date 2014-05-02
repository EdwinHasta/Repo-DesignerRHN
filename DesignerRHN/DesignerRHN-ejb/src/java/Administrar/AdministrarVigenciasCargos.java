package Administrar;

import Entidades.Empleados;
import Entidades.VWActualesTiposTrabajadores;
import Entidades.VigenciasCargos;
import InterfaceAdministrar.AdministrarSesionesInterface;
import InterfaceAdministrar.AdministrarVigenciasCargosInterface;
import InterfacePersistencia.PersistenciaEmpleadoInterface;
import InterfacePersistencia.PersistenciaVWActualesTiposTrabajadoresInterface;
import InterfacePersistencia.PersistenciaVigenciasCargosInterface;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;

/**
 *
 * @author Hugo Sin y -Felipphe-
 */
@Stateful
public class AdministrarVigenciasCargos implements AdministrarVigenciasCargosInterface {

    @EJB
    PersistenciaVigenciasCargosInterface persistenciaVigenciasCargos;
    @EJB
    PersistenciaEmpleadoInterface persistenciaEmpleado;
    @EJB
    PersistenciaVWActualesTiposTrabajadoresInterface persistenciaVWActualesTiposTrabajadores;
    
    private List<VigenciasCargos> vigenciasCargos;
    public List<VWActualesTiposTrabajadores> tipoEmpleadoLista;
    private VigenciasCargos vc;
    private Empleados empleado;
    private SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
    /**
     * Enterprise JavaBean.<br>
     * Atributo que representa todo lo referente a la conexión del usuario que
     * está usando el aplicativo.
     */
    @EJB
    AdministrarSesionesInterface administrarSesiones;

    private EntityManager em;
    
    @Override
    public void obtenerConexion(String idSesion) {
        em = administrarSesiones.obtenerConexionSesion(idSesion);
    }
    /*
     public AdministrarVigenciasCargos() {
     persistenciaVigenciasCargos = new PersistenciaVigenciasCargos();
     }
     */

    @Override
    public List<VigenciasCargos> consultarTodo() {
        try {
            vigenciasCargos = persistenciaVigenciasCargos.buscarVigenciasCargos();
        } catch (Exception e) {
            vigenciasCargos = null;
        }
        return vigenciasCargos;
    }

    @Override
    public VigenciasCargos consultarPorSecuencia(BigInteger secuenciaVC) {
        try {
            vc = persistenciaVigenciasCargos.buscarVigenciaCargo(secuenciaVC);
        } catch (Exception e) {
            vc = null;
        }
        return vc;
    }

    @Override
    public List<VigenciasCargos> vigenciasEmpleado(BigInteger secEmpleado) {
        try {
            //System.out.println("Método AdministrarVigenciasCargos.vigenciasEmpleado.");
            //SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
            vigenciasCargos = persistenciaVigenciasCargos.buscarVigenciasCargosEmpleado(em, secEmpleado);
        } catch (Exception e) {
            vigenciasCargos = null;
        }
        return vigenciasCargos;
    }

    @Override
    public void editarVigenciaCargo(VigenciasCargos vigencia) {
        try {
            System.out.println("administrarEmplVig editarVig: editar Vigencia = " + vigencia.getSecuencia());
            persistenciaVigenciasCargos.editar(vigencia);
        } catch (Exception ex) {
            System.out.println("administrarEmplVig editarVig: FALLO EN EL EDITAR");
        }
    }

    public void modificarVC(List<VigenciasCargos> listVCModificadas) {
        for (int i = 0; i < listVCModificadas.size(); i++) {
            System.out.println("Modificando...");
            if (listVCModificadas.get(i).getEmpleadojefe().getSecuencia() == null) {
                listVCModificadas.get(i).setEmpleadojefe(null);
                vc = listVCModificadas.get(i);
                persistenciaVigenciasCargos.editar(vc);
            } else {
                vc = listVCModificadas.get(i);
                persistenciaVigenciasCargos.editar(vc);
            }

        }
    }

    public void borrarVC(VigenciasCargos vigenciasCargos) {
        try {
            persistenciaVigenciasCargos.borrar(vigenciasCargos);
        } catch (Exception e) {
            System.out.println("Error" + e);
        }
        
    }

    public void crearVC(VigenciasCargos vigenciasCargos) {
        persistenciaVigenciasCargos.crear(vigenciasCargos);
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

    public List<VWActualesTiposTrabajadores> FiltrarTipoTrabajador() {

        try {
            tipoEmpleadoLista = persistenciaVWActualesTiposTrabajadores.FiltrarTipoTrabajador("ACTIVO");
            return tipoEmpleadoLista;
        } catch (Exception e) {
            tipoEmpleadoLista = null;
            return tipoEmpleadoLista;
        }

    }

    @Remove
    public void salir() {
        vigenciasCargos = null;
    }
}
