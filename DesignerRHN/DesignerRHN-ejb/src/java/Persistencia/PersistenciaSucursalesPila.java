/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import InterfacePersistencia.PersistenciaSucursalesPilaInterface;
import Entidades.SucursalesPila;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author user
 */
@Stateless
public class PersistenciaSucursalesPila implements PersistenciaSucursalesPilaInterface {

    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos.
     */
    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    public void crear(SucursalesPila sucursalesPilas) {
        try {
            em.merge(sucursalesPilas);
        } catch (Exception ex) {
            System.err.println("Error PersistenciaSucursalesPila.crear " + ex);
        }
    }

    public void editar(SucursalesPila sucursalesPilas) {
        em.merge(sucursalesPilas);
    }

    public void borrar(SucursalesPila sucursalesPilas) {
        em.remove(em.merge(sucursalesPilas));
    }

    @Override
    public List<SucursalesPila> consultarSucursalesPila() {
        try {
            Query query = em.createQuery("SELECT ta FROM SucursalesPila ta ORDER BY ta.codigo");
            List<SucursalesPila> todosSucursalesPila = query.getResultList();
            return todosSucursalesPila;
        } catch (Exception e) {
            System.err.println("Error: PersistenciaSucursalesPila consultarSucursalesPila ERROR " + e);
            return null;
        }
    }

    public List<SucursalesPila> consultarSucursalesPilaPorEmpresa(BigInteger secEmpresa) {
        try {
            Query query = em.createQuery("SELECT cce FROM SucursalesPila cce WHERE cce.empresa.secuencia = :secuenciaEmpr ORDER BY cce.codigo ASC");
            query.setParameter("secuenciaEmpr", secEmpresa);
            List<SucursalesPila> centrosCostos = query.getResultList();
            return centrosCostos;
        } catch (Exception e) {
            System.out.println("Error en Persistencia PersistenciaSucursalesPila buscarSucursalesPilaPorEmpresa  ERROR : " + e);
            return null;
        }
    }

    public BigInteger contarUbicacionesGeograficasSucursal_Pila(BigInteger secuencia) {
        BigInteger retorno = new BigInteger("-1");
        try {
            String sqlQuery = "SELECT COUNT(*)FROM ubicacionesgeograficas WHERE sucursal_pila = ?";
            Query query = em.createNativeQuery(sqlQuery);
            query.setParameter(1, secuencia);
            retorno = new BigInteger(query.getSingleResult().toString());
            System.out.println("Contador PersistenciaSucursalesPila contarUbicacionesGeograficasSucursal_Pila Retorno " + retorno);
            return retorno;
        } catch (Exception e) {
            System.err.println("Error PersistenciaSucursalesPila contarUbicacionesGeograficasSucursal_Pila ERROR : " + e);
            return retorno;
        }
    }

    public BigInteger contarParametrosInformesSucursal_Pila(BigInteger secuencia) {
        BigInteger retorno = new BigInteger("-1");
        try {
            String sqlQuery = "SELECT COUNT(*)FROM parametrosinformes WHERE sucursal_pila = ?";
            Query query = em.createNativeQuery(sqlQuery);
            query.setParameter(1, secuencia);
            retorno = new BigInteger(query.getSingleResult().toString());
            System.out.println("Contador PersistenciaSucursalesPila contarParametrosInformesSucursal_Pila Retorno " + retorno);
            return retorno;
        } catch (Exception e) {
            System.err.println("Error PersistenciaSucursalesPila contarParametrosInformesSucursal_Pila ERROR : " + e);
            return retorno;
        }
    }

    public BigInteger contarOdiscorReaccionesCabSucursal_Pila(BigInteger secuencia) {
        BigInteger retorno = new BigInteger("-1");
        try {
            String sqlQuery = "SELECT COUNT(*)FROM odiscorReccionescab WHERE sucursal_pila = ?";
            Query query = em.createNativeQuery(sqlQuery);
            query.setParameter(1, secuencia);
            retorno = new BigInteger(query.getSingleResult().toString());
            System.out.println("Contador PersistenciaSucursalesPila contarOdiscorReaccionesCabSucursal_Pila Retorno " + retorno);
            return retorno;
        } catch (Exception e) {
            System.err.println("Error PersistenciaSucursalesPila contarOdiscorReaccionesCabSucursal_Pila ERROR : " + e);
            return retorno;
        }
    }

    public BigInteger contarOdisCabecerasSucursal_Pila(BigInteger secuencia) {
        BigInteger retorno = new BigInteger("-1");
        try {
            String sqlQuery = "SELECT COUNT(*)FROM odiscabeceras WHERE sucursal_pila = ?";
            Query query = em.createNativeQuery(sqlQuery);
            query.setParameter(1, secuencia);
            retorno = new BigInteger(query.getSingleResult().toString());
            System.out.println("Contador PersistenciaSucursalesPila contarOdisCabecerasSucursal_Pila Retorno " + retorno);
            return retorno;
        } catch (Exception e) {
            System.err.println("Error PersistenciaSucursalesPila contarOdisCabecerasSucursal_Pila ERROR : " + e);
            return retorno;
        }
    }

    public BigInteger contarNovedadesCorreccionesAutolSucursal_Pila(BigInteger secuencia) {
        BigInteger retorno = new BigInteger("-1");
        try {
            String sqlQuery = "SELECT COUNT(*)FROM novedadescorreccionesautol WHERE sucursal_pila = ?";
            Query query = em.createNativeQuery(sqlQuery);
            query.setParameter(1, secuencia);
            retorno = new BigInteger(query.getSingleResult().toString());
            System.out.println("Contador PersistenciaSucursalesPila contarNovedadesCorreccionesAutolSucursal_Pila Retorno " + retorno);
            return retorno;
        } catch (Exception e) {
            System.err.println("Error PersistenciaSucursalesPila contarNovedadesCorreccionesAutolSucursal_Pila ERROR : " + e);
            return retorno;
        }
    }

    public BigInteger contarNovedadesAutoLiquidacionesSucursal_Pila(BigInteger secuencia) {
        BigInteger retorno = new BigInteger("-1");
        try {
            String sqlQuery = "SELECT COUNT(*)FROM novedadesautoliquidaciones WHERE sucursal_pila = ?";
            Query query = em.createNativeQuery(sqlQuery);
            query.setParameter(1, secuencia);
            retorno = new BigInteger(query.getSingleResult().toString());
            System.out.println("Contador PersistenciaSucursalesPila contarNovedadesCorreccionesAutolSucursal_Pila Retorno " + retorno);
            return retorno;
        } catch (Exception e) {
            System.err.println("Error PersistenciaSucursalesPila contarNovedadesCorreccionesAutolSucursal_Pila ERROR : " + e);
            return retorno;
        }
    }
}
