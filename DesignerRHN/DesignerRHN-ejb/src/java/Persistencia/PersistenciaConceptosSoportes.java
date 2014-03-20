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
    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    public void crear(ConceptosSoportes conceptosSoportes) {
        try {
            em.persist(conceptosSoportes);
        } catch (Exception e) {
            System.out.println("Error crear PersistenciaConceptosSoportes");
        }
    }

    public void editar(ConceptosSoportes conceptosSoportes) {
        try {
            em.merge(conceptosSoportes);
        } catch (Exception e) {
            System.out.println("Error editar PersistenciaConceptosSoportes");
        }
    }

    public void borrar(ConceptosSoportes conceptosSoportes) {
        try {
            em.remove(em.merge(conceptosSoportes));
        } catch (Exception e) {
            System.out.println("Error borrar PersistenciaConceptosSoportes");
        }
    }

    public List<ConceptosSoportes> consultarConceptosSoportes() {
        try {
            Query query = em.createQuery("SELECT te FROM ConceptosSoportes te");
            List<ConceptosSoportes> conceptosSoportes = query.getResultList();
            return conceptosSoportes;
        } catch (Exception e) {
            System.out.println("Error consultarConceptosSoportes");
            return null;
        }
    }

    public ConceptosSoportes consultarConceptoSoporte(BigInteger secuencia) {
        try {
            Query query = em.createQuery("SELECT te FROM ConceptosSoportes te WHERE te.secuencia = :secuencia");
            query.setParameter("secuencia", secuencia);
            ConceptosSoportes conceptosSoportes = (ConceptosSoportes) query.getSingleResult();
            return conceptosSoportes;
        } catch (Exception e) {
            System.out.println("Error consultarConceptosSoportes");
            ConceptosSoportes conceptosSoportes = null;
            return conceptosSoportes;
        }
    }

    public BigInteger consultarConceptoSoporteConceptoOperador(BigInteger concepto, BigInteger operador) {
        try {
            Query query = em.createQuery("SELECT COUNT(te) FROM ConceptosSoportes te WHERE te.concepto.secuencia = :concepto AND te.operando.secuencia = :operando");
            query.setParameter("concepto", concepto);
            query.setParameter("operando", operador);
            BigInteger conceptosSoportes = new BigInteger(query.getSingleResult().toString());
            return conceptosSoportes;
        } catch (Exception e) {
            System.out.println("Error consultarConceptosSoportes");
            ConceptosSoportes conceptosSoportes = null;
            return null;
        }
    }

}
