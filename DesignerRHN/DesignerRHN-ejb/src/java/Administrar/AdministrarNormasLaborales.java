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

    @Override
    public void modificarNormasLaborales(List<NormasLaborales> listNormasLaborales) {
        for (int i = 0; i < listNormasLaborales.size(); i++) {
            System.out.println("Administrar Modificando...");
            persistenciaNormasLaborales.editar(listNormasLaborales.get(i));
        }
    }

    @Override
    public void borrarNormasLaborales(List<NormasLaborales> listNormasLaborales) {
        for (int i = 0; i < listNormasLaborales.size(); i++) {
            System.out.println("Administrar Borrando...");
            persistenciaNormasLaborales.borrar(listNormasLaborales.get(i));
        }
    }

    @Override
    public void crearNormasLaborales(List<NormasLaborales> listNormasLaborales) {
        for (int i = 0; i < listNormasLaborales.size(); i++) {
            System.out.println("Administrar Creando...");
            persistenciaNormasLaborales.crear(listNormasLaborales.get(i));
        }
    }

    @Override
    public List<NormasLaborales> mostrarNormasLaborales() {
        List<NormasLaborales> listMotivosCambiosCargos;
        listMotivosCambiosCargos = persistenciaNormasLaborales.buscarNormasLaborales();
        return listMotivosCambiosCargos;
    }

    @Override
    public NormasLaborales mostrarMotivoContrato(BigInteger secNormasLaborales) {
        NormasLaborales motivoCambioCargo;
        motivoCambioCargo = persistenciaNormasLaborales.buscarNormaLaboral(secNormasLaborales);
        return motivoCambioCargo;
    }

    @Override
    public BigInteger verificarVigenciasNormasEmpleadoNormaLaboral(BigInteger secuenciaNormasLaborales) {
        BigInteger verificadorVNE = null;

        try {
            return verificadorVNE = persistenciaNormasLaborales.verificarBorradoNormasLaborales(secuenciaNormasLaborales);
        } catch (Exception e) {
            System.err.println("ERROR AdministrarNormasLaborales verificarBorradoVNE ERROR :" + e);
            return null;
        }
    }
}
