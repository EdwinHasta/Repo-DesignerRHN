/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import Entidades.IbcsAutoliquidaciones;
import InterfacePersistencia.PersistenciaIbcsAutoliquidacionesInterface;
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
public class PersistenciaIbcsAutoliquidaciones implements PersistenciaIbcsAutoliquidacionesInterface{

    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    /*
     */
    @Override
    public void crear(IbcsAutoliquidaciones autoliquidaciones) {
        try {
            em.persist(autoliquidaciones);
        } catch (Exception e) {
            System.out.println("Error crear PersistenciaIbcsAutoliquidaciones : " + e.toString());
        }
    }

    /*
     */
    @Override
    public void editar(IbcsAutoliquidaciones autoliquidaciones) {
        try {
            em.merge(autoliquidaciones);
        } catch (Exception e) {
            System.out.println("Error editar PersistenciaIbcsAutoliquidaciones : " + e.toString());
        }
    }

    /*
     */
    @Override
    public void borrar(IbcsAutoliquidaciones autoliquidaciones) {
        try {
            em.remove(em.merge(autoliquidaciones));
        } catch (Exception e) {
            System.out.println("Error borrar PersistenciaIbcsAutoliquidaciones : " + e.toString());
        }
    }

    /*
     */
    @Override
    public IbcsAutoliquidaciones buscarIbcAutoliquidacion(Object id) {
        try {
            BigInteger secuencia = new BigInteger(id.toString());
            return em.find(IbcsAutoliquidaciones.class, secuencia);
        } catch (Exception e) {
            System.out.println("Error buscarIbcAutoliquidacion PersistenciaIbcsAutoliquidaciones : " + e.toString());
            return null;
        }

    }

    /*
     */
    @Override
    public List<IbcsAutoliquidaciones> buscarIbcsAutoliquidaciones() {
        try {
            List<IbcsAutoliquidaciones> autoliquidaciones = (List<IbcsAutoliquidaciones>) em.createNamedQuery("IbcsAutoliquidaciones.findAll").getResultList();
            return autoliquidaciones;
        } catch (Exception e) {
            System.out.println("Error buscarIbcsAutoliquidaciones PersistenciaIbcsAutoliquidaciones : " + e.toString());
            return null;
        }
    }

    @Override
    public IbcsAutoliquidaciones buscarIbcAutoliquidacionSecuencia(BigInteger secuencia) {
        try {
            Query query = em.createQuery("SELECT ibc FROM IbcsAutoliquidaciones ibc WHERE ibc.secuencia = :secuencia");
            query.setParameter("secuencia", secuencia);
            IbcsAutoliquidaciones autoliquidaciones = (IbcsAutoliquidaciones) query.getSingleResult();
            return autoliquidaciones;
        } catch (Exception e) {
            System.out.println("Error buscarIbcAutoliquidacionSecuencia PersistenciaIbcsAutoliquidaciones : " + e.toString());
            IbcsAutoliquidaciones autoliquidaciones = null;
            return autoliquidaciones;
        }
    }
    
    @Override
    public List<IbcsAutoliquidaciones> buscarIbcsAutoliquidacionesTipoEntidadEmpleado(BigInteger secuenciaTE, BigInteger secuenciaEmpl) {
        try {
            Query query = em.createQuery("SELECT ibc FROM IbcsAutoliquidaciones ibc WHERE ibc.tipoentidad.secuencia = :secuenciaTE AND ibc.empleado.secuencia = :secuenciaEmpl");
            query.setParameter("secuenciaTE", secuenciaTE);
            query.setParameter("secuenciaEmpl", secuenciaEmpl);
            List<IbcsAutoliquidaciones> autoliquidaciones = (List<IbcsAutoliquidaciones>)query.getResultList();
            return autoliquidaciones;
        } catch (Exception e) {
            System.out.println("Error buscarIbcsAutoliquidacionesTipoEntidadEmpleado PersistenciaIbcsAutoliquidaciones : " + e.toString());
            return null;
        }
    }
}