/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Persistencia;

import InterfacePersistencia.PersistenciaTiposEmbargosInterface;
import Entidades.TiposEmbargos;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Clase Stateless.<br> 
 * Clase encargada de realizar operaciones sobre la tabla 'TiposEmbargos'
 * de la base de datos.
 * @author betelgeuse
 */
@Stateless
public class PersistenciaTiposEmbargos implements PersistenciaTiposEmbargosInterface {

    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos
     */
/*    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;
*/
    
    @Override
    public void crear(EntityManager em, TiposEmbargos tiposEmbargos) {
        em.persist(tiposEmbargos);
    }

    @Override
    public void editar(EntityManager em, TiposEmbargos tiposEmbargos) {
        em.merge(tiposEmbargos);
    }

    @Override
    public void borrar(EntityManager em, TiposEmbargos tiposEmbargos) {
        try {
            em.remove(em.merge(tiposEmbargos));
        } catch (Exception e) {
            System.err.println("Error borrando TiposEmbargos");
            System.out.println(e);
        }
    }

    @Override
    public TiposEmbargos buscarTipoEmbargo(EntityManager em, BigInteger secuencia) {
        try {
            return em.find(TiposEmbargos.class, secuencia);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<TiposEmbargos> buscarTiposEmbargos(EntityManager em) {
        Query query = em.createQuery("SELECT m FROM TiposEmbargos m ORDER BY m.codigo ASC");
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        List<TiposEmbargos> listaMotivosPrestamos = query.getResultList();
        return listaMotivosPrestamos;
    }

    @Override
    public BigInteger contadorEerPrestamos(EntityManager em, BigInteger secuencia) {
        BigInteger retorno;
        try {
            String sqlQuery = " SELECT COUNT(*)FROM eersprestamos ee WHERE ee.tipoembargo = ? ";
            Query query = em.createNativeQuery(sqlQuery);
            query.setParameter(1, secuencia);
            retorno = new BigInteger(query.getSingleResult().toString());
            System.out.println("PERSISTENCIATIPOSEMBARGOS CONTADOREERPRESTAMOS = " + retorno);
            return retorno;
        } catch (Exception e) {
            System.err.println("ERROR PERSISTENCIATIPOSEMBARGOS CONTADOREERPRESTAMOS ERROR = " + e);
            retorno = new BigInteger("-1");
            return retorno;
        }
    }

    @Override
    public BigInteger contadorFormasDtos(EntityManager em, BigInteger secuencia) {
        BigInteger retorno;
        try {
            String sqlQuery = " SELECT COUNT(*)FROM formasdtos fdts WHERE fdts.tipoembargo = ? ";
            Query query = em.createNativeQuery(sqlQuery);
            query.setParameter(1, secuencia);
            retorno = new BigInteger(query.getSingleResult().toString());
            System.out.println("PERSISTENCIATIPOSEMBARGOS CONTADORFORMASDTOS = " + retorno);
            return retorno;
        } catch (Exception e) {
            System.err.println("ERROR PERSISTENCIATIPOSEMBARGOS CONTADORFORMASDTOS ERROR = " + e);
            retorno = new BigInteger("-1");
            return retorno;
        }
    }
}
