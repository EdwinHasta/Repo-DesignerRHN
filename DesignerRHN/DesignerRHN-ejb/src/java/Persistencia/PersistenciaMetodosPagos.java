/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Persistencia;

import Entidades.MetodosPagos;
import InterfacePersistencia.PersistenciaMetodosPagosInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaQuery;

/**
 * Clase Stateless.<br>
 * Clase encargada de realizar operaciones sobre la tabla 'MetodosPagos'
 * de la base de datos.
 * @author betelgeuse
 */
@Stateless
public class PersistenciaMetodosPagos implements PersistenciaMetodosPagosInterface {
    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos.
     */
    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;
 
    @Override
    public void crear(MetodosPagos metodosPagos) {
        em.persist(metodosPagos);
    }

    @Override
    public void editar(MetodosPagos metodosPagos) {
        em.merge(metodosPagos);
    }

    @Override
    public void borrar(MetodosPagos metodosPagos) {
        em.remove(em.merge(metodosPagos));
    }

    @Override
    public MetodosPagos buscarMetodosPagosEmpleado(BigInteger secuencia) {
        try {
            return em.find(MetodosPagos.class, secuencia);
        } catch (Exception e) {
            System.out.println("Error en la persistencia vigencias formas pagos ERROR : " + e);
            return null;
        }
    }

    @Override
    public List<MetodosPagos> buscarMetodosPagos() {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(MetodosPagos.class));
        return em.createQuery(cq).getResultList();
    }
}
