/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Persistencia;

import Entidades.SectoresEvaluaciones;
import InterfacePersistencia.PersistenciaSectoresEvaluacionesInterface;
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
public class PersistenciaSectoresEvaluaciones implements PersistenciaSectoresEvaluacionesInterface {

   /**
     * Atributo EntityManager. Representa la comunicación con la base de datos.
     */
//    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
//    private EntityManager em;

    @Override
    public void crear(EntityManager em, SectoresEvaluaciones sectoresEvaluaciones) {
        em.persist(sectoresEvaluaciones);
    }

    @Override
    public void editar(EntityManager em, SectoresEvaluaciones sectoresEvaluaciones) {
        em.merge(sectoresEvaluaciones);
    }

    @Override
    public void borrar(EntityManager em, SectoresEvaluaciones sectoresEvaluaciones) {
        em.remove(em.merge(sectoresEvaluaciones));
    }

    @Override
    public List<SectoresEvaluaciones> consultarSectoresEvaluaciones(EntityManager em) {
        try {
            Query query = em.createQuery("SELECT l FROM SectoresEvaluaciones  l ORDER BY l.codigo ASC ");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<SectoresEvaluaciones> listTiposViajeros = query.getResultList();
            return listTiposViajeros;
        } catch (Exception e) {
            System.err.println("ERROR PersistenciaTiposViajeros ConsultarTiposViajeros ERROR :" + e);
            return null;
        }

    }

    @Override
    public SectoresEvaluaciones consultarSectorEvaluacion(EntityManager em, BigInteger secuencia) {

        try {
            Query query = em.createQuery("SELECT mr FROM SectoresEvaluaciones mr WHERE mr.secuencia = :secuencia");
            query.setParameter("secuencia", secuencia);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            SectoresEvaluaciones motivoR = (SectoresEvaluaciones) query.getSingleResult();
            return motivoR;
        } catch (Exception e) {
            SectoresEvaluaciones motivoR = null;
            return motivoR;
        }

    }
}
