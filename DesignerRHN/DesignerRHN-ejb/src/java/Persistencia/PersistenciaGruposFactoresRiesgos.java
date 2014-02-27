/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import Entidades.GruposFactoresRiesgos;
import InterfacePersistencia.PersistenciaGruposFactoresRiesgosInterface;
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
public class PersistenciaGruposFactoresRiesgos implements PersistenciaGruposFactoresRiesgosInterface {

    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos.
     */
    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    public void crear(GruposFactoresRiesgos grupoFactoresRiesgos) {
        try {
            em.persist(grupoFactoresRiesgos);
        } catch (Exception e) {
            System.out.println("Error crear PersistenciaGruposFactoresRiesgos : " + e);
        }
    }

    public void editar(GruposFactoresRiesgos grupoFactoresRiesgos) {
        try {
            em.merge(grupoFactoresRiesgos);
        } catch (Exception e) {
            System.out.println("Error editar PersistenciaGruposFactoresRiesgos : " + e);
        }
    }

    public void borrar(GruposFactoresRiesgos grupoFactoresRiesgos) {
        try {
            em.remove(em.merge(grupoFactoresRiesgos));
        } catch (Exception e) {
            System.out.println("Error borrar PersistenciaGruposFactoresRiesgos : " + e);
        }
    }

    public List<GruposFactoresRiesgos> consultarGruposFactoresRiesgos() {
        try {
            Query query = em.createQuery("SELECT t FROM GruposFactoresRiesgos t ORDER BY t.codigo  ASC");
            List<GruposFactoresRiesgos> evalActividades = query.getResultList();
            return evalActividades;
        } catch (Exception e) {
            System.out.println("Error buscarGruposFactoresRiesgos ERROR" + e);
            return null;
        }
    }

    public GruposFactoresRiesgos consultarGrupoFactorRiesgo(BigInteger secuencia) {
        try {
            Query query = em.createQuery("SELECT t FROM GruposFactoresRiesgos t WHERE t.secuencia =:secuencia");
            query.setParameter("secuencia", secuencia);
            GruposFactoresRiesgos grupoFactoresRiesgos = (GruposFactoresRiesgos) query.getSingleResult();
            return grupoFactoresRiesgos;
        } catch (Exception e) {
            System.out.println("Error buscarGrupoFactorRiesgoSecuencia");
            GruposFactoresRiesgos grupoFactoresRiesgos = null;
            return grupoFactoresRiesgos;
        }
    }

    public BigInteger contarSoProActividadesGrupoFactorRiesgo(BigInteger secuencia) {
        BigInteger retorno = new BigInteger("-1");
        try {
            String sqlQuery = "SELECT COUNT(*)FROM soprogactividades WHERE factorriesgo = ?";
            Query query = em.createNativeQuery(sqlQuery);
            query.setParameter(1, secuencia);
            retorno = new BigInteger(query.getSingleResult().toString());
            System.out.println("Contador PersistenciaGruposFactoresRiesgos contarSoProActividadesGrupoFactorRiesgo Retorno " + retorno);
            return retorno;
        } catch (Exception e) {
            System.err.println("Error PersistenciaGruposFactoresRiesgos contarSoProActividadesGrupoFactorRiesgo ERROR : " + e);
            return retorno;
        }
    }

    public BigInteger contarSoIndicadoresGrupoFactorRiesgo(BigInteger secuencia) {
        BigInteger retorno = new BigInteger("-1");
        try {
            String sqlQuery = "SELECT COUNT(*)FROM soindicadores WHERE factorriesgo = ?";
            Query query = em.createNativeQuery(sqlQuery);
            query.setParameter(1, secuencia);
            retorno = new BigInteger(query.getSingleResult().toString());
            System.out.println("Contador PersistenciaGruposFactoresRiesgos contarSoIndicadoresGrupoFactorRiesgo Retorno " + retorno);
            return retorno;
        } catch (Exception e) {
            System.err.println("Error PersistenciaGruposFactoresRiesgos contarSoIndicadoresGrupoFactorRiesgo ERROR : " + e);
            return retorno;
        }
    }

    public BigInteger contarFactoresRiesgoGrupoFactorRiesgo(BigInteger secuencia) {
        BigInteger retorno = new BigInteger("-1");
        try {
            String sqlQuery = "SELECT COUNT(*)FROM factoresriesgos WHERE grupo = ?";
            Query query = em.createNativeQuery(sqlQuery);
            query.setParameter(1, secuencia);
            retorno = new BigInteger(query.getSingleResult().toString());
            System.out.println("Contador PersistenciaGruposFactoresRiesgos contarFactoresRiesgoGrupoFactorRiesgo Retorno " + retorno);
            return retorno;
        } catch (Exception e) {
            System.err.println("Error PersistenciaGruposFactoresRiesgos contarFactoresRiesgoGrupoFactorRiesgo ERROR : " + e);
            return retorno;
        }
    }
}
