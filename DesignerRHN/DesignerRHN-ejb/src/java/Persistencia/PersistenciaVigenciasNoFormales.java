/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import Entidades.VigenciasNoFormales;
import InterfacePersistencia.PersistenciaVigenciasNoFormalesInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

@Stateless
public class PersistenciaVigenciasNoFormales implements PersistenciaVigenciasNoFormalesInterface{
   @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;
    
    @Override
     public void crear(VigenciasNoFormales vigenciasNoFormales) {
        try {
            em.merge(vigenciasNoFormales);
        } catch (PersistenceException ex) {
            System.out.println("rjnsf");
        }
    }
       
     
    // Editar VigenciasNoFormales. 
     
    @Override
    public void editar(VigenciasNoFormales vigenciasNoFormales) {
        em.merge(vigenciasNoFormales);
    }

    /*
     *Borrar VigenciasNoFormales.
     */
    @Override
    public void borrar(VigenciasNoFormales vigenciasNoFormales) {
        em.remove(em.merge(vigenciasNoFormales));
    }
    
     /*
     *Encontrar todas las Vigencias Formales.
     */
    @Override
    public List<VigenciasNoFormales> buscarVigenciasNoFormales() {
        javax.persistence.criteria.CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(VigenciasNoFormales.class));
        return em.createQuery(cq).getResultList();
    }
    

    //METODO PARA TRAER LAS VIGENCIAS NO FORMALES DE UNA PERSONA

   @Override
    public List<VigenciasNoFormales> vigenciasNoFormalesPersona(BigInteger secuenciaPersona) {
        try {
            Query query = em.createQuery("SELECT vNF FROM VigenciasNoFormales vNF WHERE vNF.persona.secuencia = :secuenciaPersona ORDER BY vNF.fechavigencia DESC");
            query.setParameter("secuenciaPersona", secuenciaPersona);
            List<VigenciasNoFormales> listaVigenciasNoFormales = query.getResultList();
            return listaVigenciasNoFormales;
        } catch (Exception e) {
            System.out.println("Error PersistenciaTelefonos.telefonoPersona" + e);
            return null;
        }
    }

}
