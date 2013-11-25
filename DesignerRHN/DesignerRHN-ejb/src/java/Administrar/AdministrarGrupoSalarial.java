/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Administrar;

import Entidades.GruposSalariales;
import Entidades.VigenciasGruposSalariales;
import InterfaceAdministrar.AdministrarGrupoSalarialInterface;
import InterfacePersistencia.PersistenciaGruposSalarialesInterface;
import InterfacePersistencia.PersistenciaVigenciasGruposSalarialesInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;

/**
 *
 * @author user
 */
@Stateful
public class AdministrarGrupoSalarial implements AdministrarGrupoSalarialInterface {

    @EJB
    PersistenciaGruposSalarialesInterface persistenciaGruposSalariales;
    @EJB
    PersistenciaVigenciasGruposSalarialesInterface persistenciaVigenciasGruposSalariales;

    @Override
    public List<GruposSalariales> listGruposSalariales() {
        try {
            List<GruposSalariales> gruposSalariales = persistenciaGruposSalariales.buscarGruposSalariales();
            return gruposSalariales;
        } catch (Exception e) {
            System.out.println("Error listGruposSalariales Admi : " + e.toString());
            return null;
        }
    }

    @Override
    public void crearGruposSalariales(List<GruposSalariales> listaCrear) {
        try {
            for (int i = 0; i < listaCrear.size(); i++) {
                persistenciaGruposSalariales.crear(listaCrear.get(i));
            }
        } catch (Exception e) {
            System.out.println("Error crearGruposSalariales Admi : " + e.toString());
        }
    }

    @Override
    public void editarGruposSalariales(List<GruposSalariales> listaEditar) {
        try {
            for (int i = 0; i < listaEditar.size(); i++) {
                persistenciaGruposSalariales.editar(listaEditar.get(i));
            }
        } catch (Exception e) {
            System.out.println("Error editarGruposSalariales Admi : " + e.toString());
        }
    }

    @Override
    public void borrarGruposSalariales(List<GruposSalariales> listaBorrar) {
        try {
            for (int i = 0; i < listaBorrar.size(); i++) {
                persistenciaGruposSalariales.borrar(listaBorrar.get(i));
            }
        } catch (Exception e) {
            System.out.println("Error borrarGruposSalariales Admi : " + e.toString());
        }
    }

    @Override
    public List<VigenciasGruposSalariales> lisVigenciasGruposSalarialesSecuencia(BigInteger secuencia) {
        try {
            List<VigenciasGruposSalariales> VgruposSalariales = persistenciaVigenciasGruposSalariales.buscarVigenciaGrupoSalarialSecuenciaGrupoSal(secuencia);
            return VgruposSalariales;
        } catch (Exception e) {
            System.out.println("Error lisVigenciasGruposSalarialesSecuencia Admi : " + e.toString());
            return null;
        }
    }

    @Override
    public void crearVigenciasGruposSalariales(List<VigenciasGruposSalariales> lista) {
        try {
            for(int i = 0;i<lista.size();i++){
                persistenciaVigenciasGruposSalariales.crear(lista.get(i));
            }
        } catch (Exception e) {
            System.out.println("Error crearVigenciasGruposSalariales Admi : " + e.toString());
        }
    }
    
    @Override
    public void editarVigenciasGruposSalariales(List<VigenciasGruposSalariales> lista) {
        try {
            for(int i = 0;i<lista.size();i++){
                persistenciaVigenciasGruposSalariales.editar(lista.get(i));
            }
        } catch (Exception e) {
            System.out.println("Error editarVigenciasGruposSalariales Admi : " + e.toString());
        }
    }
    
    @Override
    public void borrarVigenciasGruposSalariales(List<VigenciasGruposSalariales> lista) {
        try {
            for(int i = 0;i<lista.size();i++){
                persistenciaVigenciasGruposSalariales.borrar(lista.get(i));
            }
        } catch (Exception e) {
            System.out.println("Error borrarVigenciasGruposSalariales Admi : " + e.toString());
        }
    }
}
