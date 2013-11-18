/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import Entidades.Causasausentismos;
import InterfacePersistencia.PersistenciaCausasAusentismosInterface;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

@Stateless
public class PersistenciaCausasAusentismos implements PersistenciaCausasAusentismosInterface {

    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    @Override
    public void crear(Causasausentismos causasAusentismos) {
        try {
//            System.out.println("Persona: " + vigenciasFormales.getPersona().getNombreCompleto());
            em.merge(causasAusentismos);
        } catch (PersistenceException ex) {
            System.out.println("Error PersistenciaCausasAusentismos.crear");
        }
    }

    // Editar Ausentismos. 
    @Override
    public void editar(Causasausentismos causasAusentismos) {
        em.merge(causasAusentismos);
    }

    /*
     *Borrar Ausentismos.
     */
    @Override
    public void borrar(Causasausentismos causasAusentismos) {
        em.remove(em.merge(causasAusentismos));
    }

    //Trae los Ausentismos de un Empleado
    public List<Causasausentismos> buscarCausasAusentismos() {
        try {
            Query query = em.createQuery("SELECT ca FROM Causasausentismos ca ORDER BY ca.codigo");
            List<Causasausentismos> todasCausasAusentismos = query.getResultList();
            return todasCausasAusentismos;
        } catch (Exception e) {
            System.out.println("Error: (todasCausas)" + e);
            return null;
        }
    }

}
