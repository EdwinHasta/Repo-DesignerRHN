/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Administrar;

import Entidades.Tiposausentismos;
import InterfaceAdministrar.AdministrarTiposAusentismosInterface;
import InterfacePersistencia.PersistenciaTiposAusentismosInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;

/**
 *
 * @author user
 */
@Stateful
public class AdministrarTiposAusentismos implements AdministrarTiposAusentismosInterface {

    @EJB
    PersistenciaTiposAusentismosInterface persistenciaTiposAusentismos;

    @Override
    public void modificarTiposAusentismos(List<Tiposausentismos> listaTiposAusentismos) {
        for (int i = 0; i < listaTiposAusentismos.size(); i++) {
            System.out.println("Administrar Modificando...");
            persistenciaTiposAusentismos.editar(listaTiposAusentismos.get(i));
        }
    }

    @Override
    public void borrarTiposAusentismos(List<Tiposausentismos> listaTiposAusentismos) {
        for (int i = 0; i < listaTiposAusentismos.size(); i++) {
            System.out.println("Administrar Borrando...");
            persistenciaTiposAusentismos.borrar(listaTiposAusentismos.get(i));
        }
    }

    @Override
    public void crearTiposAusentismos(List<Tiposausentismos> listaTiposAusentismos) {
        for (int i = 0; i < listaTiposAusentismos.size(); i++) {
            System.out.println("Administrar Creando...");
            persistenciaTiposAusentismos.crear(listaTiposAusentismos.get(i));
        }
    }

    public List<Tiposausentismos> consultarTiposAusentismos() {
        List<Tiposausentismos> listMotivosCambiosCargos;
        listMotivosCambiosCargos = persistenciaTiposAusentismos.consultarTiposAusentismos();
        return listMotivosCambiosCargos;
    }

    @Override
    public Tiposausentismos consultarTipoAusentismo(BigInteger secTiposAusentismos) {
        Tiposausentismos subCategoria;
        subCategoria = persistenciaTiposAusentismos.consultarTipoAusentismo(secTiposAusentismos);
        return subCategoria;
    }

    @Override
    public BigInteger contarClasesAusentimosTipoAusentismo(BigInteger secTiposAusentismos) {
        BigInteger contarClasesAusentimosTipoAusentismo = null;

        try {
            return contarClasesAusentimosTipoAusentismo = persistenciaTiposAusentismos.contarClasesAusentimosTipoAusentismo(secTiposAusentismos);
        } catch (Exception e) {
            System.err.println("ERROR AdministrarTiposAusentismos contarClasesAusentimosTipoAusentismo ERROR : " + e);
            return null;
        }
    }

    @Override
    public BigInteger contarSOAusentimosTipoAusentismo(BigInteger secTiposAusentismos) {
        BigInteger contarSOAusentimosTipoAusentismo = null;

        try {
            return contarSOAusentimosTipoAusentismo = persistenciaTiposAusentismos.contarSOAusentimosTipoAusentismo(secTiposAusentismos);
        } catch (Exception e) {
            System.err.println("ERROR AdministrarTiposAusentismos contarClasesAusentimosTipoAusentismo ERROR : " + e);
            return null;
        }
    }
}
