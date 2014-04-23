/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Persistencia;

import InterfacePersistencia.PersistenciaMotivosMvrsInterface;
import Entidades.Motivosmvrs;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;

/**
 * Clase Stateless.<br> 
 * Clase encargada de realizar operaciones sobre la tabla 'MotivosMvrs'
 * de la base de datos.
 * @author betelgeuse
 */
@Stateless
public class PersistenciaMotivosMvrs implements PersistenciaMotivosMvrsInterface {
    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos.
     */
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

    //@Override
    public List<Motivosmvrs> buscarMotivosMvrs() {
        Query query = em.createQuery("SELECT m FROM Motivosmvrs m");
        List<Motivosmvrs> lista = query.getResultList();
        return lista;
    }
}
