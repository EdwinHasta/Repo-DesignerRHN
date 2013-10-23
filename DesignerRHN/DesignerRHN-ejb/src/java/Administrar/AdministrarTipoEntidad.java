/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Administrar;

import Entidades.Grupostiposentidades;
import Entidades.TiposEntidades;
import Entidades.VigenciasAfiliaciones;
import InterfaceAdministrar.AdministrarTipoEntidadInterface;
import InterfacePersistencia.PersistenciaGruposTiposEntidadesInterface;
import InterfacePersistencia.PersistenciaTiposEntidadesInterface;
import InterfacePersistencia.PersistenciaVigenciasAfiliacionesInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;

/**
 *
 * @author user
 */
@Stateful
public class AdministrarTipoEntidad implements AdministrarTipoEntidadInterface {

    @EJB
    PersistenciaTiposEntidadesInterface persistenciaTiposEntidades;
    @EJB
    PersistenciaGruposTiposEntidadesInterface persistenciaGruposTiposEntidades;
    @EJB
    PersistenciaVigenciasAfiliacionesInterface persistenciaVigenciasAfiliaciones;
    private TiposEntidades tipoEntidadSeleccionada;
    private TiposEntidades tipoEntidad;
    private List<TiposEntidades> listTiposEntidades;
    private List<Grupostiposentidades> listGruposTiposEntidades;
    private List<VigenciasAfiliaciones> listVigenciasAfiliaciones;
    private Long verificador;

    @Override
    public void modificarTipoEntidad(List<TiposEntidades> listTiposEntidadesModificadas) {
        for (int i = 0; i < listTiposEntidadesModificadas.size(); i++) {
            System.out.println("Administrar Modificando...");
            tipoEntidadSeleccionada = listTiposEntidadesModificadas.get(i);
            persistenciaTiposEntidades.editar(tipoEntidadSeleccionada);
        }
    }

    @Override
    public void borrarTipoEntidad(TiposEntidades tipoEntidad) {
        persistenciaTiposEntidades.borrar(tipoEntidad);
    }

    @Override
    public void crearTipoEntidad(TiposEntidades tiposEntidades) {
        persistenciaTiposEntidades.crear(tiposEntidades);
    }

    @Override
    public void buscarTipoEntidad(TiposEntidades tiposEntidades) {
        persistenciaTiposEntidades.crear(tiposEntidades);
    }

    @Override
    public List<TiposEntidades> mostrarTiposEntidades() {
        listTiposEntidades = persistenciaTiposEntidades.buscarTiposEntidades();
        return listTiposEntidades;
    }

    @Override
    public TiposEntidades mostrarTipoEntidad(BigInteger secTipoEntidad) {
        tipoEntidad = persistenciaTiposEntidades.buscarTiposEntidadesSecuencia(secTipoEntidad);
        return tipoEntidad;
    }

    @Override
    public List<Grupostiposentidades> mostrarGruposTiposEntidades() {
        listGruposTiposEntidades = persistenciaGruposTiposEntidades.buscarGruposTiposEntidades();
        return listGruposTiposEntidades;
    }

    @Override
    public List<VigenciasAfiliaciones> mostarVigenciasAfiliaciones() {
        listVigenciasAfiliaciones = persistenciaVigenciasAfiliaciones.buscarVigenciasAfiliaciones();
        return listVigenciasAfiliaciones;
    }

    @Override
    public Long verificarBorrado(BigInteger secuenciaTipoEntidad) {
        try {
            verificador = persistenciaTiposEntidades.verificarBorrado(secuenciaTipoEntidad);
        } catch (Exception e) {
            System.err.println("ERROR AdministrarTipoEntidad verificarBorrado ERROR :" + e);
        } finally {
            return verificador;
        }
    }

    @Override
    public Long verificarBorradoFCE(BigInteger secuenciaTipoEntidad) {
        try {
            verificador = persistenciaTiposEntidades.verificarBorradoFCE(secuenciaTipoEntidad);
        } catch (Exception e) {
            System.err.println("ERROR AdministrarTipoEntidad verificarBorradoFCE ERROR :" + e);
        } finally {
            return verificador;
        }
    }
}
