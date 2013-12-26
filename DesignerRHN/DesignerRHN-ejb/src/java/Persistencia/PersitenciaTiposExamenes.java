/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import InterfacePersistencia.PersistenciaTiposExamenesInterface;
import Entidades.TiposExamenes;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author John Pineda
 */
@Stateless
public class PersitenciaTiposExamenes implements PersistenciaTiposExamenesInterface {

    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;
    
     public void crear(TiposExamenes tiposExamenes) {
        em.persist(tiposExamenes);
    }

    public void editar(TiposExamenes tiposExamenes) {
        em.merge(tiposExamenes);
    }

    public void borrar(TiposExamenes tiposExamenes) {
        em.remove(em.merge(tiposExamenes));
    }

    public TiposExamenes buscarTipoExamen(BigInteger secuenciaTe) {
        try {
            return em.find(TiposExamenes.class, secuenciaTe);
        } catch (Exception e) {
            return null;
        }
    }

    public List<TiposExamenes> buscarTiposExamenes() {
        Query query = em.createQuery("SELECT te FROM TiposExamenes te ORDER BY te.codigo ASC ");
        List<TiposExamenes> listMotivosDemandas = query.getResultList();
        return listMotivosDemandas;

    }

    public BigDecimal contadorTiposExamenesCargos(BigInteger secuencia) {
        BigDecimal retorno = new BigDecimal(-1);
        try {
            String sqlQuery = "SELECT COUNT(*)FROM  tiposexamenescargos tec , tiposexamenes te WHERE tec.tipoexamen=te.secuencia AND te.secuencia = ?";
            Query query = em.createNativeQuery(sqlQuery);
            query.setParameter(1, secuencia);
            retorno = (BigDecimal) query.getSingleResult();
            System.err.println("Contador contadorTiposExamenesCargos persistencia " + retorno);
            return retorno;
        } catch (Exception e) {
            System.out.println("Error PersistenciaTiposExamenes contadorTiposExamenesCargos. " + e);
            return retorno;
        }
    }

    public BigDecimal contadorVigenciasExamenesMedicos(BigInteger secuencia) {
        BigDecimal retorno = new BigDecimal(-1);
        try {
            String sqlQuery = "SELECT COUNT(*)FROM  vigenciasexamenesmedicos vem , tiposexamenes te WHERE vem.tipoexamen=te.secuencia  AND te.secuencia = ?";
            Query query = em.createNativeQuery(sqlQuery);
            query.setParameter(1, secuencia);
            retorno = (BigDecimal) query.getSingleResult();
            System.err.println("Contador PersistenciaTiposExamenes  contadorVigenciasExamenesMedicos  " + retorno);
            return retorno;
        } catch (Exception e) {
            System.out.println("Error PersistenciaTiposExamenes   contadorVigenciasExamenesMedicos. " + e);
            return retorno;
        }
    }
    
}
