/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Administrar;

import InterfaceAdministrar.AdministrarMotivosCambiosSueldosInterface;
import Entidades.MotivosCambiosSueldos;
import InterfacePersistencia.PersistenciaMotivosCambiosSueldosInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;

/**
 *
 * @author user
 */
@Stateful
public class AdministrarMotivosCambiosSueldos implements AdministrarMotivosCambiosSueldosInterface {

    @EJB
    PersistenciaMotivosCambiosSueldosInterface persistenciaMotivosCambiosSueldos;
    private MotivosCambiosSueldos motivoCambioSueldoSeleccionado;
    private MotivosCambiosSueldos motivoCambioSueldo;
    private List<MotivosCambiosSueldos> listMotivosCambiosCargos;
    private Long verificadorVS;

    @Override
    public void modificarMotivosCambiosSueldos(List<MotivosCambiosSueldos> listMotivosCambiosSueldosModificadas) {
        for (int i = 0; i < listMotivosCambiosSueldosModificadas.size(); i++) {
            System.out.println("Administrar Modificando...");
            motivoCambioSueldoSeleccionado = listMotivosCambiosSueldosModificadas.get(i);
            persistenciaMotivosCambiosSueldos.editar(motivoCambioSueldoSeleccionado);
        }
    }

    @Override
    public void borrarMotivosCambiosSueldos(MotivosCambiosSueldos motivosCambiosSueldos) {
        persistenciaMotivosCambiosSueldos.borrar(motivosCambiosSueldos);
    }

    @Override
    public void crearMotivosCambiosSueldos(MotivosCambiosSueldos motivosCambiosSueldos) {
        persistenciaMotivosCambiosSueldos.crear(motivosCambiosSueldos);
    }

    @Override
    public List<MotivosCambiosSueldos> mostrarMotivosCambiosSueldos() {
        listMotivosCambiosCargos = persistenciaMotivosCambiosSueldos.buscarMotivosCambiosSueldos();
        return listMotivosCambiosCargos;
    }

    @Override
    public MotivosCambiosSueldos mostrarMotivoCambioCargo(BigInteger secMotivosCambiosSueldos) {
        motivoCambioSueldo = persistenciaMotivosCambiosSueldos.buscarMotivoCambioSueldo(secMotivosCambiosSueldos);
        return motivoCambioSueldo;
    }

    @Override
    public Long verificarBorradoVS(BigInteger secuenciaMovitoCambioSueldo) {
        try {
            verificadorVS = persistenciaMotivosCambiosSueldos.verificarBorradoVigenciasSueldos(secuenciaMovitoCambioSueldo);
        } catch (Exception e) {
            System.err.println("ERROR AdministrarMotivosCambiosSueldos verificarBorradoVS ERROR :" + e);
        } finally {
            return verificadorVS;
        }
    }
}
