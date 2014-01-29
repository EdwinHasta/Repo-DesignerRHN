/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Administrar;

import Entidades.SubCategorias;
import InterfaceAdministrar.AdministrarSubCategoriasInterface;
import InterfacePersistencia.PersistenciaSubCategoriasInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;

/**
 *
 * @author user
 */
@Stateful
public class AdministrarSubCategorias implements AdministrarSubCategoriasInterface {

    @EJB
    PersistenciaSubCategoriasInterface persistenciaSubCategorias;

    @Override
    public void modificarSubCategorias(List<SubCategorias> listaSubCategorias) {
        for (int i = 0; i < listaSubCategorias.size(); i++) {
            System.out.println("Administrar Modificando...");
            persistenciaSubCategorias.editar(listaSubCategorias.get(i));
        }
    }

    @Override
    public void borrarSubCategorias(List<SubCategorias> listaSubCategorias) {
        for (int i = 0; i < listaSubCategorias.size(); i++) {
            System.out.println("Administrar Borrando...");
            persistenciaSubCategorias.borrar(listaSubCategorias.get(i));
        }
    }

    @Override
    public void crearSubCategorias(List<SubCategorias> listaSubCategorias) {
        for (int i = 0; i < listaSubCategorias.size(); i++) {
            System.out.println("Administrar Creando...");
            persistenciaSubCategorias.crear(listaSubCategorias.get(i));
        }
    }

    @Override
    public List<SubCategorias> consultarSubCategorias() {
        List<SubCategorias> listMotivosCambiosCargos;
        listMotivosCambiosCargos = persistenciaSubCategorias.consultarSubCategorias();
        return listMotivosCambiosCargos;
    }

    @Override
    public SubCategorias consultarSubCategoria(BigInteger secSubCategorias) {
        SubCategorias subCategoria;
        subCategoria = persistenciaSubCategorias.consultarSubCategoria(secSubCategorias);
        return subCategoria;
    }

    @Override
    public BigInteger contarEscalafones(BigInteger secSubCategorias) {
        BigInteger contarEscalafones = null;

        try {
            return contarEscalafones = persistenciaSubCategorias.contarEscalafones(secSubCategorias);
        } catch (Exception e) {
            System.err.println("ERROR AdministrarSubCategorias contarEscalafones ERROR : " + e);
            return null;
        }
    }
}
