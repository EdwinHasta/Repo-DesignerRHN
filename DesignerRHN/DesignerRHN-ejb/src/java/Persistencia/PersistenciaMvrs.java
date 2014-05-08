/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Persistencia;

import Entidades.Mvrs;
import InterfacePersistencia.PersistenciaMvrsInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Clase Stateless.<br>
 * Clase encargada de realizar operaciones sobre la tabla 'Mvrs'
 * de la base de datos.
 * @author betelgeuse
 */
@Stateless
public class PersistenciaMvrs implements PersistenciaMvrsInterface {
    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos.
     */
//    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
//    private EntityManager em;

    @Override
    public void crear(EntityManager em, Mvrs mvrs) {
        em.persist(mvrs);
    }

    @Override
    public void editar(EntityManager em, Mvrs mvrs) {
        em.merge(mvrs);
    }

    @Override
    public void borrar(EntityManager em, Mvrs mvrs) {
        em.remove(em.merge(mvrs));
    }

    @Override
    public List<Mvrs> buscarMvrs(EntityManager em) {
        List<Mvrs> mvrs = (List<Mvrs>) em.createNamedQuery("Mvrs.findAll").getResultList();
        return mvrs;
    }

    @Override
    public Mvrs buscarMvrSecuencia(EntityManager em, BigInteger secuencia) {

        try {
            Query query = em.createQuery("SELECT mvrs FROM Mvrs mvrs WHERE mvrs.secuencia = :secuencia");
            query.setParameter("secuencia", secuencia);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            Mvrs mvrs = (Mvrs) query.getSingleResult();
            return mvrs;
        } catch (Exception e) {
            Mvrs mvrs = null;
            return mvrs;
        }
    }

    @Override
    public List<Mvrs> buscarMvrsEmpleado(EntityManager em, BigInteger secuencia) {
        try {
            Query query = em.createQuery("SELECT mvrs FROM Mvrs mvrs WHERE mvrs.empleado.secuencia = :secuencia");
            query.setParameter("secuencia", secuencia);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<Mvrs> mvrs = (List<Mvrs>) query.getResultList();
            return mvrs;
        } catch (Exception e) {
            List<Mvrs> mvrs = null;
            return mvrs;
        }
    }
}