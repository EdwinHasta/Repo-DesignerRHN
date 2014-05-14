/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Persistencia;

import Entidades.VigenciasNormasEmpleados;
import InterfacePersistencia.PersistenciaVigenciasNormasEmpleadosInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;

/**
 * Clase Stateless.<br>
 * Clase encargada de realizar operaciones sobre la tabla
 * 'VigenciasNormasEmpleados' de la base de datos.
 *
 * @author betelgeuse
 */
@Stateless
public class PersistenciaVigenciasNormasEmpleados implements PersistenciaVigenciasNormasEmpleadosInterface {

    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos.
     */
    /*    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
     private EntityManager em;
     */

    @Override
    public void crear(EntityManager em, VigenciasNormasEmpleados vigenciasNormasEmpleados) {
        try {
            em.getTransaction().begin();
            em.merge(vigenciasNormasEmpleados);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("La vigencia no exite o esta reservada por lo cual no puede ser modificada (VigenciasNormasEmpleados)" + e);
        }
        
    }

    @Override
    public void editar(EntityManager em, VigenciasNormasEmpleados vigenciasNormasEmpleados) {
        try {
            em.getTransaction().begin();
            em.merge(vigenciasNormasEmpleados);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("La vigencia no exite o esta reservada por lo cual no puede ser modificada (VigenciasNormasEmpleados)" + e);
        }
    }

    @Override
    public void borrar(EntityManager em, VigenciasNormasEmpleados vigenciasNormasEmpleados) {
        try {
            em.getTransaction().begin();
            em.remove(em.merge(vigenciasNormasEmpleados));
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error Persistencia Borrar VC: " + e);
        }
        
    }

    @Override
    public VigenciasNormasEmpleados buscarVigenciasNormasEmpleado(EntityManager em, BigInteger secuencia) {
        try {
            return em.find(VigenciasNormasEmpleados.class, secuencia);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<VigenciasNormasEmpleados> buscarVigenciasNormasEmpleadosEmpl(EntityManager em, BigInteger secuencia) {
        try {
            Query query = em.createQuery("SELECT vne FROM VigenciasNormasEmpleados vne WHERE vne.empleado.secuencia = :secuenciaEmpl ORDER BY vne.fechavigencia DESC");
            query.setParameter("secuenciaEmpl", secuencia);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<VigenciasNormasEmpleados> vigenciasNormasEmpleados = query.getResultList();
            return vigenciasNormasEmpleados;
        } catch (Exception e) {
            System.out.println("Error en Persistencia Vigencias Normas Empleados " + e);
            return null;
        }
    }

    @Override
    public List<VigenciasNormasEmpleados> buscarVigenciasNormasEmpleados(EntityManager em) {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(VigenciasNormasEmpleados.class));
        return em.createQuery(cq).getResultList();
    }
}
