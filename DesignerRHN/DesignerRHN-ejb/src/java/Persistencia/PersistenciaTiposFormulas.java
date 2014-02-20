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
    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;
    
    @Override
    public void crear(TiposFormulas tiposFormulas) {
        try {
            em.merge(tiposFormulas);
        } catch (Exception e) {
            System.out.println("El tiposFormulas no exite o esta reservada por lo cual no puede ser modificada (tiposFormulas)");
        }
    }

    @Override
    public void editar(TiposFormulas tiposFormulas) {
        try {
            em.merge(tiposFormulas);
        } catch (Exception e) {
            System.out.println("No se pudo modificar el tiposFormulas");
        }
    }

    @Override
    public void borrar(TiposFormulas tiposFormulas) {
        try {
            em.remove(em.merge(tiposFormulas));
        } catch (Exception e) {
            System.out.println("El tiposFormulas no se ha podido eliminar");
        }
    }

    @Override
    public List<TiposFormulas> tiposFormulas(BigInteger secuenciaOperando, String tipo) {
        try {
            Query query = em.createQuery("SELECT DISTINCT tf FROM TiposFormulas tf, Operandos op WHERE tf.operando.secuencia =:secuenciaOperando and op.tipo=:tipo");
            query.setParameter("secuenciaOperando", secuenciaOperando);
            query.setParameter("tipo", tipo);
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
