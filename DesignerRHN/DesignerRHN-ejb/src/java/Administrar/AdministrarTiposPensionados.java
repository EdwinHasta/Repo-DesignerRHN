/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Administrar;

import Entidades.TiposPensionados;
import InterfaceAdministrar.AdministrarTiposPensionadosInterface;
import InterfacePersistencia.PersistenciaTiposPensionadosInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author user
 */
@Stateless
public class AdministrarTiposPensionados implements AdministrarTiposPensionadosInterface {

    @EJB
    PersistenciaTiposPensionadosInterface persistenciaTiposPensionados;

    @Override
    public void modificarTiposPensionados(List<TiposPensionados> listaTiposPensionados) {
        for (int i = 0; i < listaTiposPensionados.size(); i++) {
            System.out.println("Administrar Modificando...");
            persistenciaTiposPensionados.editar(listaTiposPensionados.get(i));
        }
    }

    @Override
    public void borrarTiposPensionados(List<TiposPensionados> listaTiposPensionados) {
        for (int i = 0; i < listaTiposPensionados.size(); i++) {
            System.out.println("Administrar Borrando...");
            persistenciaTiposPensionados.borrar(listaTiposPensionados.get(i));
        }
    }

    @Override
    public void crearTiposPensionados(List<TiposPensionados> listaTiposPensionados) {
        for (int i = 0; i < listaTiposPensionados.size(); i++) {
            System.out.println("Administrar Creando...");
            persistenciaTiposPensionados.crear(listaTiposPensionados.get(i));
        }
    }

    public List<TiposPensionados> consultarTiposPensionados() {
        List<TiposPensionados> listMotivosCambiosCargos;
        listMotivosCambiosCargos = persistenciaTiposPensionados.consultarTiposPensionados();
        return listMotivosCambiosCargos;
    }

    @Override
    public TiposPensionados consultarTipoPensionado(BigInteger secTiposPensionados) {
        TiposPensionados subCategoria;
        subCategoria = persistenciaTiposPensionados.consultarTipoPensionado(secTiposPensionados);
        return subCategoria;
    }

    @Override
    public BigInteger contarRetiradosTipoPensionado(BigInteger secTiposPensionados) {
        BigInteger contarRetiradosTipoPensionado = null;

        try {
            return contarRetiradosTipoPensionado = persistenciaTiposPensionados.contarPensionadosTipoPension(secTiposPensionados);
        } catch (Exception e) {
            System.err.println("ERROR AdministrarTiposPensionados contarEscalafones ERROR : " + e);
            return null;
        }
    }
}
