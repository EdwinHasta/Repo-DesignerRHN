/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Persistencia;

import Entidades.DependenciasOperandos;
import InterfacePersistencia.PersistenciaDependenciasOperandosInterface;
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
public class PersistenciaDependenciasOperandos implements PersistenciaDependenciasOperandosInterface{

    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos
     */
    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;
    
    @Override
    public void crear(DependenciasOperandos dependenciasOperandos) {
        try {
            em.merge(dependenciasOperandos);
        } catch (Exception e) {
            System.out.println("El dependenciasOperandos no exite o esta reservada por lo cual no puede ser modificada (dependenciasOperandos)");
        }
    }

    @Override
    public void editar(DependenciasOperandos dependenciasOperandos) {
        try {
            em.merge(dependenciasOperandos);
        } catch (Exception e) {
            System.out.println("No se pudo modificar el dependenciasOperandos");
        }
    }

    @Override
    public void borrar(DependenciasOperandos dependenciasOperandos) {
        try {
            em.remove(em.merge(dependenciasOperandos));
        } catch (Exception e) {
            System.out.println("El dependenciasOperandos no se ha podido eliminar");
        }
    }

    @Override
    public List<DependenciasOperandos> dependenciasOperandos(BigInteger secuenciaOperando) {
        try {
            Query query = em.createQuery("SELECT tf FROM DependenciasOperandos tf, Operandos op WHERE tf.operando.secuencia = :secuenciaOperando ORDER BY tf.codigo DESC");
            query.setParameter("secuenciaOperando", secuenciaOperando);
            List<DependenciasOperandos> dependenciasOperandosResult = new ArrayList<DependenciasOperandos>();
            dependenciasOperandosResult = query.getResultList();
            return dependenciasOperandosResult;
        } catch (Exception e) {
            System.out.println("Error: (dependenciasOperandos)" + e);
            return null;
        }
    }
    
    @Override
    public String nombreOperandos(int codigo) {
        try {
            Query query = em.createQuery("SELECT op.nombre FROM Operandos op WHERE op.codigo = :codigo");
            query.setParameter("codigo", codigo);
            String nombre;
            nombre = (String) query.getSingleResult();
            return nombre;
        } catch (Exception e) {
            System.out.println("Error: (nombreOperandos)" + e);
            return null;
        }
    }
}