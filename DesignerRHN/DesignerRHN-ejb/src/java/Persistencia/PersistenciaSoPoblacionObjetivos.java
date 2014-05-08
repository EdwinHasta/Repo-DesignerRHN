/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Persistencia;

import Entidades.SoPoblacionObjetivos;
import InterfacePersistencia.PersistenciaSoPoblacionObjetivosInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaQuery;

/**
 *
 * @author user
 */
@Stateless
public class PersistenciaSoPoblacionObjetivos implements PersistenciaSoPoblacionObjetivosInterface {

//   @PersistenceContext(unitName = "DesignerRHN-ejbPU")
//    private EntityManager em;
    
    public void crear(EntityManager em, SoPoblacionObjetivos soPoblacionObjetivos) {
        em.persist(soPoblacionObjetivos);
    }

    public void editar(EntityManager em, SoPoblacionObjetivos soPoblacionObjetivos) {
        em.merge(soPoblacionObjetivos);
    }

    public void borrar(EntityManager em, SoPoblacionObjetivos soPoblacionObjetivos) {
        em.remove(em.merge(soPoblacionObjetivos));
    }

    public SoPoblacionObjetivos buscarSoPoblacionObjetivo(EntityManager em, BigInteger secuencia) {
        try {
            return em.find(SoPoblacionObjetivos.class, secuencia);
        } catch (Exception e) {
            System.out.println("Persistencia SoPoblacionObjetivos "+e);
            return null;
        }
    }

    public List<SoPoblacionObjetivos> consultarSoPoblacionObjetivos(EntityManager em) {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(SoPoblacionObjetivos.class));
        return em.createQuery(cq).getResultList();
    }
}
