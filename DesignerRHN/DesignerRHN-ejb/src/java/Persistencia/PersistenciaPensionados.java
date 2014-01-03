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
 * Clase encargada de realizar operaciones sobre la tabla 'MotivosContratos'
 * de la base de datos.
 * @author AndresPineda
 */
@Stateless
public class PersistenciaPensionados implements PersistenciaPensionadosInterface{
    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos.
     */
    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    @Override
    public void crear(Pensionados pensionados) {
        try {
            em.persist(pensionados);
        } catch (Exception e) {
            System.out.println("El registro Pensionados no exite o esta reservada por lo cual no puede ser modificada (Pensionados)");
        }
    }
    
    @Override
    public void editar(Pensionados pensionados) {
        try {
            em.merge(pensionados);
        } catch (Exception e) {
            System.out.println("No se pudo modificar el registro Pensionados");
        }
    }

    @Override
    public void borrar(Pensionados pensionados) {
        try {
            em.remove(em.merge(pensionados));
        } catch (Exception e) {
            System.out.println("No se pudo borrar el registro Pensionados");
        }
    }

    @Override
    public Pensionados buscarPensionado(BigInteger secuencia) {
        try {
            BigInteger in = (BigInteger) secuencia;
            return em.find(Pensionados.class, in);
        } catch (Exception e) {
            System.out.println("Error buscarPensionado (PersistenciaPensionados)");
            return null;
        }
    }

    @Override
    public List<Pensionados> buscarPensionados() {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(Pensionados.class));
        return em.createQuery(cq).getResultList();
    }

    @Override
    public List<Pensionados> buscarPensionadosEmpleado(BigInteger secEmpleado) {
        try {
            Query query = em.createQuery("SELECT p FROM Pensionados p WHERE p.vigenciatipotrabajador.empleado.secuencia = :secuenciaEmpl ORDER BY p.fechainiciopension DESC");
            query.setParameter("secuenciaEmpl", secEmpleado);

            List<Pensionados> pensionados = query.getResultList();
            return pensionados;
        } catch (Exception e) {
            System.out.println("Error en Persistencia Pensionados (buscarPensionadosEmpleado) " + e.toString());
            return null;
        }
    }
    
    @Override
    public Pensionados buscarPensionVigenciaSecuencia(BigInteger secVigencia) {
        try {
            Query query = em.createQuery("SELECT p FROM Pensionados p WHERE p.vigenciatipotrabajador.secuencia = :secVigencia");
            query.setParameter("secVigencia", secVigencia);
            Pensionados pensionVigencia = (Pensionados) query.getSingleResult();
            return pensionVigencia;
        } catch (Exception e) {
            System.out.println("buscarPensionVigenciaSecuencia Error (PersistenciaPensionados)");
            return new Pensionados();
        }
    }
}
