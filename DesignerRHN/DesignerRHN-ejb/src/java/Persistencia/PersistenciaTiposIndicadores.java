/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import Entidades.TiposIndicadores;
import InterfacePersistencia.PersistenciaTiposIndicadoresInterface;
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
public class PersistenciaTiposIndicadores implements PersistenciaTiposIndicadoresInterface{

    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    @Override
    public void crear(TiposIndicadores tiposIndicadores) {
        try {
            em.persist(tiposIndicadores);
        } catch (Exception e) {
            System.out.println("Error crear PersistenciaTiposIndicadores : "+e.toString());
        }
    }

    @Override
    public void editar(TiposIndicadores tiposIndicadores) {
        try {
            em.merge(tiposIndicadores);
        } catch (Exception e) {
            System.out.println("Error editar PersistenciaTiposIndicadores : "+e.toString());
        }
    }

    @Override
    public void borrar(TiposIndicadores tiposIndicadores) {
        try {
            em.remove(em.merge(tiposIndicadores));
        } catch (Exception e) {
            System.out.println("Error borrar PersistenciaTiposIndicadores : "+e.toString());
        }
    }

    @Override
    public TiposIndicadores buscarTipoIndicador(Object id) {
        try {
            BigInteger secuencia = new BigInteger(id.toString());
            return em.find(TiposIndicadores.class, secuencia);
        } catch (Exception e) {
            System.out.println("Error buscarTipoIndicador PersistenciaTiposIndicadores : "+e.toString());
            return null;
        }

    }

    @Override
    public List<TiposIndicadores> buscarTiposIndicadores() {
        try {
            List<TiposIndicadores> tiposIndicadores = (List<TiposIndicadores>) em.createNamedQuery("TiposIndicadores.findAll").getResultList();
            return tiposIndicadores;
        } catch (Exception e) {
            System.out.println("Error buscarTiposIndicadores PersistenciaTiposIndicadores : "+e.toString());
            return null;
        }
    }

    @Override
    public TiposIndicadores buscarTiposIndicadoresSecuencia(BigInteger secuencia) {

        try {
            Query query = em.createQuery("SELECT te FROM TiposIndicadores te WHERE te.secuencia = :secuencia");
            query.setParameter("secuencia", secuencia);
            TiposIndicadores tiposIndicadores = (TiposIndicadores) query.getSingleResult();
            return tiposIndicadores;
        } catch (Exception e) {
            System.out.println("Error PersistenciaTiposIndicadores buscarTiposIndicadoresSecuencia : "+e.toString());
            TiposIndicadores tiposEntidades = null;
            return tiposEntidades;
        }
    }
  
}
