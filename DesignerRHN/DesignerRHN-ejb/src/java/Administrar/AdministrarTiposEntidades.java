/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Administrar;

import Entidades.Grupostiposentidades;
import Entidades.TiposEntidades;
import InterfaceAdministrar.AdministrarTiposEntidadesInterface;
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
public class AdministrarTiposEntidades implements AdministrarTiposEntidadesInterface {

    @EJB
    PersistenciaTiposEntidadesInterface persistenciaTiposEntidades;
    @EJB
    PersistenciaGruposTiposEntidadesInterface persistenciaGruposTiposEntidades;
    @EJB
    PersistenciaVigenciasAfiliacionesInterface persistenciaVigenciasAfiliaciones;

    @Override
    public void modificarTipoEntidad(List<TiposEntidades> listTiposEntidadesModificadas) {
        for (int i = 0; i < listTiposEntidadesModificadas.size(); i++) {
            System.out.println("Administrar Modificando...");
            persistenciaTiposEntidades.editar(listTiposEntidadesModificadas.get(i));
        }
    }

    @Override
    public void borrarTipoEntidad(List<TiposEntidades> listTiposEntidadesModificadas) {
        for (int i = 0; i < listTiposEntidadesModificadas.size(); i++) {
            System.out.println("Administrar Borrando...");
            persistenciaTiposEntidades.borrar(listTiposEntidadesModificadas.get(i));
        }
    }

    @Override
    public void crearTipoEntidad(List<TiposEntidades> listTiposEntidadesModificadas) {
        for (int i = 0; i < listTiposEntidadesModificadas.size(); i++) {
            System.out.println("Administrar Creando...");
            persistenciaTiposEntidades.crear(listTiposEntidadesModificadas.get(i));
        }
    }

    @Override
    public List<TiposEntidades> consultarTiposEntidades() {
        List<TiposEntidades> listTiposEntidades;
        listTiposEntidades = persistenciaTiposEntidades.buscarTiposEntidades();
        return listTiposEntidades;
    }

    @Override
    public TiposEntidades consultarTipoEntidad(BigInteger secTipoEntidad) {
        TiposEntidades tipoEntidad;
        tipoEntidad = persistenciaTiposEntidades.buscarTiposEntidadesSecuencia(secTipoEntidad);
        return tipoEntidad;
    }

    @Override
    public List<Grupostiposentidades> consultarLOVGruposTiposEntidades() {
        List<Grupostiposentidades> listGruposTiposEntidades;
        listGruposTiposEntidades = persistenciaGruposTiposEntidades.consultarGruposTiposEntidades();
        return listGruposTiposEntidades;
    }

    @Override
    public BigInteger contarVigenciasAfiliacionesTipoEntidad(BigInteger secuenciaTipoEntidad) {
        BigInteger verificador;

        try {
            return verificador = persistenciaTiposEntidades.verificarBorrado(secuenciaTipoEntidad);
        } catch (Exception e) {
            System.err.println("ERROR AdministrarTipoEntidad verificarBorrado ERROR :" + e);

            return null;
        }
    }

    @Override
    public BigInteger contarFormulasContratosEntidadesTipoEntidad(BigInteger secuenciaTipoEntidad) {
        BigInteger verificador;

        try {
            return verificador = persistenciaTiposEntidades.verificarBorradoFCE(secuenciaTipoEntidad);
        } catch (Exception e) {
            System.err.println("ERROR AdministrarTipoEntidad verificarBorradoFCE ERROR :" + e);

            return null;
        }
    }
}
