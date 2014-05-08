/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Persistencia;

import Entidades.TiposConstantes;
import InterfacePersistencia.PersistenciaTiposConstantesInterface;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author user
 */
@Stateless
public class PersistenciaTiposConstantes implements PersistenciaTiposConstantesInterface{

    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos
     */
/*    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;
*/
    
    @Override
    public void crear(EntityManager em, TiposConstantes tiposConstantes) {
        try {
            em.merge(tiposConstantes);
        } catch (Exception e) {
            System.out.println("El tiposConstantes no exite o esta reservada por lo cual no puede ser modificada (tiposConstantes)");
        }
    }

    @Override
    public void editar(EntityManager em, TiposConstantes tiposConstantes) {
        try {
            em.merge(tiposConstantes);
        } catch (Exception e) {
            System.out.println("No se pudo modificar el tiposConstantes");
        }
    }

    @Override
    public void borrar(EntityManager em, TiposConstantes tiposConstantes) {
        try {
            em.remove(em.merge(tiposConstantes));
        } catch (Exception e) {
            System.out.println("El tiposConstantes no se ha podido eliminar");
        }
    }

    @Override
    public List<TiposConstantes> tiposConstantes(EntityManager em, BigInteger secuenciaOperando, String tipo) {
        try {
            Query query = em.createQuery("SELECT DISTINCT tf FROM TiposConstantes tf, Operandos op WHERE tf.operando.secuencia =:secuenciaOperando and op.tipo=:tipo ORDER BY tf.fechafinal DESC");
            query.setParameter("secuenciaOperando", secuenciaOperando);
            query.setParameter("tipo", tipo);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<TiposConstantes> tiposConstantes = query.getResultList();
            List<TiposConstantes> tiposConstantesResult = new ArrayList<TiposConstantes>(tiposConstantes);

            System.out.println("tiposConstantes" + tiposConstantesResult);
            return tiposConstantesResult;
        } catch (Exception e) {
            System.out.println("Error: (tiposConstantes)" + e);
            return null;
        }
    }
}