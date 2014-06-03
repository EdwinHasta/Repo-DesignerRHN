/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import Entidades.GruposViaticos;
import InterfacePersistencia.PersistenciaGruposViaticosInterface;
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
public class PersistenciaGruposViaticos implements PersistenciaGruposViaticosInterface {

    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos
     */
    /*@PersistenceContext(unitName = "DesignerRHN-ejbPU")
     private EntityManager em;*/
    @Override
    public void crear(EntityManager em, GruposViaticos gruposViaticos) {
        em.clear();
        em.getTransaction().begin();
        em.persist(gruposViaticos);
        em.getTransaction().commit();
    }

    @Override
    public void editar(EntityManager em, GruposViaticos gruposViaticos) {
        em.clear();
        em.getTransaction().begin();
        em.merge(gruposViaticos);
        em.getTransaction().commit();
    }

    @Override
    public void borrar(EntityManager em, GruposViaticos gruposViaticos) {
        em.clear();
        em.getTransaction().begin();
        em.remove(em.merge(gruposViaticos));
        em.getTransaction().commit();
    }

    @Override
    public GruposViaticos buscarGrupoViatico(EntityManager em, BigInteger secuenciaGV) {
        try {
            return em.find(GruposViaticos.class, secuenciaGV);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<GruposViaticos> buscarGruposViaticos(EntityManager em) {
        Query query = em.createQuery("SELECT ec FROM GruposViaticos ec  ORDER BY ec.codigo ASC");
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        List<GruposViaticos> listGruposViaticos = query.getResultList();
        return listGruposViaticos;

    }

    @Override
    public BigInteger contadorCargos(EntityManager em, BigInteger secuencia) {
        BigInteger retorno = new BigInteger("-1");
        try {
            String sqlQuery = " SELECT COUNT(*) FROM cargos WHERE grupoviatico = ?";
            Query query = em.createNativeQuery(sqlQuery);
            query.setParameter(1, secuencia);
            retorno = new BigInteger(query.getSingleResult().toString());
            System.out.println("Contador contadorCargos persistencia " + retorno);
            return retorno;
        } catch (Exception e) {
            System.err.println("persistenciaGruposViativos Error contadorCargos. " + e);
            return retorno;
        }
    }

    @Override
    public BigInteger contadorPlantas(EntityManager em, BigInteger secuencia) {
        BigInteger retorno = new BigInteger("-1");
        try {
            String sqlQuery = " SELECT COUNT(*) FROM plantas WHERE grupoviatico = ?";
            Query query = em.createNativeQuery(sqlQuery);
            query.setParameter(1, secuencia);
            retorno = new BigInteger(query.getSingleResult().toString());
            System.out.println("persistenciaGruposViativos contardorPlantas Contador " + retorno);
            return retorno;
        } catch (Exception e) {
            System.err.println("persistenciaGruposViativos contardorPlantas Error " + e);
            return retorno;
        }
    }

    @Override
    public BigInteger contadorTablasViaticos(EntityManager em, BigInteger secuencia) {
        BigInteger retorno = new BigInteger("-1");
        try {
            String sqlQuery = " SELECT COUNT(*) FROM tablasviaticos  WHERE grupoviatico = ?";
            Query query = em.createNativeQuery(sqlQuery);
            query.setParameter(1, secuencia);
            retorno = new BigInteger(query.getSingleResult().toString());
            System.out.println("persistenciaGruposViativos  contadorTablasViaticos Contador " + retorno);
            return retorno;
        } catch (Exception e) {
            System.err.println("persistenciaGruposViativos  contadorTablasViaticos Error : " + e);
            return retorno;
        }
    }

    @Override
    public BigInteger contadorEersViaticos(EntityManager em, BigInteger secuencia) {
        BigInteger retorno = new BigInteger("-1");
        try {
            String sqlQuery = " SELECT COUNT(*) FROM eersviaticos WHERE grupoviatico = ?";
            Query query = em.createNativeQuery(sqlQuery);
            query.setParameter(1, secuencia);
            retorno = new BigInteger(query.getSingleResult().toString());
            System.out.println("persistenciaGruposViativos contadorEersviaticos Contador :" + retorno);
            return retorno;
        } catch (Exception e) {
            System.err.println("persistenciaGruposViativos contadorEersviaticos Error : " + e);
            return retorno;
        }
    }
}
