/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Administrar;

import Entidades.TiposConclusiones;
import InterfaceAdministrar.AdministrarTiposConclusionesInterface;
import InterfacePersistencia.PersistenciaTiposConclusionesInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;

/**
 *
 * @author user
 */
@Stateful
public class AdministrarTiposConclusiones implements AdministrarTiposConclusionesInterface {

   @EJB
    PersistenciaTiposConclusionesInterface persistenciaTiposConclusiones;

    public void modificarTiposConclusiones(List<TiposConclusiones> listaTiposConclusiones) {
        for (int i = 0; i < listaTiposConclusiones.size(); i++) {
            System.out.println("Administrar Modificando...");
            persistenciaTiposConclusiones.editar(listaTiposConclusiones.get(i));
        }
    }

    public void borrarTiposConclusiones(List<TiposConclusiones> listaTiposConclusiones) {
        for (int i = 0; i < listaTiposConclusiones.size(); i++) {
            System.out.println("Administrar Borrando...");
            persistenciaTiposConclusiones.borrar(listaTiposConclusiones.get(i));
        }
    }

    public void crearTiposConclusiones(List<TiposConclusiones> listaTiposConclusiones) {
        for (int i = 0; i < listaTiposConclusiones.size(); i++) {
            System.out.println("Administrar Creando...");
            persistenciaTiposConclusiones.crear(listaTiposConclusiones.get(i));
        }
    }

   @Override
    public List<TiposConclusiones> consultarTiposConclusiones() {
        List<TiposConclusiones> listMotivosCambiosCargos;
        listMotivosCambiosCargos = persistenciaTiposConclusiones.consultarTiposConclusiones();
        return listMotivosCambiosCargos;
    }

    public TiposConclusiones consultarTipoConclusion(BigInteger secTiposConclusiones) {
        TiposConclusiones subCategoria;
        subCategoria = persistenciaTiposConclusiones.consultarTipoConclusion(secTiposConclusiones);
        return subCategoria;
    }

    public BigInteger contarProcesosTipoConclusion(BigInteger secTiposConclusiones) {
        BigInteger contarProcesosTipoConclusion = null;

        try {
            return contarProcesosTipoConclusion = persistenciaTiposConclusiones.contarChequeosMedicosTipoConclusion(secTiposConclusiones);
        } catch (Exception e) {
            System.err.println("ERROR AdministrarTiposConclusiones contarChequeosMedicosTipoConclusion ERROR : " + e);
            return null;
        }
    }
}
