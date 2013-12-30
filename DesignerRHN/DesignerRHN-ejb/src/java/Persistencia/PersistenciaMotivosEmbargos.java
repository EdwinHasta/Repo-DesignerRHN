/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import InterfacePersistencia.PersistenciaMotivosEmbargosInterface;
import Entidades.MotivosEmbargos;
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
public class PersistenciaMotivosEmbargos implements PersistenciaMotivosEmbargosInterface {

    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos
     */
    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;
    
     public void crear(MotivosEmbargos motivosEmbargos) {
        em.persist(motivosEmbargos);
    }

    public void editar(MotivosEmbargos motivosEmbargos) {
        em.merge(motivosEmbargos);
    }

    public void borrar(MotivosEmbargos motivosEmbargos) {
        try {
            em.remove(em.merge(motivosEmbargos));
        } catch (Exception e) {
            System.err.println("Error borrando MotivosEmbargos");
            System.out.println(e);
        }
    }

    public MotivosEmbargos buscarMotivoEmbargo(BigInteger secuenciaME) {
        try {
            return em.find(MotivosEmbargos.class, secuenciaME);
        } catch (Exception e) {
            return null;
        }
    }

    public List<MotivosEmbargos> buscarMotivosEmbargos() {
        Query query = em.createQuery("SELECT m FROM MotivosEmbargos m ORDER BY m.codigo ASC");
        List<MotivosEmbargos> listaMotivosEmbargos = query.getResultList();
        return listaMotivosEmbargos;
    }

    public BigInteger contadorEersPrestamos(BigInteger secuencia) {
        BigInteger retorno;
        try {
            String sqlQuery = " SELECT COUNT(*)FROM eersprestamos eer ,  motivosembargos mot WHERE eer.motivoembargo = mot.secuencia AND mot.secuencia =   ? ";
            Query query = em.createNativeQuery(sqlQuery);
            query.setParameter(1, secuencia);
            retorno = new BigInteger(query.getSingleResult().toString());
            System.out.println("PERSISTENCIAMOTIVOSEMBARGOS CONTADOREERSPRESTAMOS = " + retorno);
            return retorno;
        } catch (Exception e) {
            System.err.println("ERROR PERSISTENCIAMOTIVOSEMBARGOS CONTADOREERSPRESTAMOS  ERROR = " + e);
            retorno = new BigInteger("-1");
            return retorno;
        }
    }

    public BigInteger contadorEmbargos(BigInteger secuencia) {
        BigInteger retorno;
        try {
            String sqlQuery = " SELECT COUNT(*)FROM  embargos emb , motivosembargos mot WHERE emb.motivo = mot.secuencia  AND mot.secuencia =  ? ";
            Query query = em.createNativeQuery(sqlQuery);
            query.setParameter(1, secuencia);
            retorno = new BigInteger(query.getSingleResult().toString());
            System.out.println("PERSISTENCIAMOTIVOSEMBARGOS CONTADOREMBARGOS = " + retorno);
            return retorno;
        } catch (Exception e) {
            System.err.println("ERROR PERSISTENCIAMOTIVOSEMBARGOS CONTADOREMBARGOS  ERROR = " + e);
            retorno = new BigInteger("-1");
            return retorno;
        }
    }
    
}
