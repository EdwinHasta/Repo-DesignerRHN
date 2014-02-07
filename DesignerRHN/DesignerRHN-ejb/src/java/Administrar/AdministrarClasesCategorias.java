/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Administrar;

import InterfaceAdministrar.AdministrarClasesCategoriasInterface;
import Entidades.ClasesCategorias;
import InterfacePersistencia.PersistenciaClasesCategoriasInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;

/**
 *
 * @author user
 */
@Stateful
public class AdministrarClasesCategorias implements AdministrarClasesCategoriasInterface {

   @EJB
    PersistenciaClasesCategoriasInterface persistenciaClasesCategorias;

    @Override
    public void modificarClasesCategorias(List<ClasesCategorias> listaClasesCategorias) {
        for (int i = 0; i < listaClasesCategorias.size(); i++) {
            System.out.println("Administrar Modificando...");
            persistenciaClasesCategorias.editar(listaClasesCategorias.get(i));
        }
    }

    @Override
    public void borrarClasesCategorias(List<ClasesCategorias> listaClasesCategorias) {
        for (int i = 0; i < listaClasesCategorias.size(); i++) {
            System.out.println("Administrar Borrando...");
            persistenciaClasesCategorias.borrar(listaClasesCategorias.get(i));
        }
    }

    @Override
    public void crearClasesCategorias(List<ClasesCategorias> listaClasesCategorias) {
        for (int i = 0; i < listaClasesCategorias.size(); i++) {
            System.out.println("Administrar Creando...");
            persistenciaClasesCategorias.crear(listaClasesCategorias.get(i));
        }
    }

    public List<ClasesCategorias> consultarClasesCategorias() {
        List<ClasesCategorias> listMotivosCambiosCargos;
        listMotivosCambiosCargos = persistenciaClasesCategorias.consultarClasesCategorias();
        return listMotivosCambiosCargos;
    }

    @Override
    public ClasesCategorias consultarClaseCategoria(BigInteger secClasesCategorias) {
        ClasesCategorias subCategoria;
        subCategoria = persistenciaClasesCategorias.consultarClaseCategoria(secClasesCategorias);
        return subCategoria;
    }

    @Override
    public BigInteger contarCategoriaClaseCategoria(BigInteger secClasesCategorias) {
        BigInteger contarCategoriaClaseCategoria = null;

        try {
            return contarCategoriaClaseCategoria = persistenciaClasesCategorias.contarCategoriasClaseCategoria(secClasesCategorias);
        } catch (Exception e) {
            System.err.println("ERROR AdministrarClasesCategorias contarCategoriaClaseCategoria ERROR : " + e);
            return null;
        }
    }
}
