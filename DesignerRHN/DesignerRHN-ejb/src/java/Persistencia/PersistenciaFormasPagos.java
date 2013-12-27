/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Persistencia;

import InterfacePersistencia.PersistenciaFormasPagosInterface;
import Entidades.FormasPagos;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;

/**
 * Clase Stateless 
 * Clase encargada de realizar operaciones sobre la tabla 'FormasPagos'
 * de la base de datos.
 * @author betelgeuse
 */
@Stateless
public class PersistenciaFormasPagos implements PersistenciaFormasPagosInterface {
    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos
     */
    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    @Override
    public void crear(FormasPagos formasPagos) {
        em.persist(formasPagos);
    }

    @Override
    public void editar(FormasPagos formasPagos) {
        em.merge(formasPagos);
    }

    @Override
    public void borrar(FormasPagos formasPagos) {
        em.remove(em.merge(formasPagos));
    }

    @Override
    public FormasPagos buscarFormasPagos(BigInteger secuencia) {
        try {
            return em.find(FormasPagos.class, secuencia);
        } catch (Exception e) {
            System.out.println("Error en la persistencia vigencias formas pagos ERROR : "+e);
            return null;
        }
    }

    @Override
    public List<FormasPagos> buscarFormasPagosPorEmpleado(BigInteger secEmpleado) {
        try {
            Query query = em.createQuery("SELECT vfp FROM VigenciasFormasPagos vfp WHERE vfp.empleado.secuencia = :secuenciaEmpl ORDER BY vfp.fechavigencia DESC");
            query.setParameter("secuenciaEmpl", secEmpleado);

            List<FormasPagos> vigenciasNormasEmpleados = query.getResultList();
            return vigenciasNormasEmpleados;
        } catch (Exception e) {
            System.out.println("Error en Persistencia Vigencias Formas Pagos Por Empleados " + e);
            return null;
        }
    }
    
    @Override
    public List<FormasPagos> buscarFormasPagos() {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(FormasPagos.class));
        return em.createQuery(cq).getResultList();
    }

}
