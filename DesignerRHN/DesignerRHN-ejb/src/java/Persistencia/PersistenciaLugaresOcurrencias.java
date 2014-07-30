/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import Entidades.LugaresOcurrencias;
import InterfacePersistencia.PersistenciaLugaresOcurrenciasInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.Query;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;

/**
 *
 * @author user
 */
@Stateless
public class PersistenciaLugaresOcurrencias implements PersistenciaLugaresOcurrenciasInterface {

    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos
     */
//    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
//    private EntityManager em;
    @Override
    public void crear(EntityManager em, LugaresOcurrencias lugaresOcurrencias) {
        em.clear();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(lugaresOcurrencias);
            tx.commit();
        } catch (Exception e) {
            System.out.println("Error PersistenciaHistoriasformulas.crear: " + e);
            if (tx.isActive()) {
                tx.rollback();
            }
        }
    }

    @Override
    public void editar(EntityManager em, LugaresOcurrencias lugaresOcurrencias) {
        em.clear();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(lugaresOcurrencias);
            tx.commit();
        } catch (Exception e) {
            System.out.println("Error PersistenciaHistoriasformulas.crear: " + e);
            if (tx.isActive()) {
                tx.rollback();
            }
        }
    }

    @Override
    public void borrar(EntityManager em, LugaresOcurrencias lugaresOcurrencias) {
        em.clear();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.remove(em.merge(lugaresOcurrencias));
            tx.commit();

        } catch (Exception e) {
            try {
                if (tx.isActive()) {
                    tx.rollback();
                }
            } catch (Exception ex) {
                System.out.println("Error PersistenciaHistoriasformulas.borrar: " + e);
            }
        }
    }

    @Override
    public LugaresOcurrencias buscarLugaresOcurrencias(EntityManager em, BigInteger secuenciaLO) {
        try {
            em.clear();
            return em.find(LugaresOcurrencias.class, secuenciaLO);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<LugaresOcurrencias> buscarLugaresOcurrencias(EntityManager em) {
        try {
            em.clear();
            Query query = em.createQuery("SELECT l FROM LugaresOcurrencias l ORDER BY l.codigo ASC ");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<LugaresOcurrencias> listMotivosDemandas = query.getResultList();
            return listMotivosDemandas;
        } catch (Exception e) {
            System.err.println("ERROR BUSCAR TIPOS CHEQUEOS  " + e);
            return null;
        }

    }

    // NATIVE QUERY
    @Override
    public BigInteger contadorSoAccidentes(EntityManager em, BigInteger secuencia) {
        BigInteger retorno = new BigInteger("-1");
        try {
            em.clear();
            String sqlQuery = "SELECT COUNT(*)FROM soaccidentes WHERE sitioocurrencia = ?";
            Query query = em.createNativeQuery(sqlQuery);
            query.setParameter(1, secuencia);
            retorno = new BigInteger(query.getSingleResult().toString());
            System.err.println("PERSISTENCIA LUGARES OCURRENCIAS CONTADOR SO ACCIDENTES " + retorno);
            return retorno;
        } catch (Exception e) {
            System.out.println("PERSISTENCIA LUGARES OCURRENCIAS CONTADOR SO ACCIDENTES" + e);
            return retorno;
        }
    }
}
