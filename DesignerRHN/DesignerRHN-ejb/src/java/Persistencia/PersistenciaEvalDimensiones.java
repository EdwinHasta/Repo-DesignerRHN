/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import InterfacePersistencia.PersistenciaEvalDimensionesInterface;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import Entidades.EvalDimensiones;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.Query;

/**
 *
 * @author user
 */
@Stateless
public class PersistenciaEvalDimensiones implements PersistenciaEvalDimensionesInterface {

    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos
     */
    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    public void crear(EvalDimensiones evalDimensiones) {
        em.persist(evalDimensiones);
    }

    public void editar(EvalDimensiones evalDimensiones) {
        em.merge(evalDimensiones);
    }

    public void borrar(EvalDimensiones evalDimensiones) {
        em.remove(em.merge(evalDimensiones));
    }

    public EvalDimensiones buscarEvalDimension(BigInteger secuencia) {
        try {
            return em.find(EvalDimensiones.class, secuencia);
        } catch (Exception e) {
            return null;
        }
    }

    public List<EvalDimensiones> buscarEvalDimensiones() {
        try {
            Query query = em.createQuery("SELECT ed FROM EvalDimensiones ed ORDER BY ed.descripcion ");
            List<EvalDimensiones> evalDimensiones = query.getResultList();
            return evalDimensiones;
        } catch (Exception e) {
            System.err.println("PERSISTENCIAEVALDIMENSIONES BUSCAREVALDIMENSIONES ERROR : " + e);
            return null;
        }
    }
    
    public BigInteger contradorEvalPlanillas(BigInteger secuencia) {
        BigInteger retorno;
        try {
            String sqlQuery = "SELECT COUNT(*)FROM evaldimensiones ev, evalplanillas ep  WHERE ep.dimension=ev.secuencia AND ev.secuencia = ? ";
            Query query = em.createNativeQuery(sqlQuery);
            query.setParameter(1, secuencia);
            retorno = new BigInteger(query.getSingleResult().toString());
            System.out.println("PERSISTENCIAEVALDIMENSIONES contradorEvalPlanillas = " + retorno);
            return retorno;
        } catch (Exception e) {
            System.err.println("ERROR PERSISTENCIAEVALDIMENSIONES contradorEvalPlanillas  ERROR = " + e);
            retorno = new BigInteger("-1");
            return retorno;
        }
    }
}
