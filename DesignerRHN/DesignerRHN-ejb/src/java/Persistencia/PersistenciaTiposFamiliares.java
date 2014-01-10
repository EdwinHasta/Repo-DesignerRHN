/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Persistencia;

import InterfacePersistencia.PersistenciaTiposFamiliaresInterface;
import Entidades.TiposFamiliares;
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
public class PersistenciaTiposFamiliares implements PersistenciaTiposFamiliaresInterface {
  /**
     * Atributo EntityManager. Representa la comunicación con la base de datos
     */
    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;
    
     public void crear(TiposFamiliares tiposFamiliares) {
        em.persist(tiposFamiliares);
    }

    public void editar(TiposFamiliares tiposFamiliares) {
        em.merge(tiposFamiliares);
    }

    public void borrar(TiposFamiliares tiposFamiliares) {
        em.remove(em.merge(tiposFamiliares));
    }

    public TiposFamiliares buscarTiposFamiliares(BigInteger secuenciaTF) {
        try {
            return em.find(TiposFamiliares.class, secuenciaTF);
        } catch (Exception e) {
            return null;
        }
    }

    public List<TiposFamiliares> buscarTiposFamiliares() {
        Query query = em.createQuery("SELECT te FROM TiposFamiliares te ORDER BY te.codigo ASC ");
        List<TiposFamiliares> listMotivosDemandas = query.getResultList();
        return listMotivosDemandas;

    }

    public BigDecimal contadorHvReferencias(BigInteger secuencia) {
        BigDecimal retorno = new BigDecimal(-1);
        try {
            String sqlQuery = "SELECT COUNT(*) FROM tiposfamiliares tf , hvreferencias hvr WHERE hvr.parentesco = tf.secuencia AND tf.secuencia =?";
            Query query = em.createNativeQuery(sqlQuery);
            query.setParameter(1, secuencia);
            retorno = (BigDecimal) query.getSingleResult();
            System.err.println("Contador PERSISTENCIATIPOSFAMILIARES contadorHvReferencias  " + retorno);
            return retorno;
        } catch (Exception e) {
            System.out.println("Error PERSISTENCIATIPOSFAMILIARES  contadorHvReferencias. " + e);
            return retorno;
        }
    }

    public BigDecimal asignarNuevoCodigo() {
        BigDecimal siguiente = new BigDecimal(-1);
        try {
            String sqlQuery = "SELECT MAX(tf.codigo)FROM tiposfamiliares tf";
            Query query = em.createNativeQuery(sqlQuery);
            siguiente = (BigDecimal) query.getSingleResult();
            System.err.println("PERSISTENCIATIPOSFAMILIARES asignarNuevoCodigo  " + siguiente);
            return siguiente;
        } catch (Exception e) {
            System.out.println("Error PERSISTENCIATIPOSFAMILIARES  asignarNuevoCodigo. " + e);
            return siguiente;
        }
    }
}
