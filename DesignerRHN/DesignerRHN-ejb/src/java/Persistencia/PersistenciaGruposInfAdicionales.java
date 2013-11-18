/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import Entidades.GruposInfAdicionales;
import InterfacePersistencia.PersistenciaGruposInfAdicionalesInterface;
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
public class PersistenciaGruposInfAdicionales implements PersistenciaGruposInfAdicionalesInterface{

   @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

  
    @Override
    public void crear(GruposInfAdicionales gruposInfAdicionales) { 
        try{
        em.persist(gruposInfAdicionales);
        } catch(Exception e){
            System.out.println("Error creando GruposInfAdicionales PersistenciaGruposInfAdicionales");
        }
    }

  
    @Override
    public void editar(GruposInfAdicionales gruposInfAdicionales) {
        try {
        em.merge(gruposInfAdicionales);
        } catch(Exception e){
            System.out.println("Error editando GruposInfAdicionales PersistenciaGruposInfAdicionales");
        }
    }

 
    @Override
    public void borrar(GruposInfAdicionales gruposInfAdicionales) {
        try{
        em.remove(em.merge(gruposInfAdicionales));
        } catch(Exception e){
            System.out.println("Error borrando GruposInfAdicionales PersistenciaGruposInfAdicionales");
        }
    }


    @Override
    public GruposInfAdicionales buscarGrupoInfAdicional(Object id) {
        try {
            BigInteger in;
            in = (BigInteger) id;
            return em.find(GruposInfAdicionales.class, in);
        } catch (Exception e) {
            System.out.println("Error buscarGrupoInfAdicional PersistenciaGruposInfAdicionales : "+e.toString());
            return null;
        }
    }

    @Override
    public List<GruposInfAdicionales> buscarGruposInfAdicionales() {
        try{
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(GruposInfAdicionales.class));
        return em.createQuery(cq).getResultList();
        } catch(Exception e){
            System.out.println("Error buscarGruposInfAdicionales PersistenciaGruposInfAdicionales");
            return null;
        }
    }

}
