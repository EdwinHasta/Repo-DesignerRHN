/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Administrar;

import InterfaceAdministrar.AdministrarMotivosMvrsInterface;
import Entidades.Motivosmvrs;
import InterfacePersistencia.PersistenciaMotivosMvrsInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;

/**
 *
 * @author user
 */
@Stateful
public class AdministrarMotivosMvrs implements AdministrarMotivosMvrsInterface {

    @EJB
    PersistenciaMotivosMvrsInterface persistenciaMotivosMvrs;
    private Motivosmvrs motivoMvrsSeleccionada;
    private Motivosmvrs motivoMvrs;
    private List<Motivosmvrs> listMotivoMvrs;

    @Override
    public void modificarMotivosMvrs(List<Motivosmvrs> listNormasLaboralesModificadas) {
        for (int i = 0; i < listNormasLaboralesModificadas.size(); i++) {
            System.out.println("Administrar Modificando...");
            motivoMvrsSeleccionada = listNormasLaboralesModificadas.get(i);
            persistenciaMotivosMvrs.editar(motivoMvrsSeleccionada);
        }
    }

    @Override
    public void borrarMotivosMvrs(Motivosmvrs motivosMvrs) {
        persistenciaMotivosMvrs.borrar(motivosMvrs);
    }

    @Override
    public void crearMotivosMvrs(Motivosmvrs notivosMvrs) {
        persistenciaMotivosMvrs.crear(notivosMvrs);
    }

    @Override
    public List<Motivosmvrs> mostrarMotivosMvrs() {
        listMotivoMvrs = persistenciaMotivosMvrs.buscarMotivosMvrs();
        return listMotivoMvrs;
    }

    @Override
    public Motivosmvrs mostrarMotivosMvrs(BigInteger secMotivosMvrs) {
        motivoMvrs = persistenciaMotivosMvrs.buscarMotivosMvrs(secMotivosMvrs);
        return motivoMvrs;
    }
}
