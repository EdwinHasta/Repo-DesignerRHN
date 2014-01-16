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

    @Override
    public void modificarMotivosContratos(List<MotivosContratos> listaMotivosContratos) {
        for (int i = 0; i < listaMotivosContratos.size(); i++) {
            System.out.println("Administrar Modificando...");
            persistenciaMotivosContratos.editar(listaMotivosContratos.get(i));
        }
    }

    @Override
    public void borrarMotivosContratos(List<MotivosContratos> listaMotivosContratos) {
        for (int i = 0; i < listaMotivosContratos.size(); i++) {
            System.out.println("Administrar Borrando...");
            persistenciaMotivosContratos.borrar(listaMotivosContratos.get(i));
        }
    }

    @Override
    public void crearMotivosContratos(List<MotivosContratos> listaMotivosContratos) {
        for (int i = 0; i < listaMotivosContratos.size(); i++) {
            System.out.println("Administrar Creando...");
            persistenciaMotivosContratos.crear(listaMotivosContratos.get(i));
        }
    }

    @Override
    public List<MotivosContratos> consultarMotivosContratos() {
        List<MotivosContratos> listMotivosCambiosCargos;
        listMotivosCambiosCargos = persistenciaMotivosContratos.buscarMotivosContratos();
        return listMotivosCambiosCargos;
    }

    @Override
    public MotivosContratos consultarMotivoContrato(BigInteger secMotivosCambiosCargos) {
        MotivosContratos motivoCambioCargo;
        motivoCambioCargo = persistenciaMotivosContratos.buscarMotivoContrato(secMotivosCambiosCargos);
        return motivoCambioCargo;
    }

    @Override
    public BigInteger contarVigenciasTiposContratosMotivoContrato(BigInteger secuenciaMovitoCambioCargo) {
        BigInteger verificadorVTC = null;

        try {
            return verificadorVTC = persistenciaMotivosContratos.verificarBorradoVigenciasTiposContratos(secuenciaMovitoCambioCargo);
        } catch (Exception e) {
            System.err.println("ERROR AdministrarMotivosContratos contarVigenciasTiposContratosMotivoContrato ERROR :" + e);
            return null;
        }
    }
}
