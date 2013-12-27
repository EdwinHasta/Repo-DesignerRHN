/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import InterfacePersistencia.PersistenciaSoActosInsegurosInterface;
import Entidades.SoActosInseguros;
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
public class PersistenciaSoActosInseguros implements PersistenciaSoActosInsegurosInterface {

    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos.
     */
    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;
    
     public void crear(SoActosInseguros SoActosInseguros) {
        em.persist(SoActosInseguros);
    }

    public void editar(SoActosInseguros SoActosInseguros) {
        em.merge(SoActosInseguros);
    }

    public void borrar(SoActosInseguros SoActosInseguros) {
        em.remove(em.merge(SoActosInseguros));
    }

    public SoActosInseguros buscarSoActoInseguro(BigInteger secuenciaSCAP) {
        try {
            return em.find(SoActosInseguros.class, secuenciaSCAP);
        } catch (Exception e) {
            return null;
        }
    }

    public List<SoActosInseguros> buscarSoActosInseguros() {
        try {
            Query query = em.createQuery("SELECT l FROM SoActosInseguros  l ORDER BY l.codigo ASC ");
            List<SoActosInseguros> listSoCondicionesAmbientalesP = query.getResultList();
            return listSoCondicionesAmbientalesP;
        } catch (Exception e) {
            System.err.println("ERROR BUSCAR CLASES SOACTOSINSEGUROS :" + e);
            return null;
        }

    }

    public BigDecimal contadorSoAccidentesMedicos(BigInteger secuencia) {
        BigDecimal retorno = new BigDecimal(-1);
        try {
            String sqlQuery = "SELECT COUNT(*)FROM soaccidentesmedicos sam , soactosinseguros ssi WHERE sam.actoinseguro = ssi.secuencia and ssi.secuencia =   ?";
            Query query = em.createNativeQuery(sqlQuery);
            query.setParameter(1, secuencia);
            retorno = (BigDecimal) query.getSingleResult();
            System.err.println("Contador PersistenciaSoActosInseguros contadorSoAccidentesMedicos persistencia " + retorno);
            return retorno;
        } catch (Exception e) {
            System.out.println("Error PersistenciaSoActosInseguros contadorSoAccidentesMedicos. " + e);
            return retorno;
        }
    }
}
