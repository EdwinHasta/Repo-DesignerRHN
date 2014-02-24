/**
 * Documentación a cargo de AndresPineda
 */
package Persistencia;

import Entidades.ProcesosProductivos;
import InterfacePersistencia.PersistenciaProcesosProductivosInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Clase Stateless. <br>
 * Clase encargada de realizar operaciones sobre la tabla 'ProcesosProductivos'
 * de la base de datos.
 *
 * @author AndresPineda
 */
@Stateless
public class PersistenciaProcesosProductivos implements PersistenciaProcesosProductivosInterface{

    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos.
     */
    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    @Override
    public void crear(ProcesosProductivos procesos) {
        try {
            em.persist(procesos);
        } catch (Exception e) {
            System.out.println("Error crear PersistenciaProcesosProductivos : " + e.toString());
        }
    }

    @Override
    public void editar(ProcesosProductivos procesos) {
        try {
            em.merge(procesos);
        } catch (Exception e) {
            System.out.println("Error editar PersistenciaProcesosProductivos : " + e.toString());
        }
    }

    @Override
    public void borrar(ProcesosProductivos procesos) {
        try {
            em.remove(em.merge(procesos));
        } catch (Exception e) {
            System.out.println("Error borrar PersistenciaProcesosProductivos : " + e.toString());
        }
    }

    @Override
    public List<ProcesosProductivos> buscarProcesosProductivos() {
        try {
            Query query = em.createQuery("SELECT pp FROM ProcesosProductivos pp ORDER BY pp.codigo ASC");
            List<ProcesosProductivos> procesos = query.getResultList();
            return procesos;
        } catch (Exception e) {
            System.out.println("Error buscarProcesosProductivos PersistenciaProcesosProductivos : " + e.toString());
            return null;
        }
    }

    @Override
    public ProcesosProductivos buscarProcesosProductivosSecuencia(BigInteger secuencia) {
        try {
            Query query = em.createQuery("SELECT pp FROM ProcesosProductivos pp WHERE pp.secuencia = :secuencia");
            query.setParameter("secuencia", secuencia);
            ProcesosProductivos procesos = (ProcesosProductivos) query.getSingleResult();
            return procesos;
        } catch (Exception e) {
            System.out.println("Error buscarProcesosProductivosSecuencia PersistenciaProcesosProductivos : " + e.toString());
            ProcesosProductivos procesos = null;
            return procesos;
        }
    }
}
