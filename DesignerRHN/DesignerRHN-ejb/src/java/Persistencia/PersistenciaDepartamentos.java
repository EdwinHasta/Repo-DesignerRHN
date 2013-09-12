/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import Entidades.Ciudades;
import Entidades.Departamentos;
import InterfacePersistencia.PersistenciaDepartamentosInterface;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless

public class PersistenciaDepartamentos implements PersistenciaDepartamentosInterface{

    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

        public List<Departamentos> departamentos() {
        try {
            Query query = em.createQuery("SELECT d FROM Departamentos d ORDER BY d.nombre");
            List<Departamentos> departamentos = query.getResultList();
            return departamentos;
        } catch (Exception e) {
            return null;
        }
    }
}



