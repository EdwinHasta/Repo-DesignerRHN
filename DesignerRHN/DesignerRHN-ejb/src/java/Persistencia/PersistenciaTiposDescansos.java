/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Persistencia;

import Entidades.TiposDescansos;
import InterfacePersistencia.PersistenciaTiposDescansosInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Clase Stateless.<br> 
 * Clase encargada de realizar operaciones sobre la tabla 'TiposDescansos'
 * de la base de datos.
 * @author AndresPineda
 */
@Stateless
public class PersistenciaTiposDescansos implements PersistenciaTiposDescansosInterface{

/*    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;*/

    @Override
    public void crear(EntityManager em, TiposDescansos tiposDescansos) {
        try {
            em.persist(tiposDescansos);
        } catch (Exception e) {
            System.out.println("Error crear PersistenciaTiposDescansos");
        }
    }

    @Override
    public void editar(EntityManager em, TiposDescansos tiposDescansos) {
        try {
            em.merge(tiposDescansos);
        } catch (Exception e) {
            System.out.println("Error editar PersistenciaTiposDescansos");
        }
    }

    @Override
    public void borrar(EntityManager em, TiposDescansos tiposDescansos) {
        try {
            em.remove(em.merge(tiposDescansos));
        } catch (Exception e) {
            System.out.println("Error borrar PersistenciaTiposDescansos");
        }
    }

    @Override
    public List<TiposDescansos> consultarTiposDescansos(EntityManager em) {
        try {
            //List<TiposDescansos> tiposDescansos = (List<TiposDescansos>) em.createNamedQuery("TiposDescansos.findAll").getResultList();
            //return tiposDescansos;
            Query query = em.createQuery("SELECT t FROM TiposDescansos t");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<TiposDescansos> listaTiposDescansos = query.getResultList();
            return listaTiposDescansos;
        } catch (Exception e) {
            System.out.println("Error buscarTiposDescansos PersistenciaTiposDescansos");
            return null;
        }
    }

    @Override
    public TiposDescansos consultarTipoDescanso(EntityManager em, BigInteger secuencia) {
        try {
            Query query = em.createQuery("SELECT tp FROM TiposDescansos tp WHERE tp.secuencia = :secuencia");
            query.setParameter("secuencia", secuencia);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            TiposDescansos tiposDescansos = (TiposDescansos) query.getSingleResult();
            return tiposDescansos;
        } catch (Exception e) {
            System.out.println("Error buscarTiposDescansosSecuencia PersistenciaTiposDescansos");
            TiposDescansos tiposDescansos = null;
            return tiposDescansos;
        }
    }
    
    public BigInteger contarVigenciasJornadasTipoDescanso(EntityManager em, BigInteger secuencia) {
        BigInteger retorno = new BigInteger("-1");
        try {
            String sqlQuery = "SELECT COUNT(*)FROM vigenciasjornadas WHERE tipodescanso=?";
            Query query = em.createNativeQuery(sqlQuery);
            query.setParameter(1, secuencia);
            retorno = new BigInteger(query.getSingleResult().toString());
            System.out.println("PersistenciaTiposDescansos contarVigenciasJornadasTipoDescanso contador" + retorno);
            return retorno;
        } catch (Exception e) {
            System.err.println("Error PersistenciaTiposDescansos contarVigenciasJornadasTipoDescanso ERROR :  " + e);
            return retorno;
        }
    }
}
