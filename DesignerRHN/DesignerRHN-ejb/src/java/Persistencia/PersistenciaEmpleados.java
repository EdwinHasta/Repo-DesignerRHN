/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Persistencia;

import Entidades.Empleados;
import InterfacePersistencia.PersistenciaEmpleadoInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Clase Stateless. <br>
 * Clase encargada de realizar operaciones sobre la tabla 'Empleados' de la base
 * de datos.
 *
 * @author betelgeuse
 */
@Stateless
public class PersistenciaEmpleados implements PersistenciaEmpleadoInterface {

    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    @Override
    public void crear(Empleados empleados) {
        em.persist(empleados);
    }

    @Override
    public void editar(Empleados empleados) {
        em.merge(empleados);
    }

    @Override
    public void borrar(Empleados empleados) {
        em.remove(em.merge(empleados));
    }

    @Override
    public Empleados buscarEmpleado(BigInteger secuencia) {
        try {
            return em.find(Empleados.class, secuencia);
        } catch (Exception e) {
            return null;
        }

    }

    @Override
    public List<Empleados> buscarEmpleados() {
        List<Empleados> empleadosLista = (List<Empleados>) em.createNamedQuery("Empleados.findAll").getResultList();
        return empleadosLista;
    }

    @Override
    public List<Empleados> todosEmpleados() {
        try {
            Query query = em.createQuery("SELECT e FROM Empleados e ORDER BY e.codigoempleado ASC");
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
            System.err.println("Error PersistenciaEmpleados.buscarEmpleadoSecuencia " + e);
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
            return resultado > 0;
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

    @Override
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

    public List<Empleados> lovEmpleadosParametros() {
        try {
            Query query = em.createQuery("SELECT e FROM Empleados e WHERE EXISTS (SELECT vtt FROM VWActualesTiposTrabajadores vtt WHERE vtt.empleado.secuencia = e.secuencia AND vtt.tipoTrabajador.tipo IN ('ACTIVO','PENSIONADO')) ORDER BY e.persona.primerapellido");
            List<Empleados> listaEmpleados = query.getResultList();
            return listaEmpleados;
        } catch (Exception e) {
            System.out.println("Exepcion en PersistenciaEmpleados.lovEmpleadosParametros" + e);
            return null;
        }
    }

    @Override
    public List<Empleados> empleadosAuxilios() {
        try {
            String sqlQuery = "select * from empleados v where EXISTS (SELECT 'X'\n"
                    + "       from   VWACTUALESTIPOSTRABAJADORES        vtt, tipostrabajadores  tt\n"
                    + "       where tt.secuencia = vtt.tipotrabajador\n"
                    + "       and   vtt.empleado = v.secuencia\n"
                    + "              and tt.tipo='ACTIVO')";
            Query query = em.createNativeQuery(sqlQuery, Empleados.class);
            List<Empleados> listaEmpleados = query.getResultList();
            return listaEmpleados;
        } catch (Exception e) {
            System.out.println("Exepcion en PersistenciaEmpleados.empleadosAuxilios" + e);
            return null;
        }
    }
}
