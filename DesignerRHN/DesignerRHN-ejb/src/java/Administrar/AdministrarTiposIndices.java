/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Administrar;

import Entidades.TiposIndices;
import InterfaceAdministrar.AdministrarTiposIndicesInterface;
import InterfacePersistencia.PersistenciaTiposIndicesInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;

/**
 *
 * @author user
 */
@Stateful
public class AdministrarTiposIndices implements AdministrarTiposIndicesInterface {

    @EJB
    PersistenciaTiposIndicesInterface persistenciaTiposIndices;

    public void modificarTiposIndices(List<TiposIndices> listaTiposIndices) {
        for (int i = 0; i < listaTiposIndices.size(); i++) {
            System.out.println("Administrar Modificando...");
            persistenciaTiposIndices.editar(listaTiposIndices.get(i));
        }
    }

    public void borrarTiposIndices(List<TiposIndices> listaTiposIndices) {
        for (int i = 0; i < listaTiposIndices.size(); i++) {
            System.out.println("Administrar Borrando...");
            persistenciaTiposIndices.borrar(listaTiposIndices.get(i));
        }
    }

    public void crearTiposIndices(List<TiposIndices> listaTiposIndices) {
        for (int i = 0; i < listaTiposIndices.size(); i++) {
            System.out.println("Administrar Creando...");
            persistenciaTiposIndices.crear(listaTiposIndices.get(i));
        }
    }

    public List<TiposIndices> consultarTiposIndices() {
        List<TiposIndices> listMotivosCambiosCargos;
        listMotivosCambiosCargos = persistenciaTiposIndices.consultarTiposIndices();
        return listMotivosCambiosCargos;
    }

    public TiposIndices consultarTipoIndice(BigInteger secTiposIndices) {
        TiposIndices subCategoria;
        subCategoria = persistenciaTiposIndices.consultarTipoIndice(secTiposIndices);
        return subCategoria;
    }

    public BigInteger contarIndicesTipoIndice(BigInteger secTiposIndices) {
        BigInteger contarIndicesTipoIndice = null;

        try {
            return contarIndicesTipoIndice = persistenciaTiposIndices.contarIndicesTipoIndice(secTiposIndices);
        } catch (Exception e) {
            System.err.println("ERROR AdministrarTiposIndices contarIndicesTipoIndice ERROR : " + e);
            return null;
        }
    }

}
