/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Administrar;

import InterfaceAdministrar.AdministrarJuzgadosInterface;
import Entidades.Ciudades;
import Entidades.Juzgados;
import Entidades.TiposCentrosCostos;
import InterfacePersistencia.PersistenciaCiudadesInterface;
import InterfacePersistencia.PersistenciaJuzgadosInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;

/**
 *
 * @author user
 */
@Stateful
public class AdministrarJuzgados implements AdministrarJuzgadosInterface {

    /**
     * CREACION EJB
     */
    @EJB
    PersistenciaJuzgadosInterface persistenciaJuzgados;
    @EJB
    PersistenciaCiudadesInterface persistenciaCiudades;
    /**
     *
     */
    private List<Juzgados> listaJuzgados;
    private Juzgados juzgados;
    private List<TiposCentrosCostos> listaTiposCentrosCostos;
    private TiposCentrosCostos tiposCentrosCostos;
    private List<Ciudades> listaCiudades;
    private Ciudades empresas;

    //************************************EMPRESAS**************************************
    /**
     * METODO QUE SE ENCARGA DE TRAER LAS EMPRESAS
     *
     * @return
     */
    public List<Ciudades> buscarCiudades() {
        try {
            return listaCiudades = persistenciaCiudades.ciudades();
        } catch (Exception e) {
            System.out.println("ADMINISTRARJUZGADOS BUSCARCIUDADES /n" + e.getMessage());
            return listaCiudades = null;
        }
    }
    //************************************CENTROS COSTROS**************************************

    public void modificarJuzgados(Juzgados juzgados) {
        try {
            persistenciaJuzgados.editar(juzgados);
        } catch (Exception e) {
            System.out.println("AdministrarCentrosCostos: Falló al editar el CentroCosto /n" + e.getMessage());
        }
    }

    public void borrarJuzgados(Juzgados juzgados) {
        // System.out.println("ENTRE A AdministrarCentroCostos.borrarCentroCostos ");
        try {
            persistenciaJuzgados.borrar(juzgados);
        } catch (Exception e) {
            System.out.println("ERROR ADNUBUSTRARJUZGADOS BORRARJUZGADOS" + e.getMessage());
        }
    }

    /**
     *
     * @param juzgados
     */
    public void crearJuzgados(Juzgados juzgados) {
        try {
            persistenciaJuzgados.crear(juzgados);
        } catch (Exception e) {
            System.out.println("ERROR ADMINISTRARJUZGADOS CREAR JUZGADO " + e.getMessage());
        }
    }

    public List<Juzgados> buscarJuzgadosPorCiudad(BigInteger secCiudad) {
        try {
            listaJuzgados = persistenciaJuzgados.buscarJuzgadosPorCiudad(secCiudad);
        } catch (Exception e) {
            System.out.println("Error en ADMINISTRARJUZGADOS BUSCARJUZGADOPORCIUDAD");
            listaJuzgados = null;
        } finally {
            return listaJuzgados;
        }
    }

    public List<Juzgados> buscarJuzgadosPorCiudadGeneral() {
        try {
            listaJuzgados = persistenciaJuzgados.buscarJuzgados();
        } catch (Exception e) {
            System.out.println("Error en ADMINISTRARJUZGADOS BUSCARJUZGADOSPORCIUDADGENERAL " + e);
            listaJuzgados = null;
        } finally {
            return listaJuzgados;
        }
    }

    public BigInteger verificarEerPrestamos(BigInteger secuenciaJuzgados) {
        BigInteger verificarBorradoEerPrestamos = null;
        try {
            System.out.println("Administrar SecuenciaBorrar " + secuenciaJuzgados);
            verificarBorradoEerPrestamos = persistenciaJuzgados.contadorEerPrestamos(secuenciaJuzgados);
        } catch (Exception e) {
            System.err.println("ERROR ADMINISTRARJUZGADOS VERIFICAREXTRASRECARGOS ERROR :" + e);
        } finally {
            return verificarBorradoEerPrestamos;
        }
    }

    public boolean isNumeric(String cadena) {
        try {
            Integer.parseInt(cadena);
            return true;
        } catch (NumberFormatException nfe) {
            return false;
        }
    }

}
