/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import Entidades.TiposFunciones;
import InterfacePersistencia.PersistenciaTiposFuncionesInterface;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Clase Stateless.<br>
 * Clase encargada de realizar operaciones sobre la tabla 'Operandos' de la base
 * de datos.
 *
 * @author Victor Algarin.
 */
@Stateless
public class PersistenciaTiposFunciones implements PersistenciaTiposFuncionesInterface {

    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos
     */
    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;
    
    @Override
    public void crear(TiposFunciones tiposFunciones) {
        try {
            em.merge(tiposFunciones);
        } catch (Exception e) {
            System.out.println("El tiposFunciones no exite o esta reservada por lo cual no puede ser modificada (tiposFunciones)");
        }
    }

    @Override
    public void editar(TiposFunciones tiposFunciones) {
        try {
            em.merge(tiposFunciones);
        } catch (Exception e) {
            System.out.println("No se pudo modificar el tiposFunciones");
        }
    }

    @Override
    public void borrar(TiposFunciones tiposFunciones) {
        try {
            em.remove(em.merge(tiposFunciones));
        } catch (Exception e) {
            System.out.println("El tiposFunciones no se ha podido eliminar");
        }
    }

    @Override
    public List<TiposFunciones> tiposFunciones(BigInteger secuenciaOperando, String tipo) {
        try {
            Query query = em.createQuery("SELECT DISTINCT tf FROM TiposFunciones tf, Operandos op WHERE tf.operando.secuencia =:secuenciaOperando and op.tipo=:tipo");
            query.setParameter("secuenciaOperando", secuenciaOperando);
            query.setParameter("tipo", tipo);
            List<TiposFunciones> tiposFunciones = query.getResultList();
            List<TiposFunciones> tiposFuncionesResult = new ArrayList<TiposFunciones>(tiposFunciones);

            System.out.println("tiposFunciones" + tiposFuncionesResult);
            return tiposFuncionesResult;
        } catch (Exception e) {
            System.out.println("Error: (tiposFunciones)" + e);
            return null;
        }
    }
}
