/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import Entidades.FormulasContratosEntidades;
import InterfacePersistencia.PersistenciaFormulasContratosEntidadesInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

/**
 *
 * @author user
 */
@Stateless
public class PersistenciaFormulasContratosEntidades implements PersistenciaFormulasContratosEntidadesInterface {

    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos.
     */
    /* @PersistenceContext(unitName = "DesignerRHN-ejbPU")
     private EntityManager em;*/
    public void crear(EntityManager em, FormulasContratosEntidades formulasAseguradas) {
        em.clear();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(formulasAseguradas);
            tx.commit();
        } catch (Exception e) {
            System.out.println("Error PersistenciaFormulasContratosEntidades.crear: " + e);
            if (tx.isActive()) {
                tx.rollback();
            }
        }
    }

    public void editar(EntityManager em, FormulasContratosEntidades formulasAseguradas) {
        em.clear();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(formulasAseguradas);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            System.out.println("Error PersistenciaFormulasContratosEntidades.editar: " + e);
        }
    }

    public void borrar(EntityManager em, FormulasContratosEntidades formulasAseguradas) {
        em.clear();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.remove(em.merge(formulasAseguradas));
            tx.commit();

        } catch (Exception e) {
            try {
                if (tx.isActive()) {
                    tx.rollback();
                }
            } catch (Exception ex) {
                System.out.println("Error PersistenciaFormulasContratosEntidades.borrar: " + e);
            }
        }
    }

    public List<FormulasContratosEntidades> consultarFormulasContratosEntidades(EntityManager em) {
        try {
            em.clear();
            Query query = em.createQuery("SELECT te FROM FormulasContratosEntidades te");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<FormulasContratosEntidades> formulasAseguradas = query.getResultList();
            return formulasAseguradas;
        } catch (Exception e) {
            System.out.println("Error consultarFormulasContratosEntidades ERROR : " + e);
            return null;
        }
    }

    public List<FormulasContratosEntidades> consultarFormulasContratosEntidadesPorFormulaContrato(EntityManager em, BigInteger secFormulaContrato) {
        try {
            em.clear();
            Query query = em.createQuery("SELECT te FROM FormulasContratosEntidades te WHERE te.formulacontrato.secuencia = :formulaContrato");
            query.setParameter("formulaContrato", secFormulaContrato);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<FormulasContratosEntidades> formulasAseguradas = query.getResultList();
            return formulasAseguradas;
        } catch (Exception e) {
            System.out.println("Error consultarFormulasContratosEntidadesPorFormulaContrato ERROR : " + e);
            return null;
        }
    }

    public FormulasContratosEntidades consultarFormulaContratoEntidad(EntityManager em, BigInteger secuencia) {
        try {
            em.clear();
            Query query = em.createQuery("SELECT te FROM FormulasContratosEntidades te WHERE te.secuencia = :secuencia");
            query.setParameter("secuencia", secuencia);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            FormulasContratosEntidades formulasAseguradas = (FormulasContratosEntidades) query.getSingleResult();
            return formulasAseguradas;
        } catch (Exception e) {
            System.out.println("Error consultarFormulasContratosEntidades");
            FormulasContratosEntidades formulasAseguradas = null;
            return formulasAseguradas;
        }
    }
}
