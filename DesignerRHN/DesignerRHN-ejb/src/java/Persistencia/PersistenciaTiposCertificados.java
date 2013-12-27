/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
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
 * Clase Stateless 
 * Clase encargada de realizar operaciones sobre la tabla 'TiposCertificados'
 * de la base de datos.
 * @author betelgeuse
 */
@Stateless
public class PersistenciaTiposCertificados implements PersistenciaTiposCertificadosInterface {
    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos.
     */
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
    public TiposCertificados buscarTipoCertificado(BigInteger secuencia) {
        try {
            return em.find(TiposCertificados.class, secuencia);
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
