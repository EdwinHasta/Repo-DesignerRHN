/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import Entidades.TiposAsociaciones;
import InterfacePersistencia.PersistenciaTiposAsociacionesInterface;
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

public class PersistenciaTiposAsociaciones implements PersistenciaTiposAsociacionesInterface{

    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    @Override
    public void crear(TiposAsociaciones tiposAsociaciones) {
        try {
            em.persist(tiposAsociaciones);
        } catch (Exception e) {
            System.out.println("Error crear PersistenciaTiposAsociaciones");
        }
    }

    @Override
    public void editar(TiposAsociaciones tiposAsociaciones) {
        try {
            em.merge(tiposAsociaciones);
        } catch (Exception e) {
            System.out.println("Error editar PersistenciaTiposAsociaciones");
        }
    }

    @Override
    public void borrar(TiposAsociaciones tiposAsociaciones) {
        try {
            em.remove(em.merge(tiposAsociaciones));
        } catch (Exception e) {
            System.out.println("Error borrar PersistenciaTiposAsociaciones");
        }
    }

    @Override
    public TiposAsociaciones buscarTipoAsociacion(Object id) {
        try {
            BigInteger secuencia = new BigInteger(id.toString());
            return em.find(TiposAsociaciones.class, secuencia);
        } catch (Exception e) {
            System.out.println("Error buscarTipoAsociacion PersistenciaTiposAsociaciones");
            return null;
        }

    }

    @Override
    public List<TiposAsociaciones> buscarTiposAsociaciones() {
        try {
            List<TiposAsociaciones> tiposAsociaciones = (List<TiposAsociaciones>) em.createNamedQuery("TiposAsociaciones.findAll").getResultList();
            return tiposAsociaciones;
        } catch (Exception e) {
            System.out.println("Error buscarTiposAsociaciones");
            return null;
        }
    }

    @Override
    public TiposAsociaciones buscarTiposAsociacionesSecuencia(BigInteger secuencia) {

        try {
            Query query = em.createQuery("SELECT t FROM TiposAsociaciones t WHERE t.secuencia = :secuencia");
            query.setParameter("secuencia", secuencia);
            TiposAsociaciones tiposAsociaciones = (TiposAsociaciones) query.getSingleResult();
            return tiposAsociaciones;
        } catch (Exception e) {
            System.out.println("Error buscarTiposAsociacionesSecuencia");
            TiposAsociaciones tiposAsociaciones = null;
            return tiposAsociaciones;
        }
    }
}
