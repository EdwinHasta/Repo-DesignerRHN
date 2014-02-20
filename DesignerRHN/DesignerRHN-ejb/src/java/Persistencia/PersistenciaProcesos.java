/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Persistencia;

import Entidades.Procesos;
import InterfacePersistencia.PersistenciaProcesosInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Clase Stateless. <br> 
 * Clase encargada de realizar operaciones sobre la tabla 'Procesos'
 * de la base de datos.
 * @author betelgeuse
 */
@Stateless
public class PersistenciaProcesos implements PersistenciaProcesosInterface {
    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos.
     */
    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;
    
    @Override
    public void crear(Procesos procesos) {
        try {
            em.persist(procesos);
        } catch (Exception e) {
            System.out.println("Error crear PersistenciaProcesos");
        }
    }

    @Override
    public void editar(Procesos procesos) {
        try {
            em.merge(procesos);
        } catch (Exception e) {
            System.out.println("Error editar PersistenciaProcesos");
        }
    }

    @Override
    public void borrar(Procesos procesos) {
        try {
            em.remove(em.merge(procesos));
        } catch (Exception e) {
            System.out.println("Error borrar PersistenciaProcesos");
        }
    }
   
    @Override
    public List<Procesos> buscarProcesos() {
        try {
            Query query = em.createQuery("SELECT t FROM Procesos t ORDER BY t.codigo ASC");
            List<Procesos> procesos = query.getResultList();
            return procesos;
        } catch (Exception e) {
            System.out.println("Error buscarProcesos");
            return null;
        }
    }

    @Override
    public Procesos buscarProcesosSecuencia(BigInteger secuencia) {
        try {
            Query query = em.createQuery("SELECT t FROM Procesos t WHERE t.secuencia = :secuencia");
            query.setParameter("secuencia", secuencia);
            Procesos procesos = (Procesos) query.getSingleResult();
            return procesos;
        } catch (Exception e) {
            System.out.println("Error buscarProcesosSecuencia");
            Procesos procesos = null;
            return procesos;
        }
    }

    @Override
    public List<Procesos> lovProcesos() {
        try {
            Query query = em.createQuery("SELECT p FROM Procesos p ORDER BY p.descripcion");
            List<Procesos> listaProcesos = query.getResultList();
            return listaProcesos;
        } catch (Exception e) {
            System.out.println("Error PersistenciaProcesos.lovProcesos: " + e);
            return null;
        }
    }

    @Override
    public List<Procesos> procesosParametros() {
        try {
            String sqlQuery = "SELECT P.* FROM PROCESOS P\n"
                    + "                   WHERE EXISTS (SELECT 'x' FROM usuariosprocesos up, usuarios u \n"
                    + "		                           WHERE up.proceso=p.secuencia \n"
                    + "					   AND u.secuencia = up.usuario \n"
                    + "					   AND u.alias = user)\n"
                    + "                   and nvl(p.automatico,'S') = 'S'";
            Query query = em.createNativeQuery(sqlQuery, Procesos.class);
            List<Procesos> listaProcesosParametros = query.getResultList();
            return listaProcesosParametros;
        } catch (Exception e) {
            System.out.println("Error en PersistenciaProcesos.procesosParametros: " + e);
            return null;
        }
    }
}
