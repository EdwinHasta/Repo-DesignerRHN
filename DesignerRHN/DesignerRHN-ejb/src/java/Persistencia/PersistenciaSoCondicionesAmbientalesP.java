/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import InterfacePersistencia.PersistenciaSoCondicionesAmbientalesPInterface;
import Entidades.SoCondicionesAmbientalesP;
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
public class PersistenciaSoCondicionesAmbientalesP implements PersistenciaSoCondicionesAmbientalesPInterface {

    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos
     */
    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    public void crear(SoCondicionesAmbientalesP soCondicionesAmbientalesP) {
        em.persist(soCondicionesAmbientalesP);
    }

    public void editar(SoCondicionesAmbientalesP soCondicionesAmbientalesP) {
        em.merge(soCondicionesAmbientalesP);
    }

    public void borrar(SoCondicionesAmbientalesP soCondicionesAmbientalesP) {
        em.remove(em.merge(soCondicionesAmbientalesP));
    }

    public SoCondicionesAmbientalesP buscarSoCondicionAmbientalP(BigInteger secuenciaSCAP) {
        try {
            return em.find(SoCondicionesAmbientalesP.class, secuenciaSCAP);
        } catch (Exception e) {
            return null;
        }
    }

    public List<SoCondicionesAmbientalesP> buscarSoCondicionesAmbientalesP() {
        try {
            Query query = em.createQuery("SELECT l FROM SoCondicionesAmbientalesP  l ORDER BY l.codigo ASC ");
            List<SoCondicionesAmbientalesP> listSoCondicionesAmbientalesP = query.getResultList();
            return listSoCondicionesAmbientalesP;
        } catch (Exception e) {
            System.err.println("ERROR BUSCAR CLASES SO CONDICIONES AMBIENTALES P :" + e);
            return null;
        }

    }

    public BigDecimal contadorSoAccidentesMedicos(BigInteger secuencia) {
        BigDecimal retorno = new BigDecimal(-1);
        try {
            String sqlQuery = "SELECT COUNT(*)FROM soaccidentesmedicos sam , socondicionesambientalesp sap WHERE sam.condicionambientalp = sap.secuencia and sap.secuencia = ?";
            Query query = em.createNativeQuery(sqlQuery);
            query.setParameter(1, secuencia);
            retorno = (BigDecimal) query.getSingleResult();
            System.err.println("Contador PersistenciaSoCondicionesAmbientalesP contadorSoAccidentesMedicos persistencia " + retorno);
            return retorno;
        } catch (Exception e) {
            System.out.println("Error PersistenciaSoCondicionesAmbientalesP contadorSoAccidentesMedicos. " + e);
            return retorno;
        }
    }

}
