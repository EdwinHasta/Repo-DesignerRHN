/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Persistencia;

import Entidades.Empresas;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import InterfacePersistencia.PersistenciaEmpresasInterface;
import java.math.BigInteger;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

/**
 * Clase Stateless. <br>
 * Clase encargada de realizar operaciones sobre la tabla 'Empresas' de la base
 * de datos.
 *
 * @author betelgeuse
 */
@Stateless
public class PersistenciaEmpresas implements PersistenciaEmpresasInterface {

    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos
     */
    /* @PersistenceContext(unitName = "DesignerRHN-ejbPU")
     private EntityManager em;*/
    @Override
    public void crear(EntityManager em, Empresas empresas) {
        em.clear();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(empresas);
            tx.commit();
        } catch (Exception e) {
            System.out.println("Error PersistenciaVigenciasCargos.crear: " + e);
            if (tx.isActive()) {
                tx.rollback();
            }
        }
    }

    @Override
    public void editar(EntityManager em, Empresas empresas) {
        em.clear();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(empresas);
            tx.commit();
        } catch (Exception e) {
            System.out.println("Error PersistenciaVigenciasCargos.crear: " + e);
            if (tx.isActive()) {
                tx.rollback();
            }
        }
    }

    @Override
    public void borrar(EntityManager em, Empresas empresas) {
        em.clear();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.remove(em.merge(empresas));
            tx.commit();

        } catch (Exception e) {
            try {
                if (tx.isActive()) {
                    tx.rollback();
                }
            } catch (Exception ex) {
                System.out.println("Error PersistenciaVigenciasCargos.borrar: " + e);
            }
        }
    }

    @Override
    public List<Empresas> buscarEmpresas(EntityManager em) {
        try {
            em.clear();
            Query query = em.createQuery("SELECT e FROM Empresas e");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<Empresas> empresas = query.getResultList();
            return empresas;
        } catch (Exception e) {
            System.out.println("Error buscarEmpresas PersistenciaEmpresas : " + e.toString());
            return null;
        }
    }

    public List<Empresas> buscarEmpresasLista(EntityManager em, BigInteger secuencia) {
        try {
            em.clear();
            //Query query = em.createQuery("SELECT c FROM Conceptos c WHERE c.empresa.secuencia  = NVL(:secEmpresa, c.empresa.secuencia) ORDER BY c.codigo ASC");
            //Query query = em.createQuery("SELECT e FROM Empresas e WHERE e.secuencia  NVL(:secuencia, e)");
            //query.setParameter("secuencia", secuencia);
            String sqlQuery = "SELECT * FROM Empresas e WHERE e.secuencia = NVL( ?, e.secuencia)";
            Query query = em.createNativeQuery(sqlQuery, Empresas.class);
            query.setParameter(1, secuencia);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<Empresas> empresas = query.getResultList();
            return empresas;
        } catch (Exception e) {
            System.out.println("Error buscarEmpresas PersistenciaEmpresas : " + e.toString());
            return null;
        }
    }

    @Override
    public Empresas buscarEmpresasSecuencia(EntityManager em, BigInteger secuencia) {
        Empresas empresas;
        try {
            em.clear();
            Query query = em.createQuery("SELECT e FROM Empresas e WHERE e.secuencia = :secuencia");
            query.setParameter("secuencia", secuencia);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            empresas = (Empresas) query.getSingleResult();
            return empresas;
        } catch (Exception e) {
            empresas = null;
            System.out.println("Error buscarEmpresasSecuencia PersistenciaEmpresas");
            return empresas;
        }
    }

    @Override
    public String estadoConsultaDatos(EntityManager em, BigInteger secuenciaEmpresa) {
        try {
            em.clear();
            Query query = em.createQuery("SELECT e.barraconsultadatos FROM Empresas e WHERE e.secuencia = :secuenciaEmpresa");
            query.setParameter("secuenciaEmpresa", secuenciaEmpresa);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            String estado = (String) query.getSingleResult();
            if (estado == null) {
                return "N";
            }
            return estado;
        } catch (Exception e) {
            System.out.println("Error PersistenciaEmpresas.estadoConsultaDatos");
            return "N";
        }
    }

    @Override
    public String nombreEmpresa(EntityManager entity) {
        try {
            entity.clear();
            Query query = entity.createQuery("SELECT COUNT(e) FROM Empresas e WHERE e.codigo > 0");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            Long resultado = (Long) query.getSingleResult();
            if (resultado == 1) {
                query = entity.createQuery("SELECT e.nombre FROM Empresas e WHERE e.codigo > 0");
                query.setHint("javax.persistence.cache.storeMode", "REFRESH");
                String nombreE = (String) query.getSingleResult();
                return nombreE;
            } else if (resultado > 1) {
                return "(MULTIEMPRESA)";
            } else {
                return "SIN REGISTRAR";
            }
        } catch (Exception e) {
            System.out.println("Exepcion en PersistenciaEmpleados.nombreEmpresa" + e);
            return null;
        }
    }

    @Override
    public Short codigoEmpresa(EntityManager em) {
        try {
            em.clear();
            Query query = em.createQuery("SELECT COUNT(e) FROM Empresas e");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            Long resultado = (Long) query.getSingleResult();
            if (resultado == 1) {
                query = em.createQuery("SELECT e.codigo FROM Empresas e");
                query.setHint("javax.persistence.cache.storeMode", "REFRESH");
                Short codigoEmpresa = (Short) query.getSingleResult();
                return codigoEmpresa;
            } else {
                return 1;
            }
        } catch (Exception e) {
            System.out.println("Exepcion en PersistenciaEmpleados.codigoEmpresa" + e);
            return null;
        }
    }

    @Override
    public List<Empresas> consultarEmpresas(EntityManager em) {
        try {
            em.clear();
            Query query = em.createQuery("SELECT e FROM Empresas e");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<Empresas> empresas = query.getResultList();
            return empresas;
        } catch (Exception e) {
            System.out.println("Error buscarEmpresas PersistenciaEmpresas : " + e.toString());
            return null;
        }
    }

    @Override
    public String consultarPrimeraEmpresa(EntityManager em) {
        try {
            em.clear();
            String retorno = "";
            Query query = em.createNativeQuery("SELECT * FROM Empresas e WHERE ROWNUM=1", Empresas.class);
            Empresas empresa = (Empresas) query.getSingleResult();
            if (empresa != null) {
                String sqlQuery = "call EMPRESAS_PKG.RETENCIONYSEGSOCXPERSONA(?)";
                Query query2 = em.createNativeQuery(sqlQuery);
                query2.setParameter(1, empresa.getCodigo());
                String aux = (String) query2.getSingleResult();
                if (aux == null || aux.isEmpty()) {
                    retorno = "N";
                } else {
                    retorno = "S";
                }
            } else {
                retorno = "N";
            }
            return retorno;
        } catch (Exception e) {
            System.out.println("Error consultarPrimeraEmpresa PersistenciaEmpresas : " + e.toString());
            return "N";
        }
    }

    @Override
    public Empresas consultarPrimeraEmpresaSinPaquete(EntityManager em) {
        try {
            em.clear();
            Query query = em.createNativeQuery("SELECT * FROM Empresas e WHERE ROWNUM=1", Empresas.class);
            Empresas empresa = (Empresas) query.getSingleResult();
            return empresa;
        } catch (Exception e) {
            System.out.println("Error consultarPrimeraEmpresaSinPaquete PersistenciaEmpresas : " + e.toString());
            return null;
        }
    }

    @Override
    public BigInteger calcularControlEmpleadosEmpresa(EntityManager em, BigInteger secuencia) {
        try {
            em.clear();
            BigInteger contador = null;
            String sql = "SELECT COUNT(1) FROM vigenciastipostrabajadores vtt, tipostrabajadores tt, empleados e, vigenciascargos vc, estructuras est, organigramas org\n"
                    + "    WHERE tt.secuencia = vtt.tipotrabajador\n"
                    + "    AND tt.tipo='ACTIVO'\n"
                    + "    AND vtt.EMPLEADO=E.SECUENCIA\n"
                    + "    AND E.SECUENCIA=VC.EMPLEADO\n"
                    + "    AND VC.ESTRUCTURA=EST.SECUENCIA\n"
                    + "    AND EST.ORGANIGRAMA=ORG.SECUENCIA\n"
                    + "    AND ORG.EMPRESA=?\n"
                    + "    AND VC.FECHAVIGENCIA= (SELECT MAX(FECHAVIGENCIA)\n"
                    + "                          FROM VIGENCIASCARGOS VCI\n"
                    + "                          WHERE VCI.EMPLEADO=VC.EMPLEADO\n"
                    + "                          AND VCI.FECHAVIGENCIA<=SYSDATE)\n"
                    + "    AND vtt.fechavigencia = (SELECT MAX(vtti.fechavigencia)\n"
                    + "                             FROM vigenciastipostrabajadores vtti\n"
                    + "                             WHERE vtti.empleado = vtt.empleado\n"
                    + "                             AND vtti.fechavigencia <= sysdate)";
            Query query = em.createNativeQuery(sql);
            query.setParameter(1, secuencia);
            BigDecimal variable = (BigDecimal) query.getSingleResult();
            if (variable != null) {
                contador = new BigInteger(variable.toString());
            }
            return contador;
        } catch (Exception e) {
            System.out.println("Error calcularControlEmpleadosEmpresa PersistenciaEmpresas : " + e.toString());
            return null;
        }
    }

    @Override
    public BigInteger obtenerMaximoEmpleadosEmpresa(EntityManager em, BigInteger secuencia) {
        try {
            em.clear();
            BigInteger contador = null;
            Query query = em.createQuery("SELECT e FROM Empresas e WHERE e.secuencia=:secuencia");
            query.setParameter("secuencia", secuencia);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            Empresas empresa = (Empresas) query.getSingleResult();
            contador = empresa.getControlempleados();
            return contador;
        } catch (Exception e) {
            System.out.println("Error obtenerMaximoEmpleadosEmpresa PersistenciaEmpresas : " + e.toString());
            return null;
        }
    }
}
