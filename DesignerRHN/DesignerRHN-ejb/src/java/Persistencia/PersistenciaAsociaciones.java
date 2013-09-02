/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import Entidades.Asociaciones;
import InterfacePersistencia.PersistenciaAsociacionesInterface;
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
public class PersistenciaAsociaciones implements PersistenciaAsociacionesInterface{

    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    @Override
    public void crear(Asociaciones asociaciones) {
        try {
            em.persist(asociaciones);
        } catch (Exception e) {
            System.out.println("Error crear PersistenciaAsociaciones");
        }
    }

    @Override
    public void editar(Asociaciones asociaciones) {
        try {
            em.merge(asociaciones);
        } catch (Exception e) {
            System.out.println("Error editar PersistenciaAsociaciones");
        }
    }

    @Override
    public void borrar(Asociaciones asociaciones) {
        try {
            em.remove(em.merge(asociaciones));
        } catch (Exception e) {
            System.out.println("Error borrar PersistenciaAsociaciones");
        }
    }

    @Override
    public Asociaciones buscarAsociacion(Object id) {
        try {
            BigInteger secuencia = new BigInteger(id.toString());
            return em.find(Asociaciones.class, secuencia);
        } catch (Exception e) {
            System.out.println("Error buscarAsociaciones PersistenciaAsociaciones");
            return null;
        }

    }

    @Override
    public List<Asociaciones> buscarAsociaciones() {
        try {
            List<Asociaciones> asociaciones = (List<Asociaciones>) em.createNamedQuery("Asociaciones.findAll").getResultList();
            return asociaciones;
        } catch (Exception e) {
            System.out.println("Error buscarAsociaciones");
            return null;
        }
    }

    @Override
    public Asociaciones buscarAsociacionesSecuencia(BigInteger secuencia) {

        try {
            Query query = em.createQuery("SELECT t FROM Asociaciones t WHERE t.secuencia = :secuencia");
            query.setParameter("secuencia", secuencia);
            Asociaciones asociaciones = (Asociaciones) query.getSingleResult();
            return asociaciones;
        } catch (Exception e) {
            System.out.println("Error buscarAsociacionesSecuencia");
            Asociaciones asociaciones = null;
            return asociaciones;
        }
    }
}
