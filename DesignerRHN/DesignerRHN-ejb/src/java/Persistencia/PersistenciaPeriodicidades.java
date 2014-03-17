/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Persistencia;

import Entidades.Periodicidades;
import InterfacePersistencia.PersistenciaPeriodicidadesInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;

/**
 * Clase Stateless. <br>
 * Clase encargada de realizar operaciones sobre la tabla 'Periodicidades' de la
 * base de datos.
 *
 * @author betelgeuse
 */
@Stateless
public class PersistenciaPeriodicidades implements PersistenciaPeriodicidadesInterface {

    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos.
     */
    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    @Override
    public void crear(Periodicidades periodicidades) {
        em.persist(periodicidades);
    }

    @Override
    public void editar(Periodicidades periodicidades) {
        em.merge(periodicidades);
    }

    @Override
    public void borrar(Periodicidades periodicidades) {
        em.remove(em.merge(periodicidades));
    }

    @Override
    public boolean verificarCodigoPeriodicidad(BigInteger codigoPeriodicidad) {
        try {
            Query query = em.createQuery("SELECT COUNT(p) FROM Periodicidades p WHERE p.codigo = :codigo");
            query.setParameter("codigo", codigoPeriodicidad);
            Long resultado = (Long) query.getSingleResult();
            return resultado > 0;
        } catch (Exception e) {
            System.out.println("Exepcion: " + e);
            return false;
        }
    }

    @Override
    public Periodicidades consultarPeriodicidad(BigInteger secuencia) {
        try {
            return em.find(Periodicidades.class, secuencia);
        } catch (Exception e) {
            System.out.println("Error en la persistencia vigencias formas pagos ERROR : " + e);
            return null;
        }
    }

    public List<Periodicidades> consultarPeriodicidades() {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(Periodicidades.class));
        return em.createQuery(cq).getResultList();
    }

    @Override
    public BigInteger contarCPCompromisosPeriodicidad(BigInteger secuencia) {
        BigInteger retorno = new BigInteger("-1");
        try {
            String sqlQuery = "SELECT COUNT(*)FROM cpcompromisos WHERE periodicidad = ?";
            Query query = em.createNativeQuery(sqlQuery);
            query.setParameter(1, secuencia);
            retorno = new BigInteger(query.getSingleResult().toString());
            System.out.println("Contador PERSISTENCIAPERIODICIDADES contarCPCompromisosPeriodicidad  " + retorno);
            return retorno;
        } catch (Exception e) {
            System.err.println("Error PERSISTENCIAPERIODICIDADES  contarCPCompromisosPeriodicidad. " + e);
            return retorno;
        }
    }

    @Override
    public BigInteger contarDetallesPeriodicidadesPeriodicidad(BigInteger secuencia) {
        BigInteger retorno = new BigInteger("-1");
        try {
            String sqlQuery = "SELECT COUNT(*)FROM detallesperiodicidades WHERE periodicidad = ?";
            Query query = em.createNativeQuery(sqlQuery);
            query.setParameter(1, secuencia);
            retorno = new BigInteger(query.getSingleResult().toString());
            System.out.println("Contador PERSISTENCIAPERIODICIDADES contarDetallesPeriodicidadesPeriodicidad  " + retorno);
            return retorno;
        } catch (Exception e) {
            System.err.println("Error PERSISTENCIAPERIODICIDADES  contarDetallesPeriodicidadesPeriodicidad " + e);
            return retorno;
        }
    }

    @Override
    public BigInteger contarEersPrestamosDtosPeriodicidad(BigInteger secuencia) {
        BigInteger retorno = new BigInteger("-1");
        try {
            String sqlQuery = "SELECT COUNT(*)FROM eersprestamosdtos WHERE periodicidad = ?";
            Query query = em.createNativeQuery(sqlQuery);
            query.setParameter(1, secuencia);
            retorno = new BigInteger(query.getSingleResult().toString());
            System.out.println("Contador PERSISTENCIAPERIODICIDADES contarEersPrestamosDtosPeriodicidad  " + retorno);
            return retorno;
        } catch (Exception e) {
            System.err.println("Error PERSISTENCIAPERIODICIDADES  contarEersPrestamosDtosPeriodicidad " + e);
            return retorno;
        }
    }

    @Override
    public BigInteger contarEmpresasPeriodicidad(BigInteger secuencia) {
        BigInteger retorno = new BigInteger("-1");
        try {
            String sqlQuery = "SELECT COUNT(*)FROM empresas WHERE minimaperiodicidad = ?";
            Query query = em.createNativeQuery(sqlQuery);
            query.setParameter(1, secuencia);
            retorno = new BigInteger(query.getSingleResult().toString());
            System.out.println("Contador PERSISTENCIAPERIODICIDADES contarEmpresasPeriodicidad  " + retorno);
            return retorno;
        } catch (Exception e) {
            System.err.println("Error PERSISTENCIAPERIODICIDADES  contarEmpresasPeriodicidad " + e);
            return retorno;
        }
    }

    @Override
    public BigInteger contarFormulasAseguradasPeriodicidad(BigInteger secuencia) {
        BigInteger retorno = new BigInteger("-1");
        try {
            String sqlQuery = "SELECT COUNT(*)FROM formulasaseguradas WHERE periodicidad = ?";
            Query query = em.createNativeQuery(sqlQuery);
            query.setParameter(1, secuencia);
            retorno = new BigInteger(query.getSingleResult().toString());
            System.out.println("Contador PERSISTENCIAPERIODICIDADES contarFormulasAseguradasPeriodicidad  " + retorno);
            return retorno;
        } catch (Exception e) {
            System.err.println("Error PERSISTENCIAPERIODICIDADES  contarFormulasAseguradasPeriodicidad " + e);
            return retorno;
        }
    }

    @Override
    public BigInteger contarFormulasContratosPeriodicidad(BigInteger secuencia) {
        BigInteger retorno = new BigInteger("-1");
        try {
            String sqlQuery = "SELECT COUNT(*)FROM formulascontratos WHERE periodicidad = ?";
            Query query = em.createNativeQuery(sqlQuery);
            query.setParameter(1, secuencia);
            retorno = new BigInteger(query.getSingleResult().toString());
            System.out.println("Contador PERSISTENCIAPERIODICIDADES contarFormulasContratosPeriodicidad  " + retorno);
            return retorno;
        } catch (Exception e) {
            System.err.println("Error PERSISTENCIAPERIODICIDADES  contarFormulasContratosPeriodicidad " + e);
            return retorno;
        }
    }

    @Override
    public BigInteger contarGruposProvisionesPeriodicidad(BigInteger secuencia) {
        BigInteger retorno = new BigInteger("-1");
        try {
            String sqlQuery = "SELECT COUNT(*)FROM gruposprovisiones WHERE periodicidadcorte = ?";
            Query query = em.createNativeQuery(sqlQuery);
            query.setParameter(1, secuencia);
            retorno = new BigInteger(query.getSingleResult().toString());
            System.out.println("Contador PERSISTENCIAPERIODICIDADES contarGruposProvisionesPeriodicidad  " + retorno);
            return retorno;
        } catch (Exception e) {
            System.err.println("Error PERSISTENCIAPERIODICIDADES  contarGruposProvisionesPeriodicidad " + e);
            return retorno;
        }
    }

    @Override
    public BigInteger contarNovedadesPeriodicidad(BigInteger secuencia) {
        BigInteger retorno = new BigInteger("-1");
        try {
            String sqlQuery = "SELECT COUNT(*)FROM novedades WHERE periodicidad = ?";
            Query query = em.createNativeQuery(sqlQuery);
            query.setParameter(1, secuencia);
            retorno = new BigInteger(query.getSingleResult().toString());
            System.out.println("Contador PERSISTENCIAPERIODICIDADES contarNovedadesPeriodicidad  " + retorno);
            return retorno;
        } catch (Exception e) {
            System.err.println("Error PERSISTENCIAPERIODICIDADES  contarNovedadesPeriodicidad " + e);
            return retorno;
        }
    }

    @Override
    public BigInteger contarParametrosCambiosMasivosPeriodicidad(BigInteger secuencia) {
        BigInteger retorno = new BigInteger("-1");
        try {
            String sqlQuery = "SELECT COUNT(*)FROM parametroscambiosmasivos WHERE noveperiodicidad = ?";
            Query query = em.createNativeQuery(sqlQuery);
            query.setParameter(1, secuencia);
            retorno = new BigInteger(query.getSingleResult().toString());
            System.out.println("Contador PERSISTENCIAPERIODICIDADES contarParametrosCambiosMasivosPeriodicidad  " + retorno);
            return retorno;
        } catch (Exception e) {
            System.err.println("Error PERSISTENCIAPERIODICIDADES  contarParametrosCambiosMasivosPeriodicidad " + e);
            return retorno;
        }
    }

    @Override
    public BigInteger contarVigenciasFormasPagosPeriodicidad(BigInteger secuencia) {
        BigInteger retorno = new BigInteger("-1");
        try {
            String sqlQuery = "SELECT COUNT(*)FROM vigenciasformaspagos WHERE formapago = ?";
            Query query = em.createNativeQuery(sqlQuery);
            query.setParameter(1, secuencia);
            retorno = new BigInteger(query.getSingleResult().toString());
            System.out.println("Contador PERSISTENCIAPERIODICIDADES contarVigenciasFormasPagosPeriodicidad  " + retorno);
            return retorno;
        } catch (Exception e) {
            System.err.println("Error PERSISTENCIAPERIODICIDADES  contarVigenciasFormasPagosPeriodicidad " + e);
            return retorno;
        }
    }

}
