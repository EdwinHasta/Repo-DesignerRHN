/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import InterfacePersistencia.PersistenciaEvalCompetenciasInterface;
import Entidades.EvalCompetencias;
import java.math.BigDecimal;
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
public class PersistenciaEvalCompetencias implements PersistenciaEvalCompetenciasInterface {

    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos
     */
    /*@PersistenceContext(unitName = "DesignerRHN-ejbPU")
     private EntityManager em;*/
    public void crear(EntityManager em, EvalCompetencias evalCompetencias) {
        em.getTransaction().begin();
        em.persist(evalCompetencias);
        em.getTransaction().commit();
    }

    public void editar(EntityManager em, EvalCompetencias evalCompetencias) {
        em.getTransaction().begin();
        em.merge(evalCompetencias);
        em.getTransaction().commit();
    }

    public void borrar(EntityManager em, EvalCompetencias evalCompetencias) {
        em.getTransaction().begin();
        em.remove(em.merge(evalCompetencias));
        em.getTransaction().commit();
    }

    public EvalCompetencias buscarEvalCompetencia(EntityManager em, BigInteger secuenciaTE) {
        try {
            return em.find(EvalCompetencias.class, secuenciaTE);
        } catch (Exception e) {
            return null;
        }
    }

    public List<EvalCompetencias> buscarEvalCompetencias(EntityManager em) {
        Query query = em.createQuery("SELECT ec FROM EvalCompetencias ec  ORDER BY ec.codigo ASC ");
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        List<EvalCompetencias> listMotivosDemandas = query.getResultList();
        return listMotivosDemandas;

    }

    public BigInteger contadorCompetenciasCargos(EntityManager em, BigInteger secuencia) {
        BigInteger retorno = null;
        try {
            String sqlQuery = " SELECT COUNT(*)FROM competenciascargos ca, evalcompetencias ec WHERE ca.evalcompetencia= ec.secuencia AND ec.secuencia = ?";
            Query query = em.createNativeQuery(sqlQuery);
            query.setParameter(1, secuencia);
            retorno = new BigInteger(query.getSingleResult().toString());
            System.out.println("Contador PERSISTENCIAEVALCOMPETENCIAS ContadorCompetenciasCargos Retorno : " + retorno);
            return retorno;
        } catch (Exception e) {
            System.err.println("Error PERSISTENCIAEVALCOMPETENCIAS ContadorCompetenciasCargos. " + e);
            return retorno;
        }
    }

}
