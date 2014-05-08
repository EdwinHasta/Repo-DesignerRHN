/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Persistencia;

import Entidades.TiposContratos;
import InterfacePersistencia.PersistenciaTiposContratosInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
/**
 * Clase Stateless.<br>
 * Clase encargada de realizar operaciones sobre la tabla 'TiposContratos'
 * de la base de datos.
 * @author betelgeuse
 */
@Stateless
public class PersistenciaTiposContratos implements PersistenciaTiposContratosInterface{
    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos.
     */
/*    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;
*/

    @Override
    public void crear(EntityManager em, TiposContratos tiposContratos) {
        em.persist(tiposContratos);
    }
    
    @Override
    public void editar(EntityManager em, TiposContratos tiposContratos) {
        em.merge(tiposContratos);
    }

    @Override
    public void borrar(EntityManager em, TiposContratos tiposContratos) {
        em.remove(em.merge(tiposContratos));
    }
    
    @Override
    public TiposContratos buscarTipoContratoSecuencia(EntityManager em, BigInteger secuencia) {

        try {
            Query query = em.createQuery("SELECT e FROM TiposContratos e WHERE e.secuencia = :secuencia");
            query.setParameter("secuencia", secuencia);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            TiposContratos tipoC = (TiposContratos) query.getSingleResult();
            return tipoC;
        } catch (Exception e) {
        }
        TiposContratos tipoC = null;
        return tipoC;
    }
    
    @Override
     public List<TiposContratos> tiposContratos(EntityManager em) {
        try {
            Query query = em.createQuery("SELECT tc FROM TiposContratos tc ORDER BY tc.codigo");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<TiposContratos> tiposContratos = query.getResultList();
            return tiposContratos;
        } catch (Exception e) {
            return null;
        }
    }
}
