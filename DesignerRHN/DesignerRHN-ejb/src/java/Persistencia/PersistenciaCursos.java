/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;


import Entidades.Cursos;
import InterfacePersistencia.PersistenciaCursosInterface;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;


@Stateless
public class PersistenciaCursos implements PersistenciaCursosInterface{
    
    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    @Override
        public List<Cursos> cursos() {
        try {
            Query query = em.createQuery("SELECT c FROM Cursos c ORDER BY c.nombre");
            List<Cursos> cursos = query.getResultList();
            return cursos;
        } catch (Exception e) {
            return null;
        }
    }
}


