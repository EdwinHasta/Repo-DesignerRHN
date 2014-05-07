/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Persistencia;

import Entidades.Empresas;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import InterfacePersistencia.PersistenciaEmpresasInterface;
import java.math.BigInteger;
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
    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    @Override
    public void crear(Empresas empresas) {
        try {
            em.persist(empresas);
        } catch (Exception e) {
            System.out.println("No es posible crear la empresa");
        }
    }

    @Override
    public void editar(Empresas empresas) {
        try {
            em.merge(empresas);
        } catch (Exception e) {
            System.out.println("La empresa no exite o esta reservada por lo cual no puede ser modificada");
        }
    }

    @Override
    public void borrar(Empresas empresas) {
        em.remove(em.merge(empresas));
    }

    @Override
    public List<Empresas> buscarEmpresas() {
        try {
            Query query = em.createQuery("SELECT e FROM Empresas e");
            List<Empresas> empresas = query.getResultList();
            return empresas;
        } catch (Exception e) {
            System.out.println("Error buscarEmpresas PersistenciaEmpresas : " + e.toString());
            return null;
        }
    }

    @Override
    public Empresas buscarEmpresasSecuencia(BigInteger secuencia) {
        Empresas empresas;
        try {
            Query query = em.createQuery("SELECT e FROM Empresas e WHERE e.secuencia = :secuencia");
            query.setParameter("secuencia", secuencia);
            empresas = (Empresas) query.getSingleResult();
            return empresas;
        } catch (Exception e) {
            empresas = null;
            System.out.println("Error buscarEmpresasSecuencia PersistenciaEmpresas");
            return empresas;
        }
    }

    @Override
    public String estadoConsultaDatos(BigInteger secuenciaEmpresa) {
        try {
            Query query = em.createQuery("SELECT e.barraconsultadatos FROM Empresas e WHERE e.secuencia = :secuenciaEmpresa");
            query.setParameter("secuenciaEmpresa", secuenciaEmpresa);
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
            Query query = entity.createQuery("SELECT COUNT(e) FROM Empresas e WHERE e.codigo > 0");
            Long resultado = (Long) query.getSingleResult();
            if (resultado == 1) {
                query = entity.createQuery("SELECT e.nombre FROM Empresas e WHERE e.codigo > 0");
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
    public Short codigoEmpresa() {
        try {
            Query query = em.createQuery("SELECT COUNT(e) FROM Empresas e");
            Long resultado = (Long) query.getSingleResult();
            if (resultado == 1) {
                query = em.createQuery("SELECT e.codigo FROM Empresas e");
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
    public List<Empresas> consultarEmpresas() {
        try {
            Query query = em.createQuery("SELECT e FROM Empresas e");
            List<Empresas> empresas = query.getResultList();
            return empresas;
        } catch (Exception e) {
            System.out.println("Error buscarEmpresas PersistenciaEmpresas : " + e.toString());
            return null;
        }
    }

    @Override
    public String consultarPrimeraEmpresa() {
        try {
            String retorno = "";
            Query query = em.createQuery("SELECT e FROM Empresas e WHERE ROWNUM=1");
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
}
