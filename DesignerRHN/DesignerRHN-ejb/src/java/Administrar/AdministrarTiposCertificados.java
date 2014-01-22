/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Administrar;

import InterfaceAdministrar.AdministrarTiposCertificadosInterface;
import Entidades.TiposCertificados;
import InterfacePersistencia.PersistenciaTiposCertificadosInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;

/**
 *
 * @author user
 */
@Stateful
public class AdministrarTiposCertificados implements AdministrarTiposCertificadosInterface {

    @EJB
    PersistenciaTiposCertificadosInterface persistenciaTiposCertificados;

    @Override
    public void modificarTiposCertificados(List<TiposCertificados> listaTiposCertificados) {
        for (int i = 0; i < listaTiposCertificados.size(); i++) {
            System.out.println("Administrar Modificando...");
            persistenciaTiposCertificados.editar(listaTiposCertificados.get(i));
        }
    }

    @Override
    public void borrarTiposCertificados(List<TiposCertificados> listaTiposCertificados) {
        for (int i = 0; i < listaTiposCertificados.size(); i++) {
            System.out.println("Administrar Borrando...");
            persistenciaTiposCertificados.borrar(listaTiposCertificados.get(i));
        }
    }

    @Override
    public void crearTiposCertificados(List<TiposCertificados> listaTiposCertificados) {
        for (int i = 0; i < listaTiposCertificados.size(); i++) {
            System.out.println("Administrar Creando...");
            persistenciaTiposCertificados.crear(listaTiposCertificados.get(i));
        }
    }

    @Override
    public List<TiposCertificados> consultarTiposCertificados() {
        List<TiposCertificados> listTipoCertificado;
        listTipoCertificado = persistenciaTiposCertificados.buscarTiposCertificados();
        return listTipoCertificado;
    }

    @Override
    public TiposCertificados consultarTipoCertificado(BigInteger secTipoCertificado) {
        TiposCertificados tipoCertificado;
        tipoCertificado = persistenciaTiposCertificados.buscarTipoCertificado(secTipoCertificado);
        return tipoCertificado;
    }
}
