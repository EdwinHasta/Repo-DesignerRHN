/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Persistencia;

import Entidades.MotivosDefinitivas;
import InterfacePersistencia.PersistenciaMotivosDefinitivasInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Clase Stateless.<br>
 * Clase encargada de realizar operaciones sobre la tabla 'MotivosDefinitivas'
 * de la base de datos.
 *
 * @author betelgeuse
 */
@Stateless
public class PersistenciaMotivosDefinitivas implements PersistenciaMotivosDefinitivasInterface {

    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos.
     */
//    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
//    private EntityManager em;
    @Override
    public void crear(EntityManager em, MotivosDefinitivas motivosDefinitivas) {
        em.clear();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(motivosDefinitivas);
            tx.commit();
        } catch (Exception e) {
            System.out.println("Error PersistenciaMotivosDefinitivas.crear: " + e);
            if (tx.isActive()) {
                tx.rollback();
            }
        }
    }

    @Override
    public void editar(EntityManager em, MotivosDefinitivas motivosDefinitivas) {
        em.clear();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(motivosDefinitivas);
            tx.commit();
        } catch (Exception e) {
            System.out.println("Error PersistenciaMotivosDefinitivas.crear: " + e);
            if (tx.isActive()) {
                tx.rollback();
            }
        }
    }

    @Override
    public void borrar(EntityManager em, MotivosDefinitivas motivosDefinitivas) {
        em.clear();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.remove(em.merge(motivosDefinitivas));
            tx.commit();

        } catch (Exception e) {
            try {
                if (tx.isActive()) {
                    tx.rollback();
                }
            } catch (Exception ex) {
                System.out.println("Error PersistenciaMotivosDefinitivas.borrar: " + e);
            }
        }
    }

    @Override
    public List<MotivosDefinitivas> buscarMotivosDefinitivas(EntityManager em) {
        try {
            Query query = em.createQuery("SELECT md FROM MotivosDefinitivas md ORDER BY md.codigo ASC");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<MotivosDefinitivas> motivoD = query.getResultList();
            return motivoD;
        } catch (Exception e) {
            System.out.println("Error PersistenciaMotivosDefinitivfas.buscarMotivosDefinitivas" + e);
            return null;
        }
    }

    @Override
    public MotivosDefinitivas buscarMotivoDefinitiva(EntityManager em, BigInteger secuenciaME) {
        try {
            return em.find(MotivosDefinitivas.class, secuenciaME);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public BigInteger contadorNovedadesSistema(EntityManager em, BigInteger secuencia) {
        BigInteger retorno;
        try {
            String sqlQuery = "SELECT COUNT(*)FROM novedadessistema WHERE motivodefinitiva = ? ";
            Query query = em.createNativeQuery(sqlQuery);
            query.setParameter(1, secuencia);
            retorno = new BigInteger(query.getSingleResult().toString());
            System.out.println("PERSISTENCIAMOTIVOSDEFINITIVAS CONTADORNOVEDADESSISTEMA = " + retorno);
            return retorno;
        } catch (Exception e) {
            System.err.println("ERROR PERSISTENCIAMOTIVOSDEFINITIVAS CONTADORNOVEDADESSISTEMA  ERROR = " + e);
            retorno = new BigInteger("-1");
            return retorno;
        }
    }

    @Override
    public BigInteger contadorParametrosCambiosMasivos(EntityManager em, BigInteger secuencia) {
        BigInteger retorno;
        try {
            String sqlQuery = "SELECT COUNT(*)FROM parametroscambiosmasivos WHERE retimotivodefinitiva =  ? ";
            Query query = em.createNativeQuery(sqlQuery);
            query.setParameter(1, secuencia);
            retorno = new BigInteger(query.getSingleResult().toString());
            System.out.println("PERSISTENCIAMOTIVOSDEFINITIVAS CONTADORPARAMETROSCAMBIOSMASIVOS = " + retorno);
            return retorno;
        } catch (Exception e) {
            System.err.println("ERROR PERSISTENCIAMOTIVOSDEFINITIVAS CONTADORPARAMETROSCAMBIOSMASIVOS  ERROR = " + e);
            retorno = new BigInteger("-1");
            return retorno;
        }
    }
}
