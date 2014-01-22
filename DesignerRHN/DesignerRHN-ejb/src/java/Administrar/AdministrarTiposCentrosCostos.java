/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Administrar;

import InterfaceAdministrar.AdministrarTiposCentrosCostosInterface;
import Entidades.GruposTiposCC;
import Entidades.TiposCentrosCostos;
import InterfacePersistencia.PersistenciaGruposTiposCCInterface;
import InterfacePersistencia.PersistenciaTiposCentrosCostosInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;

/**
 *
 * @author user
 */
@Stateful
public class AdministrarTiposCentrosCostos implements AdministrarTiposCentrosCostosInterface {

    @EJB
    PersistenciaTiposCentrosCostosInterface persistenciaTiposCentrosCostos;
    @EJB
    PersistenciaGruposTiposCCInterface persistenciaGruposTiposCC;

    @Override
    public void modificarTipoCentrosCostos(List<TiposCentrosCostos> listaTiposCentrosCostos) {
        for (int i = 0; i < listaTiposCentrosCostos.size(); i++) {
            System.out.println("Administrar Modificando...");
            persistenciaTiposCentrosCostos.editar(listaTiposCentrosCostos.get(i));
        }
    }

    @Override
    public void borrarTiposCentrosCostos(List<TiposCentrosCostos> listaTiposCentrosCostos) {
        for (int i = 0; i < listaTiposCentrosCostos.size(); i++) {
            System.out.println("Administrar Borrando...");
            persistenciaTiposCentrosCostos.borrar(listaTiposCentrosCostos.get(i));
        }
    }

    @Override
    public void crearTiposCentrosCostos(List<TiposCentrosCostos> listaTiposCentrosCostos) {
        for (int i = 0; i < listaTiposCentrosCostos.size(); i++) {
            System.out.println("Administrar Borrando...");
            persistenciaTiposCentrosCostos.borrar(listaTiposCentrosCostos.get(i));
        }
    }

    @Override
    public List<TiposCentrosCostos> consultarTiposCentrosCostos() {
        List<TiposCentrosCostos> listTiposEntidades;
        listTiposEntidades = persistenciaTiposCentrosCostos.buscarTiposCentrosCostos();
        return listTiposEntidades;
    }

    @Override
    public TiposCentrosCostos consultarTipoCentroCosto(BigInteger secTipoCentrosCostos) {
        TiposCentrosCostos tipoCentrosCostos;
        tipoCentrosCostos = persistenciaTiposCentrosCostos.buscarTipoCentrosCostos(secTipoCentrosCostos);
        return tipoCentrosCostos;
    }

    @Override
    public List<GruposTiposCC> consultarLOVGruposTiposCentrosCostos() {
        List<GruposTiposCC> listGruposTiposEntidades;
        listGruposTiposEntidades = persistenciaGruposTiposCC.buscarGruposTiposCC();
        return listGruposTiposEntidades;
    }

    @Override
    public BigInteger contarCentrosCostosTipoCentroCosto(BigInteger secuenciaTipoEntidad) {
        BigInteger verificadorCC;
        try {
            return verificadorCC = persistenciaTiposCentrosCostos.verificarBorradoCentrosCostos(secuenciaTipoEntidad);
        } catch (Exception e) {
            System.err.println("ERROR AdministrarTiposCentrosCostos verificarBorradoCC ERROR :" + e);
            return null;
        }
    }

    @Override
    public BigInteger contarVigenciasCuentasTipoCentroCosto(BigInteger secuenciaTipoEntidad) {
        BigInteger verificadorVC;
        try {
            return verificadorVC = persistenciaTiposCentrosCostos.verificarBorradoVigenciasCuentas(secuenciaTipoEntidad);
        } catch (Exception e) {
            System.err.println("ERROR AdministrarTiposCentrosCostos verificarBorradoVC ERROR :" + e);
            return null;
        }
    }

    @Override
    public BigInteger contarRiesgosProfesionalesTipoCentroCosto(BigInteger secuenciaTipoEntidad) {
        BigInteger verificadorRP;
        try {
            return verificadorRP = persistenciaTiposCentrosCostos.verificarBorradoRiesgosProfesionales(secuenciaTipoEntidad);
        } catch (Exception e) {
            System.err.println("ERROR AdministrarTipoEntidad verificarBorrado ERROR :" + e);
            return null;
        }
    }
}
