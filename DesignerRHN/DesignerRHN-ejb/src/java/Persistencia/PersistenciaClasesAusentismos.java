/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import Entidades.Clasesausentismos;
import InterfacePersistencia.PersistenciaClasesAusentismosInterface;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

@Stateless
public class PersistenciaClasesAusentismos implements PersistenciaClasesAusentismosInterface {

    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    @Override
    public void crear(Clasesausentismos clasesAusentismos) {
        try {
//            System.out.println("Persona: " + vigenciasFormales.getPersona().getNombreCompleto());
            em.merge(clasesAusentismos);
        } catch (PersistenceException ex) {
            System.out.println("Error PersistenciaClasesAusentismos.crear");
        }
    }

    // Editar Ausentismos. 
    @Override
    public void editar(Clasesausentismos clasesAusentismos) {
        em.merge(clasesAusentismos);
    }

    /*
     *Borrar Ausentismos.
     */
    @Override
    public void borrar(Clasesausentismos clasesAusentismos) {
        em.remove(em.merge(clasesAusentismos));
    }

    //Trae los Ausentismos de un Empleado
    public List<Clasesausentismos> buscarClasesAusentismos() {
        try {
            Query query = em.createQuery("SELECT ca FROM Clasesausentismos ca ORDER BY ca.codigo");
            List<Clasesausentismos> todasClasesAusentismos = query.getResultList();
            return todasClasesAusentismos;
        } catch (Exception e) {
            System.out.println("Error: (todasNovedades)" + e);
            return null;
        }
    }
}
