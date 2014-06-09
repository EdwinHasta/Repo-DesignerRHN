/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Persistencia;

import Entidades.VigenciasFormasPagos;
import InterfacePersistencia.PersistenciaVigenciasFormasPagosInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;

/**
 * Clase Stateless.<br>
 * Clase encargada de realizar operaciones sobre la tabla 'VigenciasFormasPagos'
 * de la base de datos.
 *
 * @author betelgeuse
 */
@Stateless
public class PersistenciaVigenciasFormasPagos implements PersistenciaVigenciasFormasPagosInterface {

    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos.
     */
    /*    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
     private EntityManager em;
     */
    @Override
    public void crear(EntityManager em, VigenciasFormasPagos vigenciasFormasPagos) {
        em.clear();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(vigenciasFormasPagos);
            tx.commit();
        } catch (Exception e) {
            System.out.println("PersistenciaVigenciasFormasPagos La vigencia no exite o esta reservada por lo cual no puede ser modificada: " + e);
            try {
                if (tx.isActive()) {
                    tx.rollback();
                }
            } catch (Exception ex) {
                System.out.println("No se puede hacer rollback porque no hay una transacción");
            }
        }
    }

    @Override
    public void editar(EntityManager em, VigenciasFormasPagos vigenciasFormasPagos) {
        em.clear();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(vigenciasFormasPagos);
            tx.commit();
        } catch (Exception e) {
            System.out.println("La vigencia no exite o esta reservada por lo cual no puede ser modificada: " + e);
            try {
                if (tx.isActive()) {
                    tx.rollback();
                }
            } catch (Exception ex) {
                System.out.println("No se puede hacer rollback porque no hay una transacción");
            }
        }
    }

    @Override
    public void borrar(EntityManager em, VigenciasFormasPagos vigenciasFormasPagos) {
        em.clear();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.remove(em.merge(vigenciasFormasPagos));
            tx.commit();
        } catch (Exception e) {
            System.out.println("La vigencia no exite o esta reservada por lo cual no puede ser modificada: " + e);
            try {
                if (tx.isActive()) {
                    tx.rollback();
                }
            } catch (Exception ex) {
                System.out.println("No se puede hacer rollback porque no hay una transacción");
            }
        }
    }

    @Override
    public VigenciasFormasPagos buscarVigenciaFormaPago(EntityManager em, BigInteger secuencia) {
        try {
            return em.find(VigenciasFormasPagos.class, secuencia);
        } catch (Exception e) {
            System.out.println("Error en la persistencia vigencias formas pagos ERROR :");
            return null;
        }
    }

    @Override
    public List<VigenciasFormasPagos> buscarVigenciasFormasPagosPorEmpleado(EntityManager em, BigInteger secEmpleado) {
        try {
            Query query = em.createQuery("SELECT vfp FROM VigenciasFormasPagos vfp WHERE vfp.empleado.secuencia = :secuenciaEmpl ORDER BY vfp.fechavigencia DESC");
            query.setParameter("secuenciaEmpl", secEmpleado);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<VigenciasFormasPagos> vigenciasNormasEmpleados = query.getResultList();
            return vigenciasNormasEmpleados;
        } catch (Exception e) {
            System.out.println("Error en Persistencia Vigencias Formas Pagos Por Empleados " + e);
            return null;
        }
    }

    @Override
    public List<VigenciasFormasPagos> buscarVigenciasFormasPagos(EntityManager em) {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(VigenciasFormasPagos.class));
        return em.createQuery(cq).getResultList();
    }
}
