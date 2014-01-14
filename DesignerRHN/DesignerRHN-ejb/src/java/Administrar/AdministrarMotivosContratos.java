/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Administrar;

import InterfaceAdministrar.AdministrarMotivosContratosInterface;
import Entidades.MotivosContratos;
import InterfacePersistencia.PersistenciaMotivosContratosInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;

/**
 *
 * @author user
 */
@Stateful
public class AdministrarMotivosContratos implements AdministrarMotivosContratosInterface {

    @EJB
    PersistenciaMotivosContratosInterface persistenciaMotivosContratos;
    private MotivosContratos motivoCambioCargoSeleccionado;
    private MotivosContratos motivoCambioCargo;
    private List<MotivosContratos> listMotivosCambiosCargos;

    @Override
    public void modificarMotivosContratos(List<MotivosContratos> listMotivosCambiosCargosModificadas) {
        for (int i = 0; i < listMotivosCambiosCargosModificadas.size(); i++) {
            System.out.println("Administrar Modificando...");
            motivoCambioCargoSeleccionado = listMotivosCambiosCargosModificadas.get(i);
            persistenciaMotivosContratos.editar(motivoCambioCargoSeleccionado);
        }
    }

    @Override
    public void borrarMotivosContratos(MotivosContratos motivosCambiosCargos) {
        persistenciaMotivosContratos.borrar(motivosCambiosCargos);
    }

    @Override
    public void crearMotivosContratos(MotivosContratos motivosCambiosCargos) {
        persistenciaMotivosContratos.crear(motivosCambiosCargos);
    }

    @Override
    public List<MotivosContratos> mostrarMotivosContratos() {
        listMotivosCambiosCargos = persistenciaMotivosContratos.buscarMotivosContratos();
        return listMotivosCambiosCargos;
    }

    @Override
    public MotivosContratos mostrarMotivoContrato(BigInteger secMotivosCambiosCargos) {
        motivoCambioCargo = persistenciaMotivosContratos.buscarMotivoContrato(secMotivosCambiosCargos);
        return motivoCambioCargo;
    }

    @Override
    public BigInteger verificarBorradoVC(BigInteger secuenciaMovitoCambioCargo) {
        BigInteger verificadorVTC = null;

        try {
            return verificadorVTC = persistenciaMotivosContratos.verificarBorradoVigenciasTiposContratos(secuenciaMovitoCambioCargo);
        } catch (Exception e) {
            System.err.println("ERROR AdministrarMotivosContratos verificarBorradoVC ERROR :" + e);
            return null;
        }
    }
}
