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

    @Override
    public void modificarMotivosMvrs(List<Motivosmvrs> listaMotivosMvrs) {
        for (int i = 0; i < listaMotivosMvrs.size(); i++) {
            System.out.println("Administrar Modificando...");
            persistenciaMotivosMvrs.editar(listaMotivosMvrs.get(i));
        }
    }

    @Override
    public void borrarMotivosMvrs(List<Motivosmvrs> listaMotivosMvrs) {
        for (int i = 0; i < listaMotivosMvrs.size(); i++) {
            System.out.println("Administrar Borrando...");
            persistenciaMotivosMvrs.borrar(listaMotivosMvrs.get(i));
        }
    }

    @Override
    public void crearMotivosMvrs(List<Motivosmvrs> listaMotivosMvrs) {
        for (int i = 0; i < listaMotivosMvrs.size(); i++) {
            System.out.println("Administrar Creando...");
            persistenciaMotivosMvrs.crear(listaMotivosMvrs.get(i));
        }
    }

    @Override
    public List<Motivosmvrs> consultarMotivosMvrs() {
        List<Motivosmvrs> listMotivoMvrs;
        listMotivoMvrs = persistenciaMotivosMvrs.buscarMotivosMvrs();
        return listMotivoMvrs;
    }

    @Override
    public Motivosmvrs consultarMotivosMvrs(BigInteger secMotivosMvrs) {
        Motivosmvrs motivoMvrs;
        motivoMvrs = persistenciaMotivosMvrs.buscarMotivosMvrs(secMotivosMvrs);
        return motivoMvrs;
    }
}
