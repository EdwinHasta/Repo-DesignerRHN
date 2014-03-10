/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Administrar;

import Entidades.Ciudades;
import Entidades.Empresas;
import Entidades.SucursalesPila;
import Entidades.UbicacionesGeograficas;
import InterfaceAdministrar.AdministrarUbicacionesGeograficasInterface;
import InterfacePersistencia.PersistenciaCiudadesInterface;
import InterfacePersistencia.PersistenciaEmpresasInterface;
import InterfacePersistencia.PersistenciaSucursalesPilaInterface;
import InterfacePersistencia.PersistenciaUbicacionesGeograficasInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;

/**
 *
 * @author user
 */
@Stateful
public class AdministrarUbicacionesGeograficas implements AdministrarUbicacionesGeograficasInterface {

    //-------------------------------------------------------------------------
    //ATRIBUTOS
    //--------------------------------------------------------------------------    
    /**
     * Enterprise JavaBeans.<br>
     * Atributo que representa la comunicación con la persistencia
     * 'persistenciaUbicacionesGeograficas'.
     */
    @EJB
    PersistenciaUbicacionesGeograficasInterface persistenciaUbicacionesGeograficas;
    /**
     * Enterprise JavaBeans.<br>
     * Atributo que representa la comunicación con la persistencia
     * 'persistenciaCiudades'.
     */
    @EJB
    PersistenciaCiudadesInterface persistenciaCiudades;
    /**
     * Enterprise JavaBeans.<br>
     * Atributo que representa la comunicación con la persistencia
     * 'persistenciaEmpresas'.
     */
    @EJB
    PersistenciaEmpresasInterface persistenciaEmpresas;
    /**
     * Enterprise JavaBeans.<br>
     * Atributo que representa la comunicación con la persistencia
     * 'persistenciaSucursalesPila'.
     */
    @EJB
    PersistenciaSucursalesPilaInterface persistenciaSucursalesPila;

    //-------------------------------------------------------------------------------------
    @Override
    public List<Empresas> consultarEmpresas() {
        try {
            List<Empresas> listaEmpresas = persistenciaEmpresas.consultarEmpresas();
            return listaEmpresas;
        } catch (Exception e) {
            System.out.println("ADMINISTRARUBICACIONESGEOGRAFICAS: Falló al buscar las empresas /n" + e.getMessage());
            return null;
        }
    }

    public void modificarUbicacionesGeograficas(List<UbicacionesGeograficas> listaUbicacionesGeograficas) {
        try {
            for (int i = 0; i < listaUbicacionesGeograficas.size(); i++) {
                if (listaUbicacionesGeograficas.get(i).getCiudad().getSecuencia() == null) {
                    listaUbicacionesGeograficas.get(i).setCiudad(null);
                }
            }
            for (int i = 0; i < listaUbicacionesGeograficas.size(); i++) {
                if (listaUbicacionesGeograficas.get(i).getSucursalPila().getSecuencia() == null) {
                    listaUbicacionesGeograficas.get(i).setSucursalPila(null);
                }
            }

            for (int i = 0; i < listaUbicacionesGeograficas.size(); i++) {
                persistenciaUbicacionesGeograficas.editar(listaUbicacionesGeograficas.get(i));
            }
        } catch (Exception e) {
            System.err.println("AdministrarUbicacionesGeograficas: Falló al editar el CentroCosto /n" + e.getMessage());
        }
    }

    public void borrarUbicacionesGeograficas(List<UbicacionesGeograficas> listaUbicacionesGeograficas) {
        try {
            for (int i = 0; i < listaUbicacionesGeograficas.size(); i++) {
                if (listaUbicacionesGeograficas.get(i).getCiudad().getSecuencia() == null) {
                    listaUbicacionesGeograficas.get(i).setCiudad(null);
                }
            }
            for (int i = 0; i < listaUbicacionesGeograficas.size(); i++) {
                if (listaUbicacionesGeograficas.get(i).getSucursalPila().getSecuencia() == null) {
                    listaUbicacionesGeograficas.get(i).setSucursalPila(null);
                }
            }
            for (int i = 0; i < listaUbicacionesGeograficas.size(); i++) {
                System.out.println("Borando... sucursalpila : " + listaUbicacionesGeograficas.get(i).getSucursalPila().getSecuencia());
                persistenciaUbicacionesGeograficas.borrar(listaUbicacionesGeograficas.get(i));
            }
        } catch (Exception e) {
            System.err.println("ERROR ADMINISTRARUBICACIONESGEOGRAFICAS.borrarUbicacionesGeograficas ERROR=====" + e.getMessage());
        }
    }

    public void crearUbicacionesGeograficas(List<UbicacionesGeograficas> listaUbicacionesGeograficas) {
        try {
            for (int i = 0; i < listaUbicacionesGeograficas.size(); i++) {
                if (listaUbicacionesGeograficas.get(i).getCiudad().getSecuencia() == null) {
                    listaUbicacionesGeograficas.get(i).setCiudad(null);
                }
                if (listaUbicacionesGeograficas.get(i).getSucursalPila().getSecuencia() == null) {
                    listaUbicacionesGeograficas.get(i).setSucursalPila(null);
                }
                System.out.println("ADMINISTRAR CREANDO...");
                persistenciaUbicacionesGeograficas.crear(listaUbicacionesGeograficas.get(i));

            }

        } catch (Exception e) {
            System.err.println("ERROR ADMINISTRARUBICACIONESGEOGRAFICAS CREARUBICACIONESGEOGRAFICAS : " + e);
        }
    }

    public List<UbicacionesGeograficas> consultarUbicacionesGeograficasPorEmpresa(BigInteger secEmpresa) {
        try {
            System.out.println("ENTRE A ADMINISTRARUBICACIONESGEOGRAFICAS.buscarUbicacionesGeograficasPorEmpresa ");
            List<UbicacionesGeograficas> listaUbicacionesGeograficas = persistenciaUbicacionesGeograficas.consultarUbicacionesGeograficasPorEmpresa(secEmpresa);
            return listaUbicacionesGeograficas;
        } catch (Exception e) {
            System.out.println("ERROR ADMINISTRARUBICACIONESGEOGRAFICAS CONSULTARUBICACIONESGEOGRAFICASPOREMPRESA ERROR : " + e);
            return null;
        }
    }

    public List<Ciudades> lovCiudades() {
        try {
            List<Ciudades> listaCiudades = persistenciaCiudades.ciudades();
            return listaCiudades;
        } catch (Exception e) {
            System.out.println("\n ADMINISTRARUBICACIONESGEOGRAFICAS LOVCIUDADES ERROR : " + e);
            return null;
        }
    }

    public List<SucursalesPila> lovSucursalesPilaPorEmpresa(BigInteger secEmpresa) {
        try {
            List<SucursalesPila> listaSucursalesPila = persistenciaSucursalesPila.consultarSucursalesPilaPorEmpresa(secEmpresa);
            return listaSucursalesPila;
        } catch (Exception e) {
            System.out.println("\n ADMINISTRARUBICACIONESGEOGRAFICAS LOVSUCURSALESPILA ERROR : " + e);
            return null;
        }
    }

    public BigInteger contarAfiliacionesEntidadesUbicacionGeografica(BigInteger secuencia) {
        BigInteger contarAfiliacionesEntidadesUbicacionGeografica;
        try {
            contarAfiliacionesEntidadesUbicacionGeografica = persistenciaUbicacionesGeograficas.contarAfiliacionesEntidadesUbicacionGeografica(secuencia);
            return contarAfiliacionesEntidadesUbicacionGeografica;
        } catch (Exception e) {
            return null;
        }
    }

    public BigInteger contarInspeccionesUbicacionGeografica(BigInteger secuencia) {
        BigInteger contarInspeccionesUbicacionGeografica;
        try {
            contarInspeccionesUbicacionGeografica = persistenciaUbicacionesGeograficas.contarInspeccionesUbicacionGeografica(secuencia);
            return contarInspeccionesUbicacionGeografica;
        } catch (Exception e) {
            return null;
        }
    }

    public BigInteger contarParametrosInformesUbicacionGeografica(BigInteger secuencia) {
        BigInteger contarParametrosInformesUbicacionGeografica;
        try {
            contarParametrosInformesUbicacionGeografica = persistenciaUbicacionesGeograficas.contarParametrosInformesUbicacionGeografica(secuencia);
            return contarParametrosInformesUbicacionGeografica;
        } catch (Exception e) {
            return null;
        }
    }

    public BigInteger contarRevisionesUbicacionGeografica(BigInteger secuencia) {
        BigInteger contarRevisionesUbicacionGeografica;
        try {
            contarRevisionesUbicacionGeografica = persistenciaUbicacionesGeograficas.contarRevisionesUbicacionGeografica(secuencia);
            return contarRevisionesUbicacionGeografica;
        } catch (Exception e) {
            return null;
        }
    }

    public BigInteger contarVigenciasUbicacionesUbicacionGeografica(BigInteger secuencia) {
        BigInteger contarVigenciasUbicacionesGeografica;
        try {
            contarVigenciasUbicacionesGeografica = persistenciaUbicacionesGeograficas.contarVigenciasUbicacionesGeografica(secuencia);
            return contarVigenciasUbicacionesGeografica;
        } catch (Exception e) {
            return null;
        }
    }
}
