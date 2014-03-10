/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Administrar;

import Entidades.Empresas;
import Entidades.SucursalesPila;
import InterfaceAdministrar.AdministrarSucursalesPilaInterface;
import InterfacePersistencia.PersistenciaEmpresasInterface;
import InterfacePersistencia.PersistenciaSucursalesPilaInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;

/**
 *
 * @author user
 */
@Stateful
public class AdministrarSucursalesPila implements AdministrarSucursalesPilaInterface {

    @EJB
    PersistenciaSucursalesPilaInterface persistenciaSucursalesPila;
    @EJB
    PersistenciaEmpresasInterface persistenciaEmpresas;

    //-------------------------------------------------------------------------------------
    public List<Empresas> buscarEmpresas() {
        try {
            List<Empresas> listaEmpresas = persistenciaEmpresas.consultarEmpresas();
            return listaEmpresas;
        } catch (Exception e) {
            System.out.println("AdministrarCentroCostos: Falló al buscar las empresas /n" + e.getMessage());
            return null;
        }
    }

    public void modificarSucursalesPila(List<SucursalesPila> listaSucursalesPila) {
        for (int i = 0; i < listaSucursalesPila.size(); i++) {
            System.out.println("Administrar Modificando...");
            persistenciaSucursalesPila.editar(listaSucursalesPila.get(i));
        }
    }

    public void borrarSucursalesPila(List<SucursalesPila> listaSucursalesPila) {
        for (int i = 0; i < listaSucursalesPila.size(); i++) {
            System.out.println("Administrar Borrando...");
            persistenciaSucursalesPila.borrar(listaSucursalesPila.get(i));
        }
    }

    public void crearSucursalesPila(List<SucursalesPila> listaSucursalesPila) {
        for (int i = 0; i < listaSucursalesPila.size(); i++) {
            System.out.println("Administrar Creando...");
            persistenciaSucursalesPila.crear(listaSucursalesPila.get(i));
        }
    }

    public List<SucursalesPila> consultarSucursalesPila() {
        List<SucursalesPila> listMotivosCambiosCargos;
        listMotivosCambiosCargos = persistenciaSucursalesPila.consultarSucursalesPila();
        return listMotivosCambiosCargos;
    }

    public List<SucursalesPila> consultarSucursalPila(BigInteger secSucursalesPila) {
        List<SucursalesPila> SucursalesPila;
        SucursalesPila = persistenciaSucursalesPila.consultarSucursalesPilaPorEmpresa(secSucursalesPila);
        return SucursalesPila;
    }

    public BigInteger contarNovedadesAutoLiquidacionesSucursal_Pila(BigInteger secSucursalesPila) {
        BigInteger contarNovedadesAutoLiquidacionesSucursal_Pila = null;

        try {
            return contarNovedadesAutoLiquidacionesSucursal_Pila = persistenciaSucursalesPila.contarNovedadesAutoLiquidacionesSucursal_Pila(secSucursalesPila);
        } catch (Exception e) {
            System.err.println("ERROR AdministrarSucursalesPila contarNovedadesAutoLiquidacionesSucursal_Pila ERROR : " + e);
            return null;
        }
    }

    public BigInteger contarNovedadesCorreccionesAutolSucursal_Pila(BigInteger secSucursalesPila) {
        BigInteger contarNovedadesCorreccionesAutolSucursal_Pila = null;

        try {
            return contarNovedadesCorreccionesAutolSucursal_Pila = persistenciaSucursalesPila.contarNovedadesCorreccionesAutolSucursal_Pila(secSucursalesPila);
        } catch (Exception e) {
            System.err.println("ERROR AdministrarSucursalesPila contarNovedadesCorreccionesAutolSucursal_Pila ERROR : " + e);
            return null;
        }
    }

    public BigInteger contarOdisCabecerasSucursal_Pila(BigInteger secSucursalesPila) {
        BigInteger contarOdisCabecerasSucursal_Pila = null;

        try {
            return contarOdisCabecerasSucursal_Pila = persistenciaSucursalesPila.contarOdisCabecerasSucursal_Pila(secSucursalesPila);
        } catch (Exception e) {
            System.err.println("ERROR AdministrarSucursalesPila contarOdisCabecerasSucursal_Pila ERROR : " + e);
            return null;
        }
    }

    public BigInteger contarOdiscorReaccionesCabSucursal_Pila(BigInteger secSucursalesPila) {
        BigInteger contarOdiscorReaccionesCabSucursal_Pila = null;

        try {
            return contarOdiscorReaccionesCabSucursal_Pila = persistenciaSucursalesPila.contarOdiscorReaccionesCabSucursal_Pila(secSucursalesPila);
        } catch (Exception e) {
            System.err.println("ERROR AdministrarSucursalesPila contarOdiscorReaccionesCabSucursal_Pila ERROR : " + e);
            return null;
        }
    }

    public BigInteger contarParametrosInformesSucursal_Pila(BigInteger secSucursalesPila) {
        BigInteger contarParametrosInformesSucursal_Pila = null;

        try {
            return contarParametrosInformesSucursal_Pila = persistenciaSucursalesPila.contarParametrosInformesSucursal_Pila(secSucursalesPila);
        } catch (Exception e) {
            System.err.println("ERROR AdministrarSucursalesPila contarParametrosInformesSucursal_Pila ERROR : " + e);
            return null;
        }
    }

    public BigInteger contarUbicacionesGeograficasSucursal_Pila(BigInteger secSucursalesPila) {
        BigInteger contarUbicacionesGeograficasSucursal_Pila = null;

        try {
            return contarUbicacionesGeograficasSucursal_Pila = persistenciaSucursalesPila.contarUbicacionesGeograficasSucursal_Pila(secSucursalesPila);
        } catch (Exception e) {
            System.err.println("ERROR AdministrarSucursalesPila contarUbicacionesGeograficasSucursal_Pila ERROR : " + e);
            return null;
        }
    }

}
