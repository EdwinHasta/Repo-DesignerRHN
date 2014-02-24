/**
 * Documentación a cargo de AndresPineda
 */
package Persistencia;

import Entidades.TiposDetalles;
import InterfacePersistencia.PersistenciaTiposDetallesInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Clase Stateless. <br>
 * Clase encargada de realizar operaciones sobre la tabla 'TiposDetalles' de la
 * base de datos.
 *
 * @author AndresPineda
 */
@Stateless
public class PersistenciaTiposDetalles implements PersistenciaTiposDetallesInterface {

    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos.
     */
    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    @Override
    public void crear(TiposDetalles tiposDetalles) {
        try {
            em.persist(tiposDetalles);
        } catch (Exception e) {
            System.out.println("Error crear PersistenciaTiposDetalles : " + e.toString());
        }
    }

    @Override
    public void editar(TiposDetalles tiposDetalles) {
        try {
            em.merge(tiposDetalles);
        } catch (Exception e) {
            System.out.println("Error editar PersistenciaTiposDetalles : " + e.toString());
        }
    }

    @Override
    public void borrar(TiposDetalles tiposDetalles) {
        try {
            em.remove(em.merge(tiposDetalles));
        } catch (Exception e) {
            System.out.println("Error borrar PersistenciaTiposDetalles : " + e.toString());
        }
    }

    @Override
    public List<TiposDetalles> buscarTiposDetalles() {
        try {
            Query query = em.createQuery("SELECT td FROM TiposDetalles td ORDER BY td.codigo ASC");
            List<TiposDetalles> tiposDetalles = query.getResultList();
            return tiposDetalles;
        } catch (Exception e) {
            System.out.println("Error buscarTiposDetalles PersistenciaTiposDetalles : " + e.toString());
            return null;
        }
    }

    @Override
    public TiposDetalles buscarTiposDetallesSecuencia(BigInteger secuencia) {
        try {
            Query query = em.createQuery("SELECT td FROM TiposDetalles td WHERE td.secuencia =:secuencia");
            query.setParameter("secuencia", secuencia);
            TiposDetalles tiposDetalles = (TiposDetalles) query.getSingleResult();
            return tiposDetalles;
        } catch (Exception e) {
            System.out.println("Error buscarTiposDetallesSecuencia PersistenciaTiposDetalles : " + e.toString());
            TiposDetalles tiposDetalles = null;
            return tiposDetalles;
        }
    }
}
