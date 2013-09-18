/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import Entidades.Actividades;
import InterfacePersistencia.PersistenciaActividadesInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaQuery;

/**
 *
 * @author user
 */
@Stateless

public class PersistenciaActividades implements PersistenciaActividadesInterface{

    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    /*
     * Crear Actividades.
     */
    @Override
    public void crear(Actividades actividades) {
        try{
        em.persist(actividades);
        } catch(Exception e){
            System.out.println("Error creando bancos PersistenciaActividades");
        }
    }

    /*
     *Editar Actividades. 
     */
    @Override
    public void editar(Actividades actividades) {
        try {
        em.merge(actividades);
        } catch(Exception e){
            System.out.println("Error editando bancos PersistenciaActividades");
        }
    }

    /*
     *Borrar Actividades.
     */
    @Override
    public void borrar(Actividades actividades) {
        try{
        em.remove(em.merge(actividades));
        } catch(Exception e){
            System.out.println("Error borrando bancos PersistenciaActividades");
        }
    }

    /*
     *Encontrar un Actividades. 
     */
    @Override
    public Actividades buscarActividad(Object id) {
        try {
            BigInteger in;
            in = (BigInteger) id;
            return em.find(Actividades.class, in);
        } catch (Exception e) {
            System.out.println("Error buscarActividad PersistenciaActividades : "+e.toString());
            return null;
        }
    }

    /*
     *Encontrar todos los Actividades.
     */
    @Override
    public List<Actividades> buscarActividades() {
        try{
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(Actividades.class));
        return em.createQuery(cq).getResultList();
        } catch(Exception e){
            System.out.println("Error buscarActividades PersistenciaActividades : "+e.toString());
            return null;
        }
    }

}
