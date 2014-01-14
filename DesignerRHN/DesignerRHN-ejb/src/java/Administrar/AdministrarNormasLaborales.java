/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Administrar;

import InterfaceAdministrar.AdministrarNormasLaboralesInterface;
import Entidades.NormasLaborales;
import InterfacePersistencia.PersistenciaNormasLaboralesInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;

/**
 *
 * @author user
 */
@Stateful
public class AdministrarNormasLaborales implements AdministrarNormasLaboralesInterface {

    @EJB
    PersistenciaNormasLaboralesInterface persistenciaNormasLaborales;
    private NormasLaborales NormaLaboralSeleccionada;
    private NormasLaborales motivoCambioCargo;
    private List<NormasLaborales> listMotivosCambiosCargos;

    @Override
    public void modificarNormasLaborales(List<NormasLaborales> listNormasLaboralesModificadas) {
        for (int i = 0; i < listNormasLaboralesModificadas.size(); i++) {
            System.out.println("Administrar Modificando...");
            NormaLaboralSeleccionada = listNormasLaboralesModificadas.get(i);
            persistenciaNormasLaborales.editar(NormaLaboralSeleccionada);
        }
    }

    @Override
    public void borrarNormasLaborales(NormasLaborales normasLaborales) {
        persistenciaNormasLaborales.borrar(normasLaborales);
    }

    @Override
    public void crearNormasLaborales(NormasLaborales normasLaborales) {
        persistenciaNormasLaborales.crear(normasLaborales);
    }

    @Override
    public List<NormasLaborales> mostrarNormasLaborales() {
        listMotivosCambiosCargos = persistenciaNormasLaborales.buscarNormasLaborales();
        return listMotivosCambiosCargos;
    }

    @Override
    public NormasLaborales mostrarMotivoContrato(BigInteger secNormasLaborales) {
        motivoCambioCargo = persistenciaNormasLaborales.buscarNormaLaboral(secNormasLaborales);
        return motivoCambioCargo;
    }

    @Override
    public BigInteger verificarBorradoVNE(BigInteger secuenciaNormasLaborales) {
        BigInteger verificadorVNE = null;

        try {
            return verificadorVNE = persistenciaNormasLaborales.verificarBorradoNormasLaborales(secuenciaNormasLaborales);
        } catch (Exception e) {
            System.err.println("ERROR AdministrarNormasLaborales verificarBorradoVNE ERROR :" + e);
            return null;
        }
    }
}
