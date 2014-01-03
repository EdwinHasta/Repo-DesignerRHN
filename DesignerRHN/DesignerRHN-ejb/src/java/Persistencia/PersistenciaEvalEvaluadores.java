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
 * Clase encargada de realizar operaciones sobre la tabla 'EvalEvaluadores'
 * de la base de datos.
 * @author betelgeuse
 */
@Stateless
public class PersistenciaEvalEvaluadores implements PersistenciaEvalEvaluadoresInterface {
    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos
     */
    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    @Override
    public void crear(EvalEvaluadores evalEvaluadores) {
        try {
            em.persist(evalEvaluadores);
        } catch (Exception e) {
            System.out.println("\n ERROR EN PersistenciaMotivosContratos crear ERROR " + e);
        }
    }

    @Override
    public void editar(EvalEvaluadores evalEvaluadores) {
        try {
            em.merge(evalEvaluadores);
        } catch (Exception e) {
            System.out.println("\n ERROR EN PersistenciaEvalEvaluadores editar ERROR " + e);
        }
    }

    @Override
    public void borrar(EvalEvaluadores evalEvaluadores) {
        try {
            em.remove(em.merge(evalEvaluadores));
        } catch (Exception e) {
            System.out.println("\n ERROR EN PersistenciaEvalEvaluadores borrar ERROR " + e);
        }
    }

    @Override
    public EvalEvaluadores buscarEvalEvaluador(BigInteger secuenciaEvalEvaluadores) {
        try {
            return em.find(EvalEvaluadores.class, secuenciaEvalEvaluadores);
        } catch (Exception e) {
            System.err.println("ERROR PersistenciaMotivosContratos buscarEvalEvaluadores ERROR " + e);
            return null;
        }
    }

    @Override
    public List<EvalEvaluadores> buscarEvalEvaluadores() {
        try {
            Query query = em.createQuery("SELECT ev FROM EvalEvaluadores ev ORDER BY ev.codigo ");
            List<EvalEvaluadores> evalEvaluadores = query.getResultList();
            return evalEvaluadores;
        } catch (Exception e) {
            System.out.println("\n ERROR EN PersistenciaEvalEvaluadoress buscarEvalEvaluadores ERROR" + e);
            return null;
        }
    }

    @Override
    public Long verificarBorradoEvalPruebas(BigInteger secuencia) {
        Long retorno = new Long(-1);
        try {
            Query query = em.createQuery("SELECT count(vp) FROM EvalPruebas vp WHERE vp.evalevaluador.secuencia =:secEvalEvalualores ");
            query.setParameter("secEvalEvalualores", secuencia);
            retorno = (Long) query.getSingleResult();
            System.err.println("PersistenciaEvalEvaluadores retorno ==" + retorno.intValue());
        } catch (Exception e) {
            System.err.println("ERROR EN PersistenciaEvalEvaluadores verificarBorrado ERROR :" + e);
        } finally {
            return retorno;
        }
    }
}
