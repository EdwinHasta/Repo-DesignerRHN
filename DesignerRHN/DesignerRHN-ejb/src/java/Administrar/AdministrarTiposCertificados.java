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
    private TiposCertificados tipoCertificadoSeleccionado;
    private TiposCertificados tipoCertificado;
    private List<TiposCertificados> listTipoCertificado;

    @Override
    public void modificarTiposCertificados(List<TiposCertificados> listNormasLaboralesModificadas) {
        for (int i = 0; i < listNormasLaboralesModificadas.size(); i++) {
            System.out.println("Administrar Modificando...");
            tipoCertificadoSeleccionado = listNormasLaboralesModificadas.get(i);
            persistenciaTiposCertificados.editar(tipoCertificadoSeleccionado);
        }
    }

    @Override
    public void borrarTiposCertificados(TiposCertificados tiposCertificados) {
        persistenciaTiposCertificados.borrar(tiposCertificados);
    }

    @Override
    public void crearTiposCertificados(TiposCertificados tiposCertificados) {
        persistenciaTiposCertificados.crear(tiposCertificados);
    }

    @Override
    public List<TiposCertificados> mostrarTiposCertificados() {
        listTipoCertificado = persistenciaTiposCertificados.buscarTiposCertificados();
        return listTipoCertificado;
    }

    @Override
    public TiposCertificados mostrarTipoCertificado(BigInteger secTipoCertificado) {
        tipoCertificado = persistenciaTiposCertificados.buscarTipoCertificado(secTipoCertificado);
        return tipoCertificado;
    }
}
