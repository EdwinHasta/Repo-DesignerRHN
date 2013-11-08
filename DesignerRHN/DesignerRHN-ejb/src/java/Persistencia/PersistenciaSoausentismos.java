/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import Entidades.Soausentismos;
import InterfacePersistencia.PersistenciaSoausentismosInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

@Stateless
public class PersistenciaSoausentismos implements PersistenciaSoausentismosInterface{
    
    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    @Override
    public void crear(Soausentismos soausentismos) {
        try {
//            System.out.println("Persona: " + vigenciasFormales.getPersona().getNombreCompleto());
            em.merge(soausentismos);
        } catch (PersistenceException ex) {
            System.out.println("Error PersistenciaSoausentismos.crear");
        }
    }
    
    // Editar Ausentismos. 
    @Override
    public void editar(Soausentismos soausentismos) {
        em.merge(soausentismos);
    }

    /*
     *Borrar Ausentismos.
     */
    @Override
    public void borrar(Soausentismos soausentismos) {
        em.remove(em.merge(soausentismos));
    }
    
    
    //Trae los Ausentismos de un Empleado
    public List<Soausentismos> ausentismosEmpleado (BigInteger secuenciaEmpleado) {
        try {
            Query query = em.createQuery("SELECT soa FROM Soausentismos soa WHERE soa.empleado.secuencia= :secuenciaEmpleado");
            query.setParameter("secuenciaEmpleado", secuenciaEmpleado);
            List<Soausentismos> todosAusentismos = query.getResultList();
            return todosAusentismos;
        } catch (Exception e) {
            System.out.println("Error: (todasNovedades)" + e);
            return null;
        }
    }
    
    
    
    
}
