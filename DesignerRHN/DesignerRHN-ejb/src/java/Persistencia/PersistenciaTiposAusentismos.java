/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import Entidades.Tiposausentismos;
import InterfacePersistencia.PersistenciaTiposAusentismosInterface;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

/**
 *
 * @author user
 */
@Stateless
public class PersistenciaTiposAusentismos implements PersistenciaTiposAusentismosInterface{
    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    @Override
    public void crear(Tiposausentismos tiposAusentismos) {
        try {
//            System.out.println("Persona: " + vigenciasFormales.getPersona().getNombreCompleto());
            em.merge(tiposAusentismos);
        } catch (PersistenceException ex) {
            System.out.println("Error PersistenciaTiposAusentismos.crear");
        }
    }

    // Editar Ausentismos. 
    @Override
    public void editar(Tiposausentismos tiposAusentismos) {
        em.merge(tiposAusentismos);
    }

    /*
     *Borrar Ausentismos.
     */
    @Override
    public void borrar(Tiposausentismos tiposAusentismos) {
        em.remove(em.merge(tiposAusentismos));
    }

    //Trae los Ausentismos de un Empleado
    public List<Tiposausentismos> buscarTiposAusentismos() {
        try {
            Query query = em.createQuery("SELECT ta FROM Tiposausentismos ta ORDER BY ta.codigo");
            List<Tiposausentismos> todosTiposAusentismos = query.getResultList();
            return todosTiposAusentismos;
        } catch (Exception e) {
            System.out.println("Error: (todasNovedades)" + e);
            return null;
        }
    }
}
