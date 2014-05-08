/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Persistencia;

import Entidades.TiposDocumentos;
import InterfacePersistencia.PersistenciaTiposDocumentosInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Clase Stateless.<br>
 * Clase encargada de realizar operaciones sobre la tabla 'MotivosContratos' de
 * la base de datos.
 *
 * @author betelgeuse
 */
@Stateless
public class PersistenciaTiposDocumentos implements PersistenciaTiposDocumentosInterface {

    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos.
     */
/*    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;
*/

    @Override
    public void crear(EntityManager em, TiposDocumentos tiposDocumentos) {
        try {
            em.persist(tiposDocumentos);
        } catch (Exception e) {
            System.out.println("Error crear PersistenciaTiposDocumentos");
        }
    }

    @Override
    public void editar(EntityManager em, TiposDocumentos tiposDocumentos) {
        try {
            em.merge(tiposDocumentos);
        } catch (Exception e) {
            System.out.println("Error editar PersistenciaTiposDocumentos");
        }
    }

    @Override
    public void borrar(EntityManager em, TiposDocumentos tiposDocumentos) {
        try {
            em.remove(em.merge(tiposDocumentos));
        } catch (Exception e) {
            System.out.println("Error borrar PersistenciaTiposDocumentos");
        }
    }

    @Override
    public List<TiposDocumentos> consultarTiposDocumentos(EntityManager em) {
        try {
            Query query = em.createQuery("SELECT td FROM TiposDocumentos td ORDER BY td.nombrecorto");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<TiposDocumentos> listaTiposDocumentos = query.getResultList();
            return listaTiposDocumentos;
        } catch (Exception e) {
            System.out.println("Error PersistenciaTiposDocumentos.ciudades " + e);
            return null;
        }
    }

    @Override
    public TiposDocumentos consultarTipoDocumento(EntityManager em, BigInteger secuencia) {
        try {
            Query query = em.createQuery("SELECT tp FROM TiposDocumentos tp WHERE tp.secuencia = :secuencia");
            query.setParameter("secuencia", secuencia);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            TiposDocumentos tiposDescansos = (TiposDocumentos) query.getSingleResult();
            return tiposDescansos;
        } catch (Exception e) {
            System.out.println("Error buscarTiposDocumentosSecuencia PersistenciaTiposDocumentos");
            TiposDocumentos tiposDescansos = null;
            return tiposDescansos;
        }
    }

    @Override
    public BigInteger contarCodeudoresTipoDocumento(EntityManager em, BigInteger secuencia) {
        BigInteger retorno = new BigInteger("-1");
        try {
            String sqlQuery = "SELECT COUNT(*)FROM codeudores WHERE tipodocumento=?";
            Query query = em.createNativeQuery(sqlQuery);
            query.setParameter(1, secuencia);
            retorno = new BigInteger(query.getSingleResult().toString());
            System.out.println("Contador PersistenciaTiposDocumentos contarCodeudoresTipoDocumento persistencia " + retorno);
            return retorno;
        } catch (Exception e) {
            System.err.println("Error PersistenciaTiposDocumentos contarCodeudoresTipoDocumento. " + e);
            return retorno;
        }
    }
    
    @Override
    public BigInteger contarPersonasTipoDocumento(EntityManager em, BigInteger secuencia) {
        BigInteger retorno = new BigInteger("-1");
        try {
            String sqlQuery = "SELECT COUNT(*)FROM personas WHERE tipodocumento=?";
            Query query = em.createNativeQuery(sqlQuery);
            query.setParameter(1, secuencia);
            retorno = new BigInteger(query.getSingleResult().toString());
            System.out.println("Contador PersistenciaTiposDocumentos contarCodeudoresTipoDocumento persistencia " + retorno);
            return retorno;
        } catch (Exception e) {
            System.err.println("Error PersistenciaTiposDocumentos contarCodeudoresTipoDocumento. " + e);
            return retorno;
        }
    }
}
