/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import Entidades.TiposIndices;
import InterfacePersistencia.PersistenciaTiposIndicesInterface;
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
public class PersistenciaTiposIndices implements PersistenciaTiposIndicesInterface {

    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos.
     */
/*    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;*/

    public void crear(EntityManager em, TiposIndices tiposIndices) {
        try {
            em.persist(tiposIndices);
        } catch (Exception e) {
            System.out.println("Error crear PersistenciaTiposIndices : " + e);
        }
    }

    public void editar(EntityManager em, TiposIndices tiposIndices) {
        try {
            em.merge(tiposIndices);
        } catch (Exception e) {
            System.out.println("Error editar PersistenciaTiposIndices : " + e);
        }
    }

    public void borrar(EntityManager em, TiposIndices tiposIndices) {
        try {
            em.remove(em.merge(tiposIndices));
        } catch (Exception e) {
            System.out.println("Error borrar PersistenciaTiposIndices : " + e);
        }
    }

    public List<TiposIndices> consultarTiposIndices(EntityManager em) {
        try {
            Query query = em.createQuery("SELECT t FROM TiposIndices t ORDER BY t.codigo  ASC");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<TiposIndices> evalActividades = query.getResultList();
            return evalActividades;
        } catch (Exception e) {
            System.out.println("Error buscarTiposIndices ERROR" + e);
            return null;
        }
    }

    public TiposIndices consultarTipoIndice(EntityManager em, BigInteger secuencia) {
        try {
            Query query = em.createQuery("SELECT t FROM TiposIndices t WHERE t.secuencia =:secuencia");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            query.setParameter("secuencia", secuencia);
            TiposIndices tiposIndices = (TiposIndices) query.getSingleResult();
            return tiposIndices;
        } catch (Exception e) {
            System.out.println("Error buscarTipoIndiceSecuencia");
            TiposIndices tiposIndices = null;
            return tiposIndices;
        }
    }

    public BigInteger contarIndicesTipoIndice(EntityManager em, BigInteger secuencia) {
        BigInteger retorno = new BigInteger("-1");
        try {
            String sqlQuery = "SELECT COUNT(*)FROM indices WHERE tipoindice = ?";
            Query query = em.createNativeQuery(sqlQuery);
            query.setParameter(1, secuencia);
            retorno = new BigInteger(query.getSingleResult().toString());
            System.out.println("Contador PersistenciaTiposIndices contarIndicesTipoIndice Retorno " + retorno);
            return retorno;
        } catch (Exception e) {
            System.err.println("Error PersistenciaTiposIndices contarIndicesTipoIndice ERROR : " + e);
            return retorno;
        }
    }
}
