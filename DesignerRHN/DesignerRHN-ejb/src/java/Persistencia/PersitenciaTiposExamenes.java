/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Persistencia;

import InterfacePersistencia.PersistenciaTiposExamenesInterface;
import Entidades.TiposExamenes;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Clase Stateless.<br>
 * Clase encargada de realizar operaciones sobre la tabla 'TiposExamenes' de la
 * base de datos.
 *
 * @author John Pineda
 */
@Stateless
public class PersitenciaTiposExamenes implements PersistenciaTiposExamenesInterface {

    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos.
     */
    /*    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
     private EntityManager em;
     */
    @Override
    public void crear(EntityManager em, TiposExamenes tiposExamenes) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(tiposExamenes);
            tx.commit();
        } catch (Exception e) {
            System.out.println("Error PersitenciaTiposExamenes.crear: " + e);
            if (tx.isActive()) {
                tx.rollback();
            }
        }
    }

    @Override
    public void editar(EntityManager em, TiposExamenes tiposExamenes) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(tiposExamenes);
            tx.commit();
        } catch (Exception e) {
            System.out.println("Error PersitenciaTiposExamenes.editar: " + e);
            if (tx.isActive()) {
                tx.rollback();
            }
        }
    }

    @Override
    public void borrar(EntityManager em, TiposExamenes tiposExamenes) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.remove(em.merge(tiposExamenes));
            tx.commit();
        } catch (Exception e) {
            System.out.println("Error PersitenciaTiposExamenes.borrar: " + e);
            if (tx.isActive()) {
                tx.rollback();
            }
        }
    }

    @Override
    public TiposExamenes buscarTipoExamen(EntityManager em, BigInteger secuencia) {
        try {
            return em.find(TiposExamenes.class, secuencia);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<TiposExamenes> buscarTiposExamenes(EntityManager em) {
        Query query = em.createQuery("SELECT te FROM TiposExamenes te ORDER BY te.codigo ASC ");
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        List<TiposExamenes> listMotivosDemandas = query.getResultList();
        return listMotivosDemandas;

    }

    @Override
    public BigInteger contadorTiposExamenesCargos(EntityManager em, BigInteger secuencia) {
        BigInteger retorno = new BigInteger("-1");
        try {
            String sqlQuery = "SELECT COUNT(*)FROM  tiposexamenescargos tec , tiposexamenes te WHERE tec.tipoexamen=te.secuencia AND te.secuencia = ?";
            Query query = em.createNativeQuery(sqlQuery);
            query.setParameter(1, secuencia);
            retorno = (BigInteger) query.getSingleResult();
            System.err.println("Contador contadorTiposExamenesCargos persistencia " + retorno);
            return retorno;
        } catch (Exception e) {
            System.out.println("Error PersistenciaTiposExamenes contadorTiposExamenesCargos. " + e);
            return retorno;
        }
    }

    @Override
    public BigInteger contadorVigenciasExamenesMedicos(EntityManager em, BigInteger secuencia) {
        BigInteger retorno = new BigInteger("-1");
        try {
            String sqlQuery = "SELECT COUNT(*)FROM  vigenciasexamenesmedicos vem , tiposexamenes te WHERE vem.tipoexamen=te.secuencia  AND te.secuencia = ?";
            Query query = em.createNativeQuery(sqlQuery);
            query.setParameter(1, secuencia);
            retorno = (BigInteger) query.getSingleResult();
            System.err.println("Contador PersistenciaTiposExamenes  contadorVigenciasExamenesMedicos  " + retorno);
            return retorno;
        } catch (Exception e) {
            System.out.println("Error PersistenciaTiposExamenes   contadorVigenciasExamenesMedicos. " + e);
            return retorno;
        }
    }
}
