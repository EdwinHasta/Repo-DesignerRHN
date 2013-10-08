/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;


import Entidades.PryRoles;
import InterfacePersistencia.PersistenciaPryRolesInterface;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
/**
 *
 * @author user
 */
@Stateless
public class PersistenciaPryRoles implements PersistenciaPryRolesInterface {

    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    @Override
        public List<PryRoles> pryroles() {
        try {
            Query query = em.createQuery("SELECT p FROM PryRoles p ORDER BY p.descripcion ");
            List<PryRoles> pryroles = query.getResultList();
            return pryroles;
        } catch (Exception e) {
            return null;
        }
    }
}
