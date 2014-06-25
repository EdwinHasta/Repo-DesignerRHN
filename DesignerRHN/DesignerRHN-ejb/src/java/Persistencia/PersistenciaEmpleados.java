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
import javax.persistence.EntityTransaction;
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

    /*@PersistenceContext(unitName = "DesignerRHN-ejbPU")
     private EntityManager em;*/
    @Override
    public void crear(EntityManager em, Empleados empleados) {
        em.clear();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            System.out.println("Empleado Persona Nombre : " + empleados.getPersona().getNombre());
            System.out.println("Empleado Persona Secuencia : " + empleados.getPersona().getSecuencia());
            em.merge(empleados);
            tx.commit();
        } catch (Exception e) {
            System.out.println("Error PersistenciaEmpleados.crear: " + e);
            if (tx.isActive()) {
                tx.rollback();
            }
        }
    }

    @Override
    public void editar(EntityManager em, Empleados empleados) {

        em.clear();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(empleados);
            tx.commit();
        } catch (Exception e) {
            System.out.println("Error PersistenciaEmpleados.editar: " + e);
            if (tx.isActive()) {
                tx.rollback();
            }
        }
    }

    @Override
    public void borrar(EntityManager em, Empleados empleados) {
        em.clear();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.remove(em.merge(empleados));
            tx.commit();

        } catch (Exception e) {
            try {
                if (tx.isActive()) {
                    tx.rollback();
                }
            } catch (Exception ex) {
                System.out.println("Error PersistenciaEmpleados.borrar: " + e);
            }
        }
    }

    @Override
    public Empleados buscarEmpleado(EntityManager em, BigInteger secuencia) {
        try {
            return em.find(Empleados.class, secuencia);
        } catch (Exception e) {
            return null;
        }

    }

    @Override
    public List<Empleados> buscarEmpleados(EntityManager em) {
        List<Empleados> empleadosLista = (List<Empleados>) em.createNamedQuery("Empleados.findAll").getResultList();
        return empleadosLista;
    }

    @Override
    public List<Empleados> todosEmpleados(EntityManager em) {
        try {
            Query query = em.createQuery("SELECT e FROM Empleados e ORDER BY e.codigoempleado ASC");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<Empleados> listaEmpleados = query.getResultList();
            return listaEmpleados;
        } catch (Exception e) {
            System.out.println("Error PersistenciaEmpleados.todosEmpleados" + e);
            return null;
        }

    }

    public List<Empleados> consultarEmpleadosLiquidacionesLog(EntityManager em) {
        try {
            Query query = em.createQuery("SELECT e FROM Empleados e WHERE EXISTS (SELECT li.secuencia FROM LiquidacionesLogs li WHERE li.empleado.secuencia = e.secuencia) ORDER BY e.codigoempleado ASC");

            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<Empleados> listaEmpleados = query.getResultList();
            return listaEmpleados;
        } catch (Exception e) {
            System.out.println("Error PersistenciaEmpleados.todosEmpleados" + e);
            return null;
        }

    }

    @Override
    public Empleados buscarEmpleadoSecuencia(EntityManager em, BigInteger secuencia) {
        try {
            em.clear();
            Query query = em.createQuery("SELECT e FROM Empleados e WHERE e.secuencia = :secuencia");
            query.setParameter("secuencia", secuencia);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            Empleados empleado = (Empleados) query.getSingleResult();
            return empleado;
        } catch (Exception e) {
            System.err.println("Error PersistenciaEmpleados.buscarEmpleadoSecuencia " + e);
            return null;
        }
    }

    public Empleados buscarEmpleadoSecuenciaPersona(EntityManager em, BigInteger secuencia) {
        try {
            em.clear();
            Query query = em.createQuery("SELECT e FROM Empleados e WHERE e.secuencia = :secuencia");
            query.setParameter("secuencia", secuencia);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            Empleados empleado = (Empleados) query.getSingleResult();
            return empleado;
        } catch (Exception e) {
            System.err.println("Error PersistenciaEmpleados.buscarEmpleadoSecuenciaPersona " + e);
            return null;
        }
    }

    @Override
    public boolean verificarCodigoEmpleado_Empresa(EntityManager em, BigInteger codigoEmpleado, BigInteger secEmpresa) {
        try {
            Query query = em.createQuery("SELECT COUNT(e) FROM Empleados e WHERE e.codigoempleado = :codigo AND e.empresa.secuencia = :secEmpresa");
            query.setParameter("codigo", codigoEmpleado);
            query.setParameter("secEmpresa", secEmpresa);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            Long resultado = (Long) query.getSingleResult();
            return resultado > 0;
        } catch (Exception e) {
            System.out.println("Exepcion: " + e);
            return false;
        }
    }

    @Override
    public Empleados buscarEmpleadoCodigo_Empresa(EntityManager em, BigInteger codigoEmpleado, BigInteger secEmpresa) {
        try {
            Query query = em.createQuery("SELECT e FROM Empleados e WHERE e.codigoempleado = :codigoE AND e.empresa.secuencia = :secEmpresa");
            query.setParameter("codigoE", codigoEmpleado);
            query.setParameter("secEmpresa", secEmpresa);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            Empleados empleado = (Empleados) query.getSingleResult();
            return empleado;
        } catch (Exception e) {
            System.out.println("Exepcion en PersistenciaEmpleados.buscarEmpleadoCodigo");
            return null;
        }
    }

    @Override
    public Empleados buscarEmpleadoTipo(EntityManager em, BigInteger codigoEmpleado) {
        try {
            Query query = em.createQuery("SELECT e FROM Empleados e WHERE e.codigoempleado = :codigoE");
            query.setParameter("codigoE", codigoEmpleado);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            Empleados empleado = (Empleados) query.getSingleResult();
            return empleado;
        } catch (Exception e) {
            System.out.println("Exepcion en PersistenciaEmpleados.buscarEmpleadoCodigo");
            return null;
        }
    }

    @Override
    public Empleados buscarEmpleadoCodigo(EntityManager em, BigInteger codigoEmpleado) {
        try {
            Query query = em.createQuery("SELECT e FROM Empleados e WHERE e.codigoempleado = :codigoE");
            query.setParameter("codigoE", codigoEmpleado);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            Empleados empleado = (Empleados) query.getSingleResult();
            return empleado;
        } catch (Exception e) {
            System.out.println("Exepcion en PersistenciaEmpleados.buscarEmpleadoCodigo");
            return null;
        }
    }

    @Override
    public List<Empleados> empleadosComprobantes(EntityManager em, String usuarioBD) {
        try {
            Query query = em.createQuery("SELECT e FROM Empleados e WHERE EXISTS (SELECT 1 FROM Parametros p ,ParametrosInstancias pi, UsuariosInstancias ui, Usuarios u WHERE p.empleado = e.secuencia and p.secuencia = pi.parametro and pi.instancia = ui.instancia and ui.usuario = u.secuencia and u.alias = :usuarioBD)");
            query.setParameter("usuarioBD", usuarioBD);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<Empleados> listaEmpleados = query.getResultList();
            return listaEmpleados;
        } catch (Exception e) {
            System.out.println("Exepcion en PersistenciaEmpleados.empleadosComprobantes" + e);
            return null;
        }
    }

    @Override
    public List<Empleados> empleadosNovedad(EntityManager em) {
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
    public List<Empleados> empleadosVacaciones(EntityManager em) {
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

    public List<Empleados> lovEmpleadosParametros(EntityManager em) {
        try {
            Query query = em.createQuery("SELECT e FROM Empleados e WHERE EXISTS (SELECT vtt FROM VWActualesTiposTrabajadores vtt WHERE vtt.empleado.secuencia = e.secuencia AND vtt.tipoTrabajador.tipo IN ('ACTIVO','PENSIONADO')) ORDER BY e.persona.primerapellido");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<Empleados> listaEmpleados = query.getResultList();
            return listaEmpleados;
        } catch (Exception e) {
            System.out.println("Exepcion en PersistenciaEmpleados.lovEmpleadosParametros" + e);
            return null;
        }
    }

    @Override
    public List<Empleados> empleadosAuxilios(EntityManager em) {
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

    @Override
    public List<Empleados> empleadosNovedadEmbargo(EntityManager em) {
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
            System.out.println("Exepcion en PersistenciaEmpleados.empleadosNovedad" + e);
            return null;
        }
    }

    @Override
    public List<Empleados> buscarEmpleadosBusquedaAvanzada(EntityManager em, String queryBusquedaAvanzada) {
        try {
            Query query = em.createNativeQuery(queryBusquedaAvanzada, Empleados.class);
            List<Empleados> empleado = query.getResultList();
            return empleado;
        } catch (Exception e) {
            System.out.println("Excepcion en PersistenciaEmpleados.buscarEmpleadosBusquedaAvanzada : " + e.toString());
            return null;
        }
    }

    @Override
    public List<BigInteger> buscarEmpleadosBusquedaAvanzadaCodigo(EntityManager em, String queryBusquedaAvanzada) {
        try {
            Query query = em.createNativeQuery(queryBusquedaAvanzada);
            List<BigInteger> empleado = query.getResultList();
            return empleado;
        } catch (Exception e) {
            System.out.println("Excepcion en PersistenciaEmpleados.buscarEmpleadosBusquedaAvanzadaCodigo : " + e.toString());
            return null;
        }
    }

    @Override
    public Empleados buscarEmpleadoPorCodigoyEmpresa(EntityManager em, BigInteger codigo, BigInteger empresa) {
        try {
            String sql = "SELECT * FROM empleados WHERE CODIGOEMPLEADO =? AND NVL(EMPRESA,?)=?";
            Query query = em.createNativeQuery(sql, Empleados.class);
            query.setParameter(1, codigo);
            query.setParameter(2, empresa);
            query.setParameter(3, empresa);
            Empleados empl = (Empleados) query.getSingleResult();
            return empl;
        } catch (Exception e) {
            System.out.println("Error buscarEmpleadoPorCodigoyEmpresa PersistenciaEmpleados : " + e.toString());
            return null;
        }
    }

    @Override
    public Empleados obtenerUltimoEmpleadoAlmacenado(EntityManager em, BigInteger secuenciaEmpresa, BigInteger codigoEmpleado) {
        try {
            Query query = em.createQuery("SELECT e FROM Empleados e WHERE e.empresa.secuencia=:secuenciaEmpresa AND e.codigoempleado=:codigoEmpleado");
            query.setParameter("secuenciaEmpresa", secuenciaEmpresa);
            query.setParameter("codigoEmpleado", codigoEmpleado);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            Empleados empl = (Empleados) query.getSingleResult();
            return empl;
        } catch (Exception e) {
            System.out.println("Error obtenerUltimoEmpleadoAlmacenado PersistenciaEmpleados : " + e.toString());
            return null;
        }
    }
}
