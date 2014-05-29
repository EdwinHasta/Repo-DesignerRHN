/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import Entidades.FormulasAseguradas;
import InterfacePersistencia.PersistenciaFormulasAseguradasInterface;
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
public class PersistenciaFormulasAseguradas implements PersistenciaFormulasAseguradasInterface {

    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos.
     */
    /*@PersistenceContext(unitName = "DesignerRHN-ejbPU")
     private EntityManager em;*/
    public void crear(EntityManager em, FormulasAseguradas formulasAseguradas) {
        try {
            em.clear();
            em.getTransaction().begin();
            em.persist(formulasAseguradas);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error crear PersistenciaFormulasAseguradas");
        }
    }

    public void editar(EntityManager em, FormulasAseguradas formulasAseguradas) {
        try {
            em.clear();
            em.getTransaction().begin();
            em.merge(formulasAseguradas);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error editar PersistenciaFormulasAseguradas");
        }
    }

    public void borrar(EntityManager em, FormulasAseguradas formulasAseguradas) {
        try {
            em.clear();
            em.getTransaction().begin();
            em.remove(em.merge(formulasAseguradas));
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error borrar PersistenciaFormulasAseguradas");
        }
    }

    public List<FormulasAseguradas> consultarFormulasAseguradas(EntityManager em) {
        try {
            Query query = em.createQuery("SELECT te FROM FormulasAseguradas te");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<FormulasAseguradas> formulasAseguradas = query.getResultList();
            return formulasAseguradas;
        } catch (Exception e) {
            System.out.println("Error consultarFormulasAseguradas");
            return null;
        }
    }

    public FormulasAseguradas consultarFormulaAsegurada(EntityManager em, BigInteger secuencia) {
        try {
            Query query = em.createQuery("SELECT te FROM FormulasAseguradas te WHERE te.secuencia = :secuencia");
            query.setParameter("secuencia", secuencia);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            FormulasAseguradas formulasAseguradas = (FormulasAseguradas) query.getSingleResult();
            return formulasAseguradas;
        } catch (Exception e) {
            System.out.println("Error consultarFormulasAseguradas");
            FormulasAseguradas formulasAseguradas = null;
            return formulasAseguradas;
        }
    }
}
