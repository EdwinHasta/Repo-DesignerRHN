/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Persistencia;

import Entidades.InformacionesAdicionales;
import InterfacePersistencia.PersistenciaInformacionesAdicionalesInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Clase Stateless.<br>
 * Clase encargada de realizar operaciones sobre la tabla
 * 'InformacionesAdicionales' de la base de datos.
 *
 * @author betelgeuse
 */
@Stateless
public class PersistenciaInformacionesAdicionales implements PersistenciaInformacionesAdicionalesInterface {

    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos.
     */
//    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
//    private EntityManager em;
    @Override
    public void crear(EntityManager em, InformacionesAdicionales informacionesAdicionales) {
        em.clear();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(informacionesAdicionales);
            tx.commit();
        } catch (Exception e) {
            System.out.println("Error PersistenciaInformacionesAdicionales.crear: " + e);
            if (tx.isActive()) {
                tx.rollback();
            }
        }
    }

    @Override
    public void editar(EntityManager em, InformacionesAdicionales informacionesAdicionales) {
        em.clear();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(informacionesAdicionales);
            tx.commit();
        } catch (Exception e) {
            System.out.println("Error PersistenciaInformacionesAdicionales.editar: " + e);
            if (tx.isActive()) {
                tx.rollback();
            }
        }
    }

    @Override
    public void borrar(EntityManager em, InformacionesAdicionales informacionesAdicionales) {
        em.clear();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.remove(em.merge(informacionesAdicionales));
            tx.commit();

        } catch (Exception e) {
            try {
                if (tx.isActive()) {
                    tx.rollback();
                }
            } catch (Exception ex) {
                System.out.println("Error PersistenciaInformacionesAdicionales.borrar: " + e);
            }
        }
    }

    @Override
    public InformacionesAdicionales buscarinformacionAdicional(EntityManager em, BigInteger secuencia) {
        return em.find(InformacionesAdicionales.class, secuencia);
    }

    @Override
    public List<InformacionesAdicionales> buscarinformacionesAdicionales(EntityManager em) {
        javax.persistence.criteria.CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(InformacionesAdicionales.class));
        return em.createQuery(cq).getResultList();
    }

    @Override
    public List<InformacionesAdicionales> informacionAdicionalPersona(EntityManager em, BigInteger secuenciaEmpleado) {
        try {
            Query query = em.createQuery("SELECT COUNT(ia) FROM InformacionesAdicionales ia WHERE ia.empleado.secuencia = :secuenciaEmpleado");
            query.setParameter("secuenciaEmpleado", secuenciaEmpleado);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            Long resultado = (Long) query.getSingleResult();
            if (resultado > 0) {
                Query queryFinal = em.createQuery("SELECT ia FROM InformacionesAdicionales ia WHERE ia.empleado.secuencia = :secuenciaEmpleado and ia.fechainicial = (SELECT MAX(iad.fechainicial) FROM InformacionesAdicionales iad WHERE iad.empleado.secuencia = :secuenciaEmpleado)");
                queryFinal.setParameter("secuenciaEmpleado", secuenciaEmpleado);
                queryFinal.setHint("javax.persistence.cache.storeMode", "REFRESH");
                List<InformacionesAdicionales> listaInformacionesAdicionales = queryFinal.getResultList();
                return listaInformacionesAdicionales;
            }
            return null;
        } catch (Exception e) {
            System.out.println("Error PersistenciaInformacionesAdicionales.informacionAdicionalPersona" + e);
            return null;
        }
    }

    @Override
    public List<InformacionesAdicionales> informacionAdicionalEmpleadoSecuencia(EntityManager em, BigInteger secuenciaEmpleado) {
        try {
            Query query = em.createQuery("SELECT ia FROM InformacionesAdicionales ia WHERE ia.empleado.secuencia = :secuenciaEmpleado");
            query.setParameter("secuenciaEmpleado", secuenciaEmpleado);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<InformacionesAdicionales> resultado = (List<InformacionesAdicionales>) query.getResultList();
            return resultado;
        } catch (Exception e) {
            System.out.println("Error PersistenciaInformacionesAdicionales.informacionAdicionalEmpleadoSecuencia : " + e.toString());
            return null;
        }
    }
}
