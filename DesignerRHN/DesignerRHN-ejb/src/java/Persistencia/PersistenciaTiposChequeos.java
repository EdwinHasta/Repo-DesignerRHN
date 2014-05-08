/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Persistencia;

import InterfacePersistencia.PersistenciaTiposChequeosInterface;
import Entidades.TiposChequeos;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
/**
 * Clase Stateless.<br> 
 * Clase encargada de realizar operaciones sobre la tabla 'TiposChequeos'
 * de la base de datos.
 * @author betelgeuse
 */
@Stateless
public class PersistenciaTiposChequeos implements PersistenciaTiposChequeosInterface {
    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos
     */
//    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
//    private EntityManager em;
    
    @Override
     public void crear(EntityManager em, TiposChequeos tiposChequeos) {
        em.persist(tiposChequeos);
    }
     
    @Override
    public void editar(EntityManager em, TiposChequeos tiposChequeos) {
        em.merge(tiposChequeos);
    }
    
    @Override
    public void borrar(EntityManager em, TiposChequeos tiposChequeos) {
        em.remove(em.merge(tiposChequeos));
    }
    
    @Override
    public TiposChequeos buscarTipoChequeo(EntityManager em, BigInteger secuenciaTC) {
        try {
            return em.find(TiposChequeos.class, secuenciaTC);
        } catch (Exception e) {
            return null;
        }
    }
    
    @Override
    public List<TiposChequeos> buscarTiposChequeos(EntityManager em) {
        try {
            Query query = em.createQuery("SELECT tc FROM TiposChequeos tc ORDER BY tc.codigo ASC ");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<TiposChequeos> listMotivosDemandas = query.getResultList();
            return listMotivosDemandas;
        } catch (Exception e) {
            System.err.println("ERROR BUSCAR TIPOS CHEQUEOS  " + e);
            return null;
        }
    }
   
    @Override
    public BigInteger contadorChequeosMedicos(EntityManager em, BigInteger secuencia) {
        BigInteger retorno;
        try {
            System.out.println("Persistencia secuencia borrado " + secuencia);
            String sqlQuery = " SELECT COUNT(*)FROM chequeosmedicos cm WHERE cm.tipochequeo = ? ";
            Query query = em.createNativeQuery(sqlQuery);
            query.setParameter(1, secuencia);
            retorno = new BigInteger(query.getSingleResult().toString());
            System.out.println("PERSISTENCIAJUZGADOS CONTADORCHEQUEOSMEDICOS = " + retorno);
            return retorno;
        } catch (Exception e) {
            System.err.println("ERROR PERSISTENCIATIPOSCHEQUEOS CONTADORCHEQUETOSMEDICOS ERROR = " + e);
            retorno = new BigInteger("-1");
            return retorno;
        }
    }

    @Override
    public BigInteger contadorTiposExamenesCargos(EntityManager em, BigInteger secuencia) {
        BigInteger retorno;
        try {
            System.out.println("Persistencia secuencia borrado " + secuencia);
            String sqlQuery = " SELECT COUNT(*)FROM tiposexamenescargos cm WHERE cm.tipochequeo = ? ";
            Query query = em.createNativeQuery(sqlQuery);
            query.setParameter(1, secuencia);
            retorno = new BigInteger(query.getSingleResult().toString());
            System.out.println("PERSISTENCIAJUZGADOS CONTADORCHEQUEOSMEDICOS = " + retorno);
            return retorno;
        } catch (Exception e) {
            System.err.println("ERROR PERSISTENCIATIPOSCHEQUEOS CONTADORCHEQUETOSMEDICOS ERROR = " + e);
            retorno = new BigInteger("-1");
            return retorno;
        }
    }
}
