/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Persistencia;

import Entidades.TiposAsociaciones;
import InterfacePersistencia.PersistenciaTiposAsociacionesInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Clase Stateless 
 * Clase encargada de realizar operaciones sobre la tabla 'TiposAsociaciones'
 * de la base de datos.
 * @author betelgeuse
 */
@Stateless
public class PersistenciaTiposAsociaciones implements PersistenciaTiposAsociacionesInterface{
    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos.
     */
    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    @Override
    public void crear(TiposAsociaciones tiposAsociaciones) {
        try {
            em.persist(tiposAsociaciones);
        } catch (Exception e) {
            System.out.println("Error crear PersistenciaTiposAsociaciones");
        }
    }

    @Override
    public void editar(TiposAsociaciones tiposAsociaciones) {
        try {
            em.merge(tiposAsociaciones);
        } catch (Exception e) {
            System.out.println("Error editar PersistenciaTiposAsociaciones");
        }
    }

    @Override
    public void borrar(TiposAsociaciones tiposAsociaciones) {
        try {
            em.remove(em.merge(tiposAsociaciones));
        } catch (Exception e) {
            System.out.println("Error borrar PersistenciaTiposAsociaciones");
        }
    }

    @Override
    public List<TiposAsociaciones> buscarTiposAsociaciones() {
        try {
            List<TiposAsociaciones> tiposAsociaciones = (List<TiposAsociaciones>) em.createNamedQuery("TiposAsociaciones.findAll").getResultList();
            return tiposAsociaciones;
        } catch (Exception e) {
            System.out.println("Error buscarTiposAsociaciones");
            return null;
        }
    }

    @Override
    public TiposAsociaciones buscarTiposAsociacionesSecuencia(BigInteger secuencia) {

        try {
            Query query = em.createQuery("SELECT t FROM TiposAsociaciones t WHERE t.secuencia = :secuencia");
            query.setParameter("secuencia", secuencia);
            TiposAsociaciones tiposAsociaciones = (TiposAsociaciones) query.getSingleResult();
            return tiposAsociaciones;
        } catch (Exception e) {
            System.out.println("Error buscarTiposAsociacionesSecuencia");
            TiposAsociaciones tiposAsociaciones = null;
            return tiposAsociaciones;
        }
    }
}
