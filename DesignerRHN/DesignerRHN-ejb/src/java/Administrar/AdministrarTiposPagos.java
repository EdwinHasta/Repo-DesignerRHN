/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Administrar;

import Entidades.Tipospagos;
import InterfaceAdministrar.AdministrarTiposPagosInterface;
import InterfacePersistencia.PersistenciaTiposPagosInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;

/**
 *
 * @author user
 */
@Stateful
public class AdministrarTiposPagos implements AdministrarTiposPagosInterface {

    @EJB
    PersistenciaTiposPagosInterface persistenciaTiposPagos;

    public void modificarTiposPagos(List<Tipospagos> listaTiposPagos) {
        for (int i = 0; i < listaTiposPagos.size(); i++) {
            System.out.println("Administrar Modificando...");
            persistenciaTiposPagos.editar(listaTiposPagos.get(i));
        }
    }

    public void borrarTiposPagos(List<Tipospagos> listaTiposPagos) {
        for (int i = 0; i < listaTiposPagos.size(); i++) {
            System.out.println("Administrar Borrando...");
            persistenciaTiposPagos.borrar(listaTiposPagos.get(i));
        }
    }

    public void crearTiposPagos(List<Tipospagos> listaTiposPagos) {
        for (int i = 0; i < listaTiposPagos.size(); i++) {
            System.out.println("Administrar Creando...");
            persistenciaTiposPagos.crear(listaTiposPagos.get(i));
        }
    }

    @Override
    public List<Tipospagos> consultarTiposPagos() {
        List<Tipospagos> listMotivosCambiosCargos;
        listMotivosCambiosCargos = persistenciaTiposPagos.consultarTiposPagos();
        return listMotivosCambiosCargos;
    }

    public Tipospagos consultarTipoPago(BigInteger secTiposPagos) {
        Tipospagos subCategoria;
        subCategoria = persistenciaTiposPagos.consultarTipoPago(secTiposPagos);
        return subCategoria;
    }

    public BigInteger contarProcesosTipoPago(BigInteger secTiposPagos) {
        BigInteger contarProcesosTipoPago = null;

        try {
            return contarProcesosTipoPago = persistenciaTiposPagos.contarProcesosTipoPago(secTiposPagos);
        } catch (Exception e) {
            System.err.println("ERROR AdministrarTiposPagos contarProcesosTipoPago ERROR : " + e);
            return null;
        }
    }
}
