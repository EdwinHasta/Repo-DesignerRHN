/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Persistencia;

import Entidades.Soaccidentes;
import InterfacePersistencia.PersistenciaSoaccidentesInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

/**
 * Clase Stateless.<br>
 * Clase encargada de realizar operaciones sobre la tabla 'Soaccidentes' de la
 * base de datos.
 *
 * @author Viktor
 */
@Stateless
public class PersistenciaSoaccidentes implements PersistenciaSoaccidentesInterface {

    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos.
     */
//    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
//    private EntityManager em;
    @Override
    public void crear(EntityManager em, Soaccidentes soaccidentes) {
        em.clear();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(soaccidentes);
            tx.commit();
        } catch (Exception e) {
            System.out.println("Error PersistenciaSoaccidentes.crear: " + e);
            if (tx.isActive()) {
                tx.rollback();
            }
        }
    }

    @Override
    public void editar(EntityManager em, Soaccidentes soaccidentes) {
        em.clear();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(soaccidentes);
            tx.commit();
        } catch (Exception e) {
            System.out.println("Error PersistenciaSoaccidentes.editar: " + e);
            if (tx.isActive()) {
                tx.rollback();
            }
        }
    }

    @Override
    public void borrar(EntityManager em, Soaccidentes soaccidentes) {
        em.clear();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.remove(em.merge(soaccidentes));
            tx.commit();
        } catch (Exception e) {
            System.out.println("Error PersistenciaSoaccidentes.borrar: " + e);
            if (tx.isActive()) {
                tx.rollback();
            }
        }
    }

    @Override
    public List<Soaccidentes> accidentesEmpleado(EntityManager em, BigInteger secuenciaEmpleado) {
        try {
            Query query = em.createQuery("SELECT soa FROM Soaccidentes soa WHERE soa.empleado.secuencia = :secuenciaEmpleado");
            query.setParameter("secuenciaEmpleado", secuenciaEmpleado);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<Soaccidentes> todosAccidentes = query.getResultList();
            return todosAccidentes;
        } catch (Exception e) {
            System.out.println("Error: (todasNovedades)" + e);
            return null;
        }
    }
}
