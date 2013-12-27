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
    private TiposCentrosCostos tipoCentroCostoSeleccionado;
    private TiposCentrosCostos tipoEntidad;
    private List<TiposCentrosCostos> listTiposEntidades;
    private List<GruposTiposCC> listGruposTiposEntidades;
    private Long verificadorCC;
    private Long verificadorVC;
    private Long verificadorRP;

    @Override
    public void modificarTipoCentrosCostos(List<TiposCentrosCostos> listTiposEntidadesModificadas) {
        for (int i = 0; i < listTiposEntidadesModificadas.size(); i++) {
            System.out.println("Administrar Modificando...");
            tipoCentroCostoSeleccionado = listTiposEntidadesModificadas.get(i);
            persistenciaTiposCentrosCostos.editar(tipoCentroCostoSeleccionado);
        }
    }

    @Override
    public void borrarTiposCentrosCostos(TiposCentrosCostos tipoCentroCosto) {
        persistenciaTiposCentrosCostos.borrar(tipoCentroCosto);
    }

    @Override
    public void crearTiposCentrosCostos(TiposCentrosCostos tiposCentrosCostos) {
        persistenciaTiposCentrosCostos.crear(tiposCentrosCostos);
    }

    @Override
    public void buscarTiposCentrosCostos(TiposCentrosCostos tiposCentrosCostos) {
        persistenciaTiposCentrosCostos.crear(tiposCentrosCostos);
    }

    @Override
    public List<TiposCentrosCostos> mostrarTiposCentrosCostos() {
        listTiposEntidades = persistenciaTiposCentrosCostos.buscarTiposCentrosCostos();
        return listTiposEntidades;
    }

    @Override
    public TiposCentrosCostos mostrarTipoEntidad(BigInteger secTipoCentrosCostos) {
        tipoEntidad = persistenciaTiposCentrosCostos.buscarTipoCentrosCostos(secTipoCentrosCostos);
        return tipoEntidad;
    }

    @Override
    public List<GruposTiposCC> mostrarGruposTiposCC() {
        listGruposTiposEntidades = persistenciaGruposTiposCC.buscarGruposTiposCC();
        return listGruposTiposEntidades;
    }
    
    
    @Override
     public Long verificarBorradoCC(BigInteger secuenciaTipoEntidad) {
        try {
            verificadorCC = persistenciaTiposCentrosCostos.verificarBorradoCentrosCostos(secuenciaTipoEntidad);
        } catch (Exception e) {
            System.err.println("ERROR AdministrarTiposCentrosCostos verificarBorradoCC ERROR :" + e);
        } finally {
            return verificadorCC;
        }
    }
    @Override
     public Long verificarBorradoVC(BigInteger secuenciaTipoEntidad) {
        try {
            verificadorVC = persistenciaTiposCentrosCostos.verificarBorradoVigenciasCuentas(secuenciaTipoEntidad);
        } catch (Exception e) {
            System.err.println("ERROR AdministrarTiposCentrosCostos verificarBorradoVC ERROR :" + e);
        } finally {
            return verificadorVC;
        }
    }
    @Override
     public Long verificarBorradoRP(BigInteger secuenciaTipoEntidad) {
        try {
            verificadorRP = persistenciaTiposCentrosCostos.verificarBorradoRiesgosProfesionales(secuenciaTipoEntidad);
        } catch (Exception e) {
            System.err.println("ERROR AdministrarTipoEntidad verificarBorrado ERROR :" + e);
        } finally {
            return verificadorRP;
        }
    }
}
