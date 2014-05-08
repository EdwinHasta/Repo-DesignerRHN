/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import InterfacePersistencia.PersistenciaEnfoquesInterface;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import Entidades.Enfoques;
import java.math.BigInteger;
import java.util.List;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;

/**
 *
 * @author user
 */
@Stateless
public class PersistenciaEnfoques implements PersistenciaEnfoquesInterface {

    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos
     */
    /*@PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;*/

    public void crear(EntityManager em,Enfoques enfoques) {
        try {
            em.persist(enfoques);
        } catch (Exception e) {
            System.out.println("\n ERROR EN PersistenciaEnfoques crear ERROR " + e);
        }
    }

    public void editar(EntityManager em,Enfoques enfoques) {
        try {
            em.merge(enfoques);
        } catch (Exception e) {
            System.out.println("\n ERROR EN PersistenciaEnfoques editar ERROR " + e);
        }
    }

    public void borrar(EntityManager em,Enfoques enfoques) {
        try {
            em.remove(em.merge(enfoques));
        } catch (Exception e) {
            System.out.println("\n ERROR EN PersistenciEnfoques borrar ERROR " + e);
        }
    }

    public Enfoques buscarEnfoque(EntityManager em,BigInteger secuenciaEnfoques) {
        try {
            return em.find(Enfoques.class, secuenciaEnfoques);
        } catch (Exception e) {
            System.err.println("ERROR PersistenciaEnfoques buscarEnfoque ERROR " + e);
            return null;
        }
    }

    public List<Enfoques> buscarEnfoques(EntityManager em) {
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Enfoques.class));
            return em.createQuery(cq).getResultList();
        } catch (Exception e) {
            System.out.println("\n ERROR EN PersistenciaEnfoques buscarEnfoques ERROR" + e);
            return null;
        }
    }
    
    public BigInteger contadorTiposDetalles(EntityManager em,BigInteger secuencia) {
        BigInteger retorno;
        try {
            String sqlQuery = "SELECT COUNT(*)FROM tiposdetalles td , enfoques eee WHERE eee.secuencia=td.enfoque AND eee.secuencia  = ? ";
            Query query = em.createNativeQuery(sqlQuery);
            query.setParameter(1, secuencia);
            retorno = new BigInteger(query.getSingleResult().toString());
            System.out.println("PERSISTENCIAENFOQUES contadorTiposDetalles = " + retorno);
            return retorno;
        } catch (Exception e) {
            System.err.println("ERROR PERSISTENCIAENFOQUES contadorTiposDetalles  ERROR = " + e);
            retorno = new BigInteger("-1");
            return retorno;
        }
    }
}
