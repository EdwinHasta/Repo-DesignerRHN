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
    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;
    
    public void crear(GruposViaticos gruposViaticos) {
        em.persist(gruposViaticos);
    }

    public void editar(GruposViaticos gruposViaticos) {
        em.merge(gruposViaticos);
    }

    public void borrar(GruposViaticos gruposViaticos) {
        em.remove(em.merge(gruposViaticos));
    }

    public GruposViaticos buscarGrupoViatico(BigInteger secuenciaGV) {
        try {
            return em.find(GruposViaticos.class, secuenciaGV);
        } catch (Exception e) {
            return null;
        }
    }

    public List<GruposViaticos> buscarGruposViaticos() {
        Query query = em.createQuery("SELECT ec FROM GruposViaticos ec  ORDER BY ec.codigo ASC");
        List<GruposViaticos> listGruposViaticos = query.getResultList();
        return listGruposViaticos;

    }

    public BigInteger contadorCargos(BigInteger secuencia) {
        BigInteger retorno = new BigInteger("-1");
        try {
            String sqlQuery = " SELECT COUNT(*) FROM cargos WHERE grupoviatico = ?";
            Query query = em.createNativeQuery(sqlQuery);
            query.setParameter(1, secuencia);
            retorno = new BigInteger (query.getSingleResult().toString());
            System.out.println("Contador contadorCargos persistencia " + retorno);
            return retorno;
        } catch (Exception e) {
            System.err.println("persistenciaGruposViativos Error contadorCargos. " + e);
            return retorno;
        }
    }

    public BigInteger contadorPlantas(BigInteger secuencia) {
        BigInteger retorno = new BigInteger("-1");
        try {
            String sqlQuery = " SELECT COUNT(*) FROM plantas WHERE grupoviatico = ?";
            Query query = em.createNativeQuery(sqlQuery);
            query.setParameter(1, secuencia);
            retorno = new BigInteger (query.getSingleResult().toString());
            System.out.println("persistenciaGruposViativos contardorPlantas Contador " + retorno);
            return retorno;
        } catch (Exception e) {
            System.err.println("persistenciaGruposViativos contardorPlantas Error " + e);
            return retorno;
        }
    }

    public BigInteger contadorTablasViaticos(BigInteger secuencia) {
        BigInteger retorno = new BigInteger("-1");
        try {
            String sqlQuery = " SELECT COUNT(*) FROM tablasviaticos  WHERE grupoviatico = ?";
            Query query = em.createNativeQuery(sqlQuery);
            query.setParameter(1, secuencia);
            retorno = new BigInteger (query.getSingleResult().toString());
            System.out.println("persistenciaGruposViativos  contadorTablasViaticos Contador " + retorno);
            return retorno;
        } catch (Exception e) {
            System.err.println("persistenciaGruposViativos  contadorTablasViaticos Error : " + e);
            return retorno;
        }
    }

    public BigInteger contadorEersviaticos(BigInteger secuencia) {
        BigInteger retorno = new BigInteger("-1");
        try {
            String sqlQuery = " SELECT COUNT(*) FROM eersviaticos WHERE grupoviatico = ?";
            Query query = em.createNativeQuery(sqlQuery);
            query.setParameter(1, secuencia);
            retorno = new BigInteger (query.getSingleResult().toString());
            System.out.println("persistenciaGruposViativos contadorEersviaticos Contador :" + retorno);
            return retorno;
        } catch (Exception e) {
            System.err.println("persistenciaGruposViativos contadorEersviaticos Error : " + e);
            return retorno;
        }
    }
}
