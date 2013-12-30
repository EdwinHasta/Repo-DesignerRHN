/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import InterfacePersistencia.PersistenciaLesionesInterface;
import Entidades.Lesiones;
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
public class PersistenciaLesiones implements PersistenciaLesionesInterface {

    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos.
     */
    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    public void crear(Lesiones lesiones) {
        em.persist(lesiones);
    }

    public void editar(Lesiones lesiones) {
        em.merge(lesiones);
    }

    public void borrar(Lesiones lesiones) {
        em.remove(em.merge(lesiones));
    }

    public Lesiones buscarlesion(BigInteger secuenciaL) {
        try {
            return em.find(Lesiones.class, secuenciaL);
        } catch (Exception e) {
            return null;
        }
    }

    public List<Lesiones> buscarlesiones() {
        try {
            Query query = em.createQuery("SELECT l FROM Lesiones l ORDER BY l.codigo ASC ");
            List<Lesiones> listMotivosDemandas = query.getResultList();
            return listMotivosDemandas;
        } catch (Exception e) {
            System.err.println("ERROR BUSCAR TIPOS CHEQUEOS  " + e);
            return null;
        }

    }

    // NATIVE QUERY
    public BigDecimal contadorDetallesLicensias(BigInteger secuencia) {
        BigDecimal retorno = new BigDecimal(-1);
        try {
            String sqlQuery = "SELECT COUNT(*)FROM  detalleslicencias dl , lesiones l WHERE dl.lesion=l.secuencia AND l.secuencia= ?";
            Query query = em.createNativeQuery(sqlQuery);
            query.setParameter(1, secuencia);
            retorno = (BigDecimal) query.getSingleResult();
            System.err.println("PERSISTENCIALESIONES CONTADOR DETALLES LICENSIAS  " + retorno);
            return retorno;
        } catch (Exception e) {
            System.out.println("PERSISTENCIALESIONES  ERROR EN EL CONTADOR DETALLES LICENSIAS" + e);
            return retorno;
        }
    }

    // NATIVE QUERY
    public BigDecimal contadorSoAccidentesDomesticos(BigInteger secuencia) {
        BigDecimal retorno = new BigDecimal(-1);
        try {
            String sqlQuery = "SELECT COUNT(*)FROM soaccidentesmedicos sm , lesiones l WHERE sm.lesion = l.secuencia AND l.secuencia= ?";
            Query query = em.createNativeQuery(sqlQuery);
            query.setParameter(1, secuencia);
            retorno = (BigDecimal) query.getSingleResult();
            System.err.println("PERSISTENCIALESIONES CONTADOR SO ACCIDENTES DOMESTICOS " + retorno);
            return retorno;
        } catch (Exception e) {
            System.out.println("PERSISTENCIALESIONES  ERROR EN EL CONTADOR SO ACCIDENTES DOMESTICOS" + e);
            return retorno;
        }
    }
}
