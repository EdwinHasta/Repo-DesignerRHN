/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import Entidades.TiposAccidentes;
import InterfacePersistencia.PersistenciaTiposAccidentesInterface;
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
public class PersistenciaTiposAccidentes implements PersistenciaTiposAccidentesInterface {

    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos
     */
    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    public void crear(TiposAccidentes tiposAccidentes) {
        em.persist(tiposAccidentes);
    }

    public void editar(TiposAccidentes tiposAccidentes) {
        em.merge(tiposAccidentes);
    }

    public void borrar(TiposAccidentes tiposAccidentes) {
        em.remove(em.merge(tiposAccidentes));
    }

    public TiposAccidentes buscarTipoAccidente(BigInteger secuenciaTA) {
        try {
            return em.find(TiposAccidentes.class, secuenciaTA);
        } catch (Exception e) {
            return null;
        }
    }

    public List<TiposAccidentes> buscarTiposAccidentes() {
        try {
            Query query = em.createQuery("SELECT l FROM TiposAccidentes  l ORDER BY l.codigo ASC ");
            List<TiposAccidentes> listPartesCuerpo = query.getResultList();
            return listPartesCuerpo;
        } catch (Exception e) {
            System.err.println("ERROR BUSCAR ELEMENTOS CAUSAS ACCIDENTES  " + e);
            return null;
        }

    }

    // NATIVE QUERY
    public BigInteger contadorSoAccidentesMedicos(BigInteger secuencia) {
        BigInteger retorno = new BigInteger("-1");
        try {
            String sqlQuery = "SELECT COUNT(*) FROM soaccidentesmedicos WHERE tipoaccidente = ?";
            Query query = em.createNativeQuery(sqlQuery);
            query.setParameter(1, secuencia);
            retorno = (BigInteger) query.getSingleResult();
            System.err.println("TIPOSACCIDENTES CONTADORSOACCIDENTESMEDICOS  " + retorno);
            return retorno;
        } catch (Exception e) {
            System.out.println("TIPOSACCIDENTES  ERROR EN EL CONTADORSOACCIDENTESMEDICOS " + e);
            return retorno;
        }
    }

    // NATIVE QUERY
    public BigInteger contadorAccidentes(BigInteger secuencia) {
        BigInteger retorno = new BigInteger("-1");
        try {
            String sqlQuery = "SELECT COUNT(*) FROM accidentes WHERE se.tipoaccidente = ?";
            Query query = em.createNativeQuery(sqlQuery);
            query.setParameter(1, secuencia);
            retorno = new BigInteger(query.getSingleResult().toString());
            System.err.println("TIPOSACCIDENTES CONTADOR DETALLES EXAMENES  " + retorno);
            return retorno;
        } catch (Exception e) {
            System.out.println("TIPOSACCIDENTES  ERROR ACCIDENTES " + e);
            return retorno;
        }
    }

}
