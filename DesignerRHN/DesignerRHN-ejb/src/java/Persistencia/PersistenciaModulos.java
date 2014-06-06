/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Persistencia;

import Entidades.Modulos;
import InterfacePersistencia.PersistenciaModulosInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;

/**
 * Clase Stateless.<br>
 * Clase encargada de realizar operaciones sobre la tabla 'Modulos' de la base
 * de datos.
 *
 * @author -Felipphe- Felipe Triviño
 */
@Stateless
public class PersistenciaModulos implements PersistenciaModulosInterface {

    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos.
     */
//    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
//    private EntityManager em;
    @Override
    public void crear(EntityManager em, Modulos modulos) {
        em.clear();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(modulos);
            tx.commit();
        } catch (Exception e) {
            System.out.println("Error PersistenciaModulos.crear: " + e);
            if (tx.isActive()) {
                tx.rollback();
            }
        }
    }

    @Override
    public void editar(EntityManager em, Modulos modulos) {
        em.clear();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(modulos);
            tx.commit();
        } catch (Exception e) {
            System.out.println("Error PersistenciaModulos.editar: " + e);
            if (tx.isActive()) {
                tx.rollback();
            }
        }
    }

    @Override
    public void borrar(EntityManager em, Modulos modulos) {
        em.clear();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.remove(em.merge(modulos));
            tx.commit();

        } catch (Exception e) {
            try {
                if (tx.isActive()) {
                    tx.rollback();
                }
            } catch (Exception ex) {
                System.out.println("Error PersistenciaModulos.borrar: " + e);
            }
        }
    }

    @Override
    public Modulos buscarModulos(EntityManager em, BigInteger secuencia) {
        return em.find(Modulos.class, secuencia);
    }

    @Override
    public List<Modulos> buscarModulos(EntityManager em) {
        javax.persistence.criteria.CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(Modulos.class));
        return em.createQuery(cq).getResultList();
    }

}
