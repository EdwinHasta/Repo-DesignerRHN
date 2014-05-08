/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Persistencia;

import Entidades.TiposCentrosCostos;
import InterfacePersistencia.PersistenciaTiposCentrosCostosInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;

/**
 * Clase Stateless.<br>
 * Clase encargada de realizar operaciones sobre la tabla 'TiposCentrosCostos'
 * de la base de datos.
 *
 * @author betelgeuse
 */
@Stateless
//@LocalBean
public class PersistenciaTiposCentrosCostos implements PersistenciaTiposCentrosCostosInterface {

    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos.
     */
/*    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    EntityManager em;
*/

    @Override
    public void crear(EntityManager em, TiposCentrosCostos TiposCentrosCostos) {
        try {
            em.persist(TiposCentrosCostos);
        } catch (Exception e) {
            System.out.println("\n ERROR EN PersistenciaTiposCentrosCostos crear ERROR " + e);
        }
    }

    @Override
    public void editar(EntityManager em, TiposCentrosCostos TiposCentrosCostos) {
        try {
            em.merge(TiposCentrosCostos);
        } catch (Exception e) {
            System.out.println("\n ERROR EN PersistenciaTiposCentrosCostos editar ERROR " + e);
        }
    }

    @Override
    public void borrar(EntityManager em, TiposCentrosCostos TiposCentrosCostos) {
        try {
            em.remove(em.merge(TiposCentrosCostos));
        } catch (Exception e) {
            System.out.println("\n ERROR EN PersistenciaTiposCentrosCostos borrar ERROR " + e);
        }
    }

    @Override
    public TiposCentrosCostos buscarTipoCentrosCostos(EntityManager em, BigInteger secuencia) {
        try {
            return em.find(TiposCentrosCostos.class, secuencia);
        } catch (Exception e) {
            System.err.println("ERROR PersistenciaTiposCentosCostos buscarTiposCentrosCostos ERROR " + e);
            return null;
        }
    }

    @Override
    public List<TiposCentrosCostos> buscarTiposCentrosCostos(EntityManager em) {
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TiposCentrosCostos.class));
            return em.createQuery(cq).getResultList();
        } catch (Exception e) {
            System.out.println("\n ERROR EN PersistenciaTiposCentrosCostos buscarTiposCentrosCostos ERROR" + e);
            return null;
        }
    }

    @Override
    public BigInteger verificarBorradoCentrosCostos(EntityManager em, BigInteger secuencia) {
        BigInteger retorno = new BigInteger("-1");
        try {
            Query query = em.createQuery("SELECT count(cc) FROM CentrosCostos cc WHERE cc.tipocentrocosto.secuencia = :secTipoCentroCosto ");
            query.setParameter("secTipoCentroCosto", secuencia);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            retorno = new BigInteger(query.getSingleResult().toString());
            System.err.println("PersistenciaTiposCentrosCostos retorno ==" + retorno.intValue());

        } catch (Exception e) {
            System.err.println("ERROR EN PersistenciaTiposCentrosCostos verificarBorrado ERROR :" + e);
        } finally {
            return retorno;
        }
    }

    @Override
    public BigInteger verificarBorradoVigenciasCuentas(EntityManager em, BigInteger secuencia) {
        BigInteger retorno = new BigInteger("-1");
        try {
            Query query = em.createQuery("SELECT count(vc) FROM VigenciasCuentas vc WHERE vc.tipocc.secuencia  = :secTipoCentroCosto ");
            query.setParameter("secTipoCentroCosto", secuencia);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            retorno = new BigInteger(query.getSingleResult().toString());
            System.err.println("PersistenciaTiposCentrosCostos retorno ==" + retorno.intValue());

        } catch (Exception e) {
            System.err.println("ERROR EN PersistenciaTiposCentrosCostos verificarBorrado ERROR :" + e);
        } finally {
            return retorno;
        }
    }

    @Override
    public BigInteger verificarBorradoRiesgosProfesionales(EntityManager em, BigInteger secuencia) {
        BigInteger retorno = new BigInteger("-1");
        try {
            String sqlQuery = "SELECT COUNT(*)FROM riesgosprofesionales WHERE tipocentrocosto = ?";
            Query query = em.createNativeQuery(sqlQuery);
            query.setParameter(1, secuencia);
            retorno = new BigInteger(query.getSingleResult().toString());
            System.out.println("PersistenciaTiposCentrosCostos VerificarBorradoProfesionales Contador : " + retorno);
            return retorno;
        } catch (Exception e) {
            System.err.println("ERROR EN PersistenciaTiposCentrosCostos verificarBorrado ERROR :" + e);
        } finally {
            return retorno;
        }
    }
}
