/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Persistencia;

import InterfacePersistencia.PersistenciaEvalEvaluadoresInterface;
import Entidades.EvalEvaluadores;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Clase Stateless. <br>
 * Clase encargada de realizar operaciones sobre la tabla 'EvalEvaluadores' de
 * la base de datos.
 *
 * @author betelgeuse
 */
@Stateless
public class PersistenciaEvalEvaluadores implements PersistenciaEvalEvaluadoresInterface {

    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos
     */
    /*@PersistenceContext(unitName = "DesignerRHN-ejbPU")
     private EntityManager em;*/
    @Override
    public void crear(EntityManager em, EvalEvaluadores evalEvaluadores) {
        try {
            em.getTransaction().begin();
            em.persist(evalEvaluadores);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("\n ERROR EN PersistenciaMotivosContratos crear ERROR " + e);
        }
    }

    @Override
    public void editar(EntityManager em, EvalEvaluadores evalEvaluadores) {
        try {
            em.getTransaction().begin();
            em.merge(evalEvaluadores);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("\n ERROR EN PersistenciaEvalEvaluadores editar ERROR " + e);
        }
    }

    @Override
    public void borrar(EntityManager em, EvalEvaluadores evalEvaluadores) {
        try {
            em.getTransaction().begin();
            em.remove(em.merge(evalEvaluadores));
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("\n ERROR EN PersistenciaEvalEvaluadores borrar ERROR " + e);
        }
    }

    @Override
    public EvalEvaluadores buscarEvalEvaluador(EntityManager em, BigInteger secuenciaEvalEvaluadores) {
        try {
            return em.find(EvalEvaluadores.class, secuenciaEvalEvaluadores);
        } catch (Exception e) {
            System.err.println("ERROR PersistenciaMotivosContratos buscarEvalEvaluadores ERROR " + e);
            return null;
        }
    }

    @Override
    public List<EvalEvaluadores> buscarEvalEvaluadores(EntityManager em) {
        try {
            Query query = em.createQuery("SELECT ev FROM EvalEvaluadores ev ORDER BY ev.codigo ");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<EvalEvaluadores> evalEvaluadores = query.getResultList();
            return evalEvaluadores;
        } catch (Exception e) {
            System.out.println("\n ERROR EN PersistenciaEvalEvaluadoress buscarEvalEvaluadores ERROR" + e);
            return null;
        }
    }

    @Override
    public BigInteger verificarBorradoEvalPruebas(EntityManager em, BigInteger secuencia) {
        BigInteger retorno = new BigInteger("-1");
        try {
            Query query = em.createQuery("SELECT count(vp) FROM EvalPruebas vp WHERE vp.evalevaluador.secuencia =:secEvalEvalualores ");
            query.setParameter("secEvalEvalualores", secuencia);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            retorno = new BigInteger(query.getSingleResult().toString());
            System.err.println("PersistenciaEvalEvaluadores retorno ==" + retorno.intValue());
        } catch (Exception e) {
            System.err.println("ERROR EN PersistenciaEvalEvaluadores verificarBorrado ERROR :" + e);
        } finally {
            return retorno;
        }
    }
}
