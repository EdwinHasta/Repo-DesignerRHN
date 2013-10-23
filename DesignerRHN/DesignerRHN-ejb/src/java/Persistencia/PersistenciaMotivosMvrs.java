/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import InterfacePersistencia.PersistenciaMotivosMvrsInterface;
import Entidades.Motivosmvrs;
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
public class PersistenciaMotivosMvrs implements PersistenciaMotivosMvrsInterface {

    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    @Override
    public void crear(Motivosmvrs motivosMvrs) {
        em.persist(motivosMvrs);
    }

    @Override
    public void editar(Motivosmvrs motivosMvrs) {
        em.merge(motivosMvrs);
    }

    @Override
    public void borrar(Motivosmvrs motivosMvrs) {
        em.remove(em.merge(motivosMvrs));
    }

    @Override
    public Motivosmvrs buscarMotivosMvrs(BigInteger secuenciaMM) {
        try {
            return em.find(Motivosmvrs.class, secuenciaMM);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<Motivosmvrs> buscarMotivosMvrs() {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(Motivosmvrs.class));
        return em.createQuery(cq).getResultList();
    }
}
