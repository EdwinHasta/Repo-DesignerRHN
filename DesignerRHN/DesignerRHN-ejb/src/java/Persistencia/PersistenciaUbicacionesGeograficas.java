/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Persistencia;

import Entidades.UbicacionesGeograficas;
import InterfacePersistencia.PersistenciaUbicacionesGeograficasInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Clase Stateless.<br>
 * Clase encargada de realizar operaciones sobre la tabla
 * 'UbicacionesGeograficas' de la base de datos.
 *
 * @author betelgeuse
 */
@Stateless
public class PersistenciaUbicacionesGeograficas implements PersistenciaUbicacionesGeograficasInterface {

    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos.
     */
/*    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;
*/

    @Override
    public void crear(EntityManager em, UbicacionesGeograficas ubicacionGeografica) {
        try {

            if (ubicacionGeografica.getZona() != null) {
                if (ubicacionGeografica.getZona().isEmpty() || ubicacionGeografica.getZona().equals("") || ubicacionGeografica.getZona().equals(" ")) {
                    ubicacionGeografica.setZona(null);
                }
            }
            em.persist(ubicacionGeografica);
        } catch (Exception e) {
            System.err.println("Error crear PersistenciaUbicacionesGeograficas ERROR " + e);
        }
    }

    @Override
    public void editar(EntityManager em, UbicacionesGeograficas ubicacionGeografica) {
        try {
            em.merge(ubicacionGeografica);
        } catch (Exception e) {
            System.out.println("Error editar PersistenciaUbicacionesGeograficas");
        }
    }

    @Override
    public void borrar(EntityManager em, UbicacionesGeograficas ubicacionGeografica) {
        try {
            em.remove(em.merge(ubicacionGeografica));
        } catch (Exception e) {
            System.out.println("Error borrar PersistenciaUbicacionesGeograficas");
        }
    }

     @Override
    public List<UbicacionesGeograficas> consultarUbicacionesGeograficas(EntityManager em) {
        try {
            Query query = em.createQuery("SELECT u FROM UbicacionesGeograficas u ORDER BY u.codigo ASC");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<UbicacionesGeograficas> ubicacionesGeograficas = query.getResultList();
            return ubicacionesGeograficas;
        } catch (Exception e) {
            System.out.println("Error en Persistencia Ubicaciones Geograficas " + e);
            return null;
        }
    }

    @Override
    public UbicacionesGeograficas consultarUbicacionGeografica(EntityManager em, BigInteger secuencia) {
        try {
            Query query = em.createQuery("SELECT cc FROM UbicacionesGeograficas cc WHERE cc.secuencia = :secuencia");
            query.setParameter("secuencia", secuencia);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            UbicacionesGeograficas ubicacionGeografica = (UbicacionesGeograficas) query.getSingleResult();
            return ubicacionGeografica;
        } catch (Exception e) {
            System.out.println("Error consultarUbicacionGeografica PersistenciaUbicacionesGeograficas");
            UbicacionesGeograficas ubicacionGeografica = null;
            return ubicacionGeografica;
        }
    }

    @Override
    public List<UbicacionesGeograficas> consultarUbicacionesGeograficasPorEmpresa(EntityManager em, BigInteger secEmpresa) {
        try {
            Query query = em.createQuery("SELECT cce FROM UbicacionesGeograficas cce WHERE cce.empresa.secuencia = :secuenciaEmpr ORDER BY cce.codigo ASC");
            query.setParameter("secuenciaEmpr", secEmpresa);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<UbicacionesGeograficas> ubicacionGeografica = query.getResultList();
            return ubicacionGeografica;
        } catch (Exception e) {
            System.out.println("Error en Persistencia PersistenciaUbicacionesGeograficas consultarUbicacionesGeograficasPorEmpresa ERROR : " + e);
            return null;
        }
    }

    @Override
    public BigInteger contarAfiliacionesEntidadesUbicacionGeografica(EntityManager em, BigInteger secuencia) {
        BigInteger retorno = new BigInteger("-1");
        try {
            String sqlQuery = "SELECT COUNT(*)FROM afiliacionesentidades WHERE ubicaciongeografica = ?";
            Query query = em.createNativeQuery(sqlQuery);
            query.setParameter(1, secuencia);
            retorno = new BigInteger(query.getSingleResult().toString());
            return retorno;
        } catch (Exception e) {
            System.out.println("ERROR PERSISTENCIAUBICACIONESGEOGRAFICAS CONTARAFILIACIONESENTIDADESUBICACIONGEOGRAFICA ERROR : " + e);
            return retorno;
        }
    }

    @Override
    public BigInteger contarInspeccionesUbicacionGeografica(EntityManager em, BigInteger secuencia) {
        BigInteger retorno = new BigInteger("-1");
        try {
            String sqlQuery = "SELECT COUNT(*)FROM inspecciones WHERE ubicaciongeografica = ?";
            Query query = em.createNativeQuery(sqlQuery);
            query.setParameter(1, secuencia);
            retorno = new BigInteger(query.getSingleResult().toString());
            return retorno;
        } catch (Exception e) {
            System.out.println("ERROR PERSISTENCIAUBICACIONESGEOGRAFICAS CONTARINSPECCIONESUBICACIONGEOGRAFICA ERROR : " + e);
            return retorno;
        }
    }

    @Override
    public BigInteger contarParametrosInformesUbicacionGeografica(EntityManager em, BigInteger secuencia) {
        BigInteger retorno = new BigInteger("-1");
        try {
            String sqlQuery = "SELECT COUNT(*)FROM parametrosinformes WHERE ubicaciongeografica = ?";
            Query query = em.createNativeQuery(sqlQuery);
            query.setParameter(1, secuencia);
            retorno = new BigInteger(query.getSingleResult().toString());
            return retorno;
        } catch (Exception e) {
            System.out.println("ERROR PERSISTENCIAUBICACIONESGEOGRAFICAS CONTARPARAMETROSINFORMESUBICACIONGEOGRAFICA ERROR : " + e);
            return retorno;
        }
    }

    @Override
    public BigInteger contarRevisionesUbicacionGeografica(EntityManager em, BigInteger secuencia) {
        BigInteger retorno = new BigInteger("-1");
        try {
            String sqlQuery = "SELECT COUNT(*)FROM revisiones WHERE ubicaciongeografica = ?";
            Query query = em.createNativeQuery(sqlQuery);
            query.setParameter(1, secuencia);
            retorno = new BigInteger(query.getSingleResult().toString());
            return retorno;
        } catch (Exception e) {
            System.out.println("ERROR PERSISTENCIAUBICACIONESGEOGRAFICAS CONTARREVICIONESUBICACIONGEOGRAFICA ERROR : " + e);
            return retorno;
        }
    }

    @Override
    public BigInteger contarVigenciasUbicacionesGeografica(EntityManager em, BigInteger secuencia) {
        BigInteger retorno = new BigInteger("-1");
        try {
            String sqlQuery = "SELECT COUNT(*)FROM vigenciasubicaciones WHERE ubicacion = ?";
            Query query = em.createNativeQuery(sqlQuery);
            query.setParameter(1, secuencia);
            retorno = new BigInteger(query.getSingleResult().toString());
            return retorno;
        } catch (Exception e) {
            System.out.println("ERROR PERSISTENCIAUBICACIONESGEOGRAFICAS CONTARVIGENCIASUBICACIONESUBICACIONGEOGRAFICA ERROR : " + e);
            return retorno;
        }
    }

}
