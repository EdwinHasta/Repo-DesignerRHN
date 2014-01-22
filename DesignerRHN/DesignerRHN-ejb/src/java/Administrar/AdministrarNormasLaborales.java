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
    public void modificarNormasLaborales(List<NormasLaborales> listaNormasLaborales) {
        for (int i = 0; i < listaNormasLaborales.size(); i++) {
            System.out.println("Administrar Modificando...");
            persistenciaNormasLaborales.editar(listaNormasLaborales.get(i));
        }
    }

    @Override
    public void borrarNormasLaborales(List<NormasLaborales> listaNormasLaborales) {
        for (int i = 0; i < listaNormasLaborales.size(); i++) {
            System.out.println("Administrar Borrando...");
            persistenciaNormasLaborales.borrar(listaNormasLaborales.get(i));
        }
    }

    @Override
    public void crearNormasLaborales(List<NormasLaborales> listaNormasLaborales) {
        for (int i = 0; i < listaNormasLaborales.size(); i++) {
            System.out.println("Administrar Creando...");
            persistenciaNormasLaborales.crear(listaNormasLaborales.get(i));
        }
    }

    @Override
    public List<NormasLaborales> consultarNormasLaborales() {
        List<NormasLaborales> listMotivosCambiosCargos;
        listMotivosCambiosCargos = persistenciaNormasLaborales.consultarNormasLaborales();
        return listMotivosCambiosCargos;
    }

    @Override
    public NormasLaborales consultarMotivoContrato(BigInteger secNormasLaborales) {
        NormasLaborales motivoCambioCargo;
        motivoCambioCargo = persistenciaNormasLaborales.consultarNormaLaboral(secNormasLaborales);
        return motivoCambioCargo;
    }

    @Override
    public BigInteger contarVigenciasNormasEmpleadoNormaLaboral(BigInteger secuenciaNormasLaborales) {
        BigInteger verificadorVNE = null;

        try {
            return verificadorVNE = persistenciaNormasLaborales.contarVigenciasNormasEmpleadosNormaLaboral(secuenciaNormasLaborales);
        } catch (Exception e) {
            System.err.println("ERROR AdministrarNormasLaborales verificarBorradoVNE ERROR :" + e);
            return null;
        }
    }
}
