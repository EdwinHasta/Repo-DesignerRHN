/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import InterfacePersistencia.PersistenciaPartesCuerpoInterface;
import Entidades.PartesCuerpo;
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
public class PersistenciaPartesCuerpo implements PersistenciaPartesCuerpoInterface {

    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos
     */
    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    public void crear(PartesCuerpo partesCuerpo) {
        em.persist(partesCuerpo);
    }

    public void editar(PartesCuerpo partesCuerpo) {
        em.merge(partesCuerpo);
    }

    public void borrar(PartesCuerpo partesCuerpo) {
        em.remove(em.merge(partesCuerpo));
    }

    public PartesCuerpo buscarParteCuerpo(BigInteger secuenciaPC) {
        try {
            return em.find(PartesCuerpo.class, secuenciaPC);
        } catch (Exception e) {
            return null;
        }
    }

    public List<PartesCuerpo> buscarPartesCuerpo() {
        try {
            Query query = em.createQuery("SELECT l FROM PartesCuerpo  l ORDER BY l.codigo ASC ");
            List<PartesCuerpo> listPartesCuerpo = query.getResultList();
            return listPartesCuerpo;
        } catch (Exception e) {
            System.err.println("ERROR BUSCAR ELEMENTOS CAUSAS ACCIDENTES  " + e);
            return null;
        }

    }

    // NATIVE QUERY
    public BigDecimal contadorSoAccidentesMedicos(BigInteger secuencia) {
        BigDecimal retorno = new BigDecimal(-1);
        try {
            String sqlQuery = "SELECT COUNT(*) FROM soaccidentesmedicos so , partescuerpo pc WHERE so.parte = pc.secuencia and pc.secuencia =  ?";
            Query query = em.createNativeQuery(sqlQuery);
            query.setParameter(1, secuencia);
            retorno = (BigDecimal) query.getSingleResult();
            System.err.println("PARTESCUERPO CONTADORSOACCIDENTESMEDICOS  " + retorno);
            return retorno;
        } catch (Exception e) {
            System.out.println("PARTESCUERPO  ERROR EN EL CONTADORSOACCIDENTESMEDICOS " + e);
            return retorno;
        }
    }

    // NATIVE QUERY
    public BigDecimal contadorDetallesExamenes(BigInteger secuencia) {
        BigDecimal retorno = new BigDecimal(-1);
        try {
            String sqlQuery = "SELECT COUNT(*) FROM sodetallesexamenes se , partescuerpo pc WHERE se.partecuerpo = pc.secuencia and pc.secuencia =  ?";
            Query query = em.createNativeQuery(sqlQuery);
            query.setParameter(1, secuencia);
            retorno = (BigDecimal) query.getSingleResult();
            System.err.println("PARTESCUERPO CONTADOR DETALLES EXAMENES  " + retorno);
            return retorno;
        } catch (Exception e) {
            System.out.println("PARTESCUERPO  ERROR EN EL DETALLES EXAMENES " + e);
            return retorno;
        }
    }

    public BigDecimal contadorSoDetallesRevisiones(BigInteger secuencia) {
        BigDecimal retorno = new BigDecimal(-1);
        try {
            String sqlQuery = "SELECT COUNT(*) FROM sodetallesrevisiones sr , partescuerpo pc WHERE sr.organo = pc.secuencia and pc.secuencia =  ?";
            Query query = em.createNativeQuery(sqlQuery);
            query.setParameter(1, secuencia);
            retorno = (BigDecimal) query.getSingleResult();
            System.err.println("PARTESCUERPO CONTADOR SO DETALLES REVISIONES  " + retorno);
            return retorno;
        } catch (Exception e) {
            System.out.println("PARTESCUERPO  ERROR EN EL CONTADORSODETALLESREVISIONES " + e);
            return retorno;
        }
    }

}
