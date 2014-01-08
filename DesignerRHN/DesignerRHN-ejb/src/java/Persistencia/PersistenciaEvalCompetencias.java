/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import InterfacePersistencia.PersistenciaEvalCompetenciasInterface;
import Entidades.EvalCompetencias;
import java.math.BigDecimal;
import java.math.BigInteger;
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
public class PersistenciaEvalCompetencias implements PersistenciaEvalCompetenciasInterface {

    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos
     */
    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    public void crear(EvalCompetencias evalCompetencias) {
        em.persist(evalCompetencias);
    }

    public void editar(EvalCompetencias evalCompetencias) {
        em.merge(evalCompetencias);
    }

    public void borrar(EvalCompetencias evalCompetencias) {
        em.remove(em.merge(evalCompetencias));
    }

    public EvalCompetencias buscarEvalCompetencia(BigInteger secuenciaTE) {
        try {
            return em.find(EvalCompetencias.class, secuenciaTE);
        } catch (Exception e) {
            return null;
        }
    }

    public List<EvalCompetencias> buscarEvalCompetencias() {
        Query query = em.createQuery("SELECT ec FROM EvalCompetencias ec  ORDER BY ec.codigo ASC ");
        List<EvalCompetencias> listMotivosDemandas = query.getResultList();
        return listMotivosDemandas;

    }

    public BigInteger contadorCompetenciasCargos(BigInteger secuencia) {
        BigInteger retorno = null;
        try {
            String sqlQuery = " SELECT COUNT(*)FROM competenciascargos ca, evalcompetencias ec WHERE ca.evalcompetencia= ec.secuencia AND ec.secuencia = ?";
            Query query = em.createNativeQuery(sqlQuery);
            query.setParameter(1, secuencia);
            retorno = new BigInteger (query.getSingleResult().toString());
            System.out.println("Contador PERSISTENCIAEVALCOMPETENCIAS ContadorCompetenciasCargos Retorno : " + retorno);
            return retorno;
        } catch (Exception e) {
            System.err.println("Error PERSISTENCIAEVALCOMPETENCIAS ContadorCompetenciasCargos. " + e);
            return retorno;
        }
    }

}
