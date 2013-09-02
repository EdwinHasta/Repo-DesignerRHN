package Persistencia;

import Entidades.Empleados;
import InterfacePersistencia.PersistenciaEmpleadoInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class PersistenciaEmpleados implements PersistenciaEmpleadoInterface {

    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    /*
     * Crear empleado.
     */
    public void crear(Empleados empleados) {
        em.persist(empleados);
    }

    /*
     *Editar empleado. 
     */
    public void editar(Empleados empleados) {
        em.merge(empleados);
    }

    /*
     *Borrar empleado.
     */
    public void borrar(Empleados empleados) {
        em.remove(em.merge(empleados));
    }

    /*
     *Encontrar un empleado. 
     */
    public Empleados buscarEmpleado(Object id) {
        try {
            BigInteger secuencia = new BigInteger(id.toString());
            //return em.find(Empleados.class, id);
            return em.find(Empleados.class, secuencia);
        } catch (Exception e) {
            return null;
        }

    }

    /*
     *Encontrar todos los empleados. 
     */
    public List<Empleados> buscarEmpleados() {

        //javax.persistence.criteria.CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        /*CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
         cq.select(cq.from(Empleados.class));
         return em.createQuery(cq).getResultList();
         */
        List<Empleados> empleadosLista = (List<Empleados>) em.createNamedQuery("Empleados.findAll")
                .getResultList();
        return empleadosLista;
    }

    public Empleados buscarEmpleadoSecuencia(BigInteger secuencia) {

        try {
            Query query = em.createQuery("SELECT e FROM Empleados e WHERE e.secuencia = :secuencia");
            query.setParameter("secuencia", secuencia);
            Empleados empleado = (Empleados) query.getSingleResult();
            return empleado;
        } catch (Exception e) {
        }
        Empleados empleado = null;
        return empleado;
    }

    public boolean verificarCodigoEmpleado_Empresa(BigInteger codigoEmpleado, BigInteger secEmpresa) {
        try {
            Query query = em.createQuery("SELECT COUNT(e) FROM Empleados e WHERE e.codigoempleado = :codigo AND e.empresa.secuencia = :secEmpresa");
            query.setParameter("codigo", codigoEmpleado);
            query.setParameter("secEmpresa", secEmpresa);
            Long resultado = (Long) query.getSingleResult();
            if (resultado > 0) {
                return true;
            }
            return false;
        } catch (Exception e) {
            System.out.println("Exepcion: " + e);
            return false;
        }
    }

    public Empleados buscarEmpleadoCodigo_Empresa(BigInteger codigoEmpleado, BigInteger secEmpresa) {
        try {
            Query query = em.createQuery("SELECT e FROM Empleados e WHERE e.codigoempleado = :codigoE AND e.empresa.secuencia = :secEmpresa");
            query.setParameter("codigoE", codigoEmpleado);
            query.setParameter("secEmpresa", secEmpresa);
            Empleados empleado = (Empleados) query.getSingleResult();
            return empleado;
        } catch (Exception e) {
            System.out.println("Exepcion en PersistenciaEmpleados.buscarEmpleadoCodigo");
            return null;
        }
    }

    public Empleados buscarEmpleadoTipo(BigInteger codigoEmpleado) {
        try {
            Query query = em.createQuery("SELECT e FROM Empleados e WHERE e.codigoempleado = :codigoE");
            query.setParameter("codigoE", codigoEmpleado);
            Empleados empleado = (Empleados) query.getSingleResult();
            return empleado;
        } catch (Exception e) {
            System.out.println("Exepcion en PersistenciaEmpleados.buscarEmpleadoCodigo");
            return null;
        }
    }

    public Empleados buscarEmpleadoCodigo(BigInteger codigoEmpleado) {
        try {
            Query query = em.createQuery("SELECT e FROM Empleados e WHERE e.codigoempleado = :codigoE");
            query.setParameter("codigoE", codigoEmpleado);
            Empleados empleado = (Empleados) query.getSingleResult();
            return empleado;
        } catch (Exception e) {
            System.out.println("Exepcion en PersistenciaEmpleados.buscarEmpleadoCodigo");
            return null;
        }
    }
}
