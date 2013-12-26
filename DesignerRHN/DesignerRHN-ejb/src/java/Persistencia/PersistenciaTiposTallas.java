/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Persistencia;

import InterfacePersistencia.PersistenciaTiposTallasInterface;
import Entidades.TiposTallas;
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
public class PersistenciaTiposTallas implements PersistenciaTiposTallasInterface {

    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos.
     */
    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;
    
    public void crear(TiposTallas tiposTallas) {
        em.persist(tiposTallas);
    }

    public void editar(TiposTallas tiposTallas) {
        em.merge(tiposTallas);
    }

    public void borrar(TiposTallas tiposTallas) {
        em.remove(em.merge(tiposTallas));
    }

    public TiposTallas buscarTipoTalla(BigInteger secuenciaTT) {
        try {
            return em.find(TiposTallas.class, secuenciaTT);
        } catch (Exception e) {
            return null;
        }
    }

    public List<TiposTallas> buscarTiposTallas() {
        Query query = em.createQuery("SELECT m FROM TiposTallas m ORDER BY m.codigo ASC ");
        List<TiposTallas> listMotivosDemandas = query.getResultList();
        return listMotivosDemandas;

    }

    public BigDecimal contadorElementos(BigInteger secuencia) {
        BigDecimal retorno = new BigDecimal(-1);
        try {
            String sqlQuery = " SELECT COUNT(*)FROM  elementos e , tipostallas tp WHERE e.tipotalla = tp.secuencia AND tp.secuencia = ?";
            Query query = em.createNativeQuery(sqlQuery);
            query.setParameter(1, secuencia);
            retorno = (BigDecimal) query.getSingleResult();
            System.err.println("Contador contadorElementos persistencia " + retorno);
            return retorno;
        } catch (Exception e) {
            System.out.println("Error contadorElementos. " + e);
            return retorno;
        }
    }

    public BigDecimal contadorVigenciasTallas(BigInteger secuencia) {
        BigDecimal retorno = new BigDecimal(-1);
        try {
            String sqlQuery = "SELECT COUNT(*)FROM  vigenciastallas vt , tipostallas tp WHERE vt.tipotalla = tp.secuencia AND tp.secuencia = ?";
            Query query = em.createNativeQuery(sqlQuery);
            query.setParameter(1, secuencia);
            retorno = (BigDecimal) query.getSingleResult();
            System.err.println("Contador PersistenciaTiposTallas contadorVigenciasTallas persistencia " + retorno);
            return retorno;
        } catch (Exception e) {
            System.out.println("Error PersistenciaTiposTallas  contadorVigenciasTallas. " + e);
            return retorno;
        }
    }
}
