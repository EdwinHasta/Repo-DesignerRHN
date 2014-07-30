/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import Entidades.Declarantes;
import InterfacePersistencia.PersistenciaDeclarantesInterface;
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
public class PersistenciaDeclarantes implements PersistenciaDeclarantesInterface {

    /*@PersistenceContext(unitName = "DesignerRHN-ejbPU")
     private EntityManager em;*/
    @Override
    public void crear(EntityManager em, Declarantes declarantes) {
        em.clear();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(declarantes);
            tx.commit();
        } catch (Exception e) {
            System.out.println("Error PersistenciaDeclarantes.crear: " + e);
            if (tx.isActive()) {
                tx.rollback();
            }
        }
    }

    @Override
    public void editar(EntityManager em, Declarantes declarantes) {
        em.clear();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(declarantes);
            tx.commit();
        } catch (Exception e) {
            System.out.println("Error PersistenciaDeclarantes.editar: " + e);
            if (tx.isActive()) {
                tx.rollback();
            }
        }
    }

    @Override
    public void borrar(EntityManager em, Declarantes declarantes) {
        em.clear();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.remove(em.merge(declarantes));
            tx.commit();

        } catch (Exception e) {
            try {
                if (tx.isActive()) {
                    tx.rollback();
                }
            } catch (Exception ex) {
                System.out.println("Error PersistenciaDeclarantes.borrar: " + e);
            }
        }
    }

    @Override
    public List<Declarantes> buscarDeclarantes(EntityManager em) {
        em.clear();
        Query query = em.createNamedQuery("Declarantes.findAll");
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        List<Declarantes> declarantesLista = (List<Declarantes>) query.getResultList();
        return declarantesLista;
    }

    @Override
    public Declarantes buscarDeclaranteSecuencia(EntityManager em, BigInteger secuencia) {
        try {
            em.clear();
            Query query = em.createQuery("SELECT d FROM Declarantes d WHERE d.secuencia = :secuencia");
            query.setParameter("secuencia", secuencia);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            Declarantes declarantes = (Declarantes) query.getSingleResult();
            return declarantes;
        } catch (Exception e) {
            Declarantes declarantes = null;
            return declarantes;
        }
    }

    @Override
    public List<Declarantes> buscarDeclarantesPersona(EntityManager em, BigInteger secPersona) {
        try {
            em.clear();
            Query query = em.createQuery("SELECT d FROM Declarantes d WHERE d.persona.secuencia = :secPersona ORDER BY d.fechainicial DESC");
            query.setParameter("secPersona", secPersona);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<Declarantes> declarantesE = query.getResultList();
            return declarantesE;
        } catch (Exception e) {
            System.out.println("Error en Persistencia Declarantes " + e);
            return null;
        }
    }
}
