/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import InterfacePersistencia.PersistenciaTiposCertificadosInterface;
import Entidades.TiposCertificados;
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
public class PersistenciaTiposCertificados implements PersistenciaTiposCertificadosInterface {

    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    @Override
    public void crear(TiposCertificados motivosMvrs) {
        em.persist(motivosMvrs);
    }

    @Override
    public void editar(TiposCertificados motivosMvrs) {
        em.merge(motivosMvrs);
    }

    @Override
    public void borrar(TiposCertificados motivosMvrs) {
        em.remove(em.merge(motivosMvrs));
    }

    @Override
    public TiposCertificados buscarTipoCertificado(BigInteger secuenciaTC) {
        try {
            return em.find(TiposCertificados.class, secuenciaTC);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<TiposCertificados> buscarTiposCertificados() {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(TiposCertificados.class));
        return em.createQuery(cq).getResultList();
    }
}
