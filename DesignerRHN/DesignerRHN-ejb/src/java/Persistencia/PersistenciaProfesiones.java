/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import Entidades.Profesiones;
import InterfacePersistencia.PersistenciaProfesionesInterface;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class PersistenciaProfesiones implements PersistenciaProfesionesInterface{

    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    @Override
        public List<Profesiones> profesiones() {
        try {
            Query query = em.createQuery("SELECT p FROM Profesiones p ORDER BY p.descripcion");
            List<Profesiones> profesiones = query.getResultList();
            return profesiones;
        } catch (Exception e) {
            return null;
        }
    }
}
