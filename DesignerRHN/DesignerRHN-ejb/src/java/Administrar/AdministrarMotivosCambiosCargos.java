/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Administrar;

import Entidades.MotivosCambiosCargos;
import InterfaceAdministrar.AdministrarMotivosCambiosCargosInterface;
import InterfacePersistencia.PersistenciaMotivosCambiosCargosInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;

/**
 *
 * @author Administrator
 */
@Stateful
public class AdministrarMotivosCambiosCargos implements AdministrarMotivosCambiosCargosInterface {

    @EJB
    PersistenciaMotivosCambiosCargosInterface persistenciaMotivosCambiosCargos;

    @Override
    public List<MotivosCambiosCargos> consultarMotivosCambiosCargos() {
        List<MotivosCambiosCargos> motivosCambiosCargos = null;
        try {
            motivosCambiosCargos = persistenciaMotivosCambiosCargos.buscarMotivosCambiosCargos();
        } catch (Exception e) {
            motivosCambiosCargos = null;
            System.out.println("AdministrarMotivosCambiosCargos.consultarMotivosCambiosCargos.");
            System.out.println("Excepcion.");
            System.out.println(e);
        } finally {
            return motivosCambiosCargos;
        }
    }

    @Override
    public MotivosCambiosCargos consultarMotivosCambiosCargosPorSec(BigInteger secuenciaMCC) {
        MotivosCambiosCargos mcc = null;
        try {
            mcc = persistenciaMotivosCambiosCargos.buscarMotivoCambioCargo(secuenciaMCC);
        } catch (Exception e) {
            mcc = null;
        } finally{
            return mcc;
        }
    }

    @Override
    public List<String> consultarNombresMotivosCambiosCargos() {
        List<String> nombresMotivosCambiosCargos = null;
        try {
            nombresMotivosCambiosCargos = persistenciaMotivosCambiosCargos.buscarNombresMotivosCambiosCargos();
        } catch (Exception e) {
            System.out.println("AdministrarMotivosCambiosCargos.consultarNombresMotivosCambiosCargos.");
            System.err.println("Excepcion.");
            nombresMotivosCambiosCargos = null;
        } finally {
            return nombresMotivosCambiosCargos;
        }
    }

    @Override
    public void modificarMotivosCambiosCargos(List<MotivosCambiosCargos> listMotivosCambiosCargosModificadas) {
        MotivosCambiosCargos motivoCambioCargoSeleccionado;
        System.out.println("AdmnistrarMotivosCambiosCargos.modificarMotivosCambiosCargos.");
        System.out.println("Modificando...");
        for (int i = 0; i < listMotivosCambiosCargosModificadas.size(); i++) {
            motivoCambioCargoSeleccionado = listMotivosCambiosCargosModificadas.get(i);
            persistenciaMotivosCambiosCargos.editar(motivoCambioCargoSeleccionado);
        }
    }

    @Override
    public void borrarMotivosCambiosCargos(MotivosCambiosCargos motivosCambiosCargos) {
        persistenciaMotivosCambiosCargos.borrar(motivosCambiosCargos);
    }

    @Override
    public void crearMotivosCambiosCargos(MotivosCambiosCargos motivosCambiosCargos) {
        persistenciaMotivosCambiosCargos.crear(motivosCambiosCargos);
    }

    @Override
    public BigInteger verificarBorradoVC(BigInteger secuenciaMovitoCambioCargo) {
        BigInteger verificadorVC = null;
        try {
            verificadorVC = persistenciaMotivosCambiosCargos.verificarBorradoVigenciasCargos(secuenciaMovitoCambioCargo);
        } catch (Exception e) {
            System.out.println("AdministrarMotivosCambiosCargos.verificarBorradoVC.");
            System.err.println("Excepcion.");
            System.out.println(e);
        } finally {
            return verificadorVC;
        }
    }
}
