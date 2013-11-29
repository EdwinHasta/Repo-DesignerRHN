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
    @Override
    public void crear(Empleados empleados) {
        em.persist(empleados);
    }

    /*
     *Editar empleado. 
     */
    @Override
    public void editar(Empleados empleados) {
        em.merge(empleados);
    }

    /*
     *Borrar empleado.
     */
    @Override
    public void borrar(Empleados empleados) {
        em.remove(em.merge(empleados));
    }

    /*
     *Encontrar un empleado. 
     */
    @Override
    public Empleados buscarEmpleado(BigInteger id) {
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
    @Override
    public List<Empleados> buscarEmpleados() {
        //javax.persistence.criteria.CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        /*CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
         cq.select(cq.from(Empleados.class));
         return em.createQuery(cq).getResultList();
         */
        List<Empleados> empleadosLista = (List<Empleados>) em.createNamedQuery("Empleados.findAll").getResultList();
        return empleadosLista;
    }

    public List<Empleados> todosEmpleados() {
        try {
            Query query = em.createQuery("SELECT e FROM Empleados e ORDER BY e.codigoempleado");
            List<Empleados> listaEmpleados = query.getResultList();
            return listaEmpleados;
        } catch (Exception e) {
            System.out.println("Error PersistenciaEmpleados.todosEmpleados" + e);
            return null;
        }

    }

    @Override
    public Empleados buscarEmpleadoSecuencia(BigInteger secuencia) {
        try {
            Query query = em.createQuery("SELECT e FROM Empleados e WHERE e.secuencia = :secuencia");
            query.setParameter("secuencia", secuencia);
            Empleados empleado = (Empleados) query.getSingleResult();
            return empleado;
        } catch (Exception e) {
            System.out.println("Error PersistenciaEmpleados.buscarEmpleadoSecuencia " + e);
            return null;
        }
    }

    @Override
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

    @Override
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

    @Override
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

    @Override
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

    @Override
    public List<Empleados> empleadosComprobantes(String usuarioBD) {
        try {
            Query query = em.createQuery("SELECT e FROM Empleados e WHERE EXISTS (SELECT 1 FROM Parametros p ,ParametrosInstancias pi, UsuariosInstancias ui, Usuarios u WHERE p.empleado = e.secuencia and p.secuencia = pi.parametro and pi.instancia = ui.instancia and ui.usuario = u.secuencia and u.alias = :usuarioBD)");
            query.setParameter("usuarioBD", usuarioBD);
            List<Empleados> listaEmpleados = query.getResultList();
            return listaEmpleados;
        } catch (Exception e) {
            System.out.println("Exepcion en PersistenciaEmpleados.empleadosComprobantes" + e);
            return null;
        }
    }

    @Override
    public List<Empleados> empleadosNovedad() {
        try {
            String sqlQuery = "select * from empleados v where EXISTS (SELECT 'X' from  VWACTUALESTIPOSTRABAJADORES vtt, tipostrabajadores  tt\n"
                    + "where tt.secuencia = vtt.tipotrabajador\n"
                    + "and   vtt.empleado = v.secuencia\n"
                    + "and tt.tipo IN ('ACTIVO','PENSIONADO','RETIRADO'))";
            Query query = em.createNativeQuery(sqlQuery, Empleados.class);
            List<Empleados> listaEmpleados = query.getResultList();
            return listaEmpleados;
        } catch (Exception e) {
            System.out.println("Exepcion en PersistenciaEmpleados.empleadosNovedad" + e);
            return null;
        }
    }

    public List<Empleados> empleadosVacaciones() {
        try {
            String sqlQuery = "select * from empleados v where EXISTS (SELECT 'X' from  VWACTUALESTIPOSTRABAJADORES vtt, tipostrabajadores  tt\n"
                    + "where tt.secuencia = vtt.tipotrabajador\n"
                    + "and vtt.empleado = v.secuencia\n"
                    + "and tt.tipo IN ('ACTIVO'))";
            Query query = em.createNativeQuery(sqlQuery, Empleados.class);
            List<Empleados> listaEmpleados = query.getResultList();
            return listaEmpleados;
        } catch (Exception e) {
            System.out.println("Exepcion en PersistenciaEmpleados.empleadosVacaciones" + e);
            return null;
        }
    }
}
