/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import Entidades.Indicadores;
import InterfacePersistencia.PersistenciaIndicadoresInterface;
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
public class PersistenciaIndicadores implements PersistenciaIndicadoresInterface{

    
    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    @Override
    public void crear(Indicadores indicadores) {
        try {
            em.persist(indicadores);
        } catch (Exception e) {
            System.out.println("Error crear PersistenciaIndicadores : "+e.toString());
        }
    }

    @Override
    public void editar(Indicadores indicadores) {
        try {
            em.merge(indicadores);
        } catch (Exception e) {
            System.out.println("Error editar PersistenciaIndicadores : "+e.toString());
        }
    }

    @Override
    public void borrar(Indicadores indicadores) {
        try {
            em.remove(em.merge(indicadores));
        } catch (Exception e) {
            System.out.println("Error borrar PersistenciaIndicadores : "+e.toString());
        }
    }

    @Override
    public Indicadores buscarIndicador(Object id) {
        try {
            BigInteger secuencia = new BigInteger(id.toString());
            return em.find(Indicadores.class, secuencia);
        } catch (Exception e) {
            System.out.println("Error buscarIndicador PersistenciaIndicadores : "+e.toString());
            return null;
        }

    }

    @Override
    public List<Indicadores> buscarIndicadores() {
        try {
            List<Indicadores> indicadores = (List<Indicadores>) em.createNamedQuery("Indicadores.findAll").getResultList();
            return indicadores;
        } catch (Exception e) {
            System.out.println("Error buscarIndicadores PersistenciaIndicadores : "+e.toString());
            return null;
        }
    }

    @Override
    public Indicadores buscarIndicadoresSecuencia(BigInteger secuencia) {

        try {
            Query query = em.createQuery("SELECT te FROM Indicadores te WHERE te.secuencia = :secuencia");
            query.setParameter("secuencia", secuencia);
            Indicadores indicadores = (Indicadores) query.getSingleResult();
            return indicadores;
        } catch (Exception e) {
            System.out.println("Error buscarIndicadoresSecuencia PersistenciaIndicadores : "+e.toString());
            Indicadores indicadores = null;
            return indicadores;
        }
    }

}