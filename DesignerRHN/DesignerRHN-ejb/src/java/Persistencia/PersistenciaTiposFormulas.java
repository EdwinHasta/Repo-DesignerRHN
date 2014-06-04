/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Persistencia;

import Entidades.TiposFormulas;
import InterfacePersistencia.PersistenciaTiposFormulasInterface;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author user
 */
@Stateless
public class PersistenciaTiposFormulas implements PersistenciaTiposFormulasInterface{

    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos
     */
/*    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;
*/
    
    @Override
    public void crear(EntityManager em, TiposFormulas tiposFormulas) {
        em.clear();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(tiposFormulas);
            tx.commit();
        } catch (Exception e) {
            System.out.println("Error PersistenciaTiposFormulas.crear: " + e);
            if (tx.isActive()) {
                tx.rollback();
            }
        }
    }

    @Override
    public void editar(EntityManager em, TiposFormulas tiposFormulas) {
        em.clear();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(tiposFormulas);
            tx.commit();
        } catch (Exception e) {
            System.out.println("Error PersistenciaTiposFormulas.editar: " + e);
            if (tx.isActive()) {
                tx.rollback();
            }
        }
    }

    @Override
    public void borrar(EntityManager em, TiposFormulas tiposFormulas) {
        em.clear();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.remove(em.merge(tiposFormulas));
            tx.commit();
        } catch (Exception e) {
            System.out.println("Error PersistenciaTiposFormulas.borrar: " + e);
            if (tx.isActive()) {
                tx.rollback();
            }
        }
    }

    @Override
    public List<TiposFormulas> tiposFormulas(EntityManager em, BigInteger secuenciaOperando, String tipo) {
        try {
            Query query = em.createQuery("SELECT DISTINCT tf FROM TiposFormulas tf, Operandos op WHERE tf.operando.secuencia =:secuenciaOperando and op.tipo=:tipo ORDER BY tf.fechafinal DESC");
            query.setParameter("secuenciaOperando", secuenciaOperando);
            query.setParameter("tipo", tipo);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<TiposFormulas> tiposFormulas = query.getResultList();
            List<TiposFormulas> tiposFormulasResult = new ArrayList<TiposFormulas>(tiposFormulas);

            System.out.println("tiposFormulas" + tiposFormulasResult);
            return tiposFormulasResult;
        } catch (Exception e) {
            System.out.println("Error: (tiposFormulas)" + e);
            return null;
        }
    }
}
