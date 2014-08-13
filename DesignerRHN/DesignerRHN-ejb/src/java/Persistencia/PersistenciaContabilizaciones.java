package Persistencia;

import Entidades.Contabilizaciones;
import InterfacePersistencia.PersistenciaContabilizacionesInterface;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

/**
 *
 * @author Administrador
 */
@Stateless
public class PersistenciaContabilizaciones implements PersistenciaContabilizacionesInterface{

    @Override
    public void crear(EntityManager em, Contabilizaciones contabilizaciones) {
        em.clear();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(contabilizaciones);
            tx.commit();
        } catch (Exception e) {
            System.out.println("Error PersistenciaContabilizaciones.crear: " + e.toString());
            if (tx.isActive()) {
                tx.rollback();
            }
        }
    }

    @Override
    public void editar(EntityManager em, Contabilizaciones contabilizaciones) {
        em.clear();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(contabilizaciones);
            tx.commit();
        } catch (Exception e) {
            System.out.println("Error PersistenciaContabilizaciones.editar: " + e.toString());
            if (tx.isActive()) {
                tx.rollback();
            }
        }
    }

    @Override
    public void borrar(EntityManager em, Contabilizaciones contabilizaciones) {
        em.clear();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.remove(em.merge(contabilizaciones));
            tx.commit();
        } catch (Exception e) {
            try {
                if (tx.isActive()) {
                    tx.rollback();
                }
            } catch (Exception ex) {
                System.out.println("Error PersistenciaContabilizaciones.borrar: " + e.toString());
            }
        }
    }

    @Override
    public List<Contabilizaciones> buscarContabilizaciones(EntityManager em) {
        try {
            em.clear();
            Query query = em.createQuery("SELECT c FROM Contabilizaciones c");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<Contabilizaciones> lista = query.getResultList();
            return lista;
        } catch (Exception e) {
            System.out.println("Error buscarContabilizaciones PersistenciaContabilizaciones : " + e.toString());
            return null;
        }
    }

    @Override
    public Date obtenerFechaMaximaContabilizaciones(EntityManager em) {
        try {
            em.clear();
            String sql = "select max(fechageneracion) from contabilizaciones "
                    + " where flag = 'CONTABILIZADO' and "
                    + " exists (select 'x' from solucionesnodos sn, empleados e\n"
                    + " where e.secuencia=sn.empleado and sn.secuencia=contabilizaciones.solucionnodo)";
            Query query = em.createNativeQuery(sql);
            Date fecha = (Date) query.getSingleResult();
            return fecha;
        } catch (Exception e) {
            System.out.println("Error obtenerFechaMaximaContabilizaciones PersistenciaContabilizaciones : " + e.toString());
            return null;
        }
    }
}
