/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Persistencia;

import Entidades.Pensionados;
import InterfacePersistencia.PersistenciaPensionadosInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;

/**
 * Clase Stateless. <br>
 * Clase encargada de realizar operaciones sobre la tabla 'MotivosContratos' de
 * la base de datos.
 *
 * @author AndresPineda
 */
@Stateless
public class PersistenciaPensionados implements PersistenciaPensionadosInterface {

    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos.
     */
//    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
//    private EntityManager em;
    @Override
    public void crear(EntityManager em, Pensionados pensionados) {
        try {
            em.getTransaction().begin();
            em.persist(pensionados);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            System.out.println("El registro Pensionados no exite o esta reservada por lo cual no puede ser modificada (Pensionados) : " + e.toString());
        }
    }

    @Override
    public void editar(EntityManager em, Pensionados pensionados) {
        try {
            em.getTransaction().begin();
            em.merge(pensionados);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            System.out.println("No se pudo modificar el registro Pensionados : " + e.toString());
        }
    }

    @Override
    public void borrar(EntityManager em, Pensionados pensionados) {
        try {
            em.getTransaction().begin();
            em.remove(em.merge(pensionados));
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            System.out.println("No se pudo borrar el registro Pensionados : " + e.toString());
        }
    }

    @Override
    public Pensionados buscarPensionado(EntityManager em, BigInteger secuencia) {
        try {
            BigInteger in = (BigInteger) secuencia;
            return em.find(Pensionados.class, in);
        } catch (Exception e) {
            System.out.println("Error buscarPensionado (PersistenciaPensionados)");
            return null;
        }
    }

    @Override
    public List<Pensionados> buscarPensionados(EntityManager em) {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(Pensionados.class));
        return em.createQuery(cq).getResultList();
    }

    @Override
    public List<Pensionados> buscarPensionadosEmpleado(EntityManager em, BigInteger secEmpleado) {
        try {
            Query query = em.createQuery("SELECT p FROM Pensionados p WHERE p.vigenciatipotrabajador.empleado.secuencia = :secuenciaEmpl ORDER BY p.fechainiciopension DESC");
            query.setParameter("secuenciaEmpl", secEmpleado);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<Pensionados> pensionados = query.getResultList();
            return pensionados;
        } catch (Exception e) {
            System.out.println("Error en Persistencia Pensionados (buscarPensionadosEmpleado) " + e.toString());
            return null;
        }
    }

    @Override
    public Pensionados buscarPensionVigenciaSecuencia(EntityManager em, BigInteger secVigencia) {
        try {
            System.out.println("secVigencia : " + secVigencia);
            Query query = em.createQuery("SELECT p FROM Pensionados p WHERE p.vigenciatipotrabajador.secuencia = :secVigencia");
            query.setParameter("secVigencia", secVigencia);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            Pensionados pensionVigencia = (Pensionados) query.getSingleResult();
            return pensionVigencia;
        } catch (Exception e) {
            System.out.println("buscarPensionVigenciaSecuencia Error (PersistenciaPensionados): " + e.toString());
            return new Pensionados();
        }
    }
}
