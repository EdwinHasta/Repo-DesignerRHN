/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import InterfacePersistencia.PersistenciaSoCondicionesTrabajosInterface;
import Entidades.SoCondicionesTrabajos;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author user
 */
@Stateful
public class PersistenciaSoCondicionesTrabajos implements PersistenciaSoCondicionesTrabajosInterface {

    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos
     */
    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    public void crear(SoCondicionesTrabajos soCondicionesTrabajos) {
        em.persist(soCondicionesTrabajos);
    }

    public void editar(SoCondicionesTrabajos soCondicionesTrabajos) {
        em.merge(soCondicionesTrabajos);
    }

    public void borrar(SoCondicionesTrabajos soCondicionesTrabajos) {
        em.remove(em.merge(soCondicionesTrabajos));
    }

    public SoCondicionesTrabajos buscarSoCondicionTrabajo(BigInteger secuencia) {
        try {
            return em.find(SoCondicionesTrabajos.class, secuencia);
        } catch (Exception e) {
            System.err.println("ERROR PERSISTENCIASOCONDICIONESTRABAJOS ERROR " + e);
            return null;
        }
    }

    public List<SoCondicionesTrabajos> buscarSoCondicionesTrabajos() {
        try {
            Query query = em.createQuery("SELECT soct FROM SoCondicionesTrabajos soct ORDER BY soct.codigo ASC ");
            List<SoCondicionesTrabajos> listaSOCondicionesTrabajos = query.getResultList();
            return listaSOCondicionesTrabajos;
        } catch (Exception e) {
            System.out.println("ERROR AL CARGAR DATOS DE LA ENTIDAD SOCONDICIONESTRABAJOS ERROR " + e);

            return null;
        }
    }

    public BigDecimal contadorInspecciones(BigInteger secuencia) {
        BigDecimal retorno = new BigDecimal(-1);
        try {
            String sqlQuery = "SELECT COUNT(*)FROM socondicionestrabajos st , inspecciones ins WHERE st.secuencia = ins.factorriesgo and st.secuencia = ?";
            Query query = em.createNativeQuery(sqlQuery);
            query.setParameter(1, secuencia);
            retorno = (BigDecimal) query.getSingleResult();
            System.out.println("Contador CONTADORINSPECCIONES persistencia " + retorno);
            return retorno;
        } catch (Exception e) {
            System.out.println("Error PERSISTENCIASOCONDICIONESTRABAJOS contadorInspecciones. " + e);
            return retorno;
        }
    }

    public BigDecimal contadorSoAccidentesMedicos(BigInteger secuencia) {
        BigDecimal retorno = new BigDecimal(-1);
        try {
            String sqlQuery = "SELECT COUNT(*)FROM socondicionestrabajos st , soaccidentesmedicos soa WHERE st.secuencia = soa.factorriesgo  and soa.secuencia = ?";
            Query query = em.createNativeQuery(sqlQuery);
            query.setParameter(1, secuencia);
            retorno = (BigDecimal) query.getSingleResult();
            System.out.println("Contador CONTADORSOACCIDENTESMEDICOS persistencia " + retorno);
            return retorno;
        } catch (Exception e) {
            System.out.println("Error PERSISTENCIASOCONDICIONESTRABAJOS contadorSoAccidentesMedicos. " + e);
            return retorno;
        }
    }

    public BigDecimal contadorSoDetallesPanoramas(BigInteger secuencia) {
        BigDecimal retorno = new BigDecimal(-1);
        try {
            String sqlQuery = "SELECT COUNT(*)FROM socondicionestrabajos st , sodetallespanoramas sop WHERE st.secuencia = sop.condiciontrabajo and sop.secuencia =?";
            Query query = em.createNativeQuery(sqlQuery);
            query.setParameter(1, secuencia);
            retorno = (BigDecimal) query.getSingleResult();
            System.out.println("Contador CONTADORSODETALLESPANORAMAS persistencia " + retorno);
            return retorno;
        } catch (Exception e) {
            System.out.println("Error PERSISTENCIASOCONDICIONESTRABAJOS contadorSoDetallesPanoramas. " + e);
            return retorno;
        }
    }

    public BigDecimal contadorSoExposicionesFr(BigInteger secuencia) {
        BigDecimal retorno = new BigDecimal(-1);
        try {
            String sqlQuery = "SELECT COUNT(*)FROM socondicionestrabajos st , soexposicionesfr  ser WHERE st.secuencia = ser.indicador and ser.secuencia = ?";
            Query query = em.createNativeQuery(sqlQuery);
            query.setParameter(1, secuencia);
            retorno = (BigDecimal) query.getSingleResult();
            System.out.println("Contador CONTADORSOEXPOSICIONESFR persistencia " + retorno);
            return retorno;
        } catch (Exception e) {
            System.out.println("Error PERSISTENCIASOCONDICIONESTRABAJOS contadorSoExposicionesFr. " + e);
            return retorno;
        }
    }
}
