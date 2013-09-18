/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import Entidades.EstadosCiviles;
import InterfacePersistencia.PersistenciaEstadosCivilesInterface;
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

public class PersistenciaEstadosCiviles implements PersistenciaEstadosCivilesInterface{

    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

  
    @Override
    public void crear(EstadosCiviles estadosCiviles) {
        try{
        em.persist(estadosCiviles);
        } catch(Exception e){
            System.out.println("Error creando EstadosCiviles PersistenciaEstadosCiviles");
        }
    }

  
    @Override
    public void editar(EstadosCiviles estadosCiviles) {
        try {
        em.merge(estadosCiviles);
        } catch(Exception e){
            System.out.println("Error editando EstadosCiviles PersistenciaEstadosCiviles");
        }
    }

 
    @Override
    public void borrar(EstadosCiviles estadosCiviles) {
        try{
        em.remove(em.merge(estadosCiviles));
        } catch(Exception e){
            System.out.println("Error borrando EstadosCiviles PersistenciaEstadosCiviles");
        }
    }


    @Override
    public EstadosCiviles buscarEstadoCivil(Object id) {
        try {
            BigInteger in;
            in = (BigInteger) id;
            return em.find(EstadosCiviles.class, in);
        } catch (Exception e) {
            System.out.println("Error buscarEstadoCivil PersistenciaEstadosCiviles : "+e.toString());
            return null;
        }
    }

    @Override
    public List<EstadosCiviles> buscarEstadosCiviles() {
        try{
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(EstadosCiviles.class));
        return em.createQuery(cq).getResultList();
        } catch(Exception e){
            System.out.println("Error buscarEstadosCiviles PersistenciaEstadosCiviles");
            return null;
        }
    }

}
