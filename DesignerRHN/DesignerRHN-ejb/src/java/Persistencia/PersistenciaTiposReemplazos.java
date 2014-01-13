/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import Entidades.TiposReemplazos;
import InterfacePersistencia.PersistenciaTiposReemplazosInterface;
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
public class PersistenciaTiposReemplazos implements PersistenciaTiposReemplazosInterface {

    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos.
     */
    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    @Override
    public void crear(TiposReemplazos tiposReemplazos) {
        em.persist(tiposReemplazos);
    }

    @Override
    public void editar(TiposReemplazos tiposReemplazos) {
        em.merge(tiposReemplazos);
    }

    @Override
    public void borrar(TiposReemplazos tiposReemplazos) {
        em.remove(em.merge(tiposReemplazos));
    }

    @Override
    public TiposReemplazos buscarTipoReemplazo(BigInteger secuenciaTR) {
        try {
            return em.find(TiposReemplazos.class, secuenciaTR);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<TiposReemplazos> buscarTiposReemplazos() {
        try {
            Query query = em.createQuery("SELECT tR FROM TiposReemplazos tR ORDER BY tr.codigo");
            List<TiposReemplazos> tiposReemplazos = query.getResultList();
            return tiposReemplazos;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public BigInteger contadorEncargaturas(BigInteger secuencia) {
        BigInteger retorno = new BigInteger("-1");
        try {
            String sqlQuery = " SELECT COUNT(*) FROM encargaturas WHERE tiporeemplazo = ?";
            Query query = em.createNativeQuery(sqlQuery);
            query.setParameter(1, secuencia);
            retorno = new BigInteger(query.getSingleResult().toString());
            System.out.println("PERSISTENCIA TIPOS REEMPLAZOS CONTADOR ENCARGATURAS = " + retorno);
            return retorno;
        } catch (Exception e) {
            System.err.println("ERROR PERSISTENCIATIPOSREEMPLAZOS CONTADOR ENCARGATURAS ERROR = " + e);
            return retorno;
        }
    }

    @Override
    public BigInteger contadorProgramacionesTiempos(BigInteger secuencia) {
        BigInteger retorno = new BigInteger("-1");
        try {
            String sqlQuery = "SELECT COUNT(*) FROM programacionestiempos  WHERE tiporeemplazo = ?";
            Query query = em.createNativeQuery(sqlQuery);
            query.setParameter(1, secuencia);
            retorno = new BigInteger(query.getSingleResult().toString());
            System.out.println("PERSISTENCIA TIPOS REEMPLAZOS CONTADOR PROGRAMACIONES TIEMPOS = " + retorno);
            return retorno;
        } catch (Exception e) {
            System.err.println("ERROR PERSISTENCIATIPOSREEMPLAZOS CONTADOR PROGRAMACIONES TIEMPOS ERROR =" + e);
            return retorno;
        }
    }

    @Override
    public BigInteger contadorReemplazos(BigInteger secuencia) {
        BigInteger retorno = new BigInteger("-1");
        try {
            String sqlQuery = "SELECT COUNT(*) FROM reemplazos WHERE tiporeemplazo = ?";
            Query query = em.createNativeQuery(sqlQuery);
            query.setParameter(1, secuencia);
            retorno = new BigInteger(query.getSingleResult().toString());
            System.out.println("PERSISTENCIATIPOSREEMPLAZOS CONTADOR REEMPLAZOS = " + retorno);
            return retorno;
        } catch (Exception e) {
            System.err.println("ERROR PERSISTENCIATIPOSREEMPLAZOS CONTADOR REEMPLAZOS ERROR = " + e);
            return retorno;
        }
    }
}
