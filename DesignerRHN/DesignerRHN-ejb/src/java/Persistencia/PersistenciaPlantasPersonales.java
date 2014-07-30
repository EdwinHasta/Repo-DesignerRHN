/**
 * Documentación a cargo de AndresPineda
 */
package Persistencia;

import Entidades.PlantasPersonales;
import InterfacePersistencia.PersistenciaPlantasPersonalesInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Clase Stateless. <br>
 * Clase encargada de realizar operaciones sobre la tabla 'PlantasPersonales' de
 * la base de datos.
 *
 * @author betelgeuse
 */
@Stateless
public class PersistenciaPlantasPersonales implements PersistenciaPlantasPersonalesInterface {

    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos.
     */
//    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
//    private EntityManager em;

    @Override
    public void crear(EntityManager em, PlantasPersonales plantasPersonales) {
        em.clear();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(plantasPersonales);
            tx.commit();
        } catch (Exception e) {
            System.out.println("Error PersistenciaPlantasPersonales.crear: " + e);
            if (tx.isActive()) {
                tx.rollback();
            }
        }
    }

    @Override
    public void editar(EntityManager em, PlantasPersonales plantasPersonales) {
        em.clear();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(plantasPersonales);
            tx.commit();
        } catch (Exception e) {
            System.out.println("Error PersistenciaPlantasPersonales.editar: " + e);
            if (tx.isActive()) {
                tx.rollback();
            }
        }
    }

    @Override
    public void borrar(EntityManager em, PlantasPersonales plantasPersonales) {
        em.clear();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.remove(em.merge(plantasPersonales));
            tx.commit();
        } catch (Exception e) {
            System.out.println("Error PersistenciaPlantasPersonales.borrar: " + e);
            if (tx.isActive()) {
                tx.rollback();
            }
        }
    }

    @Override
    public List<PlantasPersonales> consultarPlantasPersonales(EntityManager em) {
        try {
            em.clear();
            Query query = em.createQuery("SELECT pp FROM PlantasPersonales pp ORDER BY pp.cantidad");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<PlantasPersonales> plantasPersonales = query.getResultList();
            return plantasPersonales;
        } catch (Exception e) {
            System.err.println("Error consultarPlantasPersonales PersistenciaPlantasPersonales: " + e.toString());
            return null;
        }
    }

    @Override 
    public BigInteger consultarCantidadEstructuras(EntityManager em, BigInteger secEstructura) {
        try {
            em.clear();
            Query query = em.createQuery("SELECT SUM(pp.cantidad) FROM PlantasPersonales pp WHERE pp.estructura.secuencia=:secEstructura");
            query.setParameter("secEstructura", secEstructura);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            BigInteger total = (BigInteger) query.getSingleResult();
            return total;
        } catch (Exception e) {
            System.out.println("Error consultarCantidadEstructuras PersistenciaPlantasPersonales: " + e);
            BigInteger total = null;
            return total;
        }
    }
}
