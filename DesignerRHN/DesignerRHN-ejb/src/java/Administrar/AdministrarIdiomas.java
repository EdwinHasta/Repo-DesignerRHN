/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Administrar;

import Entidades.Idiomas;
import InterfaceAdministrar.AdministrarIdiomasInterface;
import InterfacePersistencia.PersistenciaIdiomasInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;

/**
 *
 * @author user
 */
@Stateful
public class AdministrarIdiomas implements AdministrarIdiomasInterface {

    @EJB
    PersistenciaIdiomasInterface persistenciaIdiomas;

    public void modificarIdiomas(List<Idiomas> listaIdiomas) {
        for (int i = 0; i < listaIdiomas.size(); i++) {
            System.out.println("Administrar Modificando...");
            persistenciaIdiomas.editar(listaIdiomas.get(i));
        }
    }

    public void borrarIdiomas(List<Idiomas> listaIdiomas) {
        for (int i = 0; i < listaIdiomas.size(); i++) {
            System.out.println("Administrar Borrando...");
            persistenciaIdiomas.borrar(listaIdiomas.get(i));
        }
    }

    public void crearIdiomas(List<Idiomas> listaIdiomas) {
        for (int i = 0; i < listaIdiomas.size(); i++) {
            System.out.println("Administrar crear...");
            persistenciaIdiomas.crear(listaIdiomas.get(i));
        }
    }

    public List<Idiomas> mostrarIdiomas() {
        List<Idiomas> listIdiomas;
        listIdiomas = persistenciaIdiomas.buscarIdiomas();
        return listIdiomas;
    }

    public Idiomas consultarIdioma(BigInteger secIdiomas) {
        Idiomas idiomas;
        idiomas = persistenciaIdiomas.buscarIdioma(secIdiomas);
        return idiomas;
    }

    public BigInteger verificarBorradoIdiomasPersonas(BigInteger secuenciaIdiomas) {
        BigInteger verificadorIdiomasPersonas = null;

        try {
            System.err.println("Secuencia Idiomas Personas " + secuenciaIdiomas);
            verificadorIdiomasPersonas = persistenciaIdiomas.contadorIdiomasPersonas(secuenciaIdiomas);
        } catch (Exception e) {
            System.err.println("ERROR AdmnistrarIdiomas verificarBorradoIdiomasPersonas ERROR :" + e);
        } finally {
            return verificadorIdiomasPersonas;
        }
    }
}
