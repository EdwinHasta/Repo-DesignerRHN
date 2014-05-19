/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Persistencia;

import Entidades.VWVacaPendientesEmpleados;
import InterfacePersistencia.PersistenciaVWVacaPendientesEmpleadosInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Clase Stateless.<br>
 * Clase encargada de realizar operaciones sobre la vista
 * 'VWVacaPendientesEmpleados' de la base de datos.
 *
 * @author betelgeuse
 */
@Stateless
public class PersistenciaVWVacaPendientesEmpleados implements PersistenciaVWVacaPendientesEmpleadosInterface {

    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos.
     */
    /*    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
     private EntityManager em;
     */

    @Override
    public void crear(EntityManager em, VWVacaPendientesEmpleados vacaP) {
        try {
            em.getTransaction().begin();
            em.persist(vacaP);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error creando VWVacaPendientesEmpleados : " + e.toString());
        }
    }

    @Override
    public void editar(EntityManager em, VWVacaPendientesEmpleados vacaP) {
        try {
            em.getTransaction().begin();
            em.merge(vacaP);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error editando VWVacaPendientesEmpleados : " + e.toString());
        }
    }

    @Override
    public void borrar(EntityManager em, VWVacaPendientesEmpleados vacaP) {
        try {
            em.getTransaction().begin();
            em.remove(em.merge(vacaP));
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error borrando VWVacaPendientesEmpleados : " + e.toString());
        }
    }

    @Override
    public List<VWVacaPendientesEmpleados> vacaEmpleadoPendientes(EntityManager em, BigInteger secuencia) {
        List<VWVacaPendientesEmpleados> listaVacaPendientesEmpleados = null;
        try {
            String script = "SELECT vwv FROM VWVacaPendientesEmpleados vwv WHERE vwv.empleado = :empleado AND vwv.diaspendientes > 0";
            Query query = em.createQuery(script).setParameter("empleado", secuencia);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            listaVacaPendientesEmpleados = query.getResultList();
        } catch (Exception e) {
            System.err.println("PersistenciaVWVacaPendientesEmpleados.buscarVacaPendientesEmpleados.");
            System.out.println(e);
        } finally {
            return listaVacaPendientesEmpleados;
        }
    }

    @Override
    public List<VWVacaPendientesEmpleados> vacaEmpleadoDisfrutadas(EntityManager em, BigInteger sec) {
        List<VWVacaPendientesEmpleados> listaVacaPendientesEmpleados = null;
        try {
            String script = "SELECT vwv FROM VWVacaPendientesEmpleados vwv WHERE vwv.empleado = :empleado AND vwv.diaspendientes <= 0";
            Query query = em.createQuery(script).setParameter("empleado", sec);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            listaVacaPendientesEmpleados = query.getResultList();
        } catch (Exception e) {
            System.err.println("PersistenciaVWVacaPendientesEmpleados.buscarVacaPendientesEmpleadosDisfrutadas");
            System.out.println(e);
        } finally {
            return listaVacaPendientesEmpleados;
        }
    }

    @Override
    public List<VWVacaPendientesEmpleados> buscarVacaPendientesEmpleados(EntityManager em, BigInteger secuenciaEmpleado) {
        List<VWVacaPendientesEmpleados> listaVacaPendientesEmpleados = null;
        try {
            listaVacaPendientesEmpleados = em.createNamedQuery("VWVacaPendientesEmpleados.findByEmpleado").setParameter("empleado", secuenciaEmpleado).getResultList();
        } catch (Exception e) {
            System.err.println("PersistenciaVWVacaPendientesEmpleados.buscarVacaPendientesEmpleados.");
            System.out.println(e);
        } finally {
            return listaVacaPendientesEmpleados;
        }
    }
}
