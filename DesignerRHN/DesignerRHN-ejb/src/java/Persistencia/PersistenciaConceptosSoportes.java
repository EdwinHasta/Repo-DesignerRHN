/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import Entidades.ConceptosSoportes;
import InterfacePersistencia.PersistenciaConceptosSoportesInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author user
 */
@Stateless
public class PersistenciaConceptosSoportes implements PersistenciaConceptosSoportesInterface {

    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos.
     */
   /* @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;*/

    public void crear(EntityManager em,ConceptosSoportes conceptosSoportes) {
        em.clear();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(conceptosSoportes);
            tx.commit();
        } catch (Exception e) {
            System.out.println("Error PersistenciaConceptosSoportes.crear: " + e);
            if (tx.isActive()) {
                tx.rollback();
            }
        }
    }

    public void editar(EntityManager em,ConceptosSoportes conceptosSoportes) {
        em.clear();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(conceptosSoportes);
            tx.commit();
        } catch (Exception e) {
            System.out.println("Error PersistenciaConceptosSoportes.editar: " + e);
            if (tx.isActive()) {
                tx.rollback();
            }
        }
    }

    public void borrar(EntityManager em,ConceptosSoportes conceptosSoportes) {
        em.clear();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.remove(em.merge(conceptosSoportes));
            tx.commit();

        } catch (Exception e) {
            try {
                if (tx.isActive()) {
                    tx.rollback();
                }
            } catch (Exception ex) {
                System.out.println("Error PersistenciaConceptosSoportes.borrar: " + e);
            }
        }
    }

    public List<ConceptosSoportes> consultarConceptosSoportes(EntityManager em) {
        try {
            Query query = em.createQuery("SELECT te FROM ConceptosSoportes te");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<ConceptosSoportes> conceptosSoportes = query.getResultList();
            return conceptosSoportes;
        } catch (Exception e) {
            System.out.println("Error consultarConceptosSoportes");
            return null;
        }
    }

    public ConceptosSoportes consultarConceptoSoporte(EntityManager em,BigInteger secuencia) {
        try {
            Query query = em.createQuery("SELECT te FROM ConceptosSoportes te WHERE te.secuencia = :secuencia");
            query.setParameter("secuencia", secuencia);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            ConceptosSoportes conceptosSoportes = (ConceptosSoportes) query.getSingleResult();
            return conceptosSoportes;
        } catch (Exception e) {
            System.out.println("Error consultarConceptosSoportes");
            ConceptosSoportes conceptosSoportes = null;
            return conceptosSoportes;
        }
    }

    public BigInteger consultarConceptoSoporteConceptoOperador(EntityManager em,BigInteger concepto, BigInteger operador) {
        try {
            Query query = em.createQuery("SELECT COUNT(te) FROM ConceptosSoportes te WHERE te.concepto.secuencia = :concepto AND te.operando.secuencia = :operando");
            query.setParameter("concepto", concepto);
            query.setParameter("operando", operador);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            BigInteger conceptosSoportes = new BigInteger(query.getSingleResult().toString());
            return conceptosSoportes;
        } catch (Exception e) {
            System.out.println("Error consultarConceptosSoportes");
            ConceptosSoportes conceptosSoportes = null;
            return null;
        }
    }

}
