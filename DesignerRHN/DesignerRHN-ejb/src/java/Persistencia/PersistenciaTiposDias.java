/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Persistencia;

import Entidades.TiposDias;
import InterfacePersistencia.PersistenciaTiposDiasInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

/**
 * Clase Stateless.<br>
 * Clase encargada de realizar operaciones sobre la tabla 'TiposDias' de la base
 * de datos.
 *
 * @author AndresPineda.
 */
@Stateless
public class PersistenciaTiposDias implements PersistenciaTiposDiasInterface {

    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos
     */
    /*    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
     private EntityManager em;
     */
    @Override
    public void crear(EntityManager em, TiposDias tiposDias) {
        em.clear();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(tiposDias);
            tx.commit();
        } catch (Exception e) {
            System.out.println("Error PersistenciaTiposDias.crear: " + e);
            if (tx.isActive()) {
                tx.rollback();
            }
        }
    }

    @Override
    public void editar(EntityManager em, TiposDias tiposDias) {
        em.clear();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(tiposDias);
            tx.commit();
        } catch (Exception e) {
            System.out.println("Error PersistenciaTiposDias.editar: " + e);
            if (tx.isActive()) {
                tx.rollback();
            }
        }
    }

    @Override
    public void borrar(EntityManager em, TiposDias tiposDias) {
        em.clear();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.remove(em.merge(tiposDias));
            tx.commit();
        } catch (Exception e) {
            System.out.println("Error PersistenciaTiposDias.borrar: " + e);
            if (tx.isActive()) {
                tx.rollback();
            }
        }
    }

    @Override
    public TiposDias buscarTipoDia(EntityManager em, BigInteger secuencia) {
        try {
            return em.find(TiposDias.class, secuencia);
        } catch (Exception e) {
            System.out.println("Error PersistenciaTiposDias buscarTipoDia : " + e.toString());
            return null;
        }
    }

    @Override
    public List<TiposDias> buscarTiposDias(EntityManager em) {
        try {
            Query query = em.createQuery("SELECT td FROM TiposDias td ORDER BY td.codigo DESC");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<TiposDias> tiposDias = query.getResultList();
            return tiposDias;
        } catch (Exception e) {
            System.out.println("Error PersistenciaTiposDias buscarTiposDias : " + e.toString());
            return null;
        }
    }

    @Override
    public BigInteger contadorDiasLaborales(EntityManager em, BigInteger secuencia) {
        BigInteger retorno;
        try {
            String sqlQuery = " SELECT COUNT(*)FROM diaslaborables WHERE tipodia = ? ";
            Query query = em.createNativeQuery(sqlQuery);
            query.setParameter(1, secuencia);
            retorno = new BigInteger(query.getSingleResult().toString());
            System.out.println("PERSISTENCIATIPOSDIAS CONTADORDIASLABORALES = " + retorno);
            return retorno;
        } catch (Exception e) {
            System.err.println("ERROR PERSISTENCIATIPOSDIAS CONTADORDIASLABORALES ERROR = " + e);
            retorno = new BigInteger("-1");
            return retorno;
        }
    }

    @Override
    public BigInteger contadorExtrasRecargos(EntityManager em, BigInteger secuencia) {
        BigInteger retorno = new BigInteger("-1");
        try {
            String sqlQuery = " SELECT COUNT(*)FROM extrasrecargos WHERE tipodia = ? ";
            Query query = em.createNativeQuery(sqlQuery);
            query.setParameter(1, secuencia);
            retorno = new BigInteger(query.getSingleResult().toString());
            System.out.println("PERSISTENCIATIPOSDIAS CONTADOREXTRASRECARGOS = " + retorno);
            return retorno;
        } catch (Exception e) {
            System.err.println("ERROR PERSISTENCIATIPOSDIAS CONTADOREXTRASRECARGOS ERROR = " + e);
            retorno = new BigInteger("-1");
            return retorno;
        }
    }
}
