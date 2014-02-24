/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Administrar;

import Entidades.Grupostiposentidades;
import InterfaceAdministrar.AdministrarGruposTiposEntidadesInterface;
import InterfacePersistencia.PersistenciaGruposTiposEntidadesInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;

/**
 *
 * @author user
 */
@Stateful
public class AdministrarGruposTiposEntidades implements AdministrarGruposTiposEntidadesInterface {

    @EJB
    PersistenciaGruposTiposEntidadesInterface persistenciaGruposTiposEntidades;

    @Override
    public void modificarGruposTiposEntidades(List<Grupostiposentidades> listaGruposTiposEntidades) {
        for (int i = 0; i < listaGruposTiposEntidades.size(); i++) {
            System.out.println("Administrar Modificando...");
            persistenciaGruposTiposEntidades.editar(listaGruposTiposEntidades.get(i));
        }
    }

    @Override
    public void borrarGruposTiposEntidades(List<Grupostiposentidades> listaGruposTiposEntidades) {
        for (int i = 0; i < listaGruposTiposEntidades.size(); i++) {
            System.out.println("Administrar Borrando...");
            persistenciaGruposTiposEntidades.borrar(listaGruposTiposEntidades.get(i));
        }
    }

    @Override
    public void crearGruposTiposEntidades(List<Grupostiposentidades> listaGruposTiposEntidades) {
        for (int i = 0; i < listaGruposTiposEntidades.size(); i++) {
            System.out.println("Administrar Creando...");
            persistenciaGruposTiposEntidades.crear(listaGruposTiposEntidades.get(i));
        }
    }

    @Override
    public List<Grupostiposentidades> consultarGruposTiposEntidades() {
        List<Grupostiposentidades> listMotivosCambiosCargos;
        listMotivosCambiosCargos = persistenciaGruposTiposEntidades.consultarGruposTiposEntidades();
        return listMotivosCambiosCargos;
    }

    @Override
    public Grupostiposentidades consultarGrupoTipoEntidad(BigInteger secGruposTiposEntidades) {
        Grupostiposentidades subCategoria;
        subCategoria = persistenciaGruposTiposEntidades.consultarGrupoTipoEntidad(secGruposTiposEntidades);
        return subCategoria;
    }

    @Override
    public BigInteger contarTSgruposTiposEntidadesTipoEntidad(BigInteger secGruposTiposEntidades) {
        BigInteger contarTSgruposTiposEntidadesTipoEntidad = null;

        try {
            return contarTSgruposTiposEntidadesTipoEntidad = persistenciaGruposTiposEntidades.contarTSgruposTiposEntidadesTipoEntidad(secGruposTiposEntidades);
        } catch (Exception e) {
            System.err.println("ERROR AdministrarGruposTiposEntidades contarTSgruposTiposEntidadesTipoEntidad ERROR : " + e);
            return null;
        }
    }

    @Override
    public BigInteger contarTiposEntidadesGrupoTipoEntidad(BigInteger secGruposTiposEntidades) {
        BigInteger contarTiposEntidadesGrupoTipoEntidad = null;

        try {
            return contarTiposEntidadesGrupoTipoEntidad = persistenciaGruposTiposEntidades.contarTiposEntidadesGrupoTipoEntidad(secGruposTiposEntidades);
        } catch (Exception e) {
            System.err.println("ERROR AdministrarGruposTiposEntidades contarTiposEntidadesGrupoTipoEntidad ERROR : " + e);
            return null;
        }
    }
}
