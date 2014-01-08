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
    private Idiomas idiomaSeleccionado;
    private Idiomas idiomas;
    private List<Idiomas> listIdiomas;

    public void modificarIdiomas(List<Idiomas> listTiposIndicadoresModificadas) {
        for (int i = 0; i < listTiposIndicadoresModificadas.size(); i++) {
            System.out.println("Administrar Modificando...");
            idiomaSeleccionado = listTiposIndicadoresModificadas.get(i);
            persistenciaIdiomas.editar(idiomaSeleccionado);
        }
    }

    public void borrarIdiomas(Idiomas idiomas) {
        persistenciaIdiomas.borrar(idiomas);
    }

    public void crearIdiomas(Idiomas idiomas) {
        persistenciaIdiomas.crear(idiomas);
    }

    public List<Idiomas> mostrarIdiomas() {
        listIdiomas = persistenciaIdiomas.buscarIdiomas();
        return listIdiomas;
    }

    public Idiomas mostrarIdioma(BigInteger secIdiomas) {
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
