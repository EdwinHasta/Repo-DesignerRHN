/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Persistencia;

import Entidades.Asociaciones;
import InterfacePersistencia.PersistenciaAsociacionesInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Clase Stateless. <br>
 * Clase encargada de realizar operaciones sobre la tabla 'Asociaciones' de la base de datos
 * @author betelgeuse
 */
@Stateless
public class PersistenciaAsociaciones implements PersistenciaAsociacionesInterface{
    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos
     */
    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    @Override
    public void crear(Asociaciones asociaciones) {
        try {
            em.persist(asociaciones);
        } catch (Exception e) {
            System.out.println("Error crear PersistenciaAsociaciones");
        }
    }

    @Override
    public void editar(Asociaciones asociaciones) {
        try {
            em.merge(asociaciones);
        } catch (Exception e) {
            System.out.println("Error editar PersistenciaAsociaciones");
        }
    }

    @Override
    public void borrar(Asociaciones asociaciones) {
        try {
            em.remove(em.merge(asociaciones));
        } catch (Exception e) {
            System.out.println("Error borrar PersistenciaAsociaciones");
        }
    }

    @Override
    public List<Asociaciones> buscarAsociaciones() {
        try {
            List<Asociaciones> asociaciones = (List<Asociaciones>) em.createNamedQuery("Asociaciones.findAll").getResultList();
            return asociaciones;
        } catch (Exception e) {
            System.out.println("Error buscarAsociaciones");
            return null;
        }
    }

    @Override
    public Asociaciones buscarAsociacionesSecuencia(BigInteger secuencia) {

        try {
            Query query = em.createQuery("SELECT t FROM Asociaciones t WHERE t.secuencia = :secuencia");
            query.setParameter("secuencia", secuencia);
            Asociaciones asociaciones = (Asociaciones) query.getSingleResult();
            return asociaciones;
        } catch (Exception e) {
            System.out.println("Error buscarAsociacionesSecuencia");
            Asociaciones asociaciones = null;
            return asociaciones;
        }
    }
}
