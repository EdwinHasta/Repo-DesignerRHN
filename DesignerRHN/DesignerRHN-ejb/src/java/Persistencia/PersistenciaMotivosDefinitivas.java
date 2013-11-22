/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import Entidades.Motivosdefinitivas;
import InterfacePersistencia.PersistenciaMotivosDefinitivasInterface;
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
public class PersistenciaMotivosDefinitivas implements PersistenciaMotivosDefinitivasInterface {

    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;
    /*
     * Crear MotivoDefinitiva.
     */

    public void crear(Motivosdefinitivas motivosDefinitivas) {
        em.persist(motivosDefinitivas);
    }

    /*
     *Editar MotivoDefinitiva. 
     */
    public void editar(Motivosdefinitivas motivosDefinitivas) {
        em.merge(motivosDefinitivas);
    }

    /*
     *Borrar MotivoDefinitiva.
     */
    public void borrar(Motivosdefinitivas motivosDefinitivas) {
        em.remove(em.merge(motivosDefinitivas));
    }

    public List<Motivosdefinitivas> buscarMotivosDefinitivas() {
        try {
            Query query = em.createQuery("SELECT md FROM Motivosdefinitivas md ORDER BY md.codigo");

            List<Motivosdefinitivas> motivoD = query.getResultList();
            return motivoD;
        } catch (Exception e) {
            System.out.println("Error PersistenciaMotivosDefinitivfas.buscarMotivosDefinitivas" + e);
            return null;
        }

    }
}
