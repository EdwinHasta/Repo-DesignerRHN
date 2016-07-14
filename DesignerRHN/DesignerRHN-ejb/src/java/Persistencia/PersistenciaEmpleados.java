/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Persistencia;

import Entidades.Empleados;
import InterfacePersistencia.PersistenciaEmpleadoInterface;
import java.math.BigInteger;
//import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import org.omg.CORBA.INTERNAL;
//import org.apache.log4j.Logger;
//import org.apache.log4j.PropertyConfigurator;

/**
 * Clase Stateless. <br> Clase encargada de realizar operaciones sobre la tabla
 * 'Empleados' de la base de datos.
 *
 * @author betelgeuse
 */
@Stateless
public class PersistenciaEmpleados implements PersistenciaEmpleadoInterface {

    //private final static Logger logger = Logger.getLogger("connectionSout");
    //private Date fechaDia;
    //private final SimpleDateFormat format = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");

    /*
     * @PersistenceContext(unitName = "DesignerRHN-ejbPU") private EntityManager
     * em;
     */
    @Override
    public void crear(EntityManager em, Empleados empleados) {
        em.clear();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(empleados);
            tx.commit();
            tx.begin();
            System.out.println("empleados : " + empleados);
//            empleados = em.find(Empleados.class, empleados);
//            System.out.println("empleados : " + empleados);
            tx.commit();
            System.out.println("PersistenciaEmpleados crear() se supone creo el empleado: " + empleados.getSecuencia() + ", persona: " + empleados.getPersona() + ", nombre: " + empleados.getPersona().getNombre());
        } catch (Exception e) {
            //PropertyConfigurator.configure("log4j.properties");
            //logger.error("Metodo: crear - PersistenciaEmpleados - Fecha : " + format.format(fechaDia) + " - Error : " + e.toString());
            System.out.println(this.getClass().getName()+".crear()");
            System.out.println("error al crear el empleado");
            e.printStackTrace();
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
            //PropertyConfigurator.configure("log4j.properties");
            //logger.error("PersistenciaEmpleados.editar" + format.format(fechaDia) + " - Error : " + e.toString());
            System.out.println(this.getClass().getName() + ".editar() error " + e.toString());
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
            //PropertyConfigurator.configure("log4j.properties");
            //logger.error("Metodo: borrar - PersistenciaEmpleados - Fecha : " + format.format(fechaDia) + " - Error : " + e.toString());
            System.out.println(this.getClass().getName() + ".borrar() error " + e.toString());
            if (tx.isActive()) {
                tx.rollback();
            }
        }
    }

    @Override
    public Empleados buscarEmpleado(EntityManager em, BigInteger secuencia) {
        try {
            em.clear();
            return em.find(Empleados.class, secuencia);
        } catch (Exception e) {
            //PropertyConfigurator.configure("log4j.properties");
            //logger.error("Metodo: buscarEmpleado - PersistenciaEmpleados - Fecha : " + format.format(fechaDia) + " - Error : " + e.toString());
            System.out.println(this.getClass().getName() + ".buscarEmpleado() error " + e.toString());
            return null;
        }
    }

    /*
     * @Override public List<Empleados> buscarEmpleados(EntityManager em) {
     * System.out.println(this.getClass().getName() + ".buscarEmpleados()");
     * em.clear(); List<Empleados> empleadosLista = (List<Empleados>)
     * em.createQuery("SELECT e FROM Empleados e").getResultList(); return
     * empleadosLista; }
     */
    @Override
    public List<Empleados> buscarEmpleados(EntityManager em) {
        System.out.println(this.getClass().getName() + ".buscarEmpleados()");
        List<Empleados> listaEmpleados = null;
        try {
            em.clear();
            String sqlQuery = "select "
                    + "SECUENCIA, "
                    + "CODIGOEMPLEADO, "
                    + "RUTATRANSPORTE, "
                    + "PARQUEADERO, "
                    + "FECHACREACION, "
                    + "CODIGOALTERNATIVO, "
                    + "PAGASUBSIDIOTRANSPORTELEGAL, "
                    + "PERSONA, "
                    + "EMPRESA, "
                    + "USUARIOBD "
                    + "from empleados e";
            Query query = em.createNativeQuery(sqlQuery, Empleados.class);
            listaEmpleados = query.getResultList();
            return listaEmpleados;
        } catch (Exception e) {
            System.out.println(this.getClass().getName() + " error en buscarEmpleados()");
            e.printStackTrace();
            return listaEmpleados;
        }
    }

    @Override
    public List<Empleados> todosEmpleados(EntityManager em) {
        try {
            em.clear();
            Query query = em.createQuery("SELECT e FROM Empleados e ORDER BY e.codigoempleado ASC");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<Empleados> listaEmpleados = query.getResultList();
            return listaEmpleados;
        } catch (Exception e) {
            //PropertyConfigurator.configure("log4j.properties");
            //logger.error("Metodo: todosEmpleados - PersistenciaEmpleados - Fecha : " + format.format(fechaDia) + " - Error : " + e.toString());
            return null;
        }

    }

    public List<Empleados> consultarEmpleadosLiquidacionesLog(EntityManager em) {
        try {
            em.clear();
            Query query = em.createQuery("SELECT e FROM Empleados e WHERE EXISTS (SELECT li.secuencia FROM LiquidacionesLogs li WHERE li.empleado.secuencia = e.secuencia) ORDER BY e.codigoempleado ASC");

            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<Empleados> listaEmpleados = query.getResultList();
            return listaEmpleados;
        } catch (Exception e) {
            //PropertyConfigurator.configure("log4j.properties");
            //logger.error("Metodo: consultarEmpleadosLiquidacionesLog - PersistenciaEmpleados - Fecha : " + format.format(fechaDia) + " - Error : " + e.toString());
            return null;
        }

    }

    @Override
    public Empleados buscarEmpleadoSecuencia(EntityManager em, BigInteger secuencia) {
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

    public Empleados buscarEmpleadoSecuenciaPersona(EntityManager em, BigInteger secuencia) {
        try {
            em.clear();
            Query query = em.createQuery("SELECT e FROM Empleados e WHERE e.secuencia = :secuencia");
            query.setParameter("secuencia", secuencia);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            Empleados empleado = (Empleados) query.getSingleResult();
            return empleado;
        } catch (Exception e) {
            //PropertyConfigurator.configure("log4j.properties");
            //logger.error("Metodo: buscarEmpleadoSecuenciaPersona - PersistenciaEmpleados - Fecha : " + format.format(fechaDia) + " - Error : " + e.toString());
            return null;
        }
    }

    @Override
    public boolean verificarCodigoEmpleado_Empresa(EntityManager em, BigInteger codigoEmpleado, BigInteger secEmpresa) {
        try {
            em.clear();
            //Query query = em.createQuery("SELECT COUNT(e) FROM Empleados e WHERE e.codigoempleado = :codigo AND e.empresa.secuencia = :secEmpresa");
            Query query = em.createQuery("SELECT COUNT(e) FROM Empleados e WHERE e.codigoempleado = :codigo AND e.empresa = :secEmpresa");
            query.setParameter("codigo", codigoEmpleado);
            query.setParameter("secEmpresa", secEmpresa);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            Long resultado = (Long) query.getSingleResult();
            return resultado > 0;
        } catch (Exception e) {
            //PropertyConfigurator.configure("log4j.properties");
            //logger.error("Metodo: verificarCodigoEmpleado_Empresa - PersistenciaEmpleados - Fecha : " + format.format(fechaDia) + " - Error : " + e.toString());
            return false;
        }
    }

    @Override
    public Empleados buscarEmpleadoCodigo_Empresa(EntityManager em, BigInteger codigoEmpleado, BigInteger secEmpresa) {
        try {
            em.clear();
            //Query query = em.createQuery("SELECT e FROM Empleados e WHERE e.codigoempleado = :codigoE AND e.empresa.secuencia = :secEmpresa");
            Query query = em.createQuery("SELECT e FROM Empleados e WHERE e.codigoempleado = :codigoE AND e.empresa = :secEmpresa");
            query.setParameter("codigoE", codigoEmpleado);
            query.setParameter("secEmpresa", secEmpresa);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            Empleados empleado = (Empleados) query.getSingleResult();
            return empleado;
        } catch (Exception e) {
            //PropertyConfigurator.configure("log4j.properties");
            //logger.error("Metodo: buscarEmpleadoCodigo_Empresa - PersistenciaEmpleados - Fecha : " + format.format(fechaDia) + " - Error : " + e.toString());
            return null;
        }
    }

    @Override
    public Empleados buscarEmpleadoTipo(EntityManager em, BigInteger codigoEmpleado) {
        try {
            em.clear();
            Query query = em.createQuery("SELECT e FROM Empleados e WHERE e.codigoempleado = :codigoE");
            query.setParameter("codigoE", codigoEmpleado);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            Empleados empleado = (Empleados) query.getSingleResult();
            return empleado;
        } catch (Exception e) {
            //PropertyConfigurator.configure("log4j.properties");
            //logger.error("Metodo: buscarEmpleadoTipo - PersistenciaEmpleados - Fecha : " + format.format(fechaDia) + " - Error : " + e.toString());
            return null;
        }
    }

    @Override
    public Empleados buscarEmpleadoCodigo(EntityManager em, BigInteger codigoEmpleado) {
        try {
            em.clear();
            Query query = em.createQuery("SELECT e FROM Empleados e WHERE e.codigoempleado = :codigoE");
            query.setParameter("codigoE", codigoEmpleado);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            Empleados empleado = (Empleados) query.getSingleResult();
            return empleado;
        } catch (Exception e) {
            //PropertyConfigurator.configure("log4j.properties");
            //logger.error("Metodo: buscarEmpleadoCodigo - PersistenciaEmpleados - Fecha : " + format.format(fechaDia) + " - Error : " + e.toString());
            return null;
        }
    }

    @Override
    public List<Empleados> empleadosComprobantes(EntityManager em, String usuarioBD) {
        try {
            em.clear();
            Query query = em.createQuery("SELECT e FROM Empleados e WHERE EXISTS (SELECT 1 FROM Parametros p ,ParametrosInstancias pi, UsuariosInstancias ui, Usuarios u WHERE p.empleado = e.secuencia and p.secuencia = pi.parametro and pi.instancia = ui.instancia and ui.usuario = u.secuencia and u.alias = :usuarioBD)");
            query.setParameter("usuarioBD", usuarioBD);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<Empleados> listaEmpleados = query.getResultList();
            return listaEmpleados;
        } catch (Exception e) {
            //PropertyConfigurator.configure("log4j.properties");
            //logger.error("Metodo: empleadosComprobantes - PersistenciaEmpleados - Fecha : " + format.format(fechaDia) + " - Error : " + e.toString());
            return null;
        }
    }

    @Override
    public List<Empleados> empleadosNovedad(EntityManager em) {
        try {
            em.clear();
            String sqlQuery = "select "
                    + "SECUENCIA, "
                    + "CODIGOEMPLEADO, "
                    + "RUTATRANSPORTE, "
                    + "PARQUEADERO, "
                    + "FECHACREACION, "
                    + "CODIGOALTERNATIVO, "
                    + "PAGASUBSIDIOTRANSPORTELEGAL, "
                    + "PERSONA, "
                    + "EMPRESA, "
                    + "USUARIOBD "
                    + "from empleados v where EXISTS (SELECT 'X' from  VWACTUALESTIPOSTRABAJADORES vtt, tipostrabajadores  tt\n"
                    + "where tt.secuencia = vtt.tipotrabajador\n"
                    + "and   vtt.empleado = v.secuencia\n"
                    + "and tt.tipo IN ('ACTIVO','PENSIONADO','RETIRADO'))";
            Query query = em.createNativeQuery(sqlQuery, Empleados.class);
            List<Empleados> listaEmpleados = query.getResultList();
            return listaEmpleados;
        } catch (Exception e) {
            //PropertyConfigurator.configure("log4j.properties");
            //logger.error("Metodo: empleadosNovedad - PersistenciaEmpleados - Fecha : " + format.format(fechaDia) + " - Error : " + e.toString());
            return null;
        }
    }

    @Override
    public int contarEmpleadosNovedad(EntityManager em) {
        try {
            em.clear();
            String sqlQuery = "select count(*) \n"
                    + "from empleados v where EXISTS (SELECT 'X' from  VWACTUALESTIPOSTRABAJADORES vtt, tipostrabajadores  tt\n"
                    + "where tt.secuencia = vtt.tipotrabajador\n"
                    + "and   vtt.empleado = v.secuencia\n"
                    + "and tt.tipo IN ('ACTIVO','PENSIONADO','RETIRADO'))";
            Query query = em.createNativeQuery(sqlQuery);
            Long resultado = new Long(query.getSingleResult().toString());
            System.out.println("contarEmpleadosNovedad resultado : " + resultado);

            int N = new Integer(query.getSingleResult().toString());
            System.out.println("contarEmpleadosNovedad retorno : " + N);
            return N;
        } catch (Exception e) {
            System.out.println("Error contarEmpleadosNovedad() : " + e);
            return -1;
        }
    }

    @Override
    public List<Empleados> empleadosNovedadSoloAlgunos(EntityManager em) {
        try {
            em.clear();
            String sqlQuery = "select "
                    + "SECUENCIA, CODIGOEMPLEADO, RUTATRANSPORTE, PARQUEADERO, FECHACREACION, "
                    + "CODIGOALTERNATIVO, PAGASUBSIDIOTRANSPORTELEGAL, "
                    + "PERSONA, EMPRESA, USUARIOBD "
                    + "from empleados v where EXISTS (SELECT 'X' from  VWACTUALESTIPOSTRABAJADORES vtt, tipostrabajadores  tt\n"
                    + "where tt.secuencia = vtt.tipotrabajador\n"
                    + "and   vtt.empleado = v.secuencia\n"
                    + "and tt.tipo IN ('ACTIVO','PENSIONADO','RETIRADO')) and ROWNUM < 50";
            Query query = em.createNativeQuery(sqlQuery, Empleados.class);
            List<Empleados> listaEmpleados = query.getResultList();
            System.out.println("");
            return listaEmpleados;
        } catch (Exception e) {
            //PropertyConfigurator.configure("log4j.properties");
            //logger.error("Metodo: empleadosNovedad - PersistenciaEmpleados - Fecha : " + format.format(fechaDia) + " - Error : " + e.toString());
            return null;
        }
    }

    @Override
    public List<Empleados> empleadosVacaciones(EntityManager em) {
        try {
            em.clear();
            String sqlQuery = "select SECUENCIA, CODIGOEMPLEADO, RUTATRANSPORTE, TELEFONO,"
                    + "EXTENSION, PARQUEADERO, SERVICIORESTAURANTE, NIVELENDEUDAMIENTO, "
                    + "TOTALULTIMOPAGO, TOTALULTIMODESCUENTO, TOTALULTIMOSOBREGIRO, "
                    + "EXCLUIRLIQUIDACION, CODIGOALTERNATIVODEUDOR, CODIGOALTERNATIVOACREEDOR, "
                    + "FECHACREACION, CODIGOALTERNATIVO, TEMPTOTALINGRESOS, EXTRANJERO, "
                    + "PAGASUBSIDIOTRANSPORTELEGAL, TEMPBASERECALCULO, PERSONA, EMPRESA, USUARIOBD "
                    + "from empleados v where EXISTS (SELECT 'X' from  VWACTUALESTIPOSTRABAJADORES vtt, tipostrabajadores  tt\n"
                    + "where tt.secuencia = vtt.tipotrabajador\n"
                    + "and vtt.empleado = v.secuencia\n"
                    + "and tt.tipo IN ('ACTIVO'))";
            Query query = em.createNativeQuery(sqlQuery, Empleados.class);
            List<Empleados> listaEmpleados = query.getResultList();
            return listaEmpleados;
        } catch (Exception e) {
            //PropertyConfigurator.configure("log4j.properties");
            //logger.error("Metodo: empleadosVacaciones - PersistenciaEmpleados - Fecha : " + format.format(fechaDia) + " - Error : " + e.toString());
            return null;
        }
    }

    @Override
    public List<Empleados> lovEmpleadosParametros(EntityManager em) {
        try {
            em.clear();
            Query query = em.createQuery("SELECT e FROM Empleados e WHERE EXISTS (SELECT vtt FROM VWActualesTiposTrabajadores vtt WHERE vtt.empleado.secuencia = e.secuencia AND vtt.tipoTrabajador.tipo IN ('ACTIVO','PENSIONADO')) ORDER BY e.persona.primerapellido");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<Empleados> listaEmpleados = query.getResultList();
            return listaEmpleados;
        } catch (Exception e) {
            //PropertyConfigurator.configure("log4j.properties");
            //logger.error("Metodo: lovEmpleadosParametros - PersistenciaEmpleados - Fecha : " + format.format(fechaDia) + " - Error : " + e.toString());
            return null;
        }
    }

    @Override
    public List<Empleados> empleadosAuxilios(EntityManager em) {
        try {
            em.clear();
            String sqlQuery = "select * from empleados v where EXISTS (SELECT 'x' "
                    + "       from   VWACTUALESTIPOSTRABAJADORES        vtt, tipostrabajadores  tt "
                    + "       where tt.secuencia = vtt.tipotrabajador "
                    + "       and   vtt.empleado = v.secuencia "
                    + "              and tt.tipo='ACTIVO')";
            Query query = em.createNativeQuery(sqlQuery, Empleados.class);
            List<Empleados> listaEmpleados = query.getResultList();
            return listaEmpleados;
        } catch (Exception e) {
            //PropertyConfigurator.configure("log4j.properties");
            //logger.error("Metodo: empleadosAuxilios - PersistenciaEmpleados - Fecha : " + format.format(fechaDia) + " - Error : " + e.toString());
            return null;
        }
    }

    @Override
    public List<Empleados> empleadosNovedadEmbargo(EntityManager em) {
        try {
            em.clear();
            String sqlQuery = "select * from empleados v where EXISTS (SELECT 'X'\n"
                    + "       from   VWACTUALESTIPOSTRABAJADORES        vtt, tipostrabajadores  tt\n"
                    + "       where tt.secuencia = vtt.tipotrabajador\n"
                    + "       and   vtt.empleado = v.secuencia\n"
                    + "              and tt.tipo='ACTIVO')";
            Query query = em.createNativeQuery(sqlQuery, Empleados.class);
            List<Empleados> listaEmpleados = query.getResultList();
            return listaEmpleados;
        } catch (Exception e) {
            //PropertyConfigurator.configure("log4j.properties");
            //logger.error("Metodo: empleadosNovedadEmbargo - PersistenciaEmpleados - Fecha : " + format.format(fechaDia) + " - Error : " + e.toString());
            return null;
        }
    }

    @Override
    public List<Empleados> buscarEmpleadosBusquedaAvanzada(EntityManager em, String queryBusquedaAvanzada) {
        try {
            em.clear();
            Query query = em.createNativeQuery(queryBusquedaAvanzada, Empleados.class);
            List<Empleados> empleado = query.getResultList();
            return empleado;
        } catch (Exception e) {
            //PropertyConfigurator.configure("log4j.properties");
            //logger.error("Metodo: buscarEmpleadosBusquedaAvanzada - PersistenciaEmpleados - Fecha : " + format.format(fechaDia) + " - Error : " + e.toString());
            return null;
        }
    }

    @Override
    public List<BigInteger> buscarEmpleadosBusquedaAvanzadaCodigo(EntityManager em, String queryBusquedaAvanzada) {
        try {
            em.clear();
            Query query = em.createNativeQuery(queryBusquedaAvanzada);
            List<BigInteger> empleado = query.getResultList();
            return empleado;
        } catch (Exception e) {
            //          PropertyConfigurator.configure("log4j.properties");
//            //logger.error("Metodo: buscarEmpleadosBusquedaAvanzadaCodigo - PersistenciaEmpleados - Fecha : " + format.format(fechaDia) + " - Error : " + e.toString());
            return null;
        }
    }

    @Override
    public Empleados buscarEmpleadoPorCodigoyEmpresa(EntityManager em, BigInteger codigo, BigInteger empresa) {
        try {
            em.clear();
            
            String sql = "SELECT * FROM empleados WHERE CODIGOEMPLEADO =? AND NVL(EMPRESA,?)=?";
            Query query = em.createNativeQuery(sql, Empleados.class);
            query.setParameter(1, codigo);
            query.setParameter(2, empresa);
            query.setParameter(3, empresa);
            Empleados empl = (Empleados) query.getSingleResult();
            return empl;
        } catch (Exception e) {
//            PropertyConfigurator.configure("log4j.properties");
//            //logger.error("Metodo: buscarEmpleadoPorCodigoyEmpresa - PersistenciaEmpleados - Fecha : " + format.format(fechaDia) + " - Error : " + e.toString());
            return null;
        }
    }

    @Override
    public Empleados obtenerUltimoEmpleadoAlmacenado(EntityManager em, BigInteger secuenciaEmpresa, BigInteger codigoEmpleado) {
        try {
            System.out.println(this.getClass().getName() + "obtenerUltimoEmpleadoAlmacenado :");
            System.out.println("em" + em + ",  secuenciaEmpresa : " + secuenciaEmpresa + ",  codigoEmpleado : " + codigoEmpleado);
            em.clear();
            String sql = "SELECT * FROM EMPLEADOS WHERE EMPRESA = ? AND CODIGOEMPLEADO = ?";
            Query query = em.createNativeQuery(sql, Empleados.class);
            query.setParameter(1, secuenciaEmpresa);
            query.setParameter(2, codigoEmpleado);
            Empleados empl = (Empleados) query.getSingleResult();
            return empl;
//            em.clear();
//            Query query = em.createQuery("SELECT e FROM Empleados e WHERE e.empresa = :secuenciaEmpresa AND e.codigoempleado = :codigoEmpleado");
//            query.setParameter("secuenciaEmpresa", secuenciaEmpresa);
//            query.setParameter("codigoEmpleado", codigoEmpleado);
//            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
//            Empleados empl = (Empleados) query.getSingleResult();
//            return empl;
        } catch (Exception e) {
            System.out.println(this.getClass().getName() + " error en obtenerUltimoEmpleadoAlmacenado");
            e.printStackTrace();
            //PropertyConfigurator.configure("log4j.properties");
            //logger.error("Metodo: obtenerUltimoEmpleadoAlmacenado - PersistenciaEmpleados - Fecha : " + format.format(fechaDia) + " - Error : " + e.toString());
            return null;
        }
    }

    @Override
    public List<Empleados> consultarEmpleadosParametroAutoliq(EntityManager em) {
        try {
            em.clear();
            String sql = "SELECT * FROM empleados E WHERE EXISTS (SELECT 1 FROM VigenciasTiposTrabajadores vtte,tipostrabajadores tt\n"
                    + "  WHERE vtte.empleado = E.SECUENCIA\n"
                    + "  and vtte.tipotrabajador = tt.secuencia\n"
                    + "  and tt.tipo != 'DISPONIBLE'\n"
                    + "  AND  vtte.fechavigencia = (select max(fechavigencia)\n"
                    + "  from vigenciastipostrabajadores vtti\n"
                    + "  WHERE  vtti.empleado = vtte.empleado\n"
                    + "  and   vtti.fechavigencia <= last_day(sysdate)))";
            Query query = em.createNativeQuery(sql, Empleados.class);
            List<Empleados> lista = query.getResultList();
            return lista;
        } catch (Exception e) {
            //PropertyConfigurator.configure("log4j.properties");
            //logger.error("Metodo: consultarEmpleadosParametroAutoliq - PersistenciaEmpleados - Fecha : " + format.format(fechaDia) + " - Error : " + e.toString());
            return null;
        }
    }

    @Override
    public List<Empleados> consultarEmpleadosParaProyecciones(EntityManager em) {
        try {
            em.clear();
            String sql = "SELECT * FROM Empleados c WHERE  EXISTS (SELECT 'X' FROM Proyecciones n WHERE n.empleado  = c.secuencia)";
            Query query = em.createNativeQuery(sql, Empleados.class);
            List<Empleados> lista = query.getResultList();
            return lista;
        } catch (Exception e) {
            //PropertyConfigurator.configure("log4j.properties");
            //logger.error("Metodo: consultarEmpleadosParaProyecciones - PersistenciaEmpleados - Fecha : " + format.format(fechaDia) + " - Error : " + e.toString());
            return null;
        }
    }

    @Override
    public void eliminarEmpleadoNominaF(EntityManager em, BigInteger secuenciaEmpleado, BigInteger secuenciaPersona) {
        EntityTransaction tx = em.getTransaction();
        try {
            em.clear();
            tx.begin();
            String sqlQuery = "call ELIMINAREMPLEADO.eli_emple_no_liqui(?,?)";
            Query query = em.createNativeQuery(sqlQuery);
            query.setParameter(1, secuenciaEmpleado);
            query.setParameter(2, secuenciaPersona);
            query.executeUpdate();
            tx.commit();
        } catch (Exception e) {
            //PropertyConfigurator.configure("log4j.properties");
            //logger.error("Metodo: eliminarEmpleadoNominaF - PersistenciaEmpleados - Fecha : " + format.format(fechaDia) + " - Error : " + e.toString());
            if (tx.isActive()) {
                tx.rollback();
            }
        }
    }

    @Override
    public void reingresarEmpleado(EntityManager em, BigInteger codigoEmpleado, BigInteger centroCosto, Date fechaReingreso, BigInteger empresa, Date fechaFinal) {
        EntityTransaction tx = em.getTransaction();
        try {
            em.clear();
            tx.begin();
            String sqlQuery = "call ELIMINAREMPLEADO.reingresar_empleado(?,?,?,?,?)";
            Query query = em.createNativeQuery(sqlQuery);
            query.setParameter(1, codigoEmpleado);
            query.setParameter(2, centroCosto);
            query.setParameter(3, fechaReingreso);
            query.setParameter(4, empresa);
            query.setParameter(5, fechaFinal);
            query.executeUpdate();
            tx.commit();
        } catch (Exception e) {
            //PropertyConfigurator.configure("log4j.properties");
            //logger.error("Metodo: reingresarEmpleado - PersistenciaEmpleados - Fecha : " + format.format(fechaDia) + " - Error : " + e.toString());
            if (tx.isActive()) {
                tx.rollback();
            }
        }
    }

    @Override
    public List<Empleados> consultarEmpleadosReingreso(EntityManager em) {
        try {
            em.clear();
            String sql = "select e.* ,p.* from empleados e,personas p where e.persona = p.secuencia and exists (select 'x' from vigenciastipostrabajadores vt,tipostrabajadores tt where vt.empleado = e.secuencia and vt.tipotrabajador = tt.secuencia and tt.tipo = 'RETIRADO' AND   VT.FECHAVIGENCIA = (SELECT MAX(FECHAVIGENCIA) FROM VIGENCIASTIPOSTRABAJADORES VTTI WHERE FECHAVIGENCIA<= (SELECT  FECHAHASTACAUSADO  FROM VWACTUALESFECHAS) AND VTTI.EMPLEADO = VT.EMPLEADO ))";
            Query query = em.createNativeQuery(sql, Empleados.class);
            List<Empleados> lista = query.getResultList();
            return lista;
        } catch (Exception e) {
            //PropertyConfigurator.configure("log4j.properties");
            //logger.error("Metodo: consultarEmpleadosReingreso - PersistenciaEmpleados - Fecha : " + format.format(fechaDia) + " - Error : " + e.toString());
            return null;
        }
    }

    @Override
    public Date verificarFecha(EntityManager em, BigInteger secuenciaEmpleado) {
        Date fechaRetiro;
        try {
            em.clear();
            String sql = "select max(r.fecharetiro)\n"
                    + "      from vigenciastipostrabajadores vtt, retirados r, tipostrabajadores tt\n"
                    + "      where vtt.empleado = ?\n"
                    + "      and vtt.secuencia=r.vigenciatipotrabajador\n"
                    + "      and tt.secuencia = vtt.tipotrabajador";
            Query query = em.createNativeQuery(sql);
            query.setParameter(1, secuenciaEmpleado);
            fechaRetiro = (Date) query.getSingleResult();
            return fechaRetiro;
        } catch (Exception e) {
            //PropertyConfigurator.configure("log4j.properties");
            //logger.error("Metodo: verificarFecha - PersistenciaEmpleados - Fecha : " + format.format(fechaDia) + " - Error : " + e.toString());
            return null;
        }
    }

    @Override
    public void cambiarFechaIngreso(EntityManager em, BigInteger secuenciaEmpleado, Date fechaAntigua, Date fechaNueva) {
        EntityTransaction tx = em.getTransaction();
        try {
            em.clear();
            tx.begin();
            String sqlQuery = "call ELIMINAREMPLEADO.cambiarfechaingreso(?,?,?)";
            Query query = em.createNativeQuery(sqlQuery);
            query.setParameter(1, secuenciaEmpleado);
            query.setParameter(2, fechaAntigua);
            query.setParameter(3, fechaNueva);
            query.executeUpdate();
            tx.commit();
        } catch (Exception e) {
            //PropertyConfigurator.configure("log4j.properties");
            //logger.error("Metodo: cambiarFechaIngreso - PersistenciaEmpleados - Fecha : " + format.format(fechaDia) + " - Error : " + e.toString());
            if (tx.isActive()) {
                tx.rollback();
            }
        }
    }

    @Override
    public List<Empleados> consultarEmpleadosCuadrillas(EntityManager em) {
        try {
            em.clear();
            String sql = "SELECT E.* \n"
                    + " FROM PERSONAS P, EMPLEADOS E \n"
                    + " WHERE E.PERSONA = P.SECUENCIA \n"
                    + " AND EXISTS (SELECT 1 \n"
                    + "            FROM DETALLESTURNOSROTATIVOS DTR \n"
                    + "            WHERE DTR.EMPLEADO=E.SECUENCIA) \n"
                    + "AND EXISTS (SELECT 1 \n"
                    + "            FROM VWACTUALESTIPOSTRABAJADORES VTT,   TIPOSTRABAJADORES TT \n"
                    + "            WHERE VTT.TIPOTRABAJADOR = TT.SECUENCIA \n"
                    + "            AND   VTT.EMPLEADO = E.SECUENCIA    \n"
                    + "            AND   TT.TIPO IN ('ACTIVO','PENSIONADO','RETIRADO'))";
            Query query = em.createNativeQuery(sql, Empleados.class);
            List<Empleados> lista = query.getResultList();
            return lista;
        } catch (Exception e) {
            //PropertyConfigurator.configure("log4j.properties");
            //logger.error("Metodo: consultarEmpleadosCuadrillas - PersistenciaEmpleados - Fecha : " + format.format(fechaDia) + " - Error : " + e.toString());
            return null;
        }
    }

    @Override
    public List<Empleados> buscarEmpleadosATHoraExtra(EntityManager em) {
        try {
            em.clear();
            String sql = "SELECT *\n"
                    + " FROM EMPLEADOS V \n"
                    + " WHERE (EXISTS (\n"
                    + "     SELECT 'X' FROM VWACTUALESCARGOS VWC \n"
                    + "       WHERE VWC.empleado=V.SECUENCIA\n"
                    + "       AND   ESTRUCTURA IN \n"
                    + "         (select e1.secuencia from estructuras e1\n"
                    + "          start with e1.estructurapadre = \n"
                    + "            (SELECT ESTRUCTURA FROM EERSAUTORIZACIONES EA, USUARIOS U \n"
                    + "		     WHERE U.secuencia=EA.usuario\n"
                    + "		  	 AND   U.alias=USER\n"
                    + "			 AND EA.eerestado=(SELECT SECUENCIA FROM EERSESTADOS WHERE TIPOEER='TURNO' \n"
                    + "			 AND CODIGO=(SELECT MIN(CODIGO) FROM EERSESTADOS WHERE TIPOEER='TURNO')))\n"
                    + "          connect by prior e1.secuencia = e1.estructurapadre))\n"
                    + " AND EXISTS (SELECT 'X'\n"
                    + "      FROM VWACTUALESTIPOSTRABAJADORES vtt, tipostrabajadores  tt\n"
                    + "      WHERE tt.secuencia = vtt.tipotrabajador\n"
                    + "      AND   vtt.empleado = V.secuencia\n"
                    + "      AND   tt.tipo='ACTIVO'))";
            Query query = em.createNativeQuery(sql, Empleados.class);
            List<Empleados> lista = query.getResultList();
            return lista;
        } catch (Exception e) {
            //PropertyConfigurator.configure("log4j.properties");
            //logger.error("Metodo: buscarEmpleadosATHoraExtra - PersistenciaEmpleados - Fecha : " + format.format(fechaDia) + " - Error : " + e.toString());
            return null;
        }
    }

    @Override
    public List<Empleados> consultarEmpleadosParaAprobarHorasExtras(EntityManager em) {
        try {
            em.clear();
            String sql = "SELECT E.*\n"
                    + " FROM PERSONAS P, EMPLEADOS E \n"
                    + " WHERE E.PERSONA = P.SECUENCIA\n"
                    + "  AND EXISTS (SELECT 1 FROM VWACTUALESTIPOSTRABAJADORES VTT,   TIPOSTRABAJADORES TT \n"
                    + "   WHERE VTT.TIPOTRABAJADOR = TT.SECUENCIA \n"
                    + "   AND   VTT.EMPLEADO = E.SECUENCIA \n"
                    + "   AND   TT.TIPO IN ('ACTIVO','PENSIONADO'))";
            Query query = em.createNativeQuery(sql, Empleados.class);
            List<Empleados> lista = query.getResultList();
            return lista;
        } catch (Exception e) {
            //PropertyConfigurator.configure("log4j.properties");
            //logger.error("Metodo: consultarEmpleadosParaAprobarHorasExtras - PersistenciaEmpleados - Fecha : " + format.format(fechaDia) + " - Error : " + e.toString());
            return null;
        }
    }
}
