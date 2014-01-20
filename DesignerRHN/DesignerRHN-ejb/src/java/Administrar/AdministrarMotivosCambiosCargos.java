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
    public MotivosCambiosCargos consultarMotivoCambioCargo(BigInteger secuenciaMCC) {
        MotivosCambiosCargos mcc = null;
        try {
            mcc = persistenciaMotivosCambiosCargos.buscarMotivoCambioCargo(secuenciaMCC);
        } catch (Exception e) {
            mcc = null;
        } finally {
            return mcc;
        }
    }

    @Override
    public void modificarMotivosCambiosCargos(List<MotivosCambiosCargos> listaMotivosCambiosCargos) {
        for (int i = 0; i < listaMotivosCambiosCargos.size(); i++) {
            System.out.println("Administrar Modificando");
            persistenciaMotivosCambiosCargos.editar(listaMotivosCambiosCargos.get(i));
        }
    }

    @Override
    public void borrarMotivosCambiosCargos(List<MotivosCambiosCargos> listaMotivosCambiosCargos) {
        for (int i = 0; i < listaMotivosCambiosCargos.size(); i++) {
            System.out.println("Administrar Borrando");
            persistenciaMotivosCambiosCargos.borrar(listaMotivosCambiosCargos.get(i));
        }
    }

    @Override
    public void crearMotivosCambiosCargos(List<MotivosCambiosCargos> listaMotivosCambiosCargos) {
        for (int i = 0; i < listaMotivosCambiosCargos.size(); i++) {
            System.out.println("Administrar Creando");
            persistenciaMotivosCambiosCargos.crear(listaMotivosCambiosCargos.get(i));
        }
    }

    @Override
    public BigInteger contarVigenciasCargosMotivoCambioCargo(BigInteger secuenciaMovitoCambioCargo) {
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
